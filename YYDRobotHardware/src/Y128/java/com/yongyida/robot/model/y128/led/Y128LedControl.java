package com.yongyida.robot.model.y128.led;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.led.LedControl;
import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;
import com.yongyida.robot.communicate.app.hardware.led.data.LedStatue;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y128LedControl extends LedControl {

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

    @Override
    public BaseResponse onControl(LedStatue ledStatue) {
        return null;
    }

    @Override
    public BaseResponse onControl(LedScene ledScene) {
        return null;
    }
}
