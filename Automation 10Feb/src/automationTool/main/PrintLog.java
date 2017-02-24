package automationTool.main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PrintLog {

	public static FileOutputStream fos = null;
	public static byte bytes[] = null;
	public String logFilePath = "";
	public String newLine = "\n";
	public static StringBuilder strToPrint = new StringBuilder();
	public static StringBuilder rowSeparator = new StringBuilder();
	public static int width = 0, diff = 0, paddingSize = 0;
	public static String toPrint = "", lineSeparator = "", padding = "";
	public static String prjHdr = "";

	SimpleDateFormat ft = null;
	String currentDte = "";
	Date dNow = null;
	Calendar cal = null;

	public PrintLog() {
		String fn = "";
		try {

			cal = Calendar.getInstance();
			dNow = new Date();
			ft = new SimpleDateFormat("MMM_YY(hh.mm.s a)");
			currentDte = ft.format(dNow);
			fn = "TA_WebResults-"
					+ getDayNumberSuffix(cal.get(Calendar.DAY_OF_MONTH))
					+ currentDte + ".txt";
			logFilePath = System.getProperty("user.dir")
					+ "\\Logs\\" + fn;

			fos = new FileOutputStream(logFilePath);

		} catch (FileNotFoundException e) {
			System.out.println("File Failed to create :");
			e.printStackTrace();
		}

		if (fos != null) {
			System.out.println("LogFile is created : " + fn);
		}

		dNow = null;
		ft = null;
		currentDte = null;
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

	public void saveOutput(String text) {
		try {

			bytes = null;
			bytes = (newLine + text).getBytes();
			fos.write(bytes);

			// System.out.println("File saved some output text");

		} catch (IOException e) {
			System.out.println("Failed to save data in file :");
			e.printStackTrace();
		}
	}

	public void saveOutputInBox(String text) {
		try {
			strToPrint = new StringBuilder();
			rowSeparator = new StringBuilder();
			bytes = null;

			width = text.length();
			diff = width - text.length();

			if ((diff % 2) == 1) {
				width++;
				diff++;
			}

			paddingSize = diff / 2;
			padding = new String(new char[paddingSize]).replace("\0", " ");

			toPrint = "| " + padding + text + padding + " ";
			strToPrint.append(toPrint);
			rowSeparator.append("+");
			rowSeparator.append(new String(new char[width + 2]).replace("\0",
					"-"));
			lineSeparator = System.getProperty("line.separator");
			lineSeparator = lineSeparator == null ? "\n" : lineSeparator;
			rowSeparator.append("+").append(lineSeparator);
			strToPrint.append("|").append(lineSeparator);
			strToPrint.insert(0, rowSeparator);
			strToPrint.append(rowSeparator);
			bytes = ("\n" + strToPrint.toString()).getBytes();
			fos.write(bytes);

			strToPrint = null;
			rowSeparator = null;
			width = 0;
			diff = 0;
			paddingSize = 0;
			toPrint = "";
			lineSeparator = "";
			padding = "";

			// System.out.println("File saved some output text");

		} catch (IOException e) {
			System.out.println("Failed to save data in file :");
			e.printStackTrace();
		}
	}

	public void printInBox(String text) throws IOException {
		strToPrint = new StringBuilder();
		rowSeparator = new StringBuilder();

		width = text.length();
		diff = width - text.length();

		if ((diff % 2) == 1) {
			width++;
			diff++;
		}

		paddingSize = diff / 2;
		padding = new String(new char[paddingSize]).replace("\0", " ");

		toPrint = "| " + padding + text + padding + " ";
		strToPrint.append(toPrint);
		rowSeparator.append("+");
		rowSeparator.append(new String(new char[width + 2]).replace("\0", "-"));
		lineSeparator = System.getProperty("line.separator");
		lineSeparator = lineSeparator == null ? "\n" : lineSeparator;
		rowSeparator.append("+").append(lineSeparator);
		strToPrint.append("|").append(lineSeparator);
		strToPrint.insert(0, rowSeparator);
		strToPrint.append(rowSeparator);

		System.out.println("\n" + strToPrint.toString());

		strToPrint = null;
		rowSeparator = null;
		width = 0;
		diff = 0;
		paddingSize = 0;
		toPrint = "";
		lineSeparator = "";
		padding = "";
	}

	public void closeFile() {
		try {

			fos.close();
		} catch (IOException e) {
			System.out.println("Failed to close File :");
			e.printStackTrace();
		}
	}

	public void printLogHeaderDesign(boolean d) {

		try {

			dNow = new Date();
			ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm a");
			currentDte = ft.format(dNow);

			prjHdr = "";
			prjHdr += "      Dwellar Systems Private Limited. All Rights Reserved.  ";
			prjHdr += "\n===============================================================================";
			prjHdr += "\n  GENERIC AUTOMATION TESTING \n";
			prjHdr += "\n ............................ \n";
			prjHdr += "  PROJECT     :  AutomationTool for WebSite  \n\n";
			prjHdr += "  TEAM LEADER :  Gopal Wadekar			   	\n\n";
			prjHdr += "  MENTORS     :  Pradip Desai			    \n";
			prjHdr += "                 Meghna Vaidhya			    \n\n";
			prjHdr += "  DEVELOPERS  :  Ganesh Prasad			    \n";
			prjHdr += "                 DINESH PARMAR			    \n";
			prjHdr += "\n===============================================================================";

			saveOutput(prjHdr);

			if (d) {
				saveOutputInBox("ROOT EXCEL IS LOADED.");
				prjHdr = "----------------------------------------------------------------------------";
				prjHdr += "\nDate                            : " + currentDte;
				prjHdr += "\nExecution Id                    : "
						+ Utilities.ExecutionId;
				prjHdr += "\nBase URL                        : "
						+ Constants.rootBaseUrl;
				prjHdr += "\nURL Enviornment                 : "
						+ Constants.rootUrlEnv;
				prjHdr += "\nNavigation Location             : "
						+ Constants.rootNavLoc;
				prjHdr += "\nNavigation Folder Name          : "
						+ Constants.rootNavFldr;
				prjHdr += "\nNavigation File Name            : "
						+ Constants.rootNavFile;
				prjHdr += "\nNavigation Sheet Name           : "
						+ Constants.rootNavSheetName;
				prjHdr += "\nTestCase Location               : "
						+ Constants.rootTCLoc;
				prjHdr += "\nTestCase Folder Name            : "
						+ Constants.rootTCFldr;
				prjHdr += "\nTestCase File Name              : "
						+ Constants.rootTCFile;
				prjHdr += "\nSending Email from ID           : "
						+ Constants.rootEmailId;
				prjHdr += "\nSending Email from Password     : "
						+ Constants.rootEmailPswd;
				prjHdr += "\nSending Email to Email IDS      : "
						+ Constants.rootEmailTo;
				prjHdr += "\nSet Email to Send               : "
						+ Constants.rootEmailFlag;
				prjHdr += "\nSet Insert TestCases in DB      : "
						+ Constants.rootDB_InsFlag;
				prjHdr += "\n----------------------------------------------------------------------------";
				prjHdr += "\n===============================================================================";

				saveOutput(prjHdr);
			}

		} catch (Exception e) {
			System.out.println("Exception in printLogHeaderDesign");
		}

	}

}
