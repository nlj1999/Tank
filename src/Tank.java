import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.BasicStroke;
import java.util.List;
import java.util.Random;
import java.lang.Math;

/* ̹�� */
public class Tank {
	/* ��ǰλ�� */
	public int x, y;
	/* �ϴ�λ�� */
	private int last_x, last_y;
	/* ��С */
	public static final int width = 30;
	public static final int height = 30;
	/* �ƶ��ٶ� */
	public int speed_x = 5;
	public int speed_y = 5;
	/* ̹����ɫ */
	private Color color;
	/* �ķ��򰴼� */
	private boolean L=false, U=false, R=false, D=false;
	/* �ϳɰ˷��� */
	enum Direction {L, LU, U, RU, R, RD, D, LD, STOP};
	/* ̹�˷��� */
	private Direction dir = Direction.STOP;
	/* ��Ͳ���� */
	private Direction p_dir = Direction.D;
	
	private boolean enemy;
	private boolean live = true;
	private static Random r = new Random();
	private static int step = r.nextInt(10)+5;
	private int life = 100;
	/* Ѫ�� */
	private BloodBar bb = new BloodBar();
	
	private Map map;
	
	public Tank(int x, int y, boolean enemy, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
		this.enemy = enemy;
	}
	
	public Tank(int x, int y, boolean enemy, Color color, Direction dir, Map map) {
		this(x, y, enemy, color);
		this.dir = dir;
		this.map = map;
	}
	
	public int get_life() {
		return life;
	}
	
	public void set_life(int Life) {
		this.life = Life;
	}
	
	public boolean is_enemy() {
		return enemy;
	}
	
	public void set_enemy(boolean enemy) {
		this.enemy = enemy;
	}
	
	public boolean is_live() {
		return live;
	}
	
	public void set_live(boolean live) {
		this.live = live;
	}
	
	/* ��ȡ�е� */
	public int get_center_x() {
		return x+width/2;
	}
	
	public int get_center_y() {
		return y+height/2;
	}
	
