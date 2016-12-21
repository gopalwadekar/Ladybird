package automationTool.excel;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import automationTool.main.Constants;

public class LoadExcel {

	public static DataFormatter df = new DataFormatter();
	public static File file = null;
	public static FileInputStream inpStrm = null;
	public static String file_exe = null;
	public static Workbook wrkBk = null;
	public static Sheet sheet = null;
	public static int rowCount = 0;
	public static int colCount = 0;
	public static String[][] sheetData = null;
	// public static String filePath = System.getProperty("user.dir") + "\\";
	public static String Excel_Data[][] = null;

	public static String[][] readExcelFile(String filePath, String folder,
			String fileName, String sheetName) {
		try {
			sheetData = null;
			rowCount = 0;
			file = loadFile(filePath, folder, fileName);
			if (file != null) {
				inpStrm = getFileInputStream(file);
				if (inpStrm != null) {
					file_exe = getFileExtension(fileName);
					wrkBk = loadWorkBook(file_exe);
					if (wrkBk != null) {
						sheet = loadSheet(wrkBk, sheetName);
						if (sheet != null) {
							rowCount = getSheetRowCount(sheet);
							sheetData = getSheetData(sheet, rowCount);
							for (int i = 0; i < rowCount + 1; i++) {
								Row row = sheet.getRow(i);
								if (row != null && rowCount > 0) {
									for (int j = 0; j < row.getLastCellNum(); j++) {
										sheetData[i][j] = df
												.formatCellValue((sheet
														.getRow(i).getCell(j)));
									}
								}
							}

						}
					}
				}
			}
			return sheetData;

		} catch (Exception e) {
			System.out.println("###########################");
			System.out.println("EXCEPTION : readExcelFile : " + sheetName);
			System.out.println("Error : " + e.getMessage());
			System.out.println("###########################");
			return null;
		}

	}

	public static Workbook loadWorkBook(String file_exe) {
		Workbook w = null;
		if (!file_exe.isEmpty()) {
			try {

				if (file_exe.equals(".xlsx")) {
					w = new XSSFWorkbook(inpStrm);
				} else if (file_exe.equals(".xls")) {
					w = new HSSFWorkbook(inpStrm);
				}
			} catch (Exception e) {
				System.out.println("Error Found While Loading WorkBook");
			}
		} else {
			System.out.println("File Extension Not Found");
		}
		return w;
	}

	public static Sheet loadSheet(Workbook wk, String sheetName) {
		Sheet s = null;
		if (wk != null && !sheetName.isEmpty()) {
			try {
				s = wrkBk.getSheet(sheetName);
			} catch (Exception e) {
				System.out.println("Error Found While Loading Sheet");
			}
		} else {
			System.out.println("File WorkBook or Sheet Not Found");
		}
		return s;

	}

	public static File loadFile(String fp, String fldr, String fn) {
		File f = null;
		if (!fn.isEmpty() && !fp.isEmpty()) {
			try {

				if (fp.equalsIgnoreCase("Project")) {
					fp = System.getProperty("user.dir");
				}
				f = new File(fp + "\\" + fldr + "\\" + fn);
			} catch (Exception e) {
				System.out.println("Error Found While Loading File");
			}
		} else {
			System.out.println("File Path or File Name Not Found");
		}
		return f;

	}

	public static FileInputStream getFileInputStream(File f) {
		FileInputStream fis = null;
		if (f != null) {
			try {
				fis = new FileInputStream(f);
			} catch (Exception e) {
				System.out.println("Error Found While  FileInputStream");
			}
		} else {
			System.out.println("File Not Found");
		}
		return fis;
	}

	public static String getFileExtension(String fn) {
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

	public static int getSheetRowCount(Sheet s) {
		int cnt = 0;
		if (s != null) {
			try {
				cnt = s.getLastRowNum() - s.getFirstRowNum();
			} catch (Exception e) {
				System.out.println("Error Found on Row Count");
			}
		} else {
			System.out.println("Sheet Not Found on Row Count");
		}
		return cnt;
	}

	public static String[][] getSheetData(Sheet s, int rc) {
		String[][] data = null;
		if (s != null && rc > 0) {
			try {
				data = new String[rc + 1][s.getRow(0).getLastCellNum()];
				colCount = data.length;
			} catch (Exception e) {
				System.out.println("Error Found Loading Sheet Data");
			}
		} else {
			System.out.println("Sheet or Row Count Not Found");
		}
		return data;
	}

	public static String getSheetCellType(int cellType, Row r, int pstn) {
		String value = null;
		switch (cellType) {
		case Cell.CELL_TYPE_STRING:
			value = r.getCell(pstn).getStringCellValue() + "|| ";
			break;
		case Cell.CELL_TYPE_NUMERIC:
			value = r.getCell(pstn).getNumericCellValue() + "|| ";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = r.getCell(pstn).getBooleanCellValue() + "|| ";
			break;
		default:
			value = r.getCell(pstn).getStringCellValue() + "|| ";
			break;
		}
		return value;
	}

	public static boolean loadExcelSheet(String fileLoc, String folderName,
			String fileName, String sheetName) {
		System.out.println("::::::::::::::::::::::::::::::::::::::::::");
		System.out.println("Please Wait while loading Excel Sheet");
		System.out.println("::::::::::::[" + sheetName + "]:::::::::::::");
		boolean d = false;
		try {

			Constants.excelSheet = LoadExcel.readExcelFile(fileLoc, folderName,
					fileName, sheetName);

			if (Constants.excelSheet != null) {

				System.out.println("...................................");

				System.out.println("Excel [" + sheetName
						+ "] Sheet Found with Row : "
						+ Constants.excelSheet.length);

				System.out.println("...................................");

				d = true;
			} else {
				System.out.println("...................................");

				System.out.println("No Excel [" + sheetName + "] Sheet Found");

				System.out.println("...................................");
			}

		} catch (Exception e) {
			System.out.println("##############################");
			System.out.println("EXCEPTION : Excel Sheet " + sheetName
					+ " Loaded.");
			System.out.println("Error : " + e.getMessage());
			System.out.println("##############################");
		}

		if (d) {
			System.out
					.println("PASSED : Excel Sheet " + sheetName + " Loaded.");
		} else {
			System.out.println("FAILED : Excel Sheet " + sheetName
					+ " not Loaded.");
		}

		System.out.println("::::::::::::::::::::::::::::::::::::::::::");
		return d;

	}
}
