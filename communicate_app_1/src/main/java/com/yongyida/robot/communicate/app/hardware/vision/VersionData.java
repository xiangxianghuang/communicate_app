package com.yongyida.robot.communicate.app.hardware.vision;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class VersionData {

    public enum Position{

        LEFT,   //左
        MIDDLE, //中
        RIGHT   //右
    }

    //障碍位置
    private Position position = Position.LEFT ;

    //障碍距离(单位cm)
    private int distance ;


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "障碍物的位置 :" + position  + ",距离 : " + distance + "cm." ;
    }
}
