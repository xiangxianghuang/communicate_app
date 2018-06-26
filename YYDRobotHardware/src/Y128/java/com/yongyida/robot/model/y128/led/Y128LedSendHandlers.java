package com.yongyida.robot.model.y128.led;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.led.LedSendHandlers;
import com.yongyida.robot.model.agreement.Y128Steering;
import com.yongyida.robot.model.y128.led.control.Y128LedSendControlHandler;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y128LedSendHandlers extends LedSendHandlers {

    public Y128LedSendHandlers(Context context) {
        super(context);

        addBaseControlHandler(new Y128LedSendControlHandler(context));
    }

}
