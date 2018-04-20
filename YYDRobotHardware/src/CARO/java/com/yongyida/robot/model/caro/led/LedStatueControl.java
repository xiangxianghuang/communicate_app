package com.yongyida.robot.model.caro.led;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.breathled.LedHelper;
import com.yongyida.robot.communicate.app.hardware.led.data.LedHandle;

/**
 * Created by HuangXiangXiang on 2017/12/16.
 */
public class LedStatueControl {

    private final static String TAG = LogHelper.__FILE__() ;

    private LedHelper mLedHelper = LedHelper.getInstance();

    public BaseResponse onControl(LedHandle ledHandle){

        /**位置*/
//        int position = ledHandle.getPosition() ;
//        boolean isContain = LedHelper.getPosition(position);
//        if (!isContain){
//
//            return null;
//        }

        /**开关*/
        LedHandle.Power power = ledHandle.getPower();
        if(power != null){

            if(LedHelper.isPower(power)){

                mLedHelper.openLed() ;
            }else {

                mLedHelper.closeLed() ;
            }
        }

        /**亮度值*/
        LedHandle.Brightness brightness = ledHandle.getBrightness() ;
        if(brightness != null){

            mLedHelper.setLedBrightness(brightness.getValue()) ;
        }


        /**颜色值(0x000000-0xFFFFFF)*/
        LedHandle.Color color = ledHandle.getColor();
        if(color != null){

            mLedHelper.setLedColor(color.getRed(), color.getGreen(), color.getBlue()) ;
        }

        /**效果*/
        LedHandle.Effect effect = ledHandle.getEffect();
        if(effect != null){

            //暂时不支持效果
        }

        return null ;
    }

}
