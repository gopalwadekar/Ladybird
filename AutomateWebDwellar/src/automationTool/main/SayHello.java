package automationTool.main;

public class SayHello {
	
	public static void main (String [] args){
		System.out.println("Hello Jenkins");
		System.out.println(System.getProperty("java.class.path"));
	}
	
	
	public static void main (String [] args,String args2){
		System.out.println("Hello Jenkins");
		String n =args[1];
		System.out.println(System.getProperty("java.class.path"));
	}

}