	/* ��̹�� */
	public void draw(Graphics g) {
		if(!live) {
			if(enemy) {
				map.tanks.remove(this);
			}
			return;
		}
		/* �ݴ�ԭ�����ʵ���ɫ */
		Color c = g.getColor();
		/* ��ʱ����Բ */
		g.setColor(color);
		g.fillOval(x, y, width, height);
		/* ��Ѫ�� */
		if(!enemy){
			bb.draw(g);
		}
		
		g.setColor(c);
		
		/* ����Ͳ */
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(3.0f));
		int tank_center_x = get_center_x();
		int tank_center_y = get_center_y();
		int p_length = Tank.width;
		int p_length_sqrt = (int)(Double.valueOf(p_length)*Math.sqrt(0.5));
		switch(p_dir) {
		case L:
			g2.drawLine(tank_center_x, tank_center_y, tank_center_x-p_length, tank_center_y);
			break;
		case LU:
			g2.drawLine(tank_center_x, tank_center_y, tank_center_x-p_length_sqrt, tank_center_y-p_length_sqrt);
			break;
		case U:
			g2.drawLine(tank_center_x, tank_center_y, tank_center_x, tank_center_y-p_length);
			break;
		case RU:
			g2.drawLine(tank_center_x, tank_center_y, tank_center_x+p_length_sqrt, tank_center_y-p_length_sqrt);
			break;
		case R:
			g2.drawLine(tank_center_x, tank_center_y, tank_center_x+p_length, tank_center_y);
			break;
		case RD:
			g2.drawLine(tank_center_x, tank_center_y, tank_center_x+p_length_sqrt, tank_center_y+p_length_sqrt);
			break;
		case D:
			g2.drawLine(tank_center_x, tank_center_y, tank_center_x, tank_center_y+p_length);
			break;
		case LD:
			g2.drawLine(tank_center_x, tank_center_y, tank_center_x-p_length_sqrt, tank_center_y+p_length_sqrt);
			break;
		/* һ�㲻����STOP */
		case STOP:
			System.out.println("p_dir error");
			g2.drawLine(tank_center_x, tank_center_y, tank_center_x, tank_center_y);
			break;
		}
		
	}
	
	/* �����¼� */
	public void KeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_UP:
			U=true;
			break;
		case KeyEvent.VK_DOWN:
			D=true;
			break;
		case KeyEvent.VK_LEFT:
			L=true;
			break;
		case KeyEvent.VK_RIGHT:
			R=true;
			break;
		}
		locateDirection();
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_UP:
			U=false;
			break;
		case KeyEvent.VK_DOWN:
			D=false;
			break;
		case KeyEvent.VK_LEFT:
			L=false;
			break;
		case KeyEvent.VK_RIGHT:
			R=false;
			break;
		case KeyEvent.VK_SPACE:
			if(live)
				fire();
			break;
		case KeyEvent.VK_R:
			if(!this.live) {
				this.live=true;
				this.set_life(100);
			}
			break;
		}
		locateDirection();
	}
	
	/* ����ϳ� */
	void locateDirection() {
		if(L&&!R&&!U&&!D) dir = Direction.L;
		else if(!L&&R&&!U&&!D) dir = Direction.R;
		else if(!L&&!R&&U&&!D) dir = Direction.U;
		else if(!L&&!R&&!U&&D) dir = Direction.D;
		else if(L&&!R&&U&&!D) dir = Direction.LU;
		else if(L&&!R&&!U&&D) dir = Direction.LD;
		else if(!L&&R&&U&&!D) dir = Direction.RU;
		else if(!L&&R&&!U&&D) dir = Direction.RD;
		else dir = Direction.STOP;
	}
	
	/* �ƶ� */
	void move() {
		last_x = x;
		last_y = y;
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
		
		/* �����ж� */
		if (x < 5) x = 5;
		if (y < 25) y = 25;
		if (x+width > TankClient.SCREEN_WIDTH-5)
			x = TankClient.SCREEN_WIDTH-5;
		if (y+width > TankClient.SCREEN_HEIGHT-5)
			y = TankClient.SCREEN_HEIGHT-5;
		
		/* ��Ͳ����ı� */
		if(dir != Direction.STOP)
			p_dir = dir;
		
		
		/* ��������ƶ� */
		if(enemy) {
			Direction[] dirs = Direction.values();
			if(step == 0) {
				step = r.nextInt(10)+5;
				int randomNumber = r.nextInt(dirs.length);
				dir = dirs[randomNumber];
			}
			step--;
			if(r.nextInt(33)>30)
				this.fire();
		}
		
	}
	
	/* ���� */
	public void fire() {
		int x = this.x + width/2 - Missile.width/2;
		int y = this.y + height/2 - Missile.height/2;
		map.missiles.add(new Missile(p_dir, color, x, y, enemy, map));
	}
	
	public Rectangle get_rect() {
		return new Rectangle(x, y, width, height);
	}
	
	private void stay() {
		x = last_x;
		y = last_y;
	}
	
	/* ײǽ */
	public boolean hitWall(Wall w) {
		if(this.live&&this.get_rect().intersects(w.get_rect())) {
			this.stay();
			return true;
		}
		return false;
	}
	
	/* ̹����ײ */
	public boolean hitTanks(List<Tank> tanks) {
		for(int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if(this != t) {
				if(this.live&&t.is_live()&&this.get_rect().intersects(t.get_rect())){
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}
	
	/* ����Missile����Ŀ��� */
	public Missile fire(Direction dir) {
		if(!live)
			return null;
		int x = this.x + width/2 - Missile.width/2;
		int y = this.y + height/2 - Missile.height/2;
		Missile m = new Missile(dir, color, x, y, enemy, map);
		map.missiles.add(m);
		return m;
	}
	
	/* Ѫ�� */
	private class BloodBar{
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.red);
			g.drawRect(x, y-20, width, 10);
			int w = width*life/100;
			g.fillRect(x, y-20, w, 10);
			g.setColor(c);
		}
	}
	
	/* ��Ѫ����Ѫ */
	public boolean recovery(Recovery r) {
		if(this.live&&r.is_live()&&this.get_rect().intersects(r.get_rect())){
			this.set_life(100);
			r.set_live(false);
			return true;
		}
		return false;
	}
	
}