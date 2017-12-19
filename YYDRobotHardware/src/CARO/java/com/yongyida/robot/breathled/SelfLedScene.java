package com.yongyida.robot.breathled;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HuangXiangXiang on 2017/10/31.
 * 呼吸灯效果
 */
public class SelfLedScene {

    public static final SelfLedScene LED_NORMAL = new SelfLedScene() ;
    static {

        LedEffect ledEffect = new LedEffect(255,255,255,60) ;
        LED_NORMAL.setLedEffect(ledEffect);
    }

    public static final SelfLedScene LED_OFFLINE = new SelfLedScene() ;
    static {

        LedEffect ledEffect = new LedEffect(255,73,0,60) ;
        LED_OFFLINE.setLedEffect(ledEffect);
    }

    public static final SelfLedScene LED_SET_WIFI = new SelfLedScene() ;
    static {

        LedEffect ledEffect0 = new LedEffect(255,255,255,10) ;
        LedEffect ledEffect1 = new LedEffect(0,255,0,5) ;
        LED_SET_WIFI.setLedEffect(ledEffect0);
        LED_SET_WIFI.setLedEffect(ledEffect1);
    }


    public static final SelfLedScene LED_READY = new SelfLedScene() ;
    static {

        LedEffect ledEffect = new LedEffect(56,55,34,60) ;
        LED_READY.setLedEffect(ledEffect);
    }

    public static final SelfLedScene LED_LISTENING = new SelfLedScene() ;
    static {

        LedEffect ledEffect0 = new LedEffect(56,55,34,5) ;
        LedEffect ledEffect1 = new LedEffect(56,55,111,10) ;
        LedEffect ledEffect2 = new LedEffect(56,55,188,15) ;
        LedEffect ledEffect3 = new LedEffect(56,55,255,20) ;
        LED_LISTENING.setLedEffect(ledEffect0);
        LED_LISTENING.setLedEffect(ledEffect1);
        LED_LISTENING.setLedEffect(ledEffect2);
        LED_LISTENING.setLedEffect(ledEffect3);
    }

    public static final SelfLedScene LED_SECURITY = new SelfLedScene() ;
    static {

        LedEffect ledEffect0 = new LedEffect(255,255,255,8) ;
        LedEffect ledEffect1 = new LedEffect(255,0,0,4) ;
        LED_SECURITY.setLedEffect(ledEffect0);
        LED_SECURITY.setLedEffect(ledEffect1);
    }

    public static final SelfLedScene LED_INCOMING = new SelfLedScene() ;
    static {

        LedEffect ledEffect0 = new LedEffect(0,255,0,5) ;
        LedEffect ledEffect1 = new LedEffect(255,73,0,5) ;
        LedEffect ledEffect2 = new LedEffect(255,0,125,5) ;
        LedEffect ledEffect3 = new LedEffect(255,0,0,5) ;
        LED_INCOMING.setLedEffect(ledEffect0);
        LED_INCOMING.setLedEffect(ledEffect1);
        LED_INCOMING.setLedEffect(ledEffect2);
        LED_INCOMING.setLedEffect(ledEffect3);
    }


    public final static String OFFLINE          = "offline";
    public final static String SET_WIFI         = "setWifi";
    public final static String READY            = "ready";
    public final static String LISTENING        = "listening";
    public final static String SECURITY         = "security";
    public final static String INCOMING         = "incoming";

    private static final HashMap<String, SelfLedScene> mLedScenes = new HashMap<>() ;
    static {

        mLedScenes.put(OFFLINE, LED_OFFLINE) ;
        mLedScenes.put(SET_WIFI, LED_SET_WIFI) ;
        mLedScenes.put(READY, LED_READY) ;
        mLedScenes.put(LISTENING, LED_LISTENING) ;
        mLedScenes.put(SECURITY, LED_SECURITY) ;
        mLedScenes.put(INCOMING, LED_INCOMING) ;

    }

    public static SelfLedScene getLedScene(String sceneName){

        SelfLedScene selfLedScene = mLedScenes.get(sceneName) ;

        if(selfLedScene == null){

            return LED_NORMAL ;
        }
        return selfLedScene;
    }


    private final ArrayList<LedEffect> mLedEffects = new ArrayList<>() ;

    public ArrayList<LedEffect> getLedEffects() {
        return mLedEffects;
    }

    public void setLedEffect(LedEffect ledEffect) {

        this.mLedEffects.add(ledEffect);
    }

    public static class LedEffect{

        private int red ;
        private int green ;
        private int blue ;

        private int time ;

        public LedEffect(int red, int green, int blue, int time){

            this.red = red ;
            this.green = green ;
            this.blue = blue ;
            this.time = time*200 ;
        }

        public int getRed() {
            return red;
        }

        public int getGreen() {
            return green;
        }

        public int getBlue() {
            return blue;
        }

        public int getTime() {
            return time;
        }
    }

}
