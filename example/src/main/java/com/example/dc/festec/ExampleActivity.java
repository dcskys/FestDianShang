package com.example.dc.festec;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dc.latte.activities.ProxyActivity;
import com.example.dc.latte.delegates.LatteDelegate;
import com.example.dc.latte.ec.launcher.ILauncherListener;
import com.example.dc.latte.ec.launcher.LauncherDelegate;
import com.example.dc.latte.ec.launcher.LauncherScrollDelegate;
import com.example.dc.latte.ec.launcher.OnLauncherFinishTag;
import com.example.dc.latte.ec.sign.ISignListener;
import com.example.dc.latte.ec.sign.SignInDelegate;


/**
 *
 * 单 activity 模式
 *
 * ISignListener 登录注册 成功的回调
 *
 * ILauncherListener  是否是已登录的 状态
 *
 *
 *
 */
public class ExampleActivity extends ProxyActivity implements ISignListener,ILauncherListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       final ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }

    }

    /**
     * @return   默认 一开始是个launcher fragment
      */
    @Override
    public LatteDelegate setRootDelegare() {
            return new SignInDelegate();
    }


    //登录成功 回调
    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
    }

    //注册成功 回调
    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
    }

    //判断 是否 已登录
    //startWithPop 启动新的 结束当前fragemnt
    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {

        switch (tag) {
            case SIGNED:
//                Toast.makeText(this, "启动结束，用户登录了", Toast.LENGTH_LONG).show();
           //    getSupportDelegate().startWithPop(new EcBottomDelegate());
                break;
            case NOT_SIGNED:
//                Toast.makeText(this, "启动结束，用户没登录", Toast.LENGTH_LONG).show();
                getSupportDelegate().startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }


    }
}
