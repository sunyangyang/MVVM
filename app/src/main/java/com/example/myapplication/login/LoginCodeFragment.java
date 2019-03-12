package com.example.myapplication.login;

import android.databinding.ObservableList;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.syy.lib_common.base.BaseVMShareModelFragment;
import com.example.myapplication.databinding.FragmentLoginCodeBinding;
import com.example.myapplication.login.callback.LoginCodeCallback;
import com.example.myapplication.login.viewmodel.LoginViewModel;

import java.util.ArrayList;
import java.util.List;

public class LoginCodeFragment extends BaseVMShareModelFragment<FragmentLoginCodeBinding, LoginViewModel> implements LoginCodeCallback {
    @Override
    public int getContentLayoutId() {
        return R.layout.fragment_login_code;
    }

    private List<EditText> mList = new ArrayList<>();

    @Override
    protected void initFragment() {
        if (getViewModel() != null) {
            //edittext中的内容
            if (getViewModel().mCodes != null) {
                getViewModel().mCodes.clear();
                getViewModel().mCodes.add(getViewModel().KEY_FIRST, "");
                getViewModel().mCodes.add(getViewModel().KEY_SECOND, "");
                getViewModel().mCodes.add(getViewModel().KEY_THIRD, "");
                getViewModel().mCodes.add(getViewModel().KEY_FOURTH, "");
            }

            //edittext
            if (mList != null && mList.size() <= 0) {
                mList.add(getViewModel().KEY_FIRST, getDataBinding().editCodeFirst);
                mList.add(getViewModel().KEY_SECOND, getDataBinding().editCodeSecond);
                mList.add(getViewModel().KEY_THIRD, getDataBinding().editCodeThird);
                mList.add(getViewModel().KEY_FOURTH, getDataBinding().editCodeFourth);
            }

            getDataBinding().editCodeFirst.requestFocus();
            getViewModel().mCodes.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<String>>() {
                @Override
                public void onChanged(ObservableList<String> sender) {

                }

                @Override
                public void onItemRangeChanged(ObservableList<String> sender, int positionStart, int itemCount) {
                    StringBuilder verificationCode = new StringBuilder();
                    if (!TextUtils.isEmpty(sender.get(positionStart))) {
                        boolean hasNoEmpty = true;
                        for (int i = 0; i < sender.size(); i++) {
                            if (i != positionStart && TextUtils.isEmpty(sender.get(i))) {
                                mList.get(i).requestFocus();
                                hasNoEmpty = false;
                                break;
                            }
                            verificationCode.append(sender.get(i));
                        }
                        if (hasNoEmpty) {
                            getViewModel().login(verificationCode.toString());
                        }
                    }
                }

                @Override
                public void onItemRangeInserted(ObservableList<String> sender, int positionStart, int itemCount) {

                }

                @Override
                public void onItemRangeMoved(ObservableList<String> sender, int fromPosition, int toPosition, int itemCount) {

                }

                @Override
                public void onItemRangeRemoved(ObservableList<String> sender, int positionStart, int itemCount) {

                }
            });
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
    public void backToNumberFragment() {
        getViewModel().mIndex.setValue(getViewModel().INDEX_NUMBER);
    }

    @Override
    public void sendCode() {
        getViewModel().sendCode();
    }
}
