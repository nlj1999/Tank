import java.awt.Color;
import java.awt.Graphics;

/* 爆炸 */
public class Explode {
	int x, y;
	private boolean live = true;
	int step = 0;
	int [] diameter = new int[] {4, 7, 12, 18, 26, 32, 49, 56, 65, 77, 80, 50, 40, 30, 14, 6};
	
	private Map map;
	public Explode(int x, int y, Map map) {
		super();
		this.x = x;
		this.y = y;
		this.map = map;
	}
	
	public void draw(Graphics g) {
		if(!live) return;
		if(step == diameter.length) {
			live = false;
			step = 0;
			map.explode.remove(this);
			return;
		}
		Color c = g.getColor();
		
		g.setColor(Color.orange);
		int r = diameter[step];
		/* fillOval是画矩形的内接椭圆，需要计算中心以画出中心膨胀的效果 */
		g.fillOval(x-r/2, y-r/2, r, r);
		
		g.setColor(c);
		step++;
	}
}
