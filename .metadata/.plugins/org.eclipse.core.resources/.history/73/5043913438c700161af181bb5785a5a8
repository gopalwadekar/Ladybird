package automationTool.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.python.antlr.op.Load;


public class SendEmail {

	public static String emailFrom = "";
	public static String emailFromPassword = "";
	public static String emailHost = "";
	public static String emailPortNo = "";
	public static String emailStarttls = "";
	public static String emailAuth = "";
	public static String emailSocketFactoryClass = "";
	public static String emailFallBack = "";
	public static String emailSubject = "";
	public static String emailMessage = "";
	public static String[] emailTo = null;
	public static String emailFilePath = null;
	public static boolean emailDebugMode = false;

	public static String setEmailProperty(String PropertyName,
			String PropertyValue) {

		String d = "";
		try {

			if (!PropertyValue.isEmpty()) {
				d = PropertyValue;

				System.out.println("PASSED : Propery " + PropertyName
						+ " is set By " + PropertyValue);
			} else {
				System.out.println("FAILED : Propery " + PropertyName
						+ " is set By " + PropertyValue);
			}

		} catch (Exception e) {
			System.out.println("FAILED : Exception to  set Propery "
					+ PropertyName);
		}
		return d;
	}

	public Properties setEmailProperties() {
		Properties property = null;
		try {
			System.out.println("____________________________________");
			System.out.println("Starting to set Email Properties");
			// Object Instantiation of a properties file.
			property = new Properties();
			property.put("mail.smtp.user",
					setEmailProperty("FromEmail", emailFrom));
			property.put("mail.smtp.host", setEmailProperty("Host", emailHost));
			property.put("mail.smtp.port",
					setEmailProperty("Port", emailPortNo));
			property.put("mail.smtp.starttls.enable",
					setEmailProperty("StartTls", emailStarttls));
			property.put("mail.smtp.auth", setEmailProperty("Auth", emailAuth));

			if (emailDebugMode) {
				property.put("mail.smtp.debug",
						setEmailProperty("Debug", "true"));
			} else {
				property.put("mail.smtp.debug",
						setEmailProperty("Debug", "false"));
			}

			property.put("mail.smtp.socketFactory.port",
					setEmailProperty("SocketFactoryPort", emailPortNo));

			property.put(
					"mail.smtp.socketFactory.class",
					setEmailProperty("SocketFactoryClass",
							emailSocketFactoryClass));

			System.out.println("PASEED : Email Properties Set");

		} catch (Exception e) {
			System.out.println("FAILED : Exeption in Email Properties Set");
		}
		System.out.println("____________________________________");
		return property;
	}

	public Session setEmailSessions(Properties properties) {
		Session session = null;
		try {
			System.out.println("____________________________________");
			System.out.println("Starting to set Email Sessions");

			session = Session.getDefaultInstance(properties, null);
			session.setDebug(emailDebugMode);

			System.out.println("PASEED : Email Sessions Set");

		} catch (Exception e) {
			System.out.println("FAILED : Exeption in Email Sessions Set");
		}
		System.out.println("____________________________________");
		return session;
	}

	public boolean setEmailTransport(Session session, MimeMessage mimeMessage) {
		boolean d = false;
		try {
			System.out.println("____________________________________");
			System.out.println("Starting to set Email Transport");

			Transport transport = session.getTransport("smtp");
			transport.connect(emailHost, emailFrom, emailFromPassword);
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

			transport.close();

			System.out.println("PASEED : Email Transport Set");
			d = true;
		} catch (Exception e) {
			System.out.println("FAILED : Exeption in Email Transport Set");
		}
		System.out.println("____________________________________");
		return d;
	}

	public MimeMessage setEmailMimeMessageBody(Session session) {
		MimeMessage mimeMessage = null;
		try {
			System.out.println("____________________________________");
			System.out.println("Starting to set Email MimeMessageBody");

			mimeMessage = new MimeMessage(session);
			mimeMessage.setSubject(emailSubject);

			Multipart multipart = new MimeMultipart();

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(emailMessage);
			multipart.addBodyPart(messageBodyPart);

			MimeBodyPart attachPart = new MimeBodyPart();
			attachPart.attachFile(emailFilePath);
			attachPart.setHeader("Content-Type",
					"text/plain; charset=\"us-ascii\"; name=\"mail.txt\"");
			multipart.addBodyPart(attachPart);

			mimeMessage.setContent(multipart);
			mimeMessage.setFrom(new InternetAddress(emailFrom));

			for (int i = 0; i < emailTo.length; i++) {
				mimeMessage.addRecipient(Message.RecipientType.TO,
						new InternetAddress(emailTo[i]));
			}

			mimeMessage.saveChanges();

			System.out.println("PASEED : Email MimeMessageBody Set");

		} catch (Exception e) {
			System.out
					.println("FAILED : Exeption in Email MimeMessageBody Set");
		}
		System.out.println("____________________________________");
		return mimeMessage;
	}

