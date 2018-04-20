package com.yongyida.robot.communicate.app.hardware.motion;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.BaseControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class MotionControl extends BaseControl<MotionSend> {

    public MotionControl(Context context) {
        super(context);
    }

}
