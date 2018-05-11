package com.yongyida.robot.breathled;

import com.yongyida.robot.communicate.app.hardware.led.data.LedControl;

import java.util.ArrayList;

/**
 * Created by HuangXiangXiang on 2017/10/25.
 */
public class LedHelper {

    private boolean isPower ;

    private int brightness = 255 ;
    private int red = 255 ;
    private int blue = 255 ;
    private int green = 255 ;

    private LedHelper() {

    }

    private static LedHelper mLedHelper;
    public static LedHelper getInstance(){

        if(mLedHelper == null){

            mLedHelper = new LedHelper() ;
        }

        return mLedHelper;
    }

    public int closeLed() {
//        return JniHead.close_all_led() ;

        isPower = false ;
        return setLedColor(red,green,blue) ;
    }

    public int openLed() {

        isPower = true ;
        return setLedColor(red,green,blue) ;
    }


    public int setLedBrightness(int brightness) {

        if(brightness < 0){

            this.brightness = 0 ;

        }else if(brightness > 100){

            this.brightness  = 255 ;

        }else{

            this.brightness  = brightness*254/100 + 1 ;
        }

        return setColor();
    }

    public int setLedColor(int red, int green, int blue) {

        stopLedEffect();
        return setLedColor2(red, green, blue) ;
    }

    private int setLedColor2(int red, int green, int blue) {

        if(red < 0){

            this.red = 0 ;

        }else if(red > 255){

            this.red = 255 ;

        }else{

            this.red = red ;
        }


        if(green < 0){

            this.green = 0 ;

        }else if(green > 255){

            this.green = 255 ;

        }else{

            this.green = green ;
        }


        if(blue < 0){

            this.blue = 0 ;

        }else if(blue > 255){

            this.blue = 255 ;

        }else{

            this.blue = blue ;
        }


        return setColor() ;
    }


    public int startLedEffect(SelfLedScene selfLedScene) {

        ArrayList<SelfLedScene.LedEffect> ledEffects = selfLedScene.getLedEffects() ;

        stopLedEffect() ;

        if(ledEffects.size() == 1){

            SelfLedScene.LedEffect ledEffect = ledEffects.get(0) ;
            setLedColor2(ledEffect.getRed(), ledEffect.getGreen(), ledEffect.getBlue()) ;

        }else{

            mLedSceneControlThread = new LedSceneControlThread(ledEffects) ;
            mLedSceneControlThread.start();
        }


        return 0;
    }

    private void stopLedEffect() {

        if(mLedSceneControlThread != null && mLedSceneControlThread.isRun){

            mLedSceneControlThread.stopRun() ;
            mLedSceneControlThread = null ;
        }
    }


    private LedSceneControlThread mLedSceneControlThread ;

    private class LedSceneControlThread extends Thread {

        private ArrayList<SelfLedScene.LedEffect> ledEffects ;
        private boolean isRun ;

        public LedSceneControlThread(ArrayList<SelfLedScene.LedEffect> ledEffects){

            this.ledEffects = ledEffects;
            this.isRun = true ;
        }

        @Override
        public void run() {

            int index = 0 ;
            final int size = ledEffects.size() ;

            while (isRun){

                SelfLedScene.LedEffect ledEffect = ledEffects.get(index) ;
                setLedColor2(ledEffect.getRed(), ledEffect.getGreen(), ledEffect.getBlue()) ;

                if(++index>=size){

                    index = 0;
                }

                try {
                    Thread.sleep(ledEffect.getTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        private void stopRun(){

            isRun = false ;
        }
    }



    private int setColor(){

        if (isPower){

            int newRed = red * brightness / 255;
            int newGreen = green * brightness / 255 ;
            int newBlue = blue * brightness / 255 ;

            return JniHead.set_led_color_and_current(newRed, newGreen, newBlue) ;
        }else {

            return JniHead.set_led_color_and_current(0, 0, 0) ;
        }
    }

    public static boolean getPosition(int position){

        if(position == LedControl.POSITION_ALL){//全部

            return true ;

        }else if(((position & LedControl.POSITION_LEFT_EAR) == LedControl.POSITION_LEFT_EAR) || //左耳
                ((position & LedControl.POSITION_RIGHT_EAR) == LedControl.POSITION_RIGHT_EAR)){//右耳

            return true ;
        }

        return false ;
    }

    public static boolean isPower(LedControl.Power power){

        return power == LedControl.Power.POWER_ON ;
    }


}
