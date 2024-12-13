package obj;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import utils.UtilityTool;

public class Platform {
	int platX, platY, width, height;
	int[] platform;
	BufferedImage tiles[] = new BufferedImage[8];
	private final int scale = 40; 
	private final int size = 16;
	public boolean colision = false;
	public int screenHeight = 800;
	public Rectangle collider;
	GamePanel gp;
	public int id;
	public Platform(GamePanel gp, int x, int y, int width, int id) {
		this.platX = x;
		this.gp = gp;
		this.platY = y;
		this.height = height;
		this.width = width;
		platform = new int[width];
		this.id = id;
		setup();
	}
	private BufferedImage loadImage(String name, int scaleX) throws IOException {
		UtilityTool uTool = new UtilityTool();
		return uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/tiles/"+name+".png")), scaleX, scaleX);
	}
	private BufferedImage loadImage(String name) throws IOException{
		return ImageIO.read(getClass().getResourceAsStream("/tiles/"+name+".png"));
	}
	private BufferedImage flipImage(String name, int scaleX, boolean t) throws IOException{
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+name+".png"));
		BufferedImage flippedImage = new BufferedImage(32,32,BufferedImage.TYPE_INT_RGB);
		for(int y = 0; y<flippedImage.getHeight();y++) {
			for(int x = 0; x<flippedImage.getWidth(); x++) {
				flippedImage.setRGB(x, y, 0);
			}
		}
		System.out.println(image.getHeight()+ " "+ image.getWidth());
		for (int row = image.getHeight()-1; row >0 ; row--) {
		    for (int col = 0; col < image.getWidth(); col++) {
		        int pixel = image.getRGB(col, row);
		        System.out.println(image.getWidth() - col - 1 + " "+ row);
		        flippedImage.setRGB(image.getWidth() - col - 1 , row, pixel);
		    }
		}
		return uTool.scaleImage(flippedImage, scaleX,scaleX);
	}
//	private BufferedImage flipImage(String name, int scaleX) throws IOExceptions{
//		BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+name+".png"));
//		BufferedImage flippedTile = new BufferedImage(image.getWidth(), image.getHeight(), image.getHeight());
//		Graphics2D g = flippedTile.createGraphics();
//		AffineTransform transform = AffineTransform.getScaleInstance(-1,1);
//		
//	}
	private void setup() {
		makeCollider();
		makePlatformArray();
		try {
			 tiles[1] = loadImage("Grass_Block_Bottom",scale);
//			 tiles[0] = flipImage("Grass_Block_Bottom",scale);
			 tiles[2] = loadImage("Grass_Block_Side",scale);
			 tiles[3] = loadImage("Grass_Block_Top",scale);
			 tiles[4] = loadImage("Grassy_Edge",scale);
			 tiles[5] = loadImage("Grassy_Fill",scale);
			 tiles[6] = loadImage("Grassy_Side",scale);
			 tiles[7] = loadImage("Grassy_Top",scale);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void makePlatformArray() {
		platform[0] = -4;
		for(int i = 1; i<platform.length-1;i++) {
			platform[i] = 7;
		}
		platform[platform.length-1] = 4;
	}
	//Makes a rectangle
	public void makeCollider() {
		int totalWidth = platform.length * scale;
		int totalHeight = gp.screenHeight-platY;
		collider = new Rectangle(platX,platY,totalWidth,totalHeight);
		
	}
	
	public int getWidth() {
		return platform.length * scale;
	}
	public int getHeight() {
		return gp.screenHeight-platY;
	}
	//Update the rectangle
	public void updateCollider() {
		int totalWidth = platform.length * scale;
		int totalHeight = gp.screenHeight-platY;
		collider.reshape(platX+gp.player.x,platY,totalWidth,totalHeight);
	}
	public Rectangle getCollider() {
		return collider;
	}
	public void draw(Graphics2D g2) {
		updateCollider();
		if(gp.keyH.debug) {
			g2.setColor(Color.blue);
			g2.draw(collider);
		}
		//Draw the top piece of the pillar
		for(int x =0; x<platform.length;x++) {
				BufferedImage tile = tiles[Math.abs(platform[x])];
				int width = (platform[x]<0) ? -tile.getWidth():tile.getWidth();
				int offset = (platform[x]<0) ? tile.getWidth():0;
				offset += gp.player.x;
				if((platX+offset)+(x*scale)+64>0 && platX+offset<gp.screenWidth) {
					g2.drawImage(tile,(platX+offset)+(x*scale),platY,width,tile.getHeight(),null);
				}
			
		}
		//Draw the pillar to the bottom of the screen
		int numOfTilesFromBottom = gp.screenWidth/tiles[1].getHeight();
		int[] pillarArray = new int[platform.length+1];
		pillarArray[0] = -6;
		for(int i = 1; i<pillarArray.length-1; i++) {
			pillarArray[i] = 5;
		}
		pillarArray[pillarArray.length-2] = 6;
		for(int y = 0; y<numOfTilesFromBottom;y++) {
			for(int x = 0; x<pillarArray.length-1; x++) {
				BufferedImage tile = tiles[Math.abs(pillarArray[x])];
				int width = (pillarArray[x]<0) ? -tile.getWidth():tile.getWidth();
				int offset = (pillarArray[x]<0) ? tile.getWidth():0;
				offset += gp.player.x;
				if((platX+offset)+(x*scale)+64>0 && platX+offset<gp.screenWidth) {
					g2.drawImage(tile,(platX+offset)+(x*scale),(tile.getHeight()*y)+(platY+tile.getHeight()),width,tile.getHeight(),null);
				}
			}
		}
		
	}
	
}
