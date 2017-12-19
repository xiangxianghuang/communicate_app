package com.yongyida.robot.communicate.app.hardware.led.data;

/**
 * Created by HuangXiangXiang on 2017/12/12.
 * led场景(由多个灯组成的场景 )
 */
public enum LedScene {

    POWER_ON ,              //开机
    WAKE_UP ,               //唤醒
    LISTEN ,                //监听
    ANALYSE ,               //识别、分析
    TALK ,                  //对话
    SLEEP ,                 //休眠
    POWER_OFF ,             //关机
    CONNECT_NET_NONE ,      //未联网
    CONNECT_NET_SUCCESS ,   //联网成功
    LOW_POWER ,             //电量低
    CHARGING ,              //充电中
    PLAY_MEDIA ,            //播放音频
    PLAY_TTS ,              //播放TTS
    NORMAL  ,               //待机、常态
    OFFLINE ,               //断网
    SET_WIFI ,              //设置WIFI
    READY ,                 //准备
    SECURITY ,              //保密模式
    INCOMING,               //来电
}
