package com.yongyida.robot.model.y20.led;


import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.led.LedSendHandlers;
import com.yongyida.robot.communicate.app.utils.LogHelper;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y20LedSendHandlers extends LedSendHandlers {

    private final static String TAG = LogHelper.__FILE__() ;

    public Y20LedSendHandlers(Context context) {
        super(context);
    }


//    @Override
//    public SendResponse onHandler(LedSend ledSend, IResponseListener responseListener) {
//
////         LedScene ledScene = ledSend.getLedScene() ;
////        if(ledScene != null){
////
////            return onControl(ledScene) ;
////        }
//
//        return null;
//    }






    // 开机中
    //      胸口灯：蓝色呼吸，最弱到最亮，呼吸频率2.5秒。
    //      耳朵灯：白色跑马灯，2.5秒一圈

    private void poweringOn() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;
    }

    // 开机完毕，
    //      胸口灯蓝色灭。
    //      耳朵灯灭。
    private void poweredOn() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;
    }

    //唤醒
    //      胸口灯：蓝色常亮。监听完毕没 有任何语音交互，胸口灯灭。
    //      耳朵灯：跑马灯一圈。白色常亮。监听完毕没有任何语音交互，耳朵灯灭。
    private void wakeUp() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;
    }

    //监听
    //      胸口灯：蓝色常亮。
    //      耳朵灯：白色常亮。
    private void listening() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;
    }

    //识别
    //      胸口灯：蓝色呼吸。
    //      耳朵灯：白色旋转。
    private void recognition() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;
    }

    //对话
    //      胸口灯：说话时蓝色常亮，说完灯灭。
    //      耳朵灯：说话时白色常亮，说完灯灭。
    private void dialog() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;
    }

    //休眠
    //      胸口灯：绿色呼吸。
    //      耳朵灯：白色灭。
    private void sleep() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;
    }

    //关机
    //      胸口灯、
    //      耳朵灯灭。
    private void powerOff() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;
    }

    //联网
    //  未联网
    //      胸口灯红色常亮
    //      耳朵灯灭
    //
    //  联网成功
    //      胸口灯绿色常亮
    //      耳朵灯灭
    private void connectNet() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;
    }

    //  电量低充电中：
    //      胸口灯红色呼吸
    //      耳朵灯灭。
    //  满电
    //      胸口灯绿色常亮
    //      耳朵灯灭。充电灯的优先级低于语音交互的灯的定义。
    private void lowPowerOrCharging() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;
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
    }


    //待机
    //      胸口灯灭
    //      耳朵灯灭
    private void standBy() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;

    }



}
