package com.example.dc.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.dc.latte.app.AccountManager;
import com.example.dc.latte.app.IUserChecker;
import com.example.dc.latte.delegates.LatteDelegate;

import com.example.dc.latte.ec.R;
import com.example.dc.latte.ui.launcher.LauncherHolderCreator;
import com.example.dc.latte.ui.launcher.ScrollLauncherTag;
import com.example.dc.latte.util.storage.LattePreference;

import java.util.ArrayList;

/**
 * Created by 戴超 on 2017/10/23.
 *
 * 轮播图页面
 *
 */

public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener {

    private ConvenientBanner<Integer> mConvenientBanner =null;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();

    //启动结束判断登录 的回调 在 activity中实现
    private ILauncherListener mILauncherListener = null;


    private void initBanner() {
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        mConvenientBanner
                .setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})//翻页指示器
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//指示器方向
                .setOnItemClickListener(this)
                .setCanLoop(false);//是否循环
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
    }


    @Override
    public Object setLayout() {
        return mConvenientBanner = new ConvenientBanner<Integer>(getContext());
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {

        //如果点击的是最后一个
        if (position == INTEGERS.size() - 1) {
            //表示是第一次进入
            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);

            //检查用户状态 是否登录了APP
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    //登录
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    //未登录
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });

        }
    }
}
