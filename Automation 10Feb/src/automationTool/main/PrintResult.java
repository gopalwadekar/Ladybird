package automationTool.main;

public class PrintResult {

	public static boolean insertQueryStatus = true;
	public static String insertConnectionString = "", insertFor = "";
	public static String printOutPut = "", printTestModule = "",
			printTestSubModule = "", printTestScenario = "",
			printTestCase = "", printTestCaseId = "", printTestDataId = "",
			printTestData = "", printExpectedResult = "",
			printActualResult = "", printTestResult = "", printDBSts = "",
			printExecutionTime = "";
	public static PrintResult d = null;

	public static PrintResult createInstanceOfPrintTestCaseResult() {

		try {
			insertConnectionString = Constants.conStringAutomation;	
			//insertConnectionString = Constants.conStringPreProdFor_W;
			SqlConnection.connString = insertConnectionString;
			d = new PrintResult();

		} catch (Exception e) {
			System.out.println("Exceprtion in createInstanceOfD_Helper ");
		}

		return d;
	}

	public static String getDayNumberSuffix(int day) {
		if (day >= 11 && day <= 13) {
			return day + "th";
		}
		switch (day % 10) {
		case 1:
			return day + "st";
		case 2:
			return day + "nd";
		case 3:
			return day + "rd";
		default:
			return day + "th";
		}
	}

	public static void printResultOutputs(String lineParams[],
			String actualResult, String result, String expectedResult,
			String executionTime) {

		try {

			if (lineParams.length > 0) {

				printTestModule = lineParams[1];
				printTestSubModule = lineParams[2];
				printTestScenario = lineParams[3];
				printTestCaseId = lineParams[4];
				printTestCase = lineParams[5];
				printTestDataId = lineParams[6];
				printTestData = lineParams[7];

				// printExpectedResult = lineParams[8];

			}
			printExpectedResult = expectedResult;
			printActualResult = actualResult;
			printTestResult = result;
			printExecutionTime = executionTime.replace("[ ", "").replace(" ]",
					"");

			printOutPut = "\n======================================================\n";
			printOutPut += ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n";
			printOutPut += "......................................................";
			printOutPut += "\nTest Module      : " + printTestModule;
			printOutPut += "\nTest SubModule   : " + printTestSubModule;
			printOutPut += "\nTest Senario     : " + printTestScenario;
			printOutPut += "\nTest Case Id     : " + printTestCaseId;
			printOutPut += "\nTest Case        : " + printTestCase;
			printOutPut += "\nTest Data Id     : " + printTestDataId;
			printOutPut += "\nTest Data        : " + printTestData;
			printOutPut += "\nExpected Result  : " + printExpectedResult;
			printOutPut += "\nActual Result    : " + printActualResult;
			printOutPut += "\nTest Result      : " + printTestResult;
			printOutPut += "\nExecution Time   : " + printExecutionTime;
			printOutPut += "\n......................................................\n";
			printOutPut += " Inserting TestCases into Database : ";

			System.out.print(printOutPut);
			printOutPut = "";

			// insertQueryStatus = true;
			Thread.sleep(2000);

			// printDBSts = ((!insertQueryStatus) ? "INSERTED" :
			// "NOT INSERTED");

			printDBSts = "";
			//printDBSts="[ NOT INSERTED ]";
			
			 printDBSts = SqlConnection.insertTestResultsInTable(lineParams,
			 printActualResult, printTestResult, printExpectedResult,
			 printExecutionTime);
			 printOutPut = printDBSts;
			System.out.print(printOutPut);
			Thread.sleep(500);
			printOutPut = "";
			printOutPut += "\n......................................................\n";
			printOutPut += "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n";
			printOutPut += "======================================================\n";

			System.out.println(printOutPut);
			printOutPut = "";
			Utilities.LOG.newLine = "\n";
			Utilities.LOG
					.saveOutput("_____________________________________________________________");
			printOutPut = "\n";
			printOutPut += "\nTest Case           : " + printTestCase;
			printOutPut += "\n............................................................";
			printOutPut += "\nExpected Result     : " + printExpectedResult;
			printOutPut += "\nActual Result       : " + printActualResult;
			printOutPut += "\n............................................................";
			printOutPut += "\nTest Result         : " + printTestResult;
			printOutPut += "\tExecution Time    : " + printExecutionTime;
			printOutPut += "\nDatabase Status     : " + printDBSts;
			printOutPut += "\n";
			Utilities.LOG.saveOutput(printOutPut);
			Utilities.LOG.newLine = "\n";

			insertQueryStatus = true;
			printOutPut = "";
			insertFor = "";
			printTestModule = "";
			printTestSubModule = "";
			printTestScenario = "";
			printTestCase = "";
			printTestCaseId = "";
			printTestDataId = "";
			printTestData = "";
			printExpectedResult = "";
			printActualResult = "";
			printTestResult = "";
			printExecutionTime = "";
			printDBSts = "";
			Thread.sleep(1000);

		} catch (Exception e) {
			System.out.println("Exception in printResults");
		}

	}
}
