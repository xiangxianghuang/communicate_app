package com.yongyida.robot.hardware.test.item.hand;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

/**
 * Created by HuangXiangXiang on 2018/4/20.
 * 手臂 运动
 */
public class TestHandActivity extends TestBaseActivity implements RadioGroup.OnCheckedChangeListener {



    private RadioGroup mTypeRgp;
    private FrameLayout mContentFlt;


    @Override
    protected View initContentView() {

        View view = mLayoutInflater.inflate(R.layout.activity_test_hand, null);
        initView(view);

        return view;
    }

    @Override
    protected String getTips() {
        return null;
    }

    private void initView(View view) {

        mTypeRgp = (RadioGroup) view.findViewById(R.id.type_rgp);
        mContentFlt = (FrameLayout) view.findViewById(R.id.content_flt);

        mTypeRgp.setOnCheckedChangeListener(this);

    }


    private TestActionFragment mTestActionFragment = new TestActionFragment() ;
    private TestFingerFragment mTestFingerFragment = new TestFingerFragment() ;
    private TestArmFragment mTestArmFragment = new TestArmFragment() ;
    private TestChangeArmIdFragment mTestChangeArmIdFragment = new TestChangeArmIdFragment() ;

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if(checkedId == R.id.action_rbn){

            FragmentTransaction ft = getFragmentManager().beginTransaction() ;
            ft.replace(R.id.content_flt, mTestActionFragment) ;
            ft.commit() ;

        }else if(checkedId == R.id.finger_rbn ){

            FragmentTransaction ft = getFragmentManager().beginTransaction() ;
            ft.replace(R.id.content_flt, mTestFingerFragment) ;
            ft.commit() ;

        }else if(checkedId == R.id.arm_rbn ){

            FragmentTransaction ft = getFragmentManager().beginTransaction() ;
            ft.replace(R.id.content_flt, mTestArmFragment) ;
            ft.commit() ;

        }else if(checkedId == R.id.change_arm_id_rbn ){

            FragmentTransaction ft = getFragmentManager().beginTransaction() ;
            ft.replace(R.id.content_flt, mTestChangeArmIdFragment) ;
            ft.commit() ;

        }

    }
}
