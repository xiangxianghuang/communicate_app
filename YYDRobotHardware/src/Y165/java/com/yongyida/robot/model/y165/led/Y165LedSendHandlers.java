package com.yongyida.robot.model.y165.led;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.led.LedSendHandlers;
import com.yongyida.robot.model.y165.led.control.Y165LedSendControlHandler;

/**
 * Created by HuangXiangXiang on 2018/4/17.
 */
public class Y165LedSendHandlers extends LedSendHandlers {

    public Y165LedSendHandlers(Context context) {
        super(context);

        addBaseControlHandler(new Y165LedSendControlHandler(context));
    }

}
