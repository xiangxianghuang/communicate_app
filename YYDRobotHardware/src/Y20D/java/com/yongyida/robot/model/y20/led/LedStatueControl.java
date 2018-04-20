package com.yongyida.robot.model.y20.led;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.breathled.utils.BreathLedHelper;
import com.yongyida.robot.communicate.app.hardware.led.data.LedHandle;

/**
 * Created by HuangXiangXiang on 2017/12/16.
 */
public class LedStatueControl {

    private final static String TAG = LogHelper.__FILE__() ;

    public BaseResponse onControl(LedHandle ledHandle){

        /**位置*/
        int position = ledHandle.getPosition() ;
        String positionString = BreathLedHelper.getPosition(position);
        if (positionString == null){

            return null;
        }

        /**开关*/
        LedHandle.Power power = ledHandle.getPower();
        String powerString = BreathLedHelper.getPower(power) ;
        if(powerString != null){

            BreathLedHelper.setOnOff(positionString, powerString);
        }

        /**颜色值(0x000000-0xFFFFFF)*/
        LedHandle.Color color = ledHandle.getColor();
        String colorString = BreathLedHelper.getColor(color) ;
        if(colorString != null){

            BreathLedHelper.setColor(positionString, colorString);
        }

        /**效果*/
        LedHandle.Effect effect = ledHandle.getEffect();
        String effectString = BreathLedHelper.getEffect(effect) ;
        if(effectString != null){

            BreathLedHelper.setFreq(positionString, effectString);
        }

        return null ;
    }

}
