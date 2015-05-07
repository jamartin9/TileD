package main;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import utils.Artist;
import utils.Clock;
import utils.Physics;


public class Controller {

	private View view;
	private static Model model;
	private static Mapper mapper;

	public Controller() {
		view = new View();
		mapper = new Mapper();
		model = new Model();
		model.addEnemy(creatEnemySkele());
		model.addPlayer(createPlayer());
		model.getPlayer().createTextBox(12, false, Color.white, "Sven", Artist.loadTexture("images/LAVA.png", "PNG"));
		//model.getPlayer().getText().setCleanText(false);
		Thread ViewThread = new Thread(view);
		view.releaseContext();
		ViewThread.start();
	}

	private static Enemy createEnemyGoblin() {
		/* Create animation for enemy */
		ArrayList<Texture> enemyTexs = new ArrayList<Texture>();
		float[] enemyTime = new float[5];
		// moving
		enemyTexs.add(Artist.loadTexture("images/Goblin.png", "PNG"));
		enemyTexs.add(Artist.loadTexture("images/goblin_walk_right.png", "PNG"));
		enemyTexs.add(Artist.loadTexture("images/goblin_attack_right.png", "PNG"));
		enemyTexs.add(Artist.loadTexture("images/goblin_walk_left.png", "PNG"));
		enemyTexs.add(Artist.loadTexture("images/goblin_attack_left.png", "PNG"));
		// set time
		enemyTime[0] = 0.8f;
		enemyTime[1] = 0.8f;
		enemyTime[2] = 0.8f;
		enemyTime[3] = 0.8f;
		enemyTime[4] = 0.8f;


		Animation enemyAnim = new Animation(enemyTexs, enemyTime);
		Enemy enemy = new Enemy(enemyAnim, model.getTile(8, 3), Artist.getScaleX()*2, Artist.getScaleY()*2, 100, 7, model.getGrid(), 2);
		enemy.createTextBox(12, false, Color.white, "Goblin", Artist.loadTexture("images/LAVA.png", "PNG"));
		enemy.setSound("goblin");
		return enemy;
	}

	private Player createPlayer() {
		/* Create animation for player */
		ArrayList<Texture> playerTexs = new ArrayList<Texture>();
		float[] time = new float[10];

		// moving down/up
		playerTexs.add(Artist.loadTexture("images/character_attack_right.png", "PNG"));
		// set time
		time[0] = 0.8f;
		// moving right
		playerTexs.add(Artist.loadTexture("images/char_right_stand.png", "PNG"));
		playerTexs.add(Artist.loadTexture("images/char_right_step_right.png", "PNG"));
		playerTexs.add(Artist.loadTexture("images/character_left_step_right.png", "PNG"));
		time[1] = 0.8f;
		time[2] = 0.8f;
		time[3] = 0.8f;
		// moving left
		playerTexs.add(Artist.loadTexture("images/char_left_stand.png", "PNG"));
		playerTexs.add(Artist.loadTexture("images/char_right_step_left.png", "PNG"));
		playerTexs.add(Artist.loadTexture("images/character_left_step_left.png", "PNG"));
		time[4] = 0.8f;
		time[5] = 0.8f;
		time[6] = 0.8f;
		time[7] = 0.8f;
		time[8] = 0.8f;
		time[9] = 0.8f;
		playerTexs.add(Artist.loadTexture("images/character_attack_left.png", "PNG"));
		playerTexs.add(Artist.loadTexture("images/character_back.png", "PNG"));
		playerTexs.add(Artist.loadTexture("images/character_front.png", "PNG"));


		Animation playerAnimation = new Animation(playerTexs, time);

		return new Player(playerAnimation, model.getTile(3, 2), Artist.getScaleX(), Artist.getScaleY(), 100, 18, model.getGrid());

	}

	public static void draw() {
		updateChanges();
		// update the clock
		Clock.update();
		model.update();
		model.draw();
	}
	
	private static int changeMaps = -1;
	private static void updateChanges(){
		if(changeMaps != -1){
			// switch maps and resize
			switch (changeMaps) {
				case 1:
					if(model.getPlayer().hasItem()){
						model.getPlayer().removeItem();
					}else{
						return;
					}
					// update each maps conditions
					model.getPlayer().setX(925);
					model.getPlayer().setY(100);
					model.makeEnemiesInactive();
					// remove key
					model.setGrid(mapper.getMAP5());
					Controller.resize();
					break;
				case 2:
					model.setGrid(mapper.getMAP2());
					Controller.resize();
					model.getPlayer().setX(100);
					model.getPlayer().setY(150);
					model.getCurrentEnemy().setX(Artist.getWidth()-150);
					model.getCurrentEnemy().setY(100f);
					break;
				case 3:
					model.setGrid(mapper.getMAP3());
					Controller.resize();
					model.setTopDown(false);
					model.getPlayer().setHeight(model.getPlayer().getHeight()*2);
					model.getPlayer().setWidth(model.getPlayer().getWidth()*2);
					model.getPlayer().setX(Artist.getWidth()-Artist.getWidth()/2);
					model.getPlayer().setY(Artist.getHeight()/2);
					model.setCurrent(createEnemyGoblin());

					break;
				case 4:
					model.setGrid(mapper.getMAP4());
					Controller.resize();
					break;
				case 5:
					model.setGrid(mapper.getMAP7());
					Controller.resizeIso();
					break;
				case 6:
					model.getPlayer().setX(100);
					model.getPlayer().setY(Artist.getHeight()/2);
					model.setGrid(mapper.getMAP8());
					Controller.resize();
					break;
				default:
					model.setGrid(mapper.getMAP1());
					Controller.resize();
					break;
			}
		}
		
		changeMaps = -1;
	}
	
