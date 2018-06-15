package com.yongyida.robot.model.y128.battery;

import android.content.Context;

import com.hiva.communicate.app.common.response.SendResponse;
import com.hiva.communicate.app.server.IResponseListener;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.battery.BatteryHandler;
import com.yongyida.robot.communicate.app.hardware.battery.send.BatterySend;
import com.yongyida.robot.model.agreement.Y128Receive;
import com.yongyida.robot.model.agreement.Y128Steering;

/**
 * Created by HuangXiangXiang on 2018/2/24.
 * 电池数据值
 *
 */
public class Y128BatteryHandler extends BatteryHandler {

    private static final String TAG = Y128BatteryHandler.class.getSimpleName() ;

    public Y128BatteryHandler(Context context){

        super(context);
        startListenBatteryChanged() ;   // 开始电池监听
    }

    @Override
    public SendResponse onHandler(BatterySend send, IResponseListener responseListener) {

        LogHelper.i(TAG, LogHelper.__TAG__());

//        QueryBattery queryBattery = send.getQueryBattery() ;
//        if(queryBattery != null){
//
//            BatteryResponse batteryResponse = new BatteryResponse() ;
//            batteryResponse.setBatteryInfo(batteryInfo);
//
//            return batteryResponse ;
//        }

        return null;
    }


    private void startListenBatteryChanged(){

        Y128Receive receive = Y128Receive.getInstance() ;
        receive.setOnBatteryChangedListener(onBatteryChangedListener);

    }


    private OnBatteryChangedListener onBatteryChangedListener = new OnBatteryChangedListener() {
        @Override
        public void onBatteryChanged(int state, int level) {

            boolean isCharging = ((state == Y128Steering.ReceiveSystemState.CHARGE_IS_CHARGING) || (state == Y128Steering.ReceiveSystemState.CHARGE_WIRE_CHARGING));
            batteryInfo.setCharging(isCharging);
            batteryInfo.setState(state);
            batteryInfo.setLevel(level);

            //自定义广播
            sendYYDRobotBatteryBroadcast(isCharging, level, state);

            //系统自带的电池广播
//            sendSystemBatteryBroadcast(status, level);
        }
    };

}
