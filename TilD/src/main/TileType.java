package main;

public enum TileType {

	// the name of the file without png
	Grass("grass",true,false),
	Dirt("dirt",true,false),
	Water("water",false,false),
	Town("town",true,true);
	
	String textureName;
	boolean moveable;
	boolean hasInstance;
	
	TileType(String textureName, boolean moveable, boolean hasInstance){
		this.textureName = textureName;
		this.moveable = moveable;
		this.hasInstance = hasInstance;
	}
	
}
