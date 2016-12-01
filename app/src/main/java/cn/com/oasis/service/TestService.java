package cn.com.oasis.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/16 下午1:55
 */

public class TestService extends Service {

    String Tag = "Teset___service";
    public static String C_YEAR_MONTH_DATA_HOUR_MIN_SECOND = "yyyy年MM月dd日 HH:mm:ss";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.w(Tag, "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w(Tag, "onCreate");
        thread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w(Tag, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.w(Tag, "onDestroy");
        finish = true;
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.w(Tag, "onConfigurationChangedo");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.w(Tag, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.w(Tag, "onRebino");
        super.onRebind(intent);
    }

    private Thread thread = new Thread(new TestTask());

    private boolean finish = false;

    public class TestTask implements Runnable {
        @Override
        public void run() {
            while (!finish) {

                Log.w(Tag, "print task---->" + getStrDate(System.currentTimeMillis()) + ", thread:" + Thread.currentThread().getName());
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String getStrDate(Long time) {
        String ret = null;
        SimpleDateFormat sdf = new SimpleDateFormat(C_YEAR_MONTH_DATA_HOUR_MIN_SECOND);
        ret = sdf.format(new Date(time));

        return ret;
    }
}
