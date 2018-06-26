package com.yongyida.robot.communicate.app.hardware.touch;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.BaseSendHandlers;
import com.yongyida.robot.communicate.app.hardware.touch.send.TouchSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class TouchSendHandlers extends BaseSendHandlers<TouchSend> {

    public TouchSendHandlers(Context context) {
        super(context);


    }
}
