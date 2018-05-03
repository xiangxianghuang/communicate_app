package com.yongyida.robot.hardware.test.item.hand;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yongyida.robot.communicate.app.hardware.motion.data.HandAction;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;
import com.yongyida.robot.hardware.client.MotionClient;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

/**
 * Created by HuangXiangXiang on 2018/4/20.
 * 手臂 运动
 */
public class TestActionFragment extends Fragment implements View.OnClickListener {

    /**
     * 手臂初始化
     */
    private Button mArmResetBtn;
    /**
     * 左
     */
    private RadioButton mArmLeftRbn;
    /**
     * 右
     */
    private RadioButton mArmRightRbn;
    /**
     * 全部
     */
    private RadioButton mArmAllRbn;
    private RadioGroup mArmResetRgp;
    /**
     * 手掌初始化
     */
    private Button mPalmResetBtn;
    /**
     * 左
     */
    private RadioButton mPalmLeftBtn;
    /**
     * 右
     */
    private RadioButton mPalmRightBtn;
    /**
     * 全部
     */
    private RadioButton mPalmAllBtn;
    private RadioGroup mPalmResetRgp;
    /**
     * 手指轮动
     */
    private Button mFingerWheelBtn;
    /**
     * 233
     */
    private EditText mFingerWheelEtt;
    /**
     * 握手
     */
    private Button mHandSharkBtn;
    /**
     * OK
     */
    private Button mOkBtn;
    /**
     * 点赞
     */
    private Button mGoodBtn;
    /**
     * 跳舞
     */
    private Button mDanceBtn;
    /**
     * 石头
     */
    private Button mRockBtn;
    /**
     * 剪刀
     */
    private Button mScissorsBtn;
    /**
     * 布
     */
    private Button mPaperBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test_action, null) ;
        initView(view);
        return view;
    }


    private void initView(View view) {
        mArmResetBtn = (Button) view.findViewById(R.id.arm_reset_btn);
        mArmResetBtn.setOnClickListener(this);
        mArmLeftRbn = (RadioButton) view.findViewById(R.id.arm_left_rbn);
        mArmRightRbn = (RadioButton) view.findViewById(R.id.arm_right_rbn);
        mArmAllRbn = (RadioButton) view.findViewById(R.id.arm_all_rbn);
        mArmResetRgp = (RadioGroup) view.findViewById(R.id.arm_reset_rgp);
        mPalmResetBtn = (Button) view.findViewById(R.id.palm_reset_btn);
        mPalmResetBtn.setOnClickListener(this);
        mPalmLeftBtn = (RadioButton) view.findViewById(R.id.palm_left_rbn);
        mPalmRightBtn = (RadioButton) view.findViewById(R.id.palm_right_rbn);
        mPalmAllBtn = (RadioButton) view.findViewById(R.id.palm_all_rbn);
        mPalmResetRgp = (RadioGroup) view.findViewById(R.id.palm_reset_rgp);
        mFingerWheelBtn = (Button) view.findViewById(R.id.finger_wheel_btn);
        mFingerWheelBtn.setOnClickListener(this);
        mFingerWheelEtt = (EditText) view.findViewById(R.id.finger_wheel_ett);
        mHandSharkBtn = (Button) view.findViewById(R.id.hand_shark_btn);
        mHandSharkBtn.setOnClickListener(this);
        mOkBtn = (Button) view.findViewById(R.id.ok_btn);
        mOkBtn.setOnClickListener(this);
        mGoodBtn = (Button) view.findViewById(R.id.good_btn);
        mGoodBtn.setOnClickListener(this);
        mDanceBtn = (Button) view.findViewById(R.id.dance_btn);
        mDanceBtn.setOnClickListener(this);
        mRockBtn = (Button) view.findViewById(R.id.rock_btn);
        mRockBtn.setOnClickListener(this);
        mScissorsBtn = (Button) view.findViewById(R.id.scissors_btn);
        mScissorsBtn.setOnClickListener(this);
        mPaperBtn = (Button) view.findViewById(R.id.paper_btn);
        mPaperBtn.setOnClickListener(this);
    }

    private HandAction mHandAction = new HandAction() ;

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.arm_reset_btn) {

            int id = mArmResetRgp.getCheckedRadioButtonId() ;
            HandAction.Direction direction ;
            if(id == R.id.arm_left_rbn){

                direction = HandAction.Direction.LEFT ;

            }else if(id == R.id.arm_right_rbn){

                direction = HandAction.Direction.RIGHT ;

            }else{

                direction = HandAction.Direction.ALL ;
            }
            mHandAction.reset(HandAction.Position.RESET_ARM, direction);


        } else if (i == R.id.palm_reset_btn) {

            int id = mPalmResetRgp.getCheckedRadioButtonId() ;
            HandAction.Direction direction ;
            if(id == R.id.palm_left_rbn){

                direction = HandAction.Direction.LEFT ;

            }else if(id == R.id.palm_right_rbn){

                direction = HandAction.Direction.RIGHT ;

            }else{

                direction = HandAction.Direction.ALL ;
            }
            mHandAction.reset(HandAction.Position.RESET_PALM, direction);


        } else if (i == R.id.finger_wheel_btn) {

            byte times;
            String text = mFingerWheelEtt.getText().toString() ;
            try{

                times = Byte.parseByte(text) ;
            }catch (Exception e){

                mFingerWheelEtt.setText("2");
                times = 2 ;
            }
            mHandAction.fingerWheel(times);


        } else if (i == R.id.hand_shark_btn) {

            mHandAction.setGesture(HandAction.Gesture.GESTURE_HAND_SHAKE);

        } else if (i == R.id.ok_btn) {

            mHandAction.setGesture(HandAction.Gesture.GESTURE_OK);

        } else if (i == R.id.good_btn) {

            mHandAction.setGesture(HandAction.Gesture.GESTURE_GOOD);

        } else if (i == R.id.dance_btn) {

            mHandAction.setGesture(HandAction.Gesture.GESTURE_DANCE);

        } else if (i == R.id.rock_btn) {

            mHandAction.setGesture(HandAction.Gesture.GESTURE_ROCK);

        } else if (i == R.id.scissors_btn) {

            mHandAction.setGesture(HandAction.Gesture.GESTURE_SCISSORS);

        } else if (i == R.id.paper_btn) {

            mHandAction.setGesture(HandAction.Gesture.GESTURE_PAPER);
        }

        MotionSend motionSend = new MotionSend();
        motionSend.setHandAction(mHandAction);

        MotionClient.getInstance(getContext()).sendInMainThread(motionSend, null);

    }
}
