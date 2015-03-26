package studio.idle.mathgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import studio.idle.mathgame.common.CommonConstants;
import studio.idle.mathgame.common.PreferenceKeys;


public class MainScreenActivity extends Activity {

    private boolean isSoundOn;
    private ImageButton soundButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide the action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        //fetching sound settings
        SharedPreferences settings = getSharedPreferences(CommonConstants.PREF_FILE_NAME, 0);
        isSoundOn = settings.getBoolean(PreferenceKeys.isSoundOnKey, true);
        setSoundButtonImage(isSoundOn);
    }

    public void setSoundButtonImage(boolean isSoundOn) {
        soundButton = (ImageButton) findViewById(R.id.sound_button);
        if (isSoundOn) {
            soundButton.setBackgroundResource(R.drawable.sound_on);
        } else {
            soundButton.setBackgroundResource(R.drawable.sound_off);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openLevelActivity(View view) {
        Intent intent = new Intent(this, DifficultyLevelActivity.class);
        startActivity(intent);
    }

    public void openHighScoreActivity(View view) {
        Intent intent = new Intent(this, HighScoreActivity.class);
        startActivity(intent);
    }

    public void changeSoundSetting(View view) {
        SharedPreferences settings = getSharedPreferences(CommonConstants.PREF_FILE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        isSoundOn = settings.getBoolean(PreferenceKeys.isSoundOnKey, true);
        isSoundOn = !isSoundOn;
        setSoundButtonImage(isSoundOn);

        editor.putBoolean(PreferenceKeys.isSoundOnKey, isSoundOn);
        // Commit the edits
        editor.commit();
    }

    public void closeApplication(View view) {
        finish();
    }
}
