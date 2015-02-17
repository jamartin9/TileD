package main;

import org.newdawn.slick.opengl.Texture;

import utils.Artist;
import utils.Clock;

public class Enemy {

	private int width, height, health;
	private float speed, x, y;
	Texture texture;
	Tile startTile;
	TileGrid grid;
	// direction to start moving
	int dir;
	private boolean firstRun = true;

	public Enemy(Texture texture, Tile startTile, int width, int height,
			int health, int speed, TileGrid grid) {
		this.texture = texture;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.grid = grid;
	}

	public void Draw() {
		Artist.drawQuadTex(texture, x, y, width, height);
	}

	public void Update() {
		if (firstRun) {
			firstRun = false;
			dir = Clock.getRandom(3);
			return;
		} else {
			wander(dir);

		}
	}

	// AI
	/*TODO: Change movement Logic*/
	private void wander(int dir) {

		Tile nextTile;
		// choose random tile to move to
		//
		// 1
		// 0 X 2
		// 3
		//

		switch (dir) {
		case 0:
			if (x - 1 <= 0) {
				this.dir = Clock.getRandom(3);
				break;
			}
			nextTile = grid.getTile((int) ((x+10) / 64), (int) ((y+60) / 64));
			if (nextTile.getType().moveable) {

				// get the movable of the tile and make sure it isnt out of
				// bounds

				x -= Clock.delta() * speed;
			} else {
				this.dir = Clock.getRandom(3);
			}
			break;
		case 1:
			if (y - 1 < 0) {
				this.dir = Clock.getRandom(3);
				break;
			}
			nextTile = grid.getTile((int) ((x+32) / 64), (int) ((y+1) / 64));
			if (nextTile.getType().moveable) {

				// get the movable of the tile and make sure it isnt out of
				// bounds
				// move up
				y -= Clock.delta() * speed;
			} else {
				this.dir = Clock.getRandom(3);
			}
			break;
		case 2:
			if (x + 65 > Artist.getWidth()) {
				this.dir = Clock.getRandom(3);
				break;
			}
			nextTile = grid.getTile((int)(x+60) / 64, (int)(y+60) / 64);
			if (nextTile.getType().moveable) {

				// get the movable of the tile and make sure it isnt out of
				// bounds
				// move right
				x += Clock.delta() * speed;
			} else {
				this.dir = Clock.getRandom(3);
			}
			break;
		case 3:
			if (y + 70 > Artist.getHeight()) {
				this.dir = Clock.getRandom(3);
				break;
			}
			nextTile = grid.getTile((int) ((x+32) / 64), (int) (((y-1)/ 64) + 1));
			if (nextTile.getType().moveable) {
				// get the movable of the tile and make sure it isnt out of
				// bounds
				// move down
				y += Clock.delta() * speed;
			} else {
				this.dir = Clock.getRandom(3);
			}
			break;
		}

	}
	
}
