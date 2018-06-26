package com.yongyida.robot.communicate.app.hardware.motion;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.BaseSendHandlers;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class MotionSendHandlers extends BaseSendHandlers<MotionSend> {

    public MotionSendHandlers(Context context) {
        super(context);
    }

}
