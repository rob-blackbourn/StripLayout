package net.jetblack.swt.layout;

public class Margin {
	
	public final int left;
	public final int top;
	public final int right;
	public final int bottom;

	public Margin(int left, int top, int right, int bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}
	
	public Margin(int margin) {
		this(margin, margin, margin, margin);
	}
}
