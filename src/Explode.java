import java.awt.Color;
import java.awt.Graphics;

/* ��ը */
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
		/* fillOval�ǻ����ε��ڽ���Բ����Ҫ���������Ի����������͵�Ч�� */
		g.fillOval(x-r/2, y-r/2, r, r);
		
		g.setColor(c);
		step++;
	}
}
