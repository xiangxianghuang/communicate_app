package com.yongyida.robot.communicate.app.hardware.vision;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.vision.send.VisionDataSend;
import com.yongyida.robot.communicate.app.hardware.IControl;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class VisionControl implements IControl{

    public abstract BaseResponse sendVersionData(VisionDataSend visionDataSend);

    @Override
    public final int getType() {
        return HardwareConfig.TYPE_VISION ;
    }
}
