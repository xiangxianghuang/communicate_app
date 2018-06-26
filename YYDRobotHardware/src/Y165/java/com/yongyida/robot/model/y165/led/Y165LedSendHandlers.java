package com.yongyida.robot.model.y165.led;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.led.LedSendHandlers;
import com.yongyida.robot.model.agreement.Y165Steering;

/**
 * Created by HuangXiangXiang on 2018/4/17.
 */
public class Y165LedSendHandlers extends LedSendHandlers {

    private Y165Steering.EyeLed mEyeLed ;


    public Y165LedSendHandlers(Context context) {
        super(context);

        mEyeLed = new Y165Steering.EyeLed() ;
    }

//    @Override
//    public SendResponse onHandler(LedSend send, IResponseListener responseListener) {
//
////        LedControl ledControl = send.getLedControl() ;
////        if(ledControl != null){
////
////            LedControl.Effect effect = ledControl.getEffect() ;
////
////            mEyeLed.setOnOff(LedControl.Effect.NORMAL.equals(effect));
////            Y165Send.getInstance().sendData(mEyeLed) ;
////
////        }
//
//        return null;
//    }
}
