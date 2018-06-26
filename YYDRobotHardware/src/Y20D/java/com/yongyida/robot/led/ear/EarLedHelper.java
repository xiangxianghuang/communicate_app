package com.yongyida.robot.led.ear;

import com.yongyida.robot.communicate.app.utils.LogHelper;

/**
 * Created by HuangXiangXiang on 2017/12/14.
 */
public class EarLedHelper {

    private static final String TAG = EarLedHelper.class.getSimpleName() ;

    public static void closeLed(){

        LogHelper.i(TAG,LogHelper.__TAG__());

        JniHead.close_all_led() ;
    }

    public static void openWhiteLed(){

        LogHelper.i(TAG,LogHelper.__TAG__());

        JniHead.close_all_led() ;//呼吸灯不能在亮的时候打开白色常亮
        JniHead.open_all_led() ;
    }

    public static void openBreathLed(){

        LogHelper.i(TAG,LogHelper.__TAG__());

        JniHead.led_breath_mode() ;
    }

    public static void openHorseRaceLed(){

        LogHelper.i(TAG,LogHelper.__TAG__());

        JniHead.pao_ma_led() ;
    }


}
