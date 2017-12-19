package com.yongyida.robot.model.y20.led;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;
import com.yongyida.robot.led.ear.ChestLedHelper;
import com.yongyida.robot.led.ear.EarLedHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by HuangXiangXiang on 2017/12/16.
 */
public class LedSceneControl {

    private final static String TAG = LogHelper.__FILE__() ;

    public BaseResponse onControl(LedScene ledScene){

        LogHelper.i(TAG , LogHelper.__TAG__() + ledScene.name());

        stopTimer();

        switch (ledScene){
            case POWER_ON:

                powerOn() ;
                break;
            case WAKE_UP:

                wakeUp() ;
                break;
            case LISTEN:

                listen() ;
                break;
            case ANALYSE:

                analyse() ;
                break;
            case TALK:

                talk() ;
                break;
            case SLEEP:

                sleep() ;
                break;
            case POWER_OFF:

                powerOff() ;
                break;
            case CONNECT_NET_NONE:

                connectNetNone() ;
                break;
            case CONNECT_NET_SUCCESS:

                connectNetSuccess() ;
                break;
            case LOW_POWER:
            case CHARGING:

                lowPowerOrCharging() ;
                break;
            case PLAY_MEDIA:
            case PLAY_TTS:

                play() ;
                break;
            case NORMAL:

                normal();
                break;

        }


        return  null ;
    }


    // 开机中(有系统层完成)
    //      胸口灯：蓝色呼吸，最弱到最亮，呼吸频率2.5秒。
    //      耳朵灯：白色跑马灯，2.5秒一圈
    private void powerOn() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.openLed(ChestLedHelper.COLOR_BLUE,ChestLedHelper.FREQ_MIDDLE);

        EarLedHelper.openHorseRaceLed();
    }


    //唤醒
    //      胸口灯：蓝色常亮。
    //      耳朵灯：跑马灯一圈，白色常亮。
    private void wakeUp() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.openLed(ChestLedHelper.COLOR_BLUE,ChestLedHelper.FREQ_CONST);

        EarLedHelper.openHorseRaceLed();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        EarLedHelper.openWhiteLed();


//        startWakeUpTimer() ;

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



    //监听
    //      胸口灯：蓝色常亮。
    //      耳朵灯：白色常亮。
    private void listen() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.openLed(ChestLedHelper.COLOR_BLUE,ChestLedHelper.FREQ_CONST);

        EarLedHelper.openWhiteLed();
    }

    //识别
    //      胸口灯：蓝色呼吸。
    //      耳朵灯：白色旋转。
    private void analyse() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.openLed(ChestLedHelper.COLOR_BLUE,ChestLedHelper.FREQ_MIDDLE);

        EarLedHelper.openHorseRaceLed();
    }

    //对话
    //      胸口灯：说话时蓝色常亮。
    //      耳朵灯：说话时白色常亮。
    private void talk() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.openLed(ChestLedHelper.COLOR_BLUE,ChestLedHelper.FREQ_CONST);

        EarLedHelper.openWhiteLed();
    }

    //休眠
    //      胸口灯 绿色呼吸
    //      耳朵灯 灭
    private void sleep() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.openLed(ChestLedHelper.COLOR_GREEN,ChestLedHelper.FREQ_MIDDLE);

        EarLedHelper.closeLed();
    }

    //关机
    //      胸口灯 灭
    //      耳朵灯 灭
    private void powerOff() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.closeLed();
        EarLedHelper.closeLed();
    }

    //联网
    //  未联网
    //      胸口灯 红色常亮
    //      耳朵灯 灭

    private void connectNetNone() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.openLed(ChestLedHelper.COLOR_RED,ChestLedHelper.FREQ_CONST);

        EarLedHelper.closeLed();
    }


    //联网
    //  联网成功
    //      胸口灯 绿色常亮
    //      耳朵灯 灭
    private void connectNetSuccess() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.openLed(ChestLedHelper.COLOR_GREEN,ChestLedHelper.FREQ_CONST);

        EarLedHelper.closeLed();
    }

    //  电量低充电中：
    //      胸口灯红色呼吸
    //      耳朵灯灭。
    //  满电
    //      胸口灯绿色常亮
    //      耳朵灯灭。充电灯的优先级低于语音交互的灯的定义。
    private void lowPowerOrCharging() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.openLed(ChestLedHelper.COLOR_RED, ChestLedHelper.FREQ_MIDDLE);

        EarLedHelper.closeLed();
    }



    //播放内容：
    //  屏幕在亮的时候，
    //      胸口灯灭
    //      耳朵灯灭。
    //
    //  屏幕熄灭时，
    //      胸口灯绿色呼吸，
    //      耳朵灯依然熄灭。
    private void play() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;


        ChestLedHelper.closeLed();

        EarLedHelper.closeLed();
    }


    //待机
    //      胸口灯灭
    //      耳朵灯灭
    private void normal() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        ChestLedHelper.closeLed();
        EarLedHelper.closeLed();
    }


}
