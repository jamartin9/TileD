package main;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import utils.Artist;
import utils.Clock;
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
	private TextBoxArea box = null;
	protected Healthbar bar = null;

	

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
		bar = new Healthbar(health, x, y-10, 8, width);
		bar.hide();
		
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
		if (Physics.collidesTile(getX()+4, getY()-4, getWidth()+Artist.getScaleX(), getHeight()+Artist.getScaleY(), getGrid(), this.getClass().toString().equals("class main.Player"))) {
			return false;
		}
		return true;
	}

	public boolean moveUpLeft() {
		if (getX() - 5 <= 0 || getY() - 5 < 0) {
			return false;
		}
		if (Physics.collidesTile(getX()-4, getY()-4, getWidth(), getHeight()+Artist.getScaleY(), getGrid(),this.getClass().toString().equals("class main.Player"))) {
			return false;
		}
		return true;
	}

	public boolean moveDownRight() {

		if (getY() + height + 5 > Artist.getHeight() || getX() + width + 5 > Artist.getWidth()) {
			return false;
		}
		if (Physics.collidesTile(getX(), getY(), getWidth()+Artist.getScaleX(), getHeight()+Artist.getScaleY(), getGrid(),this.getClass().toString().equals("class main.Player"))) {
			return false;
		}
		return true;
	}

	public boolean moveDownLeft() {
		if (getY() + height + 5 > Artist.getHeight() || getX() - 5 <= 0) {
			return false;
		}
		if (Physics.collidesTile(getX()-4, getY(), getWidth(), getHeight()+Artist.getScaleY(), getGrid(),this.getClass().toString().equals("class main.Player"))) {
			return false;
		}
		return true;
	}

	public boolean moveRight() {
		if (getX() + width + 5 > Artist.getWidth()) {
			return false;
		}
		if (Physics.collidesTile(getX()+width/2, getY()+height/2+height/4+height/8, getWidth(), getHeight()-height/2-height/4-height/8, getGrid(),this.getClass().toString().equals("class main.Player"))) {
			return false;
		}
		return true;
	}

	public boolean moveLeft() {

		if (getX() - 5 <= 0) {
			return false;
		}
		if (Physics.collidesTile(getX() - 4, getY()+height/2+height/4+height/8, getWidth(), getHeight()-height/2-height/4-height/8, getGrid(),this.getClass().toString().equals("class main.Player"))) {
			return false;
		}
		return true;
	}

	public boolean moveUp() {

		if (getY() - 5 < 0) {
			return false;
		}
		if (Physics.collidesTile( getX()+width/2, getY() - 4, getWidth()-width/2, getHeight(), getGrid(),this.getClass().toString().equals("class main.Player"))) {
			return false;
		}
		return true;
	}

	public boolean moveDown() {
		if (getY() + height + 5 > Artist.getHeight()) {
			return false;
		}
		if (Physics.collidesTile(getX()+width/2, getY() + height, getWidth()-width/2, 4, getGrid(),this.getClass().toString().equals("class main.Player"))) {
			return false;
		}
		return true;
	}

	public boolean jumpUp() {
		if (getY() - (Clock.delta()*getSpeed()*2f) - 5 < 0) {
			return false;
		}
		if (Physics.collidesTile(getX(), getY() - (Clock.delta()*getSpeed()*2f), getWidth(),(int) (getHeight()+(Clock.delta()*getSpeed()*2f)), getGrid(),this.getClass().toString().equals("class main.Player"))) {
			return false;
		}
		return true;
	}

	public void Draw() {
		Artist.drawQuadTex(anim.getTex(), getX(), getY(), width, height);
		if(box != null){
			box.draw();
		}
		bar.draw();

	}

	public void setGrid(TileGrid grid) {
		this.grid = grid;
	}

	public abstract void update();

	public void startAnim() {
		anim.update();
	};
	
	public TextBoxArea getText(){
		return box;
	}
	public void createTextBox(int ptSize, boolean fullScreen,Color color,String Text,Texture texture){
		box= new TextBoxArea(ptSize, fullScreen, x+width-5, y-5, color, Text, texture);
	}


}
