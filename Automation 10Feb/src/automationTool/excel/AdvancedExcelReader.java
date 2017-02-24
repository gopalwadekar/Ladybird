package automationTool.excel;

import java.util.UUID;

import automationTool.main.AutomationException;
import automationTool.main.AutomationKeywords;
import automationTool.main.Constants;
import automationTool.main.PrintLog;
import automationTool.main.PrintResult;
import automationTool.main.Utilities;

public class AdvancedExcelReader {

	public boolean readRootSheet() {
		boolean d = false;
		System.out
				.println(".......................................................");
		try {
			System.out.println();
			System.out.println("Reading Root Sheet.............");
			Utilities.setPause(2);
			d = LoadExcel
					.loadExcelSheet("Project", "root", "root.xlsx", "root");
			if (d) {
				Constants.rootSheet = null;
				Constants.rootSheet = Constants.excelSheet;
				if (Constants.rootSheet != null) {
					Constants.rootSheetCount = 0;
					Constants.rootSheetCount = Constants.rootSheet.length;
					Constants.excelSheet = null;

					Constants.rootBaseUrl = (Utilities.readRootExcelValue("1"));
					Constants.rootUrlEnv = Utilities.readRootExcelValue("2");
					Constants.rootNavLoc = Utilities.readRootExcelValue("3");
					Constants.rootNavFldr = Utilities.readRootExcelValue("4");
					Constants.rootNavFile = Utilities.readRootExcelValue("5");
					Constants.rootNavSheetName = Utilities
							.readRootExcelValue("6");

					Constants.rootTCLoc = Utilities.readRootExcelValue("7");
					Constants.rootTCFldr = Utilities.readRootExcelValue("8");
					Constants.rootTCFile = Utilities.readRootExcelValue("9");
					Constants.rootEmailId = Utilities.readRootExcelValue("10");
					Constants.rootEmailPswd = Utilities
							.readRootExcelValue("11");
					Constants.rootEmailTo = Utilities.readRootExcelValue("12");
					Constants.rootEmailFlag = Utilities.readRootExcelValue("13");
					Constants.rootDB_InsFlag = Utilities.readRootExcelValue("14");

				}
			}

		} catch (Exception e) {
			System.out.println("###########################");
			System.out.println("EXCEPTION : readNaviFlowSheet ");
			System.out.println("Error : " + e.getMessage());
			System.out.println("###########################");
		}

		if (d) {

			System.out.println("PASSED : readRootSheet ");

			Utilities.ExecutionId = "";
			Utilities.ExecutionId = "TA_Web:" + UUID.randomUUID().toString();
			Utilities.LOG = new PrintLog();
			Utilities.LOG.newLine = "\n";

			Utilities.LOG.printLogHeaderDesign(d);

		} else {
			System.out.println("FAILED : readRootSheet ");
		}
		System.out.println();
		System.out
				.println(".......................................................");

		return d;
	}

	public boolean readNaviFlowSheet() {
		boolean d = false;
		try {

			Utilities.LOG.newLine = "\n";
			Utilities.LOG
					.saveOutput("\n======================================");
			System.out.println("--------------------------------------");
			Constants.navigationFileLoc = Constants.rootNavLoc;
			Constants.navigationFolder = Constants.rootNavFldr;
			Constants.navigationFile = Constants.rootNavFile;
			Constants.navigationSheetName = Constants.rootNavSheetName;

			System.out.println();

			if (LoadExcel.loadExcelSheet(Constants.navigationFileLoc,
					Constants.navigationFolder, Constants.navigationFile,
					Constants.navigationSheetName)) {

				Constants.naviFlowSheet = null;
				Constants.naviFlowSheet = Constants.excelSheet;
				if (Constants.naviFlowSheet != null) {
					Constants.naviFlowSheetCount = 0;
					Constants.naviFlowSheetCount = Constants.naviFlowSheet.length;
					Constants.excelSheet = null;
					d = true;
				}

			}

		} catch (Exception e) {
			System.out.println("###########################");
			System.out.println("EXCEPTION : readNaviFlowSheet ");
			System.out.println("Error : " + e.getMessage());
			System.out.println("###########################");
		}

		if (d) {
			System.out.println("PASSED : readNaviFlowSheet ");

			System.out.println("Total Navigation Scripts is ["
					+ (Constants.naviFlowSheetCount - 1) + "] :");

			Utilities.LOG.saveOutputInBox(Constants.navigationSheetName);
			
		
			
			Utilities.LOG.saveOutput("Total Navigation Scripts have to Run ["
					+ (Constants.naviFlowSheetCount - 1) + "] :");
			for (int i = 1; i < Constants.naviFlowSheetCount; i++) {

				Utilities.LOG
						.saveOutput(i
								+ ". "
								+ Constants.naviFlowSheet[i][Constants.followSheetName]);
				System.out
						.println(i
								+ ". "
								+ Constants.naviFlowSheet[i][Constants.followSheetName]);

			}

		} else {
			Utilities.LOG.saveOutput("FAILED: Navigation Flow Excel Sheet ");
			System.out.println("FAILED : readNaviFlowSheet ");
		}
		System.out.println();
		System.out.println("--------------------------------------");
		Utilities.LOG.saveOutput("======================================\n");

		return d;
	}

