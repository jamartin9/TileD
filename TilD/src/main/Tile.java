package main;

import org.newdawn.slick.opengl.Texture;

import utils.Artist;

public class Tile {

	private float x, y, width, height;
	private Texture texture;
	private TileType type;
	private boolean viewIso;

	public Tile(float x, float y, float width, float height, TileType type) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		this.setType(type);
		this.setTexture(Artist.loadTexture("images/" + type.textureName
				+ ".png", "PNG"));
		viewIso = false;
	}

	public void draw() {
		Artist.drawQuadTex(texture, getX(), getY(), width, height);

	}

	/* Getters & Setters */
	public void setIso(boolean isoView) {
		viewIso = isoView;
	}

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
		if (viewIso) {
			// return the height translated up 1/2 the scale if not in the first
			// row of tiles
			if (y == 0) {
				return y;
			} else {
				// half the image is over laping with 1 px
				int HeightOverLapping = ((Artist.getScaleY()) / 2);
				return y -(y/Artist.getScaleY())* HeightOverLapping;
			}
		} else {
			return y;
		}
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		if (viewIso) {
			// return x translated right by half the width if the y row is odd
			return x + ((y / Artist.getScaleY()) % 2)
					* (Artist.getScaleX() / 2);
		} else {
			return x;

		}
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

	public void resize(int sizeWidth, int sizeHeight, int translateX,
			int translateY) {
		this.width = sizeWidth;
		this.height = sizeHeight;
		this.x = translateX;
		this.y = translateY;
	}

}
