package com.yongyida.robot.communicate.app.hardware.battery.data;

/**
 * Created by HuangXiangXiang on 2018/2/24.
 */
public class BatteryInfo {

    private boolean isCharging = false ;    //是否充电
    private int level = -1 ;                //电量
    private int state ;                     //充电码（备用，不同机型不相同）


    public boolean isCharging() {
        return isCharging;
    }

    public void setCharging(boolean charging) {
        isCharging = charging;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
