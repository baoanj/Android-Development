package com.example.baoanj.hw6;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;


/**
 * Created by baoanj on 2016/11/7.
 */
public class MainActivity extends AppCompatActivity  {

    private ImageView musicImage;
    private TextView filepath;
    private TextView stateView;
    private TextView currentTime;
    private TextView totalTime;
    private SeekBar musicSeekbar;
    private Button playButton;
    private Button stopButton;
    private Button quitButton;

    private MusicService musicService;
    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");
    private RotateAnimation rotateAnimation;

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MyBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };

    public android.os.Handler handler = new android.os.Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {

            currentTime.setText(time.format(musicService.mp.getCurrentPosition()));
            totalTime.setText(time.format(musicService.mp.getDuration()));

            musicSeekbar.setProgress(musicService.mp.getCurrentPosition());
            musicSeekbar.setMax(musicService.mp.getDuration());

            musicSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        musicService.mp.seekTo(seekBar.getProgress());
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            handler.postDelayed(runnable, 100);
        }
    };

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        musicImage = (ImageView) findViewById(R.id.musicImage);
        filepath = (TextView) findViewById(R.id.filepath);
        stateView = (TextView) findViewById(R.id.state);
        currentTime = (TextView) findViewById(R.id.currentTime);
        totalTime = (TextView) findViewById(R.id.totalTime);
        musicSeekbar = (SeekBar) findViewById(R.id.musicSeekbar);
        playButton = (Button) findViewById(R.id.playButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        quitButton = (Button) findViewById(R.id.quitButton);

        musicService = new MusicService();

        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, sc, BIND_AUTO_CREATE);

        filepath.setText(getResources().getString(R.string.filepath));
        currentTime.setText(getResources().getString(R.string.startTime));
        totalTime.setText(time.format(musicService.mp.getDuration()));

        rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(15000);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);

        runnable.run();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicService.playOrPause();

                if(musicService.mp.isPlaying()) {
                    playButton.setText(getResources().getString(R.string.PAUSE));
                    stateView.setText(getResources().getString(R.string.Playing));
                    musicImage.startAnimation(rotateAnimation);
                } else {
                    playButton.setText(getResources().getString(R.string.PLAY));
                    stateView.setText(getResources().getString(R.string.Pause));
                    musicImage.clearAnimation();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicService.stop();

                playButton.setText(getResources().getString(R.string.PLAY));
                stateView.setText(getResources().getString(R.string.Stop));
                musicImage.clearAnimation();
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(runnable);
                unbindService(sc);
                try {
                    MainActivity.this.finish();
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }
}

