package com.infomedia.vlcplayerdemo.activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.infomedia.vlcplayer.MediaWrapper;
import com.infomedia.vlcplayer.PlaybackService;
import com.infomedia.vlcplayer.VLCMediaPlayServiceControler;
import com.infomedia.vlcplayerdemo.R;

import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.io.IOException;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initEvents();
    }

    private void initEvents() {
        findViewById(R.id.init).setOnClickListener(this);
        findViewById(R.id.play).setOnClickListener(this);
        findViewById(R.id.pause).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);
        findViewById(R.id.prev).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.init:
                VLCMediaPlayServiceControler.getService(getApplicationContext()).setResumeActivity(this);
                VLCMediaPlayServiceControler.getService(getApplicationContext()).append(new MediaWrapper(Uri.parse("mms://222.216.222.9/1003")));
                VLCMediaPlayServiceControler.getService(getApplicationContext()).append(new MediaWrapper(Uri.parse("mms://218.61.6.228/sqgb")));
                VLCMediaPlayServiceControler.getService(getApplicationContext()).append(new MediaWrapper(Uri.parse("mms://alive.rbc.cn/am927")));
                VLCMediaPlayServiceControler.getService(getApplicationContext()).append(new MediaWrapper(Uri.parse("http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8")));
                VLCMediaPlayServiceControler.getService(getApplicationContext()).append(new MediaWrapper(Uri.parse("http://wsmp32.bbc.co.uk/")));
                VLCMediaPlayServiceControler.getService(getApplicationContext()).append(new MediaWrapper(Uri.parse("http://m.livestream.com")));
                VLCMediaPlayServiceControler.getService(getApplicationContext()).setRepeatType(PlaybackService.RepeatType.All);

                //load() to clear
//                VLCMediaPlayServiceControler.getService(getApplicationContext()).load(new MediaWrapper(Uri.parse("mms://alive.rbc.cn/am927")));

                VLCMediaPlayServiceControler.getService(getApplicationContext()).addCallback(new PlaybackService.Callback() {
                    @Override
                    public void update() {
//                        Log.d(TAG, "update");
                    }

                    @Override
                    public void updateProgress() {
//                        Log.d(TAG, "updateProgress");
                    }

                    @Override
                    public void onMediaEvent(Media.Event event) {
                        Log.d(TAG, "onMediaEvent: " + event.type);
                    }

                    @Override
                    public void onMediaPlayerEvent(MediaPlayer.Event event) {
                        Log.d(TAG, "onMediaPlayerEvent" + event.toString());
                    }
                });
                break;
            case R.id.play:
                VLCMediaPlayServiceControler.getService(getApplicationContext()).play();
                break;
            case R.id.pause:
                VLCMediaPlayServiceControler.getService(getApplicationContext()).pause();
                break;
            case R.id.stop:
                VLCMediaPlayServiceControler.getService(getApplicationContext()).stop();
                break;
            case R.id.prev:
                VLCMediaPlayServiceControler.getService(getApplicationContext()).previous();
                break;
            case R.id.next:
                MediaWrapper wrapper = VLCMediaPlayServiceControler.getService(getApplicationContext()).getCurrentMediaWrapper();
                VLCMediaPlayServiceControler.getService(getApplicationContext()).next();
                break;
        }
    }
}
