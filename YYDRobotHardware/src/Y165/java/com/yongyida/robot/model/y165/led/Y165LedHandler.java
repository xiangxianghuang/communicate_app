package com.yongyida.robot.model.y165.led;

import android.content.Context;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.led.LedHandler;
import com.yongyida.robot.communicate.app.hardware.led.data.LedControl;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;
import com.yongyida.robot.model.agreement.Y128Steering;
import com.yongyida.robot.model.agreement.Y165Send;
import com.yongyida.robot.model.agreement.Y165Steering;

/**
 * Created by HuangXiangXiang on 2018/4/17.
 */
public class Y165LedHandler extends LedHandler {

    private Y165Steering.EyeLed mEyeLed ;


    public Y165LedHandler(Context context) {
        super(context);

        mEyeLed = new Y165Steering.EyeLed() ;
    }

    @Override
    public BaseResponse onHandler(LedSend send) {

        LedControl ledControl = send.getLedControl() ;
        if(ledControl != null){

            LedControl.Effect effect = ledControl.getEffect() ;

            mEyeLed.setOnOff(LedControl.Effect.NORMAL.equals(effect));
            Y165Send.getInstance().sendData(mEyeLed) ;

        }

        return null;
    }
}
