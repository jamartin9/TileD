package main;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import utils.Artist;
import utils.Clock;
import utils.Physics;
import utils.SoundManager;

public class Boot {

	private static TileGrid grid;
	private SoundManager sm;
	private static Player playerOne;
	private static Enemy enemyOne;
    private static Maps mapper;

	
	public Boot() {
		// setup
		Artist.BeginSession();
		setupSound();
		mapper = new Maps();
		createInitMap();
		enemyOne = createEnemyNix();
		playerOne = createPlayer();
				
		while (!Display.isCloseRequested()) {
			
			// update the clock
			Clock.update();

			// update positions
			playerOne.update();
			enemyOne.update();

			if(Physics.collides(playerOne, enemyOne)){
				//change view here
				changeMap(Clock.getRandom(3)+1);
			}
			
			// redraw map
			grid.draw();
			// redraw enemy
			enemyOne.Draw();
			// redraw player
			playerOne.Draw();
			
			// update the display
			Display.update();
			
			// sync at 60 fps
			Display.sync(60);
		}
		// end game
		Display.destroy();
		Keyboard.destroy();
		sm.stopSound();
		sm.dispose();
		System.exit(0);

	}
	
	public static void changeMap(int i){
		if(i == 1)
			grid = new TileGrid(mapper.map2);
		if(i == 2)
			grid = new TileGrid(mapper.map3);
		playerOne.setGrid(grid);
		enemyOne.setGrid(grid);
		
	}
	
	private void createInitMap(){
		
		// 0= grass, 1= dirt, 2= water, 3= town. Maps can be made this way on the 20x15 grid

		
		grid = new TileGrid(mapper.map1);
	}

	private Enemy createEnemyNix(){
		/*Create animation for enemy*/
		ArrayList<Texture> enemyTexs = new ArrayList<Texture>();
		float[] enemyTime = new float[1];		
		// moving
		enemyTexs.add(Artist.loadTexture("images/enemy1.png", "PNG"));
		// set time
		enemyTime[0]=0;
		Animation enemyAnim = new Animation(enemyTexs,enemyTime);
		
		return new Enemy(enemyAnim, grid.getTile(10, 10), 64, 64, 100, 7, grid);
	}
	
 	private Player createPlayer(){
		/*Create animation for player*/
		ArrayList<Texture> playerTexs = new ArrayList<Texture>();
		float[] time = new float[7];
		
		// moving down/up
		playerTexs.add(Artist.loadTexture("images/character_front.png", "PNG"));
		// set time
		time[0]=0;
		// moving right
		playerTexs.add(Artist.loadTexture("images/char_right_stand.png", "PNG"));
		playerTexs.add(Artist.loadTexture("images/char_right_step_right.png", "PNG"));
		playerTexs.add(Artist.loadTexture("images/character_left_step_right.png", "PNG"));
		time[1]=0.8f;
		time[2]=0.8f;
		time[3]=0.8f;		
		// moving left
		playerTexs.add(Artist.loadTexture("images/char_left_stand.png", "PNG"));
		playerTexs.add(Artist.loadTexture("images/char_right_step_left.png", "PNG"));
		playerTexs.add(Artist.loadTexture("images/character_left_step_left.png", "PNG"));
		time[4]=0.8f;
		time[5]=0.8f;
		time[6]=0.8f;

		
		Animation playerAnimation = new Animation(playerTexs,time);
		
		return new Player(playerAnimation,grid.getTile(0, 0),64,64,100,25, grid);
		
	}
	
	private void setupSound() {
		sm = new SoundManager(2);
		sm.setVolume(5);
		sm.enableLoop();
		sm.addSound("audio/music.wav", "music");
		sm.playSound("music");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Boot boot = new Boot();
	}

}
