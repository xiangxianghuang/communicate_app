package com.yongyida.robot.model.y20.motion;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.motion.MotionSendHandlers;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y20MotionSendHandlers extends MotionSendHandlers {

    public Y20MotionSendHandlers(Context context) {
        super(context);
    }

//    @Override
//    public SendResponse onHandler(MotionSend send, IResponseListener responseListener) {
//        return null;
//    }

//    private final static String SERIAL_PORT = "" ;
//
//
//    private MotionStatueControl motionStatueControl ;
//
//    public Y20MotionSendHandlers(Context context) {
//        super(context);
//
//        motionStatueControl = new MotionStatueControl(SERIAL_PORT) ;
//    }
//
//    @Override
//    public SendResponse onHandler(MotionSend send, IResponseListener responseListener) {
//        return null;
//    }
//
////    private MotionHelper motionHelper =  new MotionHelper() ;
//
//
//
//    protected SendResponse onControl(ArrayList<MotionSendControl> motionControls) {
//        return motionStatueControl.onControl(motionControls);
//    }


}
