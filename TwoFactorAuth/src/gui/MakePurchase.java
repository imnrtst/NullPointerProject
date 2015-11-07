package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import database.DB;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MakePurchase extends Dialog {

	protected Object result;
	protected Shell shlMakePurchase;
	private Text ccnumText;
	private DB db = new DB();

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
		ccnumText.setToolTipText("Leave blank to use CC on file");
		ccnumText.setBounds(69, 23, 365, 26);
		
		Label purchaseStatus = new Label(shlMakePurchase, SWT.WRAP);
		purchaseStatus.setText("*Note: Leave CC Num blank if you wish to use the CC on file");
		purchaseStatus.setBounds(20, 66, 318, 30);
		
		Button btnPurchase = new Button(shlMakePurchase, SWT.NONE);
		btnPurchase.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				String ccNum = "";
				if(ccnumText.getText().isEmpty())
				{
					ccNum = UIW.ud.ccnum;
				}
				else if(ccnumText.getText().matches("\\d{1,16}"))
				{
					ccNum = ccnumText.getText();
				}
				else
				{
					purchaseStatus.setText("ERROR: Malformated CC number");
					ccNum = "";
				}
				
				if(!ccNum.isEmpty())
				{
					if()
					{
						
					}
				}
			}
		});
		btnPurchase.setBounds(344, 66, 90, 30);
		btnPurchase.setText("Purchase");
		
		

	}

}
