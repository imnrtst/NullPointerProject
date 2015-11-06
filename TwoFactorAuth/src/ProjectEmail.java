
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

 
public class ProjectEmail {
 
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
//MAIN
/*	public static void main(String args[]) throws AddressException, MessagingException {
		generateAndSendEmail();
		System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
	}	//main method
*/
 //GENERATE and SEND EMAIL
	public static void generateAndSendEmail(int i) throws AddressException, MessagingException {
		
		String recipientEmail = "cse465user1@gmail.com";
		String recipientName = "User1";
		String randomPin = "JKY7";
		int emailCondition = i;	//1 = purchase email, 0 = authorization email
		// Step1
		//System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		//System.out.println("Mail Server Properties have been setup successfully..");
 
		// Step2
		//System.out.println("\n\n 2nd ===> get Mail Session..");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
		generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("ty.imnrtst@gmail.com"));
		if (emailCondition == 1){	//Sending purchase confirmation
			generateMailMessage.setSubject("Purchase Confirmation");
			String emailBody = "Hello "+ recipientName + "<br>Please confirm your purchase on our site by entering this code: "+ randomPin + "<br><br> Regards, <br>NullPointerSite";
			generateMailMessage.setContent(emailBody, "text/html");
			//System.out.println("Mail Session has been created successfully..");
		}
		else if (emailCondition == 0){	//Sending authorization confirmation
			generateMailMessage.setSubject("Card Use Request");
			String emailBody = "Hello"+ recipientName + "<br>Another user has requested permission to use your card. To confirm authorization, entering this code: "+ randomPin + "<br><br> Regards, <br>NullPointerSite";
			generateMailMessage.setContent(emailBody, "text/html");
			//System.out.println("Mail Session has been created successfully..");
		}
		else {
			System.out.print("Error with determining email condition");
		}
		
		// Step3
		//System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");
 
		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		transport.connect("smtp.gmail.com", "cse465nullpointersite@gmail.com", "nullpointer1");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}	//generate and send mail
}	//public class project email
