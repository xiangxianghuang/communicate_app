package com.yongyida.robot.model.caro.led;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.led.LedSendHandlers;

/**
 * Created by HuangXiangXiang on 2017/12/18.
 */
public class CaroLedSendHandlers extends LedSendHandlers {

    private LedSceneControl ledSceneControl = new LedSceneControl() ;
    private LedStatueControl ledStatueControl = new LedStatueControl() ;

    public CaroLedSendHandlers(Context context) {
        super(context);
    }

//    @Override
//    public SendResponse onHandler(LedSend send, IResponseListener responseListener) {
//        return null;
//    }


}
