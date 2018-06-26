package com.yongyida.robot.model.y128.touch;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.touch.TouchSendHandlers;
import com.yongyida.robot.model.y128.touch.control.Y128QueryTouchPositionControlHandler;

/**
 * Created by HuangXiangXiang on 2018/3/1.
 */
public class Y128TouchSendHandlers extends TouchSendHandlers {

    public Y128TouchSendHandlers(Context context){
        super(context);

        addBaseControlHandler(new Y128QueryTouchPositionControlHandler(context));
    }

}
