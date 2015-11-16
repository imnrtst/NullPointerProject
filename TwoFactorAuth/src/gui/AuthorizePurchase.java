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
import email.ProjectEmail;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AuthorizePurchase extends Dialog {

	protected Object result;
	protected Shell shlAuthorizePurchase;
	private Text authCodeText;
	private Text pendingAuthText;
	private Text transIdText;
	private List<AuthDataObj> ados = null;
	private AuthDataObj ado = new AuthDataObj();
	private DB db = new DB();

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AuthorizePurchase(Shell parent, int style) 
	{
		super(parent, style);
		setText("SWT Dialog");
		ado.authEmail = UIW.ud.email;
		
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlAuthorizePurchase.open();
		shlAuthorizePurchase.layout();
		Display display = getParent().getDisplay();
		while (!shlAuthorizePurchase.isDisposed()) {
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
		shlAuthorizePurchase = new Shell(getParent(), getStyle());
		shlAuthorizePurchase.setSize(505, 498);
		shlAuthorizePurchase.setText("Authorize Purchase");
		
		Label statusLbl = new Label(shlAuthorizePurchase, SWT.BORDER | SWT.WRAP);
		statusLbl.setText("Status...");
		statusLbl.setBounds(10, 307, 463, 96);
		
		Label lblAutirizationCode = new Label(shlAuthorizePurchase, SWT.NONE);
		lblAutirizationCode.setBounds(10, 261, 112, 20);
		lblAutirizationCode.setText("Authorization Code:");
		
		authCodeText = new Text(shlAuthorizePurchase, SWT.BORDER);
		authCodeText.setBounds(128, 258, 345, 26);
		
		Button btnAuthorize = new Button(shlAuthorizePurchase, SWT.NONE);
		btnAuthorize.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				for(AuthDataObj ad: ados)
				{
					if(ad.transID == Integer.parseInt(transIdText.getText()) &&
							ad.authPin.equals(authCodeText.getText()))
					{
						try
						{
							//Authorize transaction
							statusLbl.setText("SUCCESS: ID/Pin pair found. Authorizing purchase...");
							
							//Send an email to purchaser
							ProjectEmail.sendPurchaseAuthEmail(ad.purchEmail, "" + ad.transID);
							
							//Remove the auth from DB
							db.delAuthData(ad.transID);
							
							statusLbl.setText("SUCCESS: Authorization procecessed");
							updateDbEntries();
							break;
						}
						catch(MessagingException me)
						{
							statusLbl.setText("ERROR: Unable to send email. Check internet connection and retry authorization.");
						}
						
					}
					else
					{
						statusLbl.setText("ERROR: Incorrect ID and Pin pair");
					}
				}
			}
		});
		btnAuthorize.setBounds(383, 409, 90, 30);
		btnAuthorize.setText("Authorize");
		
		Label lblPendingAuthorizations = new Label(shlAuthorizePurchase, SWT.NONE);
		lblPendingAuthorizations.setBounds(10, 10, 162, 30);
		lblPendingAuthorizations.setText("Pending Authorizations");
		
		pendingAuthText = new Text(shlAuthorizePurchase, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		pendingAuthText.setBounds(10, 46, 463, 148);
		
		Label lblTransactionId = new Label(shlAuthorizePurchase, SWT.NONE);
		lblTransactionId.setText("Transaction ID:");
		lblTransactionId.setBounds(10, 222, 112, 20);
		
		transIdText = new Text(shlAuthorizePurchase, SWT.BORDER);
		transIdText.setBounds(128, 216, 345, 26);
		
		

		updateDbEntries();
	}
	private void updateDbEntries()
	{
		ados = db.getAllAuthData(ado);
		
		pendingAuthText.setText("");
		for(AuthDataObj ad: ados)
		{
			pendingAuthText.append(ad.augmentedToString() + "\n");
		}
	}
}
