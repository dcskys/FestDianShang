package com.example.dc.latte.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by 戴超 on 2017/10/9.
 *
  *自定义字体 IconFonts字体
 *
 *转成utf 不能直接用 u
 *
 * 支持阿里巴巴 图标库
 */

public enum  EcIcons  implements Icon{

    //字体   支付宝 扫描
    icon_scan('\ue602'),
    icon_ali_pay('\ue606');

    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
