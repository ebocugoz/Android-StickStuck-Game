package com.cs310.erdembocugoz;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.media.SoundPool;

import com.cs310team.project.R;

public class MainMap extends TileView{
	final View me=this;
private  SoundPool sounds;
	private int soundi;
	boolean challenge;



	public static final int	L_TYPE = 0;
	public static final int J_TYPE = 1;
	public static final int	T_TYPE = 2;
	public static final int	Z_TYPE = 3;
	public static final int	S_TYPE = 4;
	public static final int	O_TYPE = 5;
	public static final int	I_TYPE = 6;
	private StickMap mStickMap;
	private MainActivity setting = new MainActivity();
	public int tempCount;
	public static SharedPreferences prefs,prefs2;
	private int game_speed;
	private int theme;


	public boolean soundCheck=true;
	public static String saveScore ;
	public MediaPlayer mp;

	public  boolean isSoundCheck() {

		return soundCheck;
	}

	public  void setSoundCheck(boolean soundCheck) {
		this.soundCheck = soundCheck;
	}

	private RefreshHandler mRedrawHandler = new RefreshHandler();


	private long mMoveDelay;
	
	public int mGameState = PAUSE;
	//private boolean noShape = true;
	private Stick curStick;
	MainActivity mMainActivity;
	
	/**
	 * Two dimensional arrays hold the tetris map
	 * - mapCur - hold the current map
	 * - mapOld - hold the map without current Stick
	 * - mapLast - hold the map before last move of Stick
	 */
	private static StickMap mapCur;
	public static StickMap mapOld;
	private static StickMap mapLast;
	  
	private static int[] randArr = {-1,-1};
	/**
	 *  This parameter is the flag that indicate that Action_Down event 
	 *  was occur and Stick was moved left or right
	 */
	private boolean wasMoved;
	private boolean pausePressed = false;
	public boolean isEnd = false;
	
	/**
	 * Initial coordinate of the Action_Down event 
	 */
	private int xInitRaw;
	private int yInitRaw;
	private int yInitDrop;
	private long initTime;
	private static final long deltaTh = 300;//threshold time for drop
	/**
	 * X move sensitivity
	 */
	private static final int xMoveSens = 20;
	
	/**
	 * Rotate sensitivity
	 */
	private static final int rotateSens = 10; 
	/**
	 * Drop down sensitivity
	 */
	private static final int dropSensativity = 30;//~30*3.5
	public static final int READY = 1;
	public static final int PAUSE = 0;

	
	private class RefreshHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {

			if(mGameState == READY) {
				//checkHang();

				if(msg.what == 1) {//TODO change to final Name
					mapCur.copyFrom(mapLast);
					int i = mapCur.lineCheckAndClear();
					//Log.d(TAG, "Cleared " + Integer.toString(i) + " lines!");
					mapOld.copyFrom(mapCur);
					curStick = newStick(getRandomFromArr(), 4, 0,theme);//TODO check this

					mMoveDelay=120;
					if(game_speed==0)
					{
						mMoveDelay=250;
					}
					else if(game_speed==1){
						mMoveDelay=180;
					}
					else if(game_speed==2){
						mMoveDelay=120;
					}
					else if(game_speed==3){
						mMoveDelay=80;
					}
					else if(game_speed==4){
						mMoveDelay=12;
					}
					Log.d(TAG,"Next: " + Integer.toString(randArr[1]));
					tempCount++;
					sounds.play(soundi,1.0f,1.0f,0,0,1.5f);
					if(!mapCur.putStickOnMap(curStick)) {
						Log.d(TAG, "Game Over!");
						isEnd=true;
						prefs.edit().putInt(saveScore, mStickMap.highscore);

						prefs.edit().commit();
						((TetriBlastActivity)getContext()).goToMenu();
						//initNewGame();
					}
					mRedrawHandler.sleep(mMoveDelay);
				}
				else {
					sounds.play(soundi,1.0f,1.0f,0,0,1.5f);
					clearTiles();
					updateMap();
					mapCur.resetMap();
					mapCur.copyFrom(mapOld);
					gameMove();//TODO insert this function to the Stick
					MainMap.this.invalidate();
				}

			}
			else{
				mRedrawHandler.sleep(mMoveDelay);
				MainMap.this.update();
			}
			//mRedrawHandler.sleep(mMoveDelay);
			//MainMap.this.update();
			//mGameState = true;
		}

