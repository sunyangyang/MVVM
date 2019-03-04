package com.example.myapplication.base;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.BaseObservable;

public class BaseModel extends BaseObservable {
    private Resources mRes;
    public void initData() {

    }

    public void initData(Context context) {
        mRes = context.getResources();
    }

    public Resources getResources() {
        return mRes;
    }
}
