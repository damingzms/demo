package net.zhping.web.util.excel.bean;

public abstract class ExcelImportVo {

	private String sheetName;
	
	private int rowNum;

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	
}
