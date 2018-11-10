package com.cs310.erdembocugoz;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;

import com.cs310team.project.R;

public class ChallengeActivity extends AppCompatActivity {

    CheckBox challenge;
    private static final String SETTING_CHECK_BOX = "challenge";



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


    @Override
    public void onBackPressed() {
        if (challenge.isChecked()) {
            setCheckedSettingEnabled(true);

        } else {
            setCheckedSettingEnabled(false);
        }

        Intent intt = new Intent(this, MainActivity.class);
        intt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intt);
        return;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge);
        challenge = (CheckBox) findViewById(R.id.challenge);

        challenge.setChecked(isCheckedSettingEnabled());
        if (challenge.isChecked()) {
            setCheckedSettingEnabled(true);



            // i=moveSound.load(this,R.raw.move_sound,1);


        } else {
            setCheckedSettingEnabled(false);

        }



    }
}
