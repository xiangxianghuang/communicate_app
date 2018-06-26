package com.yongyida.robot.model.y20.led;

import com.yongyida.robot.communicate.app.utils.LogHelper;

/**
 * Created by HuangXiangXiang on 2017/12/16.
 */
public class LedStatueControl {

    private final static String TAG = LogHelper.__FILE__() ;

//    public SendResponse onControl(LedControl ledControl){
//
//        /**位置*/
//        int position = ledControl.getPosition() ;
//        String positionString = BreathLedHelper.getPosition(position);
//        if (positionString == null){
//
//            return null;
//        }
//
//        /**开关*/
//        LedControl.Power power = ledControl.getPower();
//        String powerString = BreathLedHelper.getPower(power) ;
//        if(powerString != null){
//
//            BreathLedHelper.setOnOff(positionString, powerString);
//        }
//
//        /**颜色值(0x000000-0xFFFFFF)*/
//        LedControl.Color color = ledControl.getColor();
//        String colorString = BreathLedHelper.getColor(color) ;
//        if(colorString != null){
//
//            BreathLedHelper.setColor(positionString, colorString);
//        }
//
//        /**效果*/
//        LedControl.Effect effect = ledControl.getEffect();
//        String effectString = BreathLedHelper.getEffect(effect) ;
//        if(effectString != null){
//
//            BreathLedHelper.setFreq(positionString, effectString);
//        }
//
//        return null ;
//    }

}
