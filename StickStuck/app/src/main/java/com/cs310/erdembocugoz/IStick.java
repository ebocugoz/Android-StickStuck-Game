package com.cs310.erdembocugoz;

public class IStick extends Stick {
	private static  int BLOCK_TYPE = TileView.BLOCK_BLUE;

	public IStick(int x, int y,int theme) {
		super(x, y,theme);
		setBlockType();
		initStick();

	}
	private void setBlockType(){
		if(theme==0)
		{
			BLOCK_TYPE = TileView.BLOCK_BLUE;
		}
		else
		{
			BLOCK_TYPE = TileView.BLOCK_BLUE_IMP;
		}

	}


	private void initStick() {

		this.sMap[0][0] = 0;
		this.sMap[0][1] = 0;
		this.sMap[0][2] = 0;
		this.sMap[1][0] = BLOCK_TYPE;
		this.sMap[1][1] = BLOCK_TYPE;
		this.sMap[1][2] = BLOCK_TYPE;
		this.sMap[2][0] = 0;
		this.sMap[2][1] = 0;
		this.sMap[2][2] = 0;
	}
	

	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return SIZE;
	}
}
