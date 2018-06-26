package com.yongyida.robot.model.y20.led;

import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.led.ear.ChestLedHelper;
import com.yongyida.robot.led.ear.EarLedHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by HuangXiangXiang on 2017/12/16.
 */
public class LedSceneControl {

    private final static String TAG = LogHelper.__FILE__() ;

//    public SendResponse onControl(LedScene ledScene){
//
//        LogHelper.i(TAG , LogHelper.__TAG__() + ledScene.name());
//
//        stopTimer();
//
//        switch (ledScene){
//
//            case WAKE_UP:
//            case LISTEN:
//            case ANALYSE:
//
//                wakeUp() ;
//                break;
//
//            case TALK:
//
//                talk() ;
//                break;
//            case POWER_OFF:
//
//                powerOff() ;
//                break;
//            case NORMAL_SCREEN_ON:
//
//                normalScreenOn() ;
//                break;
//            case NORMAL_SCREEN_OFF:
//
//                normalScreenOFF() ;
//                break;
//            case LOW_POWER:
//            case CHARGING:
//
//                lowPowerOrCharging() ;
//                break;
//            case FULL_POWER:
//
//                fullPower() ;
//                break;
//
//        }
//
//
//        return  null ;
//    }
//



    //唤醒/监听/识别
    //      胸口灯：蓝色呼吸。
    //      耳朵灯：跑马灯一圈，白色常亮。
    private void wakeUp() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.openLed(ChestLedHelper.COLOR_BLUE,ChestLedHelper.FREQ_MIDDLE);

        EarLedHelper.openHorseRaceLed();

//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        EarLedHelper.openWhiteLed();


        startWakeUpTimer() ;

    }

    private Timer mTimer ;
    private TimerTask mTask ;

    private void startWakeUpTimer(){

        mTimer = new Timer() ;
        mTask = new TimerTask() {
            @Override
            public void run() {

                EarLedHelper.openWhiteLed();
            }
        };
        mTimer.schedule(mTask,500);
    }

    private void stopTimer(){

        if(mTask != null){
            mTask.cancel() ;
            mTask = null;
        }

        if(mTimer != null){
            mTimer.cancel() ;
            mTimer = null;
        }
    }




    //对话
    //      胸口灯 说话时蓝色呼吸
    //      耳朵灯 说话时白色常亮
    private void talk() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.openLed(ChestLedHelper.COLOR_BLUE,ChestLedHelper.FREQ_MIDDLE);
        EarLedHelper.openWhiteLed();
    }


    //关机
    //      胸口灯 灭
    //      耳朵灯 灭
    private void powerOff() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.closeLed();
        EarLedHelper.closeLed();
    }

    //常态（亮屏）
    //  胸口灯 灭
    //  耳朵灯 灭

    private void normalScreenOn() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.closeLed();
        EarLedHelper.closeLed();
    }


    //常态（灭屏）
    //  胸口灯 绿色呼吸
    //  耳朵灯 灭
    private void normalScreenOFF() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.openLed(ChestLedHelper.COLOR_GREEN,ChestLedHelper.FREQ_MIDDLE);
        EarLedHelper.closeLed();
    }

    //  电量低充电中：
    //      胸口灯 红色呼吸
    //      耳朵灯 灭
    private void lowPowerOrCharging() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.openLed(ChestLedHelper.COLOR_RED, ChestLedHelper.FREQ_MIDDLE);
        EarLedHelper.closeLed();
    }


    //满电
    //  胸口灯 绿色常亮
    //  耳朵灯 灭
    private void fullPower() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.openLed(ChestLedHelper.COLOR_GREEN, ChestLedHelper.FREQ_CONST);
        EarLedHelper.closeLed();
    }




}
