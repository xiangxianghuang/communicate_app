package com.yongyida.robot.communicate.app.hardware.led;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.IControl;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;
import com.yongyida.robot.communicate.app.hardware.vision.send.VisionDataSend;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class LedControl implements IControl {

  public abstract BaseResponse onControl(LedSend ledSend);

    @Override
    public int getType() {

        return HardwareConfig.TYPE_LED;
    }

}
