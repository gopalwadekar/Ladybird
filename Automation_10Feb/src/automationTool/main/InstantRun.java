package automationTool.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import automationTool.excel.AdvancedExcelReader;
import automationTool.excel.AdvancedExcelWriter;

public class InstantRun {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String d ="";
		String parameter ="05/12/1992"; 
		System.out.println("calucating age form " + parameter);
		if (!parameter.isEmpty()) {

			d = ""
					+ ((Integer.parseInt(new SimpleDateFormat("yyyy")
							.format(new Date()))) - Integer
							.parseInt(parameter.substring(parameter
									.length() - 4)));
		}
		
		//String d =Utilities.createTime();
		System.out.println("Calculated Age : "+d);
		
//		 new AdvancedExcelWriter().createExcel("root", "readExcelData.xlsx", "addLead:AAAA,BBBB");
//	
//		 Utilities.toSave(true, new String[]{"fsdfdsfd","sfsdfsdfsdf"});
//		 Utilities.toSave(true, new String[]{"fsdfdsfd","sfsdfsdfsdf"});
//		 Utilities.toSave(true, new String[]{"fsdfdsfd","sfsdfsdfsdf"});
//		
//		 AdvancedExcelReader.readExcel("root", "readExcelData.xlsx", "addLead");
//		 
//		
//		 new AdvancedExcelWriter().createExcel("root", "readExcelData.xlsx", "addTask:AAAA,BBBB");
//		 
//		 Utilities.toSave(true, new String[]{"fsfsdfsdfsdfsdfdsfd","fsdfdsfd"});
//		 Utilities.toSave(true, new String[]{"fsfsdfsdfsdfsdfdsfd","fsdfdsfd"});
//		 
//		 
//		 AdvancedExcelReader.readExcel("root", "readExcelData.xlsx","addTask");
//		 
//		 
//		 
//		 
//		 new AdvancedExcelWriter().createExcel("root", "readExcelData.xlsx", "addNote:AAAA,BBBB");
//			
//		 Utilities.toSave(true, new String[]{"fsdfdsfd","sfsdfsdfsdf"});
//		 Utilities.toSave(true, new String[]{"fsdfdsfd","sfsdfsdfsdf"});
//		 Utilities.toSave(true, new String[]{"fsdfdsfd","sfsdfsdfsdf"});
//		 
//		 Utilities.toSave(true, new String[]{"fsdfdsfd","sfsdfsdfsdf"});
//		 Utilities.toSave(true, new String[]{"fsdfdsfd","sfsdfsdfsdf"});
//		 Utilities.toSave(true, new String[]{"fsdfdsfd","sfsdfsdfsdf"});
//		 
//		 Utilities.toSave(true, new String[]{"fsdfdsfd","sfsdfsdfsdf"});
//		 Utilities.toSave(true, new String[]{"fsdfdsfd","sfsdfsdfsdf"});
//		 Utilities.toSave(true, new String[]{"fsdfdsfd","sfsdfsdfsdf"});
//		 
//		 Utilities.toSave(true, new String[]{"fsdfdsfd","sfsdfsdfsdf"});
//		 Utilities.toSave(true, new String[]{"fsdfdsfd","sfsdfsdfsdf"});
//		 Utilities.toSave(true, new String[]{"fsdfdsfd","sfsdfsdfsdf"});
//		
//		 AdvancedExcelReader.readExcel("root", "readExcelData.xlsx", "addNote");
	}

}
