package com.example.dc.latte.net;

import com.example.dc.latte.app.ConfigKeys;
import com.example.dc.latte.app.Latte;
import com.example.dc.latte.net.rx.RxRestService;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 戴超 on 2017/10/17.
 *
 * Retrofit 初始化
 *
 */

public final  class RestCreator {



    /**
     * 构建OkHttp
     */
    private static final class OKHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        // 获取储存的 拦截器列表
        private static final ArrayList<Interceptor> INTERCEPTORS = Latte.getConfiguration(ConfigKeys.INTERCEPTOR);

        //添加拦截器
        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }


        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }


    /**
     * 构建全局Retrofit客户端
     */
    private static final class RetrofitHolder {
        private static final String BASE_URL = Latte.getConfiguration(ConfigKeys.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT) //okhttp 拦截器
                .addConverterFactory(ScalarsConverterFactory.create())//转化器 可以返回string类型
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持RxJava create同步 createAsync 异步
                .build();
    }


    /**
     * 正常
     * Service接口
     */
    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    /**正常
     * @return   获取全局  service 请求网络
     */
    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }





    /**
     * RxJava    区别 是  正常是 call开头  ，rxJava 是 Observable开头
     * Service接口
     */
    private static final class RxRestServiceHolder {
        private static final RxRestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }


    /**RxJava
     * @return   获取全局  service 请求网络
     */
    public static RxRestService getRxRestService() {
        return RxRestServiceHolder.REST_SERVICE;
    }

}
