package com.yongyida.robot.hardware.client;

import android.content.Context;

import com.hiva.communicate.app.common.IResponseListener;
import com.hiva.communicate.app.common.SendResponse;
import com.yongyida.robot.communicate.app.hardware.led.data.LedControl;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;

/**
 * Created by HuangXiangXiang on 2017/12/9.
 */
public final class LedClient extends BaseClient<LedSend> {

    private static LedClient mLedClient ;
    public static LedClient getInstance(Context context){

        if(mLedClient == null){

            mLedClient = new LedClient(context.getApplicationContext()) ;
        }
        return mLedClient ;
    }

    private LedClient(Context context) {
        super(context);
    }


    /**
     * 在主线程发送led控制,需要回调函数
     * */
    public SendResponse sendLedControl(LedControl ledControl, IResponseListener response){

        LedSend ledSend = new LedSend() ;
        ledSend.setLedControl(ledControl);

        return send(ledSend,response);
    }

    /**
     * 在主线程发送led控制,不需要回调函数
     * */
    public void sendLedControlInMainThread(LedControl ledControl, IResponseListener response) {

        LedSend ledSend = new LedSend() ;
        ledSend.setLedControl(ledControl);

        sendInMainThread(ledSend,response);
    }



}
