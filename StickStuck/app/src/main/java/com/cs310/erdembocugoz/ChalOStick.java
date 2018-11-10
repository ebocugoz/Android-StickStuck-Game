package com.cs310.erdembocugoz;

/**
 * Created by ackerman on 16.5.2016.
 */
public class ChalOStick extends Stick {

    private static  int BLOCK_TYPE = TileView.BLOCK_LIGHBLUE;
    public ChalOStick(int x, int y,int theme) {
        super(x, y,theme);
        setBlockType();
        initStick();

    }
    private void setBlockType(){
        if(theme==0)
        {
            BLOCK_TYPE = TileView.BLOCK_LIGHBLUE;
        }
        else
        {
            BLOCK_TYPE = TileView.BLOCK_LIGHTBLUE_IMP;
        }

    }

    private void initStick(){
        this.sMap[0][0] = BLOCK_TYPE;
        this.sMap[0][1] = BLOCK_TYPE;
        this.sMap[0][2] = BLOCK_TYPE;
        this.sMap[1][0] = 0;
        this.sMap[1][1] = BLOCK_TYPE;
        this.sMap[1][2] = BLOCK_TYPE;
        this.sMap[2][0] = BLOCK_TYPE;
        this.sMap[2][1] = BLOCK_TYPE;
        this.sMap[2][2] = BLOCK_TYPE;
    }
}