package main;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import utils.Artist;
import utils.Clock;
import utils.SoundManager;

public class View implements Runnable{

	// music player
	private SoundManager sm;

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
		Controller.resize();
		int fps =60;
		while (!Display.isCloseRequested()) {
			// redraw map
			Controller.draw();
		
			// update the display
			Display.update();
			if(Clock.getDeltaTime() >= 0.02){
				fps++;
			}else{
				fps--;
			}
			Display.sync(fps);
		}
		Display.destroy();
		Keyboard.destroy();
		dispose();
		System.exit(0);
	}
}
