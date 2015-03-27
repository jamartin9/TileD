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
		enemyTexs.add(Artist.loadTexture("images/Goblin.png", "PNG"));
		// set time
		enemyTime[0] = 0;
		Animation enemyAnim = new Animation(enemyTexs, enemyTime);

		return new Enemy(enemyAnim, model.getTile(8, 3), Artist.getScaleX(), Artist.getScaleY(),
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

		return new Player(playerAnimation, model.getTile(3, 2), Artist.getScaleX(),
				Artist.getScaleY(), 100, 25, model.getGrid());

	}
	
	public static void draw() {
		// update the clock
		Clock.update();
		model.update();
		model.draw();
	}
	
	public static void changeMap(int i) {
		// switch maps and resize
		switch (i) {
			case 1:
				model.setGrid(mapper.getMAP2());
				Controller.resize();
				break;
			case 2:
				model.setGrid(mapper.getMAP5());
				Controller.resize();
				break;
			case 3:
				model.setGrid(mapper.getMAP3());
				Controller.resize();
				break;
			case 4:
				model.setGrid(mapper.getMAP4());
				Controller.resize();
				break;
			case 5:
				model.setGrid(mapper.getMAP7());
				Controller.resizeIso();
				break;
			default:
				model.setGrid(mapper.getMAP1());
				Controller.resize();
				break;
		}
	}
	


	public static void topDownUpdate(){
		// if the player runs into another object
		if(model.checkCollisionTopDown()){

			// change map to a combat map
			changeMap(5);

			// set player and enemy to new positions, sizes and views
			model.getPlayer().setWidth(Artist.getScaleX());
			model.getPlayer().setHeight(Artist.getScaleY());
			model.getPlayer().setX(Artist.getScaleX()*2 +1);
			model.getPlayer().setY(Artist.getScaleY()*2 +1);
			model.getCurrentEnemy().setWidth(Artist.getScaleX());
			model.getCurrentEnemy().setHeight(Artist.getScaleY());
			model.getCurrentEnemy().setX(Artist.getWidth() - Artist.getScaleX()*2 +1);
			model.getCurrentEnemy().setY(Artist.getHeight() - Artist.getScaleY()*2 +1);

		}
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
			model.setIsoView(false);
			model.getPlayer().setGrid(model.getTopDownGrid());
			model.getPlayer().setView(model.getTopDown());
			model.getPlayer().setWidth(Artist.getScaleX());
			model.getPlayer().setHeight(Artist.getScaleY());
			model.resetPlayerCords();
			model.resetTopDownGrid();
			// resize for old grid
			resize();
			}
		}
	}
	
	public static void resize() {	
		int size;
		int newHeight;
		int newWidth;
		// pick smallest dimension of Width|Height based on screen and set size to the it
		if(Display.getDisplayMode().getWidth() > Display.getDisplayMode().getHeight()){
			size = Display.getDesktopDisplayMode().getHeight();
			newHeight = size - (size %Artist.getArHeight());
			newWidth = (newHeight*Artist.getArWidth())/Artist.getArHeight();
		}
		else if(Display.getDisplayMode().getWidth() < Display.getDisplayMode().getHeight()){
			size = Display.getDesktopDisplayMode().getWidth();
			// (px difference   -  remaining pixels) + oldSize
			newWidth = size - (size %Artist.getArWidth());
			newHeight = (newWidth*Artist.getArHeight())/Artist.getArWidth();
		}
		//same size
		else{
			// if the height/width are even (when will this happen?)
			// pick the larger of the AR sizes
			if(Artist.getArHeight()>Artist.getArWidth()){
				size = Display.getDesktopDisplayMode().getHeight();
				newHeight = size - (size %Artist.getArHeight());
				newWidth = (newHeight*Artist.getArWidth())/Artist.getArHeight();
			}else{
				size = Display.getDesktopDisplayMode().getWidth();
				// (px difference   -  remaining pixels) + oldSize
				newWidth = size - (size %Artist.getArWidth());
				newHeight = (newWidth*Artist.getArHeight())/Artist.getArWidth();
			}
			
		}
		Artist.setHeight(newHeight);
		Artist.setWidth(newWidth);
		Artist.setScaleX(newWidth/Artist.getArWidth());
		Artist.setScaleY(newHeight/Artist.getArHeight());
		model.setScale(Artist.getScaleX(),Artist.getScaleY());
		
		Display.setLocation(0, 0);
		try {
			Display.setDisplayMode(new DisplayMode(newWidth,newHeight));
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Artist.resize();


	}
	
	public static TileGrid getInitMap(){
		return mapper.getMAP1();
	}
	
	private static void resizeIso() {
		model.setIsoView(true);
		int size;
		int newHeight;
		int newWidth;
		// pick smallest dimension of Width|Height based on screen and set size to the it
		if(Display.getDisplayMode().getWidth() > Display.getDisplayMode().getHeight()){
			size = Display.getDesktopDisplayMode().getHeight();
			newHeight = size - (size %Artist.getArHeight());
			newWidth = (newHeight*Artist.getArWidth())/Artist.getArHeight();
		}
		else if(Display.getDisplayMode().getWidth() < Display.getDisplayMode().getHeight()){
			size = Display.getDesktopDisplayMode().getWidth();
			// (px difference   -  remaining pixels) + oldSize
			newWidth = size - (size %Artist.getArWidth());
			newHeight = (newWidth*Artist.getArHeight())/Artist.getArWidth();
		}
		//same size
		else{
			// if the height/width are even (when will this happen?)
			// pick the larger of the AR sizes
			if(Artist.getArHeight()>Artist.getArWidth()){
				size = Display.getDesktopDisplayMode().getHeight();
				newHeight = size - (size %Artist.getArHeight());
				newWidth = (newHeight*Artist.getArWidth())/Artist.getArHeight();
			}else{
				size = Display.getDesktopDisplayMode().getWidth();
				// (px difference   -  remaining pixels) + oldSize
				newWidth = size - (size %Artist.getArWidth());
				newHeight = (newWidth*Artist.getArHeight())/Artist.getArWidth();
			}
			
		}
		
		int scaleX = newWidth/Artist.getArWidth();
		int scaleY = newHeight/Artist.getArHeight();
		
		// specific to isoMetric
		newWidth += scaleX/2;
		newHeight -= (scaleY/2)*(Artist.getArHeight()-2);

		// set new values 
		Artist.setHeight(newHeight);
		Artist.setWidth(newWidth);
		Artist.setScaleX(scaleX);
		Artist.setScaleY(scaleY);
		model.setScale(Artist.getScaleX(),Artist.getScaleY());
		
		// set the display back location and size
		Display.setLocation(0, 0);
		try {
			Display.setDisplayMode(new DisplayMode(newWidth,newHeight));
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// resize opengl
		Artist.resize();
		
		// set the Artists width back for bounds checking
		Artist.setWidth(newWidth-scaleX/2);
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		Controller boot = new Controller();
	}
	

}
