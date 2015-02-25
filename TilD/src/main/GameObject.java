package main;

import utils.Artist;

public abstract class GameObject {

	private TileGrid grid;
	protected Animation anim;
	Thread animThread;
	protected float x;
	protected float y;
	private int width;
	protected int height;
	protected int speed;
	protected Tile nextTile; // Tile that will be moved to
	protected boolean viewTopDown;
	private int health;
	
	public GameObject(Animation anim, Tile startTile, int width, int height,
			int health, int speed, TileGrid grid) {
		this.anim = anim;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.grid = grid;
		this.health = health;
	}

	public void setView(boolean viewTopDown){
		this.viewTopDown=viewTopDown;
	}
	public void setHealth(int health){
		this.health = health;
	}
	public int getHealth(){
		return health;
	}
	public boolean getView(){
		return viewTopDown;
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
	public int getSpeed(){
		return speed;
	}
	
	public void setX(float x){
		this.x=x;
	}
	public void setY(float y){
		this.y=y;
	}
	public void setHeight(int height){
		this.height = height;
	}
	public void setWidth(int width){
		this.width=width;
	}
	
	public boolean moveRight() {
	//	System.out.println("Right");

		if (x + width + 1 > Artist.getWidth()) {
		//	System.out.println("Out of Bounds");
			return false;
		}
		nextTile = grid.getTile((int) ((x + (width-4)) / 64),
				(int) ((y + (height/2)) / 64));
		if (nextTile.getType().moveable) {
			//x += Clock.delta() * speed;
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
		nextTile = grid.getTile((int) ((x + 10) / 64), (int) ((y + (height-9)) / 64));
		if (nextTile.getType().moveable) {
			//x -= Clock.delta() * speed;
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
		nextTile = grid.getTile((int) ((x + (width/2)) / 64),
				(int) ((y + 1) / 64));
		if (nextTile.getType().moveable) {
			//y -= Clock.delta() * speed;
			return true;

		}
		return false;
	}
	public boolean jumpUp(){
		if (y- height *2 - 5 < 0) {
			//System.out.println("Out of Bounds");
			return false;
		}
		nextTile = grid.getTile((int) ((x + (width/2)) / 64),
				(int) ((y + 1-height*2) / 64));
		if (nextTile.getType().moveable) {
			//y -= Clock.delta() * speed;
			return true;

		}
		return false;
	}

	public boolean moveDown() {
	//	System.out.println("Down");

		if (y + height + 5 > Artist.getHeight()) {
			//ddSystem.out.println("Out of Bounds");
			return false;
		}
		nextTile = grid.getTile((int) ((x +(width/2)) / 64),
				(int) ((((y - 1) + height) / 64)));
		if (nextTile.getType().moveable) {
			//y += Clock.delta() * speed;
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
