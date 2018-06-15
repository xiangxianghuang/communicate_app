package com.yongyida.robot.model.caro.led;

import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.breathled.LedHelper;

/**
 * Created by HuangXiangXiang on 2017/12/16.
 */
public class LedStatueControl {

    private final static String TAG = LogHelper.__FILE__() ;

    private LedHelper mLedHelper = LedHelper.getInstance();

//    public SendResponse onControl(LedControl ledControl){
//
//        /**位置*/
////        int position = ledControl.getPosition() ;
////        boolean isContain = LedHelper.getPosition(position);
////        if (!isContain){
////
////            return null;
////        }
//
//        /**开关*/
//        LedControl.Power power = ledControl.getPower();
//        if(power != null){
//
//            if(LedHelper.isPower(power)){
//
//                mLedHelper.openLed() ;
//            }else {
//
//                mLedHelper.closeLed() ;
//            }
//        }
//
//        /**亮度值*/
//        LedControl.Brightness brightness = ledControl.getBrightness() ;
//        if(brightness != null){
//
//            mLedHelper.setLedBrightness(brightness.getValue()) ;
//        }
//
//
//        /**颜色值(0x000000-0xFFFFFF)*/
//        LedControl.Color color = ledControl.getColor();
//        if(color != null){
//
//            mLedHelper.setLedColor(color.getRed(), color.getGreen(), color.getBlue()) ;
//        }
//
//        /**效果*/
//        LedControl.Effect effect = ledControl.getEffect();
//        if(effect != null){
//
//            //暂时不支持效果
//        }
//
//        return null ;
//    }

}
