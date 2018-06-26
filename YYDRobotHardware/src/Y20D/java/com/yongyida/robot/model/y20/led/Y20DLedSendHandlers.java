package com.yongyida.robot.model.y20.led;


import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.led.LedSendHandlers;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y20DLedSendHandlers extends LedSendHandlers {

    private LedStatueControl ledStatueControl = new LedStatueControl() ;
    private LedSceneControl ledSceneControl = new LedSceneControl() ;

    public Y20DLedSendHandlers(Context context) {
        super(context);
    }

//    @Override
//    public SendResponse onHandler(LedSend send, IResponseListener responseListener) {
//        return null;
//    }


//    public SendResponse onControl(LedScene ledScene) {
//        return ledSceneControl.onControl(ledScene) ;
//    }
//
//    public SendResponse onControl(LedControl ledControl) {
//        return ledStatueControl.onControl(ledControl) ;
//    }


}
