package main;

public class Item extends GameObject{

	public Item(Animation anim, Tile startTile, int width, int height,
			int health, int speed, TileGrid grid) {
		super(anim, startTile, width, height, health, speed, grid);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		Draw();
	}
	
}