package main;

import utils.Artist;
import utils.Clock;

public abstract class GameObject {

	private TileGrid grid;
	protected Animation anim;
	Thread animThread;
	private float x;
	private float y;
	private int width;
	private int height;
	private int speed;
	protected Tile nextTile; // Tile that will be moved to

	public GameObject(Animation anim, Tile startTile, int width, int height,
			int health, int speed, TileGrid grid) {
		this.anim = anim;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.grid = grid;
	}


	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	
	public boolean moveRight() {
	//	System.out.println("Right");

		if (x + 65 > Artist.getWidth()) {
		//	System.out.println("Out of Bounds");
			return false;
		}
		nextTile = grid.getTile((int) ((x + 60) / 64),
				(int) ((y + 32) / 64));
		if (nextTile.getType().moveable) {
			x += Clock.delta() * speed;
			return true;

		}
		return false;
	}

	public boolean moveLeft() {
		//System.out.println("Left");

		if (x - 1 <= 0) {
			//System.out.println("Out of Bounds");
			return false;
		}
		nextTile = grid.getTile((int) ((x + 10) / 64), (int) ((y + 55) / 64));
		if (nextTile.getType().moveable) {
			x -= Clock.delta() * speed;
			return true;
		}
		return false;
	}

	public boolean moveUp() {
	//	System.out.println("Up");

		if (y - 5 < 0) {
			//System.out.println("Out of Bounds");
			return false;
		}
		nextTile = grid.getTile((int) ((x + 32) / 64),
				(int) ((y + 1) / 64));
		if (nextTile.getType().moveable) {
			y -= Clock.delta() * speed;
			return true;

		}
		return false;
	}

	public boolean moveDown() {
	//	System.out.println("Down");

		if (y + 70 > Artist.getHeight()) {
			//ddSystem.out.println("Out of Bounds");
			return false;
		}
		nextTile = grid.getTile((int) ((x + 32) / 64),
				(int) (((y - 1) / 64) + 1));
		if (nextTile.getType().moveable) {
			y += Clock.delta() * speed;
			return true;

		}
		return false;
	}

	public void Draw() {
		Artist.drawQuadTex(anim.getTex(), x, y, width, height);

	}

	public void setGrid(TileGrid grid){
		this.grid = grid;
	}
	
	public abstract void update();
	public void startAnim(){
		anim.update();
	};

}
