package com.example.dc.latte.net.callback;

import android.os.Handler;

import com.example.dc.latte.app.ConfigKeys;
import com.example.dc.latte.app.Latte;
import com.example.dc.latte.ui.loader.LatteLoader;
import com.example.dc.latte.ui.loader.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 戴超 on 2017/10/17.
 *
 * 自定义 返回处理
 *
 */
public final  class RequestCallbacks implements Callback<String>{

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;

    //hander 尽量声明 static
    private static final Handler HANDLER = new Handler();

    public RequestCallbacks(IRequest request, ISuccess success, IFailure failure, IError error, LoaderStyle style) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = style;
    }


    @Override
    public void onResponse(Call<String> call, Response<String> response) {

        //请求成功
        if (response.isSuccessful()) {

            //call 也执行了
            if (call.isExecuted()) {

                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }


        onRequestFinish();

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }

        //请求结束
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        onRequestFinish();
    }


    /**
     * 在请求 结束后 需要结束加载对话框
     */
    private void onRequestFinish() {

        //延迟结束对话框
        final long delayed = Latte.getConfiguration(ConfigKeys.LOADER_DELAYED);

        //加载动画不为空
        if (LOADER_STYLE != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopLoading();
                }
            }, delayed);
        }
    }


}
