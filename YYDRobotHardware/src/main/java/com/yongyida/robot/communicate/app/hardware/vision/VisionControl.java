package com.yongyida.robot.communicate.app.hardware.vision;

import android.content.Context;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.vision.send.VisionDataSend;
import com.yongyida.robot.communicate.app.hardware.BaseControl;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class VisionControl extends BaseControl {

    public VisionControl(Context context) {
        super(context);
    }

    @Override
    public BaseResponse onControl(BaseSend send) {
        return null;
    }
}
