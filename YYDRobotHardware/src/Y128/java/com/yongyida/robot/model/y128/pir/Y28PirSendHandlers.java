package com.yongyida.robot.model.y128.pir;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.pir.PirSendHandlers;
import com.yongyida.robot.model.y128.pir.control.Y128QueryPirValueControlHandler;

/**
 * Created by HuangXiangXiang on 2018/3/30.
 */
public class Y28PirSendHandlers extends PirSendHandlers {

    public Y28PirSendHandlers(Context context) {
        super(context);

        addBaseControlHandler(new Y128QueryPirValueControlHandler(context));
    }

}
