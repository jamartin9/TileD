package main;

import org.newdawn.slick.Color;

import utils.Artist;

public class Healthbar {

	private int max;
	private static Color good = Color.green;
	private static Color bad = Color.red;
	private float height;
	private float width;
	private float x;
	private float y;
	private int current;
	private boolean isHidden;
	private float textTime;

	public Healthbar(int max, float x, float y, float height, float width) {
		// TODO Auto-generated constructor stub
		this.max = max;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.current = max;
		isHidden = true;
		textTime=0f;

	}

	public void setTime(float time){
		textTime=time;
	}
	
	public void show() {
		isHidden = false;
	}

	public void hide() {
		isHidden = true;
	}
	public void setX(float x){
		this.x=x;
	}
	public void setY(float y){
		this.y=y;
	}

	public void draw(){
		textTime-=.33f;
		if(!isHidden&&textTime >= 0){
			if(current != max ){
				// percentage
				float offset = ((max-current)/current);
				Artist.drawQuad(x,y,width*offset,height,good);
				Artist.drawQuad(x+(width*offset),y,width,height,bad);
			}else{
				Artist.drawQuad(x,y,width,height,good);

			}
	}
	}
}
