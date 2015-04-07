package main;

import org.lwjgl.input.Keyboard;
import utils.Clock;

public class Player extends GameObject {
	private float jumpTime = 0f;
	private boolean left =false;
	private boolean right =false;
	private boolean up =false;
	private boolean down =false;
	
	
	public Player(Animation anim, Tile startTile, int width, int height,
			int health, int speed, TileGrid grid) {
		super(anim, startTile, width, height, health, speed, grid);
		anim.setRange(0, 0);

	}
	
	public void update() {
		/*Get keys pressed*/
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			up = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			down = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			right =true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			left = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if (!viewTopDown) {
				if (anim.getRangeStart() != 0 && anim.getRangeEnd() != 0) {
					anim.setRange(0, 0);
				}
				if (jumpUp()&&jumpTime <=0) {
					setY(getY()-height*2);
					jumpTime = 5f;
				}else{
					jumpTime -= Clock.getDeltaTime();
				}
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			System.exit(0);
		}
		move();
		startAnim();
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
			if (anim.getRangeStart() != 4 && anim.getRangeEnd() != 6) {
				anim.setRange(4, 6);
			}
			if (moveDownLeft()) {
				setX(getX() - Clock.delta() * speed);
				setY(getY() + Clock.delta()*speed);

			}
		}
		else if(down&&right){
			if (anim.getRangeStart() != 1 && anim.getRangeEnd() != 3) {
				anim.setRange(1, 3);
			}
			if (moveDownRight()) {
				setX(getX() + Clock.delta() * speed);
				setY(getY() + Clock.delta()*speed);
			}
		}
		else if(up){
			if (anim.getRangeStart() != 0 && anim.getRangeEnd() != 0) {
				anim.setRange(0, 0);
			}
			if (moveUp()) {
				setY(getY() - Clock.delta()*speed);
				
			}
			
		}
		else if(down){
			if (anim.getRangeStart() != 0 && anim.getRangeEnd() != 0) {
				anim.setRange(0, 0);
			}
			if (moveDown()) {
				setY(getY() + Clock.delta()*speed);
				
			}
			
		}
		else if(left){
			if (anim.getRangeStart() != 4 && anim.getRangeEnd() != 6) {
				anim.setRange(4, 6);
			}
			if (moveLeft()) {
				setX(getX() - Clock.delta() * speed);
				
			}
			
		}
		else if(right){
			if (anim.getRangeStart() != 1 && anim.getRangeEnd() != 3) {
				anim.setRange(1, 3);
			}
			if (moveRight()) {
				setX(getX() + Clock.delta() * speed);
				
			}
			
		}
		right =false;
		left= false;
		up=false;
		down =false;
		// update text box
		getText().setX(getX());
		getText().setY(getY());
	}

}
