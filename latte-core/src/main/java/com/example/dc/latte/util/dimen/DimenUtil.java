package com.example.dc.latte.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.dc.latte.app.Latte;

/**
 * Created by 戴超 on 2017/10/18.
 *
 */

public class DimenUtil {

    public static int getScreenWidth() {

        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }



}
