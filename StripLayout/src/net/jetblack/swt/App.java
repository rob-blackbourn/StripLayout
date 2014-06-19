package net.jetblack.swt;

import net.jetblack.swt.layout.StripData;
import net.jetblack.swt.layout.StripLayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class App {

	public static void main(String[] args) {
		test4();
		RowLayout rl;
	}
	
	private static void test1() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		Label label = new Label(shell, SWT.CENTER);
		label.setText("Hello World!");
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
	
	private static void test2() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new StripLayout(true));
		
		Label label1 = new Label(shell, SWT.CENTER);
		label1.setText("Row 1");
		
		Text text2 = new Text(shell, SWT.MULTI | SWT.BORDER);
		text2.setText("Row 2");
		text2.setLayoutData(new StripData(true, false));

		Label label3 = new Label(shell, SWT.CENTER);
		label3.setText("Row 3");
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
	
	private static void test3() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new StripLayout(false));
		
		Label label1 = new Label(shell, SWT.CENTER);
		label1.setText("Row 1");
		
		Text text2 = new Text(shell, SWT.MULTI | SWT.BORDER);
		text2.setText("Row 2");
		text2.setLayoutData(new StripData(false, true));

		Label label3 = new Label(shell, SWT.CENTER);
		label3.setText("Row 3");
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
	
	private static void test4() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new StripLayout(false));
		shell.setData("shell");
		
		Composite sendRow = new Composite(shell, SWT.NO_TRIM);
		sendRow.setLayout(new StripLayout(true));
		sendRow.setData("sendRow");
		sendRow.setLayoutData(new StripData(true, false));
		
		Button sendButton = new Button(sendRow, SWT.DEFAULT);
		sendButton.setText("Send");
		sendButton.setData("sendButton");
		
		Text sendText = new Text(sendRow, SWT.DEFAULT);
		sendText.setLayoutData(new StripData(true, false));
		sendText.setData("sendText");
		
		Combo lineEnding = new Combo(sendRow, SWT.SIMPLE);
		lineEnding.setItems(new String[] {"None", "Cr", "Lr", "CrLf"});
		lineEnding.select(0);
		lineEnding.setData("lineEnding");
		
		Text outputText = new Text(shell, SWT.MULTI | SWT.BORDER);
		outputText.setLayoutData(new StripData(true, true));
		outputText.setData("outputText");
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
	
	
}
