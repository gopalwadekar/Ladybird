package automationTool.main;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.tools.DocumentationTool.Location;

public class Temp {

	public static DecimalFormat f=null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		

		
		
		
		
//		 BigDecimal payment = new BigDecimal("211135.37");
//	        NumberFormat n = NumberFormat.getCurrencyInstance(new Locale("en", "IN")); 
//	        double doublePayment = payment.doubleValue();
//	        String s = n.format(doublePayment);
//	        System.out.println(s);
//		
		
		
		
		
		
//		Double amt = 12253.23;
//		//Double d = 234.12413;
//		String[] splitter = amt.toString().split("\\.");
//	int len=	splitter[0].length();   // Before Decimal Count
//		splitter[1].length();   // After  Decimal Count
//		 
//		System.out.println(f.format(len));
//		f = new DecimalFormat("#.00");
//		if (len<3) {
//			f = new DecimalFormat("###.00");
//		}
//		else if (len>3 && len <=3) {
//			f = new DecimalFormat("##,###.00");
//		}
		//System.out.println(f.format(amt));
//		f = new DecimalFormat("##,####,####.00");
//		System.out.println(f.format(amt));
//		f = new DecimalFormat("#,##,###.00");
//		System.out.println(f.format(amt));
//		f = new DecimalFormat("##,##,##,###.00");
//		System.out.println(f.format(amt));
//		
		
		
		//System.out.println(formatLakh(123452555285516));
	}
	
	private static String formatLakh(double d) {
	    String s = String.format(Locale.UK, "%1.2f", Math.abs(d));
	    s = s.replaceAll("(.+)(...\\...)", "$1,$2");
	    while (s.matches("\\d{3,},.+")) {
	        s = s.replaceAll("(\\d+)(\\d{2},.+)", "$1,$2");
	    }
	    return d < 0 ? ("-" + s) : s;
	}

}
