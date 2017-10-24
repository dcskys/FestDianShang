package com.example.dc.latte.ec.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * Created by 戴超 on 2017/10/9.
 *
 * 自定义的字体类型  实现  库IconFontDescriptor 接口
 *
 * ttf字体 格式  asset目录
 *
 */

public class FontEcModule implements IconFontDescriptor {


    /**
     * @return  返回一个 ttf格式  字体文件
     */
    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    /**
     * 返回自定义 的字体库
     * @return
     */
    @Override
    public Icon[] characters() {
        return EcIcons.values();
    }
}
