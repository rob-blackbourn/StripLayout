package net.jetblack.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public final class StripLayout extends Layout {

	public boolean isHorizontal = true;
	public int spacing = 3;
	public int marginLeft = 3;
	public int marginTop = 3;
	public int marginRight = 3;
	public int marginBottom = 3;

	public StripLayout() {
	}
	
	public StripLayout(boolean isHorizontal) {
		this.isHorizontal = isHorizontal;
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
		
		Control [] children = composite.getChildren ();
		int count = 0;
		for (int i=0; i<children.length; ++i) {
			Control child = children [i];
			StripData data = (StripData) child.getLayoutData ();
			if (data == null) {
				child.setLayoutData(data = new StripData());
			}
			if (!data.exclude) {
				children [count++] = children [i];
			} 
		}

		if (count == 0) {
			return new Point (marginLeft + marginRight, marginTop + marginBottom);
		}
		
		int fillChildCount = 0;
		int maxChild = 0, minStrip = 0;
		Point[] sizes = new Point[count];
		for (int i=0; i<count; ++i) {
			Control child = children [i];
			
			StripData data = (StripData)child.getLayoutData();
			
			if ((data.fillWidth && isHorizontal) || (data.fillHeight && !isHorizontal)) {
				++fillChildCount;
			}

			sizes[i] = child.computeSize(data.width, data.height, flushCache);
			if (isHorizontal) {
				maxChild = Math.max(maxChild, sizes[i].y);
				minStrip += sizes[i].x;
			} else {
				maxChild = Math.max(maxChild, sizes[i].x);
				minStrip += sizes[i].y;
			}
		}
		
		minStrip += spacing * (count - 1);
		
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
				childX = x;
				childY = data.fillHeight ? y : y + (maxChild - size.y) / 2;
			} else {
				childHeight = data.fillHeight ? size.y + childFill : size.y;
				childWidth = data.fillWidth ? (isLayout ? clientArea.width : maxChild) : size.x;
				childX = data.fillWidth ? x : x + (maxChild - size.x) / 2;
				childY = y;
			}
			
			if (isLayout) {
				child.setBounds(childX, childY, childWidth, childHeight);
			}
			
			if (isHorizontal) {
				x += childWidth + spacing;
			} else {
				y += childHeight + spacing;
			}
		}
		
		x += maxChild;
		y += maxChild;
		
		return new Point(x, y);
	}
	
	private Rectangle getCroppedClientArea(Composite composite, boolean isLayout) {
		if (isLayout) {
			Rectangle clientArea = composite.getClientArea();
			return new Rectangle(clientArea.x + marginLeft, clientArea.y + marginTop, clientArea.width - (marginLeft + marginRight), clientArea.height - (marginTop + marginBottom));
		} else {
			return new Rectangle(0, 0, 0, 0);
		}
	}
}
