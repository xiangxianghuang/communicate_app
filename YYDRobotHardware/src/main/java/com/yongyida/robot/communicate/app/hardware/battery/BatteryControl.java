package com.yongyida.robot.communicate.app.hardware.battery;

import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.BaseControl;
import com.yongyida.robot.communicate.app.hardware.battery.data.BatteryInfo;
import com.yongyida.robot.communicate.app.hardware.battery.send.BatterySend;
import com.yongyida.robot.model.agreement.Y128Steering;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class BatteryControl extends BaseControl<BatterySend> {

    private static final String TAG = BatteryControl.class.getSimpleName() ;

    protected BatteryInfo batteryInfo = new BatteryInfo() ;

    public BatteryControl(Context context) {

        super(context);
    }



    /**
     * 电池变化
     * */
    public interface OnBatteryChangedListener{

        void onBatteryChanged(int state , int level) ;
    }


    public static final String ACTION_BATTERY_CHANGE = "com.yongyida.robot.BATTERY_CHANGE" ;

    public static final String KEY_IS_CHARGING      = "isCharging" ;
    public static final String KEY_LEVEL            = "level" ;
    public static final String KEY_STATE            = "state" ;


    /**
     * 发送电池状态信息改变
     * */
    protected void sendYYDRobotBatteryBroadcast(boolean isCharging, int level, int state){

        LogHelper.i(TAG, LogHelper.__TAG__() + ", isCharging : " + isCharging + ", level :" + level) ;

        //自定义广播
        Intent intent = new Intent(ACTION_BATTERY_CHANGE);
        intent.putExtra(KEY_IS_CHARGING , isCharging) ;
        intent.putExtra(KEY_LEVEL , level) ;
        intent.putExtra(KEY_STATE , state) ;
        mContext.sendBroadcast(intent);

    }

    /**
     *
     * 发送系统电池电量广播，这个需要系统权限才能发送
     * */
    protected void sendSystemBatteryBroadcast(int status, int level){

        LogHelper.i(TAG, LogHelper.__TAG__() + ", status : " + status + ", level :" + level) ;

        try{

            Intent intent ;
            // 电量状态
            if(status == Y128Steering.ReceiveSystemState.CHARGE_IS_CHARGING){//充电

                intent = new Intent(Intent.ACTION_POWER_CONNECTED) ;
                mContext.sendBroadcast(intent);

            }else{ //断开充电

                intent = new Intent(Intent.ACTION_POWER_DISCONNECTED) ;
                mContext.sendBroadcast(intent);
            }

            //电量
            intent = new Intent(Intent.ACTION_BATTERY_CHANGED) ;
            intent.putExtra(BatteryManager.EXTRA_LEVEL , level) ;
            intent.putExtra(BatteryManager.EXTRA_SCALE , 100) ;
            mContext.sendBroadcast(intent);

        }catch (Exception e){

            LogHelper.e(TAG, LogHelper.__TAG__() + ", 发送系统广播异常 :" + e.getMessage()) ;
        }
    }

}
