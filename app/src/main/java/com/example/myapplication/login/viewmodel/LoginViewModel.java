package com.example.myapplication.login.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.database.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.login.model.LoginModel;
import com.syy.lib_common.base.BaseApplication;
import com.syy.lib_common.base.BaseViewModel;

import java.util.regex.Pattern;

public class LoginViewModel extends BaseViewModel {

    public LoginViewModel(Application application) {
        super(application);
        mIndex.setValue(INDEX_NUMBER);
        isSuccess.setValue(false);
        showToast.setValue(SHOW_DEFAULT);
    }

    public final int KEY_FIRST = 0;

    public final int KEY_SECOND = 1;

    public final int KEY_THIRD = 2;

    public final int KEY_FOURTH = 3;

    public final int CODE_TIME = 60;

    public final int INDEX_NUMBER = 0;

    public final int INDEX_CODE = 1;

    public final int SHOW_DEFAULT = -1;

    public final int SHOW_NUMBER_ERROR = 0;

    public final int SHOW_CODE_ERROR = 1;

    public final int SHOW_DISMISS_PROGRESS = 2;

    public MutableLiveData<Integer> mIndex = new MutableLiveData<>();

    private ObservableField<LoginModel> mLoginModel = new ObservableField<>();

    public ObservableArrayList<String> mCodes = new ObservableArrayList<>();

    public ObservableInt mTimeCount = new ObservableInt(CODE_TIME);

    public MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();

    public MutableLiveData<Integer> showToast = new MutableLiveData<>();//监听展示手机号有误，发送验证码失败，验证码错误等信息

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_img_back:
                mIndex.setValue(INDEX_NUMBER);
                break;
            case R.id.txt_re_send_code:
                sendCode();
                break;
        }
    }

    public void setLoginBean(LoginModel loginModel) {
        this.mLoginModel.set(loginModel);
    }


    public ObservableField<LoginModel> getLoginModel() {
        if (mLoginModel.get() == null) {
            LoginModel loginModel = new LoginModel();
            loginModel.initData(getApplication());
            mLoginModel.set(loginModel);
        }
        return mLoginModel;
    }

    public void toCodeFragment() {
        if (!isMobile(mLoginModel.get().phoneNumber.get())) {
            showToast.setValue(SHOW_NUMBER_ERROR);
        } else {
            sendCode();
        }
    }

    public void sendCode() {
        mIndex.setValue(INDEX_CODE);
        mTimeCount.set(CODE_TIME);
        countDown();
    }

    private void countDown() {
        if (mTimeCount.get() > 0) {
            ((BaseApplication)getApplication()).getMainHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTimeCount.set(mTimeCount.get() - 1);
                    if (mTimeCount.get() > 0) {
                        countDown();
                    }
                }
            }, 1000);
        }
    }

    public String getCodeTime() {
        if (mTimeCount.get() < 10) {
            return "0" + mTimeCount.get();
        }
        return String.valueOf(mTimeCount.get());
    }

    public void login(String verificationCode) {
        getLoginModel().get().verificationCode.set(verificationCode);
        isSuccess.setValue(true);
    }

    public static final Pattern PHONE_NUMBER = Pattern.compile("(13|14|15|16|17|18|19)\\d{9}");

    public static boolean isMobile(String text) {
        if (TextUtils.isEmpty(text)) return false;
        return text.matches(PHONE_NUMBER.pattern());
    }

}
