package com.yongyida.robot.hardware.led;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 * 呼吸灯
 *
 */
public class BreathLed {

    //呼吸灯位置
    public static final int POSITION_ALL                    = 0xFFFFFFFF ;  //全部位置
    public static final int POSITION_LEFT_EAR               = 0x00000001 ;  //左耳短
    public static final int POSITION_RIGHT_EAR              = 0x00000002 ;  //右耳朵
    public static final int POSITION_CHEST                  = 0x00000004 ;  //胸部
    public static final int POSITION_PAUNCH                 = 0x00000008 ;  //肚子


    public static final String EFFECT_NORMAL                = "normal" ;  //普通效果

    //位置
    private int position = POSITION_ALL ;

    //是否打开
    private boolean isOn = true ;

    //效果类型
    private String effect = EFFECT_NORMAL ;

    //亮度值（0-100）
    private int brightness ;

    //颜色值(0x000000-0xFFFFFF)
    private int color ;

    //冷光值（0-100）
    private int cold ;

    //暖光值（0-100）
    private int warm ;


}
