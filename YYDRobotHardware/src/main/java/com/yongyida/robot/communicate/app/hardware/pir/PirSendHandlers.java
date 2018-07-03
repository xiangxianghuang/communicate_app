package com.yongyida.robot.communicate.app.hardware.pir;

import android.content.Context;
import android.content.Intent;

import com.yongyida.robot.communicate.app.hardware.BaseSendHandlers;
import com.yongyida.robot.communicate.app.hardware.pir.send.PirSend;

/**
 */
public abstract class PirSendHandlers extends BaseSendHandlers<PirSend> {

    public PirSendHandlers(Context context) {
        super(context);
    }

}
