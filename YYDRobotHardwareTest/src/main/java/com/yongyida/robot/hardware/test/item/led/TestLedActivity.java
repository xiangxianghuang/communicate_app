package com.yongyida.robot.hardware.test.item.led;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.yongyida.robot.communicate.app.hardware.TestData;
import com.yongyida.robot.communicate.app.hardware.led.data.LedControl;
import com.yongyida.robot.communicate.app.hardware.touch.send.TouchSend;
import com.yongyida.robot.hardware.client.LedClient;
import com.yongyida.robot.hardware.client.TouchClient;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.data.ModelInfo;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

/**
 * Created by HuangXiangXiang on 2018/3/2.
 */
public class TestLedActivity extends TestBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {


    private static int [] POSITION_VALUE  ;
    private static String [] POSITION_NAME ;
    static{

        if(ModelInfo.getInstance().getModel().contains("YQ110")){

            POSITION_VALUE = new int[]{ LedControl.POSITION_CHEST} ;
            POSITION_NAME = new String[] { "胸部"} ;

        }else if(ModelInfo.getInstance().getModel().contains("Y165")) {

            POSITION_VALUE = new int[]{ LedControl.POSITION_EYE} ;
            POSITION_NAME = new String[] { "眼睛"} ;

        }else{

            POSITION_VALUE = new int[]{LedControl.POSITION_LEFT_EAR, LedControl.POSITION_RIGHT_EAR, LedControl.POSITION_CHEST} ;
            POSITION_NAME = new String[]{"左耳朵","右耳朵", "胸部"} ;
        }
    }





    private static LedControl.Effect[] EFFECT_VALUE ;
    private static String [] EFFECT_NAME ;
    static{

        if(ModelInfo.getInstance().getModel().contains("Y165")){

            EFFECT_VALUE = new LedControl.Effect[]{LedControl.Effect.NORMAL, LedControl.Effect.LED_OFF} ;
            EFFECT_NAME = new String[]{"常亮","常灭"} ;
        }else {

            EFFECT_VALUE = new LedControl.Effect[]{LedControl.Effect.NORMAL, LedControl.Effect.LED_OFF, LedControl.Effect.BREATH_LOW, LedControl.Effect.BREATH_MIDDLE, LedControl.Effect.BREATH_FAST} ;
            EFFECT_NAME = new String[]{"常亮","常灭","呼吸灯(慢)","呼吸灯(中)", "呼吸灯(快)"} ;
        }
    }





    private HorizontalListView mLedPositionGvw;
    /**
     * 开关灯
     */
    private Switch mPowerSih;
    /**
     * 暗
     */
    private Button mBrightnessDarkBtn;
    /**
     * 亮
     */
    private Button mBrightnessBrightBtn;
    private LinearLayout mGeneralBrightnessLlt;
    /**
     * 白
     */
    private Button mColorWhiteBtn;
    /**
     * 红
     */
    private Button mColorRedBtn;
    /**
     * 绿
     */
    private Button mColorGreenBtn;
    /**
     * 蓝
     */
    private Button mColorBlueBtn;
    private LinearLayout mGeneralColorLlt;
    private GridView mEffectGvw;

    private int width = 200 ;

