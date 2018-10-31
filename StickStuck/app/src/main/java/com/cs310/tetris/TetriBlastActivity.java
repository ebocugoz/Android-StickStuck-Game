package com.cs310.tetris;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

import com.cs310team.project.R;

public class TetriBlastActivity extends Activity {


	private MainMap mMainMapView;
    private static StickMap mStickMap;
    private int theme;

	public static final String TAG = "TetrisBlast";
    
	private static String ICICLE_KEY = "tetris-blast-view";
    Button btnPause;
    //score
    private SimpleCursorAdapter adapter;
    private String higscoreString;
    public static SharedPreferences prefs ;



    public void btn_Pause() {

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "pause button");
                if (mMainMapView.mGameState == mMainMapView.READY) {

                    onPause();
                } else {
                    Log.d(TAG, "cont button");
                    mMainMapView.mGameState = mMainMapView.READY;
                }
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Pause the game along with the activity
        mMainMapView.setMode(MainMap.PAUSE);
        prefs.edit().putInt(higscoreString, StickMap.highscore).commit();

    }

    @Override
    protected void  onStop() {
        super.onPause();
        // Pause the game along with the activity
        mMainMapView.setMode(MainMap.PAUSE);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //Store the game state
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putBundle(ICICLE_KEY, mMainMapView.saveState());
    }
    public void goToMenu()
    {

        Intent intt = new Intent(this, GameOverActivity.class);
        intt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(intt);
    }
    @Override
    public void onBackPressed() {

            mMainMapView.saveState();
        super.onBackPressed();
            return;

    }


    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnPause = (Button)findViewById(R.id.Pausebut);

        //SCORES
        prefs= getPreferences(MODE_PRIVATE);
        higscoreString=getString(R.string.highscore);
        StickMap.highscore = prefs.getInt(higscoreString, 0);

        theme = PreferenceManager.getDefaultSharedPreferences(this)
                .getInt("theme", 0);

        Log.d(TAG, "Create main layout");
     ;
        Log.d("score", "" + mStickMap.score);
        if(theme==0) {
            getWindow().setBackgroundDrawableResource(R.drawable.tetris_bg);//Draw background
        }
        else{
            getWindow().setBackgroundDrawableResource(R.drawable.tetris_bg_imp);//Draw background
        }
        mMainMapView = (MainMap) findViewById(R.id.tetris);
        mMainMapView.initNewGame();
        Log.d("score", "" + mStickMap.score);


        btn_Pause();
        //TextView myText = (TextView) findViewById(R.id.txt);
        if(mMainMapView.isEnd==true)
        {

            mMainMapView.isEnd=false;
            goToMenu();

        }
        
        if (savedInstanceState == null) {
            // We were just launched -- set up a new game
        	mMainMapView.setMode(MainMap.READY);
        } else {
            // We are being restored
           Bundle map = savedInstanceState.getBundle(ICICLE_KEY);
            if (map != null) {
            	mMainMapView.restoreState(map);
                onRestoreInstanceState(savedInstanceState);
            } else {
            	mMainMapView.setMode(MainMap.PAUSE);
            }
        }
    }

}