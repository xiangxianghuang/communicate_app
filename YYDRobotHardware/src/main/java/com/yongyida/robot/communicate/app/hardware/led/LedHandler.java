package com.yongyida.robot.communicate.app.hardware.led;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.BaseHandler;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class LedHandler extends BaseHandler<LedSend> {

    public LedHandler(Context context) {
        super(context);
    }


}
