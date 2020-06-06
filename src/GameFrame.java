import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import java.awt.Frame;
import javax.swing.*;


public class GameFrame extends JPanel{
	/* 容器位置偏移 */
	public static final int OFFSET_WIDTH = 0;
	public static final int OFFSET_HEIGHT = 0;
	/* 容器框架宽高 */
	public static final int PANEL_WIDTH = 800;
	public static final int PANEL_HEIGHT = 600;
	/* 游戏框架宽高 */
	public static final int GAME_WIDTH = 600;
	public static final int GAME_HEIGHT = 600;

	Tank myTank = null;
	Map map = null;
	MapEditer editer = null;
	enum State {GAME, EDIT};
	State game_state = State.GAME;
	TankClient tc = null;
	
	public void launchFrame(TankClient tc) {
        this.tc = tc;
        
		this.setBounds(OFFSET_WIDTH, OFFSET_HEIGHT, PANEL_WIDTH+OFFSET_WIDTH, PANEL_HEIGHT+OFFSET_HEIGHT);
		this.setLayout(null);
		this.setFocusable(true);
		new Thread(new PaintThread()).start();
		this.addKeyListener(new KeyMoniton());
		this.addMouseListener(new MouseMoniton());
		this.addMouseMotionListener(new MouseMotionMoniton());
	}
	
	@Override
	public void paint(Graphics g) {
		

		super.paint(g);
		/* 游戏模式 */
		if(game_state == State.GAME) {
			/* 处理事件 */
			map.process();
			/* 画图 */
			map.draw(g);
			/* 提示信息 */
			g.setFont(new Font("楷体", Font.BOLD, 20));
			g.drawString("移动: 方向键", 620, 20);
			g.drawString("开火: 空格键", 620, 60);
			/* 胜利 */
			if(map.win()) {
				g.setFont(new Font("Times New Roman", Font.BOLD, 100));
				g.drawString("YOU WIN", 80, 300);
			}
			else if(map.lose()) {
				g.setFont(new Font("Times New Roman", Font.BOLD, 100));
				g.drawString("YOU LOSE", 40, 300);
			}

		}
		/* 编辑模式 */
		else if(game_state == State.EDIT) {
			editer.draw(g);
		}
		
	}
	
	
	private class PaintThread implements Runnable{
		@Override
		public void run() {
			while(true) {
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
			if(myTank != null) {
				myTank.KeyPressed(e);
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			super.keyReleased(e);
			if(myTank != null) {
				myTank.keyReleased(e);
			}
		}
		
	}
	

    private class MouseMoniton extends MouseAdapter{
    	@Override
    	public void mousePressed(MouseEvent e){
    		//System.out.println("!");
    		if(game_state == State.EDIT) {
    			editer.mousePressed(e);
    		}
    	}
    	@Override
    	public void mouseClicked(MouseEvent e){
    		if(game_state == State.EDIT) {
    			editer.mouseClicked(e);
    		}   
    	} 
    	@Override
    	public void mouseReleased(MouseEvent e){
    		if(game_state == State.EDIT) {
    			editer.mouseReleased(e);
    		}   
    	}
    }
    
    private class MouseMotionMoniton extends MouseMotionAdapter{
    	@Override
    	public void mouseDragged(MouseEvent e){
    		if(game_state == State.EDIT) {
    			editer.mouseDragged(e);
    		}   
    	}
    }
	
	public void set_myTank(Tank myTank) {
		this.myTank = myTank;
	}
	
	public void set_map(Map map) {
		this.map = map;
	}
	
	public void set_editer(MapEditer editer) {
		this.editer = editer;
	}
	
	public void switch2game(Map map) {
		this.requestFocusInWindow();
		this.set_map(map);
		this.myTank = map.myTank;
		game_state = State.GAME;
		this.removeAll();
	}
	
	public void switch2editer(MapEditer editer) {
		this.requestFocusInWindow();
		this.set_editer(editer);
		game_state = State.EDIT;
		editer.active(this);
	}
	
	public boolean GameMode() {
		return game_state == State.GAME;
	}
	
	public boolean EditMode() {
		return game_state == State.EDIT;
	}
}
