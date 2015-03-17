package main;

import utils.Artist;

public class TileGrid {

	public Tile[][] map;
	public TileGrid() {
		map= new Tile[20][15];
		for(int i =0; i<map.length;i++){
			for(int j =0;j<map[i].length;j++){
				map[i][j] = new Tile(i*Artist.getScaleX(),j*Artist.getScaleY(),Artist.getScaleX(),Artist.getScaleY(),TileType.Grass);
			}
		}
	}

	public TileGrid(int [][] newMap){
		map= new Tile[20][15];
		for(int i =0; i<map.length;i++){
			for(int j =0;j<map[i].length;j++){
				
				switch(newMap[j][i]){
					case 0:
						map[i][j] = new Tile(i*Artist.getScaleX(),j*Artist.getScaleY(),Artist.getScaleX(),Artist.getScaleY(),TileType.Grass);
						break;
					case 1:
						map[i][j] = new Tile(i*Artist.getScaleX(),j*Artist.getScaleY(),Artist.getScaleX(),Artist.getScaleY(),TileType.Dirt);
						break;
					case 2:
						map[i][j] = new Tile(i*Artist.getScaleX(),j*Artist.getScaleY(),Artist.getScaleX(),Artist.getScaleY(),TileType.Water);
						break;
					case 3:
						map[i][j] = new Tile(i*Artist.getScaleX(),j*Artist.getScaleY(),Artist.getScaleX(),Artist.getScaleY(),TileType.Town);
						break;
					case 4:
						map[i][j] = new Tile(i*Artist.getScaleX(),j*Artist.getScaleY(),Artist.getScaleX(),Artist.getScaleY(),TileType.Stone);
						break;
					case 5:
						map[i][j] = new Tile(i*Artist.getScaleX(),j*Artist.getScaleY(),Artist.getScaleX(),Artist.getScaleY(),TileType.Sky);
						break;
					case 6:
						map[i][j] = new Tile(i*Artist.getScaleX(),j*Artist.getScaleY(),Artist.getScaleX(),Artist.getScaleY(),TileType.Wood);
						break;
					case 7:
						map[i][j] = new Tile(i*Artist.getScaleX(),j*Artist.getScaleY(),Artist.getScaleX(),Artist.getScaleY(),TileType.Leaf);
						break;
					case 8:
						map[i][j] = new Tile(i*Artist.getScaleX(),j*Artist.getScaleY(),Artist.getScaleX(),Artist.getScaleY(),TileType.cloud);
						break;
					default:
						map[i][j] = new Tile(i*Artist.getScaleX(),j*Artist.getScaleY(),Artist.getScaleX(),Artist.getScaleY(),TileType.Grass);
						break;
				}
			}
			
		}
	}
	
	public void draw(){
		for(int i =0; i<map.length;i++){
			for(int j =0;j<map[i].length;j++){
				map[i][j].draw();
				
			}
			
		}
	}
	public void resize(int sizeWidth,int sizeHeight){
		for(int i =0; i<map.length;i++){
			for(int j =0;j<map[i].length;j++){
				map[i][j].resize(sizeWidth, sizeHeight, i*sizeWidth,j*sizeHeight);
				
			}
			
		}
	}
	
	public void setTile(int x, int y, TileType type){
		map[x][y] = new Tile(x*Artist.getScaleX(),y*Artist.getScaleY(),Artist.getScaleX(),Artist.getScaleY(),type);
	}
	public Tile getTile(int x,int y){
		return map[x][y];
	}
}
