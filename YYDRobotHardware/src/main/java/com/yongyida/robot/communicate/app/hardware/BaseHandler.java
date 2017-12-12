package com.yongyida.robot.communicate.app.hardware;

import com.hiva.communicate.app.common.IResponseListener;
import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.vision.response.VisionDataResponse;
import com.yongyida.robot.control.model.HardwareConfig;

import static com.hiva.communicate.app.common.response.BaseResponse.RESULT_CAN_NOT_HANDLE;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class BaseHandler {

    public abstract int getType();

    public IControl getControl(){

        return HardwareConfig.getInstance().getControl(getType());
    }

    public final boolean isCanHandle(BaseSend send,IResponseListener responseListener){

        if(isCanHandle(send)){

            if(isCanControl(responseListener)){

                onHandler(send, responseListener);
            }

            return true ;
        }

        return false ;
    }

    /**是否可以处理*/
    protected abstract boolean isCanHandle(BaseSend send) ;

    /**是否可以对应的对象*/
    private boolean isCanControl(IResponseListener responseListener) {

        if(getControl() == null) {

            if (responseListener != null) {

                BaseResponse baseResponse = new VisionDataResponse(RESULT_CAN_NOT_HANDLE, "没有对应的处理类");
                responseListener.onResponse(baseResponse);
            }

            return false ;
        }
        return true ;
    }

    protected abstract void onHandler(BaseSend send,IResponseListener responseListener) ;


}
