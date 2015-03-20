package main;

import utils.Artist;

public abstract class GameObject {

	private TileGrid grid;
	protected Animation anim;
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

	public void setView(boolean viewTopDown) {
		this.viewTopDown = viewTopDown;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getHealth() {
		return health;
	}

	public boolean getView() {
		return viewTopDown;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getSpeed() {
		return speed;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	/*TODO: Move hit boxes*/
	
	public boolean moveUpRight() {
		if (x + width + 5 > Artist.getWidth() || y - 5 < 0) {
			return false;
		}
		nextTile = grid.getTile((int) ((x + width ) / Artist.getScaleX()),
				(int) (y  / Artist.getScaleY()));
		if (nextTile.getType().moveable) {
			return true;

		}
		return false;
	}

	public boolean moveUpLeft() {
		if (x -1 <= 0 || y - 5 < 0) {
			return false;
		}
		nextTile = grid.getTile((int) (x  / Artist.getScaleX()), (int) (y  / Artist.getScaleY()));
		if (nextTile.getType().moveable) {
			return true;

		}
		return false;
	}

	public boolean moveDownRight() {

		if (y + height + 5 > Artist.getHeight() || x + width + 5 > Artist.getWidth()) {
			return false;
		}
		nextTile = grid.getTile((int) ((x + width + 2) / Artist.getScaleX()),
				(int) (((y + height) / Artist.getScaleY())));
		if (nextTile.getType().moveable) {
			return true;
		}
		return false;	
		}

	public boolean moveDownLeft() {
		if (y + height + 5 > Artist.getHeight() || x - 1 <= 0) {
			return false;
		}
		nextTile = grid.getTile((int) (x / Artist.getScaleX()),
				(int) (((y + height) / Artist.getScaleY())));
		if (nextTile.getType().moveable) {
			return true;
		}
		return false;
		}

	public boolean moveRight() {
		if (x + width + 5 > Artist.getWidth()) {
			return false;
		}
		nextTile = grid.getTile((int) ((x + (width + 4)) / Artist.getScaleX()),
				(int) ((y + (height / 2)+ (height / 4)) / Artist.getScaleY()));
		if (nextTile.getType().moveable) {
			return true;

		}
		return false;
	}

	public boolean moveLeft() {

		if (x - 1 <= 0) {
			return false;
		}
		nextTile = grid.getTile((int) ((x + 2) / Artist.getScaleX()),
				(int) ((y + (height - 9)) / Artist.getScaleY()));
		if (nextTile.getType().moveable) {
			return true;
		}
		return false;
	}

	public boolean moveUp() {

		if (y - 5 < 0) {
			return false;
		}
		nextTile = grid.getTile((int) ((x + (width / 2)) / Artist.getScaleX()),
				(int) ((y + 1) / Artist.getScaleY()));
		if (nextTile.getType().moveable) {
			return true;

		}
		return false;
	}

	public boolean moveDown() {
		if (y + height + 5 > Artist.getHeight()) {
			return false;
		}
		nextTile = grid.getTile((int) ((x + (width / 2)) / Artist.getScaleX()),
				(int) ((((y - 1) + height) / Artist.getScaleY())));
		if (nextTile.getType().moveable) {
			return true;
		}
		return false;
	}

	public boolean jumpUp() {
		if (y - height * 1.25 - 5 < 0) {
			return false;
		}
		nextTile = grid.getTile((int) ((x + (width / 2)) / Artist.getScaleX()),
				(int) ((y + 1 - height * 1.25) / Artist.getScaleY()));
		if (nextTile.getType().moveable) {
			return true;

		}
		return false;
	}
	
	public void Draw() {
		Artist.drawQuadTex(anim.getTex(), x, y, width, height);

	}

	public void setGrid(TileGrid grid) {
		this.grid = grid;
	}

	public abstract void update();

	public void startAnim() {
		anim.update();
	};

}
