package com.yongyida.robot.hardware.test.item.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yongyida.robot.hardware.test.item.TestBaseActivity;

/**
 * Created by HuangXiangXiang on 2018/3/8.
 */
public class TestCalculatorActivity extends TestBaseActivity {
    @Override
    protected View initContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent mIntent = new Intent();
        mIntent.setClassName("com.android.calculator2","com.android.calculator2.Calculator");
        startActivity(mIntent);

        finish();
    }

    @Override
    protected String getTips() {
        return null;
    }
}
