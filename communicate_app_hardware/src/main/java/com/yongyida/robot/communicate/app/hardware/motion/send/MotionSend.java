package com.yongyida.robot.communicate.app.hardware.motion.send;

import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.motion.data.MotionControl;
import com.yongyida.robot.communicate.app.hardware.motion.data.QueryMoveFault;
import com.yongyida.robot.communicate.app.hardware.motion.data.UltrasonicControl;

import java.util.ArrayList;

/**
 * Created by HuangXiangXiang on 2017/12/4.
 * 动作
 */
public class MotionSend extends BaseSend {

    private QueryMoveFault queryMoveFault ;

    private MotionControl motionControl;

    private UltrasonicControl ultrasonicControl ;


    public void setMotionControl(MotionControl motionControl) {

       this.motionControl = motionControl;
    }

    public MotionControl getMotionControl() {
        return motionControl;
    }

    public QueryMoveFault getQueryMoveFault() {
        return queryMoveFault;
    }

    public void setQueryMoveFault(QueryMoveFault queryMoveFault) {
        this.queryMoveFault = queryMoveFault;
    }

    public UltrasonicControl getUltrasonicControl() {
        return ultrasonicControl;
    }

    public void setUltrasonicControl(UltrasonicControl ultrasonicControl) {
        this.ultrasonicControl = ultrasonicControl;
    }
}
