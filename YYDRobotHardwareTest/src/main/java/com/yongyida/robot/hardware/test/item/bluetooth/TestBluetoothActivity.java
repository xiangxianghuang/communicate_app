package com.yongyida.robot.hardware.test.item.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

/**
 * Created by HuangXiangXiang on 2018/2/10.
 */
public class TestBluetoothActivity extends TestBaseActivity {

    private static final String TAG = TestBluetoothActivity.class.getSimpleName() ;

    /**
     * Wifi 信息
     */
    private TextView mBlueToothInfoTvw;
    /**
     * 扫描Wifi
     */
    private TextView mScanBlueToothTvw;
    /**
     * 查找结束
     */
    private TextView mBlueToothEndTvw;

    private BluetoothManager mBluetoothManager ;
    private BluetoothAdapter mBluetoothAdapter ;


    @Override
    protected View initContentView() {

        View view = LayoutInflater.from(this).inflate(R.layout.activity_test_bluetooth, null);
        initView(view);

        return view;
    }

    @Override
    protected String getTips() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter() ;

    }


    private void initView(View view) {

        mBlueToothInfoTvw = (TextView) view.findViewById(R.id.blue_tooth_info_tvw);
        mScanBlueToothTvw = (TextView) view.findViewById(R.id.scan_blue_tooth_tvw);
        mBlueToothEndTvw = (TextView) view.findViewById(R.id.blue_tooth_end_tvw);

        mScanBlueToothTvw.setMovementMethod(ScrollingMovementMethod.getInstance()); //
    }


    @Override
    public void onResume() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;
        super.onResume();
        testBlueTooth() ;
    }

    @Override
    public void onPause() {
        LogHelper.i(TAG , LogHelper.__TAG__()) ;
        super.onPause();

        unRegisterReceiver();
    }

    private boolean isStart ;
    private void testBlueTooth(){

        if(!mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.enable() ;
        }
        isStart = false ;
        registerReceiver();
        mBluetoothAdapter.disable() ;

        mBlueToothInfoTvw.setText(R.string.bluetooth_closing);
        mScanBlueToothTvw.setText("");
        mBlueToothEndTvw.setVisibility(View.INVISIBLE);
    }

    private static final String ACTION_FOUND                = "android.bluetooth.device.action.FOUND" ;
    private static final String ACTION_DISCOVERY_FINISHED   = BluetoothAdapter.ACTION_DISCOVERY_FINISHED ;
    private static final String ACTION_STATE_CHANGED        = BluetoothAdapter.ACTION_STATE_CHANGED;

    private void registerReceiver(){

        IntentFilter filter = new IntentFilter() ;
        filter.addAction(ACTION_FOUND);
        filter.addAction(ACTION_DISCOVERY_FINISHED);
        filter.addAction(ACTION_STATE_CHANGED);
        registerReceiver(mBluetoothBroadcastReceiver, filter) ;

    }
    private void unRegisterReceiver(){

        unregisterReceiver(mBluetoothBroadcastReceiver);
    }

    private boolean isFirst = true ;

    private BroadcastReceiver mBluetoothBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction() ;

            LogHelper.i(TAG , LogHelper.__TAG__() + ", action : " + action) ;

            if(ACTION_FOUND.equals(action)){

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                String name = device.getName() ;
                if(name == null){

                    name = getString(R.string.name_null) ;
                }else {
                    name = name.trim() ;// slamWare的蓝牙名称带换行符号
                }

//                mScanBlueToothTvw.append(device.getAddress() + "  -->  " + name + "\n");

                if(isFirst){
                    isFirst = false ;

                    appendTextView(mScanBlueToothTvw,device.getAddress() + "  -->  " + name );

                }else{

                    appendTextView(mScanBlueToothTvw,"\n" + device.getAddress() + "  -->  " + name );
                }

            }else if(ACTION_DISCOVERY_FINISHED.equals(action)){

                if(isStart){

                    mBlueToothEndTvw.setVisibility(View.VISIBLE);
                }

            }else if(ACTION_STATE_CHANGED.equals(action)){

                int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                switch(blueState){
                    case BluetoothAdapter.STATE_TURNING_ON:
                        mBlueToothInfoTvw.setText(R.string.bluetooth_opening);

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        break;
                    case BluetoothAdapter.STATE_ON:

                        mBlueToothInfoTvw.setText(R.string.bluetooth_opened);
                        mBluetoothAdapter.startDiscovery() ;

                        isStart = true ;

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:

                        mBlueToothInfoTvw.setText(R.string.bluetooth_closing);

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        break;
                    case BluetoothAdapter.STATE_OFF:

                        mBlueToothInfoTvw.setText(R.string.bluetooth_closed);
                        mBluetoothAdapter.enable() ;

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        break;
                }
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

    private void setTextView(TextView textView,String msg){
        textView.setText(msg);
        int offset=textView.getLineCount()*textView.getLineHeight();
        if(offset>textView.getHeight()){
            textView.scrollTo(0,offset-textView.getHeight());
        }
    }

}
