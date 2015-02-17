package main;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

import utils.Artist;
import utils.Clock;

public class Player{

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

	
	/*TODO: Change movement Logic later*/
	public void update() {

		while (Keyboard.next()) {
			Tile nextTile;
			int key = Keyboard.getEventKey();
			switch(key){
			case Keyboard.KEY_A:
				System.out.println("Left");

				if (x - 1 <= 0) {
					System.out.println("Out of Bounds");
					break;
				}
				nextTile = grid.getTile((int) ((x+10) / 64), (int) ((y+64) / 64));
				if (nextTile.getType().moveable) {
					x -= Clock.delta() * speed;
				}
				break;
			
			case Keyboard.KEY_D:
				System.out.println("Right");

				if (x + 65 > Artist.getWidth()) {
					System.out.println("Out of Bounds");
					break;
				}
				nextTile = grid.getTile((int) ((x+64) / 64), (int) ((y+64) / 64));
				if (nextTile.getType().moveable) {
					x += Clock.delta() * speed;

				}
				break;
			
			case Keyboard.KEY_W:
				System.out.println("Up");

				if (y - 1 < 0) {
					System.out.println("Out of Bounds");
					break;
				}
				nextTile = grid.getTile((int) ((x+32) / 64), (int) ((y+1) / 64));
				if (nextTile.getType().moveable) {
					y -= Clock.delta() * speed;
				}
				break;
				
			case Keyboard.KEY_S:
				System.out.println("Down");

				if (y + 70 > Artist.getHeight()) {
					System.out.println("Out of Bounds");
					break;
				}
				nextTile = grid.getTile((int) ((x+32) / 64), (int) (((y-1)/ 64) + 1));
				if (nextTile.getType().moveable) {
					y += Clock.delta() * speed;
				}
				break;
			case Keyboard.KEY_ESCAPE:
				System.exit(0);
				break;
			}
		}
	}

	public void Draw() {
		Artist.drawQuadTex(texture, x, y, width, height);
	}


}
