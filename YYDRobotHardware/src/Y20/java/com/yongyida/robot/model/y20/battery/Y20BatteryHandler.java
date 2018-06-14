package com.yongyida.robot.model.y20.battery;

import android.content.Context;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.send.data.BaseSendControl;
import com.hiva.communicate.app.server.IResponseListener;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.battery.BatteryHandler;
import com.yongyida.robot.communicate.app.hardware.battery.response.data.BatteryInfo;
import com.yongyida.robot.communicate.app.hardware.battery.send.BatterySend;
import com.yongyida.robot.communicate.app.hardware.battery.send.data.QueryBattery;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;



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
 * Create By HuangXiangXiang 2018/6/8
 */
public class Y20BatteryHandler extends BatteryHandler {

    private final static String TAG = Y20BatteryHandler.class.getSimpleName() ;


    private BatteryInfo batteryInfo = new BatteryInfo() ;


    public Y20BatteryHandler(Context context) {
        super(context);

        new Thread(){

            @Override
            public void run() {

                while (true){

                    batteryInfo.setCharging(new Random().nextInt(2) == 1);
                    batteryInfo.setLevel(new Random().nextInt(100));
                    batteryInfo.setState(new Random().nextInt(100));

                    send() ;

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


        }.start();

    }

    private synchronized void send(){

        for (BatteryInfoChangedListener batteryInfoChangedListener : mBatteryInfoChangedListener.values()) {

            LogHelper.i(TAG ,LogHelper.__TAG__() + "batteryInfo : " + batteryInfo);
            batteryInfoChangedListener.onBatteryInfoChanged(batteryInfo);
        }


        for(String death : mDeath){

            mBatteryInfoChangedListener.remove(death) ;
        }
        mDeath.clear();

    }



    private HashSet<String> mDeath = new HashSet<>() ;
    private HashMap<String, BatteryInfoChangedListener> mBatteryInfoChangedListener = new HashMap<>() ;

    interface BatteryInfoChangedListener{

        void onBatteryInfoChanged(BatteryInfo batteryInfo) ;
    }

    @Override
    public BaseResponse onHandler(final BatterySend send, final IResponseListener responseListener) {

        BaseSendControl baseSendControl = send.getBaseControl() ;
        if(baseSendControl instanceof QueryBattery){

            QueryBattery queryBattery = (QueryBattery) baseSendControl;
            LogHelper.i(TAG ,LogHelper.__TAG__() );

            final String tag = queryBattery.getTag() ;
            if(responseListener == null){

                mBatteryInfoChangedListener.remove(tag);

            }else {

                mBatteryInfoChangedListener.put(tag, new BatteryInfoChangedListener() {

                    @Override
                    public void onBatteryInfoChanged(BatteryInfo batteryInfo) {

                        LogHelper.i(TAG ,LogHelper.__TAG__() + "batteryInfo : " + batteryInfo);
                        int result = responseListener.onResponse(batteryInfo.getResponse());
                        if(result == 1){

                            mDeath.add(tag) ;
                        }
                    }
                });

            }


            LogHelper.i(TAG ,LogHelper.__TAG__() + "batteryInfo : " + batteryInfo);
            return batteryInfo.getResponse() ;
        }


        return null;
    }
}
