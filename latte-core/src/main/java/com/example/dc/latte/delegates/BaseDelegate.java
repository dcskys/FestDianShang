package com.example.dc.latte.delegates;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dc.latte.activities.ProxyActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by 戴超 on 2017/10/9.
 *
 * SwipeBackFragment 可侧滑退出的fargment
 *
 * 基类
 *
 */

public  abstract  class BaseDelegate extends SwipeBackFragment{

    public abstract Object setLayout();


    private Unbinder mUnbinder = null;


    public abstract void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView);



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;

        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((int) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }

        mUnbinder = ButterKnife.bind(this, rootView);
        onBindView(savedInstanceState, rootView);
        return rootView;
    }


    /**
     * @return  关联fragment 的activity
     */
    public final ProxyActivity getProxyActivity() {
        return (ProxyActivity) _mActivity;
    }




    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }

    }
}
