package main;

import utils.Artist;
import utils.Physics;

public abstract class GameObject {

	private TileGrid grid;
	protected Animation anim;
	private float x;
	private float y;
	private int width;
	protected int height;
	protected int speed;
	protected boolean viewTopDown;
	protected boolean viewIso;
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
		viewIso = false;
	}

	public void setIso(boolean isoView) {
		viewIso = isoView;
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

	public TileGrid getGrid() {
		return grid;
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

	/* TODO: Move hit boxes, change for isoView, watch 1 tile hb if the width/height != scaleX/scaleY respectively*/

	public boolean moveUpRight() {
		if (getX() + width + 5 > Artist.getWidth() || getY() - 5 < 0) {
			return false;
		}
		if (Physics.collidesTile(getX()+4, getY()-4, getWidth() * 2, getHeight()*2, getGrid())) {
			return false;
		}
		return true;
	}

	public boolean moveUpLeft() {
		if (getX() - 5 <= 0 || getY() - 5 < 0) {
			return false;
		}
		if (Physics.collidesTile(getX()-4, getY()-4, getWidth()*2, getHeight()*2, getGrid())) {
			return false;
		}
		return true;
	}

	public boolean moveDownRight() {

		if (getY() + height + 5 > Artist.getHeight()
				|| getX() + width + 5 > Artist.getWidth()) {
			return false;
		}
		if (Physics.collidesTile(getX(), getY(), getWidth()*2, getHeight()*2, getGrid())) {
			return false;
		}
		return true;
	}

	public boolean moveDownLeft() {
		if (getY() + height + 5 > Artist.getHeight() || getX() - 5 <= 0) {
			return false;
		}
		if (Physics.collidesTile(getX()-4, getY(), getWidth()*2, getHeight()*2, getGrid())) {
			return false;
		}
		return true;
	}

	public boolean moveRight() {
		if (getX() + width + 5 > Artist.getWidth()) {
			return false;
		}
		if (Physics.collidesTile(getX() + width +4, getY()+height/2+height/4+height/8, getWidth(), getHeight(), getGrid())) {
			return false;
		}
		return true;
	}

	public boolean moveLeft() {

		if (getX() - 5 <= 0) {
			return false;
		}
		if (Physics.collidesTile(getX() - 4, getY()+height/2+height/4+height/8, getWidth(), getHeight(), getGrid())) {
			return false;
		}
		return true;
	}

	public boolean moveUp() {

		if (getY() - 5 < 0) {
			return false;
		}
		if (Physics.collidesTile( getX()+width/2, getY() - 4, getWidth(), 4, getGrid())) {
			return false;
		}
		return true;
	}

	public boolean moveDown() {
		if (getY() + height + 5 > Artist.getHeight()) {
			return false;
		}
		if (Physics.collidesTile(getX()+width/2, getY()+height+4, getWidth(), 4, getGrid())) {
			return false;
		}
		return true;
	}

	public boolean jumpUp() {
		if (getY() - height * 1.25 - 5 < 0) {
			return false;
		}
		if (Physics.collidesTile(getX(), getY() - height * 1.25f, getWidth(),
				(int) (getHeight() * 1.25), getGrid())) {
			return false;
		}
		return true;
	}

	public void Draw() {
		Artist.drawQuadTex(anim.getTex(), getX(), getY(), width, height);

	}

	public void setGrid(TileGrid grid) {
		this.grid = grid;
	}

	public abstract void update();

	public void startAnim() {
		anim.update();
	};

}
