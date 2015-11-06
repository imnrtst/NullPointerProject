package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

public class Login extends Dialog {

	protected Object result;
	protected Shell shlLogin;
	private Text passwordText;
	private Text emailText;

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
		passwordText.setBounds(86, 67, 329, 26);
		
		emailText = new Text(shlLogin, SWT.BORDER);
		emailText.setBounds(86, 20, 329, 26);
		
		Label lblStatus = new Label(shlLogin, SWT.WRAP | SWT.CENTER);
		lblStatus.setBounds(10, 109, 405, 60);

	}

}
