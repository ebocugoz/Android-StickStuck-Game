package com.cs310.tetris;

/**
 * Created by ackerman on 16.5.2016.
 */
public class ChalSTStick extends Stick {

    private static  int BLOCK_TYPE = TileView.BLOCK_ORANGE;
    public ChalSTStick(int x, int y,int theme) {
        super(x, y,theme);
        setBlockType();
        initStick();

    }
    private void setBlockType(){
        if(theme==0)
        {
            BLOCK_TYPE = TileView.BLOCK_ORANGE;
        }
        else
        {
            BLOCK_TYPE = TileView.BLOCK_ORANGE_IMP;
        }

    }
    private void initStick(){
        this.sMap[0][0] = 0;
        this.sMap[0][1] = 0;
        this.sMap[0][2] = 0;
        this.sMap[1][0] = 0;
        this.sMap[1][1] = BLOCK_TYPE;
        this.sMap[1][2] = 0;
        this.sMap[2][0] = BLOCK_TYPE;
        this.sMap[2][1] = 0;
        this.sMap[2][2] = BLOCK_TYPE;
    }
}

