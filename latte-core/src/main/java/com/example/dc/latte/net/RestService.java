package com.example.dc.latte.net;

import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by 戴超 on 2017/10/17.
 *
 * 定义的所有接口
 */

public interface RestService {

 /*
    TODO 不使用 RxJava 的用法 使用Call<String>
 @GET
    Call<String> get(@Url String url,@QueryMap WeakHashMap<String, Object> params);
*/


    //WeakHashMap 自动回收 减少消耗
    //
    @GET
    Call<String> get(@Url String url, @QueryMap WeakHashMap<String, Object> params);


    //@FormUrlEncoded 表示请求的是一个表单
    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap WeakHashMap<String,Object> params);

    // post 原始数据
    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody body);

    //@FormUrlEncoded 表示请求的是一个表单
    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @FieldMap WeakHashMap<String, Object> params);

    @PUT
    Call<String> putRaw(@Url String url, @Body RequestBody body);

    @DELETE
    Call<String> delete(@Url String url, @QueryMap WeakHashMap<String, Object> params);


    //边下载 边写入才能避免 一次性写入 内存溢出 （需要异步处理）
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    //@Multipart 支持文件上传的表单
    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);




}
