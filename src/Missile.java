import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.List;

/* 子弹 */
public class Missile {
	Tank.Direction dir;
	Color c;
	int x, y;
	public int speed_x = 15;
	public int speed_y = 15;
	public static final int width = 15;
	public static final int height = 15;
	private boolean live = true;
	private boolean enemy;
	
	private TankClient tc;
	
	public Missile(Tank.Direction dir, Color c, int x, int y) {
		super();
		this.dir = dir;
		this.x = x;
		this.y = y;
		this.c = c;
	}
	
	public Missile(Tank.Direction dir, Color c, int x, int y, boolean enemy, TankClient tc) {
		this(dir, c, x, y);
		this.enemy = enemy;
		this.tc = tc;
	}
	
	public boolean is_live() {
		return live;
	}
	
	public void set_live(boolean live) {
		this.live = live;
	}
	
	/* 画子弹 */
	public void draw(Graphics g) {
		if(!live) {
			tc.missiles.remove(this);
			return;
		}
		Color d = g.getColor();
		/* 暂时先画个圆 */
		g.setColor(c);
		g.fillOval(x, y, width, height);
		
		g.setColor(d);
		move();
	}
	
	/* 子弹移动 */
	public void move() {
		switch(dir) {
		case L:
			x-=speed_x;
			break;
		case LU:
			x-=speed_x;
			y-=speed_y;
			break;
		case U:
			y-=speed_y;
			break;
		case RU:
			x+=speed_x;
			y-=speed_y;
			break;
		case R:
			x+=speed_x;
			break;
		case RD:
			x+=speed_x;
			y+=speed_y;
			break;
		case D:
			y+=speed_y;
			break;
		case LD:
			x-=speed_x;
			y+=speed_y;
			break;
		case STOP:
			break;
		}
		
		if(x<0||y<0||x>TankClient.SCREEN_WIDTH||y>TankClient.SCREEN_HEIGHT)
			live = false;
	}
	
	
	public Rectangle get_rect() {
		return new Rectangle(x, y, width, height);
	}
	
	/* 碰撞判定 */
	public boolean hitTank(Tank t) {
		if(this.live&&this.get_rect().intersects(t.get_rect())&&t.is_live()&&this.enemy!=t.is_enemy()) {
			if(t.is_enemy()) {
				/* 对敌军一击必杀 */
				t.set_live(false);
			}
			else {
				/* 击中我方扣血20 */
				t.set_life(t.get_life()-20);
				if(t.get_life() <= 0)
					t.set_live(false);
			}
			this.live = false;
			tc.explode.add(new Explode(t.get_center_x(), t.get_center_y(), tc));
			return true;
			
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks) {
		for(int i = 0; i < tanks.size(); i++) {
			if(hitTank(tanks.get(i)))
				return true;
		}
		return false;
	}
	
	/* 撞墙消失 */
	public boolean hitWall(Wall w) {
		if(this.live&&this.get_rect().intersects(w.get_rect())) {
			this.live = false;
			return true;
		}
		return false;
	}
}
