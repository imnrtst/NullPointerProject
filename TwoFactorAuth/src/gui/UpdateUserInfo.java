package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class UpdateUserInfo extends Dialog {

	protected Object result;
	protected Shell shlUpdateUserInfo;
	private Text text;
	private Text text_1;
	private Text text_2;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public UpdateUserInfo(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
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
		shlUpdateUserInfo.setSize(450, 200);
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
		
		text = new Text(shlUpdateUserInfo, SWT.BORDER);
		text.setBounds(86, 7, 348, 26);
		
		text_1 = new Text(shlUpdateUserInfo, SWT.BORDER);
		text_1.setBounds(86, 39, 348, 26);
		
		text_2 = new Text(shlUpdateUserInfo, SWT.BORDER);
		text_2.setBounds(86, 71, 348, 26);
		
		Button btnSubmit = new Button(shlUpdateUserInfo, SWT.NONE);
		btnSubmit.setBounds(344, 125, 90, 30);
		btnSubmit.setText("Submit");
		
		Label lblNewLabel = new Label(shlUpdateUserInfo, SWT.WRAP | SWT.CENTER);
		lblNewLabel.setBounds(10, 111, 328, 44);
		lblNewLabel.setText("New Label");

	}

}
