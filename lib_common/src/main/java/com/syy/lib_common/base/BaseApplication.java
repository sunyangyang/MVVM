package com.syy.lib_common.base;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

public class BaseApplication extends Application {
    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Application getApplication() {
        return application;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public Handler getMainHandler() {
        return mHandler;
    }
}
