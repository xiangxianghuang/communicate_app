package com.yongyida.robot.model.y20.motion;

import android.content.Context;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.motion.MotionHandler;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y20DMotionControl extends MotionHandler {

    public Y20DMotionControl(Context context) {
        super(context);
    }

    @Override
    public BaseResponse onHandler(MotionSend send) {
        return null;
    }
}
