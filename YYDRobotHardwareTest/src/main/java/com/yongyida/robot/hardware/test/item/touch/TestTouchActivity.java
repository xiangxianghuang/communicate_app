package com.yongyida.robot.hardware.test.item.touch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hiva.communicate.app.common.send.SendResponseListener;
import com.hiva.communicate.app.common.SendResponse;
import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.response.BaseResponseControl;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.touch.send.data.QueryTouchInfo;
import com.yongyida.robot.communicate.app.hardware.touch.response.data.TouchInfo;
import com.hiva.communicate.app.common.send.SendClient;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by HuangXiangXiang on 2018/3/1.
 */
public class TestTouchActivity extends TestBaseActivity {

    private static final String TAG = TestTouchActivity.class.getSimpleName() ;


    private LayoutInflater mLayoutInflater;

    private ListView mTouchLvw ;

    private TouchHandler mTouchHandler ;
    private static class TouchHandler extends Handler{

        private static final int TOUCH_INFO = 0x01 ; //获取数据值

        private final WeakReference<TestTouchActivity> mWeakReference ;
        private TouchHandler(TestTouchActivity activity){
            mWeakReference = new WeakReference<>(activity) ;
        }

        @Override
        public void handleMessage(Message msg) {

            TestTouchActivity activity = mWeakReference.get() ;
            if(activity != null){

                switch (msg.what){

                    case TOUCH_INFO:

                        TouchInfo touchInfo = (TouchInfo) msg.obj;
                        activity.mPositions = null ;
                        if(touchInfo != null){

                            activity.mPositions = touchInfo.getPositions() ;
                        }
                        activity.refreshTouchInfo();

                        break;

                    default:
                        break;


                }

            }

        }
    }


    private ArrayList<TouchInfo.Position> mPositions ;
    /**
     * 刷新触摸点信息
     * */
    private void refreshTouchInfo(){

        mBaseAdapter.notifyDataSetChanged();
    }

    private HashMap<TouchInfo.Point,String> touchInfos = new HashMap<>() ;
    private void refreshTouchInfo(TouchInfo.Point point){

        String info = df.format(new Date()) + getString(R.string.touch_single) ;
        touchInfos.put(point, info) ;

        mBaseAdapter.notifyDataSetChanged();
    }

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ") ;

        @Override
    protected View initContentView() {

        View view = LayoutInflater.from(this).inflate(R.layout.activity_test_touch, null);

        mTouchLvw = view.findViewById(R.id.touch_lvw) ;
        mTouchLvw.setAdapter(mBaseAdapter);

        return view;
    }

    @Override
    protected String getTips() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLayoutInflater = LayoutInflater.from(this);
        mTouchHandler = new TouchHandler(this) ;

        startTest() ;

        queryTouchInfo() ;

        registerReceiver() ;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopTest();
        unRegisterReceiver() ;
    }


    public static final String ACTION_TOUCH = "com.yongyida.robot.TOUCH" ;
    public static final String ACTION_TEST_TOUCH = "com.yongyida.robot.TEST_TOUCH" ;
    public static final String KEY_TOUCH_POINT = "touch_point" ;

    private void registerReceiver(){

        IntentFilter filter = new IntentFilter() ;
        filter.addAction(ACTION_TOUCH);
        filter.addAction(ACTION_TEST_TOUCH);

        registerReceiver(broadcastReceiver ,filter ) ;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction() ;
            if(ACTION_TOUCH.equals(action) | ACTION_TEST_TOUCH.equals(action)){

                String touchPoint = intent.getStringExtra(KEY_TOUCH_POINT) ;

                LogHelper.i(TAG, LogHelper.__TAG__() + ",touchPoint : " + touchPoint);

                TouchInfo.Point point = TouchInfo.Point.valueOf(touchPoint) ;

                refreshTouchInfo(point);
            }
        }
    };

    private void unRegisterReceiver(){

        unregisterReceiver(broadcastReceiver);
    }


    /**
     * */
    private void queryTouchInfo(){

        new Thread(){

            @Override
            public void run() {



                SendResponseListener responseListener = new SendResponseListener<TouchInfo>() {

                    @Override
                    public void onSuccess(TouchInfo touchInfo) {

                        Message message = mTouchHandler.obtainMessage(TouchHandler.TOUCH_INFO) ;
                        message.obj = touchInfo ;
                        mTouchHandler.sendMessage(message) ;
                    }

                    @Override
                    public void onFail(int result, String message) {

                    }

                };

                QueryTouchInfo queryTouchInfo = new QueryTouchInfo() ;
                SendResponse sendResponse = SendClient.getInstance(TestTouchActivity.this).sendInNotMainThread(queryTouchInfo, responseListener) ;
                if (sendResponse == null) {

                    // 发送不成功
                    LogHelper.i(TAG, LogHelper.__TAG__());
//                    Toast.makeText(TestBatteryActivity.this, "发送失败，返回为空！", Toast.LENGTH_LONG).show();

                }else{

                    int result = sendResponse.getResult() ;
                    if(result != SendResponse.RESULT_SUCCESS)

                        // 发送不成功
                        LogHelper.i(TAG, LogHelper.__TAG__() + ", result : " + result);
//                        Toast.makeText(TestBatteryActivity.this,"发送失败，返回为:" + result , Toast.LENGTH_LONG).show(); ;
                }

            }
        }.start();

    }


    private void startTest(){

//        TestData testData = new TestData() ;
//        testData.setTest(true);
//        TouchSend touchSend = new TouchSend() ;
//        touchSend.setTestData(testData);
//
//        TouchClient.getInstance(TestTouchActivity.this).sendInMainThread(touchSend, null);

    }

    private void stopTest(){

//        TestData testData = new TestData() ;
//        testData.setTest(false);
//        TouchSend touchSend = new TouchSend() ;
//        touchSend.setTestData(testData);
//
//        TouchClient.getInstance(TestTouchActivity.this).sendInMainThread(touchSend, null);
    }


    private BaseAdapter mBaseAdapter = new BaseAdapter() {


        @Override
        public int getCount() {
            return mPositions == null ? 0 : mPositions.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {

                convertView = mLayoutInflater.inflate(R.layout.item_touch, null);
                holder = new ViewHolder(convertView) ;

                convertView.setTag(holder);
            }else{

                holder = (ViewHolder) convertView.getTag();
            }


            holder.setPosition(mPositions.get(position)) ;

            return convertView;
        }




        class ViewHolder {
            View view;
            TextView mPositionTvw;
            TextView mTouchInfoTvw;

            ViewHolder(View view) {
                this.view = view;
                this.mPositionTvw = (TextView) view.findViewById(R.id.position_tvw);
                this.mTouchInfoTvw = (TextView) view.findViewById(R.id.touch_info_tvw);
            }

            public void setPosition(TouchInfo.Position position) {

                mPositionTvw.setText(position.getName());
                mTouchInfoTvw.setText(touchInfos.get(position.getPoint()));
            }
        }

    };


}
