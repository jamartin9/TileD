package main;

public enum TileType {

	// the name of the file without png
	Grass("grass",true,false,0),
	Dirt("dirt",true,false,0),
	Water("water",false,false,0),
	Town("town",true,true,1),
	test("stuff",true,true,2);
	
	String textureName;
	boolean moveable;
	boolean hasInstance;
	int change;
	
	TileType(String textureName, boolean moveable, boolean hasInstance,int change){
		this.textureName = textureName;
		this.moveable = moveable;
		this.hasInstance = hasInstance;
		this.change = change;
	}
	
}
