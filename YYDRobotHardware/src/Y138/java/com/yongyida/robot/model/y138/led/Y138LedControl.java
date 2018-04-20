package com.yongyida.robot.model.y138.led;

import android.content.Context;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.led.LedControl;
import com.yongyida.robot.communicate.app.hardware.led.data.LedHandle;
import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y138LedControl extends LedControl {


    public Y138LedControl(Context context) {
        super(context);
    }

    @Override
    public BaseResponse onControl(LedSend ledSend) {
        return null;
    }

    public BaseResponse onControl(LedHandle ledHandle) {
        return null;
    }

    public BaseResponse onControl(LedScene ledScene) {
        return null;
    }
}
