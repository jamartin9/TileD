package utils;

import java.util.Random;

import org.lwjgl.Sys;

public class Clock {

	private static boolean paused = false;
	public static long lastFrame, totalTime;
	private static float deltaTime = 0, multiplier = 1;
	
	// random generator
	private static Random rmd = new Random();

	public static long getTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

	public static int getRandom(int range){
		return rmd.nextInt(range);
	}
	
	public static float getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta * 0.01f;
	}

	public static float delta() {
		if (paused) {
			return 0;
		} else {
			return deltaTime * multiplier;
		}
	}

	public static float getTotalTime() {
		return totalTime;
	}

	public static float getMultipler() {
		return multiplier;
	}

	public static void update() {
		deltaTime = getDelta();
		totalTime += deltaTime;
	}
	
	public static void changeMult(int change){
		if(multiplier+change < -1 && multiplier+change > 7){
			return;
		}else{
			multiplier+=change;
		}
		
	}
	public static void Pause(){
		if(paused){
			paused=false;
		}else{
			paused =true;
		}
	}
}
