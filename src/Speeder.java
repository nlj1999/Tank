import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.sun.javafx.tk.Toolkit;

/* 加速器 */
public class Speeder {
	int x, y, w, h;
	private boolean live = true;
	private static Random r = new Random();
	private Map map;
	
	public boolean is_live() {
		return live;
	}
	
	public void set_live(boolean live) {
		this.live = live;
	}
	
	public Speeder() {
		x = r.nextInt(TankClient.SCREEN_WIDTH/2)+100;
		y = r.nextInt(TankClient.SCREEN_HEIGHT/2)+100;
		w = h = 15;
	}
	
	public Speeder(int x, int y) {
		this.x = x;
		this.y = y;
		w = h = 15;
	}
	
	/* 画加速器 */
	public void draw(Graphics g) {
		if(!live)
			return;
		Color c = g.getColor();
		g.setColor(Color.orange);
		g.fillOval(x+(MapItem.w-w)/2, y+(MapItem.h-h)/2, w, h);
		g.setColor(c);
	}


	
	/* 加速器被吃了之后随机生成一个 */
	public void drop_speeder() {
		if(!live) {
			x = r.nextInt(TankClient.SCREEN_WIDTH/2)+100;
			y = r.nextInt(TankClient.SCREEN_HEIGHT/2)+100;
			live = true;
		}
	}
	
	public Rectangle get_rect() {
		return new Rectangle(x, y, w, h);
	}
	
	public class ObjectIcon implements Icon {
		
		 @Override
		 public void paintIcon(Component c, Graphics g, int x, int y) {
			 Speeder.this.x = x;
			 Speeder.this.y = y;
			 Speeder.this.draw(g);
		 }

		 @Override
		 public int getIconWidth() {
			 return MapItem.w;
		 }

		 @Override
		 public int getIconHeight() {
			 return MapItem.h;
		 }
	}
	
}
