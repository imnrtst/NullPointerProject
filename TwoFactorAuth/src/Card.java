import java.util.ArrayList;
import java.util.Scanner;

public class Card
{
	public String email;
	public String cardNum;
	public String expDate;
	public String cvn;
	public int cardPin;
	
	//verify card is in DB
	@SuppressWarnings({ "resource", "unused" })
	public void purchase()
	{
		boolean used = false;
		boolean verified = false;
		int pin;
		
		DB db = new DB();
		ArrayList <UserDataObj> list = new ArrayList <UserDataObj>();
		UserDataObj data = new UserDataObj();
		pinGenerator code = new pinGenerator();
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Please enter your user email address: ");
		email = scanner.nextLine();
		
		System.out.println("Please enter your credit card number: ");
		cardNum = scanner.nextLine();
		
		System.out.println("Please enter your CC expiration date: ");
		expDate = scanner.nextLine();
		
		System.out.println("Please enter your CC security pin (CVN): ");
		cvn = scanner.nextLine();
		
		used = db.checkCCNum(cardNum);
		
		if(used)
		{
			//use Tyler's Email function
			//call it here
			
			//Inform user to get pin from their email
			System.out.println("Please check your email for your PIN.");
			
			//get user data from email
			list = db.getUserData(email);
			//get first element
			data = list.get(0);
			//convert pin from string to int
			pin = Integer.parseInt(data.ccpin);
			
			while(verified != true)
			{
				System.out.println("Please enter PIN: ");
				cardPin = scanner.nextInt();
				//compare PINs
				if(cardPin == pin)
				{
					verified = true;
				}
			}
		}
		
		System.out.println("Purchase has been made.");
	}
}