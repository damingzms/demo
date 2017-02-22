package net.zhping.web.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import net.zhping.web.util.excel.annotation.Index;
import net.zhping.web.util.excel.annotation.Name;

/**
 * 
 * 创建Excel
 * 
 * @author SAM
 * 
 */
public class ExcelExportUtil {
	private static final Logger LOG = LoggerFactory.getLogger(ExcelExportUtil.class);

	public final static String DATA_START = "DATA_START";

	public final static String SHEET_NAME = "Sheet1";

	private XSSFWorkbook wb;
	private Sheet sheet;
	private Row row;

	private int curRowIndex;
	private int curColIndex;

	private int initRowIndex = 0;
	private int initColIndex = 0;

	private CreationHelper createHelper;
	private DataFormat format;

	private CellStyle dateStyle;
	private CellStyle doubleStyle;
	private CellStyle intStyle;

	private ExcelExportUtil() {}

	public static ExcelExportUtil getInstance() {
		return new ExcelExportUtil();
	}

	/**
	 * 生成excel文档，无需模板
	 * 
	 * @param title
	 * @param subTitle
	 * @param heads
	 * @param body
	 *            可以是对象列表或者数组列表（每个对象值占一个cell或者每个数组元素占一个cell）
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public ExcelExportUtil genWorkBook(String title, String subTitle, List<String> heads, List<?> body, ArrayList<Map<String, Object>> params) throws Exception {
		if (StringUtils.isBlank(title)) {
			throw new RuntimeException("表头不能为空!");
		}
		heads = CollectionUtils.isEmpty(heads) ? getHeads(body.get(0).getClass()) : heads;
		if (CollectionUtils.isEmpty(heads)) {
			throw new IllegalArgumentException("表头内容为空!");
		}
		init();
		createTitle(title, subTitle, heads.size());
		createHead(heads);
		createBody(body, heads, params);
		return this;
	};

	/**
	 * 生成excel文档，需要模板
	 * 
	 * @param templFilePath
	 * @param title
	 * @param subTitle
	 * @param body
	 *            可以是对象列表或者数组列表（每个对象值占一个cell或者每个数组元素占一个cell）
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public ExcelExportUtil genWorkBookWithTemplate(String templFilePath, String title, String subTitle, List<?> body, ArrayList<Map<String, Object>> params) throws Exception {
		if (CollectionUtils.isEmpty(body)) {
			throw new IllegalArgumentException("表内容为空!");
		}
		initWithTemplate(templFilePath);
		if (StringUtils.isNotBlank(title)) {
			sheet.getRow(0).getCell(0).setCellValue(title);
		}
		if (StringUtils.isNotBlank(subTitle)) {
			sheet.getRow(1).getCell(0).setCellValue(subTitle);
		}
		createBody(body, null, params);
		return this;
	}
	
	/**
	 * 导出到http response
	 * @throws Exception 
	 */
	public void export(HttpServletResponse response, String fileName) throws Exception {
		fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
		response.reset();
		response.setContentType("application/octet-stream; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
		OutputStream fOut = null;
		try {
			fOut = response.getOutputStream();
			wb.write(fOut);
		} finally {
			if (fOut != null) {
				try {
					fOut.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					fOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void init() throws Exception {
		wb = new XSSFWorkbook();
		createHelper = wb.getCreationHelper();
		format = wb.createDataFormat();
		sheet = wb.createSheet(SHEET_NAME);
		curRowIndex = initRowIndex - 1;
		curColIndex = initColIndex - 1;
	}

	private void initWithTemplate(String templFilePath) throws Exception {
		try {
			wb = new XSSFWorkbook(new FileInputStream(new File(templFilePath)));
		} catch (Exception e) {
			LOG.error("读取模板失败，路径：{}", templFilePath);
			throw e;
		}
		createHelper = wb.getCreationHelper();
		format = wb.createDataFormat();
		sheet = wb.getSheetAt(0);
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellTypeEnum() == CellType.STRING && DATA_START.equals(cell.getStringCellValue())) {
					initColIndex = cell.getColumnIndex();
					initRowIndex = row.getRowNum();
					curRowIndex = initRowIndex - 1;
					curColIndex = initColIndex - 1;
					return;
				}
			}
		}
	}

	private List<String> getHeads(Class<?> c) {
		List<String> heads = new ArrayList<>();
		List<Object[]> list = new ArrayList<>();
		Field[] fs = c.getDeclaredFields();
		if (fs == null) {
			return heads;
		}
		for (Field f : fs) {
			Name nameAnno = f.getAnnotation(Name.class);
			Index indexAnno = f.getAnnotation(Index.class);
			if (nameAnno != null && indexAnno != null) {
				Object[] arr = new Object[2];
				arr[0] = indexAnno.value();
				arr[1] = nameAnno.value();
				list.add(arr);
			}
		}
		heads.addAll(
				list.stream().sorted((o1, o2) -> (int) o1[0] == (int) o2[0] ? 0 : ((int) o1[0] > (int) o2[0] ? 1 : -1))
						.map(o -> (String) o[1]).collect(Collectors.toList()));
		return heads;
	}

	private Object getCellValueByName(Object rowObj, String name) throws Exception {
		Object value = null;
		Field[] fs = rowObj.getClass().getDeclaredFields();
		if (fs == null) {
			return value;
		}
		for (Field f : fs) {
			Name nameAnno = f.getAnnotation(Name.class);
			if (nameAnno != null && nameAnno.value() != null && nameAnno.value().equals(name)) {
				f.setAccessible(true);
				value = f.get(rowObj);
				break;
			}
		}
		return value;
	}

	private Object getCellValueByIndex(Object rowObj, int index) throws Exception {
		Object value = null;
		Field[] fs = rowObj.getClass().getDeclaredFields();
		if (fs == null) {
			return value;
		}
		boolean outOfBounds = true;
		for (Field f : fs) {
			Index indexAnno = f.getAnnotation(Index.class);
			if (indexAnno != null && indexAnno.value() == index) {
				f.setAccessible(true);
				value = f.get(rowObj);
				outOfBounds = false;
				break;
			}
			if (outOfBounds && indexAnno != null && indexAnno.value() > index) {
				outOfBounds = false;
			}
		}
		if (outOfBounds) {
			return new ArrayIndexOutOfBoundsException();
		}
		return value;
	}

	/**
	 * 方便其他地方使用统一的初始样式，再做扩展
	 * 
	 * @return
	 */
	private CellStyle createStyle() {
		CellStyle newStyle = wb.createCellStyle();
		return newStyle;
	}

	/**
	 * 创建新的行
	 * 
	 * @return
	 */
	private ExcelExportUtil createRow() {
		curColIndex = initColIndex - 1;
		row = sheet.createRow(++curRowIndex);
		return this;
	}

	/**
	 * 创建新的单元格
	 * 
	 * @return
	 */
	private Cell createCell() {
		Cell c = row.createCell(++curColIndex);
		return c;
	}

	/**
	 * 创建新的单元格(合并单元格)
	 * 
	 * @param rowSpan
	 *            跨行数
	 * @param colSpan
	 *            跨列数
	 * @return
	 */
	private Cell createCell(int rowSpan, int colSpan) {
		Cell c = row.createCell(++curColIndex);
		sheet.addMergedRegion(
				new CellRangeAddress(curRowIndex, curRowIndex + rowSpan - 1, curColIndex, curColIndex + colSpan - 1));
		curRowIndex += rowSpan - 1;
		curColIndex += colSpan - 1;
		return c;
	}

	private ExcelExportUtil setCellValue(Object value, Cell c, CellStyle style, Map<String, Object> params) {
		if (value == null) {
			c.setCellType(CellType.BLANK);
		} else if (value instanceof Boolean) {
			c.setCellValue((Boolean) value);
		} else if (value instanceof Date) {
			c.setCellValue((Date) value);
			if (style == null) {
				if (dateStyle != null) {
					style = dateStyle;
				} else {
					style = createStyle();
					CreationHelper createHelper = wb.getCreationHelper();
					style.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));
					dateStyle = style;
				}
			}
		} else if (value instanceof Number) {
			c.setCellValue(((Number) value).doubleValue());
			if (style == null) {
				if (value instanceof Double || value instanceof Float) {
					if (doubleStyle != null) {
						style = doubleStyle;
					} else {
						style = createStyle();
						style.setDataFormat(format.getFormat("#,##0.00"));
						doubleStyle = style;
					}
				} else {
					if (intStyle != null) {
						style = intStyle;
					} else {
						style = createStyle();
						style.setDataFormat(format.getFormat("#,##0"));
						intStyle = style;
					}
				}
			}
		} else {
			c.setCellValue(createHelper.createRichTextString(value.toString()));
		}
		if (style != null) {
			c.setCellStyle(style);
		}

		return this;
	}

