package main;

import java.util.ArrayList;

import utils.Artist;
import utils.Physics;

public class Model {
	
	// top/down or side scroll
	private boolean viewTopDown;
	// copy of the map
	private TileGrid grid;
	// list of all objects that can interact (non-terrain)
	private ArrayList<GameObject> activeObjects;
	private ArrayList<GameObject> inactiveObjects;
	// current enemy that players are fighting
	private int enemyIndex;
	// the players index
	private int playerIndex;
	private float playerX;
	private float playerY;
	// copy of grid for when world flips
	private TileGrid topDownGrid;

	public Model() {
		activeObjects = new ArrayList<GameObject>();
		inactiveObjects = new ArrayList<GameObject>();
		enemyIndex = 0;
		playerIndex = 0;
		viewTopDown = true;
		grid = Controller.getInitMap();
		topDownGrid = grid;

	}

	public void update() {

		// if we are in topDown view
		if (viewTopDown) {
			Controller.topDownUpdate();

		}
		// if not in overhead top down view
		else {
			Controller.sideScrollUpdate();
		}
	}

	public void draw() {
		grid.draw();
		// redraw objects
		for (GameObject e : activeObjects) {
			e.Draw();
		}
	
	}

	public boolean checkCollisionTopDown() {
		for (int i = 0; i < activeObjects.size(); i++) {
			// update
			activeObjects.get(i).update();

			// check for collision for all enemies with player
			if (i != playerIndex && Physics.collidesObject(getPlayer(), activeObjects.get(i))) {

				// save index, grid, x, y
				enemyIndex = i;
				topDownGrid = grid;
				playerX = getPlayer().getX();
				playerY = getPlayer().getY();

				// update views
				viewTopDown = false;
				getPlayer().setView(viewTopDown);
				getCurrentEnemy().setView(viewTopDown);
				return true;

			}
		}
		return false;
	}

	public boolean getTopDown(){
		return viewTopDown;
	} 
	
	public TileGrid getTopDownGrid(){
		return topDownGrid;
	}
	
	public void setTopDown(boolean topdown){
		viewTopDown=topdown;
	}
	
	public void resetPlayerCords(){
		getPlayer().setX(playerX);
		getPlayer().setY(playerY);
	}
	
	public boolean checkCollisionSideScroll() {

		// update player and opponent respectively
		getPlayer().update();
		if(enemyIndex != -1){
			getCurrentEnemy().update();
			// make objects fall
			Physics.applyGravity(getPlayer());
			Physics.applyGravity(getCurrentEnemy());

			// check collision
			if (Physics.collidesObject(getPlayer(), getCurrentEnemy())) {return true;}
		}else{
			// make objects fall
			Physics.applyGravity(getPlayer());
		}
		return false;
	}
	
	public void addInactives(){
		for(GameObject e:inactiveObjects){
			activeObjects.add(e);
		}
	}
	
	public void removeCurrentEnemy(){
		// remove dead enemy
		activeObjects.remove(enemyIndex);
		if (enemyIndex < playerIndex) {
			playerIndex--;
		}
		activeObjects.trimToSize();
		enemyIndex = -1;
	}

	public void makeEnemiesInactive(){
		for(int i =0; i<activeObjects.size();i++){
			if(i != playerIndex){
				inactiveObjects.add(activeObjects.get(i));
				activeObjects.remove(i);
				if (enemyIndex < playerIndex) {
					playerIndex--;
				}
			}
		}
		activeObjects.trimToSize();
	}
	
	public void addPlayer(Player player) {
		player.setView(viewTopDown);
		activeObjects.add(player);
		playerIndex = activeObjects.size() - 1;
	}

	public void addEnemy(Enemy enemy) {
		enemy.setView(viewTopDown);
		activeObjects.add(enemy);
		enemyIndex=activeObjects.size()-1;
	}

	public Player getPlayer() {
		return (Player) activeObjects.get(playerIndex);
	}

	public Enemy getCurrentEnemy() {
		if (enemyIndex == -1){return null;}
		else{return (Enemy) activeObjects.get(enemyIndex);}
	}

	public void setGrid(TileGrid newGrid) {
		grid = newGrid;
		// update all active grid holders
		for (GameObject e : activeObjects) {
			e.setGrid(grid);
			
		}

	}

	public Tile getTile(int x, int y) {
		return grid.getTile(x, y);
	}

	public TileGrid getGrid() {
		return grid;
	}

	public void resetTopDownGrid() {
		// put world view back
		grid = topDownGrid;	
		Artist.setArHeight(grid.getHeight());
		Artist.setArWidth(grid.getWidth());

	}

	public void setScale(int scaleX, int scaleY) {
		grid.resize(scaleX,scaleY);
		setGrid(grid);
		for (GameObject e : activeObjects) {
			e.setWidth(scaleX);
			e.setHeight(scaleY);
		}
	}

	public void setIsoView(boolean b) {
			for (GameObject e : activeObjects){
				e.setIso(b);
			}
	}
}
