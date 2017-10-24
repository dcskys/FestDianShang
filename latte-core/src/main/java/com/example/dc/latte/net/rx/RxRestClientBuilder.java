package com.example.dc.latte.net.rx;

import android.content.Context;

import com.example.dc.latte.net.RestClient;
import com.example.dc.latte.net.callback.IError;
import com.example.dc.latte.net.callback.IFailure;
import com.example.dc.latte.net.callback.IRequest;
import com.example.dc.latte.net.callback.ISuccess;
import com.example.dc.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 *
 * 建造者模式
 *
 * Created by 戴超 on 2017/10/17.
 */

public class RxRestClientBuilder {

    private final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    private String mUrl = null;
    private RequestBody mBody = null;
    private Context mContext = null;
    private File mFile = null;
    private LoaderStyle mLoaderStyle = null; //加载动画类型


    RxRestClientBuilder() {
    }

    public final RxRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RxRestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }


    /**
     * 请求 类型 是个 json 格式
     * @param raw
     * @return
     */
    public final RxRestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    /**
     * @param context
     * @param style   加载对话框 加载动画类型
     * @return
     */
    public final RxRestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    /**
     * @param context
     *  加载对话框 加载动画类型   默认类型
     * @return
     */
    public final RxRestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RxRestClient build() {
        return new RxRestClient(mUrl, PARAMS,
                mBody, mFile, mContext,mLoaderStyle);
    }



}
