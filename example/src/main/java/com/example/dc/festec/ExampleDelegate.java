package com.example.dc.festec;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.dc.latte.delegates.LatteDelegate;

/**
 * Created by 戴超 on 2017/10/17.
 *
 */

public class ExampleDelegate  extends LatteDelegate{
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
