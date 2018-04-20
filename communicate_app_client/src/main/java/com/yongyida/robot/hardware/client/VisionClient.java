package com.yongyida.robot.hardware.client;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.vision.send.VisionDataSend;

/**
 * Created by HuangXiangXiang on 2017/12/9.
 */
public final class VisionClient extends BaseClient<VisionDataSend> {

    private static VisionClient mVisionClient ;
    public static VisionClient getInstance(Context context){

        if(mVisionClient == null){

            mVisionClient = new VisionClient(context.getApplicationContext()) ;
        }
        return mVisionClient ;
    }

    private VisionClient(Context context) {
        super(context);
    }

//    private VisionData mVisionData = new VisionData() ;
//
//    public VisionClient(Context context){
//        super(context);
//    }
//
//    public SendResponse startVisionData(){
//
//        return sendVisionData(VisionData.Position.START, 0) ;
//    }
//
//    public SendResponse sendVisionData(VisionData.Position position, int distance){
//
//        VisionDataSend visionDataSend = new VisionDataSend() ;
//        mVisionData.setPosition(position);
//        mVisionData.setDistance(distance);
//        visionDataSend.setVisionData(mVisionData);
//
//        return mReceiver.send(visionDataSend, null) ;
//    }
//
//    public SendResponse stopVisionData(){
//
//        return sendVisionData(VisionData.Position.STOP, 0) ;
//    }
//
//    public void startVisionDataInMainThread(){
//
//        new Thread(){
//
//            @Override
//            public void run() {
//
//                startVisionData() ;
//            }
//        }.start();
//
//    }
//
//    public void sendVisionDataInMainThread(final VisionData.Position position, final int distance){
//
//        new Thread(){
//
//            @Override
//            public void run() {
//
//                sendVisionData(position,distance);
//            }
//        }.start();
//    }
//
//    public void stopVisionDataInMainThread(){
//
//        new Thread(){
//
//            @Override
//            public void run() {
//
//                stopVisionData() ;
//            }
//        }.start();
//
//    }
}
