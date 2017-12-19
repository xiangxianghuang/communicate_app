package com.yongyida.robot.model.caro.led;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.breathled.LedControl;
import com.yongyida.robot.breathled.SelfLedScene;
import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;

/**
 * Created by HuangXiangXiang on 2017/12/16.
 */
public class LedSceneControl {

    private final static String TAG = LogHelper.__FILE__() ;

    private LedControl mLedControl = LedControl.getInstance() ;

    public BaseResponse onControl(LedScene ledScene){

        LogHelper.i(TAG , LogHelper.__TAG__() + ledScene.name());

        switch (ledScene){
            case OFFLINE:

                mLedControl.startLedEffect(SelfLedScene.LED_OFFLINE) ;
                break;
            case SET_WIFI:

                mLedControl.startLedEffect(SelfLedScene.LED_OFFLINE) ;
                break;
            case READY:

                mLedControl.startLedEffect(SelfLedScene.LED_OFFLINE) ;
                break;
            case LISTEN:

                mLedControl.startLedEffect(SelfLedScene.LED_OFFLINE) ;
                break;
            case SECURITY:

                mLedControl.startLedEffect(SelfLedScene.LED_SECURITY) ;
                break;
            case INCOMING:

                mLedControl.startLedEffect(SelfLedScene.LED_INCOMING) ;
                break;
        }
        return  null ;
    }

}
