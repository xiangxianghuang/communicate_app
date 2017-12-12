package com.yongyida.robot.hardware.client;

import android.content.Context;

import com.hiva.communicate.app.client.Receiver;
import com.hiva.communicate.app.common.SendResponse;
import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;
import com.yongyida.robot.communicate.app.hardware.vision.data.VisionData;
import com.yongyida.robot.communicate.app.hardware.vision.send.VisionDataSend;

/**
 * Created by HuangXiangXiang on 2017/12/9.
 */
public class LedClient extends BaseClient {

    private HardwareClient mHardwareClient ;
    private Receiver mReceiver ;



    public LedClient(Context context){

        mHardwareClient = HardwareClient.getInstance(context) ;
        mReceiver = mHardwareClient.getHardwareReceiver() ;
    }


    public SendResponse sendLightEffect(LedScene ledScene){

        LedSend ledSend = new LedSend() ;
        ledSend.setLedScene(ledScene);

        return mReceiver.send(ledSend, null) ;
    }


    public void sendLightEffectInMainThread(final LedScene ledScene){

        new Thread(){

            @Override
            public void run() {

                sendLightEffect(ledScene) ;
            }
        }.start();

    }


}
