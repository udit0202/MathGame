package studio.idle.mathgame;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import studio.idle.mathgame.common.CommonConstants;
import studio.idle.mathgame.common.PreferenceKeys;


public class HighScoreActivity extends Activity {

    int easyScoreValue, mediumScoreValue, difficultScoreValue;
    TextView easyScoreValueView, mediumScoreValueView, difficultScoreValueView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide the action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_high_score);

        readHighScoreValues();

        easyScoreValueView = (TextView) findViewById(R.id.easyScoreValue);
        mediumScoreValueView = (TextView) findViewById(R.id.mediumScoreValue);
        difficultScoreValueView = (TextView) findViewById(R.id.difficultScoreValue);

        easyScoreValueView.setText(String.valueOf(easyScoreValue));
        mediumScoreValueView.setText(String.valueOf(mediumScoreValue));
        difficultScoreValueView.setText(String.valueOf(difficultScoreValue));
    }

    private void readHighScoreValues() {
        SharedPreferences settings = getSharedPreferences(CommonConstants.PREF_FILE_NAME, 0);
        easyScoreValue = settings.getInt(PreferenceKeys.easyScoreKey,0);
        mediumScoreValue = settings.getInt(PreferenceKeys.mediumScoreKey,0);
        difficultScoreValue = settings.getInt(PreferenceKeys.difficultScoreKey,0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_high_score, menu);
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
}
