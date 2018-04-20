package com.yongyida.robot.communicate.app.hardware.led;

import android.content.Context;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.BaseControl;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class LedControl extends BaseControl<LedSend> {

    public LedControl(Context context) {
        super(context);
    }

    @Override
    public abstract BaseResponse onControl(LedSend send);
}
