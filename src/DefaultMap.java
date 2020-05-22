import java.awt.Color;

/* д╛хо╣ьм╪ */
public class DefaultMap extends MapProducer{
	
	public DefaultMap(){
		super();
		spawn_x = 500;
		spawn_y = 400;
		for(int i = 0; i < 5; i++) {
			tanks_x.add(50+40*(i+1));
			tanks_y.add(60);
		}
		recovers_x.add(300);
		recovers_y.add(300);
	}
}
