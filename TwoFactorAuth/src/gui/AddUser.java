package gui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import database.DB;
import dataobj.UserDataObj;
import hash.PwGen;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AddUser extends Shell {
	private Text emailText;
	private Text passwordText;
	private Text ccNumText;
	private Text dbEntriesText;
	
	private DB db = new DB();

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			AddUser shell = new AddUser(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public AddUser(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		Label emailLable = new Label(this, SWT.NONE);
		emailLable.setBounds(10, 43, 70, 20);
		emailLable.setText("Email:");
		
		emailText = new Text(this, SWT.BORDER);
		emailText.setBounds(86, 43, 363, 26);
		
		Label passwordLabel = new Label(this, SWT.NONE);
		passwordLabel.setBounds(10, 92, 70, 20);
		passwordLabel.setText("Password:");
		
		passwordText = new Text(this, SWT.BORDER);
		passwordText.setBounds(86, 89, 363, 26);
		
		Label ccNumLabel = new Label(this, SWT.NONE);
		ccNumLabel.setBounds(10, 142, 70, 20);
		ccNumLabel.setText("CC Num:");
		
		ccNumText = new Text(this, SWT.BORDER);
		ccNumText.setBounds(86, 139, 363, 26);
		
		dbEntriesText = new Text(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		dbEntriesText.setBounds(10, 253, 592, 288);
		
		Label dbEntriesLabel = new Label(this, SWT.NONE);
		dbEntriesLabel.setBounds(10, 227, 70, 20);
		dbEntriesLabel.setText("DB Entries:");
		
		Label statusLabel = new Label(this, SWT.BORDER | SWT.WRAP | SWT.CENTER);
		statusLabel.setText("Status...");
		statusLabel.setBounds(470, 43, 132, 119);
		
		Button btnAdd = new Button(this, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				statusLabel.setText("");
				//Handle the case that a user leaves information blank
				if(noMissingFields())
				{
					UserDataObj newUser = buildUserDataObj();
					
					if(db.addUserData(newUser))
					{
						statusLabel.setText("Success: User Added");
					}
					else
					{
						statusLabel.setText("ERROR: Unable to add user. See DB log for details");
					}
				}
				else
				{	
					statusLabel.setText("Error: Missing required field");
				}
				updateDbEntriesText();
			}
		});
		btnAdd.setBounds(359, 188, 90, 30);
		btnAdd.setText("Add");
		updateDbEntriesText();
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Add New User");
		setSize(630, 598);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	private boolean noMissingFields()
	{
		boolean noMissingFields = true;
		
		if(emailText.getText().isEmpty() || passwordText.getText().isEmpty() || ccNumText.getText().isEmpty())
		{
			noMissingFields = false;
		}
		return noMissingFields;
	}
	
	private UserDataObj buildUserDataObj()
	{
		UserDataObj newUser = new UserDataObj();
		newUser.email = emailText.getText();
		newUser.password = PwGen.get_hash(passwordText.getText());
		newUser.ccnum = ccNumText.getText();
		return newUser;
		
	}
	
	private void updateDbEntriesText()
	{
		
		String displayText = "";
		dbEntriesText.setText("");
		ArrayList<UserDataObj> entries = db.getAllUsers();
		for(UserDataObj userData:entries)
		{
			displayText += userData.toString() + "\n";
		}
		dbEntriesText.setText(displayText);
	}
}
