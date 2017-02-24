package automationTool.main;

import org.openqa.selenium.*;

@SuppressWarnings("serial")
public class AutomationException extends Exception {

	private String message = null;
	private String messageIn = null;
	private StackTraceElement[] messageTrace = null;

	public AutomationException() {
		super();
	}

	public AutomationException(Throwable cause, String msgIn) {
		super(cause);
		this.message = null;
		messageIn = null;
		// this.messageTrace = printrace;
		this.messageIn = msgIn;
		this.messageTrace = cause.getStackTrace();

		if (cause instanceof TimeoutException) {
			this.message = "NullPointerException";
		} else if (cause instanceof NoSuchElementException) {
			this.message = "ElementNotFoundException";
		} else if (cause.equals("")) {
			this.message = "TimeoutException";
		} else if (cause instanceof Exception) {
			this.message = "Exception";
		}
	}

	@Override
	public String toString() {
		return message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void printException() {
		System.out.println("\n...............................");
		System.out.println("Exception Found : " + this.message);
		System.out.println("Exception FoundIn : " + this.messageIn);
		System.out.println("Exception TraceIn : " + this.messageTrace);
		System.out.println("...............................\n");
	}

}
