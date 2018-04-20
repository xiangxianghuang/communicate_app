package com.yongyida.robot.model.caro.led;

import android.content.Context;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.led.LedControl;
import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;
import com.yongyida.robot.communicate.app.hardware.led.data.LedHandle;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;

/**
 * Created by HuangXiangXiang on 2017/12/18.
 */
public class CaroLedControl extends LedControl {

    private LedSceneControl ledSceneControl = new LedSceneControl() ;
    private LedStatueControl ledStatueControl = new LedStatueControl() ;

    public CaroLedControl(Context context) {
        super(context);
    }

    @Override
    public BaseResponse onControl(LedSend send) {
        return null;
    }

    public BaseResponse onControl(LedHandle ledHandle) {
        return ledStatueControl.onControl(ledHandle) ;
    }

    public BaseResponse onControl(LedScene ledScene) {
        return ledSceneControl.onControl(ledScene) ;
    }
}
