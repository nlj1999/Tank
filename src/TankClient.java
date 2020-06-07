import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.awt.Frame;
import javax.swing.*;


public class TankClient extends JFrame{
	/* 屏幕宽高 */
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 660;
	
	Map map=null;
	MapProducer init_map=null;
	GameFrame gf=null;
	MapEditer editer=null;
	
	
	
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
	}
	
	private void launchFrame() {
		gf =new GameFrame();
		init_map = new DefaultMap();
		map = init_map.produce();
		editer = new MapEditer();
		
		this.setLayout(null);
		this.setLocation(400,100);
		this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
		this.setResizable(false);
		this.setTitle("Tank");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(gf);
		
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JButton newGame = new JButton("新游戏");
        JButton buttonGame = new JButton("回到游戏");
        JButton buttonEdit = new JButton("地图编辑器");
        JButton buttonLoad = new JButton("加载地图");
        
        newGame.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		map = init_map.produce();
        		gf.switch2game(map);
        	}
        });
        
        buttonGame.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(map != gf.map){
        			System.out.println("!");
        			
        		}
        		gf.switch2game(map);
        	}
        });
        
        buttonEdit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(editer == null) {
        			editer = new MapEditer();
        		}
        		gf.switch2editer(editer);
        	}
        });
        
        buttonLoad.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String basePath="savedMap/";
        		String[] files=new File(basePath).list();
        		String filePath = (String) JOptionPane.showInputDialog(
        				null,"请选择一个地图:\n", "加载地图", 
        				JOptionPane.PLAIN_MESSAGE, null, files, "");
        		if(filePath == null || filePath.equals("")) {
        			return;
        		}
        		filePath = "savedMap/"+filePath;
        		BufferedReader reader = null;
        		String laststr = "";
        		try {
        			FileInputStream fileInputStream = new FileInputStream(filePath);
        			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
        			reader = new BufferedReader(inputStreamReader);
        			String tempString = null;
        			while ((tempString = reader.readLine()) != null) {
        				laststr += tempString;
        			}
        			reader.close();
        		} catch (IOException e1) {
        			e1.printStackTrace();
        		} finally {
        			if (reader != null) {
        				try {
        					reader.close();
        				} catch (IOException e1) {
        					e1.printStackTrace();
        				}
        			}
        		}
        		if(gf.GameMode()) {
        			init_map = new MapProducer(laststr);
        			newGame.doClick();
        		}
        		else if(gf.EditMode()) {
        			editer.loadMap(laststr);
        		}
        	}
        });
        
        menuBar.add(newGame);
        menuBar.add(buttonGame);
        menuBar.add(buttonEdit);
        menuBar.add(buttonLoad);
        this.validate();
		gf.launchFrame(this);
		gf.switch2game(map);
	}
}
