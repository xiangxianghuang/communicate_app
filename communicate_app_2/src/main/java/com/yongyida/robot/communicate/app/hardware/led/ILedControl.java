package com.yongyida.robot.communicate.app.hardware.led;

import com.yongyida.robot.communicate.app.hardware.IControl;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public interface ILedControl extends IControl {

    void onPositionChanged(int position);

    void onTurnOnChanged(boolean turnOn);

    void onEffectChanged(String effect);

    void onBrightnessChanged(int brightness);

    void onColorChanged(int color);

    void onColdChanged(int cold);

    void onWarmChanged(int warm);

}
