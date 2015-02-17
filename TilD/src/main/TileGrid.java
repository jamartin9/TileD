package main;

public class TileGrid {

	public Tile[][] map;
	public TileGrid() {
		map= new Tile[20][15];
		for(int i =0; i<map.length;i++){
			for(int j =0;j<map[i].length;j++){
				map[i][j] = new Tile(i*64,j*64,64,64,TileType.Grass);
				
			}
			
		}
	}

	public TileGrid(int [][] newMap){
		map= new Tile[20][15];
		for(int i =0; i<map.length;i++){
			for(int j =0;j<map[i].length;j++){
				
				switch(newMap[j][i]){
					case 0:
						map[i][j] = new Tile(i*64,j*64,64,64,TileType.Grass);
						break;
					case 1:
						map[i][j] = new Tile(i*64,j*64,64,64,TileType.Dirt);
						break;
					case 2:
						map[i][j] = new Tile(i*64,j*64,64,64,TileType.Water);
						break;
					case 3:
						map[i][j] = new Tile(i*64,j*64,64,64,TileType.Town);
						break;
					default:
						map[i][j] = new Tile(i*64,j*64,64,64,TileType.Grass);
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
	
	public void setTile(int x, int y, TileType type){
		map[x][y] = new Tile(x*64,y*64,64,64,type);
	}
	public Tile getTile(int x,int y){
		return map[x][y];
	}
}
