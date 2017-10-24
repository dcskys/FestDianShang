package com.example.dc.latte.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.dc.latte.app.AccountManager;
import com.example.dc.latte.ec.database.DatabaseManager;
import com.example.dc.latte.ec.database.UserProfile;

/**
 * Created by 戴超 on 2017/10/24.
 *
 登录注册  处理类  保存到 数据库中
 */

public class SignHandler {


    /**
     * 处理登录 信息返回
     * @param response
     * @param signListener  接口回调
     */
    public static void onSignIn(String response, ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        //保存数据库
        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        DatabaseManager.getInstance().getDao().insert(profile);

        //标识登录成功
        AccountManager.setSignState(true);
        //通知回调
        signListener.onSignInSuccess();

    }


    /** 处理 注册信息返回
     * @param response
     * @param signListener
     */
    public static void onSignUp(String response, ISignListener signListener) {
        //fastJson
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        //保存数据库
        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        DatabaseManager.getInstance().getDao().insert(profile);

        //标识登录成功
       AccountManager.setSignState(true);
        //通知回调
        signListener.onSignUpSuccess();
    }


}
