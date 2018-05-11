package com.yongyida.robot.breathled;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.led.data.LedControl;
import com.yongyida.robot.hardware.client.LedClient;

/**
 * Created by HuangXiangXiang on 2018/4/2.
 */
public class YYDRobotBreathLedService extends Service {

    private static final String TAG = YYDRobotBreathLedService.class.getSimpleName() ;

    private LedControl mLedControl = new LedControl();

    public static void startBreathLedService(Context context){

        LogHelper.i(TAG, LogHelper.__TAG__()) ;

        Intent intent = new Intent(context, YYDRobotBreathLedService.class) ;
        context.startService(intent) ;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        LogHelper.i(TAG, LogHelper.__TAG__()) ;
        super.onCreate();

        mLedControl.setPosition(LedControl.POSITION_CHEST);

        registerReceiver() ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unRegisterReceiver() ;
    }


    public static final String ACTION_BATTERY_CHANGE = "com.yongyida.robot.BATTERY_CHANGE" ;

    public static final String KEY_IS_CHARGING      = "isCharging" ;
    public static final String KEY_LEVEL            = "level" ;

    private boolean isScreenOn = true ;
    private boolean isCharging = false ;
    private int level = 0;

    private void registerReceiver(){

        IntentFilter filter = new IntentFilter() ;

        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(ACTION_BATTERY_CHANGE);

        registerReceiver(receiver, filter) ;
    }

    private void unRegisterReceiver(){

        unregisterReceiver(receiver);
    }


    private BroadcastReceiver receiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction() ;
            if(Intent.ACTION_SCREEN_ON.equals(action)){

                isScreenOn = true ;

            }else if(Intent.ACTION_SCREEN_OFF.equals(action)){

                isScreenOn = false ;

            }else if(ACTION_BATTERY_CHANGE.equals(action)){

                isCharging = intent.getBooleanExtra(KEY_IS_CHARGING, false) ;
                level = intent.getIntExtra(KEY_LEVEL, -1) ;
            }

            refreshLed() ;

        }


    };


    private static final int CHARGING_FULL       = 0x00 ;
    private static final int CHARGING            = 0x01 ;
    private static final int NO_CHARGING_5       = 0x02 ;
    private static final int NO_CHARGING_10      = 0x03 ;
    private static final int NO_CHARGING_NORMAL  = 0x04 ;
    private static final int SCREEN_0FF          = 0x05 ;

    private int status = -1 ;
    private void refreshLed(){

        if(isScreenOn){

            if(isCharging){

                if(level == 100){

                    refreshLed(CHARGING_FULL) ;

                }else {

                    refreshLed(CHARGING);
                }
            }else{

                if(level <= 5 ){

                    refreshLed(NO_CHARGING_5) ;

                }else if( level <= 10){

                    refreshLed(NO_CHARGING_10) ;

                }else{

                    refreshLed(NO_CHARGING_NORMAL) ;
                }
            }
        }else {

            refreshLed(SCREEN_0FF) ;
        }
    }


    private void refreshLed(int status){

        if(this.status == status){

            return;
        }
        this.status = status ;
        switch (status){

            case CHARGING_FULL:

                //绿灯常亮
                mLedControl.setColor(LedControl.Color.value(0,255,0));
                mLedControl.setEffect(LedControl.Effect.NORMAL);
                sendLedControl() ;

                break;

            case CHARGING:

                //绿灯呼吸
                mLedControl.setColor(LedControl.Color.value(0,255,0));
                mLedControl.setEffect(LedControl.Effect.BREATH_MIDDLE);
                sendLedControl() ;
                break;

            case NO_CHARGING_5:

                //红灯常亮
                mLedControl.setColor(LedControl.Color.value(255,0,0));
                mLedControl.setEffect(LedControl.Effect.NORMAL);
                sendLedControl() ;
                break;

            case NO_CHARGING_10:

                //红灯慢闪
                mLedControl.setColor(LedControl.Color.value(255,0,0));
                mLedControl.setEffect(LedControl.Effect.BREATH_LOW);
                sendLedControl() ;
                break;

            case NO_CHARGING_NORMAL:

                //灭灯
//                mLedControl.setColor(LedControl.Color.value(0,0,255));
                mLedControl.setEffect(LedControl.Effect.LED_OFF);
                sendLedControl() ;
                break;

            case SCREEN_0FF:

                //蓝灯呼吸
                mLedControl.setColor(LedControl.Color.value(0,0,255));
                mLedControl.setEffect(LedControl.Effect.BREATH_MIDDLE);
                sendLedControl() ;
                break;
        }





    }


    private void sendLedControl(){

        LedClient.getInstance(this).sendLedControlInMainThread(mLedControl, null);
    }

}
