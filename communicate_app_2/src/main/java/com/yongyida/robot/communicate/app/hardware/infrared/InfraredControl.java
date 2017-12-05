package com.yongyida.robot.communicate.app.hardware.infrared;

import com.yongyida.robot.communicate.app.hardware.IControl;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class InfraredControl implements IControl {

    @Override
    public int getType() {
        return HardwareConfig.TYPE_INFRARED ;
    }
}
