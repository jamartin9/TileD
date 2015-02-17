package main;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

import utils.Clock;

public class Animation implements Runnable {

	private ArrayList<Texture> animations;
	private float[] length;
	private float time;
	private boolean isRunning;
	private int rangeStart, rangeEnd, currentIndex;

	public Animation(ArrayList<Texture> animations, float[] length) {
		this.animations = animations;
		this.length = length;
		isRunning = false;
		currentIndex = 0;
	}

	public int getRangeStart(){
		return rangeStart; 
	}
	public int getRangeEnd(){
		return rangeEnd; 
	}
	
	// get a texture
	public Texture getTex() {
		time = length[currentIndex];
		return animations.get(currentIndex);
	}

	// pass time
	private void tick() {
		time -= Clock.getDelta();
	}

	// set which animations
	public void setRange(int start, int end) {
		rangeStart = start;
		currentIndex = rangeStart;
		rangeEnd = end;
	}

	public void update() {
		if (time <= 0) {
			currentIndex++;
			if(currentIndex > rangeEnd){
				currentIndex = rangeStart;
			}
		}else{
			tick();
		}
	}

	public void stop() {
		isRunning = false;
	}

	@Override
	public void run() {
		isRunning = true;
		while (isRunning) {
			update();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
