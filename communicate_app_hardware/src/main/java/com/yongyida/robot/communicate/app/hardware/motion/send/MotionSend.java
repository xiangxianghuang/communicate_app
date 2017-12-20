package com.yongyida.robot.communicate.app.hardware.motion.send;

import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.motion.data.MotionStatue;

import java.util.ArrayList;

/**
 * Created by HuangXiangXiang on 2017/12/4.
 * 动作
 */
public class MotionSend extends BaseSend {

    private ArrayList<MotionStatue> motionStatues ;

    public ArrayList<MotionStatue> getMotionStatues() {
        return motionStatues;
    }

    public void setMotionStatues(ArrayList<MotionStatue> motionStatues) {
        this.motionStatues = motionStatues;
    }
}
