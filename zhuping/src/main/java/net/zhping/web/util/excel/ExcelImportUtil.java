package net.zhping.web.util.excel;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaError;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zhping.web.util.Converter;
import net.zhping.web.util.ReflectionUtil;
import net.zhping.web.util.excel.annotation.Name;
import net.zhping.web.util.excel.bean.ExcelImportVo;

/**
 * 
 * @author SAM
 *
 */
public class ExcelImportUtil {
	private static final Logger LOG = LoggerFactory.getLogger(ExcelImportUtil.class);

	/**
	 * 读取excel
	 * 
	 * @param input
	 *            excel流
	 * @param startRowIndex
	 *            开始行索引
	 * @param mappings
	 *            列与属性映射
	 * @param clazz
	 *            结果bean class
	 * @return 结果
	 * @throws Exception
	 */
	public static <T extends ExcelImportVo> List<T> readExcel(InputStream input, int startRowIndex, Map<String, ExcelMapping> mappings, Class<T> clazz,
			List<String> errorMessage) throws Exception {
		List<T> result = null;
		Workbook workBook = null;
		try {
			workBook = WorkbookFactory.create(input);
		} catch (Exception e){
			LOG.info(e.getMessage());
			errorMessage.add("文件格式不正确，请导入正确的Excel文档。");
		} finally {
			if (workBook != null) {
				workBook.close();
			}
		}
		if (workBook != null) {
			result = readWorkBook(workBook, startRowIndex, mappings, clazz, errorMessage);
		}
		return result;
	}

	private static <T extends ExcelImportVo> List<T> readWorkBook(Workbook workBook, int startRowIndex, Map<String, ExcelMapping> mappings,
			Class<T> clazz, List<String> errorMessage) throws Exception {
		List<T> result = new ArrayList<>();
		List<String> formatErrorMessage = new ArrayList<String>();
		List<String> dataErrorMessage = new ArrayList<String>();

		// 循环工作表Sheet
		for (Sheet sheet : workBook) {
			if (sheet == null) {
				continue;
			}
			
			if (sheet.getFirstRowNum() > startRowIndex) {
				formatErrorMessage.add(String.format("Sheet：%s，格式不正确，请使用正确的模板。", sheet.getSheetName()));
				continue;
			}

			// excel mapping
			if (mappings == null || mappings.isEmpty()) {
				mappings = getMappings(clazz, sheet.getRow(startRowIndex - 1), formatErrorMessage);
			}
			if (mappings == null || mappings.isEmpty()) {
				formatErrorMessage.add(String.format("Sheet：%s，表头不存在。", sheet.getSheetName()));
				continue;
			}

			// 循环行Row
			if (formatErrorMessage.size() == 0) {
				for (int rowNum = startRowIndex; rowNum <= sheet.getLastRowNum(); rowNum++) {
					Row row = sheet.getRow(rowNum);
					T vo = mappingRow(mappings, row, clazz, formatErrorMessage, dataErrorMessage);
					if (vo != null && CollectionUtils.isEmpty(formatErrorMessage) && CollectionUtils.isEmpty(dataErrorMessage)) {
						vo.setSheetName(sheet.getSheetName());
						vo.setRowNum(rowNum);
						result.add(vo);
					}
				}
			}
		}

		errorMessage.addAll(formatErrorMessage);
		errorMessage.addAll(dataErrorMessage);
		return result;
	}

