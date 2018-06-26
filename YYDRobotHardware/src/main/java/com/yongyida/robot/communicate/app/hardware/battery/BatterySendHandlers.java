package com.yongyida.robot.communicate.app.hardware.battery;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.BaseSendHandlers;
import com.yongyida.robot.communicate.app.hardware.battery.response.data.BatteryInfo;
import com.yongyida.robot.communicate.app.hardware.battery.send.BatterySend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class BatterySendHandlers extends BaseSendHandlers<BatterySend> {

    private static final String TAG = BatterySendHandlers.class.getSimpleName() ;

    protected BatteryInfo batteryInfo = new BatteryInfo() ;

    public BatterySendHandlers(Context context) {

        super(context);
    }





}
