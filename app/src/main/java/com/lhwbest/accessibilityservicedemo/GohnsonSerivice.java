package com.lhwbest.accessibilityservicedemo;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by hongwei21 on 2017/3/13.
 */
public class GohnsonSerivice extends Service {

    private final static int GOHNSON_ID = 1000;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GOHNSON_ID, new Notification());
        } else {
            Intent innerIntent = new Intent(this, GohnsonInnerService.class);
            startService(innerIntent);
            startForeground(GOHNSON_ID, new Notification());
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class GohnsonInnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GOHNSON_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}