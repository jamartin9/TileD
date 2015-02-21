package main;

import utils.Clock;

public class Enemy extends GameObject {

	// direction to start moving
	private int dir;
	private boolean firstRun;

	public Enemy(Animation anim, Tile startTile, int width, int height,
			int health, int speed, TileGrid grid) {
		super(anim, startTile, width, height, health, speed, grid);
		firstRun = true;
		anim.setRange(0, 0);
	}

	public void update() {
		if (firstRun) {
			firstRun = false;
			dir = Clock.getRandom(3);
			return;
		} else {
			wander(dir);
			startAnim();

		}
	}

	// AI
	/* TODO: Change movement Logic */
	private void wander(int dir) {
		// choose random tile to move to
		//
		// 1
		// 0 X 2
		// 3
		//

		switch (dir) {
		case 0:
			if (moveLeft()) {
				break;
			} else {
				this.dir = Clock.getRandom(3);
				break;
			}
		case 1:

			if (moveUp()) {
				break;
			} else {
				this.dir = Clock.getRandom(3);
				break;
			}
		case 2:
			if (moveRight()) {
				break;
			} else {
				this.dir = Clock.getRandom(3);
				break;
			}
		case 3:
			if (moveDown()) {
				break;
			} else {
				this.dir = Clock.getRandom(3);
				break;
			}
		}

	}

	@Override
	public void startAnim() {
		anim.update();
	}

}
