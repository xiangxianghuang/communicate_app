package com.yongyida.robot.model.s1.montrol;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.motion.MotionSendHandlers;
import com.yongyida.robot.model.s1.montrol.control.S1FootControlHandler;

/**
 * Created by HuangXiangXiang on 2018/4/20.
 *
 */
public class S1MotionSendHandlers extends MotionSendHandlers {

    private static final String TAG = S1MotionSendHandlers.class.getSimpleName() ;

    public S1MotionSendHandlers(Context context) {
        super(context);

        addBaseControlHandler(new S1FootControlHandler(context));

    }

}
