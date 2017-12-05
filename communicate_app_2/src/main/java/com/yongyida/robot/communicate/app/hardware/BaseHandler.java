package com.yongyida.robot.communicate.app.hardware;

import com.yongyida.robot.communicate.app.common.IResponseListener;
import com.yongyida.robot.communicate.app.common.send.BaseSend;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class BaseHandler {

    public abstract boolean onHandler(BaseSend send, IResponseListener responseListener) ;

    public abstract int getType();

    public IControl getControl(){

        return HardwareConfig.getInstance().getControl(getType());
    }

}
