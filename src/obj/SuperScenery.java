package obj;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperScenery {
	public int type;
	public BufferedImage image;
	public String name;
	public int xpos;
	public int ypos;
	public int scale;
	
	public void draw(Graphics2D g2,GamePanel gp) {
		if(image.getWidth() + xpos < gp.screenWidth && xpos > 0) {
			g2.drawImage(image,xpos,ypos,scale,scale,null);
			
		}
	}

	
}
