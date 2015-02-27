package main;

public class Mapper {
	
	TileGrid MAP1 ;
	TileGrid MAP2 ;
	TileGrid MAP3 ;
	
	public Mapper(){

		MAP1 = new TileGrid(new int[][]{			
				{1,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,2,2,2,2},
				{2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{2,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{2,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{2,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{2,0,0,0,2,1,2,2,2,2,2,0,0,0,0,0,0,0,0,0},	
				{2,0,0,0,2,0,1,0,0,0,2,0,0,0,0,0,0,0,0,0},	
				{2,0,0,0,2,0,0,1,0,0,2,0,0,0,0,0,0,0,0,0},	
				{2,0,0,0,2,0,0,0,1,0,2,0,0,0,0,0,0,0,0,0},	
				{2,0,0,0,2,0,0,0,0,1,2,0,0,0,0,0,0,0,0,0},	
				{2,0,0,0,2,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},	
				{2,0,0,0,0,2,2,2,2,0,0,1,0,0,0,0,0,0,0,0},	
				{2,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},	
				{2,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},	
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1},
				});
		

		MAP2 = new TileGrid(new int [][]{
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,4,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},	
		});
		
		MAP3 = new TileGrid(new int [][]{
				{0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},	
		});
	}
}