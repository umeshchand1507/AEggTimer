package com.bitsfy.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvCountdownTimer;
    private SeekBar sbTimerBar;
    private Button btnCounter;
    private CountDownTimer countDownTimer;
    private MediaPlayer mediaPlayer;

    private Boolean isCounterActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCountdownTimer = findViewById(R.id.tvTimer);
        btnCounter = findViewById(R.id.btnController);
        sbTimerBar = findViewById(R.id.sbTimer);

        sbTimerBar.setMax(600);
        sbTimerBar.setProgress(30);

        sbTimerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.train_sound);
    }

    public void onControllerClicked(View view) {
        if (isCounterActive) {
            mediaPlayer.stop();
            countDownTimer.cancel();

            isCounterActive = false;
            sbTimerBar.setEnabled(true);
            btnCounter.setText("GO!");

        } else {
            isCounterActive = true;
            sbTimerBar.setEnabled(false);
            btnCounter.setText("STOP");

            countDownTimer = new CountDownTimer(sbTimerBar.getProgress() * 1000,1000){
                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000 );
                }

                @Override
                public void onFinish() {
                    Log.i("Timer", "Countdown timer stopped!");
                    mediaPlayer.start();
                }

            }.start();
        }
    }

    private void updateTimer(int timeInSeconds) {
        int minutes = (int) timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        String secondStr = String.valueOf(seconds);
        if (seconds < 10) {
            secondStr = "0" + String.valueOf(seconds);
        }
        tvCountdownTimer.setText(String.valueOf(minutes) + ":" + secondStr);
    }
}
