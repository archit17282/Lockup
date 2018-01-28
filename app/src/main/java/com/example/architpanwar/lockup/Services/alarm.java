package com.example.architpanwar.lockup.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by architpanwar on 20/01/18.
 */

public class alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        context.startService(new Intent(context, check.class));


    }
}
