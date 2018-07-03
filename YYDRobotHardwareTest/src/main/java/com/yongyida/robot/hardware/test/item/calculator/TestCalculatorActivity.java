package com.yongyida.robot.hardware.test.item.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

/**
 * Created by HuangXiangXiang on 2018/3/8.
 */
public class TestCalculatorActivity extends TestBaseActivity implements View.OnClickListener {
    /**
     * 打开计算器
     */
    private Button mOpenCalculatorBtn;

    @Override
    protected View initContentView() {

        View view =mLayoutInflater.inflate(R.layout.activity_test_calculator, null) ;
        initView(view);

        return view;
    }

    @Override
    protected String getTips() {
        return null;
    }

    private void initView(View view) {
        mOpenCalculatorBtn = (Button) view.findViewById(R.id.open_calculator_btn);
        mOpenCalculatorBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == mOpenCalculatorBtn) {

            try{

                Intent mIntent = new Intent();
                mIntent.setClassName("com.android.calculator2","com.android.calculator2.Calculator");
                startActivity(mIntent);

            }catch (Exception e){

                Toast.makeText(this, "打开计算器失败",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
