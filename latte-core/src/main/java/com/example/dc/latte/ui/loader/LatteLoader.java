package com.example.dc.latte.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.dc.latte.R;
import com.example.dc.latte.util.dimen.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by 戴超 on 2017/10/17.
 *
 *对加载库 的封装
 */

public class LatteLoader {

    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;

    //集合 方便统一管理
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    //默认的加载动画类型
    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();



    public static void showLoading(Context context, Enum<LoaderStyle> type) {
        showLoading(context, type.name());
    }


    public static void showLoading(Context context, String type) {

        //v7 包中
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        // 实例化 加载控件
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null) {
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            //屏幕宽高的 1/8
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            //1/8+ 1/10
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        //添加到 list
        LOADERS.add(dialog);
        dialog.show();
    }


    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }


    /**
     * 停止所有 对话框
     */
    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
    }


}
