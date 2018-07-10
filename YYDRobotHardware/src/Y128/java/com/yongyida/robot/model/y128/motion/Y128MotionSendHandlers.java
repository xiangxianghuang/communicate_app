package com.yongyida.robot.model.y128.motion;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.motion.MotionSendHandlers;
import com.yongyida.robot.model.y128.motion.control.Y128FootControlHandler;
import com.yongyida.robot.model.y128.motion.control.Y128QueryMotionSystemControlHandler;
import com.yongyida.robot.model.y128.motion.control.Y128QueryMotionSystemHistoryControlHandler;
import com.yongyida.robot.model.y128.motion.control.Y128QueryUltrasonicControlHandler;
import com.yongyida.robot.model.y128.motion.control.Y128SoundLocationControlHandler;

/**
 * Created by HuangXiangXiang on 2018/3/5.
 */
public class Y128MotionSendHandlers extends MotionSendHandlers {

    private static final String TAG = Y128MotionSendHandlers.class.getSimpleName() ;

    public Y128MotionSendHandlers(Context context) {
        super(context);

        addBaseControlHandler(new Y128FootControlHandler(context));
        addBaseControlHandler(new Y128QueryUltrasonicControlHandler(context));
        addBaseControlHandler(new Y128QueryMotionSystemControlHandler(context));
        addBaseControlHandler(new Y128SoundLocationControlHandler(context));
        addBaseControlHandler(new Y128QueryMotionSystemHistoryControlHandler(context));
    }
}
