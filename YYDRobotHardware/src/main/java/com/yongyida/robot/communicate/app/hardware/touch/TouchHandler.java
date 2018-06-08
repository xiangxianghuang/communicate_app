package com.yongyida.robot.communicate.app.hardware.touch;

import android.content.Context;
import android.content.Intent;

import com.yongyida.robot.communicate.app.hardware.BaseHandler;
import com.yongyida.robot.communicate.app.hardware.touch.response.data.TouchInfo;
import com.yongyida.robot.communicate.app.hardware.touch.send.TouchSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class TouchHandler extends BaseHandler<TouchSend> {

    private boolean isTest ;

    public boolean isTest() {
        return isTest;
    }

    public void setTest(boolean test) {
        isTest = test;
    }

    public TouchHandler(Context context) {
        super(context);
    }

    /**
     * 触摸变化
     * */
    public interface OnTouchListener{

        void onTouchListener(TouchInfo.Point point) ;
    }


    public static final String ACTION_TOUCH = "com.yongyida.robot.TOUCH" ;
    public static final String ACTION_TEST_TOUCH = "com.yongyida.robot.TEST_TOUCH" ;
    public static final String KEY_TOUCH_POINT = "touch_point" ;

    public void onTouch(TouchInfo.Point point) {

        Intent intent = new Intent() ;
        intent.setAction(isTest ? ACTION_TEST_TOUCH : ACTION_TOUCH) ;
        intent.putExtra(KEY_TOUCH_POINT, point.name()) ;
        mContext.sendBroadcast(intent);
    }

}
