package com.yongyida.robot.communicate.app.hardware.led;

import com.hiva.communicate.app.common.IResponseListener;
import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.BaseHandler;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class LedHandler extends BaseHandler {

    private static final String TAG = LedHandler.class.getSimpleName() ;

    private LedControl mLedControl ;

    public LedHandler(){

        mLedControl = (LedControl) getControl();
    }


    @Override
    protected boolean isCanHandle(BaseSend send) {

        return send instanceof LedSend;
    }

    @Override
    public void onHandler(BaseSend send, IResponseListener responseListener) {

        LedSend ledSend = (LedSend) send;
        BaseResponse baseResponse = mLedControl.onControl(ledSend) ;
        if(responseListener != null){

            responseListener.onResponse(baseResponse);
        }
    }

    @Override
    public int getType() {

        return HardwareConfig.TYPE_LED;
    }

}
