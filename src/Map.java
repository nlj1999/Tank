import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

/* �����ͼ */
public class Map {
	
	/* ���������� */
	public int spawn_x;
	public int spawn_y;
	
	/* ǽ */
	List<Wall> walls = new ArrayList<Wall>();
	/* �з�̹�˳�ʼλ�� */
	List<Tank> tanks = new ArrayList<Tank>();
	/* �ҷ�̹�� */
	Tank myTank = null;
	/* Ѫ�� */
	List<Recovery> recovers = new ArrayList<Recovery>();
	/* �ӵ� */
	List<Missile> missiles = new ArrayList<Missile>();
	/* ��ը */
	List<Explode> explode = new ArrayList<Explode>();
	
	
	public Map(){
		super();
		/* ��߽� */
		Wall wall1 = new Wall(0, 0, 30, 600);
		/* �ϱ߽� */
		Wall wall2 = new Wall(0, 0, 600, 30);
		/* �ұ߽� */
		Wall wall3 = new Wall(570, 0, 30, 600);
		/* �±߽� */
		Wall wall4 = new Wall(0, 570, 600, 30);
		
		this.walls.add(wall1);
		this.walls.add(wall2);
		this.walls.add(wall3);
		this.walls.add(wall4);
	}
	
	
	
	/* ��������ص�ǰ��ͼ */
	public void connect2client(GameFrame gf) {
		gf.set_myTank(myTank);
	}
	
	/* ���ǽ */
	public void add_wall(Wall w) {
		walls.add(w);
	}

	public void add_wall(int x, int y, int width, int height) {
		Wall w = new Wall(x, y, width, height);
		walls.add(w);
	}
	
	/* ��ӳ����� */
	public void add_spawn(int x, int y) {
		spawn_x = x;
		spawn_y = y;
		myTank = new Tank(x, y, false, Color.red, Tank.Direction.STOP, this);
		tanks.add(myTank);
	}
	
	/* ��ӵ��� */
	public void add_enemy(int x, int y) {
		Tank enemy = new Tank(x, y, true, Color.blue, Tank.Direction.D, this);
		tanks.add(enemy);
	}
	
	/* ���Ѫ�� */
	public void add_recovery() {
		Recovery recovery = new Recovery();
		this.recovers.add(recovery);
	}
	
	public void add_recovery(int x, int y) {
		Recovery recovery = new Recovery(x, y);
		this.recovers.add(recovery);
	}
	
	/* �����¼� */
	public void process() {
		for(Tank t: tanks) {
			t.move();
			t.hitTanks(tanks);
			for(Wall w: walls) {
				t.hitWall(w);
			}
		}
		for(Recovery r: recovers) {
			myTank.recovery(r);
		}
		for(Missile m: missiles) {
			m.move();
			m.hitTanks(tanks);
			for(Wall w: walls) {
				m.hitWall(w);
			}
		}
		for(Tank t: tanks) {
			t.hitTanks(tanks);
			for(Wall w: walls) {
				t.hitWall(w);
			}
		}
	}
	
	/* ����ͼԪ�� */
	public void draw(Graphics g) {
		
		for(Wall w: walls) {
			w.draw(g);
		}
		for(int i = 0; i < recovers.size(); i++) {
			recovers.get(i).draw(g);
		}
		//System.out.println(missiles.size());
		for(int i = 0; i < missiles.size(); i++) {
			missiles.get(i).draw(g);
		}
		//System.out.println(tanks.size());
		for(int i = 0; i < tanks.size(); i++) {
			tanks.get(i).draw(g);
		}
		for(int i = 0; i < explode.size(); i++) {
			explode.get(i).draw(g);
		}
		
		
	}
	
	
	
}
