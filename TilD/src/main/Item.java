package main;

public enum Item 
{
	
	
	Dungeon_Key("Dungeon_Key",true,false);
    
    String itemName;
	public boolean useable;
	public boolean wearable;
	
	
	Item(String itemName, boolean useable, boolean wearable){
		this.itemName = itemName;
		this.useable = useable;
		this.wearable = wearable;

	}
    
}
