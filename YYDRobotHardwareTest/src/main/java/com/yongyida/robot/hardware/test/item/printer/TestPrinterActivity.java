package com.yongyida.robot.hardware.test.item.printer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.usbprintertest.MainActivity;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

import cn.com.aratek.demo.DemoActivity;

/**
 * Created by HuangXiangXiang on 2018/3/19.
 */
public class TestPrinterActivity extends TestBaseActivity {
    @Override
    protected View initContentView() {

        View view = mLayoutInflater.inflate(R.layout.activity_test_printer, null) ;

        return view;
    }

    @Override
    protected String getTips() {
        return null;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class) ;
        startActivity(intent);

        finish();

    }
}