    @Override
    protected View initContentView() {

        View view = LayoutInflater.from(this).inflate(R.layout.activity_test_led , null) ;

        mLedPositionGvw = (HorizontalListView) view.findViewById(R.id.led_position_gvw);
        mLedPositionGvw.setOnItemClickListener(this);
        mPowerSih = (Switch) view.findViewById(R.id.power_sih);
        mPowerSih.setOnCheckedChangeListener(this);
        mBrightnessDarkBtn = (Button) view.findViewById(R.id.brightness_dark_btn);
        mBrightnessDarkBtn.setOnClickListener(this);
        mBrightnessBrightBtn = (Button) view.findViewById(R.id.brightness_bright_btn);
        mBrightnessBrightBtn.setOnClickListener(this);
        mGeneralBrightnessLlt = (LinearLayout) view.findViewById(R.id.general_brightness_llt);
        mColorWhiteBtn = (Button) view.findViewById(R.id.color_white_btn);
        mColorWhiteBtn.setOnClickListener(this);
        mColorRedBtn = (Button) view.findViewById(R.id.color_red_btn);
        mColorRedBtn.setOnClickListener(this);
        mColorGreenBtn = (Button) view.findViewById(R.id.color_green_btn);
        mColorGreenBtn.setOnClickListener(this);
        mColorBlueBtn = (Button) view.findViewById(R.id.color_blue_btn);
        mColorBlueBtn.setOnClickListener(this);
        mGeneralColorLlt = (LinearLayout) view.findViewById(R.id.general_color_llt);
        mEffectGvw = (GridView) view.findViewById(R.id.effect_gvw);
        mEffectGvw.setOnItemClickListener(this);


        if(ModelInfo.getInstance().getModel().contains("Y165")){

            mGeneralColorLlt.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    protected String getTips() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        float scale = getResources().getDisplayMetrics().scaledDensity ;
        width *= scale ;

        ledControl.setPosition(POSITION_VALUE[0]);
        ledControl.setEffect(EFFECT_VALUE[0]);

        mLedPositionGvw.setAdapter(mLedPositionAdapter);
        mEffectGvw.setAdapter(mEffectAdapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }


    private void startTest(){

        TestData testData = new TestData() ;
        testData.setTest(true);
        TouchSend touchSend = new TouchSend() ;
        touchSend.setTestData(testData);

        TouchClient.getInstance(this).sendInMainThread(touchSend, null);

    }

    private void stopTest(){

        TestData testData = new TestData() ;
        testData.setTest(false);
        TouchSend touchSend = new TouchSend() ;
        touchSend.setTestData(testData);

        TouchClient.getInstance(this).sendInMainThread(touchSend, null);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.brightness_dark_btn) {


        } else if (i == R.id.brightness_bright_btn) {


        } else if (i == R.id.color_white_btn) {


        } else if (i == R.id.color_red_btn) {

            ledControl.setColor(LedControl.Color.value(255,0,0));
            sendLedControl() ;

        } else if (i == R.id.color_green_btn) {

            ledControl.setColor(LedControl.Color.value(0,255,0));
            sendLedControl() ;

        } else if (i == R.id.color_blue_btn) {

            ledControl.setColor(LedControl.Color.value(0,0,255));
            sendLedControl() ;

        } else {


        }
    }

    private LedControl ledControl = new LedControl();


    public void  sendLedControl(){

        LedClient.getInstance(this).sendLedControlInMainThread(ledControl, null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(parent == mLedPositionGvw){

            ledPositionCheckPosition = position ;
            mLedPositionAdapter.notifyDataSetChanged();

            ledControl.setPosition(POSITION_VALUE[position]);
            sendLedControl() ;


        }else if(parent == mEffectGvw){

            effectCheckPosition = position ;
            mEffectAdapter.notifyDataSetChanged();

            ledControl.setEffect(EFFECT_VALUE[position]);
            sendLedControl() ;
        }
    }

    private int ledPositionCheckPosition ;
    private BaseAdapter mLedPositionAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return POSITION_NAME.length;
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
            if(convertView  == null){

                convertView = mLayoutInflater.inflate(R.layout.item_text, null) ;
                textView = convertView.findViewById(R.id.text_tvw) ;

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.width = width ;

                convertView.setTag(textView);

            }else{

                textView = (TextView) convertView.getTag();
            }


            textView.setBackgroundResource((ledPositionCheckPosition == position) ? R.drawable.item_select_bg :R.drawable.item_bg);
            textView.setText(POSITION_NAME[position]);
//            textView.requestLayout();

            return convertView;
        }
    };

    private int effectCheckPosition ;
    private BaseAdapter mEffectAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return EFFECT_NAME.length;
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
            if(convertView  == null){

                convertView = mLayoutInflater.inflate(R.layout.item_text, null) ;

                textView = convertView.findViewById(R.id.text_tvw) ;

                convertView.setTag(textView);

            }else{

                textView = (TextView) convertView.getTag();
            }


            textView.setBackgroundResource((effectCheckPosition == position) ? R.drawable.item_select_bg :R.drawable.item_bg);
            textView.setText(EFFECT_NAME[position]);

            return convertView;
        }
    };


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//        LedControl ledStatue = new LedControl();
//        ledStatue.setPosition(POSITION_VALUE[ledPositionCheckPosition]);
//
//        LedControl.Power power = isChecked ? LedControl.Power.POWER_ON : LedControl.Power.POWER_OFF ;
//
//        ledStatue.setPower(power);
//
//        LedClient.getInstance(this).sendLedStatueInMainThread(ledStatue);

    }
}
