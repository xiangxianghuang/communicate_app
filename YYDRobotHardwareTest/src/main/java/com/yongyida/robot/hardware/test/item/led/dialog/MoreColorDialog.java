package com.yongyida.robot.hardware.test.item.led.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yongyida.robot.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl;
import com.yongyida.robot.hardware.test.R;



/* 
                              _ooOoo_ 
                             o8888888o 
                             88" . "88 
                             (| -_- |) 
                             O\  =  /O 
                          ____/`---'\____ 
                        .'  \\|     |//  `. 
                       /  \\|||  :  |||//  \ 
                      /  _||||| -:- |||||-  \ 
                      |   | \\\  -  /// |   | 
                      | \_|  ''\---/''  |   | 
                      \  .-\__  `-`  ___/-. / 
                    ___`. .'  /--.--\  `. . __ 
                 ."" '<  `.___\_<|>_/___.'  >'"". 
                | | :  `- \`.;`\ _ /`;.`/ - ` : | | 
                \  \ `-.   \_ __\ /__ _/   .-` /  / 
           ======`-.____`-.___\_____/___.-`____.-'====== 
                              `=---=' 
           .............................................  
                    佛祖镇楼                  BUG辟易  
            佛曰:  
                    写字楼里写字间，写字间里程序员；  
                    程序人员写程序，又拿程序换酒钱。  
                    酒醒只在网上坐，酒醉还来网下眠；  
                    酒醉酒醒日复日，网上网下年复年。  
                    但愿老死电脑间，不愿鞠躬老板前；  
                    奔驰宝马贵者趣，公交自行程序员。  
                    别人笑我忒疯癫，我笑自己命太贱；  
                    不见满街漂亮妹，哪个归得程序员？ 
*/

/**
 * Create By HuangXiangXiang 2018/6/6
 */
public class MoreColorDialog extends Dialog implements SeekBar.OnSeekBarChangeListener {

    private View mPreColorViw;
    private SeekBar mRedSbr;
    private TextView mRedTvw;
    private SeekBar mGreenSbr;
    private TextView mGreenTvw;
    private SeekBar mBlueSbr;
    private TextView mBlueTvw;
    private SeekBar mBrightSbr;
    private TextView mBrightTvw;


    private LedSendControl mLedControl ;

    public MoreColorDialog(@NonNull Context context,LedSendControl ledControl) {
        super(context);
        mLedControl = ledControl ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_more_color);

        this.mPreColorViw = (View) findViewById(R.id.pre_color_viw);
        this.mRedSbr = (SeekBar) findViewById(R.id.red_sbr);
        this.mRedTvw = (TextView) findViewById(R.id.red_tvw);
        this.mGreenSbr = (SeekBar) findViewById(R.id.green_sbr);
        this.mGreenTvw = (TextView) findViewById(R.id.green_tvw);
        this.mBlueSbr = (SeekBar) findViewById(R.id.blue_sbr);
        this.mBlueTvw = (TextView) findViewById(R.id.blue_tvw);
        this.mBrightSbr = (SeekBar) findViewById(R.id.bright_sbr);
        this.mBrightTvw = (TextView) findViewById(R.id.bright_tvw);

        this.mRedSbr.setOnSeekBarChangeListener(this);
        this.mGreenSbr.setOnSeekBarChangeListener(this);
        this.mBlueSbr.setOnSeekBarChangeListener(this);
        this.mBrightSbr.setOnSeekBarChangeListener(this);

        setData() ;
    }


    private void setLedControl(LedSendControl ledControl){

        this.mLedControl = ledControl ;
        setData();
    }

    private void setData(){

        if(mLedControl != null && mPreColorViw != null){

            LedSendControl.Color color = mLedControl.getColor() ;
            if(color == null){

                color = LedSendControl.Color.value(0xFFFFFF) ;
                mLedControl.setColor(color);
            }

            int red = color.getRed() ;
            int green = color.getGreen() ;
            int blue = color.getBlue() ;

            int colorValue =  color.getColor() | (0xFF << 24);


            mPreColorViw.setBackgroundColor(colorValue);
            mRedSbr.setProgress(red);
            mGreenSbr.setProgress(green);
            mBlueSbr.setProgress(blue);


            LedSendControl.Brightness brightness = mLedControl.getBrightness() ;
            if(brightness == null){

                brightness = LedSendControl.Brightness.value(100) ;
                mLedControl.setBrightness(brightness);
            }

            int brightnessValue = brightness.getValue() ;
            mBrightSbr.setProgress(brightnessValue);

        }


    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if(seekBar == mRedSbr){
            mRedTvw.setText(String.valueOf(progress));
            int colorValue = Color.rgb(mRedSbr.getProgress(), mGreenSbr.getProgress(), mBlueSbr.getProgress()) ;
            mPreColorViw.setBackgroundColor(colorValue);


            if(mLedControl != null){

                mLedControl.getColor().setRed(progress);
                SendClient.getInstance(getContext()).send(null, mLedControl, null);
            }

        }else if(seekBar == mGreenSbr){
            mGreenTvw.setText(String.valueOf(progress));
            int colorValue = Color.rgb(mRedSbr.getProgress(), mGreenSbr.getProgress(), mBlueSbr.getProgress()) ;
            mPreColorViw.setBackgroundColor(colorValue);

            if(mLedControl != null){

                mLedControl.getColor().setGreen(progress);
                SendClient.getInstance(getContext()).send(null, mLedControl, null);
            }

        }else if(seekBar == mBlueSbr){
            mBlueTvw.setText(String.valueOf(progress));
            int colorValue = Color.rgb(mRedSbr.getProgress(), mGreenSbr.getProgress(), mBlueSbr.getProgress()) ;
            mPreColorViw.setBackgroundColor(colorValue);

            if(mLedControl != null){

                mLedControl.getColor().setBlue(progress);
                SendClient.getInstance(getContext()).send(null, mLedControl, null);
            }

        }else if(seekBar == mBrightSbr){
            mBrightTvw.setText(String.valueOf(progress));

            if(mLedControl != null){

                mLedControl.getBrightness().setValue(progress);
                SendClient.getInstance(getContext()).send(null, mLedControl, null);
            }

        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
