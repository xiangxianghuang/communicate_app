package com.yongyida.robot.model.y138.led;

import android.content.Context;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.led.LedHandler;
import com.yongyida.robot.communicate.app.hardware.led.data.LedControl;
import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y138LedHandler extends LedHandler {


    public Y138LedHandler(Context context) {
        super(context);
    }

    @Override
    public BaseResponse onHandler(LedSend send) {
        return null;
    }

    public BaseResponse onControl(LedControl ledControl) {
        return null;
    }

    public BaseResponse onControl(LedScene ledScene) {
        return null;
    }
}
