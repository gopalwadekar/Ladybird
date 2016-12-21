package automationTool.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

import automationTool.excel.AdvancedExcelWriter;

import com.google.common.base.Predicate;

public class Utilities {

	public static WebDriver driver = null;
	public static PrintLog LOG = null;
	public static SendEmail Email = null;
	public static WebDriverWait wait = null;
	public static JavascriptExecutor jse = null;
	public static String runTimedata = "", ExecutionId = null;
	public static String[] testCase_lineParams = null;
	public static PrintResult PRINT = null;
	public static long startTime = 0;
	public static long endTime = 0;
	public static String waitingFor = "";
	public static boolean saveExel = false;
	public static final int RANDOM_MOBILE_NUMBERS = 8;
	public static final int RANDOM_PHONE_NUMBERS1 = 7;
	public static final int RANDOM_PHONE_NUMBERS2 = 8;
	public static boolean isRootLoaded = false, isNaviLoaded;

	public static void setPause(int sec) {

		try {
			System.out.print("Please Wait " + sec + " seconds ");

			for (int i = 0; i < sec; i++) {

				System.out.print(".");
				Thread.sleep((i + 1) * 1000);
			}
			System.out.println();

		} catch (Exception e) {
		}

	}

	public static String[] splitTextBy(String text, String by) {
		String[] d = null;
		try {
			if (!text.isEmpty() && !by.isEmpty()) {
				d = text.split(by);
			}

		} catch (Exception e) {
		}

		return d;

	}

