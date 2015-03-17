package main;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import utils.Artist;
import utils.SoundManager;

public class View implements Runnable{

	// music player
	private SoundManager sm;
	private boolean resized = false;

	public View() {
		// setup
		Artist.BeginSession();
		setupSound();
	}

	private void setupSound() {
		sm = new SoundManager(2);
		//sm.setVolume(5);
		sm.enableLoop();
		sm.addSound("audio/music.wav", "music");
		sm.playSound("music");
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
		while (!Display.isCloseRequested()) {
			/*Check if display was resized and scale everything*/
			if((Display.getHeight() != Artist.getHeight() || Display.getWidth() != Artist.getWidth())){
				Controller.resize(Display.getWidth(),Display.getHeight());		
			}
			// redraw map
			Controller.draw();

			// update the display
			Display.update();

			// sync at 60 fps
			Display.sync(60);
		}
		Display.destroy();
		Keyboard.destroy();
		System.exit(0);
	}
}
