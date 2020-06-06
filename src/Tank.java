import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Random;

import javax.swing.Icon;

import java.lang.Math;

/* ̹�� */
public class Tank extends MapItem{
	/* ��ǰλ�� */
	public int x, y;
	/* �ϴ�λ�� */
	private int last_x, last_y;
	/* ��С */
	public static final int width = 30;
	public static final int height = 30;
	/* ��ʻ�Ұ뾶 */
	public static final int inner_r = 10;
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
	/* �ڲݴ��� */
	private boolean inGrass = false;
	
	
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
		this.last_x = x;
		this.last_y = y;
	}
	
	public Tank(int x, int y, boolean enemy, Color color, Direction dir, Map map) {
		this(x, y, enemy, color);
		this.dir = dir;
		this.map = map;
		if(dir != Direction.STOP) {
			this.p_dir = dir;
		}
	}
	
	public int get_life() {
		return life;
	}
	
	public void set_life(int Life) {
		this.life = Life;
	}
	
	public void add_speed(int Speed) {
		this.speed_x += Speed;
		this.speed_y += Speed;
	}
	
	public void set_speed(int Speed) {
		this.speed_x = Speed;
		this.speed_y = Speed;
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
	
	public void set_x(int x) {
		this.x = x;
	}
	
	public void set_y(int y) {
		this.y = y;
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
        Rectangle rect = new Rectangle(x, y, width, height);
        Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.green);
        switch(p_dir) {
        case LU:
        case RU:
        case LD:
        case RD:
        	AffineTransform transform = new AffineTransform();
            transform.rotate(Math.toRadians(45), rect.getX() + rect.width / 2, rect.getY() + rect.height / 2);
            Shape transformed = transform.createTransformedShape(rect);
            g2.fill(transformed);
            break;
        default:
        	g2.fill(rect);
        }
        
		
		/* ��Ѫ�� */
		if(!enemy){
			bb.draw(g);
		}
		
		/* ����Ͳ */
		int tank_center_x = get_center_x();
		int tank_center_y = get_center_y();
		int p_length = Tank.width/2;
		int p_length_sqrt = (int)(Double.valueOf(p_length)*Math.sqrt(0.5));
		
		/* ��ʻ�ң�Բ��*/
		g.setColor(color);
		g.fillOval(tank_center_x - inner_r, tank_center_y - inner_r, inner_r*2, inner_r*2);
		
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(3.0f));
		
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
		
		g2.setStroke(new BasicStroke(1.0f));
		
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
		int tspeed_x,tspeed_y;
		if(inGrass) {
			tspeed_x=3;
			tspeed_y=3;
			inGrass=false;
		}
		else {
			tspeed_x=speed_x;
			tspeed_y=speed_y;
		}
		switch(dir) {
		case L:
			x-=tspeed_x;
			break;
		case LU:
			x-=tspeed_x;
			y-=tspeed_y;
			break;
		case U:
			y-=tspeed_y;
			break;
		case RU:
			x+=tspeed_x;
			y-=tspeed_y;
			break;
		case R:
			x+=tspeed_x;
			break;
		case RD:
			x+=tspeed_x;
			y+=tspeed_y;
			break;
		case D:
			y+=tspeed_y;
			break;
		case LD:
			x-=tspeed_x;
			y+=tspeed_y;
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
	
	/* �Լ��������� */
	public boolean speeder(Speeder s) {
		if(this.live&&s.is_live()&&this.get_rect().intersects(s.get_rect())){
			this.add_speed(5);
			s.set_live(false);
			return true;
		}
		return false;
	}
	
	/* �ݴ��м��� */
	public boolean grass(Grass gr) {
		if(this.live&&gr.is_live()&&this.get_rect().intersects(gr.get_rect())){
			inGrass=true;
			return true;
		}
		return false;
	}
	
	public class ObjectIcon implements Icon {
		
		 @Override
		 public void paintIcon(Component c, Graphics g, int x, int y) {
			 Tank.this.set_x(x);
			 Tank.this.set_y(y);
			 Tank.this.draw(g);
		 }

		 @Override
		 public int getIconWidth() {
			 return Tank.width;
		 }

		 @Override
		 public int getIconHeight() {
			 return Tank.height;
		 }
	}
	
}
