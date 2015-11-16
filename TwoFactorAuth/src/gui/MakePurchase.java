package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.util.List;

import javax.mail.MessagingException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import database.DB;
import dataobj.AuthDataObj;
import dataobj.UserDataObj;
import email.ProjectEmail;
import pin.pinGenerator;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MakePurchase extends Dialog {

	protected Object result;
	protected Shell shlMakePurchase;
	private Text ccnumText;
	private DB db = new DB();

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public MakePurchase(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlMakePurchase.open();
		shlMakePurchase.layout();
		Display display = getParent().getDisplay();
		while (!shlMakePurchase.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlMakePurchase = new Shell(getParent(), getStyle());
		shlMakePurchase.setSize(465, 262);
		shlMakePurchase.setText("Make Purchase");
		
		Label ccNumLabel = new Label(shlMakePurchase, SWT.NONE);
		ccNumLabel.setBounds(10, 26, 53, 20);
		ccNumLabel.setText("CC Num");
		
		ccnumText = new Text(shlMakePurchase, SWT.BORDER);
		ccnumText.setToolTipText("Leave blank to use CC on file");
		ccnumText.setBounds(69, 23, 365, 26);
		
		Label purchaseStatus = new Label(shlMakePurchase, SWT.BORDER | SWT.WRAP);
		purchaseStatus.setText("*Note: Leave CC Num blank if you wish to use the CC on file");
		purchaseStatus.setBounds(20, 66, 414, 103);
		
		Button btnPurchase = new Button(shlMakePurchase, SWT.NONE);
		btnPurchase.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				//Determine which credit card to use on file/entered
				String ccNum = "";
				if(ccnumText.getText().isEmpty())
				{
					ccNum = UIW.ud.ccnum;
				}
				else if(ccnumText.getText().matches("\\d{1,16}"))
				{
					ccNum = ccnumText.getText();
				}
				//The ccnum entered is malformed and cannot be used
				else
				{
					purchaseStatus.setText("ERROR: Malformated CC number");
					ccNum = "";
				}
				
				//If a valid credit card num was provided
				if(!ccNum.isEmpty())
				{
					//Check if ccnum is registered to this user
					if(ccNum.equals(UIW.ud.ccnum) && UIW.ud.ccreg)
					{
						//Authorize the purchase: User is registered card holder
						purchaseStatus.setText("SUCCESS: Order authorized (Registerd CC Holder)");
					}
					else if(!ccNum.equals(UIW.ud.ccnum) && !db.checkCCNum(ccNum))
					{
						//Authorize purchase: unregistered card num
						purchaseStatus.setText("SUCCESS: Order authorized (Unregistered CC)");
					}
					else
					{
						AuthDataObj ado = new AuthDataObj();
						//Purchase requires authorization
						
						//Get the purchaser email - this is already in UIW from login
						ado.purchEmail = UIW.ud.email;
						//Get the registered users email
						List<UserDataObj> udo = db.getUserDataCCnum(ccNum);
						ado.authEmail = udo.get(0).email;
						//Create a pin to authorize sale
						ado.authPin = "" + pinGenerator.randomGen();
						//Add entry to authorization table of DB
						db.addAuthData(ado);
						//Get the transaction id
						List<AuthDataObj> ados = db.getAuthData(ado);
						String transID = "" + ados.get(0).transID;
						try
						{
							//Send email to registered user's email
							ProjectEmail.sendPurchaseConfEmail(ado.authEmail, transID, ados.get(0).authPin);
							purchaseStatus.setText("WARNING: Order on hold. Email sent to registered card holder for validation.)");
						}
						catch(MessagingException me)
						{
							db.delAuthData(ados.get(0).transID);
							purchaseStatus.setText("ERROR: Unable to send email. Purchase canceled.");
						}
						
					}
				}
			}
		});
		btnPurchase.setBounds(344, 175, 90, 30);
		btnPurchase.setText("Purchase");
		
		

	}

}
