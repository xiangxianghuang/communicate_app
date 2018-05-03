package com.yongyida.robot.communicate.app.hardware.motion;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.BaseHandler;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class MotionHandler extends BaseHandler<MotionSend> {

    public MotionHandler(Context context) {
        super(context);
    }

}
