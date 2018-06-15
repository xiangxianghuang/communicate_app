package com.yongyida.robot.model.caro.led;

import android.content.Context;

import com.hiva.communicate.app.common.response.SendResponse;
import com.hiva.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.hardware.led.LedHandler;
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
    public SendResponse onHandler(LedSend send, IResponseListener responseListener) {
        return null;
    }


}
