package com.yongyida.robot.communicate.app.hardware.motion.data;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 * 动作(常用动作)
 */
public class MotionMode {

    public enum Mode{

        FORWARD,        //前进
        BACK,           //后退
        LEFT,           //左
        RIGHT,          //右
        STOP,           //停止
        TURN_LEFT,      //左前拐
        TURN_RIGHT,     //右前拐
        TURN_BACK_LEFT, //左后拐
        TURN_BACK_RIGHT,//右后拐
        WAKE_UP,        // 要转动的角度
    }


    /**位置*/
    private Mode mode ;

    /**方式*/
    private MotionStatue.Type type ;

    /**
     * 位移
     * */
    private MotionStatue.Distance distance ;

    /**
     * 时间
     * */
    private MotionStatue.Time time ;

    /**
     * 速度
     * */
    private MotionStatue.Speed speed ;


    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public MotionStatue.Type getType() {
        return type;
    }

    public void setType(MotionStatue.Type type) {
        this.type = type;
    }

    public MotionStatue.Distance getDistance() {
        return distance;
    }

    public void setDistance(MotionStatue.Distance distance) {
        this.distance = distance;
    }

    public MotionStatue.Time getTime() {
        return time;
    }

    public void setTime(MotionStatue.Time time) {
        this.time = time;
    }

    public MotionStatue.Speed getSpeed() {
        return speed;
    }

    public void setSpeed(MotionStatue.Speed speed) {
        this.speed = speed;
    }
}
