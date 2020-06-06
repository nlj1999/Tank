
/* ฒÝดิ */
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.Icon;

public class Grass {
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
	
	public Grass() {
		x = r.nextInt(TankClient.SCREEN_WIDTH/2)+100;
		y = r.nextInt(TankClient.SCREEN_HEIGHT/2)+100;
		w = h = 15;
	}
	
	public Grass(int x, int y) {
		this.x = x;
		this.y = y;
		w = h = 15;
	}
	
	public void draw(Graphics g) {
		if(!live)
			return;
		Color c = g.getColor();
		g.setColor(Color.green);
		int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        int nPoints = 3;
        
        xPoints[0]=x;yPoints[0]=y+MapItem.h;
        xPoints[1]=x+MapItem.w/6;yPoints[1]=y;
        xPoints[2]=x+MapItem.w/3;yPoints[2]=y+MapItem.h;
        
        Polygon polygon = new Polygon(xPoints, yPoints, nPoints);
        g.drawPolygon(polygon);
        g.fillPolygon(polygon);
        
        xPoints[0]=x+MapItem.w/3;yPoints[0]=y+MapItem.h;
        xPoints[1]=x+MapItem.w/2;yPoints[1]=y;
        xPoints[2]=x+2*MapItem.w/3;yPoints[2]=y+MapItem.h;
        
        polygon = new Polygon(xPoints, yPoints, nPoints);
        g.drawPolygon(polygon);
        g.fillPolygon(polygon);
        
        xPoints[0]=x+2*MapItem.w/3;yPoints[0]=y+MapItem.h;
        xPoints[1]=x+5*MapItem.w/6;yPoints[1]=y;
        xPoints[2]=x+MapItem.w;yPoints[2]=y+MapItem.h;
        
        polygon = new Polygon(xPoints, yPoints, nPoints);
        g.drawPolygon(polygon);
        g.fillPolygon(polygon);
        
		g.setColor(c);
	}

	
	public Rectangle get_rect() {
		return new Rectangle(x, y, w, h);
	}
	
	public class ObjectIcon implements Icon {
		
		 @Override
		 public void paintIcon(Component c, Graphics g, int x, int y) {
			 Grass.this.x = x;
			 Grass.this.y = y;
			 Grass.this.draw(g);
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