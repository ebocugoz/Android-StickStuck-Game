package com.cs310.tetris;

public class OStick extends Stick {
	private static int BLOCK_TYPE = TileView.BLOCK_YELLOW;
	public OStick(int x, int y,int theme) {
		super(x, y,theme);
		setBlockType();
		initStick();

	}
	private void setBlockType(){
		if(theme==0)
		{
			BLOCK_TYPE = TileView.BLOCK_YELLOW;
		}
		else
		{
			BLOCK_TYPE = TileView.BLOCK_YELLOW_IMP;
		}

	}
	private void initStick() {
		this.sMap[0][0] = BLOCK_TYPE;
		this.sMap[0][1] = BLOCK_TYPE;
		this.sMap[0][2] = 0;
		this.sMap[1][0] = BLOCK_TYPE;
		this.sMap[1][1] = BLOCK_TYPE;
		this.sMap[1][2] = 0;
		this.sMap[2][0] = 0;
		this.sMap[2][1] = 0;
		this.sMap[2][2] = 0;
	}

	@Override
	public boolean rotateStick(StickMap map) {
		return false;
	}
}
