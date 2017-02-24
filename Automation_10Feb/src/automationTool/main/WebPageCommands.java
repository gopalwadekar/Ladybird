package automationTool.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jnr.ffi.Struct.id_t;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.python.antlr.ast.Str;

import bsh.util.Util;

public class WebPageCommands {
	

	public static void load(String url) {
		boolean d = false, isInPge = false;
		try {
			// url="Base : /login";
			
			if (url.equalsIgnoreCase("reload")) {
				
				String crntUrl = Utilities.getCurrentURL();
				
				if (!crntUrl.isEmpty()) {
					Utilities.driver.navigate().refresh();
					System.out.println("Page Refreshing Page....");
					Utilities.setPause(2);
					if (Utilities.isPageLoaded(crntUrl)) {
						System.out.println("Page is Refresh");
						d = true;
					}
					else{
						System.out.println("Page is not Refresh");
					}
				}
				
				
			}
			else{
				
				if (url.contains("Base") || url.contains("base")) {

					url = Constants.rootBaseUrl + url.split(":")[1].trim();
				}

				if (Utilities.getCurrentURL().equals(url)) {
					isInPge = true;

				}
				if (isInPge) {
					d = true;
					System.out.println("Alreadly in URL");
				} else {
					Utilities.driver.get(url);
					Utilities.setPause(2);
					if (Utilities.isPageLoaded(url)) {
						d = true;
					}
				}
			}
			

		} catch (Exception e) {
			System.out.println("EXCEPTION : load");
		}

		if (d) {
			System.out.println("PASSED : Url is set sucessfully");
		} else {
			System.out.println("FAILED : Failed to set Url");
		}

	}

	public static void back() {
		boolean d = false;
		try {

			String url = Utilities.backClick();

			if (Utilities.isPageLoaded(url)) {
				d = true;
			}

		} catch (Exception e) {
			System.out.println("EXCEPTION : back");
		}

		if (d) {
			System.out.println("PASSED : back is clicked");
		} else {
			System.out.println("FAILED : back is clicked");
		}

	}

	public static boolean waitFor(String selector, String selectorValue,
			String parameter) {
		boolean d = false;
		int secs = 0;
		try {
			secs = Integer.parseInt(parameter);

			System.out.println("Waiting for " + selectorValue + " " + secs
					+ " secs");
			System.out.println(".....................................");
			System.out.print("Finding webElement " + selectorValue + " .");
			for (int i = 1; i < secs; i++) {
				System.out.print(".");
				if (waitElement(selector, selectorValue, i)) {
					System.out.println("\nWebElement " + selector
							+ "  found in " + i + " secs.");
					d = true;
					break;
				} else {
					continue;
				}

			}

			System.out.println();
		} catch (Exception e) {
			System.out.println("EXCEPTION : waitFor");
		}

		if (d) {
			System.out.println("PASSED : " + selectorValue
					+ " is waited set sucessfully");
		} else {
			System.out.println("FAILED : Failed to  wait " + selectorValue);
		}

		return d;
	}

	public static boolean waitElement(String eleBy, String eleByVal, int sec)
			throws AutomationException {
		boolean d = false;
		WebElement ele = null;
		try {
			Utilities.wait = null;
			Utilities.wait = new WebDriverWait(Utilities.driver, sec);
			if (Utilities.wait != null) {
				ele = Utilities.getWebElementForWait(eleBy, eleByVal);
				if (ele != null) {

					d = true;
				}

			}

		} catch (Exception e) {
			// throw new AutomationException(e, eleBy + "=" + eleByVal);

		}

		return d;

	}

	public static boolean type(String selector, String selectorValue,
			String parameter) {
		boolean d = false;
		WebElement ele = null;
		String data = "";

		try {

			// parameter="READ : addNewTaskName";
			parameter = Utilities.isParameterAuto(parameter);

			ele = Utilities.getWebElement(selector, selectorValue);

			if (ele != null) {
				ele.click();
				ele.clear();
				ele.sendKeys(parameter);
				data = parameter;
				Thread.sleep(300);

				d = true;

			}
			Utilities.toSave(Utilities.saveExel, new String[] { selectorValue,
					data });

		} catch (Exception e) {
			System.out.println("EXCEPTION : type");
		}

		if (d) {
			System.out.println("PASSED : " + parameter + " is filled in "
					+ selectorValue);
		} else {
			System.out.println("FAILED : " + parameter + " is not filled in "
					+ selectorValue);
		}

		return d;
	}

	public static boolean read(String selector, String selectorValue,
			String parameter) {
		boolean d = false;
		WebElement ele = null;
		String data = "";
		//parameter="four"; 
		//parameter = "code:,1";
		try {
			//parameter=":, task id";
			parameter = Utilities.isParameterAuto(parameter);

			if (!selector.isEmpty()) {

				ele = Utilities.getWebElement(selector, selectorValue);

				if (ele != null) {

					data = ele.getText();

					if (data.isEmpty()) {
						data = ele.getAttribute("value");
					}
					Thread.sleep(300);

					if (!parameter.isEmpty()) {

						// String splitBy = Utilities.splitTextBy(parameter,
						// ":")[1];
						//if (!splitBy.isEmpty()) {

						String p1 = Utilities.splitTextBy(parameter, ",")[0];
						String p2 = Utilities.splitTextBy(parameter, ",")[1];
						System.out.println("Spliting Text by " + p1);

						String splits[] = Utilities.splitTextBy(data, p1);
						if (splits != null & splits.length > 0) {
							if(data.contains("Task Id")){
								data=splits[1];}
							else{
							data = splits[Integer.parseInt(p2)];
							data = data.replaceAll("^\\s+", "");// left
							data = data.replaceAll("\\s+$", "");// right 
							}
						}

						// }

					}

					d = true;

				}

			} else {
				System.out.println();
				data = parameter;
			}

			Utilities.toSave(Utilities.saveExel, new String[] { selectorValue,
					data });

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("EXCEPTION : type");
		}

		if (d) {
			System.out.println("PASSED : " + parameter + " is read in "
					+ selectorValue);
		} else {
			System.out.println("FAILED : " + parameter + " is not read in "
					+ selectorValue);
		}

		return d;
	}

	public static boolean clickButton(String selector, String selectorValue,
			String parameter) {
		boolean d = false, isWaited = false;
		WebElement ele = null;
		try {
			Constants.abortIt = false;
			// int sec = 0;
			if (!parameter.isEmpty()) {
				// sec = Integer.parseInt(parameter);
				// Utilities.wait = null;
				// Utilities.wait = new WebDriverWait(Utilities.driver, sec);
				Utilities.waitingFor = null;
				Utilities.waitingFor = "waitForClickable";
				isWaited = waitFor(selector, selectorValue, parameter);
			} else {
				isWaited = true;
			}

			if (isWaited) {
				ele = Utilities.getWebElement(selector, selectorValue);

				if (ele != null) {

					ele.click();

					Thread.sleep(300);

					d = true;

				}
			}

		} catch (Exception e) {
			System.out.println("EXCEPTION : fillTextBox");
		}

		if (d) {
			System.out.println("PASSED : " + selectorValue + " is clicked ");
		} else {
			Constants.abortIt = true;
			System.out.println("FAILED : " + selectorValue + " is clicked ");

		}

		return d;
	}

	public static boolean click(String selector, String selectorValue,
			String parameter) {
		boolean d = false, isWaited = false;
		WebElement ele = null;
		try {

			if (!parameter.isEmpty()) {
				Utilities.waitingFor = null;
				Utilities.waitingFor = "waitForClickable";
				isWaited = waitFor(selector, selectorValue, parameter);
			} else {
				isWaited = true;
			}
			if (!parameter.isEmpty()) {

			}

			if (isWaited) {
				ele = Utilities.getWebElement(selector, selectorValue);

				if (ele != null) {

					ele.click();

					Thread.sleep(300);

					d = true;

				}

			}

		} catch (Exception e) {
			System.out.println("EXCEPTION : click");
		}

		if (d) {
			System.out.println("PASSED : " + selectorValue + " is clicked ");
		} else {

			System.out.println("FAILED : " + selectorValue + " is clicked ");

		}

		return d;
	}

	public static boolean addTagKendoBox(String selector, String selectorValue,
			String parameter) {
		boolean d = false, isWaited = false, isAppear;
		String sec1 = "5", sec2 = "5", data = "";
		String selctr1 = "", selctr2 = "", selctrVal1 = "", selctrVal2 = "";
		WebElement ele = null, waitForList = null;
		try {
			//parameter="SOLD";
			parameter = Utilities.isParameterAuto(parameter);
			String selectors[] = Utilities.splitTextBy(selector, ",");

			if (selectors != null) {
				if (!selectors[0].isEmpty()) {
					selctr1 = selectors[0];
				}

				if (!selectors[1].isEmpty()) {
					selctr2 = selectors[1];
				}
			}

			String selectorValues[] = Utilities.splitTextBy(selectorValue, ",");

			if (selectorValues != null) {

				if (!selectorValues[0].isEmpty()) {
					selctrVal1 = selectorValues[0];
				}

				if (!selectorValues[1].isEmpty()) {
					// sec1 = Integer.parseInt(selectorValues[1]);
					sec1 = selectorValues[1];
				}

				if (!selectorValues[2].isEmpty()) {
					selctrVal2 = selectorValues[2];
				}

				if (!selectorValues[3].isEmpty()) {
					// sec2 = Integer.parseInt(selectorValues[1]);
					sec2 = selectorValues[3];
				}
			}

			if (click(selctr1, selctrVal1, sec1)) {

				Utilities.waitingFor = "";
				Utilities.waitingFor = "waitForVisible";
				if (waitFor(selctr2, selctrVal2, sec2)) {

					ele = Utilities.getWebElement("xpath",
							"//li[contains(text(),'" + parameter + "')]");

					if (ele != null) {
						data = ele.getText();

						if (data.isEmpty()) {
							data = ele.getAttribute("value");
						}

						if (!data.isEmpty()) {
							ele.click();
							d = true;
						}

					}

				}

			}

			Utilities.toSave(Utilities.saveExel, new String[] { "tag", data });

		} catch (Exception e) {
			System.out.println("EXCEPTION : addTagKendoBox");
		}

		if (d) {
			System.out.println("PASSED : " + parameter + " is clicked ");
		} else {

			System.out.println("FAILED : " + parameter + " is clicked ");

		}

		return d;
	}

	public static boolean outFocus(String selector, String selectorValue) {
		boolean d = false;
		WebElement ele = null;
		try {

			ele = Utilities.getWebElement(selector, selectorValue);

			if (ele != null) {
				ele.click();
				Thread.sleep(300);

				d = true;
			}

		} catch (Exception e) {
			System.out.println("EXCEPTION : outFocus");
		}

		if (d) {
			System.out.println("PASSED : " + selectorValue + " is onFocus ");

		} else {
			System.out
					.println("FAILED : " + selectorValue + " is not onFocus ");
		}

		return d;
	}

