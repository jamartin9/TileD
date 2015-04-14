package main;

import org.newdawn.slick.opengl.Texture;

public enum Item 
{
	
	
	Dungeon_Key("Dungeon_Key",true,false);
    
    public String itemName;
	public boolean useable;
	public boolean wearable;
	// add item images
	public Texture item;
	private float x;
	private float y;
	private int height;
	private int width;
	
	
	Item(String itemName, boolean useable, boolean wearable){
		this.itemName = itemName;
		this.useable = useable;
		this.wearable = wearable;

	}

	public void setX(float x){
		this.x=x;
	}
	public void setY(float y){
		this.y=y;
	}
	public void setWidth(int width){
		this.width =  width;
	}
	public void setHeight(int height){
		this.height = height;
	}
	public void draw() {
		// TODO: implement drawing of item
		
	}
    
}
