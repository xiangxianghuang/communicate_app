package com.yongyida.robot.hardware.client;

import android.content.Context;

import com.hiva.communicate.app.client.Receiver;
import com.hiva.communicate.app.common.SendResponse;
import com.yongyida.robot.communicate.app.hardware.vision.data.VisionControlData;
import com.yongyida.robot.communicate.app.hardware.vision.data.VisionData;
import com.yongyida.robot.communicate.app.hardware.vision.send.VisionDataSend;

/**
 * Created by HuangXiangXiang on 2017/12/9.
 */
public class VisionClient extends BaseClient {

    private HardwareClient mHardwareClient ;
    private Receiver mReceiver ;


    private VisionData mVisionData = new VisionData() ;
    private VisionControlData mVisionControlData = new VisionControlData() ;

    public VisionClient(Context context){

        mHardwareClient = HardwareClient.getInstance(context) ;
        mReceiver = mHardwareClient.getHardwareReceiver() ;
    }

    public SendResponse startVisionData(){

        VisionDataSend visionDataSend = new VisionDataSend() ;
        mVisionControlData.setStart(true);
        visionDataSend.setVisionControlData(mVisionControlData);

        return mReceiver.send(visionDataSend, null) ;
    }


    public SendResponse sendVisionData(VisionData.Position position, int distance){

        VisionDataSend visionDataSend = new VisionDataSend() ;
        mVisionData.setPosition(position);
        mVisionData.setDistance(distance);
        visionDataSend.setVisionData(mVisionData);

        return mReceiver.send(visionDataSend, null) ;
    }

    public SendResponse stopVisionData(){

        VisionDataSend visionDataSend = new VisionDataSend() ;
        mVisionControlData.setStart(false);
        visionDataSend.setVisionControlData(mVisionControlData);

        return mReceiver.send(visionDataSend, null) ;

    }


}
