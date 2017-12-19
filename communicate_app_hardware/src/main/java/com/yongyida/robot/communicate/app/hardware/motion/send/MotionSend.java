package com.yongyida.robot.communicate.app.hardware.motion.send;

import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.motion.data.MotionStatue;

/**
 * Created by HuangXiangXiang on 2017/12/4.
 * 动作
 */
public class MotionSend extends BaseSend {

    private MotionStatue motionStatue ;

    public MotionStatue getMotionStatue() {
        return motionStatue;
    }

    public void setMotionStatue(MotionStatue motionStatue) {
        this.motionStatue = motionStatue;
    }
}
