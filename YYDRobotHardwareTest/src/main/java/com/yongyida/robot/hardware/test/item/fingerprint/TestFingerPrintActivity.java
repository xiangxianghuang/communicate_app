package com.yongyida.robot.hardware.test.item.fingerprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

import cn.com.aratek.demo.DemoActivity;
import cn.com.aratek.demo.FingerprintDemo;

/**
 * Created by HuangXiangXiang on 2018/3/19.
 */
public class TestFingerPrintActivity extends TestBaseActivity {
    @Override
    protected View initContentView() {

        View view = mLayoutInflater.inflate(R.layout.activity_test_finger_print, null) ;

        return view;
    }

    @Override
    protected String getTips() {
        return null;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, FingerprintDemo.class) ;
//        Intent intent = new Intent(this, DemoActivity.class) ;
        startActivity(intent);

        finish();

    }
}
