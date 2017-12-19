package com.yongyida.robot.communicate.app.hardware.led.data;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 *
 * 对于某个具体呼吸灯状态
 *  位置
 *  开关
 *  亮度
 *  颜色
 *  效果
 */
public class LedStatue {

    //呼吸灯位置
    public static final int POSITION_ALL                    = 0xFFFFFFFF ;  //全部位置
    public static final int POSITION_LEFT_EAR               = 0x00000001 ;  //左耳短
    public static final int POSITION_RIGHT_EAR              = 0x00000002 ;  //右耳朵
    public static final int POSITION_CHEST                  = 0x00000004 ;  //胸部
    public static final int POSITION_PAUNCH                 = 0x00000008 ;  //肚子


    /**位置*/
    private int position = POSITION_ALL ;

    /**开关*/
    private Power power ;

    /**亮度值（0-100）*/
    private Brightness brightness ;

    /**颜色值(0x000000-0xFFFFFF)*/
    private Color color ;

    /**效果*/
    private Effect effect ;


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    public Brightness getBrightness() {
        return brightness;
    }

    public void setBrightness(Brightness brightness) {
        this.brightness = brightness;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public enum Power{

        POWER_ON ,  // 开灯
        POWER_OFF   // 关灯
    }

    public static class Brightness{

        private int brightness ;

        public int getBrightness() {
            return brightness;
        }

        public void setBrightness(int brightness) {
            this.brightness = brightness;
        }
    }

    public static class Color{

        private int red ;
        private int green ;
        private int blue ;

        public int getRed() {
            return red;
        }

        public void setRed(int red) {
            this.red = red;
        }

        public int getGreen() {
            return green;
        }

        public void setGreen(int green) {
            this.green = green;
        }

        public int getBlue() {
            return blue;
        }

        public void setBlue(int blue) {
            this.blue = blue;
        }

        public void setColor(int red, int green , int blue){

            this.red = red ;
            this.green = green ;
            this.blue = blue ;
        }

        public int getColor() {

            return  (red&0xFF) << 16 | (green&0xFF) << 8 | blue ;
        }

    }



    public enum Effect{

        NORMAL ,        //常亮的状态
        BREATH_LOW ,    //呼吸灯(慢)
        BREATH_MIDDLE , //呼吸灯(中)
        BREATH_FAST ,   //呼吸灯(快)
        HORSE_RACE      //跑马灯

    }


}
