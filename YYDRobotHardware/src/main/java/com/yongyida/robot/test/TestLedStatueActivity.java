package com.yongyida.robot.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.yongyida.robot.communicate.app.hardware.led.data.LedStatue;
import com.yongyida.robot.hardware.R;
import com.yongyida.robot.hardware.client.LedClient;

/**
 * Created by HuangXiangXiang on 2017/12/16.
 */
public class TestLedStatueActivity extends Activity implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener {

    private LedClient ledClient;
    /**
     * 全部
     */
    private CheckBox mAllCbx;
    /**
     * 左耳
     */
    private CheckBox mLeftEarCbx;
    /**
     * 右耳
     */
    private CheckBox mRightEarCbx;
    /**
     * 胸部
     */
    private CheckBox mChestCbx;
    /**
     * 肚子
     */
    private CheckBox mPaunchCbx;
    /**
     * 开关灯
     */
    private Switch mPowerSih;
    private SeekBar mBrightnessSbr;
    /**
     * 100
     */
    private TextView mBrightnessTvw;
    private SeekBar mRedSbr;
    /**
     * 255
     */
    private TextView mRedTvw;
    private SeekBar mGreenSbr;
    /**
     * 255
     */
    private TextView mGreenTvw;
    private SeekBar mBlueSbr;
    /**
     * 255
     */
    private TextView mBlueTvw;
    private GridView mEffectGvw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_led_statue);
        initView();
        ledClient = new LedClient(this);
    }

    public void sendLedStatue(View view) {

        LedStatue ledStatue = new LedStatue();
        LedStatue.Power power = LedStatue.Power.POWER_ON;
        ledStatue.setPower(power);


        ledClient.sendLedStatueInMainThread(ledStatue);

    }

    private void initView() {
        mAllCbx = (CheckBox) findViewById(R.id.all_cbx);
        mLeftEarCbx = (CheckBox) findViewById(R.id.left_ear_cbx);
        mRightEarCbx = (CheckBox) findViewById(R.id.right_ear_cbx);
        mChestCbx = (CheckBox) findViewById(R.id.chest_cbx);
        mPaunchCbx = (CheckBox) findViewById(R.id.paunch_cbx);
        mPowerSih = (Switch) findViewById(R.id.power_sih);
        mBrightnessSbr = (SeekBar) findViewById(R.id.brightness_sbr);
        mBrightnessTvw = (TextView) findViewById(R.id.brightness_tvw);
        mRedSbr = (SeekBar) findViewById(R.id.red_sbr);
        mRedTvw = (TextView) findViewById(R.id.red_tvw);
        mGreenSbr = (SeekBar) findViewById(R.id.green_sbr);
        mGreenTvw = (TextView) findViewById(R.id.green_tvw);
        mBlueSbr = (SeekBar) findViewById(R.id.blue_sbr);
        mBlueTvw = (TextView) findViewById(R.id.blue_tvw);
        mEffectGvw = (GridView) findViewById(R.id.effect_gvw);


        mAllCbx.setOnCheckedChangeListener(this);
        mLeftEarCbx.setOnCheckedChangeListener(this);
        mRightEarCbx.setOnCheckedChangeListener(this);
        mChestCbx.setOnCheckedChangeListener(this);
        mPaunchCbx.setOnCheckedChangeListener(this);

        mPowerSih.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                LedStatue ledStatue = new LedStatue() ;
                ledStatue.setPosition(getPosition());

                LedStatue.Power power = isChecked ? LedStatue.Power.POWER_ON :LedStatue.Power.POWER_OFF ;
                ledStatue.setPower(power);

                ledClient.sendLedStatueInMainThread(ledStatue);

            }
        });

        mBrightnessSbr.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mRedSbr.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mGreenSbr.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mBlueSbr.setOnSeekBarChangeListener(mOnSeekBarChangeListener);

        mEffectGvw.setAdapter(mBaseAdapter);
        mEffectGvw.setOnItemClickListener(this);

    }

    private int getPosition(){

        if(mAllCbx.isChecked()){

            return LedStatue.POSITION_ALL ;

        }else {

            int position = 0 ;
            if(mLeftEarCbx.isChecked()){

                position |= LedStatue.POSITION_LEFT_EAR ;
            }

            if(mRightEarCbx.isChecked()){

                position |= LedStatue.POSITION_RIGHT_EAR ;
            }

            if(mChestCbx.isChecked()){

                position |= LedStatue.POSITION_CHEST ;
            }

            if(mPaunchCbx.isChecked()){

                position |= LedStatue.POSITION_PAUNCH ;
            }

            return position ;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(isChecked){

            if(buttonView == mAllCbx){

                mLeftEarCbx.setChecked(false);
                mRightEarCbx.setChecked(false);
                mChestCbx.setChecked(false);
                mPaunchCbx.setChecked(false);

            }else {
                mAllCbx.setChecked(false);
            }
        }

    }


    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            if(fromUser){

                if (seekBar == mBrightnessSbr){

                    mBrightnessTvw.setText(String.valueOf(progress));

                    LedStatue ledStatue = new LedStatue() ;
                    ledStatue.setPosition(getPosition());

                    LedStatue.Brightness brightness = new LedStatue.Brightness() ;
                    brightness.setBrightness(progress);
                    ledStatue.setBrightness(brightness);

                    ledClient.sendLedStatueInMainThread(ledStatue);

                }else{

                    if(seekBar == mRedSbr){

                        mRedTvw.setText(String.valueOf(progress));

                    }else if(seekBar == mGreenSbr){

                        mGreenTvw.setText(String.valueOf(progress));

                    }else if(seekBar == mBlueSbr){

                        mBlueTvw.setText(String.valueOf(progress));
                    }

                    LedStatue ledStatue = new LedStatue() ;
                    ledStatue.setPosition(getPosition());

                    LedStatue.Color color = new LedStatue.Color() ;
                    color.setRed(mRedSbr.getProgress());
                    color.setGreen(mGreenSbr.getProgress());
                    color.setBlue(mBlueSbr.getProgress());
                    ledStatue.setColor(color);

                    ledClient.sendLedStatueInMainThread(ledStatue);
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    } ;

    private LedStatue.Effect[] mEffects = LedStatue.Effect.values() ;
    private BaseAdapter mBaseAdapter = new BaseAdapter(){
        @Override
        public int getCount() {

            return mEffects.length;
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

            TextView textView ;
            if(convertView == null){
                convertView = LayoutInflater.from(TestLedStatueActivity.this).inflate(R.layout.item_text,null) ;
                textView = convertView.findViewById(R.id.text_tvw );

                convertView.setTag(textView);

            }else{

                textView = (TextView) convertView.getTag();
            }
            textView.setText(mEffects[position].name());

            return convertView;
        }
    } ;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        LedStatue ledStatue = new LedStatue() ;
        ledStatue.setPosition(getPosition());

        LedStatue.Effect effect= mEffects[position];
        ledStatue.setEffect(effect);

        ledClient.sendLedStatueInMainThread(ledStatue);

    }

}
