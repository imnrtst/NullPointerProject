package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Decorations; 

class UIW {

	protected Shell shell;

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
		shell.setSize(190, 260);
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
			}
		});
		btnLogin.setBounds(10, 46, 148, 30);
		btnLogin.setText("Login");
		
		Button btnMakePurchase = new Button(shell, SWT.NONE);
		btnMakePurchase.setBounds(10, 118, 148, 30);
		btnMakePurchase.setText("Make Purchase");
		
		Button btnAuthorizePurchase = new Button(shell, SWT.NONE);
		btnAuthorizePurchase.setBounds(10, 154, 148, 30);
		btnAuthorizePurchase.setText("Authorize Purchase");
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnNewButton.setBounds(10, 82, 148, 30);
		btnNewButton.setText("Edit User Info");


	}

}
