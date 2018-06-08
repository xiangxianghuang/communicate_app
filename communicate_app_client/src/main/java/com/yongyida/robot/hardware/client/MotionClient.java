//package com.yongyida.robot.hardware.client;
//
//import android.content.Context;
//
//import com.hiva.communicate.app.common.IResponseListener;
//import com.hiva.communicate.app.common.SendResponse;
//import com.yongyida.robot.communicate.app.hardware.motion.data.MotionControl;
//import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;
//
///**
// * Created by HuangXiangXiang on 2017/12/19.
// */
//public final class MotionClient extends BaseClient<MotionSend>{
//
//    private static MotionClient mMotionClient ;
//    public static MotionClient getInstance(Context context){
//
//        if(mMotionClient == null){
//
//            mMotionClient = new MotionClient(context.getApplicationContext()) ;
//        }
//        return mMotionClient ;
//    }
//    private MotionClient(Context context) {
//        super(context);
//    }
//
//    public SendResponse sendMotionControl(MotionControl motionControl, IResponseListener response){
//
//        MotionSend motionSend = new MotionSend() ;
//        motionSend.setMotionControl(motionControl);
//
//        return send(motionSend, response) ;
//    }
//
//    /**
//     * 在主线程发送动作控制,需要回调函数
//     * */
//    public void sendMotionControlInMainThread(MotionControl motionControl, IResponseListener response) {
//
//        MotionSend motionSend = new MotionSend() ;
//        motionSend.setMotionControl(motionControl);
//
//        sendInMainThread(motionSend, response);
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////    public SendResponse sendMotionStatues(ArrayList<MotionControl> motionControls){
////
////        MotionSend motionSend = new MotionSend() ;
////        motionSend.setMotionControls(motionControls);
////
////        return mReceiver.send(motionSend, null) ;
////    }
////
////
////    public void sendMotionStatuesInMainThread(final ArrayList<MotionControl> motionControls){
////
////        new Thread(){
////
////            @Override
////            public void run() {
////
////                sendMotionStatues(motionControls) ;
////            }
////        }.start();
////
////    }
//
//
//}
