package com.yongyida.robot.model.y148.led;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.led.LedSendHandlers;
import com.yongyida.robot.model.y148.led.control.Y148LedLibraryControlHandler;
import com.yongyida.robot.model.y148.led.control.Y148LedSend2ControlHandler;
import com.yongyida.robot.model.y148.led.control.Y148LedSendControlHandler;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y148LedSendHandlers extends LedSendHandlers {

    private static final String TAG = Y148LedSendHandlers.class.getSimpleName() ;


    public Y148LedSendHandlers(Context context) {
        super(context);

        addBaseControlHandler(new Y148LedSendControlHandler(context)) ;
        addBaseControlHandler(new Y148LedSend2ControlHandler(context)) ;
        addBaseControlHandler(new Y148LedLibraryControlHandler(context)) ;
    }











}
