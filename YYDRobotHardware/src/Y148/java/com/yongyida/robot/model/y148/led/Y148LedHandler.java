package com.yongyida.robot.model.y148.led;

import android.content.Context;

import com.hiva.communicate.app.common.response.SendResponse;
import com.hiva.communicate.app.common.send.data.BaseSendControl;
import com.hiva.communicate.app.server.IResponseListener;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.led.LedHandler;
import com.yongyida.robot.communicate.app.hardware.led.response.data.LedStatus;
import com.yongyida.robot.communicate.app.hardware.led.send.data.BaseLedSendControl;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSend2Control;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;
import com.yongyida.robot.model.y148.led.control.BaseControlHandler;

import java.util.HashMap;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y148LedHandler extends LedHandler {

    private static final String TAG = Y148LedHandler.class.getSimpleName() ;

    private final SerialLed mSerialLed ;
    private final AndroidLed mAndroidLed ;
    private final UsbLed mUsbLed ;

    private HashMap<Class<? extends BaseLedSendControl>, BaseControlHandler> controlHandlers = new HashMap() ;


    public Y148LedHandler(Context context) {
        super(context);

        mSerialLed = new SerialLed() ;
        mAndroidLed = new AndroidLed() ;
        mUsbLed = new UsbLed(context) ;
    }

    @Override
    public SendResponse onHandler(LedSend send, IResponseListener responseListener) {

        BaseSendControl baseSendControl = send.getBaseControl() ;
        LogHelper.i(TAG , LogHelper.__TAG__() );

        if(baseSendControl == null){

            LogHelper.i(TAG , LogHelper.__TAG__() );
            return null ;
        }


        BaseControlHandler baseControlHandler = controlHandlers.get(baseSendControl.getClass()) ;

        if(baseControlHandler != null){
            baseControlHandler.onHandler(baseSendControl, responseListener);

            return new SendResponse() ;
        }

        return null;
    }










}
