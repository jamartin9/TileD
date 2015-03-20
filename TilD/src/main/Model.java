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
		if (viewTopDown) {
			for (GameObject e : activeObjects) {
				e.Draw();
			}
		} else {
			// redraw player and enemy
			activeObjects.get(playerIndex).Draw();
			activeObjects.get(enemyIndex).Draw();
		}
	}

	public boolean checkCollisionTopDown() {
		for (int i = 0; i < activeObjects.size(); i++) {
			// update
			activeObjects.get(i).update();

			// check for collision for all enemies with player
			if (i != playerIndex
					&& Physics.collides(getPlayer(), activeObjects.get(i))) {

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
		getCurrentEnemy().update();

		// make objects fall
		Physics.applyGravity(getPlayer());
		Physics.applyGravity(getCurrentEnemy());

		// check collision
		if (Physics.collides(getPlayer(), getCurrentEnemy())) {
			return true;

		}
		return false;
	}
	
	public GameObject getInactive(int index){
		return inactiveObjects.get(index);
	}
	
	public void removeEnemy(){
		// save object for later
		inactiveObjects.add(activeObjects.get(enemyIndex));
		// remove dead enemy
		activeObjects.remove(enemyIndex);
		if (enemyIndex < playerIndex) {
			playerIndex--;
		}
		enemyIndex = 0;
	}

	public void addPlayer(Player player) {
		player.setView(viewTopDown);
		activeObjects.add(player);
		playerIndex = activeObjects.size() - 1;
	}

	public void addEnemy(Enemy enemy) {
		enemy.setView(viewTopDown);
		activeObjects.add(enemy);
	}

	public GameObject getPlayer() {
		return activeObjects.get(playerIndex);
	}

	public GameObject getCurrentEnemy() {
		return activeObjects.get(enemyIndex);
	}

	public void setGrid(TileGrid newGrid) {
		grid = newGrid;
		// check the players view
		if (activeObjects.get(playerIndex).getView()) {
			
			// update all grid holders
			for (GameObject e : activeObjects) {
				e.setGrid(grid);
				
			}
		} else {
			// update the two who are fighting grids only
			activeObjects.get(playerIndex).setGrid(grid);
			activeObjects.get(enemyIndex).setGrid(grid);
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
		topDownGrid.resize(scaleX, scaleY);
		for (GameObject e : activeObjects) {
			e.setWidth(scaleX);
			e.setHeight(scaleY);
		}
	}
}
