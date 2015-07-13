package com.infomedia.vlcplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.infomedia.vlcplayer.util.Constants;

public class PhoneStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING) ||
                state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            Intent newIntent = new Intent(Constants.INCOMING_CALL_INTENT);
            context.getApplicationContext().sendBroadcast(newIntent);
        }

        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            Intent newIntent = new Intent(Constants.CALL_ENDED_INTENT);
            context.getApplicationContext().sendBroadcast(newIntent);
        }
    }

}
