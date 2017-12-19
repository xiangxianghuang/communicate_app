package com.yongyida.robot.communicate.app.hardware.motion;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.IControl;
import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;
import com.yongyida.robot.communicate.app.hardware.led.data.LedStatue;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;
import com.yongyida.robot.communicate.app.hardware.motion.data.Motion;
import com.yongyida.robot.communicate.app.hardware.motion.data.MotionStatue;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class MotionControl implements IControl {

    public final BaseResponse onControl(MotionSend motionSend){

        MotionStatue motionStatue = motionSend.getMotionStatue() ;
        if(motionStatue != null){

            return onControl(motionStatue) ;
        }

        return null ;
    }

    protected abstract BaseResponse onControl(MotionStatue motionStatue);


    @Override
    public int getType() {
        return HardwareConfig.TYPE_MOTION ;
    }
}
