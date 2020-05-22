import java.awt.Color;
import java.util.*;

public class MapProducer {
	/* ���������� */
	public int spawn_x;
	public int spawn_y;
	
	/* ǽ */
	List<Wall> walls = new ArrayList<Wall>();
	/* �з�̹�˳�ʼλ�� */
	List<Integer> tanks_x = new ArrayList<Integer>();
	List<Integer> tanks_y = new ArrayList<Integer>();
	/* Ѫ�� */
	List<Integer> recovers_x = new ArrayList<Integer>();
	List<Integer> recovers_y = new ArrayList<Integer>();
	
	public Map produce() {
		Map result = new Map();
		for(Wall w: walls) {
			result.add_wall(w);
		}
		for(int i = 0; i < tanks_x.size(); i++) {
			result.add_enemy(tanks_x.get(i), tanks_y.get(i));
		}
		for(int i = 0; i < recovers_x.size(); i++) {
			result.add_recovery(recovers_x.get(i), recovers_y.get(i));
		}
		result.add_spawn(spawn_x, spawn_y);
		return result;
	}
	
}
