package com.yongyida.robot.hardware.test;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.yongyida.robot.hardware.test.data.ModelInfo;
import com.yongyida.robot.hardware.test.data.SettingData;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuangXiangXiang on 2017/12/12.
 */
public class TestMainActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private GridView mItemGvw;

    private List<ResolveInfo> mResolveInfos;
    /**
     * 退出工厂模式
     */
    private Button mBackBtn;
    /**
     * 选择打开的，所有功能只能在工厂模式,外面的不能调用
     */
    private SwitchCompat mEnableSct;
    /**
     * Version:0.0.1
     */
    private TextView mVersionTvw;

    private static boolean isOpen ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        mResolveInfos = queryResolveInfo();
        initView();

        //设置版本信息
        mVersionTvw.setText("版本名称 : " + getVersionName(this));
        mVersionTvw.setOnClickListener(this);

        isOpen = SettingData.isOpen(this);
        mEnableSct.setChecked(isOpen);
        mEnableSct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                isOpen = isChecked ;
                SettingData.saveIsOpen(TestMainActivity.this,isOpen);
            }
        });

        String cpuName = mModelInfo.getCpuName();
        if(cpuName != null && cpuName.startsWith("MT")){

        }else{

            checkPermission(this) ;
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        mBaseAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mItemGvw = findViewById(R.id.item_gvw);
        mItemGvw.setAdapter(mBaseAdapter);
        mItemGvw.setOnItemClickListener(this);

        mBackBtn = (Button) findViewById(R.id.back_btn);
        mBackBtn.setOnClickListener(this);
        mEnableSct = (SwitchCompat) findViewById(R.id.enable_sct);
        mItemGvw = (GridView) findViewById(R.id.item_gvw);
        mVersionTvw = (TextView) findViewById(R.id.version_tvw);
    }


    private void checkPermission(Activity context){

        if ( ContextCompat.checkSelfPermission(context, Manifest.permission_group.LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context,Manifest.permission_group.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context,Manifest.permission_group.MICROPHONE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context,Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    }, 1);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            for (int grantResult : grantResults){

                if (grantResult != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, R.string.toast_permissions,Toast.LENGTH_LONG).show();

                    finish();
                }
            }

        }
    }



    private List<ResolveInfo> queryResolveInfo() {

        Intent hardwareIntent = new Intent("com.yongyida.robot.HARDWARE");
        List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(hardwareIntent, 0);

        String model = mModelInfo.getModel() ;
        if(model.contains("YQ110")){    // YQ110 屏蔽身份证、打印机、指纹、麦克风

            List<ResolveInfo> temp = new ArrayList<>();

            final int size = resolveInfos.size() ;
            for (int i = 0 ; i < size ; i++){

                ResolveInfo resolveInfo = resolveInfos.get(i) ;
                if(!resolveInfo.activityInfo.name.equals("com.yongyida.robot.hardware.test.item.idcard.TestIdCardActivity") &&
                        !resolveInfo.activityInfo.name.equals("com.yongyida.robot.hardware.test.item.printer.TestPrinterActivity") &&
                        !resolveInfo.activityInfo.name.equals("com.yongyida.robot.hardware.test.item.microphone.TestMicrophoneActivity") &&
                        !resolveInfo.activityInfo.name.equals("com.yongyida.robot.hardware.test.item.fingerprint.TestFingerPrintActivity")){

                    temp.add(resolveInfo) ;
                }
            }
            resolveInfos = temp ;

        }else if(model.contains("Y165")){

            List<ResolveInfo> temp = new ArrayList<>();

            final int size = resolveInfos.size() ;
            for (int i = 0 ; i < size ; i++){

                ResolveInfo resolveInfo = resolveInfos.get(i) ;
                if(!resolveInfo.activityInfo.name.equals("com.yongyida.robot.hardware.test.item.touch.TestTouchActivity") &&    // 触摸
                        !resolveInfo.activityInfo.name.equals("com.yongyida.robot.hardware.test.item.key.TestKeyActivity")){    // 实体按键

                    temp.add(resolveInfo) ;
                }
            }
            resolveInfos = temp ;

        }



        return resolveInfos;
    }


    private BaseAdapter mBaseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {

            return mResolveInfos.size();
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
            if (convertView == null) {
                convertView = LayoutInflater.from(TestMainActivity.this).inflate(R.layout.item_text, null);
                textView = convertView.findViewById(R.id.text_tvw);

                convertView.setTag(textView);

            } else {

                textView = (TextView) convertView.getTag();
            }

            ResolveInfo resolveInfo = mResolveInfos.get(position);
            String label = (String) resolveInfo.loadLabel(getPackageManager()); // 获得应用程序的Label

            int status = SettingData.getStatus(TestMainActivity.this,resolveInfo.activityInfo.name) ;
            if(status == SettingData.STATUS_SUCCESS){
                textView.setTextColor(getResources().getColor(R.color.colorSuccess));
            }else if(status == SettingData.STATUS_FAIL){
                textView.setTextColor(getResources().getColor(R.color.colorFail));
            }else {
                textView.setTextColor(getResources().getColor(R.color.colorInit));
            }
            textView.setText(label);

            return convertView;
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        ResolveInfo reInfo = mResolveInfos.get(position);
        String packageName = reInfo.activityInfo.packageName; // 获得应用程序的包名
        String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name

        Intent intent = new Intent();
        intent.setClassName(packageName, activityName);

        String label = (String) reInfo.loadLabel(getPackageManager()); // 获得应用程序的Label
        intent.putExtra(TestBaseActivity.TITLE , label) ;

        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        if (v == mBackBtn) {

            finish();

        }else if (v == mVersionTvw) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this) ;
            builder.setTitle("详细信息") ;
            String message = mVersionTvw.getText().toString() + "\n" + mModelInfo.toString() ;

            builder.setMessage(message) ;
            builder.create().show();

        } else {

        }
    }


    /**
     * 获取版本名称
     * */
    public static String getVersionName(Context context) {

        String localVersion = "";
        try {
            PackageInfo packageInfo = context
                    .getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    public ModelInfo mModelInfo = ModelInfo.getInstance() ;
}
