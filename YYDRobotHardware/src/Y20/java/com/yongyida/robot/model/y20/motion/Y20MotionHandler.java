package com.yongyida.robot.model.y20.motion;

import android.content.Context;

import com.hiva.communicate.app.common.send.SendResponseListener;
import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.hardware.motion.MotionHandler;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.MotionSendControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;

import java.util.ArrayList;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y20MotionHandler extends MotionHandler {

    private final static String SERIAL_PORT = "" ;


    private MotionStatueControl motionStatueControl ;

    public Y20MotionHandler(Context context) {
        super(context);

        motionStatueControl = new MotionStatueControl(SERIAL_PORT) ;
    }

    @Override
    public BaseResponse onHandler(MotionSend send, IResponseListener responseListener) {
        return null;
    }

//    private MotionHelper motionHelper =  new MotionHelper() ;



    protected BaseResponse onControl(ArrayList<MotionSendControl> motionControls) {
        return motionStatueControl.onControl(motionControls);
    }


}
