package com.yongyida.robot.communicate.app.hardware.led.send.data;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 *
 * 对于某个具体呼吸灯状态
 *  位置
 *  亮度
 *  颜色
 *  效果
 *  效果参数
 */
public class LedSendControl extends BaseLedSendControl {

    /**位置*/
    private Position position = Position.ALL ;

    /**亮度值（0-100）*/
    private Brightness brightness ;

    /**颜色值(0x000000-0xFFFFFF)*/
    private Color color ;

    /**效果*/
    private Effect effect ;

    private int effectValue ;


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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

    public void setColor(int colorValue) {

        if(color == null){

            color = new Color() ;
        }
        color.setColorValue(colorValue);

    }
    public void setColor(int red, int green, int blue) {

        if(color == null){

            color = new Color() ;
        }
        color.setColor(red, green, blue);
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

    public int getEffectValue() {
        return effectValue;
    }

    public void setEffectValue(int effectValue) {
        this.effectValue = effectValue;
    }

    public enum Position{

        ALL(0xFFFFFFFF),        //全部位置

        LEFT_EAR(0x01),         // 左耳
        RIGHT_EAR(0x02),        // 右耳
        EAR(0x03),              // 双耳

        LEFT_ARM(0x04),         // 左臂
        RIGHT_ARM(0x05),        // 右臂
        ARM(0x06),              // 手臂

        LEFT_FOOT(0x07),        // 左脚
        RIGHT_FOOT(0x08),       // 右脚
        FOOT(0x09),             // 脚

        LEFT_EYE(0x0A) ,        // 左眼
        RIGHT_EYE(0x0B),        // 右眼
        EYE(0x0C),              // 眼睛

        CHEST(0x0D),            // 胸前

        PAUNCH(0x0E);           // 肚子


        public int value;
        Position(int value){

            this.value = value ;
        }

        public int getValue() {
            return value;
        }
    }

    public static class Brightness{

        private int value ;

        public static Brightness value(int value){

            Brightness brightness = new Brightness() ;
            brightness.setValue(value);

            return brightness ;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class Color{

        private int red ;
        private int green ;
        private int blue ;

        public static Color value(int red, int green , int blue){

            Color color = new Color() ;
            color.setColor(red, green, blue);

            return color ;
        }

        public static Color value(int colorValue){

            Color color = new Color() ;
           color.setColorValue(colorValue);

            return color ;
        }

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

        public void setColorValue(int colorValue){

            this.red = (colorValue >> 16) & 0xFF ;
            this.green = (colorValue >> 8) & 0xFF ;
            this.blue = colorValue & 0xFF ;
        }

        public int getColor() {

            return  (red&0xFF) << 16 | (green&0xFF) << 8 | blue ;
        }
    }

    public enum Effect{

//        LED_NORMAL ,            //常亮
        LED_ON ,            //常亮
        LED_OFF ,           //常灭
        BREATH ,            //呼吸灯
        BREATH_LOW ,        //呼吸灯(慢)
        BREATH_MIDDLE ,     //呼吸灯(中)
        BREATH_FAST ,       //呼吸灯(快)
        HORSE_RACE          //跑马灯
    }


}
