package com.cs310.tetris;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public abstract class Stick {
	public static final int SIZE = 3;
	public static boolean ghostEnabled = false;
	public int[][] sMap;

	private Point pos;
	protected int theme;





	public Stick(int x, int y,int theme) {
		sMap = new int[SIZE][SIZE];
		initStick(SIZE);
		this.theme=theme;
		pos = new Point(x,y);


	}

	private void initStick(int StickSize) {
		for(int col = 0; col < StickSize; col++) {
			for(int row = 0; row < StickSize; row++) {
				sMap[col][row] = TileView.BLOCK_EMPTY;
			}
		}

	}




	public boolean rotateStick(StickMap map) {
		int[][] temp = new int[SIZE][SIZE];
		for(int col = 0; col < SIZE; col++){
			for(int row = 0; row < SIZE; row++) {
				temp[col][row] = sMap[row][2-col];
			}
		}

		if(!isColusionX(this.pos.x, temp, map) && !isColusionY(this.pos.y, this.pos.x, temp, map, false)) {
			sMap = temp;

			return true;
		}
		else if(!isColusionX(this.pos.x-1, temp, map) && !isColusionY(this.pos.y, this.pos.x-1, temp, map, false)) {
			this.pos.x -=1;
			sMap = temp;

			return true;
		}
		else if(!isColusionX(this.pos.x+1, temp, map) && !isColusionY(this.pos.y, this.pos.x+1, temp, map, false)) {
			this.pos.x +=1;
			sMap = temp;
			return true;
		}


		return false;
	}

	/**
	 * @return the pos
	 */
	public Point getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public boolean setPos(int x, int y, StickMap map) {
		if(x >= 0 && x < StickMap.MAP_X_SIZE) {
			for(int col = 0; col < this.getSize(); col++){
				for(int row = 0; row < this.getSize(); row++) {
					if (sMap[col][row] != TileView.BLOCK_EMPTY) {
						if (x + col >= StickMap.MAP_X_SIZE || x + col < 0 ||
								y + row >= StickMap.MAP_Y_SIZE ||
								map.getMapValue(x + col, y + row) != TileView.BLOCK_EMPTY)
							return false;
					}
				}
			}
		}
		this.pos.x = x;
		this.pos.y = y;
		return true;
	}

	protected boolean isColusionY(int newY, int newX, int[][] tMap,StickMap map, boolean isGhost) {
		// TODO Auto-generated method stub
		if(newY < StickMap.MAP_Y_SIZE) {
			for(int col = 0; col < this.getSize(); col++){
				for(int row = 0; row < this.getSize(); row++) {
					if (tMap[col][row] != TileView.BLOCK_EMPTY) {

							if ((newX + col) >= 0 && (newX + col) < StickMap.MAP_X_SIZE) {
								if ((newY + row) >= StickMap.MAP_Y_SIZE ||
										map.getMapValue(newX + col,row+newY) != TileView.BLOCK_EMPTY) {
									Log.d("col", "collision" );
									return true;
								}
							}

					}
				}
			}
		}
		else
			return false;
		//if no collisions
		return false;
	}
	/**
	 * This function move Stick down by 1
	 * @param map - to check if possible
	 * @return true is success else false
	 */

	public boolean moveDown(StickMap map) {
		if(!isColusionY(this.pos.y+1, this.pos.x, sMap, map, false)) {
			this.pos.y++;
			return true;
		}
		else
			return false;
	}
	public boolean dropStick(Stick mstick,StickMap map)
	{
		if(map.getMapValue(mstick.getXPos(),mstick.getYPos()) == TileView.BLOCK_EMPTY)
		{
			return true;
		}
		else
			return false;
	}

	protected boolean isColusionX(int newX, int[][] tMap,StickMap map) {
		// TODO Auto-generated method stub
		if(newX >= -1 && newX < StickMap.MAP_X_SIZE) {
			for(int col = 0; col < this.getSize(); col++){
				for(int row = 0; row < this.getSize(); row++) {
					if (tMap[col][row] != TileView.BLOCK_EMPTY) {
						if (newX + col >= StickMap.MAP_X_SIZE || newX + col < 0 ||
								map.getMapValue(newX + col, this.pos.y + row) != TileView.BLOCK_EMPTY) {
							Log.d("xcol", "x colis");
							return true;
						}
					}
				}
			}
		}
		else
			return true;
		//if no collisions 
		return false;
	}


	public boolean moveLeft(StickMap map) {
		if(!isColusionX(this.pos.x-1, sMap, map)) {
			this.pos.x--;

			return true;
		}
		return false;
	}

	public boolean moveRight(StickMap map) {
		if(!isColusionX(this.pos.x+1, sMap, map)) {
			this.pos.x++;

			return true;
		}
		return false;
	}
	public void drop(StickMap map) {

			for (int y = 0; y < StickMap.MAP_Y_SIZE && !isColusionY(y, this.pos.x, sMap, map, false); y++)
				this.pos.y = y;
	}
	//public void drop(StickMap map) {

	/**
	 * @return the x position
	 */
	public int getXPos() {
		return pos.x;
	}

	/**
	 * @return the y position
	 */
	public int getYPos() {
		return pos.y;
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return SIZE;
	}

	protected void copyStickMap(int [][] srcMap, int[][] destMap, int size) {
		for(int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (srcMap[x][y] != TileView.BLOCK_EMPTY)
					destMap[x][y] = TileView.BLOCK_GHOST;
			}
		}
	}



}
