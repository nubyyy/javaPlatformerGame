package main;

import java.awt.Rectangle;

public class ColliderHandler {
	GamePanel gp;
	public ColliderHandler(GamePanel gp) {
		this.gp = gp;
	}
	public boolean isColliding(Rectangle obj1, Rectangle obj2) {
		return obj1.intersects(obj2);
	}
}
