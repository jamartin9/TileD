package main;

import org.lwjgl.input.Keyboard;

import utils.Artist;
import utils.Clock;

public class Player extends GameObject {
	private float jumpTime = 0f;
	private boolean left =false;
	private boolean right =false;
	private boolean up =false;
	private boolean down =false;
	private boolean attack =false;
	private Inventory inventory;
	private int lastLeftOrRight = 0; //0 neither, 1 left ,2 right
	private boolean hasJumped = false;
	private float jumpWait = 0f;
	
	public Player(Animation anim, Tile startTile, int width, int height,
			int health, int speed, TileGrid grid) {
		super(anim, startTile, width, height, health, speed, grid);
		inventory = new Inventory();
		anim.setRange(0, 0);

	}
	
	public void update() {
		/*Get keys pressed*/
		if(Keyboard.isKeyDown(Keyboard.KEY_F)){
			attack =true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)&&viewTopDown) {
			up = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			down = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			right =true; 
			lastLeftOrRight =2;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			left = true;
			lastLeftOrRight =1;
		}
		if ((Keyboard.isKeyDown(Keyboard.KEY_SPACE) && jumpTime<=0)|| hasJumped ) {
			if (!viewTopDown) {
				if(!hasJumped){
					hasJumped =true;
					jumpTime = 8f;

				}
				// jump anim
				//if (anim.getRangeStart() != 8 && anim.getRangeEnd() != 8) {
				//	anim.setRange(8, 8);
				//}
				if(jumpTime <= 0){
					hasJumped =false;
					jumpWait = 8f;
				}
				if (jumpUp() && hasJumped&&jumpWait<=0) {
					setY(getY()-Clock.delta()*getSpeed()*2f);
					jumpTime -= Clock.delta();
				}else{
					jumpWait -=Clock.delta();
				}
	
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			View.setOver();
		}
		move();
		startAnim();
	}
	@Override
	public void Draw(){
		super.Draw();
		if(inventory.isVisible()){
			inventory.showInventory(0,0,Artist.getScaleX(),Artist.getScaleY());
		}
	}

	private void move() {
		if(up && right){
			if (anim.getRangeStart() != 1 && anim.getRangeEnd() != 3) {
				anim.setRange(1, 3);
			}
			if (moveUpRight()) {
				setX(getX() + Clock.delta() * speed);
				setY(getY() - Clock.delta()*speed);
	
			}
			
		}
		else if(up&&left){
			if (anim.getRangeStart() != 4 && anim.getRangeEnd() != 6) {
				anim.setRange(4, 6);
			}
			if (moveUpLeft()) {
				setX(getX() - Clock.delta() * speed);
				setY(getY() - Clock.delta()*speed);

			}
		}
		else if(down&&left){
			if (anim.getRangeStart() != 4 || anim.getRangeEnd() != 6) {
				anim.setRange(4, 6);
			}
			if (moveDownLeft()) {
				setX(getX() - Clock.delta() * speed);
				setY(getY() + Clock.delta()*speed);

			}
		}
		else if(down&&right){
			if (anim.getRangeStart() != 1 || anim.getRangeEnd() != 3) {
				anim.setRange(1, 3);
			}
			if (moveDownRight()) {
				setX(getX() + Clock.delta() * speed);
				setY(getY() + Clock.delta()*speed);
			}
		}
		else if(up){
			if (anim.getRangeStart() != 8 && anim.getRangeEnd() != 8) {
				anim.setRange(8, 8);
			}
			if (moveUp()) {
				setY(getY() - Clock.delta()*speed);
				
			}
			
		}
		else if(down){
			if (anim.getRangeStart() != 9 && anim.getRangeEnd() != 9) {
				anim.setRange(9, 9);
			}
			if (moveDown()) {
				setY(getY() + Clock.delta()*speed);
				
			}
			
		}
		else if(left){
			if (anim.getRangeStart() != 4 || anim.getRangeEnd() != 6) {
				anim.setRange(4, 6);
			}
			if (moveLeft()) {
				setX(getX() - Clock.delta() * speed);
				
			}
			
		}
		else if(right){
			if (anim.getRangeStart() != 1 || anim.getRangeEnd() != 3) {
				anim.setRange(1, 3);
			}
			if (moveRight()) {
				setX(getX() + Clock.delta() * speed);
				
			}
			
		}
		if(attack&& !viewTopDown){
			float x = getX();
			float y = getY();
			int width = getWidth();
			int height = getHeight();
			if(lastLeftOrRight == 1){
				anim.setRange(7, 7);

			}else if(lastLeftOrRight == 2){
				anim.setRange(0, 0);
				setX(x+width);
				setY(y-10);
				setWidth(width/2);
				setHeight(height);

			}
			if(Controller.getColl()&&getText().textTime<=0){
				Controller.doDamage(Clock.getRandom(100), this.getClass().toString());
				getText().setTime(60f);
				//bar.setTime(60f);
				//bar.show();
				//bar.setX(getX());
				//Abar.setY(getY());
			}
			setX(x);
			setY(y);
			setWidth(height);
			setHeight(height);
			
		}
		if(getText().textTime<=0){
			getText().setCleanText(true);
		}

		right =false;
		left= false;
		up=false;
		down =false;
		attack =false;
		// update text box
		getText().setX(getX()+getWidth()/2);
		getText().setY(getY());
	}
	public void addItem(Item item){
		inventory.addItem(item);
		inventory.setVisible(true);
	}

	public boolean hasItem() {
		if(inventory.isVisible()){
			return true;
		}
		return false;
	}

	public void removeItem() {
		inventory.remove();
	}

}
