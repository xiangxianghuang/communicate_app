package com.yongyida.robot.communicate.app.hardware.led;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.BaseSendHandlers;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class LedSendHandlers extends BaseSendHandlers<LedSend> {

    public LedSendHandlers(Context context) {
        super(context);
    }


}
