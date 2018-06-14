package com.yongyida.robot.communicate.app.hardware.motion.send.data;

/**
 * Created by HuangXiangXiang on 2018/3/8.
 */
public class UltrasonicSendControl extends BaseMotionSendControl {


    public enum Android{

        DEFAULT,
        START,
        STOP
    }

    public enum Slamware {

        DEFAULT,
        START,
        STOP
    }

    private Android android ;
    private Slamware slamware ;


    public Android getAndroid() {
        return android;
    }

    public void setAndroid(Android android) {
        this.android = android;
    }

    public Slamware getSlamware() {
        return slamware;
    }

    public void setSlamware(Slamware slamware) {
        this.slamware = slamware;
    }

    public void setShowAndroid(boolean showAndroid) {

        android = showAndroid? Android.START : Android.STOP ;
    }

    public void setShowSlamware(boolean showSlamware) {

        slamware = showSlamware? Slamware.START : Slamware.STOP ;
    }
}
