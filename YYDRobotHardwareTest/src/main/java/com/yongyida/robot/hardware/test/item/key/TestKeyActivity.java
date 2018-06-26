package com.yongyida.robot.hardware.test.item.key;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

import java.util.HashSet;

/**
 * Created by HuangXiangXiang on 2018/3/19.
 */
public class TestKeyActivity extends TestBaseActivity {

    private static final String TAG = TestKeyActivity.class.getSimpleName();

    private String[] KEY_NAMES = {"减音", "增音", "电源" } ;
    private int[] KEY_CODES = {KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_POWER } ;

    private HashSet<Integer> mResults = new HashSet<>() ;

    private ListView mKeyLvw ;

    @Override
    protected View initContentView() {

        View view = mLayoutInflater.inflate(R.layout.activity_test_key, null) ;
        mKeyLvw = view.findViewById(R.id.key_lvw) ;

        mKeyLvw.setAdapter(mBaseAdapter);

        return view;
    }

    @Override
    protected String getTips() {
        return null;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unRegisterReceiver();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        LogHelper.i(TAG,  LogHelper.__TAG__() + " keyCode : " + keyCode);

        mResults.add(keyCode);
        mBaseAdapter.notifyDataSetChanged();

        return true;
    }

    private BaseAdapter mBaseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return KEY_NAMES.length;
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

            TextView textView;
            if(convertView == null){

                convertView = mLayoutInflater.inflate(R.layout.item_text, null) ;
                textView = convertView.findViewById(R.id.text_tvw) ;

                convertView.setTag(textView);

            }else{

                textView = (TextView) convertView.getTag();
            }

            textView.setText(KEY_NAMES[position]);

            boolean result = mResults.contains(KEY_CODES[position]) ;
            if(result){

//                textView.setBackgroundColor(R.color.colorSuccess);
                textView.setBackgroundColor(getResources().getColor(R.color.colorSuccess));

            }else {

//                textView.setBackgroundColor(R.color.colorFail);
                textView.setBackgroundColor(0x80ffffff);
            }

            return convertView;
        }

    };


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction() ;
            LogHelper.i(TAG, LogHelper.__TAG__() + " action : " + action) ;

             if(Intent.ACTION_SCREEN_ON.equals(action) ||
                    Intent.ACTION_SCREEN_OFF.equals(action) ){

                 mResults.add(KeyEvent.KEYCODE_POWER);
                 mBaseAdapter.notifyDataSetChanged();

            }

        }
    } ;

    private void registerReceiver() {

        IntentFilter mFilter = new IntentFilter() ;

        mFilter.addAction(Intent.ACTION_SCREEN_ON);
        mFilter.addAction(Intent.ACTION_SCREEN_OFF);

        registerReceiver(mReceiver, mFilter);
    }

    private void unRegisterReceiver(){

        unregisterReceiver(mReceiver);
    }

}
