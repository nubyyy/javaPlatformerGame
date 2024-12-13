package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.UtilityTool;

public class Background {
	GamePanel gp;
	BufferedImage background[];
	BufferedImage foreGround[];
	int offsets[];
	public Background(GamePanel gp) {
		this.gp = gp;
		this.background = new BufferedImage[2];
		this.offsets = new int[2];
		setup();
	}
	public void reset() {
		offsets[1] = 0;
		offsets[0] = gp.screenWidth;
	}
	public void setup() {
		try {
		
		background[0] = loadImage("Blue_Background_LINEAR");
		offsets[0] = gp.screenWidth;
		background[1] = loadImage("Blue_Background_LINEAR");
		offsets[1] = 0;
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public BufferedImage loadImage(String name) throws IOException {
		UtilityTool uTool = new UtilityTool();
		return uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/background/"+name+".png")),gp.screenWidth,gp.screenHeight);
	}
	
	public void draw(Graphics2D g2) {
		if((offsets[0]+(gp.player.x/10))+gp.screenWidth<0){
			offsets[0] += gp.screenWidth;
		}
		if((offsets[1]+(gp.player.x/10))+(gp.screenWidth)<0){
			offsets[1] += gp.screenWidth;
		}
		for(int i = 0; i<background.length; i++) {

			g2.drawImage(background[i], (gp.player.x/10)+offsets[i],0,null);
		}
		
	}
}
