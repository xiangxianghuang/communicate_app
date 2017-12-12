package com.yongyida.robot.hardware.client;

import android.content.Context;

import com.hiva.communicate.app.client.Receiver;
import com.hiva.communicate.app.common.SendResponse;
import com.yongyida.robot.communicate.app.hardware.vision.data.VisionData;
import com.yongyida.robot.communicate.app.hardware.vision.send.VisionDataSend;

/**
 * Created by HuangXiangXiang on 2017/12/9.
 */
public class VisionClient extends BaseClient {

    private HardwareClient mHardwareClient ;
    private Receiver mReceiver ;

    private VisionData mVisionData = new VisionData() ;

    public VisionClient(Context context){

        mHardwareClient = HardwareClient.getInstance(context) ;
        mReceiver = mHardwareClient.getHardwareReceiver() ;
    }

    public SendResponse startVisionData(){

        return sendVisionData(VisionData.Position.START, 0) ;
    }

    public SendResponse sendVisionData(VisionData.Position position, int distance){

        VisionDataSend visionDataSend = new VisionDataSend() ;
        mVisionData.setPosition(position);
        mVisionData.setDistance(distance);
        visionDataSend.setVisionData(mVisionData);

        return mReceiver.send(visionDataSend, null) ;
    }

    public SendResponse stopVisionData(){

        return sendVisionData(VisionData.Position.STOP, 0) ;
    }

    public void startVisionDataInMainThread(){

        new Thread(){

            @Override
            public void run() {

                startVisionData() ;
            }
        }.start();

    }

    public void sendVisionDataInMainThread(final VisionData.Position position, final int distance){

        new Thread(){

            @Override
            public void run() {

                sendVisionData(position,distance);
            }
        }.start();
    }

    public void stopVisionDataInMainThread(){

        new Thread(){

            @Override
            public void run() {

                stopVisionData() ;
            }
        }.start();

    }
}
