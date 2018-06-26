package com.yongyida.robot.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yongyida.robot.communicate.app.utils.LogHelper;

/**
 * Created by HuangXiangXiang on 2018/3/1.
 *
 * 开机广播 用于启动相关应用
 */
public class BootBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = BootBroadcastReceiver.class.getSimpleName() ;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction() ;
        LogHelper.i(TAG, LogHelper.__TAG__() + " , action : " + action);

        if(Intent.ACTION_BOOT_COMPLETED.equals(action)){

            LogHelper.i(TAG, LogHelper.__TAG__());

        }

    }



}
