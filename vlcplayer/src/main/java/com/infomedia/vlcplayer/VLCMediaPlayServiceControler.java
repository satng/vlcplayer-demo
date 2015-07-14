package com.infomedia.vlcplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class VLCMediaPlayServiceControler {
    private static final String TAG = VLCMediaPlayServiceControler.class.getSimpleName();

    private static VLCMediaPlayServiceControler instance;

    private Context mContext;

    private PlaybackService.LocalBinder mIVLCServiceBinder;
    private ServiceConnection mVLCServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mIVLCServiceBinder = (PlaybackService.LocalBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceConnected");
        }
    };

    private VLCMediaPlayServiceControler(Context context) {
        mContext = context;
    }

    //API
    public static void init(Context ctx) {
        if (null == instance) {
            synchronized (VLCMediaPlayServiceControler.class) {
                if (null == instance) {
                    instance = new VLCMediaPlayServiceControler(ctx);
                }
            }
        }

        instance.bind();
    }

    public static PlaybackService getService(Context ctx) {
        if (null == instance) {
            synchronized (VLCMediaPlayServiceControler.class) {
                if (null == instance) {
                    instance = new VLCMediaPlayServiceControler(ctx);
                }
            }
        }

        return instance.mIVLCServiceBinder.getService();
    }

    public static void exit() {
        if (null != instance) {
            instance.unbind();
        }
    }

    //private Functions
    private void bind() {
        mContext.startService(new Intent(mContext, PlaybackService.class));
        mContext.bindService(new Intent(mContext, PlaybackService.class),
                mVLCServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbind() {
        if (mContext != null) {
            mContext.unbindService(mVLCServiceConnection);
        }
    }
}
