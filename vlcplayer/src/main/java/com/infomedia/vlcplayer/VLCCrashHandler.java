package com.infomedia.vlcplayer;

import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

import com.infomedia.vlcplayer.util.Constants;
import com.infomedia.vlcplayer.util.Util;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

public class VLCCrashHandler implements UncaughtExceptionHandler {

    private static final String TAG = "VLC/VlcCrashHandler";

    private UncaughtExceptionHandler defaultUEH;

    public VLCCrashHandler() {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);

        // Inject some info about android version and the device, since google can't provide them in the developer console
        StackTraceElement[] trace = ex.getStackTrace();
        StackTraceElement[] trace2 = new StackTraceElement[trace.length + 3];
        System.arraycopy(trace, 0, trace2, 0, trace.length);
        trace2[trace.length + 0] = new StackTraceElement("Android", "MODEL", android.os.Build.MODEL, -1);
        trace2[trace.length + 1] = new StackTraceElement("Android", "VERSION", android.os.Build.VERSION.RELEASE, -1);
        trace2[trace.length + 2] = new StackTraceElement("Android", "FINGERPRINT", android.os.Build.FINGERPRINT, -1);
        ex.setStackTrace(trace2);

        ex.printStackTrace(printWriter);
        String stacktrace = result.toString();
        printWriter.close();
        Log.e(TAG, stacktrace);

        // Save the log on SD card if available
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            writeLog(stacktrace, Constants.EXTERNAL_PUBLIC_DIRECTORY + "/vlc_crash");
            writeLogcat(Constants.EXTERNAL_PUBLIC_DIRECTORY + "/vlc_logcat");
        }

        defaultUEH.uncaughtException(thread, ex);
    }

    private void writeLog(String log, String name) {
        CharSequence timestamp = DateFormat.format("yyyyMMdd_kkmmss", System.currentTimeMillis());
        String filename = name + "_" + timestamp + ".log";

        FileOutputStream stream;
        try {
            stream = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        OutputStreamWriter output = new OutputStreamWriter(stream);
        BufferedWriter bw = new BufferedWriter(output);

        try {
            bw.write(log);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Util.close(bw);
            Util.close(output);
        }
    }

    private void writeLogcat(String name) {
        CharSequence timestamp = DateFormat.format("yyyyMMdd_kkmmss", System.currentTimeMillis());
        String filename = name + "_" + timestamp + ".log";
        try {
            Util.writeLogcat(filename);
        } catch (IOException e) {
            Log.e(TAG, "Cannot write logcat to disk");
        }
    }
}
