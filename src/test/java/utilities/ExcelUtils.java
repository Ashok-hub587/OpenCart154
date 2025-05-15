package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook wb;
	public XSSFSheet ws;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	public String path;

	public ExcelUtils(String path) {
		this.path = path;
	}

	public int getRowCount(String xlsheet) throws IOException {

		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		int rowCount = ws.getLastRowNum();
		wb.close();
		fi.close();
		return rowCount;
	}

	public int getCellCount(String xlsheet, int rownum) throws IOException {

		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		int cellCount = ws.getRow(rownum).getLastCellNum();
		wb.close();
		fi.close();
		return cellCount;
	}

	public String getCellData(String xlsheet, int rownum, int cellnum) throws IOException {

		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		row = ws.getRow(rownum);
		cell = row.getCell(cellnum);

		String data;
		try {
			// data = cell.toString();
			DataFormatter formatter = new DataFormatter();
			data = formatter.formatCellValue(cell);// Return the formatted value of cell as a string reg
		} catch (Exception e) {
			data = "";
		}
		wb.close();
		fi.close();
		return data;
	}

	public void setCellData(String xlfile, String xlsheet, int rownum, int cellnum, String data)
			throws IOException {

		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		wb.getSheet(xlsheet).getRow(rownum).createCell(cellnum).setCellValue(data);

		fo = new FileOutputStream(xlfile);
		wb.write(fo);
		wb.close();
		fo.close();
		fi.close();
	}

	public void fillGreenColor(String xlfile, String xlsheet, int rownum, int cellnum) throws IOException {

		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		cell = wb.getSheet(xlsheet).getRow(rownum).createCell(cellnum);

		style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		fo = new FileOutputStream(xlfile);
		wb.write(fo);
		wb.close();
		fo.close();
		fi.close();
	}

	public void fillRedColor(String xlsheet, int rownum, int cellnum) throws IOException {

		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		cell = wb.getSheet(xlsheet).getRow(rownum).createCell(cellnum);

		style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		fo = new FileOutputStream(path);
		wb.write(fo);
		wb.close();
		fo.close();
		fi.close();
	}

	public void setCellData(String xlsheet, int rownum, int cellnum, String data) throws IOException {

		File xlfile = new File(path);
		if (!xlfile.exists()) { // if ifile not exist then create new file
			wb = new XSSFWorkbook();
			fo = new FileOutputStream(path);
			wb.write(fo);
		}

		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);

		if (wb.getSheetIndex(xlsheet) == -1) // if sheet not exist create new sheet
			wb.createSheet(xlsheet);
		ws = wb.getSheet(xlsheet);

		if (ws.getRow(rownum) == null) // if row not exist then create new row
			ws.createRow(rownum);
		row = ws.getRow(rownum);

		cell = row.createCell(cellnum);
		cell.setCellValue(data);
		fo = new FileOutputStream(path);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
	}
}
