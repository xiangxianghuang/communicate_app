package com.yongyida.robot.led.ear;

import com.yongyida.robot.breathled.utils.BreathLedHelper;

/**
 * Created by HuangXiangXiang on 2017/12/14.
 */
public class ChestLedHelper extends BreathLedHelper{

    public static void closeLed(){

        BreathLedHelper.closeLed(POSITION_CHEST) ;
    }

    public static void openLed(String color,String freq){

        BreathLedHelper.openLed(POSITION_CHEST,color,freq) ;
    }

}
