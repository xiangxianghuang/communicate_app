package com.yongyida.robot.communicate.app.hardware.humiture;

import com.yongyida.robot.communicate.app.hardware.IControl;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class HumitureControl implements IControl {

    @Override
    public int getType() {
        return HardwareConfig.TYPE_HUMITURE ;
    }
}
