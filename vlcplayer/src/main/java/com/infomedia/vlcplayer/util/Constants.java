package com.infomedia.vlcplayer.util;

import android.os.Environment;

public class Constants {
    public final static String SLEEP_INTENT = "org.videolan.vlc.SleepIntent";
    public final static String INCOMING_CALL_INTENT = "org.videolan.vlc.IncomingCallIntent";
    public final static String CALL_ENDED_INTENT = "org.videolan.vlc.CallEndedIntent";

    public final static String EXTERNAL_PUBLIC_DIRECTORY = Environment.getExternalStorageDirectory().getPath();
}
