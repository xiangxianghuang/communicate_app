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

import com.yongyida.robot.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.common.send.SendResponseListener;
import com.yongyida.robot.communicate.app.hardware.touch.response.data.TouchPosition;
import com.yongyida.robot.communicate.app.hardware.touch.send.data.QueryTouchPositionControl;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.data.ModelInfo;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.yongyida.robot.communicate.app.hardware.touch.response.data.TouchPosition.Position.BACK_HEAD;
import static com.yongyida.robot.communicate.app.hardware.touch.response.data.TouchPosition.Position.FORE_HEAD;
import static com.yongyida.robot.communicate.app.hardware.touch.response.data.TouchPosition.Position.LEFT_ARM;
import static com.yongyida.robot.communicate.app.hardware.touch.response.data.TouchPosition.Position.LEFT_SHOULDER;
import static com.yongyida.robot.communicate.app.hardware.touch.response.data.TouchPosition.Position.RIGHT_ARM;
import static com.yongyida.robot.communicate.app.hardware.touch.response.data.TouchPosition.Position.RIGHT_SHOULDER;

/**
 * Created by HuangXiangXiang on 2018/3/1.
 */
public class TestTouchActivity extends TestBaseActivity {

    private static final String TAG = TestTouchActivity.class.getSimpleName() ;

    private static String[] POSITION_NAMES;
    private static TouchPosition.Position[] POSITIONS;
    static{

        if(ModelInfo.getInstance().getModel().contains("YQ110")){

            POSITION_NAMES = new String[]{"前脑","后脑","左肩","右肩",} ;
            POSITIONS = new TouchPosition.Position[]{FORE_HEAD, BACK_HEAD, LEFT_SHOULDER, RIGHT_SHOULDER} ;

        }else {

            POSITION_NAMES = new String[]{"前脑","后脑","左肩","左手臂", "右肩","右手臂"} ;
            POSITIONS = new TouchPosition.Position[]{FORE_HEAD, BACK_HEAD, LEFT_SHOULDER, LEFT_ARM, RIGHT_SHOULDER, RIGHT_ARM} ;
        }
    }


    private LayoutInflater mLayoutInflater;

    private ListView mTouchLvw ;


    private HashMap<TouchPosition.Position,String> touchInfos = new HashMap<>() ;
    private void refreshTouchInfo(TouchPosition.Position position){

        String info = df.format(new Date()) + getString(R.string.touch_single) ;
        touchInfos.put(position, info) ;

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

        queryTouchInfo() ;

        registerReceiver() ;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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

                TouchPosition.Position point = TouchPosition.Position.valueOf(touchPoint) ;

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

        SendResponseListener responseListener = new SendResponseListener<TouchPosition>() {

            @Override
            public void onSuccess(final TouchPosition touchInfo) {

                LogHelper.i(TAG , LogHelper.__TAG__());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        TouchPosition.Position position = touchInfo.getPosition() ;
                        refreshTouchInfo(position);
                    }
                });

            }

            @Override
            public void onFail(int result, String message) {

                LogHelper.e(TAG , LogHelper.__TAG__());
            }

        };

        QueryTouchPositionControl queryTouchPositionControl = new QueryTouchPositionControl() ;
        SendClient.getInstance(TestTouchActivity.this).send(this, queryTouchPositionControl, responseListener) ;
    }



    private BaseAdapter mBaseAdapter = new BaseAdapter() {


        @Override
        public int getCount() {
            return POSITIONS.length;
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

            holder.setPosition(position) ;
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

            public void setPosition(int position) {

                mPositionTvw.setText(POSITION_NAMES[position]);
                mTouchInfoTvw.setText(touchInfos.get(POSITIONS[position]));
            }
        }

    };


}