	/**
	 * 创建表头、子表头
	 * 
	 * @param title
	 *            表头内容
	 * @param subTitle
	 *            子表头内容
	 * @param colSpan
	 *            表头宽度(占据的单元格的列的数量),从0开始
	 * @return
	 */
	private ExcelExportUtil createTitle(String title, String subTitle, int colSpan) {

		/** 表头 */
		if (StringUtils.isEmpty(title)) {
			return this;
		}
		createRow();
		Cell c = createCell(2, colSpan);
		c.setCellValue(title);

		/** 子表头 */
		if (StringUtils.isEmpty(subTitle)) {
			return this;
		}
		createRow();
		Cell subc = createCell(1, colSpan);
		subc.setCellValue(subTitle);

		return this;
	}

	/**
	 * 新建表头
	 * 
	 * @param heads
	 *            包含表头字符串的集合
	 * @return
	 */
	private ExcelExportUtil createHead(List<String> heads) {
		createRow();
		for (String h : heads) {
			Cell c = createCell();
			setCellValue(h, c, null, null);
		}
		return this;
	}

	/**
	 * 表的内容
	 * 
	 * @param body
	 *            包含行数据的集合
	 * @return
	 * @throws Exception
	 */
	private ExcelExportUtil createBody(List<?> body, List<String> heads, ArrayList<Map<String, Object>> params)
			throws Exception {
		if (body == null) {
			return this;
		}
		for (Object t : body) {
			createRow();
			if (t instanceof Object[]) {
				Object[] values = (Object[]) t;
				if (values == null || values.length < 1) {
					continue;
				}
				for (int i = 0; i < values.length; i++) {
					Object value = values[i];
					Map<String, Object> param = (params != null && params.size() > i ? params.get(i) : null);
					setCellValue(value, createCell(), null, param);
				}
			} else {
				if (CollectionUtils.isEmpty(heads)) {
					int index = 0;
					while (true) {
						Object value = getCellValueByIndex(t, index++);
						if (value instanceof ArrayIndexOutOfBoundsException) {
							break;
						}
						Map<String, Object> param = (params != null && params.size() > index ? params.get(index)
								: null);
						setCellValue(value, createCell(), null, param);
					}

				} else {
					for (int i = 0; i < heads.size(); i++) {
						String head = heads.get(i);
						Object value = getCellValueByName(t, head);
						Map<String, Object> param = (params != null && params.size() > i ? params.get(i) : null);
						setCellValue(value, createCell(), null, param);
					}
				}
			}
		}
		return this;
	}

}
