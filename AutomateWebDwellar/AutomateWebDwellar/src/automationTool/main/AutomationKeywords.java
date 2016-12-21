package automationTool.main;

import automationTool.excel.AdvancedExcelWriter;
import automationTool.excel.AdvancedExcelReader;
import bsh.util.Util;

public class AutomationKeywords {

	public static String keywordName = "";
	public static String selector = "";
	public static String selectorValue = "";
	public static String parameter = "";

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
			}

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
			case "clickButton":
				WebPageCommands.clickButton(selector, selectorValue, parameter);
				d = true;
				break;
			case "outFocus":
				WebPageCommands.outFocus(selector, selectorValue);
				d = true;
				break;
			case "checkText":
				WebPageCommands.checkText(selector, selectorValue, parameter);
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
				new AdvancedExcelWriter().createExcel(selector, selectorValue,
						parameter);
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
			case "readExcel":
				AdvancedExcelReader.readExcel(selector, selectorValue,
						parameter);
				d = true;
				break;
			case "loadGridTable":
				WebPageCommands.loadGridTable(selector, selectorValue,
						parameter);
				d = true;
				break;
			case "loadGridRow":
				WebPageCommands.loadGridRow(selector, selectorValue, parameter);
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
				WebPageCommands.clickGridTableItem(selector, selectorValue, parameter);
				d = true;
				break;
			case "clickGridTableActionItem":
				WebPageCommands.clickGridTableActionItem(selector, selectorValue, parameter);
				d = true;
				break;
			
				
			default:
				System.out.println(keywordName + " not found to Execute ");
				d = false;
				break;
			}

		} catch (Exception e) {
			System.out.println("EXCEPTION : While Executed Keyword "
					+ keywordName);
		}

		if (d) {
			System.out.println("PASSED : Successfully Executed Keyword "
					+ keywordName);
		} else {
			System.out.println("FAILED : Failed to  Executed Keyword "
					+ keywordName);
		}

		System.out.println();
		System.out.println("\n................................");
		return d;
	}

}
