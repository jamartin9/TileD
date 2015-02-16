package main;

import org.lwjgl.opengl.Display;

import utils.Artist;
import utils.Clock;
import utils.SoundManager;

public class Main {

	SoundManager sm;
	public Main() {
		// setup
		Artist.BeginSession();
		setupSound();
		
		// 0= grass, 1= dirt, 2= water. Maps can be made this way on the 20x15 grid
		int map[][]={
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
			{2,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
			{2,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
			{2,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
			{2,0,0,0,2,1,2,2,2,2,2,0,0,0,0,0,0,0,0,0},	
			{2,0,0,0,2,0,1,0,0,0,2,0,0,0,0,0,0,0,0,0},	
			{2,0,0,0,2,0,0,1,0,0,2,0,0,0,0,0,0,0,0,0},	
			{2,0,0,0,2,0,0,0,1,0,2,0,0,0,0,0,0,0,0,0},	
			{2,0,0,0,2,0,0,0,0,1,2,0,0,0,0,0,0,0,0,0},	
			{2,0,0,0,2,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},	
			{2,0,0,0,0,2,2,2,2,0,0,1,0,0,0,0,0,0,0,0},	
			{2,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},	
			{2,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},	
			{2,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1},	

		};
		
		TileGrid grid = new TileGrid(map);
		
		Enemy enemy = new Enemy(Artist.loadTexture("images/enemy.png", "PNG"),grid.getTile(10, 10), 64, 64, 100, 7, grid);
		Player player = new Player(Artist.loadTexture("images/enemy.png", "PNG"),grid.getTile(0, 0), 64, 64, 100, 7, grid);
		
		
		while (!Display.isCloseRequested()) {
			Clock.update();
			player.update();
			enemy.Update();
			
			grid.draw();
			enemy.Draw();
			player.Draw();
			
			Display.update();
			Display.sync(60);
		}
		// end game
		Display.destroy();

	}

	private void setupSound() {
		sm = new SoundManager(2);
		sm.addSound("audio/clunk.wav", "clunk");
		sm.playSound("clunk");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main boot = new Main();
	}

}
