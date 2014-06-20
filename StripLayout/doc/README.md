StripLayout
===========

The following layout is difficult to achieve with the standard SWT layout classes.

![Example Layout](StripDiagnostic-1.png)

The `StripLayout` class lays out controls in strips which have optional fill 
and margin characteristics.

We can break down this layout by looking at each part, from the top down.

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new StripLayout(false, new Margin(3)));

We make the shell, then assign a `StripLayout` with `isHorizontal` set to `false`. This
is going to layout its children vertically. We give it a margin of `3` on all sides so
the controls aren't tight to the edge of the window.

![Example Layout](StripDiagnostic-2.png)

Now we can make each of the rows.

		// Row 1
		Composite sendRow = new Composite(shell, SWT.NO_TRIM);
		sendRow.setLayout(new StripLayout(true, new Margin(0, 0, 0, 3)));
		sendRow.setLayoutData(new StripData(true, false));
		
		// Row 2
		Text outputText = new Text(shell, SWT.MULTI | SWT.BORDER);
		outputText.setLayoutData(new StripData(true, true));
		
		// Row 3
		Composite statusRow = new Composite(shell, SWT.NO_TRIM);
		statusRow.setLayout(new StripLayout(true, new Margin(0, 3, 0, 3)));
		statusRow.setLayoutData(new StripData(true, false));

The first row is a `Composite` as we want it to contain a number of child controls. 
It should have a fixed height, but fill in the horizontal direction,
so the `StripData` gets `true` for `fillHorizontal` and `false` for `fillVertical`.
The first row gets a bottom margin of 3 to provide a gap to the control below.

The second row should fill the remaining area, to we set both fills to `true`.

The third row is like the first, with some margin to provide appropriate distance
from the control above. 

This gives us the following.

![Example Layout](StripDiagnostic-3.png)

Finally we can create the top row and the last.

		// Row 1
		Button sendButton = new Button(sendRow, SWT.DEFAULT);
		sendButton.setLayoutData(new StripData(false, false));
		sendButton.setText("Send");
		
		Text sendText = new Text(sendRow, SWT.SINGLE | SWT.BORDER);
		sendText.setLayoutData(new StripData(true, false, new Margin(3, 0, 3, 0)));
		
		Combo lineEnding = new Combo(sendRow, SWT.SIMPLE);
		lineEnding.setItems(new String[] {"None", "Cr", "Lr", "CrLf"});
		lineEnding.select(0);

		// Row 3
		Text leftStatus = new Text(statusRow, SWT.BORDER);
		leftStatus.setLayoutData(new StripData(true,false, new Margin(0, 0, 2, 0)));
		
		Text rightStatus = new Text(statusRow, SWT.BORDER);
		rightStatus.setLayoutData(new StripData(true,false, new Margin(1, 0, 0, 0)));

The parent for the controls on row 1 is the `sendRow`. The button should not fill, the
"send text" should stretch in the horizontal direction,
and the combo box should not fill. The send text gets some margin on the left and right so
it doesn't but against its neighbours.

On row 3 both controls should be fixed in the vertial direction but fill horizontally.

This gives us the following.

![Example Layout](StripDiagnostic-4.png)

The following program reproduces the screenshot above.

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new StripLayout(false, new Margin(3)));
		
		Composite sendRow = new Composite(shell, SWT.NO_TRIM);
		sendRow.setLayout(new StripLayout(true, new Margin(0, 0, 0, 3)));
		sendRow.setLayoutData(new StripData(true, false));
		
		Button sendButton = new Button(sendRow, SWT.DEFAULT);
		sendButton.setLayoutData(new StripData(false, false));
		sendButton.setText("Send");
		
		Text sendText = new Text(sendRow, SWT.SINGLE | SWT.BORDER);
		sendText.setLayoutData(new StripData(true, false, new Margin(3, 0, 3, 0)));
		
		Combo lineEnding = new Combo(sendRow, SWT.SIMPLE);
		lineEnding.setItems(new String[] {"None", "Cr", "Lr", "CrLf"});
		lineEnding.select(0);
		
		Text outputText = new Text(shell, SWT.MULTI | SWT.BORDER);
		outputText.setLayoutData(new StripData(true, true));
		
		Composite statusRow = new Composite(shell, SWT.NO_TRIM);
		statusRow.setLayout(new StripLayout(true, new Margin(0, 3, 0, 3)));
		statusRow.setLayoutData(new StripData(true, false));

		Text leftStatus = new Text(statusRow, SWT.BORDER);
		leftStatus.setLayoutData(new StripData(true,false, new Margin(0, 0, 2, 0)));
		
		Text rightStatus = new Text(statusRow, SWT.BORDER);
		rightStatus.setLayoutData(new StripData(true,false, new Margin(1, 0, 0, 0)));
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

A feature that can be seen in the above code that has not yet been explained is "margin".
The layout data for each control may have a left, top, right, and bottom margin which will
be applied when laying out the control.
