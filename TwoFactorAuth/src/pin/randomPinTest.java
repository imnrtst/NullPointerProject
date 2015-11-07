package pin;
// Tests the output of the random pin
public class randomPinTest {
	
	public static void main(String[] args)
	{
		pinGenerator pin = new pinGenerator();
		System.out.println(pin.randomGen());
	}

}
