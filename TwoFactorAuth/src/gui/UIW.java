package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import classes.UserDataObj;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

class UIW {

	protected Shell shell;
	public static UserDataObj ud = null;
	private Text statusText;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIW window = new UIW();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		
		shell = new Shell();
		shell.setSize(187, 368);
		shell.setText("Two Factor Auth");
		
		Button btnAddNewUser = new Button(shell, SWT.NONE);
		btnAddNewUser.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				AddUser addUser = new AddUser(shell.getDisplay());
				addUser.open();
			}
		});
		btnAddNewUser.setBounds(10, 10, 148, 30);
		btnAddNewUser.setText("Add New User");
		
		Button btnLogin = new Button(shell, SWT.NONE);
		btnLogin.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				Login login = new Login(shell,shell.getStyle());
				login.open();
				
				if(ud != null)
				{
					statusText.setText("Status: " + ud.email + " logged in.");
				}
			}
		});
		btnLogin.setBounds(10, 46, 148, 30);
		btnLogin.setText("Login");
		
		Button btnMakePurchase = new Button(shell, SWT.NONE);
		btnMakePurchase.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				if(checkLoginStatus())
				{
					//Do stuff
				}
				else
				{
					//Display error
				}
			}
		});
		btnMakePurchase.setBounds(10, 118, 148, 30);
		btnMakePurchase.setText("Make Purchase");
		
		Button btnAuthorizePurchase = new Button(shell, SWT.NONE);
		btnAuthorizePurchase.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				if(checkLoginStatus())
				{
					//Do stuff
				}
				else
				{
					//Display error
				}
			}
		});
		btnAuthorizePurchase.setBounds(10, 154, 148, 30);
		btnAuthorizePurchase.setText("Authorize Purchase");
		
		Button btnEditUser = new Button(shell, SWT.NONE);
		btnEditUser.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				if(checkLoginStatus())
				{
					//Do stuff
					UpdateUserInfo uui = new UpdateUserInfo(shell,shell.getStyle());
					uui.open();
				}
				else
				{
					//Display error
					statusText.setText("Status: No user logged in.");
				}
			}
		});
		btnEditUser.setBounds(10, 82, 148, 30);
		btnEditUser.setText("Edit User Info");
		
		statusText = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		statusText.setText("Status: No user logged in.");
		statusText.setFont(SWTResourceManager.getFont("Consolas", 9, SWT.BOLD));
		statusText.setBounds(10, 221, 148, 98);
		
		Button btnLogOut = new Button(shell, SWT.NONE);
		btnLogOut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				if(checkLoginStatus())
				{
					//Do stuff
					ud = null;
					statusText.setText("Status: User logged out");
				}
				else
				{
					//Display error
					statusText.setText("Status: No user logged in.");
				}
			}
		});
		btnLogOut.setBounds(10, 190, 148, 25);
		btnLogOut.setText("Log Out");


	}
	private boolean checkLoginStatus()
	{
		if(ud == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
