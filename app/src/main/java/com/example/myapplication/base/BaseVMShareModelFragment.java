package com.example.myapplication.base;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;

/**
 *
 * @param <D> 对应的ViewDataBinding
 * @param <M> 共享activity的viewmodel,M必须和activity的保持一致，否则获取不到
 */
public abstract class BaseVMShareModelFragment<D extends ViewDataBinding, M extends BaseViewModel> extends Fragment {
    private D mDataBinding;
    private M mViewModel;
    protected FragmentActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mDataBinding = DataBindingUtil.inflate(inflater, getContentLayoutId(), container, false);
        View view = mDataBinding.getRoot();
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(BaseApplication.getApplication());

        /**
         * owner为fragment的activity，viewmodel是与activity共享的
         */

        if (activity != null && activity instanceof FragmentActivity) {
            ViewModelProvider provider = new ViewModelProvider((FragmentActivity)activity, factory);
            Class<M> entityClass = (Class<M>) ((ParameterizedType) activity.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
            mViewModel = provider.get(entityClass);
            if (getViewModelVariable() > 0) {
                mDataBinding.setVariable(getViewModelVariable(), mViewModel);
            }
        }

        if (getCallbackVariable() >= 0 && getCallback() != null) {
            mDataBinding.setVariable(getCallbackVariable(), getCallback());
        }
        mDataBinding.executePendingBindings();
        initFragment();
        return view;
    }

    @LayoutRes
    public abstract int getContentLayoutId();

    protected abstract void initFragment();

    public abstract int getCallbackVariable();

    public abstract Object getCallback();

    public abstract int getViewModelVariable();

    public D getDataBinding() {
        return mDataBinding;
    }

    public M getViewModel() {
        return mViewModel;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (FragmentActivity) context;
    }
}
