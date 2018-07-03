package com.yongyida.robot.hardware.test;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.yongyida.robot.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl;
import com.yongyida.robot.communicate.app.setting.send.data.FactoryConfig;
import com.yongyida.robot.hardware.test.data.ModelInfo;
import com.yongyida.robot.hardware.test.data.SettingData;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;
import com.yongyida.robot.hardware.test.item.fingerprint.TestFingerPrintActivity;
import com.yongyida.robot.hardware.test.item.idcard.TestIdCardActivity;
import com.yongyida.robot.hardware.test.item.key.TestKeyActivity;
import com.yongyida.robot.hardware.test.item.led.TestLed2Activity;
import com.yongyida.robot.hardware.test.item.microphone.TestMicrophoneActivity;
import com.yongyida.robot.hardware.test.item.motion.TestMotionActivity;
import com.yongyida.robot.hardware.test.item.pir.TestPirActivity;
import com.yongyida.robot.hardware.test.item.printer.TestPrinterActivity;
import com.yongyida.robot.hardware.test.item.touch.TestTouchActivity;
import com.yongyida.robot.hardware.test.item.wakeupmic.TestWakeUpMicActivity;

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
    private Switch mEnableSth;
    /**
     * Version:0.0.1
     */
    private TextView mVersionTvw;

    private boolean isOpen ;

    private FactoryConfig mFactoryConfig = new FactoryConfig() ;

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

        if(isOpen){

            mFactoryConfig.setFactory(true);
            SendClient.getInstance(this).send(null, mFactoryConfig, null);
        }

        mEnableSth.setChecked(isOpen);
        mEnableSth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                isOpen = isChecked ;
                SettingData.saveIsOpen(TestMainActivity.this,isOpen);

                mFactoryConfig.setFactory(isChecked);
                SendClient.getInstance(TestMainActivity.this).send(null, mFactoryConfig, null);
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
        mEnableSth = (Switch) findViewById(R.id.enable_sth);
        mItemGvw = (GridView) findViewById(R.id.item_gvw);
        mVersionTvw = (TextView) findViewById(R.id.version_tvw);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        if(isOpen){

            mFactoryConfig.setFactory(false);
            SendClient.getInstance(this).send(null, mFactoryConfig, null);
        }

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


        final String model = mModelInfo.getModel() ;

        List<ResolveInfo> temp = new ArrayList<>();
        final int size = resolveInfos.size() ;
        for (int i = 0 ; i < size ; i++){

            ResolveInfo resolveInfo = resolveInfos.get(i) ;
            final String name = resolveInfo.activityInfo.name ;
            if(TestIdCardActivity.class.getName().equals(name)){ // 身份证

                if(model.contains("Y165")){

                    temp.add(resolveInfo) ;
                }

            }else if(TestPrinterActivity.class.getName().equals(name)){//打印机

                if(model.contains("Y165")){

                    temp.add(resolveInfo) ;
                }

            }else if(TestFingerPrintActivity.class.getName().equals(name)){//指纹

                if(model.contains("Y165")){

                    temp.add(resolveInfo) ;
                }

            }else if(TestWakeUpMicActivity.class.getName().equals(name)){//唤醒麦

                if(model.contains("Y165")){

                    temp.add(resolveInfo) ;
                }

            }else if(TestTouchActivity.class.getName().equals(name)){// 触摸

                if(!model.contains("Y165")){

                    temp.add(resolveInfo) ;
                }

            }else if(TestMotionActivity.class.getName().equals(name)){// 动作

                if(!model.contains("Y165")){

                    temp.add(resolveInfo) ;
                }

            }else if(TestKeyActivity.class.getName().equals(name)){// 实体按键

                if(model.contains("YQ110")){

                    temp.add(resolveInfo) ;
                }

            }else if(TestLed2Activity.class.getName().equals(name)){// 灯带

                if(model.contains("Y138") || model.contains("Y148")){

                    temp.add(resolveInfo) ;
                }

            }else if(TestPirActivity.class.getName().equals(name)){// Pir 人体监测

                if(model.contains("Y165")){

                    temp.add(resolveInfo) ;
                }

            }else {

                temp.add(resolveInfo) ;
            }

        }


        return temp;
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
