package com.hiva.communicate.app.hardware.vision;

import com.hiva.communicate.app.common.IResponseListener;
import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.BaseHandler;
import com.hiva.communicate.app.hardware.vision.response.VersionDataResponse;
import com.hiva.communicate.app.hardware.vision.send.VersionDataSend;
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
    public boolean onHandler(BaseSend send, IResponseListener responseListener) {

        if(send instanceof VersionDataSend){

            VersionDataSend versionDataSend = (VersionDataSend) send;
            sendVersionData(versionDataSend, responseListener) ;

            return true;
        }


        return false;
    }

    private void sendVersionData(VersionDataSend versionDataSend,IResponseListener responseListener) {

        if(mVersionControl == null){

            if(responseListener != null){

                BaseResponse baseResponse = new VersionDataResponse(RESULT_CAN_NOT_HANDLE, "没有对应的处理类") ;
                responseListener.onResponse(baseResponse);
            }

        }else{

            BaseResponse baseResponse = mVersionControl.sendVersionData(versionDataSend) ;
            if(responseListener != null){

                responseListener.onResponse(baseResponse);
            }
        }
    }


    @Override
    public final int getType() {
        return HardwareConfig.TYPE_VISION ;
    }
}
