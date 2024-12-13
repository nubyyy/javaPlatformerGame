package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Player;

public class PlayerAnimation {
	private final int FRAME_SIZE = 64;
	private final double FRAME_SCALING = 1.5;
	private int animationType = 0;
	private int selector = 0;
	Player player;
	BufferedImage originalPlayerImage;
	public int imageOffset = 0;
	BufferedImage playerRunningFrames[] = new BufferedImage[11];
	BufferedImage playerWalkingFrames[] = new BufferedImage[8];
	BufferedImage playerJumpingFrames[] = new BufferedImage[8];
	BufferedImage playerIdleFrames[] = new BufferedImage[4];
	BufferedImage playerPunchingFrames[] = new BufferedImage[7];
	BufferedImage playerSlidingFrames[] = new BufferedImage[9];
	boolean standUpDone = false;
	public PlayerAnimation(Player player) {
		this.player = player;
		setup();
	}
	public BufferedImage[] cutPiece(int length,int x,int y) throws IOException {
		UtilityTool uTool = new UtilityTool();
		y *= FRAME_SIZE;
		BufferedImage returnArray[] = new BufferedImage[length];
		for(int i = 0; i<length; i++) {
			returnArray[i] = uTool.scaleImage( originalPlayerImage.getSubimage((i+x)*FRAME_SIZE, y, FRAME_SIZE, FRAME_SIZE), (int)(FRAME_SIZE*FRAME_SCALING), (int)(FRAME_SIZE*FRAME_SCALING));
		}
		return returnArray;
		
	}
	public void setup() {
		
		try {
			originalPlayerImage = ImageIO.read(getClass().getResourceAsStream("/player/TheDummyAnim-SpriteSheet.png"));
			playerRunningFrames = cutPiece(11,0,4);
			playerWalkingFrames = cutPiece(8,0,3);
			playerIdleFrames = cutPiece(4,0,0);
			playerJumpingFrames = cutPiece(4,4,8);
			playerPunchingFrames = cutPiece(7,0,2);
			playerSlidingFrames = cutPiece(9,0,6);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage imageToDraw() {
		return switch(animationType) {
		case 0 -> playerIdleFrames[selector];
		case 1 -> playerWalkingFrames[selector];
		case 2 -> playerRunningFrames[selector];
		case 3 -> playerJumpingFrames[selector];
		case 4 -> playerPunchingFrames[selector];
		case 5 -> playerSlidingFrames[selector];
		default -> originalPlayerImage;
	};
	}
	
	public void update() {
		imageOffset = 0;
		if(player.sliding) {
			animationType = 5;
			sliding();
		} else if(player.inAir) {
				animationType = 3;
				jumping();
		} else if(!standUpDone) {
			animationType = 5;
			slidingStandUp();
		} else if(player.punching) {
			animationType = 4;
			punching();
		} else if(player.idle) {
			animationType = 0;
			idle();
		}
		else if(player.walking) {
			animationType = 1;
			walking();
		} else if(player.running) {
			animationType = 2;
			running();
		}
		
		
		
		
	}
	int idleCounter = 0;
	public void idle() {
		selector = (selector>playerIdleFrames.length-1) ? 0:selector;
		idleCounter++;
		if(idleCounter %15 == 0) {
			selector = (selector<playerIdleFrames.length-1) ? selector+1:0;
		}
	}
	int walkingCounter = 0;
	public void walking() {
		selector = (selector>playerWalkingFrames.length-1) ? 0:selector;
		walkingCounter ++;
		if(walkingCounter%10==0) {
			selector = (selector<playerWalkingFrames.length-1) ? selector+1:0;
		}
	}
	int runningCounter = 0;
	public void running() {
		runningCounter ++;
		if(runningCounter%8==0) {
			selector = (selector<playerRunningFrames.length-1) ? selector+1:0;
		}
	}
	int jumpingCounter = 0;
	public void jumping() {
		selector = (selector>playerJumpingFrames.length-1) ? 0:selector;
		jumpingCounter ++;
		imageOffset = 15;
		
		if(jumpingCounter%15==0) {
			selector = (selector<playerJumpingFrames.length-1) ? selector+1:0;
		}
	}
	int punchingCounter = 0;
	public void punching() {
		selector = (selector>playerPunchingFrames.length-1) ? 0:selector;
		punchingCounter ++;
		if(punchingCounter%5==0) {
			selector = (selector<playerPunchingFrames.length-1) ? selector+1:0;
			if(selector == playerPunchingFrames.length-1) {
				player.punching = false;
				selector = 0;
			}
		}
	}
	int slidingCounter = 0;
	public void sliding() {
		selector = (selector>playerSlidingFrames.length-1) ? 0:selector;
		slidingCounter++;
		if(slidingCounter%6 == 0) {
			selector = (selector<5) ? selector+1:5;
		}
		standUpDone = false;
	}
	public void slidingStandUp() {
		selector = (selector>playerSlidingFrames.length-1) ? 0:selector;
		slidingCounter++;
		if(slidingCounter%6 == 0) {
			selector = (selector<5) ? 5:selector+1;
			if(selector == playerSlidingFrames.length-1) {
				standUpDone = true;
			}
		}
	}
}
