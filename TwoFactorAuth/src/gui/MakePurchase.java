package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class MakePurchase extends Dialog {

	protected Object result;
	protected Shell shlMakePurchase;
	private Text ccnumText;

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
		shlMakePurchase.setSize(450, 151);
		shlMakePurchase.setText("Make Purchase");
		
		Label ccNumLabel = new Label(shlMakePurchase, SWT.NONE);
		ccNumLabel.setBounds(10, 26, 70, 20);
		ccNumLabel.setText("CC Num");
		
		ccnumText = new Text(shlMakePurchase, SWT.BORDER);
		ccnumText.setBounds(69, 23, 365, 26);
		
		Button btnPurchase = new Button(shlMakePurchase, SWT.NONE);
		btnPurchase.setBounds(344, 66, 90, 30);
		btnPurchase.setText("Purchase");
		
		Label purchaseStatus = new Label(shlMakePurchase, SWT.NONE);
		purchaseStatus.setBounds(20, 66, 318, 30);

	}

}
