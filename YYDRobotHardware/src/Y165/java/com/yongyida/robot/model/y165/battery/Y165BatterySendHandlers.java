package com.yongyida.robot.model.y165.battery;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.battery.BatterySendHandlers;
import com.yongyida.robot.model.agreement.Y165Receive;

/**
 * Created by HuangXiangXiang on 2018/3/30.
 */
public class Y165BatterySendHandlers extends BatterySendHandlers {

    private static final String TAG = Y165BatterySendHandlers.class.getSimpleName() ;

    public Y165BatterySendHandlers(Context context){

        super(context);
        startListenBatteryChanged() ;   // 开始电池监听
    }



//    @Override
//    public SendResponse onHandler(BatterySend send, IResponseListener responseListener) {
//
//        LogHelper.i(TAG, LogHelper.__TAG__());
//
////        QueryBattery queryBattery = send.getQueryBattery() ;
////        if(queryBattery != null){
////
////            BatteryResponse batteryResponse = new BatteryResponse() ;
////            batteryResponse.setBatteryInfo(batteryInfo);
////
////            return batteryResponse ;
////        }
//
//        return null;
//    }


    private void startListenBatteryChanged(){

        Y165Receive receive = Y165Receive.getInstance() ;
//        receive.setOnBatteryChangedListener(onBatteryChangedListener);

    }

//    private OnBatteryChangedListener onBatteryChangedListener = new OnBatteryChangedListener() {
//        @Override
//        public void onBatteryChanged(int state, int level) {
//
//            boolean isCharging = (state == 0x01);
//            batteryInfo.setCharging(isCharging);
//            batteryInfo.setState(state);
//            batteryInfo.setLevel(level);
//
//            //自定义广播
//            sendYYDRobotBatteryBroadcast(isCharging, level, state);
//
//            //系统自带的电池广播
////            sendSystemBatteryBroadcast(status, level);
//        }
//    };



}