	private static Enemy creatEnemySkele() {
		/* Create animation for enemy */
		ArrayList<Texture> enemyTexs = new ArrayList<Texture>();
		float[] enemyTime = new float[5];
		
		
		enemyTexs.add(Artist.loadTexture("images/skeleton.png", "PNG"));
		enemyTexs.add(Artist.loadTexture("images/skeleton_walk_right.png", "PNG"));
		enemyTexs.add(Artist.loadTexture("images/skeleton_attack_right.png", "PNG"));
		enemyTexs.add(Artist.loadTexture("images/skeleton_walk_left.png", "PNG"));
		enemyTexs.add(Artist.loadTexture("images/skeleton_attack_left.png", "PNG"));
		// set time
		enemyTime[0] = 0.8f;
		enemyTime[1] = 0.8f;
		enemyTime[2] = 0.8f;
		enemyTime[3] = 0.8f;
		enemyTime[4] = 0.8f;
		
		Animation enemyAnim = new Animation(enemyTexs, enemyTime);		
		Enemy enemy = new Enemy(enemyAnim, model.getTile(5, 5), Artist.getScaleX()*2, Artist.getScaleY()*2, 100, 7, model.getGrid(), 2);;
		
		ArrayList<Texture> itemTexs = new ArrayList<Texture>();
		float[] itemTime = new float [1];
		itemTexs.add(Artist.loadTexture("images/Dungeon_Key.png", "PNG"));
		itemTime[0]=0;
		Animation anim = new Animation(itemTexs,itemTime);
		Item item = new Item(anim,model.getTile(8, 3),Artist.getScaleX(),Artist.getScaleY(),100,7,model.getGrid());
		enemy.addItem(item);
		enemy.createTextBox(12, false, Color.white, "Skeleton", Artist.loadTexture("images/LAVA.png", "PNG"));
		enemy.setSound("skele");
		return enemy;
	}

	public static void changeMap(int i) {
		changeMaps = i;
	}
	
	public static void topDownUpdate(){
		// if the player runs into another object
		if(model.checkCollisionTopDown()){
			int ind = model.getEnemyIndex();
			// check if the object is a item
			if(model.getActive(ind).getClass().toString().equals("class main.Item")){
				model.getPlayer().addItem(model.getItem());
				model.removeItem();
				model.setTopDown(true);
			}else{
			// change map to a combat map
			changeMap(model.getCurrentEnemy().getcmbMap());}
		}
	}
		
