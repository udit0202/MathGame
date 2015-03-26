package studio.idle.mathgame;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.provider.Telephony;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import studio.idle.mathgame.common.CommonConstants;
import studio.idle.mathgame.common.PreferenceKeys;


public class GameActivity extends ActionBarActivity {

    int gameLevel, answer, previousHighScore, score = 0;
    private float mProgressStatus = 0;
    TextView answerView, scoreView, gameOverView;
    private RelativeLayout mainLayout;
    boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mainLayout = (RelativeLayout) findViewById(R.id.layout);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameLevel = extras.getInt("level");
            TextView temp = (TextView) findViewById(R.id.temp_text_view);
            temp.setText(CommonConstants.difficultyLevels.get(gameLevel).name());
        }
        setFormula();
        Button tempButton = (Button) findViewById(R.id.temp_button);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAnswer()) {
                    clearAnswerView();
                    resetProgressBar();
                    setFormula();
                }
            }
        });

        answerView = (TextView) findViewById(R.id.answerView);
        scoreView = (TextView) findViewById(R.id.scoreView);
        gameOverView = (TextView) findViewById(R.id.game_over_view);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        final Handler mHandler = new Handler();

        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100 && !stopThread) {

                    mProgressStatus += .0003;
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress((int) mProgressStatus);
                        }
                    });
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeCompleted();
                    }
                });
            }

        }).start();

        fetchCurrentHighScore(gameLevel);
    }

    private void fetchCurrentHighScore(int gameLevel) {
        SharedPreferences settings = getSharedPreferences(CommonConstants.PREF_FILE_NAME, 0);
        switch (gameLevel) {
            case 0:
                previousHighScore = settings.getInt(PreferenceKeys.easyScoreKey, 0);
                break;
            case 1:
                previousHighScore = settings.getInt(PreferenceKeys.mediumScoreKey, 0);
                break;
            case 2:
                previousHighScore = settings.getInt(PreferenceKeys.difficultScoreKey, 0);
        }

    }

    public void timeCompleted() {
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.layout);
        mainLayout.setAlpha(0.1f);
        gameOverView.setAlpha(1.0f);
    }

    public void wrongAnswer() {
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.layout);
        mainLayout.setAlpha(0.1f);
        gameOverView.setAlpha(1.0f);
    }

    private void clearAnswerView() {
        answerView.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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

    @Override
    public void onBackPressed() {
        /*Intent intent = new Intent(this,MainScreenActivity.class);
        startActivity(intent);*/
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // Show Alert DialogFragment
        alertDialogBuilder.setTitle("Sure ?");
        alertDialogBuilder.setMessage(" Are you sure you want to exit ? ");
        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }

    @Override
    public void onDestroy() {
        stopThread = true;
        super.onDestroy();
    }

    public void setFormula() {

        switch (gameLevel) {
            case 0:
                fetchFormula(CommonConstants.minEasyValue, CommonConstants.maxEasyValue, CommonConstants.symbolListEasy);
                break;
            case 1:
                fetchFormula(CommonConstants.minMediumValue, CommonConstants.maxMediumValue, CommonConstants.symbolListMedium);
                break;
            case 2:
                fetchFormula(CommonConstants.minHardValue, CommonConstants.maxHardValue, CommonConstants.symbolListDifficult);
        }
    }

    private void fetchFormula(int minValue, int maxValue, List<String> symbolList) {
        Random rand = new Random();
        int firstNumber = rand.nextInt((maxValue - minValue) + 1) + minValue;
        int secondNumber = rand.nextInt((maxValue - minValue) + 1)
                + minValue;
        String symbol = symbolList.get(rand.nextInt((symbolList.size() - 1) + 1));
        showFormula(firstNumber, secondNumber, symbol);
    }

    private void showFormula(int firstNumber, int secondNumber, String symbol) {
        TextView temp1 = (TextView) findViewById(R.id.temp_value_1);
        temp1.setText(String.valueOf(firstNumber));
        TextView temp2 = (TextView) findViewById(R.id.temp_value_2);
        temp2.setText(symbol);
        TextView temp3 = (TextView) findViewById(R.id.temp_value_3);
        temp3.setText(String.valueOf(secondNumber));

        ObjectAnimator anim = ObjectAnimator.ofFloat(temp1, "alpha", 0f, 1f);
        anim.setDuration(2000);
        anim.start();
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(temp2, "alpha", 0f, 1f);
        anim2.setDuration(2000);
        anim2.start();
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(temp3, "alpha", 0f, 1f);
        anim3.setDuration(2000);
        anim3.start();

        resetProgressBar();

        switch (symbol) {
            case "+":
                answer = firstNumber + secondNumber;
                break;
            case "-":
                answer = firstNumber - secondNumber;
                break;
            case "*":
                answer = firstNumber * secondNumber;
                break;
            case "/":
                answer = firstNumber - secondNumber;
        }
    }

    private void resetProgressBar() {
        mProgressStatus = 0;
    }

    private void updateAnswerView(String s) {
        String currentText = answerView.getText().toString();
        answerView.setText(currentText + s);
    }

    public void sevenKeyPressed(View view) {
        updateAnswerView("7");
    }

    public void eightKeyPressed(View view) {
        updateAnswerView("8");
    }

    public void nineKeyPressed(View view) {
        updateAnswerView("9");
    }

    public void zeroKeyPressed(View view) {
        updateAnswerView("0");
    }

    public void fourKeyPressed(View view) {
        updateAnswerView("4");
    }

    public void fiveKeyPressed(View view) {
        updateAnswerView("5");
    }

    public void sixKeyPressed(View view) {
        updateAnswerView("6");
    }

    public void oneKeyPressed(View view) {
        updateAnswerView("1");
    }

    public void twoKeyPressed(View view) {
        updateAnswerView("2");
    }

    public void threeKeyPressed(View view) {
        updateAnswerView("3");
    }

    public void backspaceKeyPressed(View view) {
        if (answerView.getText() != null) {
            String currentText = answerView.getText().toString();
            if (currentText != "") {
                answerView.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
    }

    public void minusKeyPressed(View view) {
        String currentText = answerView.getText().toString();
        if (currentText == "") {
            answerView.setText("-");
        }
    }

    public boolean checkAnswer() {
        int answerSubmitted = Integer.parseInt(answerView.getText().toString());

        if (answerSubmitted == answer) {
            score += 10;
        } else {
            wrongAnswer();
            return false;
        }
        if (score > previousHighScore) {
            updateHighScores(gameLevel, score);
        }
        scoreView.setText(String.valueOf(score));
        return true;
    }

    private void updateHighScores(int gameLevel, int score) {
        previousHighScore = score;
        SharedPreferences settings = getSharedPreferences(CommonConstants.PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        switch (gameLevel) {
            case 0:
                editor.putInt(PreferenceKeys.easyScoreKey, score);
                break;
            case 1:
                editor.putInt(PreferenceKeys.mediumScoreKey, score);
                break;
            case 2:
                editor.putInt(PreferenceKeys.difficultScoreKey, score);
        }
        // Commit the edits
        editor.commit();
    }

}
