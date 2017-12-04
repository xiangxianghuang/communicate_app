package com.yongyida.robot.communicate.app.hardware.led;

import com.yongyida.robot.communicate.app.common.send.BaseSend;

import java.util.Locale;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 * 呼吸灯
 *
 */
public class LedStatue {

    //呼吸灯位置
    public static final int POSITION_ALL                    = 0xFFFFFFFF ;  //全部位置
    public static final int POSITION_LEFT_EAR               = 0x00000001 ;  //左耳短
    public static final int POSITION_RIGHT_EAR              = 0x00000002 ;  //右耳朵
    public static final int POSITION_CHEST                  = 0x00000004 ;  //胸部
    public static final int POSITION_PAUNCH                 = 0x00000008 ;  //肚子

     LedStatue(){}


    public static final String EFFECT_NORMAL                = "normal" ;  //普通效果

    //位置
    private int position = POSITION_ALL ;

    //是否打开
    private boolean isTurnOn = true ;

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




    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isTurnOn() {
        return isTurnOn;
    }

    public void setTurnOn(boolean isTurnOn) {
        isTurnOn = isTurnOn;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getCold() {
        return cold;
    }

    public void setCold(int cold) {
        this.cold = cold;
    }

    public int getWarm() {
        return warm;
    }

    public void setWarm(int warm) {
        this.warm = warm;
    }

    @Override
    public String toString() {

        return hashCode() + "--> "+ String.format(Locale.CHINA, "position : %d, isTurnOn :%s," +
                "effect : %s, brightness : %x," +
                "color : %x, cold : %d," +
                "warm : %d" , position,isTurnOn,effect,brightness,color,cold,warm);
    }
}
