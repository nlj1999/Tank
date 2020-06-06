import java.awt.Color;

/* д╛хо╣ьм╪ */
public class DefaultMap extends MapProducer{
	
	public DefaultMap(){
		super();
		grid[15][15] = 1;
		grid[10][10] = 4;
		for(int i = 0; i < 5; i++) {
			grid[i+2][2] = 2;
		}
	}
}
