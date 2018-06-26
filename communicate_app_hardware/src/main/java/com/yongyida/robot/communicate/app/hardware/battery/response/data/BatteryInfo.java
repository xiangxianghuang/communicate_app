package com.yongyida.robot.communicate.app.hardware.battery.response.data;

import com.yongyida.robot.communicate.app.common.response.BaseResponseControl;

import java.util.Locale;

/**
 * Created by HuangXiangXiang on 2018/2/24.
 */
public class BatteryInfo extends BaseResponseControl {

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


    public boolean setBatteryInfo (BatteryInfo batteryInfo){

        boolean isChange = false ;

        if(this.isCharging != batteryInfo.isCharging ){
            this.isCharging = batteryInfo.isCharging ;

            isChange = true ;
        }

        if(this.level != batteryInfo.level ){
            this.level = batteryInfo.level ;

            isChange = true ;
        }

        if(this.state != batteryInfo.state ){
            this.state = batteryInfo.state ;

            isChange = true ;
        }

        return isChange ;
    }


    @Override
    public String toString() {

        return String.format(Locale.CHINA, "isCharging : %s, level : %d, state : %d",isCharging, level, state );
    }
}
