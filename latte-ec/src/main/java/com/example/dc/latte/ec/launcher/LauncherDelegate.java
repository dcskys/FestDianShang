package com.example.dc.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.example.dc.latte.app.AccountManager;
import com.example.dc.latte.app.IUserChecker;
import com.example.dc.latte.delegates.LatteDelegate;
import com.example.dc.latte.ec.R;
import com.example.dc.latte.ec.R2;
import com.example.dc.latte.ui.launcher.ScrollLauncherTag;
import com.example.dc.latte.util.storage.LattePreference;
import com.example.dc.latte.util.timer.BaseTimerTask;
import com.example.dc.latte.util.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by 戴超 on 2017/10/19.
 *
 * app 启动页面

 *
 */

public class LauncherDelegate extends LatteDelegate implements ITimerListener {


   //因为 用到的是r2 所以需要先 rebuild project id会出不来
    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer = null;

    private Timer mTimer = null;
    private int mCount = 5;

    //启动结束判断登录 的回调 在 activity中实现
    private ILauncherListener mILauncherListener = null;


    //提前结束定时
    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
               checkIsShowScroll();
        }
    }


    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0, 1000);
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
      return R.layout.delegate_launcher;

    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        initTimer();

    }


    //定时结束 判断是否显示滑动启动页
    private void checkIsShowScroll() {

        Log.e("测试",LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())+"");

        //表示第一次启动
        if (!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            getSupportDelegate().start(new LauncherScrollDelegate(), SINGLETASK);
        } else {
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



    @Override
    public void onTimer() {

        //ui 线程
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //防止未初始化完成
                if (mTvTimer!=null){
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                             checkIsShowScroll();
                        }
                    }
                }

            }
        });
    }




}


