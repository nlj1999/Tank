import java.awt.Color;
import java.util.*;

public class MapProducer {

	/* 0: 空地， 1: 我方坦克, 2: 敌方坦克, 3: 墙, 4: 血包 */
	int[][] grid;
	int n = 20;
	public static final int scale = 30;
	
	MapProducer(){
		grid = new int[n][n];
	}
	
	public Map produce() {
		Map result = new Map();
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++) {
				int x = i * scale, y = j * scale;
				switch(grid[i][j]) {
					case 1:
						result.add_spawn(x, y);
						break;
					case 2:
						result.add_enemy(x, y);
						break;
					case 3:
						result.add_wall(new Wall(x, y, 30, 30));
						break;
					case 4:
						result.add_recovery(x, y);
						break;
				}
			}
		return result;
	}
	
	public void deleteMyTank() {
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
				if(grid[i][j] == 1)
					grid[i][j] = 0;
	}
	
}
