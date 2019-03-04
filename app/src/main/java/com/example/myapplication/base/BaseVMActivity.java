package com.example.myapplication.base;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;

import java.lang.reflect.ParameterizedType;

public abstract class BaseVMActivity<D extends ViewDataBinding, M extends BaseViewModel> extends FragmentActivity {
    private D mDataBinding;
    private M mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, getContentLayoutId());
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());
        ViewModelProvider provider = new ViewModelProvider(this, factory);
        Class<M> entityClass = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        mViewModel = provider.get(entityClass);
        if (getBindingVariable() > 0) {
            mDataBinding.setVariable(getBindingVariable(), mViewModel);
        }
        if (getCallbackVariable() >= 0 && getCallback() != null) {
            mDataBinding.setVariable(getCallbackVariable(), getCallback());
        }
        mDataBinding.executePendingBindings();
    }

    @LayoutRes
    public abstract int getContentLayoutId();

    public abstract int getCallbackVariable();

    public abstract Object getCallback();

    public abstract int getBindingVariable();

    public D getDataBinding() {
        return mDataBinding;
    }

    public M getViewModel() {
        return mViewModel;
    }


}
