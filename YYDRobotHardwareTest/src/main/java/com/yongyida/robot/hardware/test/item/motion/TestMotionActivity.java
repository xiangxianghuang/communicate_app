package com.yongyida.robot.hardware.test.item.motion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hiva.communicate.app.common.IResponseListener;
import com.hiva.communicate.app.common.SendResponse;
import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.motion.MotionResponse;
import com.yongyida.robot.communicate.app.hardware.motion.data.MotionControl;
import com.yongyida.robot.communicate.app.hardware.motion.data.MoveFault;
import com.yongyida.robot.communicate.app.hardware.motion.data.QueryMoveFault;
import com.yongyida.robot.communicate.app.hardware.motion.data.UltrasonicControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.TestMainActivity;
import com.yongyida.robot.hardware.client.MotionClient;
import com.yongyida.robot.hardware.test.data.ModelInfo;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * Created by HuangXiangXiang on 2018/2/25.
 * 马达测试
 */
public class TestMotionActivity extends TestBaseActivity implements View.OnClickListener {

    private static final String TAG = TestMainActivity.class.getSimpleName() ;

    private TextView mFaultTvw ;

    private LinearLayout mHeadLeftRightLlt ;


    private Button mHeadLeftRightResetBtn;
    /**
     * 左转
     */
    private Button mHeadLeftBtn;
    /**
     * 右转
     */
    private Button mHeadRightBtn;

    private Button mHeadLeftRightStopBtn;


    private LinearLayout mHeadUpDownLlt ;
    private Button mHeadUpDownResetBtn;
    /**
     * 上
     */
    private Button mHeadUpBtn;
    /**
     * 下
     */
    private Button mHeadDownBtn;
    private Button mHeadUpDownStopBtn;



    /**是否走串口*/
    private SwitchCompat mIsSerialSct ;
    private RadioGroup mSpeedRgp ;
    private RadioButton mSpeedLowRbn ;
    private RadioButton mSspeedMiddleRbn ;
    private RadioButton mSpeedHighRbn ;

    /**
     * 前
     */
    private Button mFootForwardBtn;
    /**
     * 后
     */
    private Button mFootBackBtn;
    /**
     * 左
     */
    private Button mFootLeftBtn;
    /**
     * 右
     */
    private Button mFootRightBtn;
    private Button mFootStopBtn;

    private EditText mSoundLocationEtt ;
    private Button mSoundLocationBtn ;

    private SwitchCompat mUltrasonicSct ;
    private SwitchCompat mUltrasonicSlamwareSct ;
    private SwitchCompat mUltrasonicAndroidSct ;
    private GridView mUltrasonicGvw ;
    private Button mRandomMoveBtn ;

    private TextView mODBDataTvw ;
    private TextView mSystemInfoTvw ;

    private LinearLayout mUltrasonicLlt ;


    private MotionHandler motionHandler ;
    private static class MotionHandler extends Handler {

        private static final int MOVE_FAULT = 0x01 ; //获取数据值

        private final WeakReference<TestMotionActivity> mWeakReference ;
        private MotionHandler(TestMotionActivity activity){
            mWeakReference = new WeakReference<>(activity) ;
        }

        @Override
        public void handleMessage(Message msg) {

            TestMotionActivity activity = mWeakReference.get() ;
            if(activity != null){

                switch (msg.what){

                    case MOVE_FAULT:

                        MoveFault moveFault = (MoveFault) msg.obj;
                        String message = moveFault != null ? moveFault.getMessage() : null ;
                        activity.refreshMoveFault(message);

                        break;

                    default:
                        break;

                }

            }

        }
    }


