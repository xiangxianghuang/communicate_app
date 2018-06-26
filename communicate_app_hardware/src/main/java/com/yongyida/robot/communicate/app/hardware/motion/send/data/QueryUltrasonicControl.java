package com.yongyida.robot.communicate.app.hardware.motion.send.data;


/**
 * Created by HuangXiangXiang on 2018/3/8.
 * 查询超声波信息
 */
public class QueryUltrasonicControl extends BaseMotionSendControl {

    public enum Android{

        DEFAULT,    // 不改变当前状态
        SEND,       // 超声波上传给Android
        NO_SEND,    // 超声波不会上传给Android
    }

    public enum Slam {

        DEFAULT,    // 不改变当前状态
        SEND,       // 超声波上传给Slam
        NO_SEND,    // 超声波不会上传给Slam
    }

    private Android android ;
    private Slam slam ;


    public Android getAndroid() {
        return android;
    }

    public void setAndroid(Android android) {
        this.android = android;
    }

    public Slam getSlam() {
        return slam;
    }

    public void setSlam(Slam slam) {
        this.slam = slam;
    }

}
