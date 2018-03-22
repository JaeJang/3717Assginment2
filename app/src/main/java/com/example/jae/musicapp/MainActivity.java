package com.example.jae.musicapp;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends Activity{
    private MediaPlayer mp;
    private SeekBar seekBar;
    private Handler mHandler;
    private Runnable mRunnable;
    int playstopBtns[] = {R.id.castle, R.id.pause, R.id.stop};

    int resMp3[] = {R.raw.castleon_the_hill};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        mHandler = new Handler();

        for (int btns : playstopBtns) {
            Button btn = findViewById(btns);
            btn.setOnClickListener(clickListener);
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mp !=  null && fromUser){
                    mp.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    View.OnClickListener clickListener = new View.OnClickListener() {

        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.castle:
                    Play(0); break;
                case R.id.pause:
                     Pause(); break;
                default :
                    Stop();
            }
        }

    };


    private void Play(int selNo){

        if(mp == null){
            mp = MediaPlayer.create(MainActivity.this, resMp3[selNo]);
        }
        if(!mp.isPlaying()){
            mp.start();
            initializeSeekBar();
        }
    }

    private void Stop(){
        if(mp!=null){
            mp.stop();
            mp = null;
        }
    }
    private void Pause(){
        if(mp != null){
            mp.pause();
        }
    }


    private void initializeSeekBar(){
        seekBar.setMax(mp.getDuration()/1000);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                if(mp!=null){
                    int mCurrentPosition = mp.getCurrentPosition()/1000; // In milliseconds
                    seekBar.setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(mRunnable,1000);
            }
        };
        mHandler.postDelayed(mRunnable,1000);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Stop();
    }


}
