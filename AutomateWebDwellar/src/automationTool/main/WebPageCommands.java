package automationTool.main;

import java.util.ArrayList;
import java.util.List;

import jnr.ffi.Struct.id_t;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import bsh.util.Util;

public class WebPageCommands {

	public static void load(String url) {
		boolean d = false, isInPge = false;
		try {
			// url="Base : /login";
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

		} catch (Exception e) {
			System.out.println("EXCEPTION : load");
		}

		if (d) {
			System.out.println("PASSED : Url is set sucessfully");
		} else {
			System.out.println("FAILED : Failed to set Url");
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
					data = data.replace("Your Task Code is ", "");
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

	public static boolean loadGridTable(String selector, String selectorValue,
			String parameter) {
		boolean d = false, isIdAppear = false, isTableAppear = false;
		int sec = 0, attempt = 1;

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
						//isTableAppear = false;

//						while (!isTableAppear) {
//							isTableAppear = waitElement("className",
//									"k-selectable", sec);
//							isTableAppear=true;
							//if (isTableAppear) {

								ele = ele.findElement(By
										.className("k-selectable"));
								if (ele != null) {

									table = ele.findElements(By.tagName("tr"));
									if (table != null) {
										Constants.gridTableData = null;
										Constants.gridTableData = table;
										isTableAppear = true;
									}

								} else {
									isIdAppear = false;
									//Utilities.driver.navigate().refresh();
									//isTableAppear = false;
								}

//							} else {
//								isTableAppear = true;
//								isIdAppear = false;
//								//Utilities.driver.navigate().refresh();
//							}

						//}

					} else {
						//Utilities.driver.navigate().refresh();
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

										if (d.equalsIgnoreCase(parameter)) {

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

				System.out.println("Row (" + i + ") "
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

	public static boolean checkRowValue(String selector, String selectorValue,
			String parameter) {
		boolean d = false, isDataFound1 = false, isDataFound2 = false;
		int i = 0;
		String gridData = "";
		try {
			// selectorValue="15";
			Constants.testResults[2] = null;
			parameter = Utilities.isParameterAuto(parameter);
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
			//selector="match";
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
			System.out.println("PASSED : " + parameter + " (is equal To)  "
					+ gridData);
		} else {
			System.out.println("FAILED : " + parameter + " (is not equal To)  "
					+ gridData);
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

					findTagName = datas[0].toString();
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

				colTagName = datas2[0].toString().trim();
				if (colTagName.equalsIgnoreCase("link")) {
					colTagName = "a";
				}
				if (colTagName.equalsIgnoreCase("checkbox")) {
					colTagName = "input";
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
		String findItem = null, findTagName = "span", btnId = "";
		int findindx = 0, ColIndx = 0, RowIdx = 0;
		String d = "", actionItems = "";
		WebElement ele = null;
		List<WebElement> eles = null;

		boolean isApper = false;
		int rowCellSize = 0;
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

					findTagName = datas[0].toString();
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

				btnId = datas2[1].toString().trim();

			}

			Thread.sleep(3000);
			List<WebElement> cells = null;
			if (Constants.gridTableData != null) {

				WebElement col = null;
				if (Constants.gridTableData.size() > 0) {

					if (isDirectRow) {

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
			System.out.println("Exp Found in  tableItemActionClick");

		}

		if (isDirectRow) {
			if (sts) {
				System.out.println("PASSED : Directly Row " + RowIdx
						+ " tabelItemClick");
			} else {
				System.out.println("FAILED : Directly Row " + RowIdx
						+ " tabelItemClick");
			}
		} else {
			if (sts) {
				System.out.println("PASSED : " + findItem
						+ " tableItemActionClick");
			} else {
				System.out.println("FAILED : " + findItem
						+ " tableItemActionClick");
			}
		}

		return sts;

	}

}
