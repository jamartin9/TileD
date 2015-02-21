package main;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

import utils.Artist;
import utils.Clock;

public class Player extends GameObject {
	private boolean right,left,up,down;
	public Player(Animation anim, Tile startTile, int width, int height,
			int health, int speed, TileGrid grid) {
		super(anim, startTile, width, height, health, speed, grid);
		right=false;
		left=false;
		up=false ;
		down=false;
		anim.setRange(0, 0);

	}

	public void startAnim(){
		anim.update();
		}
	/* TODO: Change movement Logic later */
	public void update() {

		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
			switch (key) {
			case Keyboard.KEY_A:
				left=moveLeft();
				if (anim.getRangeStart() != 4 && anim.getRangeEnd() != 6) {
					anim.setRange(4, 6);
				}
				if(left){
					if(nextTile.getType().hasInstance){
					    Boot.changeMap(nextTile.getType().change);
					}
				}
				break;

			case Keyboard.KEY_D:
				right=moveRight();
				if (anim.getRangeStart() != 1 && anim.getRangeEnd() != 3) {
					anim.setRange(1, 3);
				}
				if(right){
					if(nextTile.getType().hasInstance){
						Boot.changeMap(nextTile.getType().change);
					}
				}
				break;

			case Keyboard.KEY_W:
				up=moveUp();
				if (anim.getRangeStart() != 0 && anim.getRangeEnd() !=0) {
					anim.setRange(0, 0);
				}
				if(up){
					if(nextTile.getType().hasInstance){
						Boot.changeMap(nextTile.getType().change);
					}
				}

				break;

			case Keyboard.KEY_S:
				down=moveDown();
				if (anim.getRangeStart() != 0 && anim.getRangeEnd() !=0) {
					anim.setRange(0, 0);
				}
				if(down){
					if(nextTile.getType().hasInstance){
						Boot.changeMap(nextTile.getType().change);
					}
				}
				break;
			case Keyboard.KEY_ESCAPE:
				System.exit(0);
				break;
			}
		}
		
		
		startAnim();
	}

}
