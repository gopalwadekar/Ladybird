package automationTool.excel;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class AdvancedExcelWriter {

	public static File file = null;
	public String filename = null;
	public FileOutputStream fos = null;
	public FileInputStream fis = null;
	public static XSSFWorkbook workbook = null;
	public static XSSFSheet sheet = null;
	public static Row row = null;
	public static int rowIdx = 0, noOFSheets = 0;
	public static Cell cell = null;
	public static String[] cellColumns = null;
	public static Map<String, CellStyle> cellStyles = null;
	public static XSSFCellStyle cellStyle;
	public static XSSFFont font;
	public static XSSFColor myColor;

	public String filePath = System.getProperty("user.dir") + "\\";

	public boolean createExcel(String folderName, String fileName,
			String sheetName) {
		boolean d = false, isExits = false, isCreated = false;

		file = null;
		file = createFile(filePath + folderName, fileName);
		try {
			fis = null;
			fis = new FileInputStream(file);
			if (fis != null) {

				isExits = true;
				isCreated = true;
				rowIdx = 0;
				System.out.println("Your excel file is Already Exits!");
//				if (createWorkBook(getFileExtension(fileName))) {
//					workbook.cloneSheet(0);
//				}
				//workbook.cloneSheet(0);
			}

		} catch (FileNotFoundException e) {
			System.out.println("Your excel file is not present!");
			isExits = false;
		}

		if (!isExits) {
			try {
				fos = new FileOutputStream(file);
				if (fos != null) {
					isCreated = true;
					System.out.println("Your excel file is created!");
				}
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException while creating");
			}
		}

		try {

			if (isCreated) {

				if (createWorkBook(getFileExtension(fileName))) {

					sheetName = splitSheetName(sheetName);

					if (createSheet(sheetName)) {
						createMultipleExcelCellStyles();
						d = saveInExcel(cellColumns);

						if (d) {
							d = true;
						} else {
							d = false;
						}

						// d = true;
					}

				}

			}

		} catch (Exception e) {
			System.out.println("EXCEPTION : Found while createExcel");
		}

		if (d) {
			System.out.println("PASSED : Excel " + fileName
					+ " is created with sheet " + sheetName);
		} else {
			System.out.println("FAILED : To created with Excel " + fileName
					+ " sheet " + sheetName);
		}
		return d;
	}

	public File createFile(String fp, String fn) {
		File f = null;
		if (!fn.isEmpty() && !fp.isEmpty()) {
			try {
				f = new File(fp + "\\" + fn);
			} catch (Exception e) {
				System.out.println("Error Found While Creating File");
			}
		} else {
			System.out.println("File Path or File Name Not Found");
		}
		return f;

	}

	public boolean createWorkBook(String file_exe) {
		boolean w = false;
		if (!file_exe.isEmpty()) {
			try {
				workbook = null;
				if (file_exe.equals(".xlsx")) {
					workbook = new XSSFWorkbook();
				} else if (file_exe.equals(".xls")) {
					workbook = new XSSFWorkbook();
				}

				if (workbook != null) {
					// noOFSheets = 0;
					noOFSheets = workbook.getNumberOfSheets();

					w = true;
				}

			} catch (Exception e) {
				System.out.println("Error Found While Creating WorkBook");
			}
		} else {
			System.out.println("File Extension Not Found");
		}
		return w;
	}

	public String getFileExtension(String fn) {
		String s = "";
		if (!fn.isEmpty()) {
			try {
				s = fn.substring(fn.indexOf("."));
			} catch (Exception e) {
				System.out.println("Error Found While  FileInputStream");
			}
		} else {
			System.out.println("File Not Found");
		}
		return s;
	}

	public boolean createSheet(String sheetName) {
		boolean d = false;
		if (!sheetName.isEmpty()) {
			try {
				sheet = null;
				sheet = workbook.createSheet(sheetName);
				if (sheet != null) {
					d = true;
				}

			} catch (Exception e) {
				System.out.println("Error Found While Creating Sheet");
			}
		} else {
			System.out.println("File WorkBook or Sheet Not Found");
		}
		return d;

	}

	public static Row getRow() {
		return row;
	}

	public static boolean saveInExcel(String data[])

	{

		boolean d = false;

		try {

			Row row = sheet.createRow(rowIdx);

			for (int i = 0; i < data.length; i++) {

				row.createCell(i).setCellValue(data[i]);

				cell = null;
				cell = row.createCell(i);
				cell.setCellValue(data[i]);

				if (cellStyles != null) {
					if (rowIdx == 0) {
						cell.setCellStyle(cellStyles.get("HEADER"));
					} else {
						if (data[1].isEmpty()) {
							cell.setCellStyle(cellStyles.get("NODATA"));
						} else {
							cell.setCellStyle(cellStyles.get("DATA"));
						}

					}
				}
				sheet.autoSizeColumn(i);
			}
			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();
			d = true;
			rowIdx++;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (d) {
			System.out.println("Data save in row: " + rowIdx);
		} else {
			System.out.println("No Data save in row: " + rowIdx);
		}

		return d;
	}

	public String splitSheetName(String sheetName) {
		String d = null;
		String data[] = null;
		try {

			if (sheetName.contains(":")) {

				data = sheetName.split(":");
				if (data.length > 0) {
					d = data[0];

					if (data.length == 2) {
						cellColumns = data[1].split(",");
					}
				}

			} else {
				d = sheetName;
			}
		} catch (Exception e) {
			System.out.println("Exception in splitSheetName");
		}

		return d;
	}

	public void createMultipleExcelCellStyles() {

		boolean d = false;
		try {
			cellStyles = null;
			cellStyles = new HashMap<String, CellStyle>();

			cellStyle = workbook.createCellStyle();
			myColor = new XSSFColor(new java.awt.Color(47, 117, 181));
			cellStyle.setFillForegroundColor(myColor);
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

			cellStyle.setAlignment(cellStyle.ALIGN_CENTER_SELECTION);
			font = workbook.createFont();
			font.setFontHeightInPoints((short) 16);
			font.setFontName("Calibri");
			font.setColor(new XSSFColor(new java.awt.Color(255, 255, 255)));
			font.setBold(true);
			cellStyle.setFont(font);
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);
			cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			cellStyles.put("HEADER", cellStyle);

			cellStyle = workbook.createCellStyle();
			myColor = new XSSFColor(new java.awt.Color(255, 255, 255));
			cellStyle.setFillForegroundColor(myColor);
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cellStyle.setAlignment(cellStyle.ALIGN_LEFT);
			font = workbook.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setFontName("Calibri");
			font.setColor(new XSSFColor(new java.awt.Color(0, 0, 0)));
			cellStyle.setFont(font);
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);
			cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			cellStyles.put("DATA", cellStyle);

			cellStyle = workbook.createCellStyle();
			myColor = new XSSFColor(new java.awt.Color(248, 46, 6));
			cellStyle.setFillForegroundColor(myColor);
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cellStyle.setAlignment(cellStyle.ALIGN_LEFT);
			font = workbook.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setFontName("Calibri");
			font.setColor(new XSSFColor(new java.awt.Color(255, 255, 255)));
			cellStyle.setFont(font);
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);
			cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			cellStyles.put("NODATA", cellStyle);

			d = true;

		} catch (Exception e) {
			System.out.println("Exception in createMultipleExcelCellStyles");
		}

		if (d) {
			System.out.println("Styles is created");
		} else {
			System.out.println("Styles is not created");
		}

	}

}
