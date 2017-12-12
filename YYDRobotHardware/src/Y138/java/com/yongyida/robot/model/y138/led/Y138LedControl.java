package com.yongyida.robot.model.y138.led;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.led.LedControl;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y138LedControl extends LedControl {


    @Override
    public boolean start() {
        return false;
    }

    @Override
    public boolean stop() {
        return false;
    }

    @Override
    public boolean isStart() {
        return false;
    }


    @Override
    public BaseResponse onControl(LedSend ledSend) {
        return null;
    }
}
