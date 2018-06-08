package com.yongyida.robot.model.y138.led;

import android.content.Context;

import com.hiva.communicate.app.common.send.SendResponseListener;
import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.send.data.BaseSendControl;
import com.hiva.communicate.app.server.IResponseListener;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.led.LedHandler;
import com.yongyida.robot.communicate.app.hardware.led.response.data.LedStatus;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl2;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y138LedHandler extends LedHandler {

    private static final String TAG = Y138LedHandler.class.getSimpleName() ;


    private final SerialLed mSerialLed ;
    private final AndroidLed mAndroidLed ;
    private final UsbLed mUsbLed ;


    public Y138LedHandler(Context context) {
        super(context);

        mSerialLed = new SerialLed() ;
        mAndroidLed = new AndroidLed() ;
        mUsbLed = new UsbLed(context) ;
    }

    @Override
    public BaseResponse onHandler(LedSend send, IResponseListener responseListener) {

//        String s = null ;
//        s.trim() ;

        BaseSendControl baseSendControl = send.getBaseControl() ;
        LogHelper.i(TAG , LogHelper.__TAG__() );

        if(baseSendControl instanceof LedSendControl2){

            // 灯带控制
            LedSendControl2 ledControl2 = (LedSendControl2) baseSendControl;
            mSerialLed.controlLed(ledControl2);

            return null ;
        }

        if(baseSendControl instanceof LedSendControl){

            LedSendControl ledControl = (LedSendControl) baseSendControl;
            LedSendControl.Position position = ledControl.getPosition() ;

            switch (position){

                case EAR:

                    mAndroidLed.controlLed(ledControl);
                    break;

                case CHEST:

                    mUsbLed.controlLed(ledControl);

                    break;

                case LEFT_ARM:

                    break;

                case RIGHT_ARM:
                    break;
                case ARM:
                    break;

            }
//            LedResponse ledResponse = new LedResponse() ;

            LedStatus ledStatus = new LedStatus() ;

            return ledStatus.getResponse() ;

//            return null ;
        }

        return null;
    }










}
