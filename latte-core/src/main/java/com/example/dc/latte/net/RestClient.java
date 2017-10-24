package com.example.dc.latte.net;

import android.content.Context;

import com.example.dc.latte.net.callback.IError;
import com.example.dc.latte.net.callback.IFailure;
import com.example.dc.latte.net.callback.IRequest;
import com.example.dc.latte.net.callback.ISuccess;
import com.example.dc.latte.net.callback.RequestCallbacks;
import com.example.dc.latte.net.download.DownloadHandler;
import com.example.dc.latte.ui.loader.LatteLoader;
import com.example.dc.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by 戴超 on 2017/10/17.
 *
 *
 * RestClient.builder().url("").build(); 建造者模式
 */

public final class RestClient {

    private final WeakHashMap<String, Object> PARAMS;
    private final String URL;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR; //下载目录
    private final String EXTENSION;//扩展名
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE; //加载对话框的动画类型
    private final File FILE;
    private final Context CONTEXT;

    RestClient(String url,
               WeakHashMap<String, Object> params,
               String downloadDir,
               String extension,
               String name,
               IRequest request,
               ISuccess success,
               IFailure failure,
               IError error,
               RequestBody body,
               File file,
               Context context,
               LoaderStyle loaderStyle) {

        this.URL = url;
        this.PARAMS = params;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }


    /**
     * 建造者模式
     * @return
     */
    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }


    /**
     *网络请求的类型
     * @param method
     */
    private void request(HttpMethod method){

        //获取 网络请求服务
        final RestService service = RestCreator.getRestService();

        Call<String> call =null;

        //请求开始前
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        //加载动画类型 不为空  显示 加载动画
        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW: //post 原始数据
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                //类型为表单形式的文件上传
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, body);
                break;
            default:
                break;

        }

        if (call != null) {
            call.enqueue(getRequestCallback());
        }

    }


    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR,
                LOADER_STYLE
        );
    }


    /**
     * 请求类型 为 get
     */
    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {

        if (BODY == null) {
            //其实  是个 map
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            //post 原始数据
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }


    /**
     *
     *上传
     */
    public final void upload() {
        request(HttpMethod.UPLOAD);
    }


    /**
     * 下载
     */
    public final void dowload() {
        new DownloadHandler(URL, PARAMS,REQUEST, DOWNLOAD_DIR, EXTENSION, NAME,
                SUCCESS, FAILURE, ERROR)
                .handleDownload();
    }




}
