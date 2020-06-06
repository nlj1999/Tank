import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;



/* 地图编辑器 */
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
	
	public void loadMap(String mapStr) {
		gen = new MapProducer(mapStr);
		mapUpdate();
	}
	
	public void mapUpdate() {
		map = gen.produce();
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
	
	public void get_choice(){
		grid_x = new ArrayList<Integer>();
		grid_y = new ArrayList<Integer>();
		if(choose_x > 0) {
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
					grid_x.add(x);
					grid_y.add(y);
				}
		}
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
	
	public void active(GameFrame gf) {
		choose_x = choose_y = -1;
		
		MyTankButton mytankButton = new MyTankButton();
		EnemyTankButton enemytankButton = new EnemyTankButton();
		WallButton wallButton = new WallButton();
		RecoveryButton recoveryButton = new RecoveryButton();
		RemoveButton removeButton = new RemoveButton();
		ActiveButton activeButton = new ActiveButton(gf);
		ClearButton clearButton = new ClearButton();
		SaveButton saveButton = new SaveButton();
		gf.add(mytankButton);
		gf.add(enemytankButton);
		gf.add(wallButton);
		gf.add(recoveryButton);
		gf.add(removeButton);
		gf.add(activeButton);
		gf.add(clearButton);
		gf.add(saveButton);
		gf.validate();
	}
	
	public class MapItemButton extends JButton{
		MapItemButton(){
			this.setVerticalTextPosition(SwingConstants.BOTTOM);//
			this.setHorizontalTextPosition(SwingConstants.CENTER);//
		}
	}
	
	public class MyTankButton extends MapItemButton{
		MyTankButton(){
			Tank.ObjectIcon icon = new Tank(0, 0, false, Color.red, Tank.Direction.L, map).new ObjectIcon();
			this.setText("我方坦克");
			this.setIcon(icon);
			this.setBounds(600, 0, 90, 70);
			this.setPreferredSize(new Dimension(90,70));
			this.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		get_choice();
	        		if(grid_x.size() > 0) {
	        			gen.deleteMyTank();
	        			gen.grid[grid_x.get(0)/30][grid_y.get(0)/30] = 1;
	        		}
	        		mapUpdate();
	        	}
	        });
		}
	}
	
	public class EnemyTankButton extends MapItemButton{
		EnemyTankButton(){
			Tank.ObjectIcon icon = new Tank(0, 0, true, Color.blue, Tank.Direction.L, map).new ObjectIcon();
			this.setText("敌方坦克");
			this.setIcon(icon);
			this.setBounds(690, 0, 90, 70);
			this.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		get_choice();
	        		for(int i = 0; i < grid_x.size(); i++) {
	        			gen.grid[grid_x.get(i)/30][grid_y.get(i)/30] = 2;
	        		}
	        		mapUpdate();
	        	}
	        });
		}
	}
	
	public class WallButton extends MapItemButton{
		WallButton(){
			Wall.ObjectIcon icon = new Wall(0, 0, 30, 30).new ObjectIcon();
			this.setText("墙");
			this.setIcon(icon);
			this.setBounds(600, 70, 90, 70);
			this.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		get_choice();
	        		for(int i = 0; i < grid_x.size(); i++) {
	        			gen.grid[grid_x.get(i)/30][grid_y.get(i)/30] = 3;
	        		}
	        		mapUpdate();
	        	}
	        });
		}
	}
	
	public class RemoveButton extends MapItemButton{
		RemoveButton(){
			this.setText("清除");
			this.setBounds(650, 300, 90, 40);
			this.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		get_choice();
	        		for(int i = 0; i < grid_x.size(); i++) {
	        			gen.grid[grid_x.get(i)/30][grid_y.get(i)/30] = 0;
	        		}
	        		mapUpdate();
	        	}
	        });
		}
	}
	
	public class RecoveryButton extends MapItemButton{
		RecoveryButton(){
			Recovery.ObjectIcon icon = new Recovery().new ObjectIcon();
			this.setText("补给包");
			this.setIcon(icon);
			this.setBounds(690, 70, 90, 70);
			this.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		get_choice();
	        		for(int i = 0; i < grid_x.size(); i++) {
	        			gen.grid[grid_x.get(i)/30][grid_y.get(i)/30] = 4;
	        		}
	        		mapUpdate();
	        	}
	        });
		}
	}
	
	public class ClearButton extends MapItemButton{
		ClearButton(){
			this.setText("清空地图");
			this.setBounds(650, 490, 90, 40);
			this.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		gen.clear();
	        		mapUpdate();
	        	}
	        });
		}
	}

	public class ActiveButton extends MapItemButton{
		GameFrame gf=null;
		ActiveButton(GameFrame gf){
			this.gf = gf;
			this.setText("启用地图");
			this.setBounds(650, 450, 90, 40);
			this.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		gf.tc.init_map.copy(gen);
	        	}
	        });
		}
	}
	
	public class SaveButton extends MapItemButton{
		SaveButton(){
			this.setText("保存地图");
			this.setBounds(650, 530, 90, 40);
			this.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		String mapName = (String)JOptionPane.showInputDialog(null,"请输入地图名：\n","保存地图",JOptionPane.PLAIN_MESSAGE,null,null,"我的地图"); 
	        		if(mapName == null || mapName.equals("")) {
	        			return;
	        		}
	        		File file = new File("savedMap/"+ mapName + ".txt");
	        		BufferedWriter writer = null;
	        		try {
	                    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), "UTF-8"));
	                    writer.write(gen.getString());
	                } catch (IOException e1) {
	                    e1.printStackTrace();
	                }finally {
	                    try {
	                        if(writer != null){
	                            writer.close();
	                        }
	                    } catch (IOException e1) {
	                        e1.printStackTrace();
	                    }
	                }
	               System.out.println("文件写入成功！");
	        	}
	        });
		}
	}
	
	
}
