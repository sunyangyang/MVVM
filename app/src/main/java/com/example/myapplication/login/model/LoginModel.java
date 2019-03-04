package com.example.myapplication.login.model;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseModel;

public class LoginModel extends BaseModel {
    public ObservableField<String> phoneNumber = new ObservableField<>();
    public ObservableField<String> verificationCode = new ObservableField<>();
    public ObservableField<String> areaCode = new ObservableField<>();
    public ObservableArrayList<String> areaList = new ObservableArrayList<>();

    @Override
    public void initData(Context context) {
        super.initData(context);
        areaList.add(getResources().getString(R.string.login_ch) + getResources().getString(R.string.login_ch_area_code));
        areaList.add(getResources().getString(R.string.login_usa) + getResources().getString(R.string.login_usa_area_code));
        areaCode.set(getResources().getString(R.string.login_ch_area_code));
    }
}
