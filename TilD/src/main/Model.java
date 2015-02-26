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
	private ArrayList<GameObject> gameObjects;
	// current enemy that players are fighting
	private int enemyIndex;
	// the players index
	private int playerIndex;
	private float playerX;
	private float playerY;
	// copy of grid for when world flips
	private TileGrid topDownGrid;

	public Model() {
		gameObjects = new ArrayList<GameObject>();
		enemyIndex = 0;
		playerIndex = 0;
		viewTopDown = true;
		grid = new TileGrid();
	}

	public void update() {
		/*TODO: Possibly move logic to Controller*/

		// if we are in topDown view
		if (viewTopDown) {
			// for each gameObject
			for (int i = 0; i < gameObjects.size(); i++) {
				// update
				gameObjects.get(i).update();

				// check for collision for all enemies with player
				if (i != playerIndex && Physics.collides(getPlayer(), gameObjects.get(i))) {

					// save index, grid, x, y
					enemyIndex = i;
					topDownGrid = grid;
					playerX = getPlayer().getX();
					playerY = getPlayer().getY();

					// change map
					Controller.changeMap(2);

					// set player and enemy to new positions, sizes and views
					getPlayer().setWidth(128);
					getPlayer().setHeight(128);
					getPlayer().setX(5);
					getPlayer().setY(Artist.getHeight() - getPlayer().getHeight() * 2);
					getCurrentEnemy().setWidth(128);
					getCurrentEnemy().setHeight(128);
					getCurrentEnemy().setX(Artist.getWidth() - getCurrentEnemy().getWidth() - 7);
					getCurrentEnemy().setY(Artist.getHeight() - getCurrentEnemy().getHeight() * 2);

					// update views
					viewTopDown = false;
					getPlayer().setView(viewTopDown);
					getCurrentEnemy().setView(viewTopDown);
					return;

				}
			}
		}
		// if not in overhead top down view
		else {
			// update player and opponent respectively
			getPlayer().update();
			getCurrentEnemy().update();

			// make objects fall
			Physics.applyGravity(getPlayer());
			Physics.applyGravity(getCurrentEnemy());

			// check collision
			if (Physics.collides(getPlayer(),getCurrentEnemy())) {
				/* kill enemy for now */
				getCurrentEnemy().setHealth(0);

				// if player died
				if (getPlayer().getHealth() <= 0) {
					System.out.println("GAME OVER");
					System.exit(0);
				}

				// if enemy died
				else if (getCurrentEnemy().getHealth() <= 0) {

					// remove dead enemy
					gameObjects.remove(enemyIndex);
					if(enemyIndex < playerIndex){
						playerIndex--;
					}
					enemyIndex = 0;

					// put view back on player
					viewTopDown = true;
					getPlayer().setGrid(topDownGrid);
					getPlayer().setView(viewTopDown);
					getPlayer().setWidth(64);
					getPlayer().setHeight(64);
					getPlayer().setX(playerX);
					getPlayer().setY(playerY);

					// put world view back
					grid = topDownGrid;
				}
			}
		}
	}

	public void draw() {
		grid.draw();
		// redraw objects
		if (viewTopDown) {
			for (GameObject e : gameObjects) {
				e.Draw();
			}
		} else {
			// redraw player and enemy
			gameObjects.get(playerIndex).Draw();
			gameObjects.get(enemyIndex).Draw();
		}
	}

	public void addPlayer(Player player) {
		player.setView(viewTopDown);
		gameObjects.add(player);
		playerIndex = gameObjects.size()-1;
	}

	public void addEnemy(Enemy enemy) {
		enemy.setView(viewTopDown);
		gameObjects.add(enemy);
	}
	
	public GameObject getPlayer(){
		return gameObjects.get(playerIndex);
	}
	
	public GameObject getCurrentEnemy(){
		return gameObjects.get(enemyIndex);
	}

	public void setGrid(TileGrid newGrid) {
		grid = newGrid;
		// check the players view
		if (gameObjects.get(playerIndex).getView()) {

			// update all grid holders
			for (GameObject e : gameObjects) {
				e.setGrid(grid);
			}
		} else {
			// update the two who are fighting grids only
			gameObjects.get(playerIndex).setGrid(grid);
			gameObjects.get(enemyIndex).setGrid(grid);
		}
	}
	
	public Tile getTile(int x, int y){
		return grid.getTile(x, y);
	}
	
	public TileGrid getGrid(){
		return grid;
	}
}