	public boolean readRuntimeNaviFlowLine(String lineNo,
			String fllowSheetName, String fllowSheetTestCasesName) {

		// String d[] = null;
		boolean d = false;
		System.out.println();
		try {

			System.out
					.println("______________________________________________________");
			System.out.println("STARTED : Reading Line.....");
			// d = new String[4];

			System.out.println();
			System.out.println("Fllow No                : " + lineNo);
			System.out.println("FlowSheetName           : " + fllowSheetName);
			System.out.println("FlowTestCasesSheetName  : "
					+ fllowSheetTestCasesName);

			System.out.println();

			if (!lineNo.equals("0")) {
				Utilities.LOG.newLine = "\n";
				Utilities.LOG
						.saveOutput("******************************************************");
				Utilities.LOG.saveOutputInBox(fllowSheetName);
			}

			Constants.getFllowSheetName = fllowSheetName;

			if (Constants.getFllowTestCaseSheetName
					.equals(fllowSheetTestCasesName)) {

				Constants.isSameTestCaseSheet = true;
			} else {
				Constants.isSameTestCaseSheet = false;
				Constants.getFllowTestCaseSheetName = fllowSheetTestCasesName;
			}

			d = true;
			// d[0] = fllowSheetName;
			// d[1] = fllowSheetTestCasesName;

		} catch (Exception e) {
			System.out.println("###########################");
			System.out.println("EXCEPTION : readRuntimeNaviLine LineNo: "
					+ lineNo);
			System.out.println("Error : " + e.getMessage());
			System.out.println("###########################");
		}

		if (d) {
			
			System.out.println("PASSED : LineNo: " + lineNo
					+ " readed sucessfully");
		} else {
			System.out.println("FAILED : LineNo: " + lineNo
					+ " readed unsucessfully");
		}

		System.out
				.println("______________________________________________________");
		System.out.println();
		return d;

	}

	public boolean readRuntimeNavigationSheet(String navigateSheetName) {
		boolean d = false;
		try {

			System.out.println("............................");

			if (!navigateSheetName.isEmpty()) {

				System.out
						.println("Navigating to sheet : " + navigateSheetName);

				Constants.navigationFileLoc = Constants.rootNavLoc;
				Constants.navigationFolder = Constants.rootNavFldr;
				Constants.navigationFile = Constants.rootNavFile;
				Constants.navigationSheetName = navigateSheetName;

				if (LoadExcel.loadExcelSheet(Constants.navigationFileLoc,
						Constants.navigationFolder, Constants.navigationFile,
						Constants.navigationSheetName)) {
					Constants.naviRuntimeNaviSheet = null;
					Constants.naviRuntimeNaviSheet = Constants.excelSheet;
					if (Constants.naviRuntimeNaviSheet != null) {
						Constants.naviRuntimeNaviSheetCount = 0;
						Constants.naviRuntimeNaviSheetCount = Constants.naviRuntimeNaviSheet.length;
						Constants.excelSheet = null;
						d = true;
					}

				}

			} else {

				System.out.println("Navigation sheetName not Found");

			}

		} catch (Exception e) {
			System.out.println("###########################");
			System.out.println("EXCEPTION : readRuntimeNavigationSheet : "
					+ navigateSheetName);
			System.out.println("Error : " + e.getMessage());
			System.out.println("###########################");
		}

		if (d) {
			System.out.println("PASSED : readRuntimeNavigationSheet : "
					+ navigateSheetName);
		} else {
			System.out.println("FAILED : readRuntimeNavigationSheet : "
					+ navigateSheetName);
		}

		System.out.println("............................");
		return d;
	}

