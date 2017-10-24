package com.example.dc.latte.app;

/**
 * Created by 戴超 on 2017/10/24.
 *
 *
 * 检测 账户是否登录的接口
 *
 */

public interface IUserChecker {

    //账户登录
    void onSignIn();

    //未登录
    void onNotSignIn();

}
