package com.example.dc.latte.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.example.dc.latte.R;
import com.example.dc.latte.delegates.LatteDelegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by 戴超 on 2017/10/9.
 *
 * SupportActivity fragmentation库中的用法
 *
 * 单activity 多fragment架构
 *
 *
 */
public  abstract class ProxyActivity extends SupportActivity{

    //基础的fragment  单activity 加载 的fragment
    public abstract LatteDelegate setRootDelegare();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initContainer(savedInstanceState);

    }


    private void initContainer(@Nullable Bundle savedInstanceState){

        //根布局为 frameLayout
        final ContentFrameLayout  container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);

        //fragmentation库 中加载fragment
        if (savedInstanceState == null) {
           loadRootFragment(R.id.delegate_container, setRootDelegare());
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //不一定执行 进行垃圾回收的工作
        System.gc();
        System.runFinalization();

    }
}
