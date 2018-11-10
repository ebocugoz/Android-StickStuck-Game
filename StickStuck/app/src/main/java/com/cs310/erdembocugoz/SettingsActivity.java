package com.cs310.erdembocugoz;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.cs310team.project.R;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class SettingsActivity extends AppCompatActivity {


    CheckBox soundChecker;
    Spinner hard,themes;
    String text;
    private static final String SETTING_HARD = "hard_setting";

    public  static boolean sound=false;
    private static final String SETTING_CHECK_BOX = "checkbox_setting";



    private void setCheckedSettingEnabled(boolean enabled) {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean(SETTING_CHECK_BOX, enabled)
                .apply();
    }

    public boolean isCheckedSettingEnabled() {
        return PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(SETTING_CHECK_BOX, false);
    }
    private void setGameSpeed(int enabled) {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putInt("hard_setting", enabled)
                .apply();
    }

    public int getGameSpeed() {
        return PreferenceManager.getDefaultSharedPreferences(this)
                .getInt("hard_setting", 0);
    }

    private void setThemes(int enabled) {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putInt("theme", enabled)
                .apply();
    }

    public int getThemes() {
        return PreferenceManager.getDefaultSharedPreferences(this)
                .getInt("theme", 0);
    }

    @Override
    public void onBackPressed() {
        if (soundChecker.isChecked()) {
            setCheckedSettingEnabled(true);
            sound=true;
        } else {
            setCheckedSettingEnabled(false);
            sound=false;
        }
        setGameSpeed(hard.getSelectedItemPosition());
        setThemes(themes.getSelectedItemPosition());
        Intent intt = new Intent(this, MainActivity.class);
        intt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intt);
        return;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setings);
        soundChecker = (CheckBox) findViewById(R.id.sound);
        hard = (Spinner)findViewById(R.id.speed);
        themes = (Spinner)findViewById(R.id.theme);
        ArrayAdapter adapter_theme= ArrayAdapter.createFromResource(this, R.array.theme, android.R.layout.simple_spinner_item);

        ArrayAdapter adapter= ArrayAdapter.createFromResource(this, R.array.game_speed, android.R.layout.simple_spinner_item);
        hard.setAdapter(adapter);
        themes.setAdapter(adapter_theme);
        if(getGameSpeed()==0||getGameSpeed()==1||getGameSpeed()==2||getGameSpeed()==3||getGameSpeed()==4) {
            hard.setSelection(getGameSpeed());
        }
        else {
            hard.setSelection(0);
        }

        if(getThemes()==0||getThemes()==1) {
            themes.setSelection(getThemes());
        }
        else {
            themes.setSelection(0);
        }


        soundChecker.setChecked(isCheckedSettingEnabled());
        if (soundChecker.isChecked()) {
            setCheckedSettingEnabled(true);

            sound = true;

            // i=moveSound.load(this,R.raw.move_sound,1);


        } else {
            setCheckedSettingEnabled(false);
            sound = false;
        }


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
