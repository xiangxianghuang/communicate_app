package com.yongyida.robot.model.sr1.montrol;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.motion.MotionSendHandlers;
import com.yongyida.robot.model.sr1.montrol.control.S1FootControlHandler;

/**
 * Created by HuangXiangXiang on 2018/4/20.
 *
 */
public class SR1MotionSendHandlers extends MotionSendHandlers {

    private static final String TAG = SR1MotionSendHandlers.class.getSimpleName() ;

    public SR1MotionSendHandlers(Context context) {
        super(context);

        addBaseControlHandler(new S1FootControlHandler(context));

    }

}