	public static void sideScrollUpdate(){
		if(!model.getCurrentEnemy().isPlaying()){
			model.getCurrentEnemy().playSound();
		}
		
		/*If we hit*/
		if(model.checkCollisionSideScroll()){
			//model.getPlayer().setHealth(model.getPlayer().getHealth()-5);
			//amodel.getCurrentEnemy().setHealth(model.getCurrentEnemy().getHealth()-2);
		}
		if (model.getPlayer().getHealth() <= 0) {
			// Splash screen for death
			System.out.println("GAME OVER");
			View.setOver();
		}

		// if enemy died
		else if (model.getCurrentEnemy().getHealth() <= 0) {
			Item item = null;
			if(model.getCurrentEnemy().hasItem()){
				item = model.getCurrentEnemy().getItem();
			}
			model.getCurrentEnemy().resetSound();
			model.removeCurrentEnemy();
			// put view back on player
			model.setTopDown(true);
			model.getPlayer().setGrid(model.getTopDownGrid());
			model.getPlayer().setView(model.getTopDown());
			model.getPlayer().setWidth(Artist.getScaleX());
			model.getPlayer().setHeight(Artist.getScaleY());
			model.getPlayer().setHealth(100);
			model.resetPlayerCords();
			model.resetTopDownGrid();
			// resize for old grid
			resize();
			if(item != null){
				model.addItem(item);
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
			if(newWidth > Display.getDesktopDisplayMode().getWidth()){newWidth=Display.getDesktopDisplayMode().getWidth() - (Display.getDesktopDisplayMode().getWidth() %Artist.getArWidth());}
		}
		else if(Display.getDisplayMode().getWidth() < Display.getDisplayMode().getHeight()){
			size = Display.getDesktopDisplayMode().getWidth();
			// (px difference   -  remaining pixels) + oldSize
			newWidth = size - (size %Artist.getArWidth());
			newHeight = (newWidth*Artist.getArHeight())/Artist.getArWidth();
			if(newHeight > Display.getDesktopDisplayMode().getHeight()){newHeight=Display.getDesktopDisplayMode().getHeight() - (Display.getDesktopDisplayMode().getHeight() %Artist.getArHeight());}

		}
		//same size
		else{
			// if the height/width are even (when will this happen?)
			// pick the larger of the AR sizes
			if(Artist.getArHeight()>Artist.getArWidth()){
				size = Display.getDesktopDisplayMode().getHeight();
				newHeight = size - (size %Artist.getArHeight());
				newWidth = (newHeight*Artist.getArWidth())/Artist.getArHeight();
				if(newWidth > Display.getDesktopDisplayMode().getWidth()){newWidth=Display.getDesktopDisplayMode().getWidth() - (Display.getDesktopDisplayMode().getWidth() %Artist.getArWidth());}
			}else{
				size = Display.getDesktopDisplayMode().getWidth();
				// (px difference   -  remaining pixels) + oldSize
				newWidth = size - (size %Artist.getArWidth());
				newHeight = (newWidth*Artist.getArHeight())/Artist.getArWidth();
				if(newHeight > Display.getDesktopDisplayMode().getHeight()){newHeight=Display.getDesktopDisplayMode().getHeight() - (Display.getDesktopDisplayMode().getHeight() %Artist.getArHeight());}

			}
			
		}
		Artist.setHeight(newHeight);
		Artist.setWidth(newWidth);
		Artist.setScaleX(newWidth/Artist.getArWidth());
		Artist.setScaleY(newHeight/Artist.getArHeight());
		model.setScale(Artist.getScaleX(),Artist.getScaleY());
		
		Display.setLocation(0, 0);
		try {
			/*// full screen mode
	        DisplayMode displayMode = null;
	        DisplayMode[] modes = Display.getAvailableDisplayModes();
	        //System.out.println("The desired is "+newWidth+" "+newHeight);
	         for (int i = 0; i < modes.length; i++)
	         {
	             if (modes[i].getWidth() >= Display.getDesktopDisplayMode().getWidth()
	             && modes[i].getHeight() >= Display.getDesktopDisplayMode().getHeight() &&
	              modes[i].isFullscreenCapable())
	               {
	                    displayMode = modes[i];
	                   // System.out.println(modes[i].getHeight()+" "+modes[i].getWidth());
	               }
	         }*/
			//Display.setFullscreen(true);
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
			if(newWidth > Display.getDesktopDisplayMode().getWidth()){newWidth=Display.getDesktopDisplayMode().getWidth() - (Display.getDesktopDisplayMode().getWidth() %Artist.getArWidth());}
		}
		else if(Display.getDisplayMode().getWidth() < Display.getDisplayMode().getHeight()){
			size = Display.getDesktopDisplayMode().getWidth();
			// (px difference   -  remaining pixels) + oldSize
			newWidth = size - (size %Artist.getArWidth());
			newHeight = (newWidth*Artist.getArHeight())/Artist.getArWidth();
			if(newHeight > Display.getDesktopDisplayMode().getHeight()){newHeight=Display.getDesktopDisplayMode().getHeight() - (Display.getDesktopDisplayMode().getHeight() %Artist.getArHeight());}
		}
		//same size
		else{
			// if the height/width are even (when will this happen?)
			// pick the larger of the AR sizes
			if(Artist.getArHeight()>Artist.getArWidth()){
				size = Display.getDesktopDisplayMode().getHeight();
				newHeight = size - (size %Artist.getArHeight());
				newWidth = (newHeight*Artist.getArWidth())/Artist.getArHeight();
				if(newWidth > Display.getDesktopDisplayMode().getWidth()){newWidth=Display.getDesktopDisplayMode().getWidth() - (Display.getDesktopDisplayMode().getWidth() %Artist.getArWidth());}
			}else{
				size = Display.getDesktopDisplayMode().getWidth();
				// (px difference   -  remaining pixels) + oldSize
				newWidth = size - (size %Artist.getArWidth());
				newHeight = (newWidth*Artist.getArHeight())/Artist.getArWidth();
				if(newHeight > Display.getDesktopDisplayMode().getHeight()){newHeight=Display.getDesktopDisplayMode().getHeight() - (Display.getDesktopDisplayMode().getHeight() %Artist.getArHeight());}
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
			DisplayMode newDisplay = new DisplayMode(newWidth,newHeight);
			Display.setFullscreen(true);
			System.out.println(newDisplay.isFullscreenCapable());
			Display.setDisplayMode(newDisplay);
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

	public static boolean getColl() {
		return Physics.collidesObject(model.getPlayer(), model.getCurrentEnemy());		
	}

	public static void doDamage(int i, String className) {
		switch(className){
		case "class main.Enemy":
			model.getPlayer().setHealth(model.getPlayer().getHealth()-i);
			model.getCurrentEnemy().getText().setString(Integer.toString(i));
			model.getCurrentEnemy().getText().setCleanText(false);
			break;
		case "class main.Player":
			model.getCurrentEnemy().setHealth(model.getCurrentEnemy().getHealth()-i);
			model.getPlayer().getText().setString(Integer.toString(i));
			model.getPlayer().getText().setCleanText(false);
			break;
		default:
			System.out.println("Do damage failed on switch");
		}
	}
	

}
