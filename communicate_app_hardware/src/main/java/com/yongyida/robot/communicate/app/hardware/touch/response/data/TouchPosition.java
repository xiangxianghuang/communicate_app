package com.yongyida.robot.communicate.app.hardware.touch.response.data;

import com.yongyida.robot.communicate.app.common.response.BaseResponseControl;

/**
 * Created by HuangXiangXiang on 2018/3/1.
 * 触摸点
 */
public class TouchPosition extends BaseResponseControl {

    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Create By HuangXiangXiang 2018/6/22
     * 触摸位置
     */
    public enum Position {

        FORE_HEAD,              //前额
        BACK_HEAD,              //后脑
        LEFT_SHOULDER,          //左肩
        RIGHT_SHOULDER,         //右肩
        DOUBLE_SHOULDER,        //双肩
        LEFT_ARM,               //左手臂
        RIGHT_ARM,              //右手臂
        DOUBLE_ARM,             //双臂
    }
}
