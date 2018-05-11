package com.yongyida.robot.hardware.test.item.touchpad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

/**
 * Created by HuangXiangXiang on 2018/2/27.
 * 触摸屏测试
 */
public class TestTouchPadActivity extends TestBaseActivity {

    private boolean isFirst = false;    //触摸屏 测试2次
    private TouchPadView mTouchPadView ;


    @Override
    protected View initContentView() {
        return null;
    }

    @Override
    protected String getTips() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTouchPadView = new TouchPadView(this) ;
        mTouchPadView.setOnTouchCompleteListener(mOnTouchCompleteListener);
        setTimeOut() ;

        setContentView(mTouchPadView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cancelTimeOut();
    }

    private TouchPadView.OnTouchCompleteListener mOnTouchCompleteListener = new TouchPadView.OnTouchCompleteListener() {
        @Override
        public void onTouchComplete() {

            cancelTimeOut() ;

            if(!isFirst){
                isFirst = true ;
                // 第一次结束
                showFirstDialog() ;

            }else {

                showResultDialog();
            }

        }
    };


    private Handler mHandler = new Handler() ;
    private Runnable mTimeOutRunnable = new Runnable() {
        @Override
        public void run() {

            showResultDialog();

        }
    };

    private void setTimeOut(){

        mHandler.postDelayed(mTimeOutRunnable, 60* 1000) ;
    }

    private void cancelTimeOut(){

        mHandler.removeCallbacks(mTimeOutRunnable);
    }

    protected void showFirstDialog(){

        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which == DialogInterface.BUTTON_POSITIVE){

                    mTouchPadView.reset();
                    setTimeOut() ;

                }else if(which == DialogInterface.BUTTON_NEGATIVE){

                    onFail();
                }

            }
        };

        new AlertDialog.Builder(this)
                .setTitle(mTitle)
                .setMessage(R.string.result_test)
                .setPositiveButton(R.string.success,onClickListener)
                .setNegativeButton(R.string.fail,onClickListener)
                .create()
                .show();

    }


}
