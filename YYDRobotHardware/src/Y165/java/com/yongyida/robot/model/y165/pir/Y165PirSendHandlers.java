package com.yongyida.robot.model.y165.pir;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.pir.PirSendHandlers;
import com.yongyida.robot.model.agreement.Y165Receive;
import com.yongyida.robot.model.y165.pir.control.Y165QueryPirValueControlHandler;

/**
 * Created by HuangXiangXiang on 2018/3/30.
 */
public class Y165PirSendHandlers extends PirSendHandlers {

    public Y165PirSendHandlers(Context context) {
        super(context);

        addBaseControlHandler(new Y165QueryPirValueControlHandler(context));
    }

}
