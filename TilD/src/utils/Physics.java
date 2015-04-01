package utils;

import java.awt.Rectangle;
import main.Controller;
import main.GameObject;
import main.Tile;
import main.TileGrid;

public class Physics {

	public static boolean collidesObject(GameObject player, GameObject enemy) {
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
			player.setY(player.getY() + (Clock.delta()*player.getSpeed()*1.25f)); 
		}
		
	}

	public static boolean collidesTile(float x, float y, int width, int height, TileGrid grid, boolean player) {
		//TODO: use the actual size of tiles
		int tileWidth = Artist.getScaleX();
		int tileHeight = Artist.getScaleY();

		// tiles wide of object
		int TilesWide = width/tileWidth;
		if(width%tileWidth != 0){TilesWide++;}
		// tiles tall of object
		int TilesTall = height/tileHeight;
		if(height%tileHeight != 0){TilesTall++;}

		// check each tile covered
		Tile nextTile;
		for(int yCounter = 0, offX = 0, offY = 0 ; yCounter<TilesTall; yCounter++,offY+=tileHeight){
			for(int xCounter = 0; xCounter<TilesWide; xCounter++, offX+=tileWidth){
				// get the tile
				nextTile = grid.getTile(((int) x+offX)/tileWidth, ((int)y+offY)/tileHeight);
				// check for change
				if(player && nextTile.getType().hasInstance){
					Controller.changeMap(nextTile.getType().change);
					return false;
				}
				// check if able to move there
				if(!nextTile.getType().moveable){
					return true;
				}
			}
			offX = 0;
		}

		return false;
	}

}
