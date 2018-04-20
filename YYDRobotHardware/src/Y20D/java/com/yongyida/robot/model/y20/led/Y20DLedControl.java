package com.yongyida.robot.model.y20.led;


import android.content.Context;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.led.LedControl;
import com.yongyida.robot.communicate.app.hardware.led.data.LedHandle;
import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y20DLedControl extends LedControl {

    private LedStatueControl ledStatueControl = new LedStatueControl() ;
    private LedSceneControl ledSceneControl = new LedSceneControl() ;

    public Y20DLedControl(Context context) {
        super(context);
    }

    @Override
    public BaseResponse onControl(LedSend send) {
        return null;
    }


    public BaseResponse onControl(LedScene ledScene) {
        return ledSceneControl.onControl(ledScene) ;
    }

    public BaseResponse onControl(LedHandle ledHandle) {
        return ledStatueControl.onControl(ledHandle) ;
    }


}
