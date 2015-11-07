package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import classes.UserDataObj;
import database.DB;
import email.ProjectEmail;
import hash.PwGen;
import pin.pinGenerator;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UpdateUserInfo extends Dialog {

	protected Object result;
	protected Shell shlUpdateUserInfo;
	private Text emailText;
	private Text passwordText;
	private Text ccNumText;
	private Text confirmationText;
	private String pin = "";
	private UserDataObj oldUD = null;
	private UserDataObj newUD = new UserDataObj();
	private DB db = new DB();
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public UpdateUserInfo(Shell parent, int style) {
		super(parent, style);
		setText("Update User Info");
		oldUD = UIW.ud; 
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlUpdateUserInfo.open();
		shlUpdateUserInfo.layout();
		Display display = getParent().getDisplay();
		while (!shlUpdateUserInfo.isDisposed()) {
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
		shlUpdateUserInfo = new Shell(getParent(), getStyle());
		shlUpdateUserInfo.setSize(465, 252);
		shlUpdateUserInfo.setText("Update User Info");
		
		Label lblEmail = new Label(shlUpdateUserInfo, SWT.NONE);
		lblEmail.setBounds(10, 10, 70, 20);
		lblEmail.setText("Email:");
		
		Label lblPassword = new Label(shlUpdateUserInfo, SWT.NONE);
		lblPassword.setBounds(10, 42, 70, 20);
		lblPassword.setText("Password:");
		
		Label lblCcNum = new Label(shlUpdateUserInfo, SWT.NONE);
		lblCcNum.setBounds(10, 74, 70, 20);
		lblCcNum.setText("CC Num:");
		
		emailText = new Text(shlUpdateUserInfo, SWT.BORDER);
		emailText.setBounds(86, 7, 348, 26);
		
		passwordText = new Text(shlUpdateUserInfo, SWT.BORDER);
		passwordText.setBounds(86, 39, 348, 26);
		
		ccNumText = new Text(shlUpdateUserInfo, SWT.BORDER);
		ccNumText.setBounds(86, 71, 348, 26);
		
		Label lblStatus = new Label(shlUpdateUserInfo, SWT.WRAP | SWT.CENTER);
		lblStatus.setBounds(10, 111, 328, 44);
		
		Label lblConfirmation = new Label(shlUpdateUserInfo, SWT.NONE);
		lblConfirmation.setBounds(159, 178, 83, 15);
		lblConfirmation.setText("Confirmation:");
		
		confirmationText = new Text(shlUpdateUserInfo, SWT.BORDER);
		confirmationText.setBounds(248, 175, 90, 21);
		
		Label label = new Label(shlUpdateUserInfo, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 161, 424, -6);
		
		Button btnSubmit = new Button(shlUpdateUserInfo, SWT.NONE);
		btnSubmit.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				boolean changes = false;
				newUD = oldUD.clone();
				//Check if a field is empty if it is ignore it, otherwise update the data in the newUD
				if(!emailText.getText().isEmpty())
				{
					newUD.email = emailText.getText();
					changes = true;
				}
				
				if(!passwordText.getText().isEmpty())
				{
					newUD.password = PwGen.get_hash(passwordText.getText());
					changes = true;
				}
				
				if(!ccNumText.getText().isEmpty())
				{
					newUD.ccnum = ccNumText.getText();
					changes = true;
				}
				
				if(changes)
				{
					pin = "" + pinGenerator.randomGen();
					ProjectEmail.sendUpdateInfoEmail(oldUD.email, pin);
					lblStatus.setText("SUCCESS: Authorization pin sent to user");
				}
				else
				{
					lblStatus.setText("ERROR: No changes detected");
				}
			}
		});
		btnSubmit.setBounds(344, 125, 90, 30);
		btnSubmit.setText("Submit");
		
		
		
		Button btnConfirm = new Button(shlUpdateUserInfo, SWT.NONE);
		btnConfirm.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				if(pin.equals(confirmationText.getText()))
				{
					db.updateUserData(oldUD.email, newUD, oldUD.ccnum);
					lblStatus.setText("SUCCESS: User information updated");
				}
				else
				{
					lblStatus.setText("ERROR: Incorrect pin entered");
				}
				
			}
		});
		btnConfirm.setBounds(344, 172, 90, 26);
		btnConfirm.setText("Confirm");

	}
}
