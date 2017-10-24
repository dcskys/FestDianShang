package com.example.dc.latte.app;

/**
 * Created by 戴超 on 2017/9/30.
 *
 *枚举  是单例   只能被初始化一次
 *
 *
 *相当于线程安全的懒汉模式
 *
 * 用于多线程 中
 *
 */

public enum ConfigKeys {
    API_HOST,//主机地址

    APPLICATION_CONTEXT,//context

    CONFIG_REDAY, //控制初始化配置完成了没有   fasle 未初始化

    ICON, //字体等初始化

    INTERCEPTOR, //okhttp3 拦截器

    HANDLER, //全局的hander

    LOADER_DELAYED, //hander 结束延迟时间 毫秒

}