	public String[] readRuntimeNaviLine(String lineNo, String lineStep,
			String lineKeyword, String lineSelector, String lineSelectorValue,
			String lineParameter, String lineTestCaseNo) {

		String d[] = null;

		System.out.println();
		try {

			System.out
					.println("______________________________________________________");
			System.out.println("STARTED : Reading Naigation Line.....");

			d = new String[5];

			System.out.println();
			System.out.println("Line No: "
					+ ((lineNo.isEmpty()) ? "no Line No Found" : lineNo));
			System.out.println("Line Step: "
					+ ((lineStep.isEmpty()) ? "no Line Step Found" : lineStep));
			System.out.println("Line Keyword: "
					+ ((lineKeyword.isEmpty()) ? "no Line Keyword Found"
							: lineKeyword));
			System.out.println("Line Selector: "
					+ ((lineSelector.isEmpty()) ? "no Line Selector Found"
							: lineSelector));
			System.out
					.println("Line SelectorValue: "
							+ ((lineSelectorValue.isEmpty()) ? "no Line Selector Value Found"
									: lineSelectorValue));

			System.out.println("Line Parameter: "
					+ ((lineParameter.isEmpty()) ? "no Line Parameter Found"
							: lineParameter));
			if (lineTestCaseNo.equalsIgnoreCase("SAVE")) {
				Utilities.saveExel = false;
				Utilities.saveExel = true;
				Constants.isTestCaseNoFound = false;
				System.out
						.println("Line TestCaseNo: "
								+ ((lineTestCaseNo.isEmpty()
										|| lineTestCaseNo == null || lineTestCaseNo
											.equalsIgnoreCase("SAVE")) ? "no TestCase No Found"
										: lineTestCaseNo));
			} else {
				Utilities.saveExel = false;
				System.out
						.println("Line TestCaseNo: "
								+ ((lineTestCaseNo.isEmpty()
										|| lineTestCaseNo == null || lineTestCaseNo
											.equalsIgnoreCase("SAVE")) ? "no TestCase No Found"
										: lineTestCaseNo));
			}

			System.out.println();

			d[0] = ((lineKeyword.isEmpty()) ? "" : lineKeyword);
			d[1] = ((lineSelector.isEmpty()) ? "" : lineSelector);
			d[2] = ((lineSelectorValue.isEmpty()) ? "" : lineSelectorValue);
			d[3] = ((lineParameter.isEmpty()) ? "" : lineParameter);
			d[4] = ((lineTestCaseNo.isEmpty()) ? "no TestCase No Found"
					: lineTestCaseNo);

		} catch (Exception e) {
			System.out.println("###########################");
			System.out.println("EXCEPTION : readRuntimeNaviLine LineNo: "
					+ lineNo);
			System.out.println("Error : " + e.getMessage());
			System.out.println("###########################");
		}

		if (d != null) {
			System.out.println("PASSED : LineNo: " + lineNo
					+ " readed sucessfully");
		} else {
			System.out.println("FAILED : LineNo: " + lineNo
					+ " readed unsucessfully");
		}

		System.out
				.println("______________________________________________________");
		System.out.println();
		return d;

	}

