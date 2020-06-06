import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.Icon;

/* ǽ */
public class Wall {
	int x, y, w, h;
	
	public Wall(int x, int y, int w, int h) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public Rectangle get_rect() {
		return new Rectangle(x, y, w, h);
	}
	
	public void draw(Graphics g) {
		g.fillRect(x,  y,  w, h);
	}
	
	public class ObjectIcon implements Icon {
		
		 @Override
		 public void paintIcon(Component c, Graphics g, int x, int y) {
			 Wall.this.x = x;
			 Wall.this.y = y;
			 Wall.this.draw(g);
		 }

		 @Override
		 public int getIconWidth() {
			 return Wall.this.w;
		 }

		 @Override
		 public int getIconHeight() {
			 return Wall.this.h;
		 }
	}
}
