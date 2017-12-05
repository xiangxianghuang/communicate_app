package com.yongyida.robot.communicate.app.hardware.camera;

import com.yongyida.robot.communicate.app.hardware.IControl;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class CameraControl implements IControl {

    public int getType() {
        return HardwareConfig.TYPE_CAMERA;
    }
}
