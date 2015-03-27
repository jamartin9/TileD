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
			dir = Clock.getRandom(4);
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

		if(!getView()){
			// get new dir
			if(dir == 1 || dir == 3){
				dir =0;
			}
		}

		switch (dir) {
		case 0:
			if (moveLeft()) {
				setX(getX() - Clock.delta() * speed);
				break;
			} else {
				this.dir = Clock.getRandom(4);
				break;
			}
		case 1:

			if (moveUp()) {
				setY(getY() - Clock.delta()*speed);
				break;
			} else {
				this.dir = Clock.getRandom(4);
				break;
			}
		case 2:
			if (moveRight()) {
				setX(getX() + Clock.delta() * speed);
				break;
			} else {
				this.dir = Clock.getRandom(4);
				break;
			}
		case 3:
			if (moveDown()) {
				setY(getY() + Clock.delta()*speed);
				break;
			} else {
				this.dir = Clock.getRandom(4);
				break;
			}
		}

	}

}
