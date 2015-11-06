package classes;
//New Login
import java.sql.*;
import java.util.Scanner;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Login_New{
	public static void main(String[] args) throws Exception, AddressException, MessagingException{
		DB db = new DB();
		Connection conn = db.openDBConnection();
		int i = 0;
		Scanner scan = new Scanner(System.in);
		ProjectEmail email = new ProjectEmail();
		System.out.println("Enter a number to choose an option:\n--------------------------------------------------\n");
		System.out.println("0 - Quit\n1 - Send a purchase confirmation email\n2 - Send an authorization request email");
		System.out.println("3 - Make a purchase\n4 - register a credit card");
		while(i == 0){
			String input = scan.next();
			switch (input){
			case "0": {	//quit
				i++;
				break;
			}
			case "1": {	//send purchase email
				System.out.println("A purchase confirmation email has been sent. Check your email...");
				email.generateAndSendEmail(1);
				break;
			}
			case "2": {	//send authorize email
				System.out.println("An authorization request has been sent to the appropriate user. Please be patient...");
				email.generateAndSendEmail(0);
				break;
			}
			case "3":{	//Make a purchase
				System.out.println("This is a work in progress.");
				break;
			}
			case "4":{	//Register a credit card
				System.out.println("This is a work in progress.");
				break;
			}
			default: {
				System.out.println("Incorrect input");
				break;
			}
			}	//switch statement
		}	//while loop
		
		db.closeDBConnection(conn);
	}	//main method
}	//Login_New