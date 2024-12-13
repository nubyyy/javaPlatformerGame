package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.UtilityTool;

public class MainMenu {
	GamePanel gp;
	public MainMenu(GamePanel gp){
		this.gp = gp;
	}
	public BufferedImage loadImage(String name, int x,int y, int width, int height, int xScale, int yScale) throws IOException {
		BufferedImage image = ImageIO.read(getClass().getResourceAsStream("buttons/"+name+".png"));
		image = UtilityTool.scaleImage(image.getSubimage(x,y,width,height), xScale, yScale);
		return image;
	}
	public void setup() {
		
	}
	
	public void draw() {
		
	}
	
	public void drawText() {
		
	}
}
