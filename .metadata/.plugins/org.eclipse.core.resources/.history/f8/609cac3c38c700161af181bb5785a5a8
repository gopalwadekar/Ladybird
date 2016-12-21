package automationTool.main;

import java.util.UUID;

import jnr.ffi.Struct.id_t;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import bsh.util.Util;

import automationTool.excel.*;

public class ExecuteAutomationTool {

	public static AdvancedExcelReader excel = new AdvancedExcelReader();
	public static String[] lineParams = null;
	public static String[] followLineParams = null;
	public static boolean isBrowserLoaded = false;
	public static String scriptName = "", scriptTime = "", startTime = "",
			endTime = "";
	public static Long start = null, end = null;

	public static void main(String[] args) {

		System.out.println("--- Dwellar Web AutomationTool Started ---");

		try {

			Constants.noOfScript = 0;
			Constants.nameOfScript.clear();
			Utilities.isRootLoaded = excel.readRootSheet();

			if (Utilities.isRootLoaded) {

				Utilities.LOG
						.saveOutputInBox("--- Dwellar Web AutomationTool Started ---");

				startTime = Utilities.createTime();
				start = System.currentTimeMillis();

				System.out
						.println("Please Wait reading Excel For Start Script............");

				Utilities.isNaviLoaded = excel.readNaviFlowSheet();

				RUN_GENERIC_SCRIPTS(Utilities.isNaviLoaded);

				endTime = Utilities.createTime();
				end = System.currentTimeMillis();
				scriptTime = "";
				scriptTime += "\n";
				scriptTime += "____________________________________________________________";
				scriptTime += "\nStart Time   : " + startTime;
				scriptTime += "\nEnd Time     : " + endTime;
				scriptTime += "\nTotal Time   : "
						+ Utilities.getExecutionTime(start, end)
								.replace("[ ", "").replace(" ]", "");
				scriptTime += "\n____________________________________________________________";
				scriptTime += "\n";
				Utilities.LOG.saveOutput(scriptTime);
				System.out.println(scriptTime);
				Utilities.LOG
						.saveOutputInBox("--- Dwellar Web AutomationTool Finished ---");

				Utilities.Email.automateSendEmail(Utilities.LOG.logFilePath);
				Utilities.LOG.closeFile();

				Utilities.autoExitProgram();
			}

		} catch (Exception e) {

			Utilities.LOG.newLine = "\n";
			Utilities.LOG
					.saveOutput("###########################################################");
			Utilities.LOG.saveOutputInBox("Exception in Loops");
			Utilities.LOG
					.saveOutput("###########################################################");

			System.out
					.println("--- Exception in Dwellar Web AutomationTool ---");
		}

		System.out.println("Execution Id : " + Utilities.ExecutionId);
		System.out.println("--- Dwellar Web AutomationTool Finished ---");

	}

	public static void RUN_GENERIC_SCRIPTS(boolean naviFlowSheet) {
		try {

			if (naviFlowSheet) {

				System.out.println("Please Wait loading Browser............");
				Utilities.setPause(2);
				isBrowserLoaded = Utilities.loadBroweser("Chrome");
				// isBrowserLoaded=true;
				if (isBrowserLoaded) {

					System.out
							.println("Please Wait reading Excel For loaded Scripts............");

					for (int i = 1; i < Constants.naviFlowSheetCount; i++) {
						Utilities.setPause(2);

						if (i == 1) {
							System.out
									.println("***********************************************");
							System.out
									.println("_____________________________________");
							System.out.println("\nNavigating To Sheet  ");
							System.out
									.println("_____________________________________");
							System.out.println();
						}

						if (excel
								.readRuntimeNaviFlowLine(
										Constants.naviFlowSheet[i][Constants.followNo],
										Constants.naviFlowSheet[i][Constants.followSheetName],
										Constants.naviFlowSheet[i][Constants.followTestCaseSheetName])) {

							scriptName = "";
							scriptName += Constants.naviFlowSheet[i][Constants.followNo]
									+ ". "
									+ Constants.naviFlowSheet[i][Constants.followName]
									+ "\n\t\t";
							// scriptName+=" "+Constants.naviFlowSheet[i][Constants.followSheetName]+"\n";
							Constants.nameOfScript.add(scriptName);
							scriptName = "";
							Utilities.setPause(1);

							if (excel
									.readRuntimeNavigationSheet(Constants.getFllowSheetName)) {

								Utilities.setPause(1);
								if (excel

										.readRuntimeNavigationTestCasesSheet(Constants.getFllowTestCaseSheetName)) {
									Utilities.setPause(1);

									for (int j = 1; j < Constants.naviRuntimeNaviSheetCount; j++) {
										System.out.println();
										lineParams = null;
										System.out.println();

										lineParams = excel
												.readRuntimeNaviLine(
														Constants
																.getExcelCellData(
																		"naviSheet",
																		j,
																		Constants.runTimeNaviSheetSrNo),
														Constants
																.getExcelCellData(
																		"naviSheet",
																		j,
																		Constants.runTimeNaviSheetStep),
														Constants
																.getExcelCellData(
																		"naviSheet",
																		j,
																		Constants.runTimeNaviSheetKeyword),
														Constants
																.getExcelCellData(
																		"naviSheet",
																		j,
																		Constants.runTimeNaviSheetSelector),
														Constants
																.getExcelCellData(
																		"naviSheet",
																		j,
																		Constants.runTimeNaviSheetSelectorValue),
														Constants
																.getExcelCellData(
																		"naviSheet",
																		j,
																		Constants.runTimeNaviSheetParameter),
														Constants
																.getExcelCellData(
																		"naviSheet",
																		j,
																		Constants.runTimeNaviSheetTestCase));

										System.out.println();

										Utilities.setPause(1);

										if (lineParams != null) {

											excel.executeRuntimeNaviLineToExecute(lineParams);

											if (Constants.isTestCaseNoFound) {

												if (Utilities.testCase_lineParams != null) {
													excel.executeRuntimeTestCasesExcelSheet(Utilities.testCase_lineParams);
												}
											}

										}

										if (Constants.abortIt) {
											Utilities.LOG.newLine = "\n";
											Utilities.LOG.newLine = "\n";
											Utilities.LOG
													.saveOutput("......................................");

											Utilities.LOG
													.saveOutput("Aborted : "
															+ Constants.naviRuntimeNaviSheet[i][Constants.runTimeNaviSheetStep]);
											Utilities.LOG.newLine = "\n";

											System.out
													.println("Script Aborting..........");
											break;
										}

									}

								}

							}

						}

						if (Constants.abortIt) {
							System.out.println("Script Aborted");

							Utilities.LOG
									.saveOutput("Aborted : "
											+ Constants.naviFlowSheet[i][Constants.followSheetName]);

							Utilities.LOG
									.saveOutput("......................................");
							Utilities.LOG.newLine = "\n";
							Utilities.LOG.newLine = "\n";

							break;
						} else {

							if (i != Constants.naviFlowSheetCount) {
								System.out
										.println("***********************************************");
								System.out
										.println("_____________________________________");
								System.out.println("\nNavigating To Sheet : "
										+ Constants.navigationSheetName);
								System.out
										.println("_____________________________________");
								System.out.println();
							}

						}

					}

					System.out
							.println("***********************************************");

				}

			}

		} catch (Exception e) {
			System.out.println("Exception in RUN_GENERIC_SCRIPTS");
		}

	}

}
