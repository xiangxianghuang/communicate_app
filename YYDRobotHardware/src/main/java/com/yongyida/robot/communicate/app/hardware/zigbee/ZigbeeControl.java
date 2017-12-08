package com.yongyida.robot.communicate.app.hardware.zigbee;

import com.yongyida.robot.communicate.app.hardware.IControl;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class ZigbeeControl implements IControl{

    @Override
    public int getType() {
        return HardwareConfig.TYPE_ZIGBEE ;
    }

    @Override
    public boolean start() {
        return false;
    }

    @Override
    public boolean end() {
        return false;
    }
}
