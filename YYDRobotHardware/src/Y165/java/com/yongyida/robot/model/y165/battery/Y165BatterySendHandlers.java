package com.yongyida.robot.model.y165.battery;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.battery.BatterySendHandlers;
import com.yongyida.robot.model.agreement.Y165Receive;
import com.yongyida.robot.model.y165.battery.control.Y165QueryBatteryControlHandler;

/**
 * Created by HuangXiangXiang on 2018/3/30.
 */
public class Y165BatterySendHandlers extends BatterySendHandlers {

    public Y165BatterySendHandlers(Context context){

        super(context);

        addBaseControlHandler(new Y165QueryBatteryControlHandler(context));
    }

}
