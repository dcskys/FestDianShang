package com.example.dc.latte.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.dc.latte.app.Latte;
import com.example.dc.latte.net.callback.IRequest;
import com.example.dc.latte.net.callback.ISuccess;
import com.example.dc.latte.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by 戴超 on 2017/10/18.
 *
 *  下载保存文件 的异步任务
 *
 *  Object 传入的 参数
 */

public class SaveFileTask extends AsyncTask<Object, Void, File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;


    SaveFileTask(IRequest REQUEST, ISuccess SUCCESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
    }


    @Override
    protected File doInBackground(Object... params) {

        //第一个 参数  下载目录
        String downloadDir = (String) params[0];
        //第二个参数   扩展名
        String extension = (String) params[1];
        //第三个 参数 请求体
        final ResponseBody body = (ResponseBody) params[2];
        //第4个参数 文件名
        final String name = (String) params[3];

        final InputStream is = body.byteStream();

        //默认下载目录
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "down_loads";
        }
        //默认无 扩展名
        if (extension == null || extension.equals("")) {
            extension = "";
        }

        if (name == null) {
            //根据后缀 自动生成文件名
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            //指定文件名
            return FileUtil.writeToDisk(is, downloadDir, name);
        }

    }


    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null) {
            //保存的文件路径
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        autoInstallApk(file);
    }


    /**
     * 自动 安装目录
     * @param file
     */
    private void autoInstallApk(File file) {
        //文件后缀 为apk
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(install);
        }
    }




}
