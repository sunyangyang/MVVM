package com.example.myapplication.login.callback;

public interface LoginNumberCallback {
    void showAreaCodePop();
    void dismissPop();
    void changeArea(String areaCode);
    void toCodeFragment();
}