	private static Map<String, ExcelMapping> getMappings(Class<? extends ExcelImportVo> clazz, Row header, List<String> formatErrorMessage)
			throws Exception {
		Map<String, ExcelMapping> mappings = new HashMap<String, ExcelMapping>();
		if (header == null) {
			return mappings;
		}

		List<String> headers = new ArrayList<>();
		short firstCellNum = 0;
		for (int cellNo = firstCellNum; cellNo < header.getLastCellNum(); cellNo++) {
			Cell cell = header.getCell(cellNo);
			String h = (String) getCellValue(cell, String.class);
			headers.add(h);
		}

		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			Name nameAnno = f.getAnnotation(Name.class);
			if (nameAnno == null) {
				continue;
			}

			// 字段名
			String propertyName = f.getName();

			// 类型
			Class<?> fClazz = f.getType();

			// 列号
			int cloumnIndex = -1;
			String name = nameAnno.value();
			int index = headers.indexOf(name);
			if (index == -1) {
				formatErrorMessage.add(String.format("Sheet：%s，Row：%s，表头【%s】不存在。", header.getSheet().getSheetName(),
						header.getRowNum() + 1, name));
			} else {
				cloumnIndex = firstCellNum + index;
			}

			// converter
			net.zhping.web.util.excel.annotation.Converter converterAnno = f
					.getAnnotation(net.zhping.web.util.excel.annotation.Converter.class);
			Converter converter = converterAnno != null ? converterAnno.value().newInstance() : null;

			// 是否可为空
			NotNull notBlankAnno = f.getAnnotation(NotNull.class);
			Boolean nullable = notBlankAnno == null;
			
			// 长度
			Size sizeAnno = f.getAnnotation(Size.class);
			Integer lengthMax = sizeAnno != null ? sizeAnno.max() : null;
			Integer lengthMin = sizeAnno != null ? sizeAnno.min() : null;
			
			// 大小
			Max maxAnno = f.getAnnotation(Max.class);
			Min minAnno = f.getAnnotation(Min.class);
			Long max = maxAnno != null ? maxAnno.value() : null;
			Long min = minAnno != null ? minAnno.value() : null;
			
			// 位数
			Digits digitsAnno = f.getAnnotation(Digits.class);
			Integer integer = digitsAnno != null ? digitsAnno.integer() : null;
			Integer fraction = digitsAnno != null ? digitsAnno.fraction() : null;

			ExcelMapping mapping = new ExcelMapping();
			mapping.setName(name);
			mapping.setClazz(fClazz);
			mapping.setCloumnIndex(cloumnIndex);
			mapping.setConverter(converter);
			mapping.setNullable(nullable);
			mapping.setLengthMax(lengthMax);
			mapping.setLengthMin(lengthMin);
			mapping.setMax(max);
			mapping.setMin(min);
			mapping.setInteger(integer);
			mapping.setFraction(fraction);

			mappings.put(propertyName, mapping);
		}
		return mappings;
	}

	/**
	 * 转换excel行为对象
	 * 
	 * @param mappings
	 * @param row
	 * @param clazz
	 * @return Object<T> OR null(存在非空列为空,row == null)
	 * @throws Exception
	 */
	private static <T> T mappingRow(Map<String, ExcelMapping> mappings, Row row, Class<T> clazz,
			List<String> formatErrorMessage, List<String> dataErrorMessage) throws Exception {
		if (null == row) {
			return null;
		}

		T target = clazz.newInstance();
		for (String propertyName : mappings.keySet()) {
			ExcelMapping mapping = mappings.get(propertyName);
			if (null != mapping) {
				Cell cell = row.getCell(mapping.getCloumnIndex(), MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Class<?> propertyClazz = mapping.getClazz();
				String name = mapping.getName();
				Object value = null;
				try {
					value = getCellValue(cell, propertyClazz);
				} catch (Exception e) {
					LOG.info(e.getMessage());
					formatErrorMessage.add(String.format("Sheet：%s，Row：%s，Column：%s，【%s】数据类型不正确。",
							row.getSheet().getSheetName(), row.getRowNum() + 1, mapping.getCloumnIndex() + 1, name));
				}
				if (CollectionUtils.isNotEmpty(formatErrorMessage)) {
					continue;
				}

				// 存在空的不能为空列
				if (!mapping.isNullable()) {
					if (value == null || (propertyClazz == String.class && StringUtils.isBlank((String) value))) {
						dataErrorMessage.add(String.format("Sheet：%s，Row：%s，Column：%s，【%s】不能为空。", row.getSheet().getSheetName(),
								row.getRowNum() + 1, mapping.getCloumnIndex() + 1, name));
						continue;
					}
				}
				
				if (value != null) {
					// 长度
					if (propertyClazz == String.class) {
						String valStr = (String) value;
						Integer lengthMax = mapping.getLengthMax();
						Integer lengthMin = mapping.getLengthMin();
						if ((lengthMax != null && valStr.length() > lengthMax) ||
								(lengthMin != null && valStr.length() < lengthMin)) {
							dataErrorMessage.add(String.format("Sheet：%s，Row：%s，Column：%s，【%s】长度超过限制。", row.getSheet().getSheetName(),
									row.getRowNum() + 1, mapping.getCloumnIndex() + 1, name));
							continue;
						}
					}
					
					if (propertyClazz.getSuperclass() == Number.class) {
						Double valNum = ((Number) value).doubleValue();
						
						// 大小
						Long max = mapping.getMax();
						Long min = mapping.getMin();
						if (max != null && valNum > max) {
							dataErrorMessage.add(String.format("Sheet：%s，Row：%s，Column：%s，【%s】大小超过限制，最大值为%s。", row.getSheet().getSheetName(),
									row.getRowNum() + 1, mapping.getCloumnIndex() + 1, name, max));
							continue;
						}
						if (min != null && valNum < min) {
							dataErrorMessage.add(String.format("Sheet：%s，Row：%s，Column：%s，【%s】大小超过限制，最小值为%s。", row.getSheet().getSheetName(),
									row.getRowNum() + 1, mapping.getCloumnIndex() + 1, name, min));
							continue;
						}
						
						// 精度
						if (propertyClazz == Float.class || propertyClazz == Double.class) {
							Integer integer = mapping.getInteger();
							Integer fraction = mapping.getFraction();
							String valStr = String.valueOf(valNum);
							String[] vals = valStr.split("[.]");
							if (integer != null && vals[0].length() > integer) {
								dataErrorMessage.add(String.format("Sheet：%s，Row：%s，Column：%s，【%s】整数部分超过限制，最大整数位数为%s。", row.getSheet().getSheetName(),
										row.getRowNum() + 1, mapping.getCloumnIndex() + 1, name, integer));
								continue;
							}
							if (fraction != null && vals.length > 1 && vals[1].length() > fraction) {
								dataErrorMessage.add(String.format("Sheet：%s，Row：%s，Column：%s，【%s】小数部分超过限制，最大小数位数为%s。", row.getSheet().getSheetName(),
										row.getRowNum() + 1, mapping.getCloumnIndex() + 1, name, fraction));
								continue;
							}
						}
					}
				}
				
				if (null != mapping.getConverter() && null != value) {
					value = mapping.getConverter().convertValue(cell, value);
				}
				if (null != value) {
					ReflectionUtil.invokeSetterMethod(target, propertyName, value);
				}
			}
		}
		return target;
	}

	/**
	 * 只涵括了常用类型，后续如果需要其他类型，还需要扩充
	 * 
	 * @param cell
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	private static Object getCellValue(Cell cell, Class<?> clazz) throws Exception {
		if (cell == null) {
			return null;
		}

		switch (cell.getCellTypeEnum()) {
		case BLANK:
			return null;
		case ERROR:
			String msg = FormulaError.forInt(cell.getErrorCellValue()).getString();
			throw new Exception("Error cell type: " + msg);
		default:
		}

		if (clazz == String.class) {
			return cell.getRichStringCellValue().getString();
		} else if (clazz == Date.class) {
			return cell.getDateCellValue();
		} else if (clazz == Double.class || clazz == double.class) {
			return cell.getNumericCellValue();
		} else if (clazz == Integer.class || clazz == int.class) {
			double value = cell.getNumericCellValue();
			if (value > Integer.MAX_VALUE) {
				throw new NumberFormatException();
			}
			return (int) value;
		} else if (clazz == Long.class || clazz == long.class) {
			double value = cell.getNumericCellValue();
			if (value > Long.MAX_VALUE) {
				throw new NumberFormatException();
			}
			return (long) value;
		} else if (clazz == Boolean.class || clazz == boolean.class) {
			return cell.getBooleanCellValue();
		} else {
			return null;
		}

	}
	
}
