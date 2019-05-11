/**
 *
 * @author brom
 */

package DCAD;

import java.awt.Color;
import java.awt.Graphics;

import org.jgroups.util.UUID;

/**
 *
 * @author brom
 */
public class GObject {
	private Shape s;
	private Color c;
	private int x, y, width, height;

	private UUID objectID;
	// Note that the x and y coordinates are relative to the top left corner of the
	// graphics context in which the object is to be drawn - NOT the top left corner
	// of the GUI window.

	public GObject() {

	}

	public GObject(Shape s, Color c, int x, int y, int width, int height) {
		this.s = s;
		this.c = c;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		objectID = UUID.randomUUID();
	}

	public void setShape(Shape s) {
		this.s = s;
	}

	public void setColor(Color c) {
		this.c = c;
	}

	public void setCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public Shape getShape() {
		return s;
	}

	public Color getColor() {
		return c;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setObjectID(UUID objectID) {
		this.objectID = objectID;
	}

	public UUID getObjectID() {
		return this.objectID;
	}

	public void draw(Graphics g) {
		g.setColor(c);
		int drawX = x, drawY = y, drawWidth = width, drawHeight = height;

		// Convert coordinates and dimensions if objects are not drawn from top left
		// corner to
		// bottom right.
		if (width < 0) {
			drawX = x + width;
			drawWidth = -width;
		}

		if (height < 0) {
			drawY = y + height;
			drawHeight = -height;
		}

		// Use string comparison to allow comparison of shapes even if the objects
		// have different nodes of origin

		if (s.toString().compareTo(Shape.OVAL.toString()) == 0) {
			g.drawOval(drawX, drawY, drawWidth, drawHeight);
		} else if (s.toString().compareTo(Shape.RECTANGLE.toString()) == 0) {
			g.drawRect(drawX, drawY, drawWidth, drawHeight);
		} else if (s.toString().compareTo(Shape.LINE.toString()) == 0) {
			g.drawLine(x, y, x + width, y + height);
		} else if (s.toString().compareTo(Shape.FILLED_RECTANGLE.toString()) == 0) {
			g.fillRect(drawX, drawY, drawWidth, drawHeight);
		} else if (s.toString().compareTo(Shape.FILLED_OVAL.toString()) == 0) {
			g.fillOval(drawX, drawY, drawWidth, drawHeight);
		}
	}

	public int compareTo(GObject arg0) {
		if (this.s.equals(arg0.getShape())) {
			if (this.c == arg0.getColor()) {
				if (this.height == arg0.getHeight()) {
					if (this.width == arg0.getWidth()) {
						if (this.x == arg0.getX()) {
							if (this.y == arg0.getY()) {
								return 0;
							}
							return this.y - arg0.getY();
						}
						return this.x - arg0.getX();
					}
					return this.width - arg0.getWidth();
				}
				return this.height - arg0.getHeight();
			}
			return this.c.toString().compareTo(arg0.getColor().toString());
		}
		return this.s.getType().compareTo(arg0.getShape().getType());
	}

	@Override
	public boolean equals(Object obj) {
		if (this.compareTo((GObject) obj) == 0) {
			return true;
		}
		return false;
	}
}
