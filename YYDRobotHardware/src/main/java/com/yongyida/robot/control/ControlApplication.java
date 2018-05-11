package com.yongyida.robot.control;

import android.app.Application;
import android.content.Context;

import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.breathled.YYDRobotBreathLedService;
import com.yongyida.robot.control.server.HardWareServerService;

/**
 * Created by HuangXiangXiang on 2018/4/3.
 */
public class ControlApplication extends Application {

    private static final String TAG = ControlApplication.class.getSimpleName() ;

    public static void initService(Context context){

        LogHelper.i(TAG, LogHelper.__TAG__());

        HardWareServerService.startHardWareServerService(context);
        YYDRobotBreathLedService.startBreathLedService(context);
    }


    @Override
    public void onCreate() {

        LogHelper.i(TAG, LogHelper.__TAG__());
        super.onCreate();

        initService(this) ;
    }




}
