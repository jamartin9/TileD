package main;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import utils.Artist;
import utils.Clock;
import utils.SoundManager;

public class View implements Runnable{

	// music player
	private static SoundManager sm;

	public View() {
		// setup
		Artist.BeginSession();
		setupSound();
	}

	private void setupSound() {
		sm = new SoundManager(3);
		//sm.setVolume(5);
		sm.addSound("audio/skele.wav", "skele");
		sm.addSound("audio/goblin.wav", "goblin");
		sm.addSound("audio/music.wav", "music");
		sm.enableLoop();
		sm.playSound("music");

	}
	
	public static void playSound(String sound){
		sm.stopSound();
		sm.playSound(sound);
	}
	
	public void dispose(){
		sm.stopSound();
		sm.dispose();
	}
	
	/*This must be called from thread with current Context*/
	public void releaseContext(){
		try {
			Display.releaseContext();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
		try {
			if(!Display.isCurrent()){
				Display.makeCurrent();
			}
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Controller.resize();
		int fps =60;
		while (!Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			if(!isOver){
			// redraw map
			Controller.draw();
			if(Clock.getDeltaTime() >= 0.02){
				fps++;
			}else{
				fps--;
			}
			}else{
				drawOver();
			}
			// update the display
			Display.update();
			Display.sync(fps);
			
		}
		Display.destroy();
		Keyboard.destroy();
		dispose();
		System.exit(0);
	}

	
	private void drawOver() {
		TextBoxArea text = new TextBoxArea(69, false, Artist.getWidth()/2 - Artist.getScaleX(), Artist.getHeight()/2, Color.white, "GAME OVER", Artist.loadTexture("images/LAVA.png", "PNG"));
		text.setCleanText(false);
		text.setShow(true);
		text.setTime(180f);
		text.draw();
		once = true;
	}


	private static boolean isOver =false;
	private static boolean once = false;
	public static void setOver() {
		isOver = true;
	}
}