	public int executeRuntimeNaviLineToExecute(String lineParams[]) {

		int d = 0;
		String sheetName = "";
		try {

			System.out
					.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
			System.out
					.println("STARTED : Executing Navigation Line..... form  Excel Sheet");

			if (lineParams != null) {
				sheetName = lineParams[2];

				System.out.println("Keyword : " + lineParams[0]);
				System.out.println("Selector : " + lineParams[1]);
				System.out.println("SelectorValue : " + lineParams[2]);
				System.out.println("Parameter : " + lineParams[3]);
				System.out.println("TestCaseNo : " + lineParams[4]);

				if (lineParams[4].equals("no TestCase No Found")) {
					Constants.isTestCaseNoFound = false;
				} else {
					Constants.isTestCaseNoFound = true;
				}

				AutomationKeywords.keywordName = "";
				AutomationKeywords.keywordName = lineParams[0];
				AutomationKeywords.selector = "";
				AutomationKeywords.selector = lineParams[1];
				AutomationKeywords.selectorValue = "";
				AutomationKeywords.selectorValue = lineParams[2];
				AutomationKeywords.parameter = "";
				AutomationKeywords.parameter = lineParams[3];

				if (Constants.isTestCaseNoFound) {
					Utilities.startTime = 0;
					Utilities.startTime = System.currentTimeMillis();
					Utilities.setPause(1);
					Utilities.testCase_lineParams = null;
					Utilities.testCase_lineParams = readRuntimeTestCaseLine(lineParams[4]);
				}

				if (!Constants.abortIt) {

					AutomationKeywords.executeKeyword();
				}

				d = 1;

			}

			System.out.println();

		} catch (Exception e) {
			System.out.println("###########################");
			System.out
					.println("EXCEPTION : To Exceute Navigation Excel Sheet : "
							+ sheetName);
			System.out.println("Error : " + e.getMessage());
			System.out.println("###########################");
		}

		if (d > 0) {
			System.out.println("PASSED : To Execute Navigation Excel Sheet : "
					+ sheetName);
		} else {
			System.out.println("FAILED : To Execute Navigation Excel Sheet : "
					+ sheetName);
		}

		System.out
				.println("\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

		return d;

	}

	public boolean readRuntimeNavigationTestCasesSheet(
			String navigateTestCasesSheetName) {
		boolean d = false;
		try {
			System.out.println();
			System.out.println();
			System.out.println("............................");

			if (!navigateTestCasesSheetName.isEmpty()) {

				if (!Constants.isSameTestCaseSheet) {

					System.out.println("Navigating to TestCases sheet : "
							+ navigateTestCasesSheetName);
					Constants.testCaseLoc = Constants.rootTCLoc;
					Constants.testCaseFolder = Constants.rootTCFldr;
					Constants.testCaseFile = Constants.rootTCFile;
					Constants.testCaseSheetName = navigateTestCasesSheetName;

					if (LoadExcel.loadExcelSheet(Constants.testCaseLoc,
							Constants.testCaseFolder, Constants.testCaseFile,
							Constants.testCaseSheetName)) {
						Constants.naviRuntimeNaviTestCasesSheet = null;
						Constants.naviRuntimeNaviTestCasesSheet = Constants.excelSheet;
						if (Constants.naviRuntimeNaviTestCasesSheet != null) {
							Constants.naviRuntimeNaviTestCasesSheetCount = 0;
							Constants.naviRuntimeNaviTestCasesSheetCount = Constants.naviRuntimeNaviTestCasesSheet.length;
							Constants.excelSheet = null;
							d = true;
						}

					}

				} else {
					d = true;
					System.out.println("Navigation sheetName is Same");

				}

			} else {

				System.out.println("Navigation sheetName not Found");

			}

		} catch (Exception e) {
			System.out.println("###########################");
			System.out
					.println("EXCEPTION : readRuntimeNavigationTestCasesSheet : "
							+ navigateTestCasesSheetName);
			System.out.println("Error : " + e.getMessage());
			System.out.println("###########################");
		}

		if (d) {
			System.out.println("PASSED : readRuntimeNavigationTestCasesSheet :"
					+ navigateTestCasesSheetName);
		} else {
			System.out
					.println("FAILED : readRuntimeNavigationTestCasesSheet : "
							+ navigateTestCasesSheetName);
		}

		System.out.println("............................");
		System.out.println();
		System.out.println();
		return d;
	}

	public String[] readRuntimeTestCaseLine(String lineNo)
			throws AutomationException {

		String[] d = null;
		System.out.println();
		try {
			Constants.abortIt = false;
			System.out
					.println("______________________________________________________");
			System.out.println("STARTED : Reading TestCase Line.....");

			d = new String[9];

			System.out.println();

			if (lineNo.isEmpty()) {
				System.out.println("TestCase Line is Empty ");
				Utilities.saveExel = false;
				d[0] = "TestCase Line is Empty ";
				Constants.isTestCaseNoFound = false;
				// Constants.isTestCaseFound = false;
			} else {

				if (lineNo.equalsIgnoreCase("SAVE")) {
					Utilities.saveExel = true;
					Constants.isTestCaseNoFound = false;
					d[0] = "TestCase Line is Empty ";
				} else {
					Utilities.saveExel = false;
					int lineNum = Integer.parseInt(lineNo);

					if (lineNum < 0
							|| lineNum > Constants.naviRuntimeNaviTestCasesSheetCount) {
						System.out.println("TestCase Line is Not Found ");
						Constants.abortIt = true;
						Constants.isTestCaseNoFound = false;
						d = null;
					} else {
						Constants.isTestCaseNoFound = true;
						System.out.println("Please Wait.....");
						System.out.println("Reading Test Cases for Line No : "
								+ lineNum);
						System.out.println();

						d[0] = Constants.getExcelCellData("naviTestCaseSheet",
								lineNum, Constants.runTimeTestCaseSheetSrNo);// Constants.naviRuntimeNaviTestCasesSheet[lineNum][Constants.runTimeTestCaseSheetSrNo];
						d[1] = Constants.getExcelCellData("naviTestCaseSheet",
								lineNum, Constants.runTimeTestCaseSheetModule);// Constants.naviRuntimeNaviTestCasesSheet[lineNum][Constants.runTimeTestCaseSheetModule];
						d[2] = Constants.getExcelCellData("naviTestCaseSheet",
								lineNum,
								Constants.runTimeTestCaseSheetSubModule);// Constants.naviRuntimeNaviTestCasesSheet[lineNum][Constants.runTimeTestCaseSheetSubModule];
						d[3] = Constants.getExcelCellData("naviTestCaseSheet",
								lineNum,
								Constants.runTimeTestCaseSheetTestSeneraio);// Constants.naviRuntimeNaviTestCasesSheet[lineNum][Constants.runTimeTestCaseSheetTestSeneraio];
						d[4] = Constants.getExcelCellData("naviTestCaseSheet",
								lineNum,
								Constants.runTimeTestCaseSheetTestCaseId);// Constants.naviRuntimeNaviTestCasesSheet[lineNum][Constants.runTimeTestCaseSheetTestCaseId];
						d[5] = Constants
								.getExcelCellData("naviTestCaseSheet", lineNum,
										Constants.runTimeTestCaseSheetTestCase);// Constants.naviRuntimeNaviTestCasesSheet[lineNum][Constants.runTimeTestCaseSheetTestCase];
						d[6] = Constants.getExcelCellData("naviTestCaseSheet",
								lineNum,
								Constants.runTimeTestCaseSheetTestCaseDataId);// Constants.naviRuntimeNaviTestCasesSheet[lineNum][Constants.runTimeTestCaseSheetTestCaseDataId];
						d[7] = Constants.getExcelCellData("naviTestCaseSheet",
								lineNum,
								Constants.runTimeTestCaseSheetTestCaseData);// Constants.naviRuntimeNaviTestCasesSheet[lineNum][Constants.runTimeTestCaseSheetTestCaseData];
						d[8] = Constants.getExcelCellData("naviTestCaseSheet",
								lineNum,
								Constants.runTimeTestCaseSheetExpectedResult);// Constants.naviRuntimeNaviTestCasesSheet[lineNum][Constants.runTimeTestCaseSheetExpectedResult];

						System.out.println();

						if (d != null) {
							System.out.println();

							System.out
									.println("--------------------------------------");

							System.out.println("Sr.No               : " + d[0]);
							System.out.println("Module              : " + d[1]);
							System.out.println("Sub Module          : " + d[2]);
							System.out.println("Test Seanario       : " + d[3]);
							System.out.println("Test Case Id        : " + d[4]);
							System.out.println("Test Case           : " + d[5]);
							System.out.println("Test Case Data Id   : " + d[6]);
							System.out.println("Test Case Data      : " + d[7]);
							System.out.println("Expected Result     : " + d[8]);

							System.out
									.println("--------------------------------------");

							System.out.println();

							// Constants.isTestCaseFound = true;
						}

					}

				}

			}

			System.out.println();

		} catch (Exception e) {
			d = null;
			System.out.println("###########################");
			System.out.println("EXCEPTION : readRuntimeTestCaseLine LineNo: "
					+ lineNo);
			System.out.println("Error : " + e.getMessage());
			System.out.println("###########################");
		}

		if (d != null) {
			System.out.println("PASSED : LineNo: " + lineNo
					+ " readed sucessfully");
		} else {
			System.out.println("FAILED : LineNo: " + lineNo
					+ " readed unsucessfully");
		}

		System.out
				.println("______________________________________________________");
		System.out.println();
		return d;

	}

	public boolean executeRuntimeTestCasesExcelSheet(String lineParams[]) {

		boolean d = false;
		String sheetName = "";
		// String[] testResults = null;
		try {

			System.out
					.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			System.out
					.println("STARTED : Executing Test Cases Line.....from Excel Sheet");

			Utilities.endTime = 0;
			Utilities.endTime = System.currentTimeMillis();
			// Constants.expectedResult = null;
			// Constants.expectedResult = lineParams[8];
			// testResults = Utilities.Test(lineParams[7]);

			PrintResult.printResultOutputs(lineParams,
					Constants.testResults[0], Constants.testResults[1],
					Constants.testResults[2], Utilities.getExecutionTime(
							Utilities.startTime, Utilities.endTime));

			System.out.println();

			d = true;

		} catch (Exception e) {
			System.out.println("###########################");
			System.out
					.println("EXCEPTION : To Execute Test Cases Excel Sheet : "
							+ sheetName);
			System.out.println("Error : " + e.getMessage());
			System.out.println("###########################");
		}

		if (d) {
			System.out.println("PASSED : To Execute Test Cases Excel Sheet : "
					+ sheetName);
		} else {
			System.out.println("FAILED : To Execute Test Cases Excel Sheet : "
					+ sheetName);
		}

		System.out
				.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

		return d;

	}

	public static boolean readExcel(String folderName, String fileName,
			String sheetName) {
		boolean d = false;

		try {
			
			if (LoadExcel.loadExcelSheet("Project", folderName, fileName,
					sheetName)) {

				Constants.readDataSheet = null;
				Constants.readDataSheet = Constants.excelSheet;
				if (Constants.readDataSheet != null) {
					Constants.readDataSheetCount = 0;
					Constants.readDataSheetCount = Constants.readDataSheet.length;
					Constants.excelSheet = null;
					d = true;
				}

			}

		} catch (Exception e) {
			System.out.println("Exception in readExcel");
		}

		if (d) {
			System.out.println("PASSED : Excel sheet " + fileName
					+ " is readed  " + sheetName);
			System.out.println("==========================================");

			for (int i = 1; i < Constants.readDataSheetCount+1; i++) {

				System.out.println("(" + i + ") "
						+ Constants.readDataSheet[i][0] + " - "
						+ Constants.readDataSheet[i][1]);

			}
			System.out.println("==========================================");

		} else {
			System.out.println("FAILED : To read Excel " + fileName
					+ " sheet " + sheetName);
		}
		return d;
	}




public static boolean reReadExcel(String folderName, String fileName,
		String sheetName) {
	boolean d = false;
	LoadExcel reRead = new LoadExcel();
	if(reRead==null)
		System.out.println("As expected");
	reRead.file = null;
	try {
		
		if (LoadExcel.loadExcelSheet("Project", folderName, fileName,
				sheetName)) {

			Constants.readDataSheet = null;
			Constants.readDataSheet = Constants.excelSheet;
			if (Constants.readDataSheet != null) {
				Constants.readDataSheetCount = 0;
				Constants.readDataSheetCount = Constants.readDataSheet.length;
				Constants.excelSheet = null;
				d = true;
			}

		}

	} catch (Exception e) {
		System.out.println("Exception in readExcel");
	}

	if (d) {
		System.out.println("PASSED : Excel sheet " + fileName
				+ " is readed  " + sheetName);
		System.out.println("==========================================");

		for (int i = 1; i < Constants.readDataSheetCount+1; i++) {

			System.out.println("(" + i + ") "
					+ Constants.readDataSheet[i][0] + " - "
					+ Constants.readDataSheet[i][1]);

		}
		System.out.println("==========================================");

	} else {
		System.out.println("FAILED : To read Excel " + fileName
				+ " sheet " + sheetName);
	}
	return d;
}

}

