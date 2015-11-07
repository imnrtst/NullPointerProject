package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import database.DB;
import dataobj.UserDataObj;
import hash.PwGen;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Login extends Dialog {

	protected Object result;
	protected Shell shlLogin;
	private Text passwordText;
	private Text emailText;
	private DB db = new DB();
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Login(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlLogin.open();
		shlLogin.layout();
		Display display = getParent().getDisplay();
		while (!shlLogin.isDisposed()) {
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
		shlLogin = new Shell(getParent(), getStyle());
		shlLogin.setSize(450, 214);
		shlLogin.setText("Login");
		
		Label lblEmail = new Label(shlLogin, SWT.NONE);
		lblEmail.setBounds(10, 20, 70, 20);
		lblEmail.setText("Email:");
		
		Label lblPassword = new Label(shlLogin, SWT.NONE);
		lblPassword.setBounds(10, 67, 70, 20);
		lblPassword.setText("Password:");
		
		passwordText = new Text(shlLogin, SWT.BORDER);
		passwordText.setText("brokenPass");
		passwordText.setBounds(86, 67, 329, 26);
		
		emailText = new Text(shlLogin, SWT.BORDER);
		emailText.setText("testUser@asu.edu");
		emailText.setBounds(86, 20, 329, 26);
		
		Label lblStatus = new Label(shlLogin, SWT.WRAP | SWT.CENTER);
		lblStatus.setBounds(10, 109, 300, 60);
		
		Button btnLogin = new Button(shlLogin, SWT.NONE);
		btnLogin.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				List<UserDataObj> userDatum = db.getUserData(emailText.getText());
				UserDataObj ud = null;
				//If the user data obj is empty the user does not exist
				if(!userDatum.isEmpty())
				{
					//Check the password before passing the user data object back to UIW
					ud = userDatum.get(0);
					
					if(ud.password.equals(PwGen.get_hash(passwordText.getText())))
					{
						lblStatus.setText("SUCCESS: " + ud.email + " logged in");
						UIW.ud = ud;
					}
					else
					{
						lblStatus.setText("ERROR: Invalid password");
						ud = null;
					}
				}
				else
				{
					lblStatus.setText("ERROR: Invalid credentials");
					ud = null;
				}
			}
		});
		btnLogin.setBounds(325, 109, 90, 30);
		btnLogin.setText("Login");

	}
}
