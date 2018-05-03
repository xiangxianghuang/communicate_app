package com.yongyida.robot.communicate.app.hardware.motion.send;

import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.motion.data.ArmControl;
import com.yongyida.robot.communicate.app.hardware.motion.data.ChangeArmId;
import com.yongyida.robot.communicate.app.hardware.motion.data.HandAction;
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

    private HandAction handAction ;

    private ChangeArmId changeArmId ;

    private ArmControl armControl ;


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

    public HandAction getHandAction() {
        return handAction;
    }

    public void setHandAction(HandAction handAction) {
        this.handAction = handAction;
    }

    public ChangeArmId getChangeArmId() {
        return changeArmId;
    }

    public void setChangeArmId(ChangeArmId changeArmId) {
        this.changeArmId = changeArmId;
    }

    public ArmControl getArmControl() {
        return armControl;
    }

    public void setArmControl(ArmControl armControl) {
        this.armControl = armControl;
    }
}
