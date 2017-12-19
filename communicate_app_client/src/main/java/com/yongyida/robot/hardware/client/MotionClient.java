package com.yongyida.robot.hardware.client;

import android.content.Context;

import com.hiva.communicate.app.common.SendResponse;
import com.yongyida.robot.communicate.app.hardware.led.data.LedStatue;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;
import com.yongyida.robot.communicate.app.hardware.motion.data.MotionStatue;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;

/**
 * Created by HuangXiangXiang on 2017/12/19.
 */
public class MotionClient extends BaseClient{


    public MotionClient(Context context) {
        super(context);
    }

    public SendResponse sendMotionStatue(MotionStatue motionStatue){

        MotionSend motionSend = new MotionSend() ;
        motionSend.setMotionStatue(motionStatue);

        return mReceiver.send(motionSend, null) ;
    }


    public void sendMotionStatueInMainThread(final MotionStatue motionStatue){

        new Thread(){

            @Override
            public void run() {

                sendMotionStatue(motionStatue) ;
            }
        }.start();

    }


}
