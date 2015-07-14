package com.infomedia.vlcplayerdemo.activity;

import android.app.Application;
import com.infomedia.vlcplayer.MediaDatabase;
import com.infomedia.vlcplayer.VLCMediaPlayServiceControler;

public class InfoApplication extends Application{
    private static InfoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        MediaDatabase.getInstance(getApplicationContext());
        VLCMediaPlayServiceControler.init(getApplicationContext());
    }

    public static InfoApplication getInstance() {
        return instance;
    }
}
