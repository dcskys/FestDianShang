package com.example.dc.latte.util.timer;

import java.util.TimerTask;

/**
 * Created by 戴超 on 2017/10/19
 *
 * 计时任务  一个可以被定时执行的任务
 *
 */
public class BaseTimerTask  extends TimerTask{

    private ITimerListener  mITimerListener = null;

    public BaseTimerTask(ITimerListener mITimerListener) {
        this.mITimerListener = mITimerListener;
    }

    @Override
    public void run() {

        if (mITimerListener!=null){
            mITimerListener.onTimer();
        }



    }
}
