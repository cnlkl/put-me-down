package com.fzuclover.putmedown.services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.fzuclover.putmedown.utils.LogUtil;

public class ShowPMDForegroundService extends Service {
    public ShowPMDForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(7000);
                    ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                    activityManager.moveTaskToFront(intent.getIntExtra("task_id",0), 0);
                    LogUtil.d("show_service", "将计时界面移至前台");
                    Thread.sleep(10 * 60 * 1000);
                    startService(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (RuntimeException e){
                    e.printStackTrace();
                    stopSelf();
                }

            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("show_service", "服务结束");
    }
}
