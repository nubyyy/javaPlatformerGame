package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import obj.Platform;
import obj.PlatformManager;

public class GamePanel extends JPanel implements Runnable {
	public int FPS = 60;
	public int screenWidth = 1200;
	public int screenHeight = 800;
	
	public KeyHandler keyH = new KeyHandler();
	Thread thread;
	public Background background;
	public PlatformManager platformManager;
	public Player player;
//	public Platform plat[];
	public ColliderHandler collisionHandler = new ColliderHandler(this);
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		this.setDoubleBuffered(true);
		initDefaults();
		
	}
	public void initDefaults() {
		player = new Player(this,keyH);
		background = new Background(this);
		platformManager = new PlatformManager(this, 10, 5);
//		this.plat = new Platform[3];
//		this.plat[0] = new Platform(this,100,250,5);
//		this.plat[1] = new Platform(this,700,300,5);
//		this.plat[2] = new Platform(this,1000,300,10);
	}
	
	public void startGameThread() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		//Divides 1 second by FPS
		double drawInterval = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;

		while(thread!=null) {
			
			
			update();
			
			repaint(); //paintComponent() method
			
			
			//Sleeps the fps
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000; //Converts nano -> milli
				if(remainingTime <0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	} 
	
	public void update() {
		player.update();
		platformManager.update();
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		background.draw(g2);
		g2.drawRect(0, 0, 10, 10);
		platformManager.draw(g2);
		
//		for(Platform i:plat) {
//			i.draw(g2);
//		}
		player.draw(g2);
		
		
		g2.dispose();
		
	}
}
