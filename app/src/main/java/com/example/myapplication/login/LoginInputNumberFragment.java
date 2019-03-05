package com.example.myapplication.login;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.syy.lib_common.base.BaseVMShareModelFragment;
import com.example.myapplication.databinding.FragmentLoginInputNumberBinding;
import com.example.myapplication.databinding.PopupShowAreaCodeBinding;
import com.example.myapplication.login.callback.LoginNumberCallback;
import com.example.myapplication.login.viewmodel.LoginViewModel;
import com.syy.lib_common.util.AppUtil;

public class LoginInputNumberFragment extends BaseVMShareModelFragment<FragmentLoginInputNumberBinding, LoginViewModel> implements LoginNumberCallback {
    private PopupWindow popupWindow;
    private Resources mRes;

    @Override
    public int getContentLayoutId() {
        return R.layout.fragment_login_input_number;
    }

    @Override
    protected void initFragment() {
        mRes = getResources();
        if (getViewModel() != null) {
            getDataBinding().setLoginModel(getViewModel().getLoginModel().getValue());
        }
    }

    @Override
    public int getCallbackVariable() {
        return BR.callback;
    }

    @Override
    public Object getCallback() {
        return this;
    }

    @Override
    public int getViewModelVariable() {
        return BR.viewModel;
    }

    @Override
    public void showAreaCodePop() {
        AppUtil.hideSoftInput(getContext(), getActivity().getCurrentFocus());
        if (popupWindow == null) {
            PopupShowAreaCodeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.popup_show_area_code, null, false);
            binding.setAreaCode(getViewModel().getLoginModel().getValue().areaList);
            binding.setCallback(this);
            popupWindow = new PopupWindow(binding.getRoot(), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            popupWindow.setAnimationStyle(R.style.pop_anim_style);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
        }
        popupWindow.showAtLocation(getDataBinding().getRoot(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void dismissPop() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void changeArea(String areaCode) {
        getViewModel().getLoginModel().getValue().areaCode.set(areaCode);
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void toCodeFragment() {
        getViewModel().toCodeFragment();
    }
}
