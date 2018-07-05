package com.yongyida.robot.hardware.test.item.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.yongyida.robot.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.hardware.test.data.ModelInfo;



/* 
                              _ooOoo_ 
                             o8888888o 
                             88" . "88 
                             (| -_- |) 
                             O\  =  /O 
                          ____/`---'\____ 
                        .'  \\|     |//  `. 
                       /  \\|||  :  |||//  \ 
                      /  _||||| -:- |||||-  \ 
                      |   | \\\  -  /// |   | 
                      | \_|  ''\---/''  |   | 
                      \  .-\__  `-`  ___/-. / 
                    ___`. .'  /--.--\  `. . __ 
                 ."" '<  `.___\_<|>_/___.'  >'"". 
                | | :  `- \`.;`\ _ /`;.`/ - ` : | | 
                \  \ `-.   \_ __\ /__ _/   .-` /  / 
           ======`-.____`-.___\_____/___.-`____.-'====== 
                              `=---=' 
           .............................................  
                    佛祖镇楼                  BUG辟易  
            佛曰:  
                    写字楼里写字间，写字间里程序员；  
                    程序人员写程序，又拿程序换酒钱。  
                    酒醒只在网上坐，酒醉还来网下眠；  
                    酒醉酒醒日复日，网上网下年复年。  
                    但愿老死电脑间，不愿鞠躬老板前；  
                    奔驰宝马贵者趣，公交自行程序员。  
                    别人笑我忒疯癫，我笑自己命太贱；  
                    不见满街漂亮妹，哪个归得程序员？ 
*/

/**
 * Create By HuangXiangXiang 2018/7/4
 * 此类用于YQ110 修改呼吸灯的变化
 */
public class BatteryReceiver extends BroadcastReceiver {

    private static final String TAG = BatteryReceiver.class.getSimpleName() ;

    public static final String ACTION_BATTERY_CHANGE = "com.yongyida.robot.BATTERY_CHANGE" ;

    public static final String KEY_IS_CHARGING      = "isCharging" ;
    public static final String KEY_LEVEL            = "level" ;

    private static boolean isCharging = false ;    //是否充电
    private static int level = -1 ;                //电量


    @Override
    public void onReceive(Context context, Intent intent) {

        LogHelper.i(TAG, LogHelper.__TAG__());
        if(!ModelInfo.getInstance().getModel().contains("YQ110")){

            return;
        }

        if(ACTION_BATTERY_CHANGE.equals(intent.getAction())){

            boolean isCharging = intent.getBooleanExtra(KEY_IS_CHARGING, false) ;
            int level = intent.getIntExtra(KEY_LEVEL, -1) ;

            if(BatteryReceiver.isCharging != isCharging || BatteryReceiver.level != level){

                BatteryReceiver.isCharging = isCharging ;
                BatteryReceiver.level = level ;

                LedSendControl ledSendControl = new LedSendControl() ;
                ledSendControl.setPosition(LedSendControl.Position.CHEST);

                if(isCharging){

                    if(level >= 100){   //绿色常亮

                        ledSendControl.setEffect(LedSendControl.Effect.LED_ON);
                        ledSendControl.setColor(Color.GREEN);

                    }else{  //绿色呼吸

                        ledSendControl.setEffect(LedSendControl.Effect.BREATH_MIDDLE);
                        ledSendControl.setColor(Color.GREEN);
                    }

                }else {

                    if(level <= 5){ // 红色常亮

                        ledSendControl.setEffect(LedSendControl.Effect.LED_ON);
                        ledSendControl.setColor(Color.RED);


                    }else if(level <= 10){//红色慢闪

                        ledSendControl.setEffect(LedSendControl.Effect.BREATH_LOW);
                        ledSendControl.setColor(Color.RED);

                    }else{//灭灯

                        ledSendControl.setEffect(LedSendControl.Effect.LED_OFF);
                    }
                }

                SendClient.getInstance(context).send(null,ledSendControl,null );

            }

        }


    }





}
