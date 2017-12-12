package com.yongyida.robot.communicate.app.hardware.light;

import com.hiva.communicate.app.common.IResponseListener;
import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.BaseHandler;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class LightHandler extends BaseHandler {

    @Override
    public int getType() {
        return HardwareConfig.TYPE_LIGHT ;
    }

    @Override
    protected boolean isCanHandle(BaseSend send) {
        return false;
    }

    @Override
    protected void onHandler(BaseSend send, IResponseListener responseListener) {

    }
}
