package com.yongyida.robot.communicate.app.hardware.led;

import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.utils.StringUtils;

/**
 * Created by HuangXiangXiang on 2017/12/4.
 */
public class LedControl {

    private static final String TAG = LedControl.class.getSimpleName() ;


    private final LedStatue mLedStatue = new LedStatue() ;

    public LedStatue getLedStatue() {

        LogHelper.i(TAG, LogHelper.__TAG__());

        return mLedStatue;
    }

    public void setLedStatue(LedStatue ledStatue) {

        LogHelper.i(TAG, LogHelper.__TAG__() + " ,ledStatue : " + ledStatue.toString());

        if(mLedStatue.getPosition() != ledStatue.getPosition()){

            mLedStatue.setPosition(ledStatue.getPosition());
            onPositionChanged(ledStatue.getPosition());
        }

        if(mLedStatue.isTurnOn() != ledStatue.isTurnOn()){

            mLedStatue.setTurnOn(ledStatue.isTurnOn());
            onTurnOnChanged(ledStatue.isTurnOn());
        }

        if(!StringUtils.isEquals(mLedStatue.getEffect(), ledStatue.getEffect()) ){

            mLedStatue.setEffect(ledStatue.getEffect());
            onEffectChanged(ledStatue.getEffect());
        }

        if(mLedStatue.getBrightness() != ledStatue.getBrightness()){

            mLedStatue.setBrightness(ledStatue.getBrightness());
            onBrightnessChanged(ledStatue.getBrightness());
        }

        if(mLedStatue.getColor() != ledStatue.getColor()){

            mLedStatue.setColor(ledStatue.getColor());
            onColorChanged(ledStatue.getColor());
        }

        if(mLedStatue.getCold() != ledStatue.getCold()){

            mLedStatue.setCold(ledStatue.getCold());
            onColdChanged(ledStatue.getCold());
        }

        if(mLedStatue.getWarm() != ledStatue.getWarm()){

            mLedStatue.setWarm(ledStatue.getWarm());
            onWarmChanged(ledStatue.getWarm());
        }

    }

    protected void onPositionChanged(int position) {

        LogHelper.i(TAG, LogHelper.__TAG__() + " ,position : " + position);
    }


    protected void onTurnOnChanged(boolean turnOn) {

        LogHelper.i(TAG, LogHelper.__TAG__() + " ,turnOn : " + turnOn);

    }

    protected void onEffectChanged(String effect) {

        LogHelper.i(TAG, LogHelper.__TAG__() + " ,effect : " + effect);
    }

    protected void onBrightnessChanged(int brightness) {
        LogHelper.i(TAG, LogHelper.__TAG__() + " ,brightness : " + brightness);

    }

    protected void onColorChanged(int color) {
        LogHelper.i(TAG, LogHelper.__TAG__() + " ,color : " + color);

    }

    protected void onColdChanged(int cold) {

        LogHelper.i(TAG, LogHelper.__TAG__() + " ,cold : " + cold);

    }

    protected void onWarmChanged(int warm) {

        LogHelper.i(TAG, LogHelper.__TAG__() + " ,warm : " + warm);

    }




}
