package com.yongyida.robot.communicate.app.hardware.vision;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class VersionData {

    public static final String POSTION_LEFT             = "left" ;
    public static final String POSTION_MIDDLE           = "middle" ;
    public static final String POSTION_RIGHT            = "right" ;

    //障碍位置
    private String position ;

    //障碍距离(单位cm)
    private int distance ;


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
