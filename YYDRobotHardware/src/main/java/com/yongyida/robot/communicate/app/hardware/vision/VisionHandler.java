package com.yongyida.robot.communicate.app.hardware.vision;

import com.hiva.communicate.app.common.IResponseListener;
import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.BaseHandler;
import com.yongyida.robot.communicate.app.hardware.vision.response.VisionDataResponse;
import com.yongyida.robot.communicate.app.hardware.vision.send.VisionDataSend;
import com.yongyida.robot.control.model.HardwareConfig;

import static com.hiva.communicate.app.common.response.BaseResponse.RESULT_CAN_NOT_HANDLE;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class VisionHandler extends BaseHandler {

    private VisionControl mVersionControl ;

    public VisionHandler(){

        mVersionControl = (VisionControl) getControl();
    }


    @Override
    protected boolean isCanHandle(BaseSend send) {

        return send instanceof VisionDataSend;
    }

    @Override
    protected void onHandler(BaseSend send, IResponseListener responseListener) {

        VisionDataSend visionDataSend = (VisionDataSend) send;
        BaseResponse baseResponse = mVersionControl.onControl(visionDataSend) ;
        if(responseListener != null){

            responseListener.onResponse(baseResponse);
        }
    }

    @Override
    public final int getType() {
        return HardwareConfig.TYPE_VISION ;
    }
}
