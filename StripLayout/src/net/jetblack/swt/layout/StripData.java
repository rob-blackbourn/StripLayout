package net.jetblack.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;

public final class StripData {

	public boolean fillWidth = false;
	public boolean fillHeight = false;
	public int width = SWT.DEFAULT;
	public int height = SWT.DEFAULT;
	public boolean exclude = false;
	
	public StripData () {
	}

	public StripData (boolean fillWidth, boolean fillHeight, int width, int height) {
		this.fillWidth = fillWidth;
		this.fillHeight = fillHeight;
		this.width = width;
		this.height = height;
	}

	public StripData (boolean fillWidth, boolean fillHeight, Point point) {
		this (fillWidth, fillHeight, point.x, point.y);
	}

	public StripData(boolean fillWidth, boolean fillHeight) {
		this(fillWidth, fillHeight, SWT.DEFAULT, SWT.DEFAULT);
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
		if (width != SWT.DEFAULT) string += "width="+width+" ";
		if (height != SWT.DEFAULT) string += "height="+height+" ";
		if (exclude) string += "exclude="+exclude+" ";
		string = string.trim();
		string += "}";
		return string;
	}
}
