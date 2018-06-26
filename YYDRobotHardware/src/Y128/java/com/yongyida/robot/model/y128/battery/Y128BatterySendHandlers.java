package com.yongyida.robot.model.y128.battery;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.battery.BatterySendHandlers;
import com.yongyida.robot.model.agreement.Y128Receive;
import com.yongyida.robot.model.y128.battery.control.Y128QueryBatteryControlHandler;

/**
 * Created by HuangXiangXiang on 2018/2/24.
 * 电池数据值
 *
 */
public class Y128BatterySendHandlers extends BatterySendHandlers {

    private static final String TAG = Y128BatterySendHandlers.class.getSimpleName() ;

    public Y128BatterySendHandlers(Context context){

        super(context);

        addBaseControlHandler(new Y128QueryBatteryControlHandler(context));

    }

}
