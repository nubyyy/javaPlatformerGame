package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import obj.Platform;
import utils.PlayerAnimation;
import utils.UtilityTool;

public class Player {
	GamePanel gp;
	public KeyHandler keyHandler;
	public int x = 0;
	int y = 0;
	int oldX = x;
	float slidingSpeed = 0;
	float gravity = 0f;
	int sprintSpeed = 10	;
	int walkSpeed = 1;
	boolean foward = true;
	boolean sideCollision = false;
	public boolean inAir = false;
	public boolean doubleJump = false;
	public boolean releasedJump = false;
	public boolean walking,running,idle,punching,sliding,slidingStandUp;
	final int FRAME_SIZE = 64;
	final int CHARECTER_SIZE = 48;
	final double scale = 1.5;
	int idleCounter = 0;
	int spriteCounter = 0;
	int spriteJumpCounter = 0;
	public Rectangle playerCollider;
	int[] colliderOffset = {36,36};
	int[] colliderSize = {20,64};
	int[] colliderJumpOffset = {36,60};
	int[] colliderJumpSize = {20,20};
	public boolean colliding = true;
	BufferedImage originalPlayerImage; 
	PlayerAnimation animationHandler;
	public Player(GamePanel gp,KeyHandler keyHandler) {
		this.gp = gp;
		this.keyHandler = keyHandler;
		this.animationHandler = new PlayerAnimation(this);
		this.playerCollider = new Rectangle(x+colliderOffset[0],y+colliderOffset[1],colliderSize[0],colliderSize[1]);
	}
	
	public void update() {
		
		animationHandler.update();
		handleInputs();
		for(int i = 0; i<gp.platformManager.arraySize-1;i++) {
			colliding = false;
			if(gp.collisionHandler.isColliding(playerCollider, gp.platformManager.platforms[i].getCollider())) {
				colliding = true;
				gp.platformManager.platforms[i].colision = true;
			} else {
				gp.platformManager.platforms[i].colision = false;
			}
		}
		
		if(!inAir) {
			playerCollider.reshape(100+colliderOffset[0], y+colliderOffset[1], colliderSize[0], colliderSize[1]);
		} else {
			playerCollider.reshape(100+colliderJumpOffset[0], y+colliderJumpOffset[1], colliderJumpSize[0], colliderJumpSize[1]);
		}
		if(sliding) {
			x-=(foward)?slidingSpeed:-slidingSpeed;
		}
		inAir =true;
			for(int i = Math.abs(gp.platformManager.lastPlatform - gp.platformManager.platformsOnScreen) ; i<gp.platformManager.lastPlatform;i++) {
				if(gp.platformManager.platforms[i].colision) {
//					System.out.println("Colliding");
				if(gp.platformManager.platforms[i].getCollider().y>y+colliderSize[1]&&gp.platformManager.platforms[i].getCollider().x<x-playerCollider.getWidth()) {
//					x=(int) (gp.plat.getCollider().x+playerCollider.getWidth());
					x=oldX-1;
					gravity += 0.2;
					y += (int)gravity;
					sideCollision = true;
					System.out.println("NSJSJJSJJSDJJSD");
				} else if(gp.platformManager.platforms[i].getCollider().y<y+colliderSize[1]&&gp.platformManager.platforms[i].getCollider().x+100<x) {
//					x=gp.plat.getCollider().x;
					x=oldX-1;
					gravity += 0.0;
					y += (int)gravity;
					sideCollision = true;
					System.out.println("NIGE NGE NIGEINGER");
				} else if(gp.platformManager.platforms[i].getCollider().y>y){
					y = gp.platformManager.platforms[i].getCollider().y-(int)(FRAME_SIZE*scale);
					gravity = 0;
					if(inAir) {
						y+=3;
					}
					sideCollision = false;
//					System.out.println("On platform");
					inAir = false;
					doubleJump=false;
				} 
				} 
				
			}
			if(inAir) {
				gravity += 0.5;
				y += (int)gravity;
				oldX=x;
				if(y>gp.screenHeight) {
					x = 1;
					y = 1;
					inAir = true;
					gp.background.reset();
					colliding = false;
				}
//				System.out.println(gravity);
			}
			
		 
		
		  
		
	}
	public void draw(Graphics2D g2) {
		if(gp.keyH.debug) {
			g2.setColor(Color.red);
			g2.draw(playerCollider);
		}
		if(foward) {
			g2.drawImage(animationHandler.imageToDraw(), 100,y+animationHandler.imageOffset,null);
		} else if(!foward) {
			g2.drawImage(animationHandler.imageToDraw(), 100+animationHandler.imageToDraw().getWidth(),y+animationHandler.imageOffset,-animationHandler.imageToDraw().getWidth(),animationHandler.imageToDraw().getHeight(),null);
		}
	}
	public void handleInputs() {
		if(keyHandler.downPressed) {
			sliding = true;
			slidingSpeed -= (slidingSpeed <= 0) ? 0:.1;
			
		} else {
			sliding = false;
			slidingSpeed = 10;
			
		}
		if(keyHandler.upPressed|| keyHandler.leftPressed|| keyHandler.rightPressed) {
			idle = false;
			if(keyHandler.rightPressed && !sliding && !sideCollision) {
				x -= (keyHandler.shiftPressed) ? sprintSpeed:walkSpeed;
				walking = !keyHandler.shiftPressed;
				running = keyHandler.shiftPressed;
				foward = true;
			}
			if(keyHandler.upPressed && !inAir && !sliding) {
				gravity = -8;
				y-=1;
				inAir=true;
				sliding = false;
				punching = false;
				releasedJump=false;
//				System.out.println("Jumped");
			} else if (!keyHandler.upPressed&&inAir){
				releasedJump = true;
			}
			if(releasedJump&&keyHandler.upPressed&&inAir&&!doubleJump) {
				doubleJump = true;
				gravity = -10;
				y-=1;
//				System.out.println("Double Jumped");
			}
			if(keyHandler.leftPressed && !sliding  && !sideCollision) {
				x+= (keyHandler.shiftPressed) ? sprintSpeed:walkSpeed;
				walking = !keyHandler.shiftPressed;
				running = keyHandler.shiftPressed;
				foward = false;
			}
			if(keyHandler.spacePressed && !sliding) {
				punching = true;
			}
			
		}else {
			walking = false;
			running = false;
			idle = true;
			if(keyHandler.spacePressed) {
				punching = true;
			}
		}
		
	}
}
