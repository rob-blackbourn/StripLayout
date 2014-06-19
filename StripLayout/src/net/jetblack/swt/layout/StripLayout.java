package net.jetblack.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public final class StripLayout extends Layout {

	public final boolean isHorizontal;
	public final Margin margin;

	public StripLayout() {
		this(true);
	}
	
	public StripLayout(boolean isHorizontal) {
		this(isHorizontal, 0, 0, 0, 0);
	}
	
	public StripLayout(boolean isHorizontal, int marginLeft, int marginTop, int marginRight, int marginBottom)
	{
		this(isHorizontal, new Margin(marginLeft, marginTop, marginRight, marginBottom));
	}
	
	public StripLayout(boolean isHorizontal, Margin margin) {
		this.isHorizontal = isHorizontal;
		this.margin = margin;
	}
	
	@Override
	protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
		Point extent = layoutOrSize(composite, false, flushCache);
		if (wHint != SWT.DEFAULT) extent.x = wHint;
		if (hHint != SWT.DEFAULT) extent.y = hHint;
		return extent;
	}

	@Override
	protected void layout(Composite composite, boolean flushCache) {
		layoutOrSize(composite, true, flushCache);
	}

	@Override
	protected boolean flushCache (Control control) {
		return true;
	}
	
	private Point layoutOrSize(Composite composite, boolean isLayout, boolean flushCache) {

		Rectangle clientArea = getCroppedClientArea(composite, isLayout);
		
		// find all non-excluded child controls.
		Control [] children = composite.getChildren ();
		int count = 0;
		for (int i=0; i<children.length; ++i) {
			Control child = children [i];
			StripData data = (StripData) child.getLayoutData ();
			
			// ensure all controls have layout data.
			if (data == null) {
				child.setLayoutData(data = new StripData());
			}
			
			if (!data.exclude) {
				children [count++] = children [i];
			} 
		}

		if (count == 0) {
			return new Point (margin.left + margin.right, margin.top + margin.bottom);
		}
		
		int fillChildCount = 0;
		int maxChild = 0;
		int minStrip = 0;
		Point[] sizes = new Point[count];
		for (int i=0; i<count; ++i) {
			Control child = children [i];
			
			StripData data = (StripData)child.getLayoutData();
			
			if ((data.fillWidth && isHorizontal) || (data.fillHeight && !isHorizontal)) {
				++fillChildCount;
			}

			Point size = child.computeSize(data.size.width, data.size.height, flushCache);
			
			int width = size.x + data.margin.left + data.margin.right;
			int height = size.y + data.margin.top + data.margin.bottom;
			
			if (isHorizontal) {
				maxChild = Math.max(maxChild, height);
				minStrip += width;
			} else {
				maxChild = Math.max(maxChild, width);
				minStrip += height;
			}
			
			sizes[i] = size;
		}
				
		int emptySpace = (isHorizontal ? clientArea.width : clientArea.height) - minStrip;
		int childFill = fillChildCount == 0 || emptySpace < fillChildCount ? 0 : emptySpace / fillChildCount;
		
		int x = clientArea.x, y = clientArea.y;
		for (int i = 0; i < count; ++i) {
			
			Control child = children[i];
			StripData data = (StripData)child.getLayoutData();
			Point size = sizes[i];
			
			int childWidth, childHeight, childX, childY;
			if (isHorizontal) {
				childWidth = data.fillWidth ? size.x + childFill : size.x;
				childHeight = data.fillHeight ? (isLayout ? clientArea.height : maxChild) : size.y;
				childX = x + data.margin.left;
				childY = data.fillHeight ? y : y + (maxChild - size.y) / 2;
			} else {
				childHeight = data.fillHeight ? size.y + childFill : size.y;
				childWidth = data.fillWidth ? (isLayout ? clientArea.width : maxChild) : size.x;
				childX = data.fillWidth ? x : x + (maxChild - size.x) / 2;
				childY = y + data.margin.top;
			}
			
			if (isLayout) {
				child.setBounds(childX, childY, childWidth, childHeight);
			}
			
			if (isHorizontal) {
				x += childWidth + data.margin.left + data.margin.right;
			} else {
				y += childHeight + data.margin.top + data.margin.bottom;
			}
		}
		
		x += maxChild + margin.right;
		y += maxChild + margin.bottom;
		
		return new Point(x, y);
	}
	
	private Rectangle getCroppedClientArea(Composite composite, boolean isLayout) {
		if (isLayout) {
			Rectangle clientArea = composite.getClientArea();
			return new Rectangle(clientArea.x + margin.left, clientArea.y + margin.top, clientArea.width - (margin.left + margin.right), clientArea.height - (margin.top + margin.bottom));
		} else {
			return new Rectangle(0, 0, 0, 0);
		}
	}
}
