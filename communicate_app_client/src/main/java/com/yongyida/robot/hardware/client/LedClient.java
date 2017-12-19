package com.yongyida.robot.hardware.client;

import android.content.Context;

import com.hiva.communicate.app.common.SendResponse;
import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;
import com.yongyida.robot.communicate.app.hardware.led.data.LedStatue;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;

/**
 * Created by HuangXiangXiang on 2017/12/9.
 */
public class LedClient extends BaseClient {

    public LedClient(Context context){
        super(context);
    }

    public SendResponse sendLightEffect(LedScene ledScene){

        LedSend ledSend = new LedSend() ;
        ledSend.setLedScene(ledScene);

        return mReceiver.send(ledSend, null) ;
    }

    public SendResponse sendLedStatue(LedStatue ledStatue){

        LedSend ledSend = new LedSend() ;
        ledSend.setLedStatue(ledStatue);

        return mReceiver.send(ledSend, null) ;
    }


    public void sendLedStatueInMainThread(final LedStatue ledStatue){

        new Thread(){

            @Override
            public void run() {

                sendLedStatue(ledStatue) ;
            }
        }.start();

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
