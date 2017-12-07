package com.yongyida.robot.control.model.y138.version;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.vision.VisionData;
import com.yongyida.robot.communicate.app.hardware.vision.VisionControl;
import com.yongyida.robot.communicate.app.hardware.vision.response.VersionDataResponse;
import com.yongyida.robot.communicate.app.hardware.vision.send.VersionDataSend;
import com.hiva.communicate.app.utils.LogHelper;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y138VisionControl extends VisionControl {

    private static final String TAG = Y138VisionControl.class.getSimpleName() ;


    @Override
    public BaseResponse sendVersionData(VersionDataSend versionDataSend) {

        BaseResponse response = new VersionDataResponse() ;

        VisionData visionData = versionDataSend.getVisionData() ;

        LogHelper.i(TAG, LogHelper.__TAG__() + ",visionData : " + visionData);

       return response;
    }
}
