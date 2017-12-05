package com.yongyida.robot.communicate.app.hardware.humiture;

import com.yongyida.robot.communicate.app.common.IResponseListener;
import com.yongyida.robot.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.BaseHandler;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class HumitureHandler extends BaseHandler {
    @Override
    public boolean onHandler(BaseSend send, IResponseListener responseListener) {
        return false;
    }

    @Override
    public int getType() {
        return HardwareConfig.TYPE_HUMITURE ;
    }
}
