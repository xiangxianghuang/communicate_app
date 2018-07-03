package com.yongyida.robot.hardware.test.item.led;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;


import com.yongyida.robot.communicate.app.common.response.BaseResponseControl;
import com.yongyida.robot.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.common.send.SendResponseListener;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.data.ModelInfo;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;
import com.yongyida.robot.hardware.test.item.led.dialog.MoreColorDialog;
import com.yongyida.robot.hardware.test.view.HorizontalListView;

import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl.Effect.BREATH;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl.Effect.BREATH_FAST;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl.Effect.BREATH_LOW;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl.Effect.BREATH_MIDDLE;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl.Effect.HORSE_RACE;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl.Effect.LED_OFF;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl.Effect.LED_ON;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl.Position.ARM;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl.Position.CHEST;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl.Position.EAR;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl.Position.LEFT_ARM;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl.Position.RIGHT_ARM;

/**
 * Created by HuangXiangXiang on 2018/3/2.
 */
public class TestLedActivity extends TestBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemLongClickListener {

    private static final String TAG = TestLedActivity.class.getSimpleName() ;

    private static LedSendControl.Position[] POSITION_VALUE  ;
    private static String [] POSITION_NAME ;
    static{

        String model = ModelInfo.getInstance().getModel();
        if(model.contains("YQ110")){

            POSITION_VALUE = new LedSendControl.Position[]{ CHEST} ;
            POSITION_NAME = new String[] { "胸部"} ;

        }else if(model.contains("Y138")) {

            POSITION_VALUE = new LedSendControl.Position[]{ CHEST, EAR, LEFT_ARM, RIGHT_ARM, ARM} ;
            POSITION_NAME = new String[] { "胸部","耳朵","左臂","右臂","双臂"} ;

        }else if(model.contains("Y165")) {

            POSITION_VALUE = new LedSendControl.Position[]{ LedSendControl.Position.EYE} ;
            POSITION_NAME = new String[] { "眼睛"} ;

        }else{

            POSITION_VALUE = new LedSendControl.Position[]{LedSendControl.Position.LEFT_EAR, LedSendControl.Position.RIGHT_EAR, CHEST} ;
            POSITION_NAME = new String[]{"左耳朵","右耳朵", "胸部"} ;
        }
    }



