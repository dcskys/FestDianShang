package com.example.dc.latte.net.interceptors;

import android.support.annotation.NonNull;
import android.support.annotation.RawRes;


import com.example.dc.latte.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 戴超 on 2017/10/18.
 *
 *
 * 测试拦截器   测试地址 直接返回模拟数据
 * 模拟json文件 储存在 raw目录
 *
 */

public class DebugInterceptor extends BaseInterceptor {

    private final String DEBUG_URL;
    //raw 目录
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String debugUrl, int rawId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = rawId;
    }

    //@RawRes 确保正确性
    private Response debugResponse(Chain chain, @RawRes int rawId) {
        //读取raw目录中的文件
        final String json = FileUtil.getRawFile(rawId);
        return getResponse(chain, json);
    }


    /**
     *      模拟 一个请求返回         raw 的json 数据
     * @param chain
     * @param json
     * @return
     */
    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    /**
     *拦截器
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final String url = chain.request().url().toString();
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID);
        }
        return chain.proceed(chain.request());
    }
}