    /**
     * */
    private void queryMoveFault(){

        new Thread(){

            @Override
            public void run() {

                MotionSend motionSend= new MotionSend() ;
                QueryMoveFault queryMoveFault = new QueryMoveFault() ;
                motionSend.setQueryMoveFault(queryMoveFault);

                final MotionClient motionClient = MotionClient.getInstance(TestMotionActivity.this) ;
                IResponseListener responseListener = new IResponseListener() {
                    @Override
                    public void onResponse(BaseResponse response) {

                        LogHelper.i(TAG, LogHelper.__TAG__() + ", response : " + response );

                        MotionResponse motionResponse = (MotionResponse) response;
                        if(motionResponse != null){

                            MoveFault moveFault = motionResponse.getMoveFault() ;

                            Message message = motionHandler.obtainMessage(MotionHandler.MOVE_FAULT) ;
                            message.obj = moveFault ;
                            motionHandler.sendMessage(message) ;
                        }

                    }
                };

                SendResponse sendResponse = motionClient.send(motionSend,responseListener) ;
                if (sendResponse == null) {

                    // 发送不成功
                    LogHelper.i(TAG, LogHelper.__TAG__());
//                    Toast.makeText(TestBatteryActivity.this, "发送失败，返回为空！", Toast.LENGTH_LONG).show();

                }else{

                    int result = sendResponse.getResult() ;
                    if(result != SendResponse.RESULT_SUCCESS)

                        // 发送不成功
                        LogHelper.i(TAG, LogHelper.__TAG__() + ", result : " + result);
//                        Toast.makeText(TestBatteryActivity.this,"发送失败，返回为:" + result , Toast.LENGTH_LONG).show(); ;
                }

            }
        }.start();

    }

    private int speed ;

