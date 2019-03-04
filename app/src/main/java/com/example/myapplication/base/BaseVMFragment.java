package com.example.myapplication.base;

import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;

/**
 *
 * @param <D> 对应的ViewDataBinding
 * @param <M> 只拥有属于自己的viewmodel
 */
public abstract class BaseVMFragment<D extends ViewDataBinding, M extends BaseViewModel> extends Fragment {
    protected D mDataBinding;
    protected M mFragmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, getContentLayoutId(), container, false);
        View view = mDataBinding.getRoot();
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(BaseApplication.getApplication());
        /**
        * owner为fragment本身
        */
        ViewModelProvider providerOwner = new ViewModelProvider(this, factory);
        Class<M> entityClassOwner = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        mFragmentViewModel = providerOwner.get(entityClassOwner);
        if (getBindingVariable() > 0) {
            mDataBinding.setVariable(getBindingVariable(), mFragmentViewModel);
        }
        if (getCallbackVariable() >= 0 && getCallback() != null) {
            mDataBinding.setVariable(getCallbackVariable(), getCallback());
        }
        mDataBinding.executePendingBindings();
        initFragment();
        return view;
    }

    public abstract int getContentLayoutId();

    protected abstract void initFragment();

    public abstract int getCallbackVariable();

    public abstract Object getCallback();

    public abstract int getBindingVariable();

    public D getDataBinding() {
        return mDataBinding;
    }

    public M getViewModel() {
        return mFragmentViewModel;
    }

}
