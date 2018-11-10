package com.cs310.erdembocugoz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs310team.project.R;

/**
 * Created by erdembocugoz on 08/05/16.
 */
public class GameOverActivity extends Activity {

    Button yesbutton,nobutton;
    final Activity me=this;
    StickMap mStickMap;
    public void init(){
        yesbutton = (Button)findViewById(R.id.yes);
        nobutton = (Button)findViewById(R.id.no);

    }
    public void registerHandlers(){
        // TODO: run here actionClick methods
    button_no();
        button_yes();
    }
    @Override
    public void onBackPressed() {
        Intent intt = new Intent(me, MainActivity.class);
        intt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(intt);
        return;

    }

    public void button_yes() {

        yesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt = new Intent(me, TetriBlastActivity.class);
                intt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intt);
            }
        });
    }
    public void button_no() {

        nobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt = new Intent(me, MainActivity.class);
                intt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intt);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        init();
        registerHandlers();
        TextView score = (TextView) findViewById(R.id.over_score);
        score.setText("Score"+String.valueOf(mStickMap.score));


    }
}
