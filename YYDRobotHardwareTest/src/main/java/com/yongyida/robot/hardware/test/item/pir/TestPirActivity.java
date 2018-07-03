package com.yongyida.robot.hardware.test.item.pir;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.yongyida.robot.communicate.app.common.response.BaseResponseControl;
import com.yongyida.robot.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.common.send.SendResponseListener;
import com.yongyida.robot.communicate.app.hardware.pir.response.data.PirValue;
import com.yongyida.robot.communicate.app.hardware.pir.send.data.QueryPirValueControl;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HuangXiangXiang on 2018/3/3.
 */
public class TestPirActivity extends TestBaseActivity {

    private static final String TAG = TestPirActivity.class.getSimpleName() ;

    private TextView mMonitorResultTvw ;
    @Override
    protected View initContentView() {

        View view = mLayoutInflater.inflate(R.layout.activity_test_pir, null) ;
        mMonitorResultTvw = view.findViewById(R.id.monitor_result_tvw) ;
        mMonitorResultTvw.setMovementMethod(ScrollingMovementMethod.getInstance()); //可以滑动

        return view;
    }

    @Override
    protected String getTips() {
        return getString(R.string.pir_tips);
    }


    private QueryPirValueControl mQueryPirValueControl = new QueryPirValueControl() ;
    private SendResponseListener mSendResponseListener = new SendResponseListener< PirValue>(){

        @Override
        public void onSuccess(final PirValue pirValue) {

            if(pirValue != null){

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        int distance = pirValue.getPeopleDistance() ;
                        String info;
                        if(mMonitorResultTvw.getText().length() > 0){

                            info = "\n" ;

                        }else{
                            info = "" ;
                        }

                        if(distance > 0){

                            info += df.format(new Date()) +distance + "厘米处"+getString(R.string.pir_monitor_people ) ;
                        }else {

                            info += df.format(new Date()) + getString(R.string.pir_monitor_people ) ;
                        }

                        appendTextView(mMonitorResultTvw, info) ;

                    }
                });

            }

        }

        @Override
        public void onFail(int result, String message) {

        }
    } ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        registerReceiver() ;

        SendClient.getInstance(this).send(this,mQueryPirValueControl,mSendResponseListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver();
    }

    private void registerReceiver(){

        IntentFilter filter = new IntentFilter(ACTION_FIND_PEOPLE) ;
        filter.addAction(ACTION_FIND_PEOPLE);
        filter.addAction(ACTION_MONITOR_PERSON);

        registerReceiver(mHumanInductionReceiver, filter) ;
    }

    private void unregisterReceiver(){

        unregisterReceiver(mHumanInductionReceiver);
    }



    private static final String ACTION_FIND_PEOPLE  = "TouchSensor";
    private static final String KEY_FIND_PEOPLE  = "android.intent.extra.Touch";
    private static final String VALUE_FIND_PEOPLE  = "pir";


    public static final String ACTION_MONITOR_PERSON = "com.yongyida.robot.PIR_VALUE" ;
    public static final String KEY_DISTANCE      = "peopleDistance" ;


    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ") ;

    private BroadcastReceiver mHumanInductionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction() ;
            LogHelper.i(TAG, LogHelper.__TAG__() + " ,action : " + action) ;
            if(ACTION_FIND_PEOPLE.equals(action)){

                String value = intent.getStringExtra(KEY_FIND_PEOPLE) ;
                LogHelper.i(TAG, LogHelper.__TAG__() + " ,value : " + value) ;

                if(VALUE_FIND_PEOPLE.equals(value)){

                    String info;
                    if(mMonitorResultTvw.getText().length() > 0){

                        info = "\n" ;

                    }else{
                        info = "" ;
                    }

                    info += df.format(new Date()) + getString(R.string.pir_monitor_people) ;
                    appendTextView(mMonitorResultTvw, info) ;
                }
            }else if(ACTION_MONITOR_PERSON.equals(action)){

                int distance = intent.getIntExtra(KEY_DISTANCE, -1) ;

                String info;
                if(mMonitorResultTvw.getText().length() > 0){

                    info = "\n" ;

                }else{
                    info = "" ;
                }

                if(distance > 0){

                    info += df.format(new Date()) +distance + "厘米处"+getString(R.string.pir_monitor_people ) ;
                }else {

                    info += df.format(new Date()) + getString(R.string.pir_monitor_people ) ;
                }

                appendTextView(mMonitorResultTvw, info) ;
            }
        }
    };


    private void appendTextView(TextView textView,String msg){
        textView.append(msg);
        int offset=textView.getLineCount()*textView.getLineHeight();
        if(offset>textView.getHeight()){
            textView.scrollTo(0,offset-textView.getHeight());
        }
    }



}
