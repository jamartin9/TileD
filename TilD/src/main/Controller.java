package main;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

import utils.Artist;
import utils.Clock;


public class Controller {

	private View view;
	private static Model model;
	private static Mapper mapper;


	public Controller() {
		view = new View();
		mapper = new Mapper();
		model = new Model();
		model.addEnemy(createEnemyGoblin());
		model.addPlayer(createPlayer());
		Thread ViewThread = new Thread(view);
		view.releaseContext();
		ViewThread.start();
	}

	private Enemy createEnemyGoblin() {
		/* Create animation for enemy */
		ArrayList<Texture> enemyTexs = new ArrayList<Texture>();
		float[] enemyTime = new float[1];
		// moving
		enemyTexs.add(Artist.loadTexture("images/enemy.png", "PNG"));
		// set time
		enemyTime[0] = 0;
		Animation enemyAnim = new Animation(enemyTexs, enemyTime);

		return new Enemy(enemyAnim, model.getTile(10, 10), Artist.getScaleX(), Artist.getScaleY(),
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

		return new Player(playerAnimation, model.getTile(0, 0), Artist.getScaleX(),
				Artist.getScaleY(), 100, 25, model.getGrid());

	}

	
	public static void draw() {
		// update the clock
		Clock.update();
		model.update();
		model.draw();
	}

	
	public static void changeMap(int i) {
		switch (i) {
			case 1:
				if(mapper.MAP2.getTile(0, 0).getWidth() != Artist.getWidth()){mapper.MAP2.resize(Artist.getScaleX(), Artist.getScaleY());}
				model.setGrid(mapper.MAP2);
				break;
			case 2:
				if(mapper.MAP3.getTile(0, 0).getWidth() != Artist.getWidth()){mapper.MAP3.resize(Artist.getScaleX(), Artist.getScaleY());}
				model.setGrid(mapper.MAP3);
				break;
			case 3:
				if(mapper.MAP4.getTile(0, 0).getWidth() != Artist.getWidth()){mapper.MAP4.resize(Artist.getScaleX(), Artist.getScaleY());}
				model.setGrid(mapper.MAP4);
				break;
			default:
				if(mapper.MAP1.getTile(0, 0).getWidth() != Artist.getWidth()){mapper.MAP1.resize(Artist.getScaleX(), Artist.getScaleY());}
				model.setGrid(mapper.MAP1);
				break;
		}
	}
	
	public static void topDownUpdate(){
		
		if(model.checkCollisionTopDown()){

			// change map
			changeMap(2);

			// set player and enemy to new positions, sizes and views
			model.getPlayer().setWidth(Artist.getScaleX()*2);
			model.getPlayer().setHeight(Artist.getScaleY()*2);
			model.getPlayer().setX(5);
			model.getPlayer().setY(Artist.getHeight() -model.getPlayer().getHeight() * 2);
			model.getCurrentEnemy().setWidth(Artist.getScaleX()*2);
			model.getCurrentEnemy().setHeight(Artist.getScaleY()*2);
			model.getCurrentEnemy().setX(Artist.getWidth() - model.getCurrentEnemy().getWidth() - 7);
			model.getCurrentEnemy().setY(Artist.getHeight() - model.getCurrentEnemy().getHeight() * 2);

		}
	}
	
	public static TileGrid getInitMap(){
		return mapper.MAP1;
	}
	
	public static void sideScrollUpdate(){
		if(model.checkCollisionSideScroll()){
		/* kill enemy for now */
		model.getCurrentEnemy().setHealth(0);

		// if player died
		if (model.getPlayer().getHealth() <= 0) {
			System.out.println("GAME OVER");
			System.exit(0);
		}

		// if enemy died
		else if (model.getCurrentEnemy().getHealth() <= 0) {
			model.removeEnemy();
			// put view back on player
			model.setTopDown(true);
			model.getPlayer().setGrid(model.getTopDownGrid());
			model.getPlayer().setView(model.getTopDown());
			model.getPlayer().setWidth(Artist.getScaleX());
			model.getPlayer().setHeight(Artist.getScaleY());
			model.resetPlayerCords();
			model.resetTopDownGrid();
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		Controller boot = new Controller();
	}

	public static void resize(int width, int height) {
		int minArWidth = 20*32;
		int minArHeight = 15*32;
		// check the minimum
		if(width < minArWidth || height < minArHeight){
			try {
				Display.setDisplayMode(new DisplayMode(1280,960));
			} catch (LWJGLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		// set height and width based on the new width 
		int oldWidth = Artist.getWidth();
		int oldHeight = Artist.getHeight();
		// the newWidth = (px difference   -  remaining pixels) + oldWidth
		//int newWidth = ((width - oldWidth)-((width-oldWidth)%20))+oldWidth;
		// set the height on the based on new width
		//int newHeight = (newWidth*15)/20;
		int newHeight = ((height - oldHeight)-((height-oldHeight)%15))+oldHeight;
		int newWidth = (newHeight*20)/15;
		Artist.setHeight(newHeight);
		Artist.setWidth(newWidth);
		Artist.setScaleX(newWidth/20);
		Artist.setScaleY(newHeight/15);
		model.setScale(Artist.getScaleX(),Artist.getScaleY());
		
		Display.setResizable(false);
		Display.setLocation(0, 0);
		try {
			Display.setDisplayMode(new DisplayMode(newWidth,newHeight));
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Artist.resize();


	}
	

}
