package automationTool.main;

import automationTool.excel.AdvancedExcelWriter;
import automationTool.excel.AdvancedExcelReader;
import bsh.util.Util;

public class AutomationKeywords {

	public static String keywordName = "";
	public static String selector = "";
	public static String selectorValue = "";
	public static String parameter = "";
	public static boolean skipKeyword = false;

	public static boolean executeKeyword() {
		boolean d = false;
		try {
			System.out.println();
			System.out.println("................................\n");
			System.out.println("Executing Keyword : " + keywordName + "\n");
			Utilities.setPause(2);

			if (keywordName.equals("waitForVisible")) {
				keywordName = "waitFor";
				Utilities.waitingFor = "";
				Utilities.waitingFor = "waitForVisible";
			} else if (keywordName.equals("waitForClickable")) {
				keywordName = "waitFor";
				Utilities.waitingFor = "";
				Utilities.waitingFor = "waitForClickable";
			} else if (keywordName.equals("no Line Keyword Found")
					|| keywordName.isEmpty()) {
				skipKeyword = true;
			}

			if (!skipKeyword) {
				skipKeyword = false;

				switch (keywordName) {
				case "load":
					WebPageCommands.load(parameter);
					d = true;
					break;
				case "waitFor":
					WebPageCommands.waitFor(selector, selectorValue, parameter);
					d = true;
					break;
				case "type":
					WebPageCommands.type(selector, selectorValue, parameter);
					d = true;
					break;
				case "read":
					WebPageCommands.read(selector, selectorValue, parameter);
					d = true;
					break;
				case "clickButton":
					WebPageCommands.clickButton(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "outFocus":
					WebPageCommands.outFocus(selector, selectorValue);
					d = true;
					break;
				case "checkText":
					WebPageCommands.checkText(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "checkDeletedTagText":
					WebPageCommands.checkDeletedTagText(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "checkTextForDropDown":
					WebPageCommands.checkTextForDropDown(selector,
							selectorValue, parameter);
					d = true;
					break;
				case "checkTextInKendoIFrame":
					WebPageCommands.checkTextInKendoIFrame(selector,
							selectorValue, parameter);
					d = true;
					break;

				case "checkTextRadioButton":
					WebPageCommands.checkTextRadioButton(selector,
							selectorValue, parameter);
					d = true;
					break;

				case "addTagKendoBox":
					WebPageCommands.addTagKendoBox(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "checkTagText":
					WebPageCommands.checkTagText(selector, selectorValue,
							parameter);
					d = true;
					break;

				case "checkTextCheckBoxes":
					WebPageCommands.checkTextCheckBoxes(selector,
							selectorValue, parameter);
					d = true;
					break;
				case "saveIdFromURL":
					WebPageCommands.saveIdFromURL(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "saveIdFromPage":
					WebPageCommands.saveIdFromPage(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "checkTextKendoMultiDropDown":
					WebPageCommands.checkTextKendoMultiDropDown(selector,
							selectorValue, parameter);
					d = true;
					break;

				case "checkErrMsgText":
					WebPageCommands.checkErrMsgText(selector, selectorValue,
							parameter);
					d = true;
					break;

				case "checkURL":
					WebPageCommands.checkURL(parameter);
					d = true;
					break;

				case "checkIdFromURL":
					WebPageCommands.checkIdFromURL(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "checkContaints":
					WebPageCommands.checkContaints(selector, selectorValue,
							parameter);
					d = true;
					break;

				case "loadListTable":
					WebPageCommands.loadListTable(selector, selectorValue,
							parameter);
					d = true;
					break;

				case "loadNoteListTableItem":
					WebPageCommands.loadNoteListTableItem(selector,
							selectorValue, parameter);
					d = true;
					break;
				case "fetchNoteListTableItem":
					WebPageCommands.fetchNoteListTableItem(selector,
							selectorValue, parameter);
					d = true;
					break;
				case "checkNoteListTableItem":
					WebPageCommands.checkNoteListTableItem(selector,
							selectorValue, parameter);
					d = true;
					break;

				case "loadTaskListTableItem":
					WebPageCommands.loadTaskListTableItem(selector,
							selectorValue, parameter);
					d = true;
					break;
				case "fetchTaskListTableItem":
					WebPageCommands.fetchTaskListTableItem(selector,
							selectorValue, parameter);
					d = true;
					break;
				case "checkTaskListTableItem":
					WebPageCommands.checkTaskListTableItem(selector,
							selectorValue, parameter);
					d = true;
					break;

				case "clickAuditLink":
					WebPageCommands.clickAuditLink(selector, selectorValue,
							parameter);
					d = true;
					break;

				case "checkItemIsDisplay":
					WebPageCommands.checkItemIsDisplay(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "click":
					WebPageCommands.click(selector, selectorValue, parameter);
					d = true;
					break;
				case "clickRadioButton":
					WebPageCommands.clickRadioButton(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "clickKendoDropDown":
					WebPageCommands.clickKendoDropDown(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "scroll":
					WebPageCommands.scroll(selector, selectorValue);
					d = true;
					break;
				case "clickCheckBoxes":
					WebPageCommands.clickCheckBoxes(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "clickKendoMultiDropDown":
					WebPageCommands.clickKendoMultiDropDown(selector,
							selectorValue, parameter);
					d = true;
					break;
				case "createExcel":
					new AdvancedExcelWriter().createExcel(selector,
							selectorValue, parameter);
					d = true;
					break;
				case "clickDropDown":
					WebPageCommands.clickDropDown(selector, selectorValue,
							parameter);
					d = true;
					break;

				case "typeInKendoIFrame":
					WebPageCommands.typeInKendoIFrame(selector, selectorValue,
							parameter);
					d = true;
					break;

				case "readSwalPopUp":
					WebPageCommands.readSwalPopUp(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "checkSwalPopUpText":
					WebPageCommands.checkSwalPopUpText(selector, selectorValue,
							parameter);
					d = true;
					break;

				case "readExcel":
					AdvancedExcelReader.readExcel(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "reReadExcel":
					AdvancedExcelReader.reReadExcel(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "loadGridTable":
					WebPageCommands.loadGridTable(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "loadGridRow":
					WebPageCommands.loadGridRow(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "loadGridRowSPANS":
					WebPageCommands.loadGridRowSPANS(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "checkRowValue":
					WebPageCommands.checkRowValue(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "createDate":
					Utilities.createDate(selector, selectorValue, parameter);
					d = true;
					break;
				case "calculateAge":
					Utilities.calculateAge(selectorValue, parameter);
					d = true;
					break;

				case "clickGridTableItem":
					WebPageCommands.clickGridTableItem(selector, selectorValue,
							parameter);
					d = true;
					break;
				case "clickGridTableActionItem":
					WebPageCommands.clickGridTableActionItem(selector,
							selectorValue, parameter);
					d = true;
					break;
				case "backToPage":
					WebPageCommands.back();
					d = true;
					break;
				case "takeSnap":
					WebPageCommands.takeScreenShot(parameter);
					d = true;
					break;
				default:
					System.out.println(keywordName + " not found to Execute ");
					d = false;
					break;
				}

			} else {
				System.out.println("SKIPPED : Keyword not Found");

			}

		} catch (Exception e) {
			System.out.println("EXCEPTION : While Executed Keyword "
					+ keywordName);
		}

		if (!skipKeyword) {
			if (d) {
				System.out.println("PASSED : Successfully Executed Keyword "
						+ keywordName);
			} else {
				System.out.println("FAILED : Failed to  Executed Keyword "
						+ keywordName);
			}
		}

		System.out.println();
		System.out.println("\n................................");
		return d;
	}

}
