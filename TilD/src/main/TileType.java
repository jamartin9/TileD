package main;

public enum TileType {

	// the name of the file without png
	Grass("grass",true),
	Dirt("dirt",true),
	Water("water",false);
	
	String textureName;
	boolean moveable;
	
	TileType(String textureName, boolean moveable){
		this.textureName = textureName;
		this.moveable = moveable;
	}
	
}
