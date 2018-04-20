package com.yongyida.robot.model.y20.motion;

import android.content.Context;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.motion.data.MotionControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;

import java.util.ArrayList;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y20MotionControl extends com.yongyida.robot.communicate.app.hardware.motion.MotionControl {

    private final static String SERIAL_PORT = "" ;


    private MotionStatueControl motionStatueControl ;

    public Y20MotionControl(Context context) {
        super(context);

        motionStatueControl = new MotionStatueControl(SERIAL_PORT) ;
    }

    @Override
    public BaseResponse onControl(MotionSend send) {
        return null;
    }

//    private MotionHelper motionHelper =  new MotionHelper() ;



    protected BaseResponse onControl(ArrayList<MotionControl> motionControls) {
        return motionStatueControl.onControl(motionControls);
    }


}
