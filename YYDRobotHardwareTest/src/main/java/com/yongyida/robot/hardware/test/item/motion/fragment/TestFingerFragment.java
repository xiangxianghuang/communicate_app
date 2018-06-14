//package com.yongyida.robot.hardware.test.item.motion.fragment;
//
//import android.app.Fragment;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Switch;
//
//import com.hiva.communicate.app.common.send.SendClient;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.FingerSendControl;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Direction;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Mode;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Type;
//import com.yongyida.robot.hardware.test.R;
//
///**
// * Created by HuangXiangXiang on 2018/4/20.
// * <p>
// * 手指转动
// */
//public class TestFingerFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener, TextWatcher {
//
//
//    private View view;
//    /**
//     * 左手
//     */
//    private RadioButton mDirectionLeftRbn;
//    /**
//     * 右手
//     */
//    private RadioButton mDirectionRightRbn;
//    /**
//     * 双手
//     */
//    private RadioButton mDirectionBothRbn;
//    private RadioGroup mDirectionRgp;
//    /**
//     * 大拇指
//     */
//    private CheckBox mFinger0Cbx;
//    /**
//     * 食指
//     */
//    private CheckBox mFinger1Cbx;
//    /**
//     * 中指
//     */
//    private CheckBox mFinger2Cbx;
//    /**
//     * 无名指
//     */
//    private CheckBox mFinger3Cbx;
//    /**
//     * 小指
//     */
//    private CheckBox mFinger4Cbx;
//    /**
//     * 偏移量
//     */
//    private RadioButton mTypeByRbn;
//    /**
//     * 目标值
//     */
//    private RadioButton mTypeToRbn;
//    private RadioGroup mTypeRgp;
//    /**
//     * 停止
//     */
//    private RadioButton mModeStopRbn;
//    /**
//     * 时间
//     */
//    private RadioButton mModeTimeRbn;
//    /**
//     * 速度
//     */
//    private RadioButton mModeSpeedRbn;
//    /**
//     * 循环
//     */
//    private RadioButton mModeLoopRbn;
//    private RadioGroup mModeRgp;
//    private EditText mTypeValueEdt;
//    private EditText mModeValueEdt;
//    private EditText mDelayEtt;
//    /**
//     * 发送命令
//     */
//    private Button mSendCmdBtn;
//
//
//    private FingerSendControl mFingerControl = new FingerSendControl();
//
//    private FingerSendControl.Finger finger0 = new FingerSendControl.Finger(0);
//    private FingerSendControl.Finger finger1 = new FingerSendControl.Finger(1);
//    private FingerSendControl.Finger finger2 = new FingerSendControl.Finger(2);
//    private FingerSendControl.Finger finger3 = new FingerSendControl.Finger(3);
//    private FingerSendControl.Finger finger4 = new FingerSendControl.Finger(4);
//    private FingerSendControl.Finger showFinger;
//    /**
//     * 摊开
//     */
//    private Switch mTurnToSih;
//
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_test_finger, null);
//
//        initView(view);
//        return view;
//    }
//
//    private void initView(View view) {
//
//        mDirectionLeftRbn = (RadioButton) view.findViewById(R.id.direction_left_rbn);
//        mDirectionRightRbn = (RadioButton) view.findViewById(R.id.direction_right_rbn);
//        mDirectionBothRbn = (RadioButton) view.findViewById(R.id.direction_both_rbn);
//        mDirectionRgp = (RadioGroup) view.findViewById(R.id.direction_rgp);
//        mFinger0Cbx = (CheckBox) view.findViewById(R.id.finger_0_cbx);
//        mFinger1Cbx = (CheckBox) view.findViewById(R.id.finger_1_cbx);
//        mFinger2Cbx = (CheckBox) view.findViewById(R.id.finger_2_cbx);
//        mFinger3Cbx = (CheckBox) view.findViewById(R.id.finger_3_cbx);
//        mFinger4Cbx = (CheckBox) view.findViewById(R.id.finger_4_cbx);
//        mTurnToSih = (Switch) view.findViewById(R.id.turn_to_sih);
//        mTypeByRbn = (RadioButton) view.findViewById(R.id.type_by_rbn);
//        mTypeToRbn = (RadioButton) view.findViewById(R.id.type_to_rbn);
//        mTypeRgp = (RadioGroup) view.findViewById(R.id.type_rgp);
//        mModeStopRbn = (RadioButton) view.findViewById(R.id.mode_stop_rbn);
//        mModeTimeRbn = (RadioButton) view.findViewById(R.id.mode_time_rbn);
//        mModeSpeedRbn = (RadioButton) view.findViewById(R.id.mode_speed_rbn);
//        mModeLoopRbn = (RadioButton) view.findViewById(R.id.mode_loop_rbn);
//        mModeRgp = (RadioGroup) view.findViewById(R.id.mode_rgp);
//        mTypeValueEdt = (EditText) view.findViewById(R.id.type_value_edt);
//        mModeValueEdt = (EditText) view.findViewById(R.id.mode_value_edt);
//        mSendCmdBtn = (Button) view.findViewById(R.id.send_cmd_btn);
//        mSendCmdBtn.setOnClickListener(this);
//        mDelayEtt = (EditText) view.findViewById(R.id.delay_ett);
//
//
//
//
//        mFinger0Cbx.setOnCheckedChangeListener(this) ;
//        mFinger1Cbx.setOnCheckedChangeListener(this) ;
//        mFinger2Cbx.setOnCheckedChangeListener(this) ;
//        mFinger3Cbx.setOnCheckedChangeListener(this) ;
//        mFinger4Cbx.setOnCheckedChangeListener(this) ;
//        mTurnToSih.setOnCheckedChangeListener(this);
//
//        mDirectionRgp.setOnCheckedChangeListener(this);
//        mModeRgp.setOnCheckedChangeListener(this);
//        mTypeRgp.setOnCheckedChangeListener(this);
//
//        mModeValueEdt.addTextChangedListener(this);
//        mTypeValueEdt.addTextChangedListener(this);
//        mDelayEtt.addTextChangedListener(this);
//
//
//        setFingerUI(finger0, true);
//    }
//
//
//    @Override
//    public void onClick(View v) {
//
//        if (v == mSendCmdBtn) {
//
//            SendClient.getInstance(getActivity()).send(null, mFingerControl, null);
//        }
//    }
//
//
//    private void setFingerUI(FingerSendControl.Finger finger, boolean isAdd) {
//
//        if(isAdd){
//            mFingerControl.addFinger(finger);
//        }else {
//            mFingerControl.removeFinger(finger) ;
//        }
//
//        if(finger == showFinger){
//
//            return;
//        }
//
//        showFinger = finger ;
//
//        if(finger.isOpen){
//            mTurnToSih.setText("反转");
//            mTurnToSih.setChecked(true);
//
//        }else {
//
//            mTurnToSih.setText("正转");
//            mTurnToSih.setChecked(false);
//        }
//
//        if(Type.BY.equals(finger.type)){
//
//            mTypeRgp.check(R.id.type_by_rbn);
//
//        }else if(Type.TO.equals(finger.type)){
//
//            mTypeRgp.check(R.id.type_to_rbn);
//
//
//        }else {
//
//            mTypeRgp.clearCheck();
//        }
//
//        if(Mode.TIME.equals(finger.mode)){
//
//            mModeRgp.check(R.id.mode_time_rbn);
//
//        }else  if(Mode.SPEED.equals(finger.mode)){
//
//            mModeRgp.check(R.id.mode_speed_rbn);
//
//        }else  if(Mode.LOOP.equals(finger.mode)){
//
//            mModeRgp.check(R.id.mode_loop_rbn);
//
//        }else  if(Mode.STOP.equals(finger.mode)){
//
//            mModeRgp.check(R.id.mode_stop_rbn);
//
//        }else {
//
//            mModeRgp.clearCheck();
//        }
//
//        mTypeValueEdt.setText(String.valueOf(finger.typeValue));
//        mModeValueEdt.setText(String.valueOf(finger.modeValue));
//        mDelayEtt.setText(String.valueOf(finger.delay));
//
//
//    }
//
//    private CheckBox lastCbx ;
//
//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//        if(buttonView instanceof CheckBox ){
//
//            if(lastCbx != null){
//
//                lastCbx.setBackgroundColor(Color.TRANSPARENT);
//            }
//
//            buttonView.setBackgroundColor(Color.GRAY);
//            lastCbx = (CheckBox) buttonView;
//        }
//
//
//
//        if(buttonView == mFinger0Cbx){
//            setFingerUI(finger0 ,isChecked) ;
//
//        }else if(buttonView == mFinger1Cbx){
//            setFingerUI(finger1 ,isChecked) ;
//
//        }else if(buttonView == mFinger2Cbx){
//            setFingerUI(finger2 ,isChecked) ;
//
//        }else if(buttonView == mFinger3Cbx){
//            setFingerUI(finger3 ,isChecked) ;
//
//        }else if(buttonView == mFinger4Cbx){
//            setFingerUI(finger4 ,isChecked) ;
//
//        }else if(buttonView == mTurnToSih){
//
//            showFinger.setOpen(isChecked);
//            if(isChecked){
//                mTurnToSih.setText("握紧");
//
//            }else {
//                mTurnToSih.setText("张开");
//            }
//
//        }
//
//    }
//
//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//        if(group == mTypeRgp){
//
//            if (checkedId == R.id.type_to_rbn) {
//
//                showFinger.setType(Type.TO);
//
//            } else if (checkedId == R.id.type_by_rbn) {
//
//                showFinger.setType(Type.BY);
//            }
//
//        }else if(group == mModeRgp){
//
//            if (checkedId == R.id.mode_time_rbn) {
//
//                showFinger.setMode(Mode.TIME);
//
//            } else if (checkedId == R.id.mode_speed_rbn) {
//
//                showFinger.setMode(Mode.SPEED);
//
//            } else if (checkedId == R.id.mode_stop_rbn) {
//
//                showFinger.setMode(Mode.STOP);
//
//            } else if (checkedId == R.id.mode_loop_rbn) {
//
//                showFinger.setMode(Mode.LOOP);
//            }
//
//        }else if(group == mDirectionRgp){
//
//            if(checkedId == R.id.direction_left_rbn){
//
//                mFingerControl.setDirection(Direction.LEFT);
//
//            }else if(checkedId == R.id.direction_right_rbn){
//
//                mFingerControl.setDirection(Direction.RIGHT);
//
//            }else if(checkedId == R.id.direction_both_rbn){
//
//                mFingerControl.setDirection(Direction.BOTH);
//
//            }
//        }
//
//    }
//
//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//
//        if(s == mTypeValueEdt.getText()){
//
//            int value ;
//            try{
//
//                value = Integer.parseInt(s.toString()) ;
//            }catch (Exception e){
//
//                value = 0 ;
//                mTypeValueEdt.setText(String.valueOf(value));
//            }
//            showFinger.typeValue = value ;
//
//        }else if(s == mModeValueEdt.getText()){
//
//            int value ;
//            try{
//
//                value = Integer.parseInt(s.toString()) ;
//            }catch (Exception e){
//
//                value = 0 ;
//                mModeValueEdt.setText(String.valueOf(value));
//            }
//            showFinger.modeValue = value ;
//
//        }else if(s == mDelayEtt.getText()){
//
//            int value ;
//            try{
//
//                value = Integer.parseInt(s.toString()) ;
//            }catch (Exception e){
//
//                value = 0 ;
//                mDelayEtt.setText(String.valueOf(value));
//            }
//            showFinger.delay = value ;
//
//        }
//
//    }
//}