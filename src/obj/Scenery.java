package obj;

import java.awt.image.BufferedImage;

public class Scenery extends SuperScenery{
	public Scenery(String name, int type, int x, int y, BufferedImage image) {
		this.type = type;
		this.xpos = x;
		this.ypos = y;
		this.name = name;
		this.image = image;
	}
	

	
}
