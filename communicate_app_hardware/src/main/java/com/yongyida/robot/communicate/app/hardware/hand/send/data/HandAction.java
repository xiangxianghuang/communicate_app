package com.yongyida.robot.communicate.app.hardware.hand.send.data;

import com.yongyida.robot.communicate.app.hardware.motion.send.data.BaseMotionSendControl;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Direction;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.PreHandAction;

/**
 * Created by HuangXiangXiang on 2018/4/20.
 * 预设手势的动作（一般需要手臂和手指）
 *
 */
public class HandAction extends BaseHandSendControl {

    // 方向位置
    private Direction direction = Direction.BOTH ;

    // 预设动作
    private PreHandAction preHandAction;


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public PreHandAction getPreHandAction() {
        return preHandAction;
    }

    public void setPreHandAction(PreHandAction preHandAction) {
        this.preHandAction = preHandAction;
    }
}
