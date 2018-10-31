package com.cs310.tetris;

        import android.app.Activity;
        import android.content.Intent;
        import android.media.MediaPlayer;
        import android.media.SoundPool;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;

        import com.cs310team.project.R;

public class MainActivity extends AppCompatActivity {
    final Activity me = this;
    public SoundPool moveSound;
    public boolean sound=false;
    public MediaPlayer mp;


    Button btnPlay, btnChallenge, btnScore;
    ImageView imgInfo, imgSetting;

    public void init(){
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnChallenge = (Button)findViewById(R.id.btnChallenge);
        btnScore = (Button)findViewById(R.id.btnScore);
        imgInfo = (ImageView)findViewById(R.id.imgInfo);
        imgSetting = (ImageView)findViewById(R.id.imgSetting);

    }

    public void btnPlay_Click() {

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt = new Intent(me, TetriBlastActivity.class);


                startActivity(intt);
            }
        });
    }

    public void btnChallenge_Click() {

        btnChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i= new Intent(me,ChallengeActivity.class);
                startActivity(i);
                // TODO: when challange button click run challange_activity
            }
        });
    }

    public void btnScore_Click() {

        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(me,HighScoreActivity.class);
                startActivity(i);
                // TODO: when clicked run highscores activity
            }
        });
    }

    public void imgInfo_Click() {

        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt = new Intent(me, InfoActivity.class);
                startActivity(intt);

            }
        });
    }

    public void imgSetting_Click() {

        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt = new Intent(me, SettingsActivity.class);


                startActivity(intt);

            }
        });
    }


    public void registerHandlers(){
        // TODO: run here actionClick methods
        btnPlay_Click();
        btnChallenge_Click();
        btnScore_Click();
        imgInfo_Click();
        imgSetting_Click();

    }
    public void createMedia()
    {
        mp = MediaPlayer.create(MainActivity.this, R.raw.move_sound);
        mp.start();
    }
    public void play(){
        mp.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(MainActivity.this, R.raw.move_sound);
        init();
        registerHandlers();

    }
}
