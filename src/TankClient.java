import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import java.awt.Frame;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TankClient extends JFrame{
	/* 缓存画面 */
	Image OffScreenImage = null;
	/* 屏幕宽高 */
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	Tank myTank = new Tank(500, 400, false, Color.red, Tank.Direction.STOP, this);
	List<Missile> missiles = new ArrayList<Missile>();
	List<Explode> explode = new ArrayList<Explode>();
	List<Tank> tanks = new ArrayList<Tank>();
	
	/* 四面墙 */
	Wall wall1 = new Wall(10, 0, 20, 600, this);
	Wall wall2 = new Wall(10, 30, 600, 20, this);
	Wall wall3 = new Wall(600, 0, 20, 600, this);
	Wall wall4 = new Wall(10, 580, 600, 20, this);
	
	Recovery r = new Recovery();
	
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
	}
	
	private void launchFrame() {
		for(int i = 0; i < 5; i++) {
			tanks.add(new Tank(50+40*(i+1), 50, true, Color.blue, Tank.Direction.D, this));
		}
		this.setLocation(400,100);
		this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
		this.setResizable(false);
		this.setTitle("Tank");
		this.setBackground(Color.BLACK);
		this.setVisible(true);
		this.setFocusable(true);
		new Thread(new PaintThread()).start();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(new KeyMoniton());
		
	}
	
	@Override
	public void paint(Graphics g_screen) {
		
		/* 先在幕后画好当前状态 */
		if (OffScreenImage == null)
			OffScreenImage = this.createImage(SCREEN_WIDTH, SCREEN_HEIGHT);
		Graphics g = OffScreenImage.getGraphics();
		super.paint(g);
		myTank.draw(g);
		wall1.draw(g);
		wall2.draw(g);
		wall3.draw(g);
		wall4.draw(g);
		r.draw(g);
		myTank.recovery(r);
		myTank.hitWall(wall1);
		myTank.hitWall(wall2);
		myTank.hitWall(wall3);
		myTank.hitWall(wall4);
		
		for(int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitWall(wall1);
			m.hitWall(wall2);
			m.hitWall(wall3);
			m.hitWall(wall4);
			m.hitTank(myTank);
			m.draw(g);
		}
		for(int i = 0; i < explode.size(); i++) {
			explode.get(i).draw(g);
		}
		for(int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.draw(g);
			t.hitTanks(tanks);
			t.hitWall(wall1);
			t.hitWall(wall2);
			t.hitWall(wall3);
			t.hitWall(wall4);
			
		}
		g.drawString("missiles count: "+missiles.size(), 650, 50);
		g.drawString("explode count: "+explode.size(), 650, 70);
		g.drawString("tanks count: "+tanks.size(), 650, 90);
		g.drawString("myTank Life: "+myTank.get_life(), 650, 110);
		g.drawString("移动: 方向键", 650, 130);
		g.drawString("开火: 空格键", 650, 150);
		
		//将画好的画面画到屏幕上
		g_screen.drawImage(OffScreenImage, 0, 0, null);
	}
	
	
	private class PaintThread implements Runnable{
		@Override
		public void run() {
			while(true) {
				//System.out.println("!");
				repaint();
				try {
					Thread.sleep(50);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class KeyMoniton extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			myTank.KeyPressed(e);
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			super.keyReleased(e);
			myTank.keyReleased(e);
		}
	}

}
