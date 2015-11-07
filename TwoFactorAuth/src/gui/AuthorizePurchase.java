package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class AuthorizePurchase extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text authCodeText;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AuthorizePurchase(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
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
		shell = new Shell(getParent(), getStyle());
		shell.setSize(356, 129);
		shell.setText(getText());
		
		Label lblAutirizationCode = new Label(shell, SWT.NONE);
		lblAutirizationCode.setBounds(10, 10, 130, 20);
		lblAutirizationCode.setText("Authorization Code:");
		
		authCodeText = new Text(shell, SWT.BORDER);
		authCodeText.setBounds(146, 10, 183, 26);
		
		Button btnAuthorize = new Button(shell, SWT.NONE);
		btnAuthorize.setBounds(239, 53, 90, 30);
		btnAuthorize.setText("Authorize");
		
		Label lblAuthStatus = new Label(shell, SWT.NONE);
		lblAuthStatus.setBounds(21, 53, 212, 26);

	}

}
