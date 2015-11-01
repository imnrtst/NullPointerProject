import java.util.ArrayList;
import java.util.Scanner;

public class Card
{
	public String email;
	public String cardNum;
	public String expDate;
	public String cvn;
	public int cardPin;
	
	//add user to DB
	/*public void registerCard()
	{
		
	}*/
	
	//verify card is in DB
	@SuppressWarnings({ "resource", "unused" })
	public void purchase()
	{
		boolean used = false;
		boolean verified = false;
		int pin;
		
		DB db = new DB();
		ArrayList <UserDataObj> data = new ArrayList <UserDataObj>();
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
			//get user data from email
			data = db.getUserData(email);
			
			//add PIN to database
			
			pin = code.randomGen();
			//email PIN
			
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