package com.yongyida.robot.model.caro.led;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.breathled.LedControl;
import com.yongyida.robot.communicate.app.hardware.led.data.LedStatue;

/**
 * Created by HuangXiangXiang on 2017/12/16.
 */
public class LedStatueControl {

    private final static String TAG = LogHelper.__FILE__() ;

    private LedControl mLedControl = LedControl.getInstance();

    public BaseResponse onControl(LedStatue ledStatue){

        /**位置*/
//        int position = ledStatue.getPosition() ;
//        boolean isContain = LedControl.getPosition(position);
//        if (!isContain){
//
//            return null;
//        }

        /**开关*/
        LedStatue.Power power = ledStatue.getPower();
        if(power != null){

            if(LedControl.isPower(power)){

                mLedControl.openLed() ;
            }else {

                mLedControl.closeLed() ;
            }
        }

        /**亮度值*/
        LedStatue.Brightness brightness = ledStatue.getBrightness() ;
        if(brightness != null){

            mLedControl.setLedBrightness(brightness.getBrightness()) ;
        }


        /**颜色值(0x000000-0xFFFFFF)*/
        LedStatue.Color color = ledStatue.getColor();
        if(color != null){

            mLedControl.setLedColor(color.getRed(), color.getGreen(), color.getBlue()) ;
        }

        /**效果*/
        LedStatue.Effect effect = ledStatue.getEffect();
        if(effect != null){

            //暂时不支持效果
        }

        return null ;
    }

}
