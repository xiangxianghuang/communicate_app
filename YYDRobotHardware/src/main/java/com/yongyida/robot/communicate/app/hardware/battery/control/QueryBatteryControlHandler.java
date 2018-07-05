package com.yongyida.robot.communicate.app.hardware.battery.control;

import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;


import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.BaseControlHandler;
import com.yongyida.robot.communicate.app.hardware.battery.response.data.BatteryInfo;
import com.yongyida.robot.communicate.app.hardware.battery.send.data.QueryBattery;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.server.ServerService;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.model.y148.battery.control.Y148QueryBatteryControlHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;



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
 * Create By HuangXiangXiang 2018/6/19
 */
public abstract class QueryBatteryControlHandler extends BaseControlHandler<QueryBattery> {

    public static final String TAG = QueryBatteryControlHandler.class.getSimpleName() ;

    public static final String ACTION_BATTERY_CHANGE = "com.yongyida.robot.BATTERY_CHANGE" ;

    public static final String KEY_IS_CHARGING      = "isCharging" ;
    public static final String KEY_LEVEL            = "level" ;
    public static final String KEY_STATE            = "state" ;

    protected final BatteryInfo mBatteryInfo = new BatteryInfo() ;

    public QueryBatteryControlHandler(Context context){
        super(context);

        this.mContext = context ;

        startListenBattery(mOnBatteryChangeListener) ;
    }


    /**
     * 开始监控电池信息
     * */
    protected abstract void startListenBattery(OnBatteryChangeListener onBatteryChangeListener);

    /**
     * 发送电池状态信息改变
     * */
    private void sendYYDRobotBatteryChange(BatteryInfo batteryInfo){

        boolean isChange = mBatteryInfo.setBatteryInfo(batteryInfo);
        if(isChange){
            //反馈 只有数据变化才反馈
            responseYYDRobotBatteryService() ;
        }


        boolean isFactory = ServerService.isFactory() ;
        LogHelper.i(TAG, LogHelper.__TAG__() + ", isFactory : " + isFactory );
        if(!ServerService.isFactory()) {

            //发广播 为了保证数据准确 需要一直发送数据
            sendYYDRobotBatteryBroadcast(batteryInfo) ;
//            sendSystemBatteryBroadcast(batteryInfo) ;
        }

    }

    private void responseYYDRobotBatteryService(){

        LogHelper.i(TAG, LogHelper.__TAG__() + ", size : " + mResponseBatteryChangedListeners.size()) ;

        HashSet<String> deaths = new HashSet<>() ;
        for (Map.Entry<String, ResponseBatteryChangedListener> entry : mResponseBatteryChangedListeners.entrySet()){

            ResponseBatteryChangedListener batteryChangeListener = entry.getValue() ;
            int result = batteryChangeListener.responseBatteryChanged() ;
            LogHelper.i(TAG, LogHelper.__TAG__() + "result : " + result) ;
            if(result != 0){    //非 0 表示已经断开

                deaths.add(entry.getKey()) ;
            }
        }

        //移除已经断开的
        for (String death : deaths) {

            mResponseBatteryChangedListeners.remove(death) ;
        }
    }


    private void sendYYDRobotBatteryBroadcast(BatteryInfo batteryInfo){

        LogHelper.i(TAG, LogHelper.__TAG__() + ", batteryInfo : " +batteryInfo) ;

        //自定义广播
        Intent intent = new Intent(ACTION_BATTERY_CHANGE);
        intent.putExtra(KEY_IS_CHARGING , batteryInfo.isCharging()) ;
        intent.putExtra(KEY_LEVEL , batteryInfo.getLevel()) ;
        intent.putExtra(KEY_STATE , batteryInfo.getState()) ;
        mContext.sendBroadcast(intent);

    }

    /**
     *
     * 发送系统电池电量广播，这个需要系统权限才能发送
     * */
    private void sendSystemBatteryBroadcast(BatteryInfo batteryInfo){

        LogHelper.i(TAG, LogHelper.__TAG__() + ", batteryInfo : " +batteryInfo) ;

        try{

            Intent intent ;
            // 电量状态
            if(batteryInfo.isCharging()){//充电

                intent = new Intent(Intent.ACTION_POWER_CONNECTED) ;
                mContext.sendBroadcast(intent);

            }else{ //断开充电

                intent = new Intent(Intent.ACTION_POWER_DISCONNECTED) ;
                mContext.sendBroadcast(intent);
            }

            //电量
            intent = new Intent(Intent.ACTION_BATTERY_CHANGED) ;
            intent.putExtra(BatteryManager.EXTRA_LEVEL , batteryInfo.getLevel()) ;
            intent.putExtra(BatteryManager.EXTRA_SCALE , 100) ;
            mContext.sendBroadcast(intent);

        }catch (Exception e){

            LogHelper.e(TAG, LogHelper.__TAG__() + ", 发送系统广播异常 :" + e.getMessage()) ;
        }
    }


    @Override
    public SendResponse onHandler(QueryBattery queryBattery, final IResponseListener responseListener) {

        String tag = queryBattery.getTag() ;
        if(tag != null){

            if(responseListener == null){

                mResponseBatteryChangedListeners.remove(tag) ;

            }else {

                if(!mResponseBatteryChangedListeners.containsKey(tag)){

                    ResponseBatteryChangedListener sendBatteryChangeListener = new ResponseBatteryChangedListener(){
                        @Override
                        public int responseBatteryChanged() {

                            return responseListener.onResponse(mBatteryInfo.getResponse()) ;
                        }
                    };
                    mResponseBatteryChangedListeners.put(tag, sendBatteryChangeListener) ;
                }
            }
        }

        return mBatteryInfo.getResponse();
    }

    private HashMap<String, ResponseBatteryChangedListener> mResponseBatteryChangedListeners = new HashMap<>() ;
    public interface ResponseBatteryChangedListener{

        /**
         * @return 0 表示反馈成功
         *          1 表示反馈失败（一般是由于通讯断开）
         * */
        int responseBatteryChanged() ;
    }


    /**电池信息*/
    public interface OnBatteryChangeListener{

        void onBatteryChange(BatteryInfo batteryInfo) ;
    }

    private OnBatteryChangeListener mOnBatteryChangeListener = new OnBatteryChangeListener(){

        @Override
        public void onBatteryChange(BatteryInfo batteryInfo) {

            sendYYDRobotBatteryChange(batteryInfo) ;

        }
    };

}
