package com.yongyida.robot.model.y20.led;


import android.content.Context;

import com.hiva.communicate.app.common.response.SendResponse;
import com.hiva.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.hardware.led.LedHandler;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y20DLedHandler extends LedHandler {

    private LedStatueControl ledStatueControl = new LedStatueControl() ;
    private LedSceneControl ledSceneControl = new LedSceneControl() ;

    public Y20DLedHandler(Context context) {
        super(context);
    }

    @Override
    public SendResponse onHandler(LedSend send, IResponseListener responseListener) {
        return null;
    }


//    public SendResponse onControl(LedScene ledScene) {
//        return ledSceneControl.onControl(ledScene) ;
//    }
//
//    public SendResponse onControl(LedControl ledControl) {
//        return ledStatueControl.onControl(ledControl) ;
//    }


}
