package utils;

import java.awt.Rectangle;

import main.GameObject;

public class Physics {

	public static boolean collides(GameObject player, GameObject enemy) {
		Rectangle playerRect = new Rectangle((int) player.getX(),
				(int) player.getY(), player.getWidth(), player.getHeight());
		Rectangle enemyRect = new Rectangle((int) enemy.getX(),
				(int) enemy.getY(), enemy.getWidth(), enemy.getHeight());
		if (playerRect.intersects(enemyRect)) {
			return true;
		} else {
			// TODO Auto-generated method stub
			return false;
		}
	}
	public static void applyGravity(GameObject player){
		if(player.moveDown()){
			player.setY(player.getY() + (Clock.delta()*player.getSpeed())); 
		}
	}

}