    @Override
    protected View initContentView() {

        View view = mLayoutInflater.inflate(R.layout.activity_test_motion, null) ;

        mFaultTvw = (TextView) view.findViewById(R.id.fault_tvw);

        mHeadLeftRightLlt = view.findViewById(R.id.head_left_right_llt) ;
        mHeadUpDownLlt = view.findViewById(R.id.head_up_down_llt) ;
        mHeadLeftRightResetBtn = (Button) view.findViewById(R.id.head_left_right_reset_btn);
        mHeadLeftRightResetBtn.setOnClickListener(this);
        mHeadLeftBtn = (Button) view.findViewById(R.id.head_left_btn);
        mHeadLeftBtn.setOnClickListener(this);
        mHeadRightBtn = (Button) view.findViewById(R.id.head_right_btn);
        mHeadRightBtn.setOnClickListener(this);
        mHeadLeftRightStopBtn = (Button) view.findViewById(R.id.head_left_right_stop_btn);
        mHeadLeftRightStopBtn.setOnClickListener(this);

        mHeadUpDownResetBtn = (Button) view.findViewById(R.id.head_up_down_reset_btn);
        mHeadUpDownResetBtn.setOnClickListener(this);
        mHeadUpBtn = (Button) view.findViewById(R.id.head_up_btn);
        mHeadUpBtn.setOnClickListener(this);
        mHeadDownBtn = (Button) view.findViewById(R.id.head_down_btn);
        mHeadDownBtn.setOnClickListener(this);
        mHeadUpDownStopBtn = (Button) view.findViewById(R.id.head_up_down_stop_btn);
        mHeadUpDownStopBtn.setOnClickListener(this);


        mIsSerialSct = (SwitchCompat) view.findViewById(R.id.is_serial_sct);
        mIsSerialSct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                mSpeedRgp.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
        mSpeedRgp = (RadioGroup) view.findViewById(R.id.speed_rgp);
        mSpeedRgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.speed_low_rbn) {

                    speed = 20 ;

                } else if (checkedId == R.id.speed_middle_rbn) {

                    speed = 50 ;

                } else if (checkedId == R.id.speed_high_rbn) {

                    speed = 80 ;
                }

            }
        });
        mSpeedRgp.check(R.id.speed_middle_rbn);


        mSpeedLowRbn = (RadioButton) view.findViewById(R.id.speed_low_rbn);
        mSspeedMiddleRbn = (RadioButton) view.findViewById(R.id.speed_middle_rbn);
        mSpeedHighRbn = (RadioButton) view.findViewById(R.id.speed_high_rbn);


        mFootForwardBtn = (Button) view.findViewById(R.id.foot_forward_btn);
        mFootForwardBtn.setOnClickListener(this);
        mFootBackBtn = (Button) view.findViewById(R.id.foot_back_btn);
        mFootBackBtn.setOnClickListener(this);
        mFootLeftBtn = (Button) view.findViewById(R.id.foot_left_btn);
        mFootLeftBtn.setOnClickListener(this);
        mFootRightBtn = (Button) view.findViewById(R.id.foot_right_btn);
        mFootRightBtn.setOnClickListener(this);
        mFootStopBtn = (Button) view.findViewById(R.id.foot_stop_btn);
        mFootStopBtn.setOnClickListener(this);
        mSoundLocationEtt = (EditText) view.findViewById(R.id.sound_location_ett);
        mSoundLocationBtn = (Button) view.findViewById(R.id.sound_location_btn);
        mSoundLocationBtn.setOnClickListener(this);

        mUltrasonicSlamwareSct = view.findViewById(R.id.ultrasonic_slamware_sct) ;
        mUltrasonicSlamwareSct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                UltrasonicControl ultrasonicControl = new UltrasonicControl() ;
                ultrasonicControl.setShowAndroid(isChecked);

                MotionSend motionSend= new MotionSend() ;
                motionSend.setUltrasonicControl(ultrasonicControl);

                MotionClient.getInstance(TestMotionActivity.this).sendInMainThread(motionSend,null);

            }
        });

        mUltrasonicSlamwareSct = view.findViewById(R.id.ultrasonic_slamware_sct) ;
        mUltrasonicSlamwareSct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                setShowSlamware(isChecked) ;

            }
        });


        mUltrasonicAndroidSct = view.findViewById(R.id.ultrasonic_android_sct) ;
        mUltrasonicAndroidSct.setChecked(true);
        mUltrasonicAndroidSct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                setShowAndroid(isChecked) ;
            }
        });


        mUltrasonicGvw = view.findViewById(R.id.ultrasonic_gvw) ;
        mRandomMoveBtn = view.findViewById(R.id.random_move_btn) ;
        mRandomMoveBtn.setOnClickListener(this);

        mODBDataTvw = view.findViewById(R.id.odb_data_tvw) ;
        mSystemInfoTvw = view.findViewById(R.id.system_info_tvw) ;

        mUltrasonicLlt = view.findViewById(R.id.ultrasonic_llt) ;


        if(isY165){

            return null ;
        }

        return view;
    }



   private void setAllShow(){

        UltrasonicControl ultrasonicControl = new UltrasonicControl() ;
        ultrasonicControl.setShowSlamware(false);
        ultrasonicControl.setShowAndroid(true);

        MotionSend motionSend= new MotionSend() ;
        motionSend.setUltrasonicControl(ultrasonicControl);

        MotionClient.getInstance(TestMotionActivity.this).sendInMainThread(motionSend,null);
    }

    private void setAllHide(){

        UltrasonicControl ultrasonicControl = new UltrasonicControl() ;
        ultrasonicControl.setShowSlamware(true);
        ultrasonicControl.setShowAndroid(false);

        MotionSend motionSend= new MotionSend() ;
        motionSend.setUltrasonicControl(ultrasonicControl);

        MotionClient.getInstance(TestMotionActivity.this).sendInMainThread(motionSend,null);
    }



    private void setShowSlamware(boolean isChecked){

        UltrasonicControl ultrasonicControl = new UltrasonicControl() ;
        ultrasonicControl.setShowSlamware(isChecked);

        MotionSend motionSend= new MotionSend() ;
        motionSend.setUltrasonicControl(ultrasonicControl);

        MotionClient.getInstance(TestMotionActivity.this).sendInMainThread(motionSend,null);
    }


    private void setShowAndroid(boolean isChecked){

        UltrasonicControl ultrasonicControl = new UltrasonicControl() ;
        ultrasonicControl.setShowAndroid(isChecked);

        MotionSend motionSend= new MotionSend() ;
        motionSend.setUltrasonicControl(ultrasonicControl);

        MotionClient.getInstance(TestMotionActivity.this).sendInMainThread(motionSend,null);
    }



    @Override
    protected String getTips() {

        if(isY165){

            return "推动轮子前进、后退、左转、右转、锁住轮子是否有效" ;
        }

        return null;
    }

    private boolean isYQ110 = ModelInfo.getInstance().getModel().contains("YQ110") ;
    private boolean isY165 = ModelInfo.getInstance().getModel().contains("Y165") ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isY165){

            mUltrasonicAdapter = new UltrasonicAdapter() ;
            mUltrasonicGvw.setAdapter(mUltrasonicAdapter);

            motionHandler = new MotionHandler(this);
            queryMoveFault();

            if(isYQ110){

                mHeadUpDownLlt .setVisibility(View.GONE);

                mUltrasonicLlt.setVisibility(View.INVISIBLE);
            }else{

                mHeadUpDownLlt .setVisibility(View.VISIBLE);

                mUltrasonicLlt.setVisibility(View.VISIBLE);

            }


            setAllShow() ;

            registerReceiver() ;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(!isY165){

            unRegisterReceiver() ;

            setAllHide();

            stopRandomMove() ;
        }

    }

    public static final String ACTION_FAULT = "com.yongyida.robot.MOVE_FAULT" ;
    public static final String KEY_FAULT_CODE = "faultCode" ;
    public static final String KEY_FAULT_MESSAGE = "faultMessage" ;

    public static final String ACTION_ULTRASONIC_CHANGED = "com.yongyida.robot.ULTRASONIC_CHANGED" ;
    public static final String KEY_DISTANCES = "distances" ;

    public static final String ACTION_SYSTEM_CHANGED_GENERA = "com.yongyida.robot.SYSTEM_CHANGED_GENERA" ;
    public static final String KEY_GENERA_MESSAGE = "generaMessage" ;
    public static final String ACTION_OBD_DATA_CHANGED_GENERA = "com.yongyida.robot.OBD_DATA_CHANGED_GENERA" ;

    private void registerReceiver(){

        IntentFilter filter = new IntentFilter() ;
        filter.addAction(ACTION_FAULT);
        filter.addAction(ACTION_ULTRASONIC_CHANGED);
        filter.addAction(ACTION_SYSTEM_CHANGED_GENERA);
        filter.addAction(ACTION_OBD_DATA_CHANGED_GENERA);

        registerReceiver(broadcastReceiver ,filter ) ;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction() ;
            if(ACTION_FAULT.equals(action)){

                int code = intent.getIntExtra(KEY_FAULT_CODE, -1 ) ;
                String message = intent.getStringExtra(KEY_FAULT_MESSAGE) ;

                LogHelper.i(TAG, LogHelper.__TAG__() + ",code : " + code + ",message : " + message);

                refreshMoveFault(message);

            }else if(ACTION_ULTRASONIC_CHANGED.equals(action)){

                int [] distances = intent.getIntArrayExtra(KEY_DISTANCES) ;
                mUltrasonicAdapter.setDistances(distances) ;

            }else if(ACTION_SYSTEM_CHANGED_GENERA.equals(action)){

                String generaMessage = intent.getStringExtra(KEY_GENERA_MESSAGE) ;
                if(TextUtils.isEmpty(generaMessage)){

                    mSystemInfoTvw.setText(null);

                }else {

                    mSystemInfoTvw.setText("\t\t系统信息 \n" + generaMessage);
                }


            }else if(ACTION_OBD_DATA_CHANGED_GENERA.equals(action)){

                String generaMessage = intent.getStringExtra(KEY_GENERA_MESSAGE) ;
                if(TextUtils.isEmpty(generaMessage)){

                    mODBDataTvw.setText(null);

                }else {

                    mODBDataTvw.setText("\t\tODB 信息 \n" + generaMessage);
                }

            }


        }
    };

    private void unRegisterReceiver(){

        unregisterReceiver(broadcastReceiver);
    }


    private void refreshMoveFault(String message){

        mFaultTvw.setText(message);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.head_left_right_reset_btn) {

            MotionControl motionControl = new MotionControl() ;

            motionControl.setAction(MotionControl.Action.HEAD_LEFT_RIGHT_RESET);
            motionControl.setMode(MotionControl.Mode.DISTANCE_SPEED);

            sendMotionStatue(motionControl) ;

        }else if (i == R.id.head_left_btn) {

            MotionControl motionControl = new MotionControl() ;
            motionControl.setAction(MotionControl.Action.HEAD_LEFT);
            motionControl.setMode(MotionControl.Mode.DISTANCE_SPEED);
            motionControl.setDistance(MotionControl.Distance.valueOf(200));
            motionControl.setSpeed(MotionControl.Speed.valueOf(20));

            sendMotionStatue(motionControl) ;

        } else if (i == R.id.head_right_btn) {

            MotionControl motionControl = new MotionControl() ;
            motionControl.setAction(MotionControl.Action.HEAD_RIGHT);
            motionControl.setMode(MotionControl.Mode.TIME_SPEED);
            motionControl.setDistance(MotionControl.Distance.valueOf(200));
            motionControl.setSpeed(MotionControl.Speed.valueOf(20));

            sendMotionStatue(motionControl) ;

        } else if (i == R.id.head_left_right_stop_btn) {

            MotionControl motionControl = new MotionControl() ;
            motionControl.setAction(MotionControl.Action.HEAD_LEFT_RIGHT_SHAKE);
            motionControl.setMode(MotionControl.Mode.DISTANCE_SPEED);

            sendMotionStatue(motionControl) ;


        } else if (i == R.id.head_up_down_reset_btn) {

            MotionControl motionControl = new MotionControl() ;
            motionControl.setAction(MotionControl.Action.HEAD_UP_DOWN_RESET);
            motionControl.setMode(MotionControl.Mode.DISTANCE_SPEED);

            sendMotionStatue(motionControl) ;

        } else if (i == R.id.head_up_btn) {

            MotionControl motionControl = new MotionControl() ;
            motionControl.setAction(MotionControl.Action.HEAD_UP);
            motionControl.setMode(MotionControl.Mode.TIME_SPEED);
            motionControl.setDistance(MotionControl.Distance.valueOf(200));
            motionControl.setSpeed(MotionControl.Speed.valueOf(20));

            sendMotionStatue(motionControl) ;

        } else if (i == R.id.head_down_btn) {

            MotionControl motionControl = new MotionControl() ;
            motionControl.setAction(MotionControl.Action.HEAD_DOWN);
            motionControl.setMode(MotionControl.Mode.TIME_SPEED);
            motionControl.setDistance(MotionControl.Distance.valueOf(200));
            motionControl.setSpeed(MotionControl.Speed.valueOf(20));

            sendMotionStatue(motionControl) ;
        } else if (i == R.id.head_up_down_stop_btn) {

            MotionControl motionControl = new MotionControl() ;
            motionControl.setAction(MotionControl.Action.HEAD_UP_DOWN_SHAKE);
            motionControl.setMode(MotionControl.Mode.DISTANCE_SPEED);

            sendMotionStatue(motionControl) ;

        } else if (i == R.id.foot_forward_btn) {

            stopRandomMove() ;

            MotionControl motionControl = new MotionControl() ;
            motionControl.setAction(MotionControl.Action.FOOT_FORWARD);
            motionControl.setMode(MotionControl.Mode.TIME_SPEED);
            if(mIsSerialSct.isChecked()){

                motionControl.setType(MotionControl.Type.SERIAL );
                motionControl.setSpeed(MotionControl.Speed.valueOf(speed));

            }else{
                motionControl.setType(MotionControl.Type.SLAM );
            }

            sendMotionStatue(motionControl) ;

        } else if (i == R.id.foot_back_btn) {

            stopRandomMove() ;

            MotionControl motionControl = new MotionControl() ;
            motionControl.setAction(MotionControl.Action.FOOT_BACK);
            motionControl.setMode(MotionControl.Mode.TIME_SPEED);
            if(mIsSerialSct.isChecked()){

                motionControl.setType(MotionControl.Type.SERIAL );
                motionControl.setSpeed(MotionControl.Speed.valueOf(speed));

            }else{
                motionControl.setType(MotionControl.Type.SLAM );
            }

            sendMotionStatue(motionControl) ;

        } else if (i == R.id.foot_left_btn) {

            stopRandomMove() ;

            MotionControl motionControl = new MotionControl() ;
            motionControl.setAction(MotionControl.Action.FOOT_LEFT);
            motionControl.setMode(MotionControl.Mode.TIME_SPEED);
            if(mIsSerialSct.isChecked()){

                motionControl.setType(MotionControl.Type.SERIAL );
                motionControl.setSpeed(MotionControl.Speed.valueOf(speed));

            }else{
                motionControl.setType(MotionControl.Type.SLAM );
            }
            sendMotionStatue(motionControl) ;

        } else if (i == R.id.foot_right_btn) {

            stopRandomMove() ;

            MotionControl motionControl = new MotionControl() ;
            motionControl.setAction(MotionControl.Action.FOOT_RIGHT);
            motionControl.setMode(MotionControl.Mode.TIME_SPEED);
            if(mIsSerialSct.isChecked()){

                motionControl.setType(MotionControl.Type.SERIAL );
                motionControl.setSpeed(MotionControl.Speed.valueOf(speed));

            }else{
                motionControl.setType(MotionControl.Type.SLAM );
            }

            sendMotionStatue(motionControl) ;

        } else if (i == R.id.foot_stop_btn) {

            stopRandomMove() ;

            MotionControl motionControl = new MotionControl() ;
            motionControl.setAction(MotionControl.Action.FOOT_STOP);
            sendMotionStatue(motionControl) ;

        } else if (i == R.id.sound_location_btn) {

            String angle = mSoundLocationEtt.getText().toString();
            int ang = 90 ;
            try {

                ang = Integer.parseInt(angle) ;
            }catch (Exception e ){

                mSoundLocationEtt.setText(String.valueOf(ang));
            }

            MotionControl motionControl = new MotionControl() ;
            motionControl.setAction(MotionControl.Action.SOUND_LOCATION);
            motionControl.setType(mIsSerialSct.isChecked() ? MotionControl.Type.SERIAL : MotionControl.Type.SLAM);
            motionControl.setDistance(MotionControl.Distance.valueOf(ang));
            sendMotionStatue(motionControl) ;

        } else if(i == R.id.random_move_btn){

            //随机运动
            startRandomMove() ;

        } else {
        }
    }


    /**随机运动*/
    private void startRandomMove(){

        if(mRandomMoveThread == null || !mRandomMoveThread.isRun){

            mRandomMoveThread = new RandomMoveThread() ;
            mRandomMoveThread.start();
        }

    }

    private void stopRandomMove(){

        if(mRandomMoveThread != null && mRandomMoveThread.isRun){

            mRandomMoveThread.stopRun();
            mRandomMoveThread = null ;
        }
    }


    private RandomMoveThread mRandomMoveThread ;
    private class RandomMoveThread extends Thread{

        private boolean isRun ;

        private final int MAX_FORWARD_BACK = 2000 ;
        private final int MAX_LEFT_RIGHT = 4000 ;


        public RandomMoveThread(){

            isRun = true ;
        }


        public void stopRun(){

            isRun = false ;
        }

        @Override
        public void run() {

            while (isRun){

                // 前
                MotionControl motionControl = new MotionControl() ;
                motionControl.setAction(MotionControl.Action.FOOT_FORWARD);

                if(mIsSerialSct.isChecked()){

                    motionControl.setType(MotionControl.Type.SERIAL );
                    motionControl.setSpeed(MotionControl.Speed.valueOf(speed));

                }else{
                    motionControl.setType(MotionControl.Type.SLAM );
                }

                motionControl.setMode(MotionControl.Mode.TIME_SPEED);
                motionControl.setTime(MotionControl.Time.valueOf(MAX_FORWARD_BACK));

                sendMotionStatue(motionControl) ;

                try {
                    Thread.sleep(MAX_FORWARD_BACK);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!isRun){

                    return;
                }
                motionControl = new MotionControl() ;
                motionControl.setAction(MotionControl.Action.FOOT_STOP);
                sendMotionStatue(motionControl) ;


                // 后
                motionControl = new MotionControl() ;
                motionControl.setAction(MotionControl.Action.FOOT_BACK);

                if(mIsSerialSct.isChecked()){

                    motionControl.setType(MotionControl.Type.SERIAL );
                    motionControl.setSpeed(MotionControl.Speed.valueOf(speed));

                }else{
                    motionControl.setType(MotionControl.Type.SLAM );
                }

                motionControl.setMode(MotionControl.Mode.TIME_SPEED);
                motionControl.setTime(MotionControl.Time.valueOf(MAX_FORWARD_BACK));

                sendMotionStatue(motionControl) ;

                try {
                    Thread.sleep(MAX_FORWARD_BACK);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!isRun){

                    return;
                }
                motionControl = new MotionControl() ;
                motionControl.setAction(MotionControl.Action.FOOT_STOP);
                sendMotionStatue(motionControl) ;


                // 左
                motionControl = new MotionControl() ;
                motionControl.setAction(MotionControl.Action.FOOT_LEFT);

                if(mIsSerialSct.isChecked()){

                    motionControl.setType(MotionControl.Type.SERIAL );
                    motionControl.setSpeed(MotionControl.Speed.valueOf(speed));

                }else{
                    motionControl.setType(MotionControl.Type.SLAM );
                }

                motionControl.setMode(MotionControl.Mode.TIME_SPEED);
                motionControl.setTime(MotionControl.Time.valueOf(MAX_LEFT_RIGHT));

                sendMotionStatue(motionControl) ;


                try {
                    Thread.sleep(MAX_LEFT_RIGHT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!isRun){

                    return;
                }
                motionControl = new MotionControl() ;
                motionControl.setAction(MotionControl.Action.FOOT_STOP);
                sendMotionStatue(motionControl) ;


                // 右
                motionControl = new MotionControl() ;
                motionControl.setAction(MotionControl.Action.FOOT_RIGHT);

                if(mIsSerialSct.isChecked()){

                    motionControl.setType(MotionControl.Type.SERIAL );
                    motionControl.setSpeed(MotionControl.Speed.valueOf(speed));

                }else{
                    motionControl.setType(MotionControl.Type.SLAM );
                }

                motionControl.setMode(MotionControl.Mode.TIME_SPEED);
                motionControl.setTime(MotionControl.Time.valueOf(MAX_LEFT_RIGHT));

                sendMotionStatue(motionControl) ;
                try {
                    Thread.sleep(MAX_LEFT_RIGHT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                motionControl = new MotionControl() ;
                motionControl.setAction(MotionControl.Action.FOOT_STOP);
                sendMotionStatue(motionControl) ;
            }

        }

    }



    /**
     *
     * */
    private void sendMotionStatue(MotionControl motionControl){

        MotionClient.getInstance(this).sendMotionControlInMainThread(motionControl, null);
    }


    private UltrasonicAdapter mUltrasonicAdapter ;
    private class UltrasonicAdapter extends BaseAdapter{

        private String[] POSITION_NAMES = {"前左腿","前右腿","肚子","左胸","右胸","后左腿","后右腿"} ;

        private int[] distances ;
        private int[] preDistances ;

        public void setDistances(int[] distances){

            if(distances == null || distances.length != 7){

                return;
            }

            if(this.distances == null){

                this.preDistances = distances ;
                this.distances = distances ;

            }else{

                this.preDistances = this.distances ;
                this.distances = distances ;
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return distances == null ? 0 : distances.length;
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

                convertView = mLayoutInflater.inflate(R.layout.item_text, null) ;

                textView = convertView.findViewById(R.id.text_tvw) ;
                convertView.setTag(textView);

            }else{

                textView = (TextView) convertView.getTag();
            }

            int offset = Math.abs(distances[position] - preDistances[position]) ;
            if(offset > 10){

                textView.setBackgroundColor(0x80ffff00);

            }else{

                textView.setBackgroundColor(0x8000FF00);
            }

            textView.setText(POSITION_NAMES[position] + " ：" + distances[position] + "厘米");

            return convertView;
        }
    }




}
