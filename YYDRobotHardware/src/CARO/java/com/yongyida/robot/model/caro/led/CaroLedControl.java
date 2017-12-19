package com.yongyida.robot.model.caro.led;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.led.LedControl;
import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;
import com.yongyida.robot.communicate.app.hardware.led.data.LedStatue;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;

/**
 * Created by HuangXiangXiang on 2017/12/18.
 */
public class CaroLedControl extends LedControl {

    private LedSceneControl ledSceneControl = new LedSceneControl() ;
    private LedStatueControl ledStatueControl = new LedStatueControl() ;

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
    public BaseResponse onControl(LedStatue ledStatue) {
        return ledStatueControl.onControl(ledStatue) ;
    }

    @Override
    public BaseResponse onControl(LedScene ledScene) {
        return ledSceneControl.onControl(ledScene) ;
    }
}
