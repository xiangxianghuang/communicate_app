package com.yongyida.robot.communicate.app.hardware.motion;

import com.hiva.communicate.app.common.IResponseListener;
import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.BaseHandler;
import com.yongyida.robot.communicate.app.hardware.led.LedControl;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;
import com.yongyida.robot.communicate.app.hardware.motion.data.Motion;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class MotionHandler extends BaseHandler {

    private MotionControl mMotionControl ;

    public MotionHandler(){

        mMotionControl = (MotionControl) getControl();
    }



    @Override
    public int getType() {
        return HardwareConfig.TYPE_MOTION ;
    }

    @Override
    protected boolean isCanHandle(BaseSend send) {

        return send instanceof MotionSend;
    }

    @Override
    protected void onHandler(BaseSend send, IResponseListener responseListener) {

        MotionSend motionSend = (MotionSend) send;
        BaseResponse baseResponse = mMotionControl.onControl(motionSend) ;
        if(responseListener != null){

            responseListener.onResponse(baseResponse);
        }
    }
}
