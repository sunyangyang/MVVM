package com.example.myapplication.login;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseVMActivity;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.login.viewmodel.LoginViewModel;
import com.example.myapplication.util.AppUtil;
import com.example.myapplication.util.DisplayHelper;
import com.example.myapplication.util.ToastUtils;

public class LoginActivity extends BaseVMActivity<ActivityLoginBinding, LoginViewModel> {

    LoginCodeFragment mCodeFragment;
    LoginInputNumberFragment mPhoneNumberFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPhoneNumberFragment = new LoginInputNumberFragment();
        mCodeFragment = new LoginCodeFragment();

        getViewModel().mIndex.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer == 0) {
                    replaceNumberFragment();
                } else {
                    replaceCodeFragment();
                }
            }
        });

        getViewModel().showToast.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer == getViewModel().SHOW_NUMBER_ERROR) {
                    new ToastUtils.Builder(getApplicationContext()).
                            setCustomView(R.layout.toast_phone_number_invalid).
                            setDuration(Toast.LENGTH_LONG).
                            setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP).
                            setYOffset(DisplayHelper.dp2px(getApplicationContext(), 54)).
                            build().
                            show();
                } else if (integer == getViewModel().SHOW_CODE_ERROR) {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_error_code), Toast.LENGTH_LONG).show();
                } else if (integer == getViewModel().SHOW_DISMISS_PROGRESS) {
                    //TODO
                }
            }
        });

        getViewModel().isSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public int getCallbackVariable() {
        return 0;
    }

    @Override
    public Object getCallback() {
        return null;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppUtil.hideSoftInput(this, getCurrentFocus());
    }

    public void replaceCodeFragment() {
        getSupportFragmentManager().
                beginTransaction().
                setCustomAnimations(R.anim.activity_slide_from_right_to_left_enter, R.anim.activity_slide_from_right_to_left_exit).
                replace(R.id.layout_container, mCodeFragment).
                commit();
    }

    public void replaceNumberFragment() {
        getSupportFragmentManager().
                beginTransaction().
                setCustomAnimations(R.anim.activity_slide_from_left_to_right_enter, R.anim.activity_slide_from_left_to_right_exit).
                replace(R.id.layout_container, mPhoneNumberFragment).
                commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (getViewModel().mIndex.getValue().intValue() != 0) {
                getViewModel().mIndex.setValue(0);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
