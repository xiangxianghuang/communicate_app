package com.yongyida.robot.hardware.test.item.wifi;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

import java.util.List;

/**
 * Created by HuangXiangXiang on 2018/2/25.
 */
public class TestWifiActivity extends TestBaseActivity implements View.OnClickListener {


    private WifiManager mWifiManager ;
    private String mConnectionSSID ;

    private Button mMifiTestBtn ;
    private TextView mWifiInfoTvw ;
    private TextView mScanWifiTvw ;

    @Override
    protected View initContentView() {

        View view = LayoutInflater.from(this).inflate(R.layout.activity_test_wifi, null) ;
        mMifiTestBtn = view.findViewById(R.id.mifi_test_btn) ;
        mWifiInfoTvw = view.findViewById(R.id.wifi_info_tvw) ;
        mScanWifiTvw = view.findViewById(R.id.scan_wifi_tvw) ;

        mMifiTestBtn.setOnClickListener(this);

        mScanWifiTvw.setMovementMethod(ScrollingMovementMethod.getInstance());

        return view;
    }

    @Override
    protected String getTips() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        initWifiInfo() ;
    }

    /**获取WIFI信息*/
    private void initWifiInfo(){

        //
        if(isOpenWifi()){
            WifiInfo wifiInfo = mWifiManager.getConnectionInfo() ;
            if(wifiInfo != null){
                mConnectionSSID = wifiInfo.getSSID() ;
                mConnectionSSID = mConnectionSSID.substring(1, mConnectionSSID.length()-1) ;

                mWifiInfoTvw.setText("WIFI 已经打开,已经连接 : " + mConnectionSSID + " WIFI。");

            }else{
                mConnectionSSID = null ;

                mWifiInfoTvw.setText("WIFI 已经打开,暂无连接WIFI。");
            }

            mScanWifiTvw.setText(getScanInfo());

        }else {
            mWifiInfoTvw.setText("WIFI 已经关闭");
            mScanWifiTvw.setText(null);
        }
    }

    private boolean isOpenWifi(){

        return mWifiManager.isWifiEnabled()  ;
    }

    public String getScanInfo() {
        mWifiManager.startScan();

        //得到扫描结果
        List<ScanResult> results = mWifiManager.getScanResults();
        StringBuilder sb = new StringBuilder() ;

        for(ScanResult result : results){
            if (result.SSID == null || result.SSID.length() == 0 ||
                    result.capabilities.contains("[IBSS]")||result.SSID.equals(mConnectionSSID)) {
                continue;
            }
            sb.append(result.SSID + "\n") ;
        }

        return sb.toString().trim() ;
    }

    @Override
    public void onClick(View v) {

        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)); //直接进入手机中的wifi网络设置界面
    }
}
