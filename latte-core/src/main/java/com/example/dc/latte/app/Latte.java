package com.example.dc.latte.app;
import android.content.Context;


/**
 * Created by  戴超 on 2017/9/30.
 *
 */
public final  class Latte {


    public static Configurator init(Context context){

        Configurator.getInstance().getLatteConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT,context.getApplicationContext());

        //返回单例
        return Configurator.getInstance();
    }

    /**
     *
     * @return  Configurator 单例
     */
    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }


    /**
     * 全局 cotext
     *
     * @return
     */
    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }


}
