package com.cs310.erdembocugoz;

public class SStick extends Stick {
	private static  int BLOCK_TYPE = TileView.BLOCK_GREEN;
	public SStick(int x, int y,int theme) {
		super(x, y,theme);
		setBlockType();
		initStick();

	}
	private void setBlockType(){
		if(theme==0)
		{
			BLOCK_TYPE = TileView.BLOCK_GREEN;
		}
		else
		{
			BLOCK_TYPE = TileView.BLOCK_GREEN_IMP;
		}

	}

	private void initStick() {
		this.sMap[0][0] = 0;
		this.sMap[0][1] = BLOCK_TYPE;
		this.sMap[0][2] = 0;
		this.sMap[1][0] = BLOCK_TYPE;
		this.sMap[1][1] = BLOCK_TYPE;
		this.sMap[1][2] = 0;
		this.sMap[2][0] = BLOCK_TYPE;
		this.sMap[2][1] = 0;
		this.sMap[2][2] = 0;
	}

}