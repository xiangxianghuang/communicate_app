package com.yongyida.robot.hardware.test.item.lcd;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.yongyida.robot.hardware.test.item.TestBaseActivity;

/**
 * Created by HuangXiangXiang on 2018/2/9.
 */
public class TestLcdActivity extends TestBaseActivity {


    private int[] COLORS = {Color.YELLOW,Color.GREEN,Color.GRAY,Color.BLUE,
            Color.rgb(255,0,255),Color.BLACK,Color.WHITE} ;
    private int index = -1 ;

    private View mView ;

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

        mView = new View(this) ;
        setContentView(mView);

        changeBackGroundColor() ;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){

            changeBackGroundColor() ;
        }

        return super.onTouchEvent(event);
    }


    private void changeBackGroundColor(){

        if(++index >= COLORS.length){

            showResultDialog() ;

        }else{

            mView.setBackgroundColor(COLORS[index]);
        }

    }
}
