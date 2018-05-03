package com.yongyida.robot.breathled.utils;

import android.graphics.Color;

import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.led.data.LedControl;

import java.util.HashSet;

/**
 * Created by HuangXiangXiang on 2017/12/14.
 */
public class BreathLedHelper {

    private static final String TAG = BreathLedHelper.class.getSimpleName() ;

    public static final String POSITION_EAR         = "ear" ;
    public static final String POSITION_CHEST       = "chest" ;
    public static final String POSITION_ALL         = "all" ;

    public static final String FUNCTION_ON          = "on" ;
    public static final String FUNCTION_OFF         = "off" ;

    public static final String COLOR_RED            = "Red" ;
    public static final String COLOR_GREEN          = "Green" ;
    public static final String COLOR_BLUE           = "Blue" ;
    public static final String COLOR_GREEN_BLUE     = "GreenBlue" ;
    public static final String COLOR_RED_BLUE       = "RedBlue" ;
    public static final String COLOR_RED_GREEN      = "RedGreen" ;
    public static final String COLOR_WHITE          = "White" ;

    private static final HashSet<String> COLORS = new HashSet<>();
    static {

        COLORS.add(COLOR_RED) ;
        COLORS.add(COLOR_GREEN) ;
        COLORS.add(COLOR_BLUE) ;
        COLORS.add(COLOR_GREEN_BLUE) ;
        COLORS.add(COLOR_RED_BLUE) ;
        COLORS.add(COLOR_RED_GREEN) ;
        COLORS.add(COLOR_WHITE) ;
    }

    public static final String FREQ_LOW             = "Low" ;
    public static final String FREQ_MIDDLE          = "Middle" ;
    public static final String FREQ_HIGH            = "High" ;
    public static final String FREQ_CONST           = "Const" ;


    public static void closeLed(String position){

        LogHelper.i(TAG , LogHelper.__TAG__() + ", position : " + position);
        BreathLed.setOnoff(position,FUNCTION_OFF) ;
    }

    public static void openLed(String position,String color,String freq){

        LogHelper.i(TAG , LogHelper.__TAG__() + ", position : " + position);
//        BreathLed.setOnoff(position,FUNCTION_ON) ;
        BreathLed.setColor(position, color);
        BreathLed.setFreq(position, freq) ;
    }

    public static void openLed(String position){

        LogHelper.i(TAG , LogHelper.__TAG__() + ", position : " + position);

        BreathLed.setOnoff(position,FUNCTION_ON) ;
    }


    public static void setOnOff(String position,String power){

        LogHelper.i(TAG , LogHelper.__TAG__() + ", position : " + position);

        BreathLed.setOnoff(position,power);
    }

    public static void setColor(String position,String color){

        LogHelper.i(TAG , LogHelper.__TAG__() + ", position : " + position);

        BreathLed.setColor(position, color);
    }

    public static void setFreq(String position,String freq){

        LogHelper.i(TAG , LogHelper.__TAG__() + ", position : " + position);

        BreathLed.setFreq(position, freq) ;
    }

    public static String getPosition(int position){

        LogHelper.i(TAG , LogHelper.__TAG__() + ", position : " + position);

        String positionString ;
        if(position == LedControl.POSITION_ALL){//全部

            positionString = BreathLedHelper.POSITION_ALL ;

        }else if((position & LedControl.POSITION_CHEST)== LedControl.POSITION_CHEST){   // 胸部

            if(((position & LedControl.POSITION_LEFT_EAR) == LedControl.POSITION_LEFT_EAR) || //左耳
                    ((position & LedControl.POSITION_RIGHT_EAR) == LedControl.POSITION_RIGHT_EAR)){//右耳

                positionString = BreathLedHelper.POSITION_ALL ;

            }else {

                positionString = BreathLedHelper.POSITION_CHEST ;
            }

        }else if(((position & LedControl.POSITION_LEFT_EAR) == LedControl.POSITION_LEFT_EAR) || //左耳
                ((position & LedControl.POSITION_RIGHT_EAR) == LedControl.POSITION_RIGHT_EAR)){//右耳

            positionString = BreathLedHelper.POSITION_EAR ; //耳朵

        }else{

            positionString = null ;
        }

        return positionString;
    }

    public static String getPower(LedControl.Power power){

        if(power == null){

            return null ;
        }

        return power == LedControl.Power.POWER_ON ? FUNCTION_ON : FUNCTION_OFF;

    }

    public static String getColor(LedControl.Color color){

        if(color == null){

            return null ;
        }

        int value = 0xFF000000 | color.getColor() ;
        switch (value){
            case Color.RED:
                return COLOR_RED ;

            case Color.GREEN:
                return COLOR_GREEN ;

            case Color.BLUE :
                return COLOR_BLUE ;

            case Color.CYAN:
                return COLOR_GREEN_BLUE ;

            case Color.MAGENTA:
                return COLOR_RED_BLUE ;

            case Color.YELLOW:
                return COLOR_RED_GREEN ;

            case Color.WHITE:
                return COLOR_WHITE ;

        }

        return null ;
    }


    public static String getEffect(LedControl.Effect effect){

        if(effect == null){

            return null ;
        }

        switch (effect){

            case NORMAL:

                return FREQ_CONST ;

            case BREATH_LOW:
                return FREQ_LOW ;

            case BREATH_MIDDLE :
                return FREQ_MIDDLE ;

            case BREATH_FAST:
                return FREQ_HIGH ;
        }

        return null ;
    }

}