	public void automateSendEmail(String filepath) {
		System.out.println("===============================================");
		System.out.println("automateSendEmail Started");

		try {

			emailFrom = Constants.rootEmailId;
			emailFromPassword = Constants.rootEmailPswd;
			emailTo = septrateEmailIds(Constants.rootEmailTo);
			emailSubject = "[ ANGULAR ] Automation Test Results";

			emailMessage = setEmailMessage();
			emailHost = "smtp.gmail.com";
			emailPortNo = "465";
			emailStarttls = "true";
			emailAuth = "true";
			emailSocketFactoryClass = "javax.net.ssl.SSLSocketFactory";
			emailFallBack = "false";

			emailFilePath = filepath;// septrateFilePaths(filepath);
			emailDebugMode = true;

			Properties properties = setEmailProperties();

			if (properties != null) {

				Session session = setEmailSessions(properties);

				if (session != null) {
					MimeMessage mimeMessage = setEmailMimeMessageBody(session);

					if (mimeMessage != null) {

						if (setEmailTransport(session, mimeMessage)) {
							System.out.println("@@@@@    EMAIL   @@@@@");
							System.out
									.println("PASSED : Email has been set Sucessfully");

							System.out.println("@@@@@    EMAIL   @@@@@");
							Utilities.LOG.newLine = "\n";
							Utilities.LOG.saveOutput("\n\n");
							Utilities.LOG
									.saveOutput("@@@@@@@@@@@@@@@@@@@@@@@EMAIL@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
							Utilities.LOG
									.saveOutputInBox("EMAIL HAS BEEN SET SUCESSFULLY");
							Utilities.LOG
									.saveOutput("@@@@@@@@@@@@@@@@@@@@@@@EMAIL@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						} else {
							System.out.println("@@@@@    EMAIL   @@@@@");

							System.out
									.println("FAILED : Email has been Failed to send");

							System.out.println("@@@@@    EMAIL   @@@@@");

							Utilities.LOG
									.saveOutput("@@@@@@@@@@@@@@@@@@@@@@@EMAIL@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
							Utilities.LOG
									.saveOutputInBox("EMAIL HAS BEEN FAILED TO SEND");
							Utilities.LOG
									.saveOutput("@@@@@@@@@@@@@@@@@@@@@@@EMAIL@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

						}

					}

				}

			}

			System.out.println("automateSendEmail Completed");
		} catch (Exception e) {
			System.out.println("automateSendEmail Exception Found");
		}
		System.out.println("===============================================");

	}

	public final static String[] septrateEmailIds(String txt) {
		String[] d = txt.split(",");
		return d;
	}

	public final static String[] septrateFilePaths(String txt) {
		String[] d = txt.split(",");
		return d;
	}

	
	public String setEmailMessage() {
		String d = "";
		try {
			d = "";
			d += "GENERIC AUTOMATION TOOL V_1.0.0.1\n\n";
			d += "[ Srcipt Execution Date : "
					+ new SimpleDateFormat("E dd.MM.yyyy 'at' hh:mm a")
							.format(new Date()) + " ]\n";
			d += "[ Srcipt Execution Id : " + Utilities.ExecutionId + " ]\n";
			 d += Constants.setScriptsMessages() + "\n\n";// seprateMessages() +
			// "\n\n";
			d += SqlConnection.getScriptsResultsCount(Utilities.ExecutionId);

			// d += "________________________________________________________";

			// if
			// (SqlConnection.getSqlConnection(LoadApp.conStringPreProdFor_W)) {
			// d += "\nNo of Scripts RUNNED : "
			// + SqlConnection.getScriptsResultsCount(
			// LoadApp.ExecutionId, "all");
			// d += "\nNo of Scripts PASSED : "
			// + SqlConnection.getScriptsResultsCount(
			// LoadApp.ExecutionId, "passed");
			//
			// d += "\nNo of Scripts FAILED : "
			// + SqlConnection.getScriptsResultsCount(
			// LoadApp.ExecutionId, "Failed");
			//
			// SqlConnection.closeSqlConnection();
			// }

			// d +=
			// "\n________________________________________________________";
		} catch (Exception e) {
			System.out.println("Exception in setEmailMessage");
		}
		return d;

	}

}
