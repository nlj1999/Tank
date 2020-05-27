import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.*;


/* µØÍ¼±à¼­Æ÷ */
public class MapEditer {
	public static final int grid_width = 30;
	public static final int grid_height = 30;
	MapProducer gen;
	Map map;
	List<Integer> grid_x;
	List<Integer> grid_y;
	
	int choose_x;
	int choose_y;
	int mouse_x;
	int mouse_y;
	
	MapEditer() {
		gen = new MapProducer();
		map = gen.produce();
		choose_x = -1;
		choose_y = -1;
	}
	
	public void draw(Graphics g) {
		map.draw(g);
		g.setColor(Color.gray);
		for(int x = 0; x < 600; x += grid_width)
			for(int y = 0; y < 600; y += grid_height) {
				draw_grid(g, x, y);
			}
		draw_choose(g);
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			choose_x = e.getX();
			choose_y = e.getY();
			mouse_x = e.getX();
			mouse_y = e.getY();
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		System.out.println(e.getX());
		mouse_x = e.getX();
		mouse_y = e.getY();
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mouseDragged(MouseEvent e) {
		mouse_x = e.getX();
		mouse_y = e.getY();
	}
	
	public void draw_grid(Graphics g, int x, int y) {
		g.drawRect(x, y, grid_width, grid_height);
	}
	
	public void draw_choose(Graphics g) {
		if(choose_x > 0) {
			g.setColor(Color.red);
			int start_x = Math.max(30, Math.min(choose_x, mouse_x));
			int start_y = Math.max(30, Math.min(choose_y, mouse_y));
			int end_x = Math.min(540, Math.max(choose_x, mouse_x));
			int end_y = Math.min(540, Math.max(choose_y, mouse_y));
			start_x = start_x / 30 * 30;
			start_y = start_y / 30 * 30;
			end_x = (end_x+30) / 30 * 30;
			end_y = (end_y+30) / 30 * 30;
			for(int x = start_x; x < end_x; x += 30)
				for(int y = start_y; y < end_y; y += 30) {
					draw_grid(g, x, y);
				}
		}
	}
	
	
}
