package main;

import java.util.ArrayList;
import org.newdawn.slick.opengl.Texture;

import utils.Artist;
import utils.Clock;


public class Controller {

	private View view;
	private static Model model;
	/* Keep mapper if generating all at once, remove/change if not */
	private static Maps mapper;


	public Controller() {
		view = new View();
		mapper = new Maps();
		model = new Model();
		model.addEnemy(createEnemyNix());
		model.addPlayer(createPlayer());
		Thread ViewThread = new Thread(view);
		view.releaseContext();
		ViewThread.start();
	}

	private Enemy createEnemyNix() {
		/* Create animation for enemy */
		ArrayList<Texture> enemyTexs = new ArrayList<Texture>();
		float[] enemyTime = new float[1];
		// moving
		enemyTexs.add(Artist.loadTexture("images/enemy1.png", "PNG"));
		// set time
		enemyTime[0] = 0;
		Animation enemyAnim = new Animation(enemyTexs, enemyTime);

		return new Enemy(enemyAnim, model.getTile(10, 10), 64, 64,
				100, 7, model.getGrid());
	}

	private Player createPlayer() {
		/* Create animation for player */
		ArrayList<Texture> playerTexs = new ArrayList<Texture>();
		float[] time = new float[7];

		// moving down/up
		playerTexs.add(Artist.loadTexture("images/character_front.png", "PNG"));
		// set time
		time[0] = 0;
		// moving right
		playerTexs
				.add(Artist.loadTexture("images/char_right_stand.png", "PNG"));
		playerTexs.add(Artist.loadTexture("images/char_right_step_right.png",
				"PNG"));
		playerTexs.add(Artist.loadTexture(
				"images/character_left_step_right.png", "PNG"));
		time[1] = 0.8f;
		time[2] = 0.8f;
		time[3] = 0.8f;
		// moving left
		playerTexs.add(Artist.loadTexture("images/char_left_stand.png", "PNG"));
		playerTexs.add(Artist.loadTexture("images/char_right_step_left.png",
				"PNG"));
		playerTexs.add(Artist.loadTexture(
				"images/character_left_step_left.png", "PNG"));
		time[4] = 0.8f;
		time[5] = 0.8f;
		time[6] = 0.8f;

		Animation playerAnimation = new Animation(playerTexs, time);

		return new Player(playerAnimation, model.getTile(0, 0), 64,
				64, 100, 25, model.getGrid());

	}

	
	public static void draw() {
		// update the clock
		Clock.update();
		model.update();
		model.draw();
	}

	
	public static void changeMap(int i) {
		/*Change this to not make a new map each time?*/
		switch (i) {
			case 1:
				model.setGrid(new TileGrid(mapper.map2));
				break;
			case 2:
				model.setGrid(new TileGrid(mapper.map3));
				break;
			default:
				model.setGrid(new TileGrid(mapper.map1));
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		Controller boot = new Controller();
	}
	

}
