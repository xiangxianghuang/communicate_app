package com.yongyida.robot.hardware.test.item.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hiva.communicate.app.common.send.SendResponseListener;
import com.hiva.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.hardware.battery.response.data.BatteryInfo;
import com.yongyida.robot.communicate.app.hardware.battery.send.data.QueryBattery;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

import java.lang.ref.WeakReference;

/**
 * Created by HuangXiangXiang on 2018/2/24.
 * 测试充电状态以及电量
 */
public class TestBatteryActivity extends TestBaseActivity {

    private static final String TAG = TestBatteryActivity.class.getSimpleName() ;

    /**
     * 充电中
     */
    private TextView mBatteryStatusValue;
    /**
     * 88%
     */
    private TextView mBatteryLevelValue;
    private TextView mBatteryStateValue;


    private MyHandler myHandler ;
    private static class MyHandler extends Handler{

        private static final int BATTERY_INFO = 0x01 ; //获取数据值

        private final WeakReference<TestBatteryActivity> mWeakReference ;
        private MyHandler(TestBatteryActivity activity){
            mWeakReference = new WeakReference<>(activity) ;
        }

        @Override
        public void handleMessage(Message msg) {

            TestBatteryActivity activity = mWeakReference.get() ;
            if(activity != null){

                switch (msg.what){

                    case BATTERY_INFO:

                        BatteryInfo batteryInfo = (BatteryInfo) msg.obj;
                        activity.refreshBatteryInfo(batteryInfo);

                        break;

                    default:
                        break;

                }

            }

        }
    }


    @Override
    protected View initContentView() {

        View view = LayoutInflater.from(this).inflate(R.layout.activity_test_battery, null);

        mBatteryStatusValue = (TextView) view.findViewById(R.id.battery_status_value);
        mBatteryLevelValue = (TextView) view.findViewById(R.id.battery_level_value);
        mBatteryStateValue = (TextView) view.findViewById(R.id.battery_state_value);

        return view;
    }


    @Override
    protected String getTips() {
        return null;
    }


    //刷新数据
    private void refreshBatteryInfo(BatteryInfo batteryInfo){

        refreshBatteryInfo(batteryInfo.isCharging(), batteryInfo.getLevel(), batteryInfo.getState()) ;
    }

    private void refreshBatteryInfo(boolean isChange, int level, int state){

        if(isChange){

            mBatteryStatusValue.setText(R.string.battery_status_charging);
        }else {

            mBatteryStatusValue.setText(R.string.battery_status_discharging);
        }

        mBatteryLevelValue.setText(level + "%");
        mBatteryStateValue.setText(String.valueOf(state));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myHandler = new MyHandler(this) ;

        registerReceiver() ;
        queryBatteryInfo() ;

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver() ;

    }


    public static final String ACTION_BATTERY_CHANGE = "com.yongyida.robot.BATTERY_CHANGE" ;

    public static final String KEY_IS_CHARGING      = "isCharging" ;
    public static final String KEY_LEVEL            = "level" ;
    public static final String KEY_STATE            = "state" ;




    private void registerReceiver(){

        IntentFilter filter = new IntentFilter() ;
        filter.addAction(ACTION_BATTERY_CHANGE);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);

        registerReceiver(broadcastReceiver ,filter ) ;
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction() ;
            if(ACTION_BATTERY_CHANGE.equals(action)){

                boolean isCharging = intent.getBooleanExtra(KEY_IS_CHARGING , false) ;  //是否处于充电状态
                int level = intent.getIntExtra(KEY_LEVEL , -1) ;    //电量水平
                int state = intent.getIntExtra(KEY_STATE , -1) ;    //电量状态

                refreshBatteryInfo(isCharging, level,state) ;

            }else if(Intent.ACTION_BATTERY_CHANGED.equals(action)){

                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL/*当前电量*/, 0);
                int state = intent.getIntExtra(BatteryManager.EXTRA_STATUS/*电池状态*/,0);

                boolean isCharging = BatteryManager.BATTERY_STATUS_CHARGING == state ;

                refreshBatteryInfo(isCharging, level,state) ;

            }
        }
    };


    private void unregisterReceiver(){

        unregisterReceiver(broadcastReceiver);
    }

    private QueryBattery mQueryBattery = new QueryBattery() ;



    /**
     * 查询电池信息
     * */
    private void queryBatteryInfo(){

        SendResponseListener iResponseListener = new SendResponseListener<BatteryInfo>() {

            @Override
            public void onSuccess(BatteryInfo batteryInfo) {

            }

            @Override
            public void onFail(int result, String message) {

            }
        };

        SendClient.getInstance(this).send(mQueryBattery, iResponseListener);


//        new Thread(){
//
//            @Override
//            public void run() {
//
//
//                SendResponseListener responseListener = new SendResponseListener() {
//                    @Override
//                    public void onResponse(BaseResponse response) {
//
//                        LogHelper.i(TAG, LogHelper.__TAG__() + ", response : " + response );
//
//                        BaseResponseControl baseResponseControl = response.getBaseResponseControl();
//                        if(baseResponseControl instanceof BatteryInfo){
//
//                            BatteryInfo batteryInfo = (BatteryInfo) baseResponseControl;
//
//                            Message message = myHandler.obtainMessage(MyHandler.BATTERY_INFO) ;
//                            message.obj = batteryInfo ;
//                            myHandler.sendMessage(message) ;
//                        }
//
//                    }
//                };
//
//                QueryBattery queryBattery = new QueryBattery() ;
//                SendResponse sendResponse = SendClient.getInstance(TestBatteryActivity.this).sendInNotMainThread(queryBattery, responseListener) ;
//                if (sendResponse == null) {
//
//                    // 发送不成功
//                    LogHelper.i(TAG, LogHelper.__TAG__());
////                    Toast.makeText(TestBatteryActivity.this, "发送失败，返回为空！", Toast.LENGTH_LONG).show();
//
//                }else{
//
//                    int result = sendResponse.getResult() ;
//                    if(result != SendResponse.RESULT_SUCCESS)
//
//                        // 发送不成功
//                        LogHelper.i(TAG, LogHelper.__TAG__() + ", result : " + result);
////                        Toast.makeText(TestBatteryActivity.this,"发送失败，返回为:" + result , Toast.LENGTH_LONG).show(); ;
//                    }
//
//            }
//        }.start();

    }

}
