package main;

public enum TileType {

	// the name of the file without png
	Grass("Grass",true,false,0),
	Dirt("dirt",true,false,0),
	Water("Water",false,false,0),
	Town("Town",true,true,1),
	Stone("Stone",false,false,0),
	Sky("Sky",true,false,0),
	Wood("Wood",true,false,0),
	Leaf("Leaves",true,false,0),
	isoGrass("isoTileGrass", true, false, 0),
	cloud("AngryCloud",true,false,0),
	StoneBack("Stoneback",true,false,0),
	ChangeMapDungeon1("Stoneback",true,true,3),
	lava("LAVA",false,false,0),
	lavaDirt("LAVA2",false,false,0),
	Mountain("Mountain",false,false,0),
	Dungeon("Dungeondoor",true,true,1),
	GrassMove1("dirt",true,true,6);
	
	String textureName;
	public boolean moveable;
	public boolean hasInstance;
	public int change;
	
	TileType(String textureName, boolean moveable, boolean hasInstance,int change){
		this.textureName = textureName;
		this.moveable = moveable;
		this.hasInstance = hasInstance;
		this.change = change;
	}
	
}
