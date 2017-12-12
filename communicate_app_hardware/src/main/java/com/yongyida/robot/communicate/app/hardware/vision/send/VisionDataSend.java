package com.yongyida.robot.communicate.app.hardware.vision.send;

import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.vision.data.VisionData;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class VisionDataSend extends BaseSend {

    private VisionData visionData;

    public VisionData getVisionData() {
        return visionData;
    }

    public void setVisionData(VisionData visionData) {
        this.visionData = visionData;
    }

}