	public static boolean loadBroweser(String browserName) {
		boolean d = false;
		try {

			if (browserName.equalsIgnoreCase("Chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("chrome.switches", "--disable-extensions");
				System.setProperty("webdriver.chrome.driver",
						"chromedriver.exe");
				driver = null;
				driver = new ChromeDriver(options);
				if (driver != null) {
					driver.manage().window().maximize();
					jse = (JavascriptExecutor) driver;
					PRINT = PrintResult.createInstanceOfPrintTestCaseResult();
					Email = new SendEmail();
					d = true;
				}

			}

		} catch (Exception e) {
			System.out.println("FAILED :Exception Found : " + e.getMessage());
		}

		if (d) {
			System.out.println("PASSED : Chrome Driver Found!");
		} else {
			System.out.println("FAILED :Chrome Driver Not Found!");
		}
		return d;

	}

	public static WebElement getWebElement(String selector, String seletorValue) {
		WebElement g = null;
		try {
			System.out.println("Finding webElement by " + selector
					+ " with value " + seletorValue);

			if (Utilities.waitingFor.equals("waitForVisible")) {

				switch (selector) {
				case "id":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.id(seletorValue)));
					break;
				case "className":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By
									.className(seletorValue)));
					break;
				case "cssSelector":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By
									.cssSelector(seletorValue)));
					break;
				case "linkText":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By
									.linkText(seletorValue)));
					break;
				case "name":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.name(seletorValue)));
					break;
				case "partialLinkText":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By
									.partialLinkText(seletorValue)));
					break;
				case "tagName":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By
									.tagName(seletorValue)));
					break;
				case "xpath":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath(seletorValue)));
					break;

				default:
					System.out
							.println("#######################Invalid WebElement Selector Process##########################");
					break;
				}

			} else if (Utilities.waitingFor.equals("waitForClickable")) {

				switch (selector) {
				case "id":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.id(seletorValue)));
					break;
				case "className":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By
									.className(seletorValue)));
					break;
				case "cssSelector":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By
									.cssSelector(seletorValue)));
					break;
				case "linkText":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By
									.linkText(seletorValue)));
					break;
				case "name":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.name(seletorValue)));
					break;
				case "partialLinkText":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By
									.partialLinkText(seletorValue)));
					break;
				case "tagName":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By
									.tagName(seletorValue)));
					break;
				case "xpath":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath(seletorValue)));
					break;

				default:
					System.out
							.println("#######################Invalid WebElement Selector Process##########################");
					break;
				}

			}

		} catch (Exception e) {
			System.out.println("Exception: Finding webElement by " + selector
					+ " with value " + seletorValue);
			g = null;
		}
		return g;
	}

	public static WebElement getWebElementWithElemet(WebElement ele,
			String selector, String seletorValue) {
		WebElement g = null;
		try {
			System.out.println("Finding webElement by " + selector
					+ " with value " + seletorValue);

			switch (selector) {
			case "id":
				g = ele.findElement((By.id(seletorValue)));
				break;
			case "className":
				g = ele.findElement((By.className(seletorValue)));
				break;
			case "cssSelector":
				g = wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.cssSelector(seletorValue)));
				break;
			case "linkText":
				g = ele.findElement((By.linkText(seletorValue)));
				break;
			case "name":
				g = ele.findElement((By.name(seletorValue)));
				break;
			case "partialLinkText":
				g = ele.findElement((By.partialLinkText(seletorValue)));
				break;
			case "tagName":
				g = ele.findElement((By.tagName(seletorValue)));
				break;
			case "xpath":
				g = ele.findElement((By.xpath(seletorValue)));
				break;

			default:
				System.out
						.println("#######################Invalid WebElement By WebElement Selector Process##########################");
				break;
			}

		} catch (Exception e) {
			System.out.println("Exception: Finding webElement by " + selector
					+ " with value " + seletorValue);
			g = null;
		}
		return g;
	}

	public static List<WebElement> getWebElements(WebElement ele,
			String selector, String seletorValue) {
		List<WebElement> d = null;
		try {
			System.out.println("Finding webElement by " + selector
					+ " with value " + seletorValue);

			switch (selector) {
			case "id":
				d = ele.findElements(By.id(seletorValue));
				break;
			case "className":
				d = ele.findElements(By.className(seletorValue));
				break;
			case "cssSelector":
				d = ele.findElements(By.cssSelector(seletorValue));
				break;
			case "linkText":
				d = ele.findElements(By.linkText(seletorValue));
				break;
			case "name":
				d = ele.findElements(By.name(seletorValue));
				break;
			case "partialLinkText":
				d = ele.findElements(By.partialLinkText(seletorValue));
				break;
			case "tagName":
				d = ele.findElements(By.tagName(seletorValue));
				break;
			case "xpath":
				d = ele.findElements(By.xpath(seletorValue));
				break;

			default:
				System.out
						.println("#######################Invalid WebElements Selector Process##########################");
				break;
			}

		} catch (Exception e) {
			System.out.println("Exception: Finding webElements by " + selector
					+ " with value " + seletorValue);
			d = null;
		}
		return d;
	}

	public static WebElement getWebElementForWait(String selector,
			String seletorValue) throws AutomationException {
		WebElement g = null;
		try {
			if (Utilities.waitingFor.equals("waitForVisible")) {
				switch (selector) {
				case "id":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.id(seletorValue)));

					break;
				case "className":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By
									.className(seletorValue)));
					break;
				case "cssSelector":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By
									.cssSelector(seletorValue)));
					break;
				case "linkText":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By
									.linkText(seletorValue)));
					break;
				case "name":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.name(seletorValue)));
					break;
				case "partialLinkText":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By
									.partialLinkText(seletorValue)));
					break;
				case "tagName":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By
									.tagName(seletorValue)));
					break;
				case "xpath":
					g = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath(seletorValue)));
					break;

				default:
					g = null;
					break;
				}

			} else if (Utilities.waitingFor.equals("waitForClickable")) {
				switch (selector) {
				case "id":
					g = wait.until(ExpectedConditions.elementToBeClickable(By
							.id(seletorValue)));
					break;
				case "className":
					g = wait.until(ExpectedConditions.elementToBeClickable(By
							.className(seletorValue)));
					break;
				case "cssSelector":
					g = wait.until(ExpectedConditions.elementToBeClickable(By
							.cssSelector(seletorValue)));
					break;
				case "linkText":
					g = wait.until(ExpectedConditions.elementToBeClickable(By
							.linkText(seletorValue)));
					break;
				case "name":
					g = wait.until(ExpectedConditions.elementToBeClickable(By
							.name(seletorValue)));
					break;
				case "partialLinkText":
					g = wait.until(ExpectedConditions.elementToBeClickable(By
							.partialLinkText(seletorValue)));
					break;
				case "tagName":
					g = wait.until(ExpectedConditions.elementToBeClickable(By
							.tagName(seletorValue)));
					break;
				case "xpath":
					g = wait.until(ExpectedConditions.elementToBeClickable(By
							.xpath(seletorValue)));
					break;

				default:
					g = null;
					break;
				}
			}
		}

		catch (Exception e) {

		}
		return g;
	}

	public static String getExecutionTime(long ST, long ET) {

		long d = (long) ((ET - ST) / 1000d);

		String time = "";
		if (d <= 60) {
			if (d <= 1) {
				time = d + " second";
			} else {
				time = d + " seconds";
			}

		} else if (d > 60 && d < 3600) {
			d = (ET - ST) / (1000 * 60) % 60;
			if (d == 1) {
				time = d + " minute";
			} else {
				time = d + " minutes";
			}

		} else if (d >= 3600) {
			d = (ET - ST) / (1000 * 60 * 60) % 24;
			if (d == 1) {
				time = d + " hour";
			} else {
				time = d + " hours";
			}

		}
		return "[ " + time + " ]";
	}

	public static String getCurrentURL() {
		String d = "";
		try {

			d = driver.getCurrentUrl();
		} catch (Exception e) {

			if (e instanceof AutomationException) {
				System.out.println("AutomationException");
			} else {
				System.out.println("###########################");
				System.out.println("EXCEPTION : getCurrentURL ");
				System.out.println("Error : " + e.getMessage());

				if (e.getMessage().contains("unexpected alert open")) {

					if (ignoreAlertPop()) {
						d = getCurrentURL();
					}

				}
				d = getCurrentURL();
				System.out.println("###########################");
				d = "";
			}

		}
		return d;

	}

	public static Predicate<WebDriver> pageLoaded = new Predicate<WebDriver>() {

		@Override
		public boolean apply(WebDriver input) {
			return ((JavascriptExecutor) input).executeScript(
					"return document.readyState").equals("complete");
		}

	};

	public static boolean isPageLoaded(String url) {
		boolean d = false, isUrlMatched = false;
		try {
			FluentWait<WebDriver> urlWait = null;
			while (!d) {
				System.out.println("Waiting for Page Load.....");

				try {
					if (pageLoaded != null) {

						if (url.equals(getCurrentURL())) {
							isUrlMatched = true;
							urlWait = new FluentWait<WebDriver>(driver);
							if (urlWait != null) {
								urlWait.until(pageLoaded);
								d = true;
							}
						} else {
							isUrlMatched = false;
							d = true;
						}

					}
				} catch (Exception e) {
					if (e instanceof AutomationException) {
						System.out.println("AutomationException");
					}

					// throw new AutomationException(e, "", "");

					// WebPageCommands.load(url);
				}

				if (!isUrlMatched) {
					d = false;
				}

				if (d) {
					System.out.println("Page Loaded Sucessfully");
					break;
				} else {
					System.out.println("Failed to Load Page");
				}

			}

		} catch (Exception e) {
			System.out.println("Exception in isPageLoaded ");
			d = false;

		}

		return d;
	}

	public static String autoGenerateLetters(int length) {
		String alphabet = new String(
				"abcdefghijklABCDEFGHIJKLMNOPQRSTUVWXYZmnopqrstuvwxyz"); // 9
		int n = alphabet.length(); // 10

		String result = new String();
		Random r = new Random(); // 11

		for (int i = 0; i < length; i++)
			// 12
			result = result + alphabet.charAt(r.nextInt(n)); // 13

		return result;
	}

	public final static String autoMobile_PhoneNumber(long len, char N) {
		String dNum = "";
		String prefixNum = "";
		if (N == 'M') {
			prefixNum = String.valueOf((int) (Math.random() * (70 - 99)) + 99);
		} else {
			prefixNum = "022";
		}

		dNum = prefixNum
				+ String.valueOf((long) (Math.random() * ((long) Math.pow(10,
						len - 1) * 9)) + (long) Math.pow(10, len - 1) * 1);
		return dNum;
	}

	public final static String autoRandomEmail(String fn, String ln, String dm) {
		return fn + String.valueOf((int) (Math.random() * (70 - 99)) + 99) + ln
				+ "@" + dm;// + ".com";

	}

	public static String isParameterAuto(String parameter) {
		String d = "";
		String ary[] = null;
		try {

			// parameter = "READ : iframe";
			// ;

			if (parameter.startsWith("auto")) {
				ary = Utilities.splitTextBy(parameter, "-");
				int len = 0;
				if (ary.length <= 2) {
					parameter = ary[0];
					if (parameter.equals("autoLetter")) {

						try {
							len = Integer.parseInt(ary[1]);
						} catch (Exception e) {
							len = 0;
						}

						if (len > 0) {
							d = autoGenerateLetters(len);
						} else {
							d = autoGenerateLetters(5);
						}

					} else if (parameter.equals("autoPhoneNumber1")) {
						d = autoMobile_PhoneNumber(RANDOM_PHONE_NUMBERS1, 'P');
					} else if (parameter.equals("autoPhoneNumber2")) {
						d = autoMobile_PhoneNumber(RANDOM_PHONE_NUMBERS2, 'P');
					} else if (parameter.equals("autoMobileNumber")) {
						d = autoMobile_PhoneNumber(RANDOM_MOBILE_NUMBERS, 'M');
					} else if (parameter.equals("autoEmail1")) {
						String dmn = "";
						try {
							dmn = ary[1];
						} catch (Exception e) {
							dmn = "gmail.com";
						}
						d = autoRandomEmail(autoGenerateLetters(3),
								autoGenerateLetters(2), dmn);
					} else if (parameter.equals("autoEmail2")) {
						String dmn = "";
						try {
							dmn = ary[1];
						} catch (Exception e) {
							dmn = "yahoo.com";
						}
						d = autoRandomEmail(autoGenerateLetters(2),
								autoGenerateLetters(3), dmn);
					} else if (parameter.equals("autoPin")) {
						d = autoMobile_PhoneNumber(RANDOM_PHONE_NUMBERS1, 'P');
					} else if (parameter.equals("autoFacebook")) {

						try {
							len = Integer.parseInt(ary[1]);
						} catch (Exception e) {
							len = 0;
						}

						if (len > 0) {
							d = "https://www.facebook.com/"
									+ autoGenerateLetters(len);
						} else {
							d = "https://www.facebook.com/"
									+ autoGenerateLetters(5);
						}
					} else if (parameter.equals("autoTwitter")) {

						try {
							len = Integer.parseInt(ary[1]);
						} catch (Exception e) {
							len = 0;
						}

						if (len > 0) {
							d = "https://www.twitter.com/"
									+ autoGenerateLetters(len);
						} else {
							d = "https://www.twitter.com/"
									+ autoGenerateLetters(5);
						}
					}

					else if (parameter.equals("autoLinkedIn")) {

						try {
							len = Integer.parseInt(ary[1]);
						} catch (Exception e) {
							len = 0;
						}

						if (len > 0) {
							d = "https://www.linkedin.com/"
									+ autoGenerateLetters(len);
						} else {
							d = "https://www.linkedin.com/"
									+ autoGenerateLetters(5);
						}
					}

				}

			} else if (parameter.startsWith("READ")
					|| parameter.startsWith("read")) {

				ary = Utilities.splitTextBy(parameter, ":");
				if (ary.length <= 2) {
					d = getReadExcelValue(ary[1]);
				}
			} else if (parameter.startsWith("Date")
					|| parameter.startsWith("DATE")) {

				ary = Utilities.splitTextBy(parameter, "-");
				if (ary.length <= 2) {
					d = ary[1];
				}
			} else if (parameter.startsWith("READs")
					|| parameter.startsWith("reads")) {

				ary = Utilities.splitTextBy(parameter, ":");
				if (ary.length <= 2) {
					d = getReadExcelValue(ary[1]);
				}
			}

			else {
				d = parameter;
			}

		} catch (Exception e) {
			d = parameter;
		}

		return d;

	}

	public static boolean ignoreAlertPop() {

		try {
			Alert alert = driver.switchTo().alert();
			if (alert != null) {
				System.out.println(alert.getText());
				alert.accept();
				setPause(1);
				driver.switchTo().defaultContent();
				setPause(1);
				return true;

			}

		} catch (Exception e) {
			System.out.println("Exception on finding alert");
			return false;

		}
		return true;
	}

	public static void toSave(boolean toSave, String[] data) {
		boolean d = false;
		String val = "";
		try {
			if (toSave) {

				d = new AdvancedExcelWriter().saveInExcel(data);
				if (d) {
					d = true;
				}
			}

		} catch (Exception e) {
			System.out.println("Exception isSAVE");
		}

		if (d) {
			System.out.println("PASSED : " + val + " is save in excel");
		} else {
			System.out.println("FAILED : " + val + " is save in excel");
		}

	}

	public static boolean isNumeric(String name) {
		char[] chars = name.toCharArray();
		for (char c : chars) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	public static WebElement getKendoIFrameBody(String selector,
			String seletorValue) {

		try {

			WebElement iframe_container = getWebElement(selector, seletorValue);
			if (iframe_container != null) {
				Thread.sleep(1500);
				driver.switchTo().frame(iframe_container);

				WebElement bodytag = driver.findElement(By.cssSelector("body"));
				Thread.sleep(500);
				if (bodytag == null) {
					System.out
							.println("FAILED : WebElement (KendoWebEditor) Not Found");
					return null;
				} else {

					System.out
							.println("PASSED : WebElement (KendoWebEditor) Sucessfully Loaded");
					return bodytag;
				}
			}

		} catch (Exception e) {
			return null;
		}
		return null;

	}

	public static void switchDriverToDefault() {

		try {
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			System.out.println("Exception in switching");
		}

	}

	public static String getReadExcelValue(String dataName) {
		String dataVal = "";
		String sheetVal = "";
		int indx = 0;
		try {

			if (!dataName.isEmpty()) {
				dataName = dataName.trim();
				if (Constants.readDataSheet != null) {

					if (isNumeric(dataName)) {

						indx = Integer.parseInt(dataName);
						indx = indx - 1;
						dataVal = Constants.readDataSheet[indx][1].toString()
								.trim();

					} else if (dataName.contains(",")) {

						String datas[] = splitTextBy(dataName, ",");
						if (datas != null) {

							for (int j = 0; j < datas.length; j++) {
								dataName = datas[j].toLowerCase().trim();

								for (int i = 0; i < Constants.readDataSheetCount; i++) {

									sheetVal = Constants.readDataSheet[i][0]
											.toString().trim().toLowerCase();

									if (sheetVal.equalsIgnoreCase(dataName)) {

										dataVal += Constants.readDataSheet[i][1]
												.toString().trim() + " ";
										break;
									}

								}

							}
						}

					} else {
						for (int i = 0; i < Constants.readDataSheetCount; i++) {

							sheetVal = Constants.readDataSheet[i][0].toString()
									.trim().toLowerCase();
							dataName = dataName.toLowerCase().trim();

							if (sheetVal.equalsIgnoreCase(dataName)) {

								dataVal = Constants.readDataSheet[i][1]
										.toString().trim();
								break;
							}

						}

					}

				}
			}

		} catch (Exception e) {
			System.out.println("Exception in getReadExcelValue");
		}

		return dataVal;

	}

	public static String readRootExcelValue(String dataName) {
		String dataVal = "";
		String sheetVal = "";
		int indx = 0;
		try {

			if (!dataName.isEmpty()) {
				dataName = dataName.trim();
				if (Constants.rootSheet != null) {

					if (isNumeric(dataName)) {

						indx = Integer.parseInt(dataName);
						// indx = indx - 1;
						dataVal = Constants.rootSheet[indx][Constants.rootDataValue]
								.toString().trim();

					} else if (dataName.contains(",")) {

						String datas[] = splitTextBy(dataName, ",");
						if (datas != null) {

							for (int j = 0; j < datas.length; j++) {
								dataName = datas[j].toLowerCase().trim();

								for (int i = 0; i < Constants.rootSheetCount; i++) {

									sheetVal = Constants.rootSheet[i][Constants.rootDataValue]
											.toString().trim().toLowerCase();

									if (sheetVal.equalsIgnoreCase(dataName)) {

										dataVal += Constants.rootSheet[i][Constants.rootDataValue]
												.toString().trim() + " ";
										break;
									}

								}

							}
						}

					} else {
						for (int i = 0; i < Constants.readDataSheetCount; i++) {

							sheetVal = Constants.rootSheet[i][0].toString()
									.trim().toLowerCase();
							dataName = dataName.toLowerCase().trim();

							if (sheetVal.equalsIgnoreCase(dataName)) {

								dataVal = Constants.rootSheet[i][1].toString()
										.trim();
								break;
							}

						}

					}

				}
			}

		} catch (Exception e) {
			System.out.println("Exception in readRootExcelValue");
		}

		return dataVal;

	}

	public static String createDate(String selector, String selectorValue,
			String parameter) {
		String d = "";

		try {

			// selector="dd/MM/YY";
			parameter = Utilities.isParameterAuto(parameter);
			if (parameter.toLowerCase().equalsIgnoreCase("current")) {
				d = new SimpleDateFormat(selector).format(new Date());
			} else {
				d = parameter;
			}
			Utilities.toSave(Utilities.saveExel, new String[] { selectorValue,
					d });
		} catch (Exception e) {
			System.out.println("FAILED : EXCEPTION FOUND ON CREATE_DATE");
		}

		if (!d.isEmpty()) {
			System.out.println("PASSED : DATE IS CREATED");
		} else {
			System.out.println("FAILED : DATE IS NOT CREATED");
		}

		return d;

	}

	public static String createTime() {
		String d = "";
		try {
			d = new SimpleDateFormat("hh : mm a").format(new Date());
		} catch (Exception e) {
		}
		return d;
	}

	public static boolean isValidDate(String dateString) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			df.parse(dateString);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public static String calculateAge(String selectorValue, String parameter) {
		String d = "";
		try {
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

			parameter = isParameterAuto(parameter);
			parameter = parameter.replaceAll("^\\s+", "").replaceAll("\\s+$",
					"");
			// parameter = removeLRSpace(parameter);
			System.out.println("calucating age form " + parameter);
			if (!parameter.isEmpty()) {

				d = ""
						+ ((Integer.parseInt(new SimpleDateFormat("yyyy")
								.format(new Date()))) - Integer
								.parseInt(parameter.substring(parameter
										.length() - 4)));
			}

			Utilities.toSave(Utilities.saveExel, new String[] { selectorValue,
					d });

		} catch (Exception e) {
			// TODO: handle exception
		}

		if (d.isEmpty()) {
			d = "age not found properly";
			System.err.println("Age : " + d);
			d = "";
		} else {
			System.out.println("Age : " + d);
		}

		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

		return d;
	}

	public static String removeLRSpace(String myString) {
		myString.replaceAll("^\\s+", "");// left
		myString.replaceAll("\\s+$", "");// right
		return myString;
	}

	public static void autoExitProgram() {
		try {

			System.out.println("\n\n\n");
			System.out.println(Constants.setScriptsMessages());
			System.out.println(SqlConnection
					.getScriptsResultsCount(ExecutionId));

			System.out.println("_________________________________________");
			System.out.println("=========================================");
			System.out.println("-----------------------------------------");
			System.out.println("=========================================");
			System.out.println("_________________________________________");

			System.out.println("Press Enter to exit Program.....");
			System.out.println("\n\n");
			String d = "";
			while (d.isEmpty()) {
				d = new BufferedReader(new InputStreamReader(System.in))
						.readLine();
				if (d.isEmpty() || !d.isEmpty()) {
					driver.close();
					Runtime.getRuntime().exec(
							"taskkill /F /IM chromedriver.exe");
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("Exception while Exiting Program");
		}

	}

}
