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
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.ArmSendControl;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Direction;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Mode;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Type;
//import com.yongyida.robot.hardware.test.R;
//
///**
// * Created by HuangXiangXiang on 2018/4/23.
// * <p>
// * 手臂转到
// */
//public class TestArmFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener, TextWatcher {
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
//     * 位置0
//     */
//    private CheckBox mArm0Cbx;
//    /**
//     * 位置1
//     */
//    private CheckBox mArm1Cbx;
//    /**
//     * 位置2
//     */
//    private CheckBox mArm2Cbx;
//    /**
//     * 位置3
//     */
//    private CheckBox mArm3Cbx;
//    /**
//     * 位置4
//     */
//    private CheckBox mArm4Cbx;
//    /**
//     * 位置5
//     */
//    private CheckBox mArm5Cbx;
//    /**
//     * 反转
//     */
//    private Switch mTurnToSih;
//    /**
//     * 偏移量
//     */
//    private RadioButton mTypeToRbn;
//    /**
//     * 目标值
//     */
//    private RadioButton mTypeAtRbn;
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
//    /**
//     * 发送命令
//     */
//    private Button mSendCmdBtn;
//    private EditText mDistanceEtt;
//    private EditText mParameterEtt;
//    private EditText mDelayEtt;
//
//    private ArmSendControl mArmControl = new ArmSendControl();
//
//    private ArmSendControl.Joint joint0 = new ArmSendControl.Joint(0) ;
//    private ArmSendControl.Joint joint1 = new ArmSendControl.Joint(1) ;
//    private ArmSendControl.Joint joint2 = new ArmSendControl.Joint(2) ;
//    private ArmSendControl.Joint joint3 = new ArmSendControl.Joint(3) ;
//    private ArmSendControl.Joint joint4 = new ArmSendControl.Joint(4) ;
//    private ArmSendControl.Joint joint5 = new ArmSendControl.Joint(5) ;
//    private ArmSendControl.Joint showJoint ;
//
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_test_arm, null);
//
//        initView(view);
//        return view;
//    }
//
//
//    private void initView(View view) {
//        mDirectionRgp = (RadioGroup) view.findViewById(R.id.direction_rgp);
//        mDirectionLeftRbn = (RadioButton) view.findViewById(R.id.direction_left_rbn);
//        mDirectionRightRbn = (RadioButton) view.findViewById(R.id.direction_right_rbn);
//        mDirectionBothRbn = (RadioButton) view.findViewById(R.id.direction_both_rbn);
//        mArm0Cbx = (CheckBox) view.findViewById(R.id.arm_0_cbx);
//        mArm1Cbx = (CheckBox) view.findViewById(R.id.arm_1_cbx);
//        mArm2Cbx = (CheckBox) view.findViewById(R.id.arm_2_cbx);
//        mArm3Cbx = (CheckBox) view.findViewById(R.id.arm_3_cbx);
//        mArm4Cbx = (CheckBox) view.findViewById(R.id.arm_4_cbx);
//        mArm5Cbx = (CheckBox) view.findViewById(R.id.arm_5_cbx);
//        mTurnToSih = (Switch) view.findViewById(R.id.turn_to_sih);
//        mTypeToRbn = (RadioButton) view.findViewById(R.id.type_to_rbn);
//        mTypeAtRbn = (RadioButton) view.findViewById(R.id.type_by_rbn);
//        mTypeRgp = (RadioGroup) view.findViewById(R.id.type_rgp);
//        mModeStopRbn = (RadioButton) view.findViewById(R.id.mode_stop_rbn);
//        mModeTimeRbn = (RadioButton) view.findViewById(R.id.mode_time_rbn);
//        mModeSpeedRbn = (RadioButton) view.findViewById(R.id.mode_speed_rbn);
//        mModeLoopRbn = (RadioButton) view.findViewById(R.id.mode_loop_rbn);
//        mModeRgp = (RadioGroup) view.findViewById(R.id.mode_rgp);
//        mSendCmdBtn = (Button) view.findViewById(R.id.send_cmd_btn);
//        mSendCmdBtn.setOnClickListener(this);
//        mDistanceEtt = (EditText) view.findViewById(R.id.distance_ett);
//        mParameterEtt = (EditText) view.findViewById(R.id.parameter_ett);
//        mDelayEtt = (EditText) view.findViewById(R.id.delay_ett);
//
//
//
//
//        mArm0Cbx.setOnCheckedChangeListener(this);
//        mArm1Cbx.setOnCheckedChangeListener(this);
//        mArm2Cbx.setOnCheckedChangeListener(this);
//        mArm3Cbx.setOnCheckedChangeListener(this);
//        mArm4Cbx.setOnCheckedChangeListener(this);
//        mArm5Cbx.setOnCheckedChangeListener(this);
//        mTurnToSih.setOnCheckedChangeListener(this);
//
//        mDirectionRgp.setOnCheckedChangeListener(this);
//        mModeRgp.setOnCheckedChangeListener(this);
//        mTypeRgp.setOnCheckedChangeListener(this);
//
//        mDistanceEtt.addTextChangedListener(this);
//        mParameterEtt.addTextChangedListener(this);
//        mDelayEtt.addTextChangedListener(this);
//
//
//        setJointUI(joint0,true) ;
//    }
//
//
//    @Override
//    public void onClick(View v) {
//
//        if (v == mSendCmdBtn) {
//
//            SendClient.getInstance(getActivity()).send(null,mArmControl, null);
//        }
//    }
//
//    private CheckBox lastCbx ;
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
//        if(buttonView == mArm0Cbx){
//
//            setJointUI(joint0 ,isChecked) ;
//
//        }else if(buttonView == mArm1Cbx){
//
//            setJointUI(joint1,isChecked) ;
//        }else if(buttonView == mArm2Cbx){
//
//            setJointUI(joint2 ,isChecked) ;
//        }else if(buttonView == mArm3Cbx){
//
//            setJointUI(joint3, isChecked) ;
//        }else if(buttonView == mArm4Cbx){
//
//            setJointUI(joint4, isChecked) ;
//        }else if(buttonView == mArm5Cbx){
//
//            setJointUI(joint5, isChecked) ;
//
//        }else if(buttonView == mTurnToSih){
//
//            showJoint.setNegative(isChecked);
//            if(isChecked){
//                mTurnToSih.setText("反转");
//
//            }else {
//                mTurnToSih.setText("正转");
//            }
//        }
//
//    }
//
//
//    private void setJointUI(ArmSendControl.Joint joint, boolean isAdd ){
//
//        if(isAdd){
//            mArmControl.addJoint(joint);
//        }else {
//            mArmControl.removeJoint(joint) ;
//        }
//
//        if(joint == showJoint){
//
//            return;
//        }
//
//        showJoint = joint ;
//
//        if(joint.isNegative){
//            mTurnToSih.setText("反转");
//            mTurnToSih.setChecked(true);
//
//        }else {
//
//            mTurnToSih.setText("正转");
//            mTurnToSih.setChecked(false);
//        }
//
//        if(Type.BY.equals(joint.type)){
//
//            mTypeRgp.check(R.id.type_by_rbn);
//
//        }else if(Type.TO.equals(joint.type)){
//
//            mTypeRgp.check(R.id.type_to_rbn);
//
//        }else {
//
//            mTypeRgp.clearCheck();
//        }
//
//        if(Mode.TIME.equals(joint.mode)){
//
//            mModeRgp.check(R.id.mode_time_rbn);
//
//        }else  if(Mode.SPEED.equals(joint.mode)){
//
//            mModeRgp.check(R.id.mode_speed_rbn);
//
//        }else {
//
//            mModeRgp.clearCheck();
//        }
//
//        mDistanceEtt.setText(String.valueOf(joint.typeValue));
//        mParameterEtt.setText(String.valueOf(joint.modeValue));
//        mDelayEtt.setText(String.valueOf(joint.delay));
//
//    }
//
//
//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//        if(group == mTypeRgp){
//
//            if (checkedId == R.id.type_to_rbn) {
//
//                showJoint.setType(Type.TO);
//
//            } else if (checkedId == R.id.type_by_rbn) {
//
//                showJoint.setType(Type.BY);
//            }
//
//        }else if(group == mModeRgp){
//
//            if (checkedId == R.id.mode_time_rbn) {
//
//                showJoint.setMode(Mode.TIME);
//
//            } else if (checkedId == R.id.mode_speed_rbn) {
//
//                showJoint.setMode(Mode.SPEED);
//            }
//
//        }else if(group == mDirectionRgp){
//
//            if(checkedId == R.id.direction_left_rbn){
//
//                mArmControl.setDirection(Direction.LEFT);
//
//            }else if(checkedId == R.id.direction_right_rbn){
//
//                mArmControl.setDirection(Direction.RIGHT);
//
//            }else if(checkedId == R.id.direction_both_rbn){
//
//                mArmControl.setDirection(Direction.BOTH);
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
//        if(s == mDistanceEtt.getText()){
//
//            int value ;
//            try{
//
//                value = Integer.parseInt(s.toString()) ;
//            }catch (Exception e){
//
//                value = 0 ;
//                mDistanceEtt.setText(String.valueOf(value));
//            }
//            showJoint.typeValue = value ;
//
//        }else if(s == mParameterEtt.getText()){
//
//            int value ;
//            try{
//
//                value = Integer.parseInt(s.toString()) ;
//            }catch (Exception e){
//
//                value = 0 ;
//                mParameterEtt.setText(String.valueOf(value));
//            }
//            showJoint.modeValue = value ;
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
//            showJoint.delay = value ;
//
//        }
//
//    }
//}
