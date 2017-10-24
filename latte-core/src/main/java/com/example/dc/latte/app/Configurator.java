package com.example.dc.latte.app;

import android.os.Handler;
import android.util.Log;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by 戴超 on 2017/9/30.
 * <p>
 * 配置文件
 */

public class Configurator {

    //存放配置信息
    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();

    //字体图标集合 用来初始化 这个库
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    //okhttp3 拦截器集合
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();


    //  线程安全的懒汉  使用 静态内部类的形式进行初始化（优雅）
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    private Configurator() {
        //准备未完成
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_REDAY, false);
        //保存hander
        LATTE_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }


    //获取单例
    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * @return 返回 map
     */
    final HashMap<Object, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }


    public final void confugure() {
        //初始化图标
        initIcons();
        //表示配置文件配置好了
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_REDAY, true);
    }


    /**
     * @param host 设置主机名
     * @return
     */
    public final Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    /**
     * @param delayed hander 延迟时长
     * @return
     */
    public final Configurator withLoaderDelayed(long delayed) {
        LATTE_CONFIGS.put(ConfigKeys.LOADER_DELAYED, delayed);
        return this;
    }

    /**
     * @param descriptor 添加到图标列表
     * @return
     */
    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }


    /**
     * @param interceptor okhttp拦截器
     * @return
     */
    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    /**
     * @param interceptors 拦截器列表
     * @return
     */
    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }


    //推荐 使用 final  检测配置是否完成
    private void checkConfiguration() {


        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_REDAY);


        if (!isReady) {
            throw new RuntimeException("配置未初始化完成 ");
        }

    }


    /**
     * 初始化 图标库 android-iconify 库的方法
     */
    private void initIcons() {
        if (ICONS.size() > 0) {
            //初始化第一个
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            //有第二个 再初始化
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }


    /**
     * @param key 获取 key 对应的值
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {

        checkConfiguration();

        final Object value = LATTE_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) LATTE_CONFIGS.get(key);
    }


}
