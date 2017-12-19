package com.yongyida.robot.model.y20.led;


import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.breathled.utils.BreathLedHelper;
import com.yongyida.robot.communicate.app.hardware.led.LedControl;
import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;
import com.yongyida.robot.communicate.app.hardware.led.data.LedStatue;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y20DLedControl extends LedControl {

    private LedStatueControl ledStatueControl = new LedStatueControl() ;
    private LedSceneControl ledSceneControl = new LedSceneControl() ;

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
    public BaseResponse onControl(LedScene ledScene) {
        return ledSceneControl.onControl(ledScene) ;
    }

    public BaseResponse onControl(LedStatue ledStatue) {
        return ledStatueControl.onControl(ledStatue) ;
    }


}
