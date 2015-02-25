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
	private static ArrayList<GameObject> gameObjects;
	private static Maps mapper;
	private static int enemyIndex;

	public Boot() {
		// setup
		Artist.BeginSession();
		setupSound();
		mapper = new Maps();
		createInitMap();
		gameObjects = new ArrayList<GameObject>(2);
		// GameObjects[0] = player;
		gameObjects.add(createPlayer());
		gameObjects.add(createEnemyNix());

		// Change view boolean
		boolean viewTopDown = true;
		// set views
		for (GameObject e : gameObjects) {
			e.setView(viewTopDown);
		}
		// create Grid for saving when view flips
		TileGrid topDownGrid = null;
		enemyIndex = 0;
		float playerX = 0;
		float playerY = 0;
		while (!Display.isCloseRequested()) {

			// update the clock
			Clock.update();

			// if we are in topDown
			if (viewTopDown) {
				// check for collision/update
				for (int i = 0; i < gameObjects.size(); i++) {
					// update
					gameObjects.get(i).update();

					// check for collision for all enemies with player
					if (i != 0 && Physics.collides(gameObjects.get(0), gameObjects.get(i))) {

						// save index, grid, x, y
						enemyIndex = i;
						topDownGrid = grid;
						playerX=gameObjects.get(0).getX();
						playerY=gameObjects.get(0).getY();

						// change map
						changeMap(2);

						// set player and enemy to new positions, sizes and views
						gameObjects.get(0).setWidth(128);
						gameObjects.get(0).setHeight(128);
						gameObjects.get(0).setX(5);
						gameObjects.get(0).setY(Artist.getHeight()- gameObjects.get(0).getHeight() * 2);
						gameObjects.get(i).setWidth(128);
						gameObjects.get(i).setWidth(128);
						gameObjects.get(i).setX(Artist.getWidth()- gameObjects.get(i).getWidth() - 7);
						gameObjects.get(i).setY(Artist.getHeight()- gameObjects.get(i).getHeight() * 2);

						// update views
						viewTopDown = false;
						gameObjects.get(0).setView(viewTopDown);
						gameObjects.get(i).setView(viewTopDown);
						break;

					}
				}
			}
			// if not in overhead top down view
			else {
				// update player and opponent respectively
				gameObjects.get(0).update();
				gameObjects.get(enemyIndex).update();
				// make objects fall
				Physics.applyGravity(gameObjects.get(0));
				Physics.applyGravity(gameObjects.get(enemyIndex));
				// check collision
				if (Physics.collides(gameObjects.get(0), gameObjects.get(enemyIndex))) {
					/* kill enemy for now */
					gameObjects.get(enemyIndex).setHealth(0);
					
					// if player died
					if (gameObjects.get(0).getHealth() <= 0) {
						System.out.println("GAME OVER");
						break;
					}
					
					// if enemy died
					else if (gameObjects.get(enemyIndex).getHealth() <= 0) {
						
						// remove dead enemy
						gameObjects.remove(enemyIndex);
						
						// put view back on player
						viewTopDown = true;
						gameObjects.get(0).setGrid(topDownGrid);
						gameObjects.get(0).setView(viewTopDown);
						gameObjects.get(0).setWidth(64);
						gameObjects.get(0).setHeight(64);
						gameObjects.get(0).setX(playerX);
						gameObjects.get(0).setY(playerY);
						// put world view back
						grid = topDownGrid;
					}
				}
			}
			// redraw map
			grid.draw();

			// redraw objects
			if (viewTopDown) {
				for (GameObject e : gameObjects) {
					e.Draw();
				}
			} else {
				// redraw player and enemy
				gameObjects.get(0).Draw();
				gameObjects.get(enemyIndex).Draw();
			}

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

	public static void changeMap(int i) {
		switch (i) {
		case 1:
			grid = new TileGrid(mapper.map2);
			break;
		case 2:
			grid = new TileGrid(mapper.map3);
			break;
		default:
			grid = new TileGrid(mapper.map1);
		}

		// check the players view
		if (gameObjects.get(0).getView()) {
			// update all grid holders
			for (GameObject e : gameObjects) {
				e.setGrid(grid);
			}
		} else {
			// update the two who are fightings grid only
			gameObjects.get(0).setGrid(grid);
			gameObjects.get(enemyIndex).setGrid(grid);
		}
	}

	private void createInitMap() {

		// 0= grass, 1= dirt, 2= water, 3= town. Maps can be made this way on
		// the 20x15 grid

		grid = new TileGrid(mapper.map1);
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

		return new Enemy(enemyAnim, grid.getTile(10, 10), 64, 64, 100, 7, grid);
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

		return new Player(playerAnimation, grid.getTile(0, 0), 64, 64, 100, 25,
				grid);

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
