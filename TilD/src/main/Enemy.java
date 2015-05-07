package main;

import utils.Clock;

public class Enemy extends GameObject {

	// direction to start moving
	private int dir;
	private boolean firstRun;
	private int cmbMap;
	private boolean hasItem;
	private Item item;
	private String soundString = null;
	private boolean soundIsPlaying = false;
	
	public Enemy(Animation anim, Tile startTile, int width, int height,
			int health, int speed, TileGrid grid, int cmbMap) {
		super(anim, startTile, width, height, health, speed, grid);
		firstRun = true;
		this.cmbMap = cmbMap;
		anim.setRange(0, 0);
		hasItem =false;
	}

	public void setSound(String sound){
		soundString = sound;
	}
	public void playSound(){
		View.playSound(soundString);
		soundIsPlaying=true;

	}
	public boolean isPlaying(){
		return soundIsPlaying;
	}
	
	public void resetSound(){
		View.playSound("music");
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
		boolean view = getView();
		if(!view){
			// get new dir
			if(dir == 1 || dir == 3){
				dir = 0;
			}
		}
		switch (dir) {
		case 0:
			if (moveLeft()) {
				if(!view){
					if (anim.getRangeStart() != 3 || anim.getRangeEnd() != 4) {
						anim.setRange(3,4);
					}
					// change dims
					float x = getX();
					float y = getY();
					int width = getWidth();
					int height = getHeight();
					setX(x-width/2);
					setY(y-10);
					setWidth(width/2);
					setHeight(height);
					if(Controller.getColl()){
						Controller.doDamage(Clock.getRandom(10), this.getClass().toString());			
						getText().setTime(60f);

						
					}
					// put back old dims
					setX(x);
					setY(y);
					setWidth(height);
					setHeight(height);
				}else{
					if (anim.getRangeStart() != 3 || anim.getRangeEnd() != 3) {
						anim.setRange(3, 3);
					}
				}
				setX(getX() - Clock.delta() * speed);
				break;
			} else {
				this.dir = Clock.getRandom(4);
				break;
			}
		case 1:

			if (moveUp()) {
				anim.setRange(0, 0);
				setY(getY() - Clock.delta()*speed);
				break;
			} else {
				this.dir = Clock.getRandom(4);
				break;
			}
		case 2:
			if (moveRight()) {
				if(!view){
					if (anim.getRangeStart() != 1 || anim.getRangeEnd() != 2) {
						anim.setRange(1,2);
					}
					// change dims
					float x = getX();
					float y = getY();
					int width = getWidth();
					int height = getHeight();
					setX(x+width);
					setY(y-10);
					setWidth(width/2);
					setHeight(height);
					if(Controller.getColl()){
						Controller.doDamage(Clock.getRandom(10), this.getClass().toString());			
						getText().setTime(60f);

					}
					// put back old dims
					setX(x);
					setY(y);
					setWidth(height);
					setHeight(height);

					
				}else{
					if (anim.getRangeStart() != 1 || anim.getRangeEnd() != 1) {
						anim.setRange(1, 1);
					}
				}
				setX(getX() + Clock.delta() * speed);
				break;
			} else {
				this.dir = Clock.getRandom(4);
				break;
			}
		case 3:
			if (moveDown()) {
				anim.setRange(0, 0);
				setY(getY() + Clock.delta()*speed);
				break;
			} else {
				this.dir = Clock.getRandom(4);
				break;
			}
		}

		if(getText().textTime<=0){
			getText().setCleanText(true);
		}
		getText().setX(getX()+getWidth()/2);
		getText().setY(getY());

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
