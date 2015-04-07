package main;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import utils.Artist;

public class TextBoxArea {

	private TrueTypeFont font;
	private boolean fullScreen;
	private String text;
	private Color color;
	private float x;
	private float y;
	private Texture background;
	// set the text to show by default
	private boolean showText = true;
	// set if the text with be removed after one showing
	private boolean removeText = true;

	public TextBoxArea(int ptSize, boolean fullScreen, float x, float y, Color color, String Text, Texture texture) {
		font = new TrueTypeFont(new Font("Times New Roman", Font.BOLD, ptSize), false);
		this.color = color;
		this.fullScreen = fullScreen;
		text = Text;
		this.x = x;
		this.y = y;
		background = texture;

	}
	
	public void setX(float x){
		this.x=x;
	}

	public void setY(float y){
		this.y=y;
	}
	public void setBackGround(Texture back){
		this.background = back;
	}
	public void setColor(Color color){
		this.color = color;
	}
	public void setFullScreen(boolean fs){
		fullScreen=fs;
	}
	
	public void setString(String str) {
		text = str;
	}

	public boolean isFullScreen() {
		return fullScreen;
	}

	public boolean hasString() {
		if (text == null) {
			return false;
		}
		return true;
	}

	public void setShow(boolean show) {
		showText = show;
	}

	public void setCleanText(boolean clean) {
		removeText = clean;
	}

	public void draw() {
		if (showText) {
			int beginIndex = 0;
			int endIndex = text.length();
			float tempY =y;
			// if the text runs off the screen wrap it
			// resizing for text
			/*
			while (x +font.getWidth(text.substring(beginIndex, endIndex)) > Artist.getWidth()) {
				// start at end of string and work back
				for (endIndex = text.length(); endIndex > 0; endIndex--) {
					// if the size is still too big
					if (x+font.getWidth(text.substring(beginIndex, endIndex)) > Artist.getWidth()) {
						continue;
					} else {
						// draw part of background and draw string
						Artist.drawQuadTex(background, x, tempY, font.getWidth(text.substring(beginIndex, endIndex)), font.getHeight());
						font.drawString(x, tempY,text.substring(beginIndex, endIndex));
						// update indexs
						beginIndex = endIndex+1;
						endIndex = text.length();
						if(beginIndex==endIndex){
							tempY+=font.getHeight();
							Artist.drawQuadTex(background, x, tempY, font.getWidth(text.substring(beginIndex, endIndex)), font.getHeight());
							font.drawString(x, tempY,text.substring(beginIndex-1, endIndex));
							if (removeText) {
								text = null;
							}
							return;
						}
						tempY += font.getHeight();
					}
				}
				return;
			}*/
			Artist.drawQuadTex(background, x, tempY, font.getWidth(text.substring(beginIndex, endIndex)),font.getHeight());
			font.drawString(x, tempY, text.substring(beginIndex, endIndex), color);
			// remove text when printed
			if (removeText) {
				text = null;
			}
		}
	}

}