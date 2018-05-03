package com.yongyida.robot.model.caro.led;

import android.content.Context;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.led.LedHandler;
import com.yongyida.robot.communicate.app.hardware.led.data.LedControl;
import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;

/**
 * Created by HuangXiangXiang on 2017/12/18.
 */
public class CaroLedHandler extends LedHandler {

    private LedSceneControl ledSceneControl = new LedSceneControl() ;
    private LedStatueControl ledStatueControl = new LedStatueControl() ;

    public CaroLedHandler(Context context) {
        super(context);
    }

    @Override
    public BaseResponse onHandler(LedSend send) {
        return null;
    }

    public BaseResponse onControl(LedControl ledControl) {
        return ledStatueControl.onControl(ledControl) ;
    }

    public BaseResponse onControl(LedScene ledScene) {
        return ledSceneControl.onControl(ledScene) ;
    }
}