    private static LedSendControl.Effect[] EFFECT_VALUE ;
    private static String [] EFFECT_NAME ;
    static{

        String model = ModelInfo.getInstance().getModel();
        if(model.contains("Y165")){

            EFFECT_VALUE = new LedSendControl.Effect[]{LED_ON, LED_OFF} ;
            EFFECT_NAME = new String[]{"常亮","常灭"} ;

        }else if(model.contains("Y138")) {

            EFFECT_VALUE = new LedSendControl.Effect[]{LED_ON, LED_OFF, BREATH_LOW, BREATH_MIDDLE, BREATH_FAST, BREATH} ;
            EFFECT_NAME = new String[]{"常亮","常灭","呼吸灯(慢)","呼吸灯(中)", "呼吸灯(快)","呼吸[2000秒]"} ;

        }else {

            EFFECT_VALUE = new LedSendControl.Effect[]{LED_ON, LED_OFF, BREATH_LOW, BREATH_MIDDLE, BREATH_FAST, HORSE_RACE} ;
            EFFECT_NAME = new String[]{"常亮","常灭","呼吸灯(慢)","呼吸灯(中)", "呼吸灯(快)","跑马灯"} ;
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
    private Button mColorMoreBtn;
    private LinearLayout mGeneralColorLlt;
    private GridView mEffectGvw;

    private int width = 100 ;

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
        mColorMoreBtn = (Button) view.findViewById(R.id.color_more_btn);
        mColorMoreBtn.setOnClickListener(this);
        mGeneralColorLlt = (LinearLayout) view.findViewById(R.id.general_color_llt);
        mEffectGvw = (GridView) view.findViewById(R.id.effect_gvw);
        mEffectGvw.setOnItemClickListener(this);
        mEffectGvw.setOnItemLongClickListener(this);


        if(ModelInfo.getInstance().getModel().contains("Y165")){

            mGeneralColorLlt.setVisibility(View.GONE);
        }

        ledControl.setEffectValue(breathTime);

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

        ledControl.setPosition(POSITION_VALUE[0]);
        ledControl.setEffect(LedSendControl.Effect.NORMAL);
        ledControl.setColor(0xFF0000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }


//    private void startTest(){
//
//        TestData testData = new TestData() ;
//        testData.setTest(true);
//        TouchSend touchSend = new TouchSend() ;
//        touchSend.setTestData(testData);
//
//        SendClient.getInstance(this).sendInMainThread(TestData, null);
//
//    }
//
//    private void stopTest(){
//
//        TestData testData = new TestData() ;
//        testData.setTest(false);
//        TouchSend touchSend = new TouchSend() ;
//        touchSend.setTestData(testData);
//
//        TouchClient.getInstance(this).sendInMainThread(touchSend, null);
//    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.brightness_dark_btn) {


        } else if (i == R.id.brightness_bright_btn) {


        } else if (i == R.id.color_white_btn) {


        } else if (i == R.id.color_red_btn) {

            ledControl.setColor(0xFF0000);
            sendLedControl() ;

        } else if (i == R.id.color_green_btn) {

            ledControl.setColor(0x00FF00);
            sendLedControl() ;

        } else if (i == R.id.color_blue_btn) {

            ledControl.setColor(0x0000FF);
            sendLedControl() ;

        } else if (i == R.id.color_more_btn) {

            new MoreColorDialog(this, ledControl).show();

        } else {


        }
    }

    private LedSendControl ledControl = new LedSendControl();


    public void  sendLedControl(){

        SendClient.getInstance(this).send(null, ledControl,responseListener);
    }
    private SendResponseListener responseListener = new SendResponseListener(){

        @Override
        public void onSuccess(BaseResponseControl baseResponseControl) {

            LogHelper.i(TAG, LogHelper.__TAG__() + "baseResponseControl : " + baseResponseControl);
        }

        @Override
        public void onFail(int result, String message) {

            LogHelper.i(TAG, LogHelper.__TAG__() + "result : " + result + ", message : " + message );
        }
    } ;

    private int breathTime = 2000 ;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

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


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        if(parent == mEffectGvw){

            LedSendControl.Effect effect = EFFECT_VALUE[position] ;
            if(effect == BREATH) {

                final EditText editText = new EditText(this);
                editText.setText(String.valueOf(breathTime));
                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String text = editText.getText().toString();
                        try {
                            breathTime = Integer.parseInt(text);
                        } catch (Exception e) {
                        }

                        EFFECT_NAME[position] = "呼吸[" + breathTime + "秒]";

                        mEffectAdapter.notifyDataSetChanged();

                        ledControl.setEffect(EFFECT_VALUE[position]);
                        ledControl.setEffectValue(breathTime);

                        sendLedControl();

                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("设置呼吸时间轮回")
                        .setView(editText)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", onClickListener);

                builder.create().show();
            }
        }

        return true;
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

//        LedSendControl ledStatue = new LedSendControl();
//        ledStatue.setPosition(POSITION_VALUE[ledPositionCheckPosition]);
//
//        LedSendControl.Power power = isChecked ? LedSendControl.Power.POWER_ON : LedSendControl.Power.POWER_OFF ;
//
//        ledStatue.setPower(power);
//
//        LedClient.getInstance(this).sendLedStatueInMainThread(ledStatue);

    }


    /**隐藏功能*/
    @Override
    protected void onTouchTitleLeft() {
        super.onTouchTitleLeft();

        LedLibraryActivity.startActivity(this);
    }


}
