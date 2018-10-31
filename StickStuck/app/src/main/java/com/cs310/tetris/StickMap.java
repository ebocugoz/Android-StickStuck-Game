package com.cs310.tetris;

import android.util.Log;

public class StickMap {
	public static final int MAP_X_SIZE = 10;
	public static final int MAP_Y_SIZE = 20;
	private int[][] map;
	public static MainMap mMainMap;
	//score
	public static int score=0;
	public static int highscore;



	public StickMap() {
		map = new int[MAP_X_SIZE][MAP_Y_SIZE];
		this.resetMap();	
	}
	
	public StickMap(int x, int y) {
		//TODO implement this for any map size
	}
	
	public void resetMap() {
		for(int x = 0; x < MAP_X_SIZE; x++) {
			for(int y = 0; y < MAP_Y_SIZE;y++) {
				map[x][y] = 0;
			}
		}
	}
	

	public boolean putStickOnMap(Stick shape) {
		for(int col = 0; col < shape.getSize(); col++){
			for(int row = 0; row < shape.getSize(); row++) {
				if (shape.sMap[col][row] != TileView.BLOCK_EMPTY) {
					if(shape.getXPos() + col >= 0 && shape.getXPos() + col < StickMap.MAP_X_SIZE &&
							shape.getYPos() + row >= 0 && shape.getYPos() + row < StickMap.MAP_Y_SIZE &&
							(map[shape.getXPos() + col][shape.getYPos() + row] == TileView.BLOCK_EMPTY ||
									map[shape.getXPos() + col][shape.getYPos() + row] == TileView.BLOCK_GHOST)) 
						map[shape.getXPos()+col][shape.getYPos()+row] = shape.sMap[col][row];
					else
						return false;
				}


			}	
		}
		return true;
	}
	
	
	public int getMapValue(int x, int y) {
		return map[x][y];
	}
	
	public void copyFrom(StickMap srcMap) {
		for (int x = 0; x < StickMap.MAP_X_SIZE; x++) {
			for(int y = 0; y < StickMap.MAP_Y_SIZE; y++) {
				this.map[x][y] = srcMap.getMapValue(x, y);
			}
		}
		
	}

	public int lineCheckAndClear() {
		boolean isLine = true;
		int lineCounter = 0;
		for(int y = 0; y < 20; y++) {// TODO Auto-generated method stub
			for(int x = 0; x < 10; x++){
				if(map[x][y] == TileView.BLOCK_EMPTY)
					isLine = false;
			}
			if(isLine) {
				removeLine(y);
				lineCounter++;
			}
			isLine = true;
		}

		score=score+10*lineCounter;
		if(score>highscore)
			highscore=score;
		Log.d("linecounter",""+score);
		return lineCounter;
	}
	public void dropBoxes()
	{
		StickMap stickMap=new StickMap();
		for(int x=0;x<MAP_X_SIZE;x++)
		{
			for(int y=0;y<MAP_Y_SIZE-1;y++)
			{
				if(getMapValue(x,y)!=TileView.BLOCK_EMPTY&&getMapValue(x,y+1)==TileView.BLOCK_EMPTY)
				{
					stickMap.setMapValue(x, y + 1, getMapValue(x, y));
					stickMap.setMapValue(x,y,TileView.BLOCK_EMPTY);
				}
			}
		}
		copyFrom(stickMap);
	}

	private void removeLine(int row) {
		for (int y = row; y > 0; y--) {// TODO Auto-generated method stub
			for(int x = 0; x < 10; x++){
				map[x][y] = map[x][y-1]; 
			}
		}
		for (int x = 0; x < 10; x++) {
			map[x][0] = TileView.BLOCK_EMPTY;
		}
	}

	public void setMapValue(int i, int j, int value) {
		// TODO Auto-generated method stub
		map[i][j] = value;
	}
}
