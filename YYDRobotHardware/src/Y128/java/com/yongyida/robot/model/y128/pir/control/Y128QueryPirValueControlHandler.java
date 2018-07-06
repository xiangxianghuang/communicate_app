package com.yongyida.robot.model.y128.pir.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.yongyida.robot.communicate.app.hardware.motion.send.data.FootControl;
import com.yongyida.robot.communicate.app.hardware.pir.control.QueryPirValueControlHandler;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.model.agreement.Y165Receive;



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
 * Create By HuangXiangXiang 2018/7/2
 */
public class Y128QueryPirValueControlHandler extends QueryPirValueControlHandler{

    private static final String TAG = Y128QueryPirValueControlHandler.class.getSimpleName() ;

    public Y128QueryPirValueControlHandler(Context context) {
        super(context);
        LogHelper.i(TAG, LogHelper.__TAG__() + "context : " + context);
    }

    @Override
    protected void startListenPir(OnPirValueChangedListener onTouchPositionListener) {

        LogHelper.i(TAG, LogHelper.__TAG__() + "mContext : " + mContext);

        registerReceiver();
    }


    private void registerReceiver(){

        LogHelper.i(TAG, LogHelper.__TAG__() + "mContext : " + mContext);

        IntentFilter filter = new IntentFilter() ;
        filter.addAction(ACTION);

        BroadcastReceiver mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                final String action = intent.getAction() ;
                LogHelper.i(TAG, LogHelper.__TAG__() + ", action : " + action);

                if(ACTION.equals(action)){

                    String value = intent.getStringExtra(KEY)             ;
                    LogHelper.i(TAG, LogHelper.__TAG__() + ", value : " + value);

                    if(VALUE.equals(value)){

                        LogHelper.i(TAG, LogHelper.__TAG__() + ", mOnPirValueChangedListener : " + mOnPirValueChangedListener);
                        if(mOnPirValueChangedListener != null){

                            mOnPirValueChangedListener.onPirValueChanged(true, -1);
                        }
                    }

                }
            }
        };
        mContext.registerReceiver(mReceiver, filter) ;
    }

    private final static String ACTION = "TouchSensor" ;
    private final static String KEY = "android.intent.extra.Touch" ;
    private final static String VALUE = "pir" ;



}
