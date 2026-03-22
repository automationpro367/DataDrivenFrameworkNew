package com.w2a.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ExcelReader {

	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	public ExcelReader(String path) {
		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) return 0;
		sheet = workbook.getSheetAt(index);
		return sheet.getLastRowNum() + 1;
	}

	public int getColumnCount(String sheetName) {
		if (!isSheetExist(sheetName)) return -1;
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		if (row == null) return -1;
		return row.getLastCellNum();
	}

	public boolean isSheetExist(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		return index != -1;
	}

	// Method 1: Data by Column Name (String)
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0) return "";
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1) return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			int col_Num = -1;
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1) return "";
			return getCellData(sheetName, col_Num, rowNum);
		} catch (Exception e) {
			return "Error";
		}
	}

	// Method 2: Data by Column Number (Integer) - YE SAHI KIYA HAI
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0) return "";
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1) return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null) return "";
			cell = row.getCell(colNum);
			if (cell == null) return "";

			if (cell.getCellType() == CellType.STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
				String cellText = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					double d = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
				}
				return cellText;
			} else if (cell.getCellType() == CellType.BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
			return "";
		}
	}
}