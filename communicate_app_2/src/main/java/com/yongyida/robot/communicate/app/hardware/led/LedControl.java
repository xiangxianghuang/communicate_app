package com.yongyida.robot.communicate.app.hardware.led;

import com.yongyida.robot.communicate.app.hardware.IControl;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class LedControl implements IControl {

  public abstract void onPositionChanged(int position);

  public abstract void onTurnOnChanged(boolean turnOn);

  public abstract void onEffectChanged(String effect);

  public abstract void onBrightnessChanged(int brightness);

  public abstract void onColorChanged(int color);

  public abstract void onColdChanged(int cold);

  public abstract void onWarmChanged(int warm);

    @Override
    public int getType() {

        return HardwareConfig.TYPE_LED;
    }

}