	public static boolean checkText(String selector, String selectorValue,
			String parameter) {
		boolean d = false, isTextFound = false, check = false;
		WebElement ele = null;
		String eleData = "";
		try {

			// selectorValue="leadDsgn";
			// parameter="READ : Sources";
			parameter = Utilities.isParameterAuto(parameter);

			ele = Utilities.getWebElement(selector, selectorValue);

			if (ele != null) {

				eleData = ele.getText();

				if (eleData.isEmpty()) {

					eleData = ele.getAttribute("value");
				}

				if (!eleData.isEmpty()) {
					isTextFound = true;
				}
				d = true;
			}

		} catch (Exception e) {

			System.out.println("EXCEPTION : checkText");
		}

		if (!isTextFound) {
			eleData = " no text found in " + selectorValue;
			isTextFound = true;
		}

		if (isTextFound) {

			// parameter=Utilities.removeLRSpace(parameter);
			parameter = parameter.replaceAll("^\\s+", "").replaceAll("\\s+$",
					"");

			if (parameter.contains(",") || eleData.contains(",")) {
				eleData = eleData.replace(",", "").trim();
				parameter = parameter.replace(",", "").trim();
				check = eleData.equalsIgnoreCase(parameter);
				if (!check) {
				//	int x = eleData.compareTo(parameter);
					boolean x = eleData.equalsIgnoreCase(parameter);

					if (x) {
						check = true;
					}
				}

			} else {
				check = eleData.equalsIgnoreCase(parameter);

				if (!check) {
					check = eleData.trim().contains(parameter.trim());
				}

				if (!check) {
					check = eleData.trim().matches(parameter.trim());
				}

				if (!check) {
					if (eleData.startsWith("Task created")) {

						eleData = Utilities.splitTextBy(eleData, " with task")[0];// eleData.split(eleData,"Task created successfully")[0];
						boolean x = (eleData.trim()).equalsIgnoreCase(parameter.trim());
						if (x) {
							check = true;
						}

					} else if (eleData.contains("/")) {
						if (!check) {
							//int x = eleData.compareTo(parameter);
							int x = eleData.compareTo(parameter);

							if (x <= 0) {
								check = true;
							}
						}
					}

				}
			}

			if (check) {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "PASSED";
			} else {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "FAILED";
				d=false;
			}

			Constants.testResults[2] = parameter;

		}

		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : " + parameter + " (is equal To)  "
					+ eleData);
		} else {
			System.out.println("FAILED : " + parameter + " (is not equal To)  "
					+ eleData);
		}
		System.out
				.println("------------------------------------------------------");

		return d;
	}

	public static boolean checkTagText(String selector, String selectorValue,
			String parameter) {
		boolean d = false, isTextFound = false, check = false;
		WebElement ele = null;
		String eleData = "";
		try {

			// selectorValue="leadDsgn";
			// parameter="READ : Sources";
			parameter = Utilities.isParameterAuto(parameter);
			selectorValue = Utilities.isParameterAuto(selectorValue);
			ele = Utilities.getWebElement(selector, selectorValue);

			if (ele != null) {

				eleData = ele.getText();

				if (eleData.isEmpty()) {

					eleData = ele.getAttribute("value");
				}

				if (!eleData.isEmpty()) {
					isTextFound = true;
				}
				d = true;
			}

		} catch (Exception e) {

			System.out.println("EXCEPTION : checkText");
		}

		if (!isTextFound) {
			eleData = " no text found in " + selectorValue;
			isTextFound = true;
		}

		if (isTextFound) {

			// parameter=Utilities.removeLRSpace(parameter);
			parameter = parameter.replaceAll("^\\s+", "").replaceAll("\\s+$",
					"");

			if (parameter.contains(",") || eleData.contains(",")) {
				eleData = eleData.replace(",", "").trim();
				parameter = parameter.replace(",", "").trim();
				check = eleData.equalsIgnoreCase(parameter);
				if (!check) {
					int x = eleData.compareTo(parameter);

					if (x <= 0) {
						check = true;
					}
				}

			} else {
				check = eleData.equalsIgnoreCase(parameter);

				if (!check) {
					check = eleData.trim().contains(parameter.trim());
				}

				if (!check) {
					check = eleData.trim().matches(parameter.trim());
				}

				if (!check) {
					if (eleData.startsWith("Task created")) {

						eleData = Utilities.splitTextBy(eleData, " with task")[0];// eleData.split(eleData,"Task created successfully")[0];
						int x = eleData.trim().compareTo(parameter.trim());
						if (x > 0) {
							check = true;
						}

					} else if (eleData.contains("/")) {
						if (!check) {
							int x = eleData.compareTo(parameter);

							if (x <= 0) {
								check = true;
							}
						}
					}

				}
			}

			if (check) {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "PASSED";
			} else {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "FAILED";
			}

			Constants.testResults[2] = parameter;

		}

		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : " + parameter + " (is equal To)  "
					+ eleData);
		} else {
			System.out.println("FAILED : " + parameter + " (is not equal To)  "
					+ eleData);
		}
		System.out
				.println("------------------------------------------------------");

		return d;
	}

	
	public static boolean checkDeletedTagText(String selector, String selectorValue,
			String parameter) {
		boolean d = false, isDeleted = false, check = false;
		WebElement ele = null;
		String eleData = "";
		try {

			// selectorValue="leadDsgn";
			// parameter="No tag Found";
			parameter = Utilities.isParameterAuto(parameter);
			selectorValue = Utilities.isParameterAuto(selectorValue);
			ele = Utilities.getWebElement(selector, selectorValue);

			if (ele != null) {

				eleData = ele.getText();

				if (eleData.isEmpty()) {

					eleData = ele.getAttribute("value");
				}

				if (!eleData.isEmpty()) {
					isDeleted = false;
				}
				
			}
			else{
				isDeleted = true;
				d = true;
			}

		} catch (Exception e) {

			System.out.println("EXCEPTION : checkText");
		}

		if (isDeleted) {
			eleData = "No tag Found";// + selectorValue;
			//isTextFound = true;
		}

		if (isDeleted) {
			check = eleData.equalsIgnoreCase(parameter);
			
			} else {
				check=false;
			}

			if (check) {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "PASSED";
			} else {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "FAILED";
			}

			Constants.testResults[2] = parameter;

		

		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : " + parameter + " (is equal To)  "
					+ eleData);
		} else {
			System.out.println("FAILED : " + parameter + " (is not equal To)  "
					+ eleData);
		}
		System.out
				.println("------------------------------------------------------");

		return d;
	}

	
	public static boolean saveIdFromURL(String selector, String selectorValue,
			String parameter) {
		boolean d = false;
		try {
			String id = "";
			parameter = Utilities.isParameterAuto(parameter);

			if (!selector.isEmpty()) {
				System.out.println("Spliting After : " + selector);

				String currentUrl = Utilities.getCurrentURL();

				if (!currentUrl.isEmpty()) {
					String data[] = currentUrl.split("id=");

					if (data != null && data.length > 0) {
						id = data[1];

						if (selectorValue.equalsIgnoreCase("jar")) {
							Constants.userID = "";
							Constants.userID = id;// data[1];

							System.out.println("Saved in JAR");

						} else {
							System.out.println("Saved in EXCEL");
							Utilities.toSave(Utilities.saveExel, new String[] {
									selectorValue, id });
						}

						d = true;
					}
				}

			}

			// if (selectorValue.equalsIgnoreCase("jar")) {
			// Constants.userID = "";
			// Constants.userID = parameter;
			//
			// d = true;
			// } else {
			//
			//
			// }

		} catch (Exception e) {

			System.out.println("EXCEPTION : saveIdFromURL");
		}

		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : UserId " + Constants.userID);

		} else {
			System.out.println("FAILED : UserId " + Constants.userID);
		}
		System.out
				.println("------------------------------------------------------");

		return d;
	}

	public static boolean saveIdFromPage(String selector, String selectorValue,
			String parameter) {
		boolean d = false;
		WebElement ele = null;
		String data = "";

		try {
			// parameter="split -Task Id: ";
			parameter = Utilities.isParameterAuto(parameter);

			if (!parameter.isEmpty() && !parameter.startsWith("split -")) {

				data = parameter;
				Thread.sleep(300);

			} else {
				ele = Utilities.getWebElement(selector, selectorValue);

				if (ele != null) {
					data = ele.getText();
					if (data.isEmpty()) {
						data = ele.getAttribute("value");
					}
					Thread.sleep(300);

				}

				if (!data.isEmpty()) {

					if (!parameter.isEmpty()) {

						parameter = parameter.split("split -")[1];

						if (!parameter.isEmpty()) {
							data = Utilities.splitTextBy(data, parameter)[1];
							if (!data.isEmpty()) {
								d = true;
							}
						}

					}

					// String splitData = Utilities.splitTextBy(data, by)

					d = true;
				}

			}

			Utilities.toSave(Utilities.saveExel, new String[] { selectorValue,
					data });

		} catch (Exception e) {
			System.out.println("EXCEPTION : saveIdFromPage");
		}

		if (!parameter.isEmpty()) {
			if (d) {
				System.out.println("PASSED : Id Found in " + parameter);
			} else {
				System.out.println("FAILED : Id not Found in " + parameter);

			}
		} else {

			if (d) {
				System.out.println("PASSED : Id Found in " + selectorValue);
			} else {
				System.out.println("FAILED : Id not Found in " + selectorValue);
			}
		}

		return d;

	}

	public static boolean checkIdFromURL(String selector, String selectorValue,
			String parameter) {
		boolean d = false, isIdFound = false, isIdUrlFound = false, check = false;
		String pageId = "", parId = "";
		try {

			// parameter="userId";
			// parameter="READ : leadId";
			parameter = Utilities.isParameterAuto(parameter);

			if (!parameter.isEmpty()) {
				parId = parameter;
				isIdFound = true;
			}
			if (!selector.isEmpty()) {
				System.out.println("Spliting After : " + selector);
				String currentUrl = Utilities.getCurrentURL();
				pageId = Utilities.getIdfromURL(currentUrl, selector, 1);
				if (!pageId.isEmpty()) {
					isIdUrlFound = true;
				}

			}

		} catch (Exception e) {
			System.out.println("Exeption "+e);
			System.out.println("EXCEPTION : checkIdFromURL");
		}

		if (!isIdUrlFound) {
			Utilities.ignoreAlertPop();
		}
		if (!isIdFound) {

		} else {
			check = parameter.equalsIgnoreCase(pageId);
			d = true;
			System.out.println();
		}

	
		if (check) {
			Constants.testResults[0] = pageId;
			Constants.testResults[1] = "PASSED";
		} else {
			Constants.testResults[0] = pageId;
			Constants.testResults[1] = "FAILED";
			d=false;
		}

		Constants.testResults[2] = pageId;

		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : " + parameter + " (is equal To)  "
					+ pageId);
		} else {
			System.out.println("FAILED : " + parameter + " (is not equal To)  "
					+ pageId);
		}
		System.out
				.println("------------------------------------------------------");

		return d;
	}

	public static boolean checkContaints(String selector, String selectorValue,
			String parameter) {
		boolean d = false, isIdFound = false, isIdUrlFound = false, check = false;
		String pageId = "", parId = "";
		WebElement ele = null;
		try {

			// parameter="userId";
			// parameter="READ : leadId";
			parameter = Utilities.isParameterAuto(parameter);

			if (!parameter.isEmpty()) {
				parId = parameter;
				isIdFound = true;
			}
			if (!selector.isEmpty()) {
				ele = Utilities.getWebElement(selector, selectorValue);
				
				if (!(ele == null)) {
					isIdUrlFound = true;
				}

			}

		} catch (Exception e) {
			System.out.println("Exeption "+e);
			System.out.println("EXCEPTION : checkIdFromURL");
		}

		if (!isIdUrlFound) {
			Utilities.ignoreAlertPop();
		}
		if (!isIdFound) {

		} else {
			check = ele.getText().contains(parameter);
			d = true;
			System.out.println();
		}

	
		if (check) {
			Constants.testResults[0] = pageId;
			Constants.testResults[1] = "PASSED";
		} else {
			Constants.testResults[0] = pageId;
			Constants.testResults[1] = "FAILED";
			d=false;
		}

		Constants.testResults[2] = pageId;

		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : " + ele.getText() + " contains  "
					+ parameter);
		} else {
			System.out.println("FAILED : " + ele.getText() + " not contains  "
					+ parameter);
		}
		System.out
				.println("------------------------------------------------------");

		return d;
	}
	
	public static void takeScreenShot(String imagename) {
		File scrFile = ((TakesScreenshot) Utilities.driver)
				.getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		// imagename="READ : addNewTaskName";
		imagename = Utilities.isParameterAuto(imagename);

		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")
					+ "\\Screenshorts\\" + imagename + ".jpg"));
		} catch (IOException e) {
			System.out.println("Not able to take a screen short");
		}
	}

	public static boolean getUserId(String selector, String selectorValue,
			String parameter) {
		boolean d = false;
		try {
			Constants.userID = "";
			parameter = Utilities.isParameterAuto(parameter);

			if (!parameter.isEmpty()) {

				Constants.userID = parameter;
				d = true;
			} else {
				if (!selector.isEmpty()) {
					System.out.println("Spliting After : " + selector);

					String currentUrl = Utilities.getCurrentURL();

					if (!currentUrl.isEmpty()) {
						String data[] = currentUrl.split("id=");

						if (data != null && data.length > 0) {
							Constants.userID = data[1];
							d = true;
						}
					}

				}

			}

		} catch (Exception e) {

			System.out.println("EXCEPTION : setUserId");
		}

		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : UserId " + Constants.userID);

			Utilities.LOG.saveOutputInBox(Constants.userID);

		} else {
			System.out.println("FAILED : UserId " + Constants.userID);
		}
		System.out
				.println("------------------------------------------------------");

		return d;
	}

	public static boolean checkTextKendoMultiDropDown(String selector,
			String selectorValue, String parameter) {
		boolean d = false, isTextFound = false, check = false;
		WebElement ele = null;
		String eleData = "";
		try {

			// selectorValue="leadDsgn";
			// parameter="READ : Sources";
			parameter = Utilities.isParameterAuto(parameter);

			ele = Utilities.getWebElement(selector, selectorValue);

			if (ele != null) {

				eleData = ele.getText();

				if (!eleData.isEmpty()) {

					eleData = eleData.replaceAll("\ndelete", ",");
					eleData = eleData.replaceAll("\n", "");
					eleData = eleData.replaceAll(" ", "");
				}

				if (!eleData.isEmpty()) {
					isTextFound = true;
				}
				d = true;
			}

		} catch (Exception e) {

			System.out.println("EXCEPTION : checkText");
		}

		if (!isTextFound) {
			eleData = " no text found in " + selectorValue;
			isTextFound = true;
		}

		if (isTextFound) {

			// parameter=Utilities.removeLRSpace(parameter);
			parameter = parameter.replaceAll("^\\s+", "").replaceAll("\\s+$",
					"");

			if (parameter.contains(",") || eleData.contains(",")) {
				eleData = eleData.replace(",", "").trim();
				parameter = parameter.replace(",", "").trim();
				check = eleData.equalsIgnoreCase(parameter);
				if (!check) {
					int x = eleData.compareTo(parameter);

					if (x <= 0) {
						check = true;
					}
				}

			} else {
				check = eleData.equalsIgnoreCase(parameter);

				if (!check) {
					check = eleData.trim().equalsIgnoreCase(parameter.trim());
				}
			}

			if (check) {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "PASSED";
			} else {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "FAILED";
			}

			Constants.testResults[2] = parameter;

		}

		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : " + parameter + " (is equal To)  "
					+ eleData);
		} else {
			System.out.println("FAILED : " + parameter + " (is not equal To)  "
					+ eleData);
		}
		System.out
				.println("------------------------------------------------------");

		return d;
	}

	public static boolean checkTextForDropDown(String selector,
			String selectorValue, String parameter) {
		boolean d = false, isTextFound = false, check = false;
		WebElement ele = null;
		String eleData = "";
		try {

			// selectorValue="leadDsgn";
			// parameter = "MvNZX";
			parameter = Utilities.isParameterAuto(parameter);
			parameter = parameter.replaceAll("\\s", "");// .replaceAll("\\S",
														// "");
			ele = Utilities.getWebElement(selector, selectorValue);

			if (ele != null) {

				org.openqa.selenium.support.ui.Select se = new org.openqa.selenium.support.ui.Select(
						ele);
				WebElement s = se.getFirstSelectedOption();
				if (s != null) {
					eleData = s.getText().replaceAll("\\s", "");
				}

				if (!eleData.isEmpty()) {
					isTextFound = true;
				}
				d = true;
			}

		} catch (Exception e) {

			System.out.println("EXCEPTION : checkText");
		}

		if (!isTextFound) {
			eleData = " no text found in " + selectorValue;
			isTextFound = true;
		}

		if (isTextFound) {

			check = eleData.equalsIgnoreCase(parameter);

			// if (!check) {
			// eleData.contains(parameter.trim());
			// }

			// parameter=Utilities.removeLRSpace(parameter);
			// parameter = parameter.replaceAll("^\\s+", "").replaceAll("\\s+$",
			// "");

			// if (parameter.contains(",") || eleData.contains(",")) {
			// eleData = eleData.replace(",", "").trim();
			// parameter = parameter.replace(",", "").trim();
			// check = eleData.equalsIgnoreCase(parameter);
			// if (!check) {
			// int x = eleData.compareTo(parameter);
			//
			// if (x <= 0) {
			// check = true;
			// }
			// }
			//
			// } else {
			//
			//
			// // if (!check) {
			// // check = eleData.trim().equalsIgnoreCase(parameter.trim());
			// // }
			// }

			if (check) {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "PASSED";
			} else {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "FAILED";
			}

			Constants.testResults[2] = parameter;

		}

		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : " + parameter + " (is equal To)  "
					+ eleData);
		} else {
			System.out.println("FAILED : " + parameter + " (is not equal To)  "
					+ eleData);
		}
		System.out
				.println("------------------------------------------------------");

		return d;
	}

	public static boolean checkTextInKendoIFrame(String selector,
			String selectorValue, String parameter) {
		boolean d = false, isTextFound = false, check = false;
		String eleData = "";
		try {
			parameter = Utilities.isParameterAuto(parameter);

			WebElement ele = Utilities.getKendoIFrameBody(selector,
					selectorValue);
			if (ele != null) {
				Thread.sleep(1000);

				eleData = ele.getText();

				if (!eleData.isEmpty()) {
					isTextFound = true;
				}

				Thread.sleep(500);
				Utilities.driver.switchTo().defaultContent();
				d = true;
			}
		} catch (Exception e) {
			System.out.println("Exception in checkTextInKendoIFrame");
			d = false;
		}

		if (!isTextFound) {
			eleData = " no text found in " + selectorValue;
			isTextFound = true;
		}

		if (isTextFound) {

			// parameter=Utilities.removeLRSpace(parameter);
			parameter = parameter.replaceAll("^\\s+", "").replaceAll("\\s+$",
					"");

			if (parameter.contains(",") || eleData.contains(",")) {
				eleData = eleData.replace(",", "").trim();
				parameter = parameter.replace(",", "").trim();
				check = eleData.equalsIgnoreCase(parameter);
				if (!check) {
					int x = eleData.compareTo(parameter);

					if (x <= 0) {
						check = true;
					}
				}

			} else {
				check = eleData.equalsIgnoreCase(parameter);

				if (!check) {
					check = eleData.trim().equalsIgnoreCase(parameter.trim());
				}
			}

			if (check) {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "PASSED";
			} else {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "FAILED";
				d=false;
			}

			Constants.testResults[2] = parameter;

		}

		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : " + parameter + " (is equal To)  "
					+ eleData);
		} else {
			System.out.println("FAILED : " + parameter + " (is not equal To)  "
					+ eleData);
		}
		System.out
				.println("------------------------------------------------------");

		return d;

	}

	public static boolean checkTextRadioButton(String selector,
			String selectorValue, String parameter) {
		String eleData = "", rbtn = "";
		boolean d = false, isTextFound = false, check = false;
		String isChecked = "";
		try {
			WebElement ele = null;
			List<WebElement> eles = null;

			ele = Utilities.getWebElement(selector, selectorValue);

			if (ele != null) {

				eles = ele.findElements(By.tagName("md-radio-button"));
				if (eles.size() > 0) {

					for (int i = 0; i < eles.size(); i++) {

						rbtn = eles.get(i).getText().toString();
						rbtn = rbtn.toLowerCase().trim().toString();
						parameter = parameter.toLowerCase().trim().toString();
						// parameter ="Channel Partner";
						if (rbtn.equalsIgnoreCase(parameter)) {

							isChecked = eles.get(i)
									.getAttribute("aria-checked");

							if (isChecked != null) {

								if (!isChecked.isEmpty()) {
									if (isChecked.equals("true")) {
										eles.get(i).click();
										eleData = eles.get(i).getText()
												.toString();
										if (!eleData.isEmpty()) {
											isTextFound = true;
											d = true;
											break;
										}

									}
								} else {

								}

							}

						}

					}

				}

			}

		} catch (Exception e) {
			System.out.println("Exception in checkTextRadioButton ");
		}

		if (!isTextFound) {
			eleData = " no text found in " + selectorValue;
			isTextFound = true;
		}

		if (isTextFound) {
			check = eleData.equalsIgnoreCase(parameter);
			// // parameter=Utilities.removeLRSpace(parameter);
			// parameter = parameter.replaceAll("^\\s+", "").replaceAll("\\s+$",
			// "");
			//
			// if (parameter.contains(",") || eleData.contains(",")) {
			// eleData = eleData.replace(",", "").trim();
			// parameter = parameter.replace(",", "").trim();
			// check = eleData.equalsIgnoreCase(parameter);
			// if (!check) {
			// int x = eleData.compareTo(parameter);
			//
			// if (x <= 0) {
			// check = true;
			// }
			// }
			//
			// } else {
			// check = eleData.equalsIgnoreCase(parameter);
			//
			// if (!check) {
			// check = eleData.trim().equalsIgnoreCase(parameter.trim());
			// }
			// }

			if (check) {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "PASSED";
			} else {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "FAILED";
			}

			Constants.testResults[2] = parameter;

		}
		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : " + parameter + " (is equal To)  "
					+ eleData);
		} else {
			System.out.println("FAILED : " + parameter + " (is not equal To)  "
					+ eleData);
		}
		System.out
				.println("------------------------------------------------------");
		return d;
	}

	public static boolean checkTextCheckBoxes(String selector,
			String selectorValue, String parameter) {
		boolean d = false, isTextFound = false, check = false;
		String eleData = "", isChecked = "";

		WebElement ele = null;
		try {

			parameter=  Utilities.isParameterAuto(parameter);
			
			ele = Utilities.getWebElement(selector, selectorValue);
			if (ele != null) {

				List<WebElement> eles = ele.findElements(By
						.tagName("md-checkbox"));
				if (eles.size() > 0) {

					for (int i = 0; i < eles.size(); i++) {

						isChecked = eles.get(i).getAttribute("aria-checked");

						if (isChecked != null) {
							if (!isChecked.isEmpty()) {
								if (isChecked.equals("true")) {
									Thread.sleep(200);
									eleData += eles.get(i).getText().toString()
											+ ",";
									Thread.sleep(200);
									// break;
								}
							}
						}

					}

					if (!eleData.isEmpty()) {
						isTextFound = true;
						d = true;
					}

				}
			}

		} catch (Exception e) {
			System.out.println("Exception in checkTextCheckBoxes ");
		}

		if (!isTextFound) {
			eleData = " no text found in " + selectorValue;
			isTextFound = true;
		}

		if (isTextFound) {
			// parameter=Utilities.removeLRSpace(parameter);

			check = eleData.equalsIgnoreCase(parameter);
			if (check) {
				
				check=true;
			}
			else{
			String	parameter2 = parameter.replaceAll("^\\s+", "").replaceAll("\\s+$",
						"").replaceAll(",", "").replaceAll(" ", "");
				
			String	eleData2 = eleData.replaceAll("^\\s+", "").replaceAll("\\s+$",
						"").replaceAll(",", "").replaceAll(" ", "");
				
				if (parameter2.contains(",") && eleData2.contains(",")) {
					eleData2 = eleData.replace(",", "").trim();
					parameter2 = parameter.replace(",", "").trim();
					check = eleData2.equalsIgnoreCase(parameter2);
					if (!check) {
						int x = eleData2.compareTo(parameter2);

						if (x <= 0) {
							check = true;
						}
					}

				}
				else{
					
					check = eleData2.equalsIgnoreCase(parameter2);
					if (check) {
						
						check=true;
					}
				}
			}
			
			

//			 else {
//				check = eleData.equalsIgnoreCase(parameter);
//
//				if (!check) {
//					check = eleData.trim().equalsIgnoreCase(parameter.trim());
//				}
//			}

			if (check) {
				d=true;
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "PASSED";
			} else {
				d=false;
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "FAILED";
			}

			Constants.testResults[2] = parameter;
		}

		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : " + parameter + " (is equal To)  "
					+ eleData);
		} else {
			System.out.println("FAILED : " + parameter + " (is not equal To)  "
					+ eleData);
		}
		System.out
				.println("------------------------------------------------------");

		return d;
	}

	public static boolean checkURL(String parameter) {
		boolean d = false, isURLFound = false;
		String url = "";
		try {

			if (parameter.contains("Base") || parameter.contains("base")) {

				parameter = Constants.rootBaseUrl + url.split(":")[1].trim();
			}

			Constants.abortIt = false;
			while (!isURLFound) {
				url = Utilities.getCurrentURL();
				if (!url.isEmpty()) {

					if (url.equals("Some Exception Found")) {

						isURLFound = false;
					} else {
						isURLFound = true;

					}

				}

			}

		} catch (Exception e) {

			System.out.println("EXCEPTION : checkURL");
		}

		if (isURLFound) {

			if (url.equalsIgnoreCase(parameter)) {
				Constants.testResults[0] = url;
				Constants.testResults[1] = "PASSED";
			} else {
				Constants.testResults[0] = url;
				Constants.testResults[1] = "FAILED";
				Constants.abortIt = true;
			}
			Constants.testResults[2] = parameter;
		}

		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : URL is Loaded " + url);
		} else {
			System.out.println("FAILED : URL is not Loaded " + url);
		}
		System.out
				.println("------------------------------------------------------");

		return d;
	}

	public static boolean checkItemIsDisplay(String selector,
			String selectorValue, String parameter) {
		boolean d = false, isDisplayed = false, isWaiting = false;
		WebElement ele = null;
		int sec = 0;
		try {
			sec = Integer.parseInt(selectorValue);
			Utilities.wait = null;
			Utilities.wait = new WebDriverWait(Utilities.driver, sec);

			isWaiting = waitElement(selector, parameter, sec);

			if (isWaiting) {

				ele = Utilities.getWebElement(selector, parameter);

				if (ele != null) {

					isDisplayed = ele.isDisplayed();

					Thread.sleep(300);

					d = true;

				}

			}

		} catch (Exception e) {
			System.out.println("EXCEPTION : checkIsDisplay");
		}

		if (isDisplayed) {
			Constants.testResults[0] = parameter + " is Displayed";
			Constants.testResults[1] = "PASSED";
		} else {
			Constants.testResults[0] = parameter + " is not Displayed";
			Constants.testResults[1] = "FAILED";
			Constants.abortIt = true;
		}

		Constants.testResults[2] = parameter;

		if (d) {
			System.out.println("PASSED : " + selectorValue + " is Displayed");
		} else {
			System.out.println("FAILED : " + selectorValue
					+ " is not Displayed");
		}

		return d;
	}

	public static String clickRadioButton(String selector,
			String selectorValue, String parameter) {
		String d = "", rbtn = "";
		// String[] selectors, selectorValues;
		// boolean isProper = false;
		String isChecked = "";
		try {
			WebElement ele = null;
			List<WebElement> eles = null;
			// selectors = Utilities.splitTextBy(selector, ",");
			// selectorValues = Utilities.splitTextBy(selectorValue, ",");
			// // parameters = Utilities.splitTextBy(parameter, ",");
			// if (selectors.length == 2 && selectorValues.length == 2) {
			// isProper = true;
			// }

			// if (isProper) {
			ele = Utilities.getWebElement(selector, selectorValue);

			if (ele != null) {

				eles = ele.findElements(By.tagName("md-radio-button"));
				if (eles.size() > 0) {

					for (int i = 0; i < eles.size(); i++) {

						rbtn = eles.get(i).getText().toString();
						rbtn = rbtn.toLowerCase().trim().toString();
						parameter = parameter.toLowerCase().trim().toString();
						if (rbtn.equalsIgnoreCase(parameter)) {

							isChecked = eles.get(i)
									.getAttribute("aria-checked");

							if (isChecked != null) {

								if (!isChecked.isEmpty()) {
									if (isChecked.equals("false")
											|| isChecked.equals("true")) {
										eles.get(i).click();
										d = eles.get(i).getText().toString();
										break;
									}
								} else {

								}

							}

						}

					}

				}

			}

			Utilities.toSave(Utilities.saveExel, new String[] { selectorValue,
					d });

		} catch (Exception e) {
			System.out.println("Exception in clickRadioButton ");
		}
		if (!d.isEmpty()) {
			System.out.println("PASSED : " + parameter + " is clicked");
		} else {
			System.out.println("FAILED : " + parameter + " is not clicked");
		}

		return d;
	}

	public static String clickKendoDropDown(String selector,
			String selectorValue, String parameter) {
		String d = "";
		boolean isApper = false;

		try {
			WebElement ele = null;

			ele = Utilities.getWebElement(selector, selectorValue);

			if (ele != null) {
				ele.click();
				ele = null;
				for (String winHandle : Utilities.driver.getWindowHandles()) {
					Utilities.driver.switchTo().window(winHandle);
					System.out.println(selectorValue + " is Appear");
					isApper = true;
				}

				if (isApper) {
					ele = Utilities.getWebElement(selector, parameter);
					if (ele != null) {
						ele.click();
						d = parameter;
						Utilities.driver.switchTo().defaultContent();

					}
				}

			}
			Utilities.toSave(Utilities.saveExel, new String[] { selectorValue,
					d });
		} catch (Exception e) {
			System.out.println("Exception in clickKendoDropDown ");
		}
		if (!d.isEmpty()) {
			System.out.println("PASSED : " + parameter + " is clicked");
		} else {
			System.out.println("FAILED : " + parameter + " is not clicked");
		}

		return d;
	}

	public static boolean checkTextKendoDropDown(String selector,
			String selectorValue, String parameter) {
		String eleData = "";
		boolean d = false, isTextFound = false, check = false;
		boolean isApper = false;

		try {
			WebElement ele = null;

			ele = Utilities.getWebElement(selector, selectorValue);

			if (ele != null) {
				ele.click();
				ele = null;
				for (String winHandle : Utilities.driver.getWindowHandles()) {
					Utilities.driver.switchTo().window(winHandle);
					System.out.println(selectorValue + " is Appear");
					isApper = true;
				}

				if (isApper) {
					ele = Utilities.getWebElement(selector, selectorValue);
					if (ele != null) {
						ele.click();
						eleData = parameter;

						if (!eleData.isEmpty()) {

						}

						Utilities.driver.switchTo().defaultContent();

					}
				}

			}

		} catch (Exception e) {
			System.out.println("Exception in clickKendoDropDown ");
		}

		return d;
	}

	public static boolean scroll(String selector, String seletorValue) {
		boolean b = false;
		WebElement ele = null;
		try {

			ele = Utilities.getWebElement(selector, seletorValue);

			if (ele != null) {
				Utilities.jse.executeScript("arguments[0].scrollIntoView();",
						ele);
				b = true;
			}

		} catch (Exception e) {
			System.out.println("Exception in scroll ");
		}
		if (b) {
			System.out.println("PASSED : " + seletorValue + " is scroll");
		} else {
			System.out.println("FAILED : " + seletorValue + " is not scroll");
		}

		return b;
	}

	public static String clickCheckBoxes(String selector, String selectorValue,
			String data) {
		String datas[] = null;
		String d = "", eleData = "", clickData = "";
		String isChecked = "";
		WebElement ele = null;
		try {

			ele = Utilities.getWebElement(selector, selectorValue);
			if (ele != null) {

				List<WebElement> eles = ele.findElements(By
						.tagName("md-checkbox"));
				datas = Utilities.splitTextBy(data, ",");
				if (eles.size() > 0 && datas != null) {

					for (int j = 0; j < datas.length; j++) {
						for (int i = 0; i < eles.size(); i++) {

							isChecked = "";
							eleData = eles.get(i).getText();
							clickData = datas[j];
							eleData = eleData.toLowerCase().trim().toString();
							clickData = clickData.toLowerCase().trim()
									.toString();
							if (eleData.equalsIgnoreCase(clickData)) {
								isChecked = eles.get(i).getAttribute(
										"aria-checked");

								if (isChecked != null) {
									if (!isChecked.isEmpty()) {
										if (isChecked.equals("false")) {
											eles.get(i).click();
											Thread.sleep(200);
											isChecked = "true";
											d += eles.get(i).getText()
													.toString()
													+ ",";

											Thread.sleep(200);
											break;
										}
										else{
											isChecked = "true";
											d += eles.get(i).getText()
													.toString()
													+ ",";

											Thread.sleep(200);
											break;
										}
									}
								}
							}
							else{
								isChecked = eles.get(i).getAttribute(
										"aria-checked");

								if (isChecked != null) {
									if (!isChecked.isEmpty()) {
										if (isChecked.equals("true")) {
											
											isChecked = "true";
											
											String tmpVal = eles.get(i).getText()
													.toString();
											
											//boolean sts = d.contains(tmpVal);
											
											if (!d.contains(tmpVal)) {
												d += eles.get(i).getText()
														.toString()
														+ ",";
											}
//											else{
//												d += eles.get(i).getText()
//														.toString()
//														+ ",";
//											}
											
										

//											Thread.sleep(200);
//											break;
										}
									}
								}
								
							}

						}

					}

				}

			}
			Utilities.toSave(Utilities.saveExel, new String[] { selectorValue,
					d });
		} catch (Exception e) {
			System.out.println("Exception in clickToFillCheckBoxes ");
		}
		if (!d.isEmpty()) {
			System.out.println("PASSED : " + selectorValue + "[ " + d
					+ " ] are clicked ");
		} else {
			System.out.println("FAILED : " + selectorValue + "[ " + d
					+ " ] are not clicked ");
		}

		return d;
	}

	public static String clickKendoMultiDropDown(String selector,
			String selectorValue, String parameter) {
		String d = "", paraVal = "", drpDwnVal = "";
		boolean isApper = false;
		String[] selectors, selectorValues, parameters;
		boolean isProper = false;
		try {
			WebElement ele = null;
			List<WebElement> li = null;
			selectors = Utilities.splitTextBy(selector, ",");
			selectorValues = Utilities.splitTextBy(selectorValue, ",");
			parameters = Utilities.splitTextBy(parameter, ",");
			if (selectors.length == 3 && selectorValues.length == 3
					&& parameters.length != 0) {
				isProper = true;
			}

			for (int i = 0; i < parameters.length; i++) {

				if (isProper) {

					ele = Utilities.getWebElement(selectors[0],
							selectorValues[0]);

					if (ele != null) {
						ele.click();
						ele = null;
						ele = Utilities.getWebElement(selectors[1],
								"Lead_Source_listbox");
						if (ele != null) {
							li = Utilities.getWebElements(ele, selectors[2],
									selectorValues[2]);

							if (li != null) {

								for (int j = 0; j < li.size(); j++) {

									paraVal = parameters[i].trim()
											.toLowerCase();
									drpDwnVal = li.get(j).getText().trim()
											.toLowerCase();
									String d2 = li.get(j).getText();
									if (paraVal.equalsIgnoreCase(drpDwnVal)) {

										li.get(j).click();
										d += d2 + ",";
										break;

									}
								}

							}

						}

					}

				} else {
					System.out
							.println("may be selector or selectorValue is not proper");
				}

			}

			Utilities.toSave(Utilities.saveExel,
					new String[] { selectorValue.split(",")[0], d });
			// ele = Utilities.getWebElement(selector, selectorValue);

		} catch (Exception e) {
			System.out.println("Exception in clickKendoMultiDropDown ");
		}
		if (!d.isEmpty()) {
			System.out.println("PASSED : " + d + " is clicked");
		} else {
			System.out.println("FAILED : " + d + " is not clicked");
		}

		return d;
	}

	public static String clickDropDown(String selector, String selectorValue,
			String parameter) {
		String d = "", dpVal = "";
		boolean isApper = false;
		int indx = 1, ddLen = 0;

		try {
			WebElement ele = null;
			List<WebElement> eles = null;

			ele = Utilities.getWebElement(selector, selectorValue);

			if (ele != null) {
				ele.click();

				for (String winHandle : Utilities.driver.getWindowHandles()) {
					Utilities.driver.switchTo().window(winHandle);
					System.out.println(selectorValue + " is Appear");
					isApper = true;
				}

				if (isApper) {

					eles = ele.findElements(By.tagName("option"));
					if (eles != null) {
						ddLen = eles.size();

						if (Utilities.isNumeric(parameter)) {

							indx = Integer.parseInt(parameter);
							if (indx < 0 || indx > ddLen) {
								indx = 1;
							}
							d = eles.get(indx).getText();
							eles.get(indx).click();
							Utilities.driver.switchTo().defaultContent();
							eles.get(indx).click();
							ele.click();

						} else {

							for (int i = 0; i < ddLen; i++) {

								dpVal = eles.get(i).getText().toString()
										.toLowerCase();
								dpVal = dpVal.replace("\n", "").trim();
								parameter = parameter.toLowerCase().trim();

								if (dpVal.equals(parameter)) {
									d = eles.get(i).getText();

									eles.get(i).click();
									Utilities.driver.switchTo()
											.defaultContent();
									eles.get(i).click();
									ele.click();
									break;
								}

							}
						}

					}
				}

			}
			Utilities.toSave(Utilities.saveExel, new String[] { selectorValue,
					d });
		} catch (Exception e) {
			System.out.println("Exception in clickDropDown ");
		}
		if (!d.isEmpty()) {
			System.out.println("PASSED : " + d + " is clicked");
		} else {
			System.out.println("FAILED : " + d + " is not clicked");
		}

		return d;
	}

	public static boolean typeInKendoIFrame(String selector,
			String selectorValue, String parameter) {
		boolean d = false;
		try {
			parameter = Utilities.isParameterAuto(parameter);

			WebElement ele = Utilities.getKendoIFrameBody(selector,
					selectorValue);
			if (ele != null) {
				Thread.sleep(1000);
				ele.clear();
				ele.sendKeys(parameter);
				Thread.sleep(500);
				Utilities.driver.switchTo().defaultContent();
				d = true;
			}
		} catch (Exception e) {
			System.out.println("Exception in typeInKendoIFrame");
			d = false;
		}

		if (d) {
			System.out.println("PASSED : " + parameter + " is filled in "
					+ selectorValue);
		} else {
			System.out.println("FAILED : " + parameter + " is not filled in "
					+ selectorValue);
		}

		Utilities.toSave(Utilities.saveExel, new String[] { selectorValue,
				parameter });

		return d;
	}

	public static boolean readSwalPopUp(String selector, String selectorValue,
			String parameter) {
		boolean d = false, isAppear = false;
		String data = "";
		int len = 0;
		WebElement ele = null;
		try {

			if (!parameter.isEmpty()) {
				if (Utilities.isNumeric(parameter)) {

					len = Integer.parseInt(parameter);

					waitElement(selector, selectorValue, len);
					for (String winHandle : Utilities.driver.getWindowHandles()) {
						Utilities.driver.switchTo().window(winHandle);
						System.out.println(selectorValue + " is Appear");
						isAppear = true;
					}

				}
			}

			if (isAppear) {
				ele = Utilities.getWebElement(selector, selectorValue);
				if (ele != null) {
					Thread.sleep(1000);

					data = ele.getText().toString();
					// data = data.replace("Your Task Code is ", "");
					ele = null;

					Utilities.waitingFor = "";
					Utilities.waitingFor = "waitForClickable";
					isAppear = waitElement("tagName", "button", 5);
					if (isAppear) {
						ele = Utilities.getWebElement("tagName", "button");
						if (ele != null) {
							ele.click();
							Utilities.driver.switchTo().defaultContent();

							ele.click();
							d = true;
						}
					}

				}
			}

		} catch (Exception e) {
			System.out.println("Exception in readSwalPopUp");
			d = false;
		}

		if (d) {
			System.out.println("PASSED : " + data + " is appear in "
					+ selectorValue);
		} else {
			System.out.println("FAILED : " + parameter + " is not appear in "
					+ selectorValue);
		}

		Utilities.toSave(Utilities.saveExel,
				new String[] { selectorValue, data });

		return d;
	}

	public static boolean checkSwalPopUpText(String selector,
			String selectorValue, String parameter) {
		boolean d = false, isTextFound = false, isAppear = false, check = false;
		String data = "", eleData = "", checkTxt = "";
		String[] parameters;
		int len = 0;
		WebElement ele = null;
		try {

			if (!parameter.isEmpty()) {

				parameters = Utilities.splitTextBy(parameter, ",");

				if (Utilities.isNumeric(parameters[0])) {

					len = Integer.parseInt(parameters[0]);
					checkTxt = parameters[1];
					waitElement(selector, selectorValue, len);
					for (String winHandle : Utilities.driver.getWindowHandles()) {
						Utilities.driver.switchTo().window(winHandle);
						System.out.println(selectorValue + " is Appear");
						isAppear = true;
					}

				}
			}

			if (isAppear) {
				ele = Utilities.getWebElement(selector, selectorValue);
				if (ele != null) {
					Thread.sleep(1000);

					String[] datas = ele.getText().toString().split("\n");

					if (!datas[0].isEmpty()) {
						eleData = datas[0];
						isTextFound = true;
					}

					ele = null;

					Utilities.waitingFor = "";
					Utilities.waitingFor = "waitForClickable";
					isAppear = waitElement("tagName", "button", 5);
					if (isAppear) {
						ele = Utilities.getWebElement("tagName", "button");
						if (ele != null) {
							ele.click();
							Utilities.driver.switchTo().defaultContent();

							ele.click();
							d = true;
						}
					}

				}
			}

		} catch (Exception e) {
			System.out.println("Exception in checkSwalPopUpText");
			d = false;
		}

		if (!isTextFound) {
			eleData = " no text found in " + selectorValue;
			isTextFound = true;
		}

		if (isTextFound) {

			check = eleData.equalsIgnoreCase(checkTxt);

			if (check) {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "PASSED";
				d = true;
			} else {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "FAILED";
			}

			Constants.testResults[2] = checkTxt;

			System.out
					.println("------------------------------------------------------");
			if (d) {
				System.out.println("PASSED : " + checkTxt + " (is equal To)  "
						+ eleData);
			} else {
				System.out.println("FAILED : " + checkTxt
						+ " (is not equal To)  " + eleData);
			}
			System.out
					.println("------------------------------------------------------");
		}
		return d;
	}

	public static boolean loadGridTable(String selector, String selectorValue,
			String parameter) {
		boolean d = false, isIdAppear = false, isTableAppear = false;
		int sec = 0, attempt = 0;

		WebElement ele = null, ele2 = null;
		List<WebElement> table = null;
		try {

			System.out.println("Waiting.....to found element");
			Thread.sleep(800);

			if (!parameter.isEmpty()) {
				if (Utilities.isNumeric(parameter)) {

					sec = Integer.parseInt(parameter);
				} else {
					sec = 10;
				}
			} else {
				sec = 10;
			}

			while (!isIdAppear) {
				isIdAppear = waitElement(selector, selectorValue, sec);

				if (isIdAppear) {

					ele = Utilities.getWebElement(selector, selectorValue);
					if (ele != null) {
						// isTableAppear = false;

						// while (!isTableAppear) {
						// isTableAppear = waitElement("className",
						// "k-selectable", sec);
						// isTableAppear=true;
						// if (isTableAppear) {

						ele = ele.findElement(By.className("k-selectable"));
						if (ele != null) {

							table = ele.findElements(By.tagName("tr"));
							if (table != null) {
								Constants.gridTableData = null;
								Constants.gridTableData = table;
								isTableAppear = true;
								d=true;
							}

						} else {
							isIdAppear = false;
							// Utilities.driver.navigate().refresh();
							// isTableAppear = false;
						}

						// } else {
						// isTableAppear = true;
						// isIdAppear = false;
						// //Utilities.driver.navigate().refresh();
						// }

						// }

					} else {
						// Utilities.driver.navigate().refresh();
						isIdAppear = false;
					}

				}

				attempt++;

				if (attempt == 3) {
					d = false;

					System.out.println("Failed to load grid in " + attempt
							+ " attempts");
					break;
				}

			}

		} catch (Exception e) {
			System.out.println("Exception in loadGridTable");
			if (e.getMessage().contains("unexpected alert open")) {
				d = false;
				isIdAppear = false;
				isTableAppear = false;
				if (Utilities.ignoreAlertPop()) {
					Utilities.driver.navigate().refresh();
					d = loadGridTable(selector, selectorValue, parameter);
				}

			}
			d = loadGridTable(selector, selectorValue, parameter);

		}

		if (d) {
			System.out.println("Table is loaded in " + attempt + " attempts");
		}

		return d;

	}

	public static boolean loadGridRow(String selector, String selectorValue,
			String parameter) {
		boolean sts = false;
		// List<String> list = new ArrayList<String>();
		int col_indx = 0;
		// if (new WebPageDriver().isPageLoaded()) {

		String d = "", coltagName = "";
		int rowCellSize = 0;
		try {

			parameter = Utilities.isParameterAuto(parameter);
			// parameter="WIKTu exUg";
			Constants.readGridDataList.clear();
			if (Utilities.isNumeric(selectorValue)) {
				col_indx = Integer.parseInt(selectorValue);

			}
			if (!selector.isEmpty()) {
				if (selector.equalsIgnoreCase("normal")) {
					coltagName = "span";
				} else if (selector.equalsIgnoreCase("link")) {
					coltagName = "a";
				}

			} else {
				coltagName = "span";
			}

			Thread.sleep(3000);
			List<WebElement> cells = null;
			if (Constants.gridTableData != null) {
				System.out.println("Fetching Column index " + col_indx + " of "
						+ parameter);
				WebElement col = null;
				if (Constants.gridTableData.size() > 0) {

					for (int i = 0; i < Constants.gridTableData.size(); i++) {
						// WebPageDriver.wait.until(ExpectedConditions
						// .presenceOfElementLocated(By.tagName("td")));
						cells = Constants.gridTableData.get(i).findElements(
								By.tagName("td"));

						if (cells != null) {

							if (cells.size() > 0) {
								for (int j = 0; j < cells.size(); j++) {

									col = cells.get(col_indx).findElement(
											By.tagName(coltagName));

									if (col != null) {

										d = col.getText();

										if (d.equalsIgnoreCase(parameter)
												|| d.contains(parameter)) {

											System.out.println(d
													+ " Found in Row index "
													+ (i + 1));

											System.out
													.println("Fecthing Row items.....");

											for (int k = i; j < Constants.gridTableData
													.size(); k++) {
												cells = Constants.gridTableData
														.get(i)
														.findElements(
																By.tagName("td"));
												rowCellSize = cells.size();
												for (int l = 0; l < rowCellSize; l++) {
													Thread.sleep(500);
													d = cells.get(l).getText()
															.toString();
													// System.out.println(" (" +
													// l
													// + ") " + d);
													Thread.sleep(500);
													Constants.readGridDataList
															.add(d);
													d = "Found";

													// l Loop
												}

												if (!d.isEmpty()) {
													if (d.equals("Found")) {
														break;
													}
												}

												// k Loop
											}

											System.out
													.println(Constants.readGridDataList
															.size()
															+ " Columns Found in Row");

											if (!d.isEmpty()) {
												if (d.equals("Found")) {
													break;
												}
											}
										}

									}

									if (!d.isEmpty()) {
										break;

									}

								}

								// j Loop

								if (!d.isEmpty()) {
									if (d.equals("Found")) {
										break;
									}
								}

							}

							if (!d.isEmpty()) {
								if (d.equals("Found")) {
									break;
								}

							}

						}

						// i Loop
					}

				}

			}

			if (Constants.readGridDataList.size() > 0) {
				sts = true;
			}

		} catch (Exception e) {
			System.out.println("EXCEPTION : in loadGridRow");
		}

		if (sts) {
			System.out.println("PASSED : " + parameter + " of Row is Fetched");
			System.out
					.println("===============================================");

			for (int i = 0; i < Constants.readGridDataList.size(); i++) {

				System.out.println("Coulmn (" + i + ") "
						+ Constants.readGridDataList.get(i));
			}

			System.out
					.println("===============================================");
		} else {
			System.out.println("FAILED : " + parameter
					+ " of Row is not Fetched");
		}
		// }

		// return list;

		return sts;
	}

	public static boolean loadGridRowSPANS(String selector,
			String selectorValue, String parameter) {
		boolean sts = false;
		String[] selectors, selectorValues;

		// List<String> list = new ArrayList<String>();
		int col_indx = 0, tags_idx = 0;
		// if (new WebPageDriver().isPageLoaded()) {

		String d = "", coltagName = "";
		int rowCellSize = 0;
		try {
			// selector="normal,status";
			// selectorValue="1,14";
			Constants.readGridDataList.clear();

			selectors = Utilities.splitTextBy(selector, ",");
			selectorValues = Utilities.splitTextBy(selectorValue, ",");

			parameter = Utilities.isParameterAuto(parameter);

			// parameter = "vhwUb";//"olR96tj@gmail.com";//"XgY73QY@gmail.com";

			// Constants.readGridDataList.clear();

			if (Utilities.isNumeric(selectorValues[0])) {
				col_indx = Integer.parseInt(selectorValues[0]);

			}
			// col_indx=1;
			if (Utilities.isNumeric(selectorValues[1])) {
				tags_idx = Integer.parseInt(selectorValues[1]);

			}

			if (!selectors[0].isEmpty()) {
				if (selectors[0].equalsIgnoreCase("normal")) {
					coltagName = "span";
				} else if (selectors[0].equalsIgnoreCase("link")) {
					coltagName = "a";
				}

			} else {
				coltagName = "span";
			}

			Thread.sleep(3000);
			List<WebElement> cells = null;
			if (Constants.gridTableData != null) {
				System.out.println("Fetching Column index " + col_indx + " of "
						+ parameter);
				WebElement col = null;
				if (Constants.gridTableData.size() > 0) {

					for (int i = 0; i < Constants.gridTableData.size(); i++) {
						// WebPageDriver.wait.until(ExpectedConditions
						// .presenceOfElementLocated(By.tagName("td")));
						cells = Constants.gridTableData.get(i).findElements(
								By.tagName("td"));

						if (cells != null) {

							if (cells.size() > 0) {
								for (int j = 0; j < cells.size(); j++) {

									col = cells.get(col_indx).findElement(
											By.tagName(coltagName));

									if (col != null) {

										d = col.getText();

										if (d.equalsIgnoreCase(parameter)
												|| d.contains(parameter)) {

											System.out.println(d
													+ " Found in Row index "
													+ (i + 1));

											System.out
													.println("Fecthing Row items.....");

											for (int k = i; j < Constants.gridTableData
													.size(); k++) {
												cells = Constants.gridTableData
														.get(i)
														.findElements(
																By.tagName("td"));
												rowCellSize = cells.size();

												Thread.sleep(500);

												List<WebElement> spans = cells
														.get(13)
														.findElements(
																By.tagName("span"));

												if (spans != null
														&& spans.size() > 0) {

													System.out
															.println(spans
																	.size()
																	+ " spans found in Col ("
																	+ selectors[1]
																	+ ").....");

													for (int l = 0; l < spans
															.size(); l++) {
														d = spans.get(l)
																.getText()
																.toString();

														if (d.isEmpty()) {
															d = spans
																	.get(l)
																	.getAttribute(
																			"id")
																	.toString();
														}

														System.out.println(" ("
																+ tags_idx
																+ "-" + l
																+ ") " + d);
														Thread.sleep(500);
														Constants.readGridDataList
																.add(d);
														d = "Found";
													}

												}

												// Thread.sleep(500);
												// d = cells.get(l).getText()
												// .toString();
												// System.out.println(" (" +
												// l
												// + ") " + d);
												// Thread.sleep(500);
												// Constants.readGridDataList
												// .add(d);
												// d = "Found";
												//
												// // l Loop
												// }

												if (!d.isEmpty()) {
													if (d.equals("Found")) {
														break;
													}
												}

												// k Loop
											}

											System.out
													.println(Constants.readGridDataList
															.size()
															+ " Columns Found in Row");

											if (!d.isEmpty()) {
												if (d.equals("Found")) {
													break;
												}
											}
										}

									}

									if (!d.isEmpty()) {
										break;

									}

								}

								// j Loop

								if (!d.isEmpty()) {
									if (d.equals("Found")) {
										break;
									}
								}

							}

							if (!d.isEmpty()) {
								if (d.equals("Found")) {
									break;
								}

							}

						}

						// i Loop
					}

				}

			}

			if (Constants.readGridDataList.size() > 0) {
				sts = true;
			}

		} catch (Exception e) {
			System.out.println("EXCEPTION : in loadGridRowSPANS");
		}

		if (sts) {
			System.out.println("PASSED : " + parameter + " of Row is Fetched");
			System.out
					.println("===============================================");

			for (int i = 0; i < Constants.readGridDataList.size(); i++) {

				System.out.println("Coulmn (" + i + ") "
						+ Constants.readGridDataList.get(i));
			}

			System.out
					.println("===============================================");
		} else {
			System.out.println("FAILED : " + parameter
					+ " of Row is not Fetched");
		}
		// }

		// return list;

		return sts;
	}

	public static boolean loadListTable(String selector, String selectorValue,
			String parameter) {
		boolean d = false, isIdAppear = false, isTableAppear = false;
		int sec = 0, attempt = 1;
		// selectorValue = "record-content";
		WebElement ele = null, ele2 = null;
		List<WebElement> table = null;
		try {

			System.out.println("Waiting.....to found element");
			Thread.sleep(800);

			if (!parameter.isEmpty()) {
				if (Utilities.isNumeric(parameter)) {

					sec = Integer.parseInt(parameter);
				} else {
					sec = 10;
				}
			} else {
				sec = 10;
			}

			while (!isIdAppear) {
				isIdAppear = waitElement(selector, selectorValue, sec);
				// isIdAppear=true;
				if (isIdAppear) {

					ele = Utilities.getWebElement(selector, selectorValue);
					if (ele != null) {
						// isTableAppear = false;

						// while (!isTableAppear) {
						// isTableAppear = waitElement("className",
						// "k-selectable", sec);
						// isTableAppear=true;
						// if (isTableAppear) {

						ele = ele.findElement(By.className("k-selectable"));
						if (ele != null) {

							table = ele.findElements(By.tagName("tr"));
							if (table != null) {
								Constants.listTableData = null;
								Constants.listTableData = table;
								isTableAppear = true;
							}

						} else {
							isIdAppear = false;
							// Utilities.driver.navigate().refresh();
							// isTableAppear = false;
						}

						// } else {
						// isTableAppear = true;
						// isIdAppear = false;
						// //Utilities.driver.navigate().refresh();
						// }

						// }

					} else {
						// Utilities.driver.navigate().refresh();
						isIdAppear = false;
					}

				}

				attempt++;

				if (attempt == 3) {
					d = false;

					System.out.println("Failed to load grid in " + attempt
							+ " attempts");
					break;
				}

			}

		} catch (Exception e) {
			System.out.println("Exception in loadGridTable");
			if (e.getMessage().contains("unexpected alert open")) {
				d = false;
				isIdAppear = false;
				isTableAppear = false;
				if (Utilities.ignoreAlertPop()) {
					Utilities.driver.navigate().refresh();
					d = loadListTable(selector, selectorValue, parameter);
				}

			}
			d = loadGridTable(selector, selectorValue, parameter);

		}

		if (d) {
			System.out.println("Table is loaded in " + attempt + " attempts");
		}

		return d;

	}

	public static boolean checkRowValue(String selector, String selectorValue,
			String parameter) {
		boolean d = false, isDataFound1 = false, isDataFound2 = false;
		int i = 0;
		String gridData = "";
		try {
			//selector="Compare";
			// selectorValue="0";
			Constants.testResults[2] = null;
			parameter = Utilities.isParameterAuto(parameter);
			System.out.println(Constants.readGridDataList.size());
			if (Constants.readGridDataList.size() > 0) {

				if (Utilities.isNumeric(selectorValue)) {

					i = Integer.parseInt(selectorValue);
					gridData = Constants.readGridDataList.get(i);
					isDataFound1 = true;
				}

			}

			if (!parameter.isEmpty()) {
				Constants.testResults[2] = parameter;
				isDataFound2 = true;
			}

		} catch (Exception e) {
			System.out.println("Exception in checkRowValue");
		}

		if (isDataFound1 && isDataFound2) {

			boolean check = false;
			// selector="match";
			if (selector.equalsIgnoreCase("Compare")) {

				if (parameter.contains(",") || gridData.contains(",")) {
					gridData = gridData.replace(",", "").trim();
					parameter = parameter.replace(",", "").trim();
					check = gridData.equalsIgnoreCase(parameter);
					if (!check) {
						int x = gridData.compareTo(parameter);

						if (x <= 0) {
							check = true;
						}
					}

				} else {
					int x = gridData.compareToIgnoreCase(parameter);
					if (x >= 0) {
						check = true;
					}

				}

			} else if (selector.equalsIgnoreCase("equal")) {
				check = gridData.toLowerCase().trim()
						.equalsIgnoreCase(parameter.toLowerCase().trim());
			} else if (selector.equalsIgnoreCase("match")) {
				if (Utilities.isValidDate(parameter)) {
					check = gridData.startsWith(parameter.split(" ")[0]);
					if (!check) {
						check = gridData.matches(parameter);
					}
				}
			}

			if (check) {
				Constants.testResults[0] = gridData;
				Constants.testResults[1] = "PASSED";
			} else {
				Constants.testResults[0] = gridData;
				Constants.testResults[1] = "FAILED";
			}

			d = true;
		} else if (!isDataFound1) {
			Constants.testResults[0] = "No Expected Result Found";
			Constants.testResults[1] = "FAILED";

		} else if (!isDataFound2) {
			Constants.testResults[0] = "No Expected Result Found";
			Constants.testResults[1] = "FAILED";
			Constants.testResults[2] = "No Expected Result Found";
		}

		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : " + parameter + " (is " + selector
					+ " To)  " + gridData);
		} else {
			System.out.println("FAILED : " + parameter + " (is not " + selector
					+ " To)  " + gridData);
		}
		System.out
				.println("------------------------------------------------------");

		return d;

	}
	
	

	public static boolean loadNoteListTableItem(String selector,
			String selectorValue, String parameter) {
		boolean d = false, directRow = false, isTableAppear = false;
		int rowIdx = 0, attempt = 1;
		WebElement divContainer = null, ele2 = null;
		// parameter="cssSelector,div[class='inner-notes-text-msg']";
		List<WebElement> divs = null;
		try {

			System.out.println("Waiting.....to found list element");
			Thread.sleep(850);

			divContainer = Utilities.getWebElement(selector, selectorValue);
			if (divContainer != null) {
				String parametres[] = Utilities.splitTextBy(parameter, ",");
				divs = Utilities.getWebElements(divContainer, parametres[0],
						parametres[1]);// divContainer.findElements(By.cssSelector("div[class='inner-notes-text-msg']"));
				if (divs != null) {
					Constants.notelistDivs = null;
					Constants.notelistDivs = divs;
					System.out.println("List Rows " + (divs.size()) + " Found");
					d = true;
				}
			}

		} catch (Exception e) {
			System.out.println("loadNoteListTableItem keyword is failed");

		}

		if (d) {
			System.out.println("PASSED : " + selectorValue + " is loaded ");
		} else {
			System.out.println("FAILED : to load " + selectorValue);
		}

		return d;

	}

	public static boolean fetchNoteListTableItem(String selector,
			String selectorValue, String parameter) {
		boolean d = false, directRow = false, isTableAppear = false;
		int rowIdx = 0, attempt = 1;
		WebElement divContainer = null, ele2 = null;
		// parameter="cssSelector,div[class='inner-notes-text-msg']";
		String ids = "";
		List<WebElement> divs = null, subDivs = null;
		WebElement div = null, subDiv = null;
		try {
			Constants.notelistRowItems.clear();
			if (Constants.notelistDivs != null) {

				if (!parameter.isEmpty()) {

					if (Utilities.isNumeric(parameter)) {
						rowIdx = 1;
						directRow = true;
					} else {
						directRow = false;
						parameter = Utilities.isParameterAuto(parameter);
					}

					if (directRow) {
						div = Constants.notelistDivs.get((rowIdx - 1));
						if (div != null) {
							subDivs = div.findElements(By.tagName("div"));

							if (subDivs != null) {

								for (int i = 0; i < subDivs.size(); i++) {

									WebElement item = subDivs.get(i);

									if (item != null) {
										ids = item.getAttribute("id");
										if (!ids.isEmpty()) {

											Constants.notelistRowItems.add(ids);
											// d = true;
										}
									}

									// ids = subDivs.get(i).getAttribute("id");
									//
									// if (!ids.isEmpty()) {
									//
									// Constants.notelistRowItems.add(ids);
									// // d = true;
									// }

								}

							}

						}

					} else {

						for (int i = 0; i < Constants.notelistDivs.size(); i++) {
							div = Constants.notelistDivs.get(i);
							if (div != null) {

								subDiv = div.findElement(By.tagName("div"));
								if (subDiv != null) {

									String id = subDiv.getAttribute("id");
									if (!id.isEmpty()) {

										if (id.equals(parameter)) {

											subDivs = div.findElements(By
													.tagName("div"));

											if (div != null) {
												subDivs = div.findElements(By
														.tagName("div"));

												if (subDivs != null) {

													for (int j = 0; j < subDivs
															.size(); j++) {

														WebElement item = subDivs
																.get(j);

														if (item != null) {
															ids = item
																	.getAttribute("id");
															if (!ids.isEmpty()) {

																Constants.notelistRowItems
																		.add(ids);
																// d = true;
															}
														}

													}
													break;

												}

											}

											// if (Constants.notelistRowDivs !=
											// null) {
											// d = true;
											// break;
											// }

										}
									}
								}
							}

						}

					}

				}

				if (Constants.notelistRowItems != null
						&& Constants.notelistRowItems.size() > 0) {
					d = true;

				}

			} else {
				System.out.println("loadNoteListTableItem was not loaded");
			}

		} catch (Exception e) {
			System.out.println("Exception in fetchNoteListTableItem");

		}

		if (d) {
			System.out.println("PASSED : Note List  " + parameter
					+ " of Row is Fetched");
			System.out
					.println("===============================================");

			for (int i = 0; i < Constants.notelistRowItems.size(); i++) {

				System.out.println("Item (" + i + ") "
						+ Constants.notelistRowItems.get(i));
			}

			System.out
					.println("===============================================");
		} else {
			System.out.println("FAILED : Note List " + parameter
					+ " of Row is not Fetched");
		}

		return d;

	}

	public static boolean checkNoteListTableItem(String selector,
			String selectorValue, String parameter) {
		boolean d = false, isDataFound1 = false, isDataFound2 = false;
		int i = 0;
		String listItem = "";
		try {
			// selectorValue="15";
			Constants.testResults[2] = null;
			parameter = Utilities.isParameterAuto(parameter);
			if (Constants.notelistRowItems.size() > 0) {

				if (Utilities.isNumeric(selectorValue)) {

					i = Integer.parseInt(selectorValue);
					listItem = Constants.notelistRowItems.get(i);
					isDataFound1 = true;
				}

			}

			if (!parameter.isEmpty()) {
				Constants.testResults[2] = parameter;
				isDataFound2 = true;
			}

		} catch (Exception e) {
			System.out.println("Exception in checkNoteListTableItem");
		}

		if (isDataFound1 && isDataFound2) {

			listItem = Utilities.removeLRSpace(listItem);
			parameter = Utilities.removeLRSpace(parameter);

			boolean check = false;
			// selector="match";
			if (selector.equalsIgnoreCase("Compare")) {

				if (parameter.contains(",") || listItem.contains(",")) {
					listItem = listItem.replace(",", "").trim();
					parameter = parameter.replace(",", "").trim();
					check = listItem.equalsIgnoreCase(parameter);
					if (!check) {
						int x = listItem.compareTo(parameter);

						if (x <= 0) {
							check = true;
						}
					}

				} else {
					int x = listItem.compareToIgnoreCase(parameter);
					if (x >= 0) {
						check = true;
					}

				}

			} else if (selector.equalsIgnoreCase("equal")) {

				check = listItem
						.replaceAll("^\\s+", "")
						.replaceAll("^\\s+$", "")
						.toLowerCase()
						.trim()
						.equalsIgnoreCase(
								parameter.toLowerCase().trim()
										.replaceAll("^\\s+", "")
										.replaceAll("^\\s+$", ""));
			} else if (selector.equalsIgnoreCase("match")) {
				if (Utilities.isValidDate(parameter)) {
					check = listItem.startsWith(parameter.split(" ")[0]);
					if (!check) {
						check = listItem.matches(parameter);
					}
				}
			}

			if (check) {
				Constants.testResults[0] = listItem;
				Constants.testResults[1] = "PASSED";
			} else {
				Constants.testResults[0] = listItem;
				Constants.testResults[1] = "FAILED";
			}

			d = true;
		} else if (!isDataFound1) {
			Constants.testResults[0] = "Row Data not Found";
			Constants.testResults[1] = "FAILED";

		} else if (!isDataFound2) {
			Constants.testResults[0] = "Row Check Data not Found";
			Constants.testResults[1] = "FAILED";
			Constants.testResults[2] = "No Expected Result Found";
		}

		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : " + parameter + " (is " + selector
					+ " To)  " + listItem);
		} else {
			System.out.println("FAILED : " + parameter + " (is not " + selector
					+ " To)  " + listItem);
		}
		System.out
				.println("------------------------------------------------------");

		return d;

	}

	public static boolean loadTaskListTableItem(String selector,
			String selectorValue, String parameter) {
		boolean d = false, directRow = false, isTableAppear = false;
		int rowIdx = 0, attempt = 1;
		WebElement divContainer = null, ele2 = null;
		// parameter="cssSelector,div[class='inner-notes-details']";
		List<WebElement> divs = null;
		try {

			System.out.println("Waiting.....to found list element");
			Thread.sleep(850);

			divContainer = Utilities.getWebElement(selector, selectorValue);
			if (divContainer != null) {
				String parametres[] = Utilities.splitTextBy(parameter, ",");
				divs = Utilities.getWebElements(divContainer, parametres[0],
						parametres[1]);// divContainer.findElements(By.cssSelector("div[class='inner-notes-text-msg']"));
				if (divs != null) {
					Constants.tasklistDivs = null;
					Constants.tasklistDivs = divs;
					System.out.println("List Rows " + (divs.size()) + " Found");
					d = true;
				}
			}

		} catch (Exception e) {
			System.out.println("loadTaskListTableItem keyword is failed");

		}

		if (d) {
			System.out.println("PASSED : " + selectorValue + " is loaded ");
		} else {
			System.out.println("FAILED : to load " + selectorValue);
		}

		return d;

	}

	public static boolean fetchTaskListTableItem(String selector,
			String selectorValue, String parameter) {
		boolean d = false, directRow = false, isTableAppear = false;
		int rowIdx = 0, attempt = 1;
		WebElement divContainer = null, ele2 = null;
		// parameter="cssSelector,div[class='inner-notes-text-msg']";
		String ids = "";
		List<WebElement> divs = null, subDivs = null;
		WebElement div = null, div2 = null, subDiv = null;
		try {
			Constants.tasklistRowItems.clear();
			if (Constants.tasklistDivs != null) {

				if (!parameter.isEmpty()) {

					if (Utilities.isNumeric(parameter)) {
						rowIdx = 1;
						directRow = true;
					} else {
						directRow = false;
						parameter = Utilities.isParameterAuto(parameter);
					}

					if (directRow) {
						div = Constants.tasklistDivs.get((rowIdx - 1));
						if (div != null) {
							subDivs = div.findElements(By.tagName("div"));

							if (subDivs != null) {

								for (int i = 0; i < subDivs.size(); i++) {

									WebElement item = subDivs.get(i);

									if (item != null) {
										ids = item.getAttribute("id");
										if (!ids.isEmpty()) {

											Constants.tasklistRowItems.add(ids);
											// d = true;
										}
									}

									// ids = subDivs.get(i).getAttribute("id");
									//
									// if (!ids.isEmpty()) {
									//
									// Constants.notelistRowItems.add(ids);
									// // d = true;
									// }

								}

							}

						}

					} else {

						for (int i = 0; i < Constants.tasklistDivs.size(); i++) {
							div = Constants.tasklistDivs.get(i);
							if (div != null) {
								div2 = div.findElement(By.tagName("div"));
								if (div2 != null) {

									subDiv = div2
											.findElement(By.tagName("div"));
									if (subDiv != null) {

										String id = subDiv.getAttribute("id");
										if (!id.isEmpty()) {

											if (id.equals(parameter)) {

												subDivs = div.findElements(By
														.tagName("div"));

												if (div != null) {
													subDivs = div
															.findElements(By
																	.tagName("div"));

													if (subDivs != null) {

														for (int j = 0; j < subDivs
																.size(); j++) {

															WebElement item = subDivs
																	.get(j);

															if (item != null) {
																ids = item
																		.getAttribute("id");
																if (!ids.isEmpty()) {

																	Constants.tasklistRowItems
																			.add(ids);
																	// d = true;
																}
															}

														}
														break;

													}

												}

												// if (Constants.notelistRowDivs
												// !=
												// null) {
												// d = true;
												// break;
												// }

											}
										}
									}
								}

							}

						}

					}

				}

				if (Constants.tasklistRowItems != null
						&& Constants.tasklistRowItems.size() > 0) {
					d = true;

				}

			} else {
				System.out.println("loadTaskListTableItem was not loaded");
			}

		} catch (Exception e) {
			System.out.println("Exception in fetchTaskListTableItem");

		}

		if (d) {
			System.out.println("PASSED : Task List  " + parameter
					+ " of Row is Fetched");
			System.out
					.println("===============================================");

			for (int i = 0; i < Constants.tasklistRowItems.size(); i++) {

				System.out.println("Item (" + i + ") "
						+ Constants.tasklistRowItems.get(i));
			}

			System.out
					.println("===============================================");
		} else {
			System.out.println("FAILED : Task  List " + parameter
					+ " of Row is not Fetched");
		}

		return d;

	}

	public static boolean checkTaskListTableItem(String selector,
			String selectorValue, String parameter) {
		boolean d = false, isDataFound1 = false, isDataFound2 = false;
		int i = 0;
		String listItem = "";
		try {
			// selectorValue="15";
			Constants.testResults[2] = null;
			parameter = Utilities.isParameterAuto(parameter);
			if (Constants.tasklistRowItems.size() > 0) {

				if (Utilities.isNumeric(selectorValue)) {

					i = Integer.parseInt(selectorValue);
					listItem = Constants.tasklistRowItems.get(i);
					isDataFound1 = true;
				}

			}

			if (!parameter.isEmpty()) {
				Constants.testResults[2] = parameter;
				isDataFound2 = true;
			}

		} catch (Exception e) {
			System.out.println("Exception in checkTaskListTableItem");
		}

		if (isDataFound1 && isDataFound2) {

			listItem = Utilities.removeLRSpace(listItem);
			parameter = Utilities.removeLRSpace(parameter);

			boolean check = false;
			// selector="match";
			if (selector.equalsIgnoreCase("Compare")) {

				if (parameter.contains(",") || listItem.contains(",")) {
					listItem = listItem.replace(",", "").trim();
					parameter = parameter.replace(",", "").trim();
					check = listItem.equalsIgnoreCase(parameter);
					if (!check) {
						int x = listItem.compareTo(parameter);

						if (x <= 0) {
							check = true;
						}
					}

				} else {
					int x = listItem.compareToIgnoreCase(parameter);
					if (x >= 0) {
						check = true;
					}

				}

			} else if (selector.equalsIgnoreCase("equal")) {

				check = listItem
						.replaceAll("^\\s+", "")
						.replaceAll("^\\s+$", "")
						.toLowerCase()
						.trim()
						.equalsIgnoreCase(
								parameter.toLowerCase().trim()
										.replaceAll("^\\s+", "")
										.replaceAll("^\\s+$", ""));
			} else if (selector.equalsIgnoreCase("match")) {
				if (Utilities.isValidDate(parameter)) {
					check = listItem.startsWith(parameter.split(" ")[0]);
					if (!check) {
						check = listItem.matches(parameter);
					}
				}
			}

			if (check) {
				Constants.testResults[0] = listItem;
				Constants.testResults[1] = "PASSED";
			} else {
				Constants.testResults[0] = listItem;
				Constants.testResults[1] = "FAILED";
			}

			d = true;
		} else if (!isDataFound1) {
			Constants.testResults[0] = "Row Data not Found";
			Constants.testResults[1] = "FAILED";

		} else if (!isDataFound2) {
			Constants.testResults[0] = "Row Check Data not Found";
			Constants.testResults[1] = "FAILED";
			Constants.testResults[2] = "No Expected Result Found";
		}

		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : " + parameter + " (is " + selector
					+ " To)  " + listItem);
		} else {
			System.out.println("FAILED : " + parameter + " (is not " + selector
					+ " To)  " + listItem);
		}
		System.out
				.println("------------------------------------------------------");

		return d;

	}

	public static boolean checkErrMsgText(String selector,
			String selectorValue, String parameter) {
		boolean d = false, isTextFound = false, check = false;
		WebElement ele = null;
		String eleData = "";
		try {

			parameter = Utilities.isParameterAuto(parameter);

			ele = Utilities.getWebElement(selector, selectorValue);

			if (ele != null) {

				eleData = ele.getText();
				if (!eleData.isEmpty()) {
					isTextFound = true;
				}
				d = true;
			}

		} catch (Exception e) {

			System.out.println("EXCEPTION : checkErrMsgText");
		}

		if (!isTextFound) {
			eleData = "No error message";

			isTextFound = true;
		}

		if (isTextFound) {

			// parameter=Utilities.removeLRSpace(parameter);
			parameter = parameter.replaceAll("^\\s+", "").replaceAll("\\s+$",
					"");

			if (parameter.contains(",") || eleData.contains(",")) {
				eleData = eleData.replace(",", "").trim();
				parameter = parameter.replace(",", "").trim();
				check = eleData.equalsIgnoreCase(parameter);
				if (!check) {
					int x = eleData.compareTo(parameter);

					if (x <= 0) {
						check = true;
					}
				}

			} else {
				check = eleData.equalsIgnoreCase(parameter);
			}

			if (check) {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "PASSED";
			} else {
				Constants.testResults[0] = eleData;
				Constants.testResults[1] = "FAILED";
			}

			Constants.testResults[2] = parameter;

		}

		System.out
				.println("------------------------------------------------------");
		if (d) {
			System.out.println("PASSED : " + parameter + " (is equal To)  "
					+ eleData);
		} else {
			System.out.println("FAILED : " + parameter + " (is not equal To)  "
					+ eleData);
		}
		System.out
				.println("------------------------------------------------------");

		return d;
	}

	public static boolean clickGridTableItem(String selector,
			String selectorValue, String parameter) {
		boolean sts = false, isDirectRow = false;
		String findItem = null, findTagName = "span", colTagName = null;
		int findindx = 0, ColIndx = 0, RowIdx = 0;
		String d = "";
		int rowCellSize = 0;
		 
		// selector="link : 1";
		// parameter="link : 1";

		selectorValue = Utilities.isParameterAuto(selectorValue);
		//selectorValue="auto megre";
		try {

			if (Utilities.isNumeric(selectorValue)) {
				RowIdx = Integer.parseInt(selectorValue);
				isDirectRow = true;
			} else {
				findItem = selectorValue;
			}

			if (!selector.isEmpty()) {
				String datas[] = Utilities.splitTextBy(selector, ":");
				if (datas.length >= 2) {

					findTagName = datas[0].toString().replaceAll("^\\s+", "")
							.replaceAll("\\s+$", "");
					if (findTagName.equalsIgnoreCase("link")) {
						findTagName = "a";
					} else {
						findTagName = "span";
					}

					if (Utilities.isNumeric(datas[1].toString().trim())) {
						findindx = Integer.parseInt(datas[1].toString().trim());
					}

				}
			}

			String datas2[] = Utilities.splitTextBy(parameter, ":");
			if (datas2.length >= 2) {

				colTagName = datas2[0].toString().trim()
						.replaceAll("^\\s+", "").replaceAll("\\s+$", "");
				if (colTagName.equalsIgnoreCase("link")) {
					colTagName = "a";
				} else if (colTagName.equalsIgnoreCase("checkbox")) {
					colTagName = "input";
				} else {
					colTagName = "span";
				}
				if (Utilities.isNumeric(datas2[1].toString().trim())) {
					ColIndx = Integer.parseInt(datas2[1].toString().trim());
				}

			}
			// if (Utilities.isNumeric(parameter.trim())) {
			// ColIndx = Integer.parseInt(parameter);
			// }

			Thread.sleep(3000);
			List<WebElement> cells = null;
			if (Constants.gridTableData != null) {

				WebElement col = null;
				if (Constants.gridTableData.size() > 0) {

					if (isDirectRow) {

						System.out.println("Directly Finding Row : " + RowIdx);

						cells = Constants.gridTableData.get(RowIdx - 1)
								.findElements(By.tagName("td"));

						if (cells != null) {

							col = cells.get(ColIndx).findElement(
									By.tagName(colTagName));

							if (col != null) {
								d = col.getText();
								col.click();

								sts = true;

							}

						}
					} else {
						System.out.println("Finding Row item  " + findItem
								+ " on Column  " + findindx);

						for (int i = 0; i < Constants.gridTableData.size(); i++) {
							cells = Constants.gridTableData.get(i)
									.findElements(By.tagName("td"));

							if (cells != null) {

								if (cells.size() > 0) {
									for (int j = 0; j < cells.size(); j++) {

										if (isDirectRow) {
											col = cells
													.get(findindx)
													.findElement(
															By.tagName(findTagName));
										} else {
											col = cells
													.get(findindx)
													.findElement(
															By.tagName(findTagName));
										}

										if (col != null) {

											d = col.getText().trim()
													.toLowerCase();

											if (d.equalsIgnoreCase(findItem
													.trim().toLowerCase())) {

												System.out
														.println(d
																+ " Found in Row index "
																+ (i + 1));

												for (int k = i; j < Constants.gridTableData
														.size(); k++) {
													cells = Constants.gridTableData
															.get(i)
															.findElements(
																	By.tagName("td"));
													rowCellSize = cells.size();

													for (int l = 0; l < rowCellSize; l++) {
														col = null;
														// ColIndx=0;
														col = cells
																.get(ColIndx)
																.findElement(
																		By.tagName(colTagName));

														if (col != null) {

															d = col.getText()
																	.trim()
																	.toLowerCase();
															col.click();
															System.out
																	.println("Item click "
																			+ d
																			+ " of "
																			+ findItem);
															sts = true;

															d = "Found";
															break;

														}

														// l Loop
													}

													if (!d.isEmpty()) {
														if (d.equals("Found")) {
															break;
														}
													}

													// k Loop
												}

												if (!d.isEmpty()) {
													if (d.equals("Found")) {
														break;
													}
												}
											}

										}

										if (!d.isEmpty()) {
											break;

										}

									}

									// j Loop

									if (!d.isEmpty()) {
										if (d.equals("Found")) {
											break;
										}
									}

								}

								if (!d.isEmpty()) {
									if (d.equals("Found")) {
										break;
									}

								}

							}

							// i Loop
						}

					}

				}

			}

		} catch (Exception e) {
			System.out.println("Exp Found in  tabelItemClick");

		}

		if (isDirectRow) {
			if (sts) {
				System.out.println("PASSED :  Directly Row " + RowIdx
						+ " tabelItemClick");
			} else {
				System.out.println("FAILED :  Directly Row " + RowIdx
						+ " tabelItemClick");
			}
		} else {

		}
		if (sts) {
			System.out.println("PASSED : " + findItem + " tabelItemClick");
		} else {
			System.out.println("FAILED : " + findItem + " tabelItemClick");
		}

		return sts;

	}

	public static boolean clickGridTableActionItem(String selector,
			String selectorValue, String parameter) {
		boolean sts = false, isDirectRow = false;
		String findItem = null, findTagName = "span", colTagName = null;
		int findindx = 0, ColIndx = 0, RowIdx = 0;
		String d = "", btnId = "";
		int rowCellSize = 0;

		String actionItems = "";
		WebElement ele = null;
		List<WebElement> eles = null;
		 //selectorValue = "xvcvxcv xcvxc";
		// selector="link : 1";
		// parameter="link : 1";

		selectorValue = Utilities.isParameterAuto(selectorValue);
		try {

			if (Utilities.isNumeric(selectorValue)) {
				RowIdx = Integer.parseInt(selectorValue);
				isDirectRow = true;
			} else {
				findItem = selectorValue;
			}

			if (!selector.isEmpty()) {
				String datas[] = Utilities.splitTextBy(selector, ":");
				if (datas.length >= 2) {

					findTagName = datas[0].toString().replaceAll("^\\s+", "")
							.replaceAll("\\s+$", "");
					if (findTagName.equalsIgnoreCase("link")) {
						findTagName = "a";
					} else {
						findTagName = "span";
					}

					if (Utilities.isNumeric(datas[1].toString().trim())) {
						findindx = Integer.parseInt(datas[1].toString().trim());
					}

				}
			}

			String datas2[] = Utilities.splitTextBy(parameter, ":");

			if (datas2.length >= 2) {

				if (Utilities.isNumeric(datas2[0].toString().trim())) {
					ColIndx = Integer.parseInt(datas2[0].toString().trim());
				}

				btnId = datas2[1].toString().trim().replaceAll("^\\s+", "")
						.replaceAll("\\s+$", "");
				
			}

			

			Thread.sleep(3000);
			List<WebElement> cells = null;
			if (Constants.gridTableData != null) {

				WebElement col = null;
				if (Constants.gridTableData.size() > 0) {

					if (isDirectRow) {

						System.out.println("Directly Finding Row : " + RowIdx);

						cells = Constants.gridTableData.get(RowIdx - 1)
								.findElements(By.tagName("td"));

						if (cells != null) {

							if (cells.size() > 0) {

								col = null;
								col = cells.get(ColIndx).findElement(
										By.tagName("button"));

								if (col != null) {
									Thread.sleep(500);
									d = col.getAttribute("id");
									col.click();
									Thread.sleep(500);
									System.out.println("Item click " + d
											+ " of " + findItem);

									col = null;
									col = cells.get(ColIndx).findElement(
											By.tagName("ul"));

									if (col != null) {
										eles = col.findElements(By
												.tagName("li"));

										if (eles != null) {

											for (int m = 0; m < eles.size(); m++) {

												actionItems = eles.get(m)
														.getText();
												System.out.println(actionItems);

												if (actionItems.trim()
														.equalsIgnoreCase(
																btnId.trim())) {

													eles.get(m).click();
													Thread.sleep(500);
													System.out
															.println("Item click "
																	+ actionItems
																	+ " of Row "
																	+ RowIdx);
													sts = true;

													d = "Found";
													break;
												}

											}

										}
									}

								}

							}
						}

					} else {
						System.out.println("Finding Row item  " + findItem
								+ " on Column  " + findindx);

						for (int i = 0; i < Constants.gridTableData.size(); i++) {
							cells = Constants.gridTableData.get(i)
									.findElements(By.tagName("td"));

							if (cells != null) {

								if (cells.size() > 0) {
									for (int j = 0; j < cells.size(); j++) {

										col = null;
										col = cells.get(findindx).findElement(
												By.tagName(findTagName));

										if (col != null) {

											d = col.getText().trim()
													.toLowerCase();

											if (d.equalsIgnoreCase(findItem
													.trim().toLowerCase())) {

												System.out
														.println(d
																+ " Found in Row index "
																+ (i + 1));

												for (int k = i; j < Constants.gridTableData
														.size(); k++) {
													cells = Constants.gridTableData
															.get(i)
															.findElements(
																	By.tagName("td"));
													rowCellSize = cells.size();

													for (int l = 0; l < rowCellSize; l++) {
														col = null;
														col = cells
																.get(ColIndx)
																.findElement(
																		By.tagName("button"));

														if (col != null) {
															Thread.sleep(500);
															d = col.getAttribute("id");
															col.click();
															Thread.sleep(500);
															System.out
																	.println("Item click "
																			+ d
																			+ " of "
																			+ findItem);

															col = null;
															col = cells
																	.get(ColIndx)
																	.findElement(
																			By.tagName("ul"));

															if (col != null) {
																eles = col
																		.findElements(By
																				.tagName("li"));

																if (eles != null) {

																	for (int m = 0; m < eles
																			.size(); m++) {

																		actionItems = eles
																				.get(m)
																				.getText();
																		System.out
																				.println(actionItems);

																		if (actionItems
																				.trim()
																				.equalsIgnoreCase(
																						btnId.trim())) {

																			eles.get(
																					m)
																					.click();
																			Thread.sleep(500);
																			System.out
																					.println("Item click "
																							+ actionItems
																							+ " of "
																							+ findItem);
																			sts = true;

																			d = "Found";
																			break;
																		}

																	}

																}
															}

														}
														if (!d.isEmpty()) {
															if (d.equals("Found")) {
																break;
															}
														}
														// l Loop
													}

													if (!d.isEmpty()) {
														if (d.equals("Found")) {
															break;
														}
													}

													// k Loop
												}

												if (!d.isEmpty()) {
													if (d.equals("Found")) {
														break;
													}
												}
											}

										}

										if (!d.isEmpty()) {
											break;

										}

									}

									// j Loop

									if (!d.isEmpty()) {
										if (d.equals("Found")) {
											break;
										}
									}

								}

								if (!d.isEmpty()) {
									if (d.equals("Found")) {
										break;
									}

								}

							}

							//i Loop
						}

					}

				}

			}

		} catch (Exception e) {
			System.out.println("Exp Found in  tabelItemClick");

		}

		if (isDirectRow) {
			if (sts) {
				System.out.println("PASSED :  Directly Row " + RowIdx
						+ " tabelItemClick");
			} else {
				System.out.println("FAILED :  Directly Row " + RowIdx
						+ " tabelItemClick");
			}
		} else {

		}
		if (sts) {
			System.out.println("PASSED : " + findItem + " tabelItemClick");
		} else {
			System.out.println("FAILED : " + findItem + " tabelItemClick");
		}

		return sts;

	}

	public static boolean clickAuditLink(String selector, String selectorValue,
			String parameter) {
		boolean d = false, isWaited = false;
		int RowIdx = 0, linkIdx = 0;

		WebElement ele = null;
		try {

			if (!selector.isEmpty()) {

				if (Utilities.isNumeric(selector)) {
					RowIdx = Integer.parseInt(selector);
				}

			}

			if (!selectorValue.isEmpty()) {

				if (Utilities.isNumeric(selectorValue)) {
					linkIdx = Integer.parseInt(selectorValue);
				}

			}

			Thread.sleep(1500);
			List<WebElement> cells = null, divs = null, links = null;
			if (Constants.listTableData != null) {

				WebElement div = null;
				if (Constants.listTableData.size() > 0) {

					/*
					 * cells = Constants.listTableData.get(RowIdx - 1)
					 * .findElements(By.tagName("td"));
					 * 
					 * if (cells != null) {
					 */

					cells = Constants.listTableData.get(RowIdx - 1)
							.findElements(By.tagName("td"));

					if (cells != null) {

						divs = Constants.listTableData.get(RowIdx - 1)
								.findElements(By.tagName("div"));

						// if (links != null) {
						//
						// links.get(linkIdx).click();
						//
						// }

						// div = cells.get(0).findElement(
						// By.tagName("div"));
						//
						if (divs != null) {

							links = divs.get(RowIdx - 1).findElements(
									By.tagName("a"));

							if (links != null) {

								links.get(linkIdx - 1).click();

								d = true;

							}
							//
						}

					}

				}
			}

		} catch (Exception e) {
			System.out.println("EXCEPTION : clickAuditLink");
		}

		if (d) {
			System.out.println("PASSED : audit link " + selectorValue
					+ " is clicked ");
		} else {

			System.out.println("FAILED : audit " + selectorValue
					+ " is clicked ");

		}

		return d;
	}

}
