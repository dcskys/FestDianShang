package com.example.dc.latte.ec.sign;

/**
 * Created by 戴超 on 2017/10/24.
 *
 * 登录和 注册 成功接口回调
 */

public interface ISignListener {

    //登录
    void onSignInSuccess();

    //注册
    void onSignUpSuccess();
}
