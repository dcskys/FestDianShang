package com.example.dc.latte.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.dc.latte.delegates.LatteDelegate;
import com.example.dc.latte.util.storage.LattePreference;

/**
 * Created by 戴超 on 2017/10/24.
 *
 * 账户管理
 *
 */

public class AccountManager  {

    private enum SignTag {
        SIGN_TAG  //登录标识
    }

    //保存用户登录状态，登录后调用
    public static void setSignState(boolean state) {
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    /**
     * @return  ture   已登录
     */
    private static boolean isSignIn() {
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }


    /**
     * 检测 账户 是否登录
     * @param checker
     */
    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }


}
