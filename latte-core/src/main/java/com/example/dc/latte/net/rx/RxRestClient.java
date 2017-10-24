package com.example.dc.latte.net.rx;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.dc.latte.net.HttpMethod;
import com.example.dc.latte.net.RestClientBuilder;
import com.example.dc.latte.net.RestCreator;
import com.example.dc.latte.net.RestService;
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

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import static java.security.AccessController.getContext;

/**
 * Created by 戴超 on 2017/10/17.
 *
 *
 *RxJava 结合
 *
 * RestClient.builder().url("").build(); 建造者模式
 *
 * 下面这些都没了  rxjava 已经关联了
 *    IRequest REQUEST;
 *    DOWNLOAD_DIR; //下载目录
  EXTENSION;//扩展名
  NAME;
  SUCCESS;
  IFailure FAILURE;
  IError ERROR;


 *
 */

public final class RxRestClient {

    private final WeakHashMap<String, Object> PARAMS;
    private final String URL;

    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE; //加载对话框的动画类型
    private final File FILE;
    private final Context CONTEXT;

    RxRestClient(String url,
                 WeakHashMap<String, Object> params,
                 RequestBody body,
                 File file,
                 Context context,
                 LoaderStyle loaderStyle) {

        this.URL = url;
        this.PARAMS = params;
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }


    /**
     * 建造者模式
     * @return
     */
    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }


    /**
     * Observable  RxJava
     *
     * 区别 是  正常是 call开头  ，rxJava 是 Observable开头
     *
     *网络请求的类型
     * @param method
     */
    private Observable<String> request(HttpMethod method){

        //获取 网络请求服务
        final RxRestService service = RestCreator.getRxRestService();

        Observable<String> observable = null;


        //加载动画类型 不为空  显示 加载动画
        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW: //post 原始数据
                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BODY);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                //类型为表单形式的文件上传
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                observable = service.upload(URL, body);
                break;
            default:
                break;

        }
        return observable;
    }


    /**
     * 请求类型 为 get
     */
    public final Observable<String> get() {
       return request(HttpMethod.GET);
    }



    public final Observable<String> post() {
        if (BODY == null) {
            return request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.POST_RAW);
        }
    }



    public final Observable<String> put() {
        if (BODY == null) {
            return request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }


    /**
     *
     *上传
     */
    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }


    /**
     * 下载
     * ResponseBody 类型    不需要再写异步任务了
     * @return
     */
    public final Observable<ResponseBody> download() {
        return RestCreator.getRxRestService().download(URL, PARAMS);
    }



    //todo rxJava 网络框架的 用法1

    void onCallRxet(){

        final String url = "index.php";
        final WeakHashMap<String, Object> params = new WeakHashMap<>();

        //todo rxJava get 请求 地址和参数
        final Observable<String> observable = RestCreator.getRxRestService().get(url, params);

        //Schedulers.io() 运行在io线程 推荐   Schedulers.newThread()新启一个线程
        observable.subscribeOn(Schedulers.io()) //运行线程 io
                .observeOn(AndroidSchedulers.mainThread()) //结果运行 在 主线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        // 请求刚开始
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull String s) {
                        //ui 线程
                        Log.e("请求的结果返回",""+s);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        //请求错误
                    }

                    @Override
                    public void onComplete() {
                        //请求结束

                    }
                });


    }


    //todo rxJava 网络框架的 用法X2
    private void onCallRxRestClient(){

        final String url = "index.php";

        RxRestClient.builder()
                .url(url)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //如果是 下载 操作 这里要是io线程或新线程去处理  文件写入操作
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull String s) {
                        //ui 线程
                        Log.e("请求的结果返回",""+s);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



}