		public void sleep(long delayMillis) {
			this.removeMessages(0);
			sendEmptyMessageDelayed(0, delayMillis);
			//sendMessageDelayed(obtainMessage(0), delayMillis);
		}
	};




	/**
	 * Constructs a MainMap View based on inflation from XML
	 * 
	 * @param context
	 * @param attrs
	 */

	public MainMap(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.d(TAG, "MainMap constructor");
		sounds= new SoundPool(10, AudioManager.STREAM_MUSIC,0);
		soundi = sounds.load(context,R.raw.move_sound,1);
		initMainMap();
		prefs = context.getSharedPreferences("com.cs310team.tetris", context.MODE_PRIVATE);
		prefs2=context.getSharedPreferences("com.cs310team.tetris", context.MODE_PRIVATE);
				game_speed=   PreferenceManager.getDefaultSharedPreferences(context)
				.getInt("hard_setting", 0);
		theme = PreferenceManager.getDefaultSharedPreferences(context)
				.getInt("theme", 0);
		challenge =PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean("challenge", false);
		String spackage = "com.cs310team.tetris";

		mStickMap.highscore = prefs.getInt(saveScore,0);
	}
	
	

	public MainMap(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Log.d(TAG, "MainMap constructor defStyle");
		sounds= new SoundPool(10, AudioManager.STREAM_MUSIC,0);
		soundi = sounds.load(context, R.raw.move_sound, 1);
		initMainMap();
	}

	/**
	 * Initialize MainMap Tail icons from drawable 
	 *
	 */
	private void initMainMap() {
		setFocusable(true);
		mapCur = new StickMap();
		mapOld = new StickMap();
		mapLast = new StickMap();
		resetTiles(NUM_OF_TILES + 10);//TODO fix this
	}


	public void initNewGame() {
		//mTileList.clear();
		mapCur.score=0;
		Log.d(TAG, "game init");
		mMoveDelay = 90;//delay [ms]
		mapCur.resetMap();
		mapOld.resetMap();
		mapLast.resetMap();
		//noShape = true;
		tempCount = 0;
		mRedrawHandler.sendEmptyMessage(1);//TODO change to final name
	}
	
	private Stick newStick(int type, int x, int y,int thm) {

		if(!challenge) {
			switch (type) {
				case L_TYPE:
					return new LStick(x, y, thm);
				case J_TYPE:
					return new JStick(x, y, thm);
				case T_TYPE:
					return new TStick(x, y, thm);
				case Z_TYPE:
					return new ZStick(x, y, thm);
				case S_TYPE:
					return new SStick(x, y, thm);
				case O_TYPE:
					return new OStick(x, y, thm);
				case I_TYPE:
					return new IStick(x, y, thm);
				default:
					return new LStick(x, y, thm);

			}
		}
		else
		{
			switch (type) {
				case L_TYPE:
					return new ChalBTStick(x, y, thm);
				case J_TYPE:
					return new ChalDotStick(x, y, thm);
				case T_TYPE:
					return new ChalXStick(x, y, thm);
				case Z_TYPE:
					return new ChalMStick(x, y, thm);
				case S_TYPE:
					return new ChalOStick(x, y, thm);
				case O_TYPE:
					return new ChalSTStick(x, y, thm);
				case I_TYPE:
					return new ChalUStick(x, y, thm);
				default:
					return new ChalBTStick(x, y, thm);

			}

		}
	}
	

	private int[] coordArrayListToArray(StickMap map) {
		int[] rawArray = new int[StickMap.MAP_X_SIZE*StickMap.MAP_Y_SIZE];
		for (int row = 0; row < StickMap.MAP_Y_SIZE; row++) {
			for (int col = 0; col < StickMap.MAP_X_SIZE; col++) {
				rawArray[row*StickMap.MAP_X_SIZE+col] = map.getMapValue(col, row);
			}
		}
		return rawArray;
	}


	public Bundle saveState() {
		Bundle map = new Bundle();
		map.putIntArray("mapCur", coordArrayListToArray(mapCur));
		map.putIntArray("mapLast", coordArrayListToArray(mapLast));
		map.putIntArray("mapOld", coordArrayListToArray(mapOld));
		map.putLong("mMoveDelay", Long.valueOf(mMoveDelay));
		return map;
	}


	private StickMap coordArrayToArrayList(int[] rawArray) {
		StickMap tMap = new StickMap();//TODO change to get map from argument
		int arrSize = rawArray.length;
		for (int i = 0; i < arrSize; i++) {
			tMap.setMapValue(i%StickMap.MAP_X_SIZE,(i/StickMap.MAP_Y_SIZE),rawArray[i]);
		}
		return tMap;
	}


	public void restoreState(Bundle icicle) {
		setMode(PAUSE);
		mapCur = coordArrayToArrayList(icicle.getIntArray("mapCur"));
		mapLast = coordArrayToArrayList(icicle.getIntArray("mapLast"));
		mapOld = coordArrayToArrayList(icicle.getIntArray("mapOld"));
		mMoveDelay = icicle.getLong("mMoveDelay");
	}
	    

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		//This prevents touchscreen events from flooding the main thread
		synchronized (event)
		{
			try
			{
				//Waits 16ms.
				event.wait(16);

				//when user touches the screen
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					initTime = SystemClock.uptimeMillis();
					xInitRaw = (int) Math.floor(event.getRawX());
					yInitRaw = (int) Math.floor(event.getRawY());
					yInitDrop = yInitRaw;
					wasMoved = false;
					if(xInitRaw > 360 && xInitRaw < 450 && yInitRaw > 560 && yInitRaw < 600) {
						pausePressed = true;
						if(mGameState == READY)
							mGameState = PAUSE;
						else
							mGameState = READY;
					}

					//mMoveDelay=12;

				}

				if(event.getAction() == MotionEvent.ACTION_MOVE && mGameState == READY && !pausePressed) {
					int xCurRaw = (int) Math.floor(event.getRawX());
					int yCurRaw = (int)Math.floor(event.getRawY());
					if ((xInitRaw - xCurRaw) > xMoveSens && Math.abs(yInitRaw - yCurRaw) < dropSensativity) {
						int q = (xInitRaw - xCurRaw)/xMoveSens;
						if(q > 1)
							Log.d(TAG, "move left q = " + Integer.toString(q));
						mMoveDelay=120;
						wasMoved = true;
						xInitRaw = xCurRaw;
						mapCur.resetMap();
						mapCur.copyFrom(mapOld);
						for (int i = 0; i < q; i++) { 
							if (curStick.moveLeft(mapCur) &&
									!curStick.isColusionY(curStick.getYPos()+1, curStick.getXPos(), curStick.sMap, mapCur, false)) {
								if (mRedrawHandler.hasMessages(1) == true) {//TODO change to final Name
									mRedrawHandler.removeMessages(1);
									mRedrawHandler.sendEmptyMessageDelayed(0, 400);//TODO convert to parameter and change to final Name
								}
							}
						//Log.d("position stick",String.valueOf(curStick.getYPos()));
						mapCur.putStickOnMap(curStick);
						}
						update();
					}
					else if((xCurRaw - xInitRaw) > xMoveSens && Math.abs(yInitRaw - yCurRaw) < dropSensativity) {
						int q = (xCurRaw - xInitRaw)/xMoveSens;
						if(q > 1)
							Log.d(TAG, "move right q = " + Integer.toString(q));
						mMoveDelay=120;
						wasMoved = true;
						xInitRaw = xCurRaw;
						mapCur.resetMap();
						mapCur.copyFrom(mapOld);
						for (int i = 0; i < q; i++) {
							if(curStick.moveRight(mapCur) &&
									!curStick.isColusionY(curStick.getYPos()+1, curStick.getXPos(), curStick.sMap, mapCur, false)) {
								if (mRedrawHandler.hasMessages(1) == true) {//TODO change to final Name
									mRedrawHandler.removeMessages(1);
									mRedrawHandler.sendEmptyMessageDelayed(0, 400);//TODO convert to parameter and change to final Name
								}
							}
							mapCur.putStickOnMap(curStick);
						}
						update();
						
					}
					if ((yCurRaw - yInitRaw) > xMoveSens) {
						long timeDelta = Math.abs(initTime - SystemClock.uptimeMillis());
						if(timeDelta > deltaTh) {
							yInitDrop = yCurRaw;
							initTime = SystemClock.uptimeMillis();
						}
						wasMoved = true;
						yInitRaw = yCurRaw;
						//yInitDrop = yInitRaw;
						mapCur.resetMap();
						mapCur.copyFrom(mapOld);
						curStick.moveDown(mapCur);
						mapCur.putStickOnMap(curStick);
						update();

					}
				}
					
				//when screen is released
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					long timeDelta = Math.abs(initTime - SystemClock.uptimeMillis());
					if(mGameState == READY && !pausePressed){
						int yCurRaw = (int) Math.floor(event.getRawY());
						/*if(yCurRaw - yInitDrop > dropSensativity && timeDelta < deltaTh) {
							mapCur.resetMap();
							mapCur.copyFrom(mapOld);

							mapCur.putStickOnMap(curStick);
							update();
							mRedrawHandler.removeMessages(0);
							mRedrawHandler.sendEmptyMessage(1);
						}*/
						//Rotate Stick (release on same x pos)
						if (!wasMoved && Math.abs(yCurRaw - yInitRaw) < rotateSens) {
							mapCur.resetMap();
							mapCur.copyFrom(mapOld);
							sounds.play(soundi,1.0f,1.0f,0,0,1.5f);
							if(curStick!=null)
							curStick.rotateStick(mapCur);
							mapCur.putStickOnMap(curStick);
							update();
						}
					}
					else
						pausePressed = false;

				}
				//}


			}
			catch (InterruptedException e)
			{
				return true;
			}
		}
		return true;
	}
	
	private int getRandomFromArr() {
		if (randArr[1] == -1)
			randArr[1] = (int)Math.floor(Math.random()*7);
		randArr[0] = randArr[1];//shift to next
		randArr[1] = (int)Math.floor(Math.random()*7);//next
		mCurNext = randArr[1];
		return randArr[0];
	}
	
	private void gameMove() {
		if(curStick.moveDown(mapCur)){
			Log.d("position",String.valueOf(curStick.getYPos()));
			mapCur.putStickOnMap(curStick);
			mRedrawHandler.sleep(mMoveDelay);
		}
		else {//TODO convert to parametr and convert to final name
			mRedrawHandler.sendEmptyMessageDelayed(1, 1000);
		}
	}


	public void update() {
		if(mGameState == READY) {
			clearTiles();
			updateMap();
			MainMap.this.invalidate();		
		}

	}
		
	private void updateMap() {

		mapLast.copyFrom(mapCur);
		for(int col = 0; col < StickMap.MAP_X_SIZE; col++){
			for(int row = 0; row < StickMap.MAP_Y_SIZE; row++) {
				setTile(mapLast.getMapValue(col, row), col, row);
			}
		}

		
	}
	public void checkHang()
	{
		for(int x=0;x<StickMap.MAP_X_SIZE-1;x++)
		{
			for(int y=1;y<StickMap.MAP_Y_SIZE-1;y++)
			{
				if(y+1<StickMap.MAP_Y_SIZE) {
					if (mapCur.getMapValue(x, y) != TileView.BLOCK_EMPTY && mapCur.getMapValue(x, y + 1) == TileView.BLOCK_EMPTY) {
						curStick.moveDown(mapCur);
					}
				}
			}
		}

	}

	public void setMode(int state) {
		// TODO Auto-generated method stub
		Log.d(TAG,state+" state");
		mGameState = state;
	}


	    
}
