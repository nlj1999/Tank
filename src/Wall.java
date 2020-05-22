import java.awt.Graphics;
import java.awt.Rectangle;

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
}
