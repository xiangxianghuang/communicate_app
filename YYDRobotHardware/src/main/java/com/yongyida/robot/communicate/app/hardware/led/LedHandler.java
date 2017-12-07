package com.yongyida.robot.communicate.app.hardware.led;

import com.hiva.communicate.app.common.IResponseListener;
import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.BaseHandler;
import com.yongyida.robot.communicate.app.hardware.led.response.LedResponse;
import com.yongyida.robot.communicate.app.hardware.led.response.LedStatueResponse;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;
import com.yongyida.robot.communicate.app.hardware.led.send.LedStatueSend;
import com.hiva.communicate.app.utils.LogHelper;
import com.hiva.communicate.app.utils.StringUtils;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class LedHandler extends BaseHandler {

    private static final String TAG = LedHandler.class.getSimpleName() ;

    private LedControl mLedControl ;
    private final LedStatue mLedStatue = new LedStatue() ;

    public LedHandler(){

        mLedControl = (LedControl) getControl();
    }


    public boolean onHandler(BaseSend send, IResponseListener responseListener) {

        if(send instanceof LedStatueSend){

            if(responseListener != null){

                LedStatueResponse ledStatueResponse = new LedStatueResponse() ;
                mLedStatue.setColor(0xffffff);
                ledStatueResponse.setLedStatue(mLedStatue);
                responseListener.onResponse(ledStatueResponse) ;
            }

        }else if(send instanceof LedSend){

            LedSend ledSend = (LedSend) send;
            LedStatue ledStatue = ledSend.getLedStatue() ;
            setLedStatue(ledStatue);

            LogHelper.i(TAG, LogHelper.__TAG__() + ", mLedStatue : " + ledStatue);

            if(responseListener != null){

                LedResponse ledResponse = new LedResponse() ;
                responseListener.onResponse(ledResponse) ;
            }
        }
        return false ;
    }

    @Override
    public int getType() {

        return HardwareConfig.TYPE_LED;
    }


    public LedStatue getLedStatue() {

        LogHelper.i(TAG, LogHelper.__TAG__());

        return mLedStatue;
    }

    public void setLedStatue(LedStatue ledStatue) {

        LogHelper.i(TAG, LogHelper.__TAG__() + " ,ledStatue : " + ledStatue.toString());

        if (mLedStatue.getPosition() != ledStatue.getPosition()) {

            mLedStatue.setPosition(ledStatue.getPosition());
            if (mLedControl != null) {

                mLedControl.onPositionChanged(ledStatue.getPosition());
            }

        }

        if (mLedStatue.isTurnOn() != ledStatue.isTurnOn()) {

            mLedStatue.setTurnOn(ledStatue.isTurnOn());
            if (mLedControl != null) {
                mLedControl.onTurnOnChanged(ledStatue.isTurnOn());
            }

            if (!StringUtils.isEquals(mLedStatue.getEffect(), ledStatue.getEffect())) {

                mLedStatue.setEffect(ledStatue.getEffect());
                if (mLedControl != null) {
                    mLedControl.onEffectChanged(ledStatue.getEffect());
                }
            }

            if (mLedStatue.getBrightness() != ledStatue.getBrightness()) {

                mLedStatue.setBrightness(ledStatue.getBrightness());
                if (mLedControl != null) {
                    mLedControl.onBrightnessChanged(ledStatue.getBrightness());
                }
            }

            if (mLedStatue.getColor() != ledStatue.getColor()) {

                mLedStatue.setColor(ledStatue.getColor());
                if (mLedControl != null) {
                    mLedControl.onColorChanged(ledStatue.getColor());
                }
            }

            if (mLedStatue.getCold() != ledStatue.getCold()) {

                mLedStatue.setCold(ledStatue.getCold());
                if (mLedControl != null) {
                    mLedControl.onColdChanged(ledStatue.getCold());
                }
            }

            if (mLedStatue.getWarm() != ledStatue.getWarm()) {

                mLedStatue.setWarm(ledStatue.getWarm());
                if (mLedControl != null) {
                    mLedControl.onWarmChanged(ledStatue.getWarm());
                }
            }

        }
    }


}
