package main;

import utils.Clock;

public class Enemy extends GameObject {

	// direction to start moving
	private int dir;
	private boolean firstRun;
	private int cmbMap;
	private boolean hasItem;
	private Item item;
	
	public Enemy(Animation anim, Tile startTile, int width, int height,
			int health, int speed, TileGrid grid, int cmbMap) {
		super(anim, startTile, width, height, health, speed, grid);
		firstRun = true;
		this.cmbMap = cmbMap;
		anim.setRange(0, 0);
		hasItem =false;
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

	public int getcmbMap(){
		return cmbMap;
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
	public void addItem(Item item){
		this.item=item;
		hasItem=true;
	}

	public boolean hasItem() {
		return hasItem;
	}

	public Item getItem() {
		// TODO Auto-generated method stub
		return item;
	}

}
