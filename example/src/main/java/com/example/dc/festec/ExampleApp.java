package com.example.dc.festec;

import android.app.Application;

import com.example.dc.latte.app.Latte;
import com.example.dc.latte.ec.database.DatabaseManager;
import com.example.dc.latte.ec.icon.FontEcModule;
import com.example.dc.latte.net.interceptors.DebugInterceptor;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by 戴超 on 2017/10/9.
 */

public class ExampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Latte.init(this)
                .withIcon(new FontAwesomeModule())//封装好的 FontAwesome 网站字体图标库
                .withIcon(new FontEcModule())//自定义的字体库  本地ttf
                .withLoaderDelayed(1000)
                .withInterceptor(new DebugInterceptor("index",R.raw.test))//拦截器 用来模拟数据
                .withApiHost("http://127.0.0.1/")
                .confugure();

        //初始化 数据库
        DatabaseManager.getInstance().init(this);

    }
}
