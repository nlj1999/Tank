import java.awt.Graphics;
import java.awt.Rectangle;

/* ǽ */
public class Wall {
	int x, y, w, h;
	private TankClient tc;
	
	public Wall(int x, int y, int w, int h, TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}
	
	public Rectangle get_rect() {
		return new Rectangle(x, y, w, h);
	}
	
	public void draw(Graphics g) {
		g.fillRect(x,  y,  w, h);
	}
}
