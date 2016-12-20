package automationTool.main;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

public class Constants {

	public static AutomationException automationException = new AutomationException();

	public static String excelSheet[][] = null;

	// Navigation
	public static String navigationFileLoc = "";
	public static String navigationFolder = "";
	public static String navigationFile = "";
	public static String navigationSheetName = "";

	// rootExcel
	public static int rootData = 1;
	public static int rootDataValue = 2;
	public static String rootSheet[][] = null;
	public static int rootSheetCount = 0;
	public static String rootBaseUrl = "";
	public static String rootUrlEnv = "";
	public static String rootNavLoc = "";
	public static String rootNavFldr = "";
	public static String rootNavFile = "";
	public static String rootNavSheetName = "";
	public static String rootTCLoc = "";
	public static String rootTCFldr = "";
	public static String rootTCFile = "";
	public static String rootEmailId = "";
	public static String rootEmailPswd = "";
	public static String rootEmailTo = "";

	// NaviFlow sheet Columns
	public static int followNo = 0;
	public static int followName = 1;
	public static int followSheetName = 2;
	public static int followTestCaseSheetName = 3;

	public static String getFllowSheetName = "";
	public static String getFllowTestCaseSheetName = "";
	public static String naviFlowSheet[][] = null;
	public static int naviFlowSheetCount = 0;

	// RuntimeNavigate sheet Columns

	public static int runTimeNaviSheetSrNo = 0;
	public static int runTimeNaviSheetStep = 1;
	public static int runTimeNaviSheetKeyword = 2;
	public static int runTimeNaviSheetSelector = 3;
	public static int runTimeNaviSheetSelectorValue = 4;
	public static int runTimeNaviSheetParameter = 5;
	public static int runTimeNaviSheetTestCase = 6;

	public static String naviRuntimeNaviSheet[][] = null;
	public static int naviRuntimeNaviSheetCount = 0;

	// TestCases
	public static String testCaseLoc = "";
	public static String testCaseFolder = "";
	public static String testCaseFile = "";
	public static String testCaseSheetName = "";
	public static String lineTestCase = "";

	// RuntimeTestCase sheet Columns

	public static int runTimeTestCaseSheetSrNo = 0;
	public static int runTimeTestCaseSheetModule = 1;
	public static int runTimeTestCaseSheetSubModule = 2;
	public static int runTimeTestCaseSheetTestSeneraio = 3;
	public static int runTimeTestCaseSheetTestCaseId = 4;
	public static int runTimeTestCaseSheetTestCase = 5;
	public static int runTimeTestCaseSheetTestCaseDataId = 6;
	public static int runTimeTestCaseSheetTestCaseData = 7;
	public static int runTimeTestCaseSheetExpectedResult = 8;

	public static String naviRuntimeNaviTestCasesSheet[][] = null;
	public static int naviRuntimeNaviTestCasesSheetCount = 0;

	public static String getExcelCellData(String sheetName, int rw, int cl) {
		String d = "";
		// System.out.println(":::::::::::::::::::::::::::");
		try {

			if (sheetName != null) {

				if (sheetName.equalsIgnoreCase("naviSheet")) {
					d = Constants.naviRuntimeNaviSheet[rw][cl];
				} else if (sheetName.equalsIgnoreCase("naviTestCaseSheet")) {
					d = Constants.naviRuntimeNaviTestCasesSheet[rw][cl];
				}

				if (d == null) {
					d = "";
				}

			}

		} catch (Exception e) {
			// d = "";
			// System.out.println("###########################");
			// System.out.println("EXCEPTION : getExcelCellData ");
			// System.out.println("Error : " + e.getMessage());
			// System.out.println("###########################");
		}
		//
		// if (!d.isEmpty()) {
		//
		// System.out.println("PASSED : Data in excel cell is not Empty");
		// } else {
		//
		// System.out.println("FAILED : Data in excel cell is Empty");
		// }
		//
		// System.out.println(":::::::::::::::::::::::::::");
		return d;

	}

	public static boolean isTestCaseNoFound = false,
			isSameTestCaseSheet = false;
	public static String actualResult = "";
	public static String testResult = "";
	public static boolean abortIt = false;
	public static String[] testResults = new String[3];

	public static String readDataSheet[][] = null;
	public static int readDataSheetCount = 0;
	public static List<WebElement> gridTableData = null;
	public static List<String> readGridDataList = new ArrayList<String>();

	public static String conStringPreProdFor_W = "jdbc:sqlserver://dwyyeuw8x3.database.secure.windows.net:1433;"
			+ "database=dw-mobileservice-preprod_db;"
			+ "user=dwellarpoc-db-user@dwyyeuw8x3;"
			+ "password=Elemental@123;"
			+ "encrypt=true;"
			+ "trustServerCertificate=false;"
			+ "hostNameInCertificate=*.database.secure.windows.net;"
			+ "loginTimeout=30;";

	public static String conStringPreProdFor_R = "jdbc:sqlserver://dwyyeuw8x3.database.secure.windows.net:1433;"
			+ "database=dw-mobileservice-preprod_db;"
			+ "user=dwellarpoc-db-user@dwyyeuw8x3;"
			+ "password=Elemental@123;"
			+ "encrypt=true;"
			+ "trustServerCertificate=false;"
			+ "hostNameInCertificate=*.database.secure.windows.net;"
			+ "loginTimeout=30;";

	public static String conStringProdFor_R = "jdbc:sqlserver://hb81m9j87d.database.secure.windows.net:1433;"
			+ "database=dw-mobileservice-preprod_db;"
			+ "user=ronly@hb81m9j87d;"
			+ "password=mvf5ydCJZG37;"
			+ "encrypt=true;"
			+ "trustServerCertificate=false;"
			+ "hostNameInCertificate=*.database.secure.windows.net;"
			+ "loginTimeout=30;";
	
	
	 public static int noOfScript = 0;
	 public static List<String> nameOfScript = new ArrayList<String>();
	
	 
	 public static String setScriptsMessages() {
		  String d = "";
		  if (nameOfScript.size() > 0) {
		   d += "\n====================================\n";
		   d += " Script Suite contains " + "[" + nameOfScript.size() + "] "
		     + "Scripts";
		   d += "\n====================================\n";
		   d += "\n***************************\n\n";
		   for (String dmsgs : nameOfScript) {
		    d += dmsgs + "\n";
		   }
		   d += "\n\n***************************\n";
		  }

		  return d;
		 }

}
