package main;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

import utils.Artist;
import utils.Clock;

public class Player {

	private TileGrid grid;
	private Texture texture;
	private float x;
	private float y;
	private int width;
	private int height;
	private int speed;

	public Player(Texture texture, Tile startTile, int width, int height,
			int health, int speed, TileGrid grid) {
		this.texture = texture;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.grid = grid;
	}

	
	/*TODO: Change movement Logic*/
	public void update() {
		Tile nextTile;

		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_A) {
				System.out.println("Left");

				if ((int) (x / 64) - 1 <= 0) {
					return;
				}
				nextTile = grid.getTile((int) (x / 64) - 1, (int) (y / 64));
				if (nextTile.getType().moveable) {
					x -= Clock.delta() * speed;
				}

			}
			else if (Keyboard.getEventKey() == Keyboard.KEY_D) {
				System.out.println("Right");

				if ((int) (x / 64) + 1 >= 20) {
					return;
				}
				nextTile = grid.getTile((int) (x / 64) + 1, (int) (y / 64));
				if (nextTile.getType().moveable) {
					x += Clock.delta() * speed;
				}

			}
			else if (Keyboard.getEventKeyState()
					&& Keyboard.getEventKey() == Keyboard.KEY_S) {
				System.out.println("Down");

				if ((int) (y / 64) + 1 >= 15) {
					return;
				}
				nextTile = grid.getTile((int) (x / 64), (int) (y / 64) + 1);
				if (nextTile.getType().moveable) {
					y += Clock.delta() * speed;
				}

			}
			else if (Keyboard.getEventKeyState()
					&& Keyboard.getEventKey() == Keyboard.KEY_W) {
				System.out.println("Up");

				if ((int) (y / 64) - 1 <= 0) {
					return;
				}
				nextTile = grid.getTile((int) (x / 64), (int) (y / 64) - 1);
				if (nextTile.getType().moveable) {
					y -= Clock.delta() * speed;
				}

			}
			else if (Keyboard.getEventKeyState()
					&& Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
				System.exit(1);

			}
		}
	}

	public void Draw() {
		Artist.drawQuadTex(texture, x, y, width, height);
	}
}
