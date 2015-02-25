package main;

import org.lwjgl.input.Keyboard;

import utils.Clock;

public class Player extends GameObject {

	public Player(Animation anim, Tile startTile, int width, int height,
			int health, int speed, TileGrid grid) {
		super(anim, startTile, width, height, health, speed, grid);
		anim.setRange(0, 0);

	}

	/* TODO: Change movement Logic later */
	public void update() {

		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
			switch (key) {
			case Keyboard.KEY_A:
				if (anim.getRangeStart() != 4 && anim.getRangeEnd() != 6) {
					anim.setRange(4, 6);
				}
				if (moveLeft()) {
					x -= Clock.delta() * speed;
					if (nextTile.getType().hasInstance) {
						Boot.changeMap(nextTile.getType().change);
					}
				}
				break;

			case Keyboard.KEY_D:
				if (anim.getRangeStart() != 1 && anim.getRangeEnd() != 3) {
					anim.setRange(1, 3);
				}
				if (moveRight()) {
					x += Clock.delta() * speed;
					if (nextTile.getType().hasInstance) {
						Boot.changeMap(nextTile.getType().change);
					}
				}
				break;

			case Keyboard.KEY_W:
				if (anim.getRangeStart() != 0 && anim.getRangeEnd() != 0) {
					anim.setRange(0, 0);
				}
				if (moveUp()) {
					y -= Clock.delta() * speed;
					if (nextTile.getType().hasInstance) {
						Boot.changeMap(nextTile.getType().change);
					}
				}

				break;

			case Keyboard.KEY_S:
				if (anim.getRangeStart() != 0 && anim.getRangeEnd() != 0) {
					anim.setRange(0, 0);
				}
				if (moveDown()) {
					y += Clock.delta() * speed;
					if (nextTile.getType().hasInstance) {
						Boot.changeMap(nextTile.getType().change);
					}
				}
				break;
			case Keyboard.KEY_SPACE:
				if (!viewTopDown) {
					if (anim.getRangeStart() != 0 && anim.getRangeEnd() != 0) {
						anim.setRange(0, 0);
					}
					if(jumpUp()){
						y -= height;
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
