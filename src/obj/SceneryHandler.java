package obj;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class SceneryHandler {
	public BufferedImage[] objectImages;
	public Scenery objects[];
	public int platformIDs[];
	GamePanel gp;
	public SceneryHandler(GamePanel gp) {
		this.objectImages = new BufferedImage[9];
		this.gp = gp;
		setup();
	}
	public void setup() {
		objectImages[0] = loadImage("PixelTree1");
		objectImages[1] = loadImage("PixelTree2");
		objectImages[2] = loadImage("PixelTree3");
		objectImages[3] = loadImage("PixelTree4");
		objectImages[4] = loadImage("PixelTree5");
		objectImages[5] = loadImage("PixelTree6");
		objectImages[6] = loadImage("Rock_1");
		objectImages[7] = loadImage("Rock_2");
		objectImages[8] = loadImage("Rock_3");
		
	}
	public BufferedImage loadImage(String name){
		try {
			return ImageIO.read(getClass().getResourceAsStream("/objects/"+name+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void draw(Graphics2D g2) {
		for(int i = 0; i<platformIDs.length;i++) {
			Platform platform = gp.platformManager.getPlatform(platformIDs[i]);
			int x =platform.getCollider().x+objects[i].xpos;
			int y = platform.getCollider().y;
			objects[i].draw(g2, gp);
			
		}
	}
}
