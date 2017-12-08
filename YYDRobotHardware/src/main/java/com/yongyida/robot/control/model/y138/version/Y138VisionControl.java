package com.yongyida.robot.control.model.y138.version;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.vision.VisionData;
import com.yongyida.robot.communicate.app.hardware.vision.response.VisionDataResponse;
import com.yongyida.robot.communicate.app.hardware.vision.send.VisionDataSend;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.model.y128.serial.VisionSerialSend;
import com.yongyida.robot.communicate.app.hardware.vision.VisionControl;


/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y138VisionControl extends VisionControl {

    private static final String TAG = Y138VisionControl.class.getSimpleName() ;

    private VisionSerialSend visionSerialSend ;

    public void setVisionSerialSend(VisionSerialSend visionSerialSend) {
        this.visionSerialSend = visionSerialSend;
    }



    @Override
    public BaseResponse sendVersionData(VisionDataSend visionDataSend) {

        BaseResponse response = new VisionDataResponse() ;

        VisionData visionData = visionDataSend.getVisionData() ;

        LogHelper.i(TAG, LogHelper.__TAG__() + ",visionData : " + visionData);

        visionSerialSend.sendVision(visionData);


       return response;
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
