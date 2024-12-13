package obj;

import java.awt.Graphics2D;

import main.GamePanel;

public class PlatformManager {
	GamePanel gp;
	public Platform platforms[];
	public int arraySize;
	public final int RESERVED_ARRAY_SPACE = 1000;
	public int platformsOnScreen;
	public int lastPlatform;
	int maxLength;
	public PlatformManager(GamePanel gp,int numOfPlatforms,int maxLength) {
		this.gp = gp;
		this.platforms = new Platform[RESERVED_ARRAY_SPACE];
		this.maxLength = maxLength;
		this.arraySize = 100;
		
		
		setupInit();
	}
	public void setupInit() {
		platforms[0] = new Platform(gp,0, 500, 10,1);
		for(int i = 1; i<arraySize-1; i++) {
			
			int rand = (int)(Math.random() * 201) + (gp.screenWidth/2)-201;
			int width = (int)(Math.random()*10)+5;
			platforms[i] = new Platform(gp,i*750, rand, width,i+1);
		}
	}
	public Platform getPlatform(int i) {
		return platforms[i];
	}
	
	public void update() {
		
	}
	public void draw(Graphics2D g2) {
		int offset = gp.player.x;
		platformsOnScreen = 0;
		for(int i = 0; i<arraySize-1; i++) {
			if(platforms[i].platX +  platforms[i].getWidth() +offset >0 && platforms[i].platX+offset < gp.screenWidth ) {
				platforms[i].draw(g2);
				platformsOnScreen += 1;
				lastPlatform = platforms[i].id;
			}
		}
	}
}
