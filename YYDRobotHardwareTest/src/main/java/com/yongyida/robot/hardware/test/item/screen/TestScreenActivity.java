package com.yongyida.robot.hardware.test.item.screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

/**
 * Created by HuangXiangXiang on 2018/2/25.
 */
public class TestScreenActivity extends TestBaseActivity {

    private SeekBar mScreenBrightSbr ;

    private Window mWindow ;
    private WindowManager.LayoutParams mLayoutParams ;


    @Override
    protected View initContentView() {

        mWindow = getWindow() ;
        mLayoutParams = mWindow.getAttributes();

        View view = LayoutInflater.from(this).inflate(R.layout.activity_test_screen, null) ;
        mScreenBrightSbr = view.findViewById(R.id.screen_bright_sbr) ;

        mScreenBrightSbr.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mScreenBrightSbr.setProgress(128);

        return view;
    }

    @Override
    protected String getTips() {
        return null;
    }


    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            mLayoutParams.screenBrightness = progress / 255F ;
            mWindow.setAttributes(mLayoutParams);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };
}
