package net.jetblack.swt.layout;

import org.eclipse.swt.SWT;

public final class StripData {

	public final boolean fillWidth;
	public final boolean fillHeight;
	public final Size size;
	public final Margin margin;
	public boolean exclude = false;
	
	public StripData () {
		this(false, false);
	}

	public StripData (boolean fillWidth, boolean fillHeight) {
		this(fillWidth, fillHeight, SWT.DEFAULT, SWT.DEFAULT, 0);
	}

	public StripData (boolean fillWidth, boolean fillHeight, int width, int height) {
		this(fillWidth, fillHeight, width, height, 0);
	}

	public StripData (boolean fillWidth, boolean fillHeight, int width, int height, int margin) {
		this(fillWidth, fillHeight, width, height, margin, margin, margin, margin);
	}
	
	public StripData (boolean fillWidth, boolean fillHeight, int width, int height, int left, int top, int right, int bottom) {
		this(fillWidth, fillHeight, new Size(width, height), new Margin(left, top, right, bottom));
	}

	public StripData (boolean fillWidth, boolean fillHeight, Size size) {
		this(fillWidth, fillHeight, size, new Margin(0));
	}
	
	public StripData (boolean fillWidth, boolean fillHeight, Margin margin) {
		this(fillWidth, fillHeight, new Size(SWT.DEFAULT, SWT.DEFAULT), margin);
	}
	
	public StripData (boolean fillWidth, boolean fillHeight, Size size, Margin margin) {
		this.fillWidth = fillWidth;
		this.fillHeight = fillHeight;
		this.size = size;
		this.margin = margin;
	}
	
	private String getName () {
		String string = getClass().getName();
		int index = string.lastIndexOf ('.');
		if (index == -1) return string;
		return string.substring (index + 1, string.length ());
	}

	public String toString () {
		String string = getName ()+" {";
		if (fillWidth) string += "fillWidth="+fillWidth+" ";
		if (fillHeight) string += "fillHeight="+fillHeight+" ";
		if (size.width != SWT.DEFAULT) string += "width="+size.width+" ";
		if (size.height != SWT.DEFAULT) string += "height="+size.height+" ";
		if (exclude) string += "exclude="+exclude+" ";
		string = string.trim();
		string += "}";
		return string;
	}
}
