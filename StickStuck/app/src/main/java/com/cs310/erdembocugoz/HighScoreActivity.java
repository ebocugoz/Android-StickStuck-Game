package com.cs310.erdembocugoz;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cs310team.project.R;

public class HighScoreActivity extends AppCompatActivity {
String higscorer;
    StickMap mStickMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
setContentView(R.layout.higscore);
        TextView higscore = (TextView)findViewById(R.id.highestscore);
        SharedPreferences prefs = getSharedPreferences("com.cs310team.tetris", this.MODE_PRIVATE);
        higscorer = String.valueOf(mStickMap.highscore);
        higscore.setText(higscorer);


    }
}
