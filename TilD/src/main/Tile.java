package main;

import org.newdawn.slick.opengl.Texture;

import utils.Artist;

public class Tile {

	private float x, y, width, height;
	private Texture texture;
	private TileType type;

	public Tile(float x, float y, float width, float height, TileType type) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		this.setType(type);
		this.setTexture(Artist.loadTexture("images/" + type.textureName
				+ ".png", "PNG"));
	}

	public void draw() {
		Artist.drawQuadTex(texture, x, y, width, height);

	}

	/* Getters & Setters */
	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

}
