package com.yongyida.robot.hardware.test.item.hand;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.ArmSendControl;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.ChangeArmId;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Direction;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Mode;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Type;
import com.hiva.communicate.app.common.send.SendClient;
import com.yongyida.robot.hardware.test.R;

/**
 * Created by HuangXiangXiang on 2018/4/23.
 */
public class TestChangeArmIdFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = TestChangeArmIdFragment.class.getSimpleName() ;

    private View view;
    /**
     * 新设备
     */
    private RadioButton mSrcNewRbn;
    /**
     * 左侧
     */
    private RadioButton mSrcLeftRbn;
    /**
     * 右侧
     */
    private RadioButton mSrcRightRbn;
    private RadioGroup mSrcRgp;
    /**
     * 位置0
     */
    private RadioButton mSrcId0Rbn;
    /**
     * 位置1
     */
    private RadioButton mSrcId1Rbn;
    /**
     * 位置2
     */
    private RadioButton mSrcId2Rbn;
    /**
     * 位置3
     */
    private RadioButton mSrcId3Rbn;
    /**
     * 位置4
     */
    private RadioButton mSrcId4Rbn;
    /**
     * 位置5
     */
    private RadioButton mSrcId5Rbn;
    /**
     * 左侧
     */
    private RadioButton mDestLeftRbn;
    /**
     * 右侧
     */
    private RadioButton mDestRightRbn;
    private RadioGroup mDestRgp;
    /**
     * 位置0
     */
    private RadioButton mDestId0Rbn;
    /**
     * 位置1
     */
    private RadioButton mDestId1Rbn;
    /**
     * 位置2
     */
    private RadioButton mDestId2Rbn;
    /**
     * 位置3
     */
    private RadioButton mSrdId3Rbn;
    /**
     * 位置4
     */
    private RadioButton mDestId4Rbn;
    /**
     * 位置5
     */
    private RadioButton mDestId5Rbn;
    /**
     * 更改ID
     */
    private Button mChangeArmIdBtn;
    /**
     * 目标ID正向转动
     */
    private Button mPositiveTurnBtn;
    /**
     * 目标ID反向转动
     */
    private Button mNegativeTurnBtn;
    private RadioGroup mSrcIdRgp;
    private RadioGroup mDestIdRgp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test_change_arm_id, null);

        initView(view);



        return view;
    }

    private void initView(View view) {

        mSrcNewRbn = (RadioButton) view.findViewById(R.id.src_new_rbn);
        mSrcLeftRbn = (RadioButton) view.findViewById(R.id.src_left_rbn);
        mSrcRightRbn = (RadioButton) view.findViewById(R.id.src_right_rbn);
        mSrcRgp = (RadioGroup) view.findViewById(R.id.src_rgp);
        mSrcId0Rbn = (RadioButton) view.findViewById(R.id.src_id_0_rbn);
        mSrcId1Rbn = (RadioButton) view.findViewById(R.id.src_id_1_rbn);
        mSrcId2Rbn = (RadioButton) view.findViewById(R.id.src_id_2_rbn);
        mSrcId3Rbn = (RadioButton) view.findViewById(R.id.src_id_3_rbn);
        mSrcId4Rbn = (RadioButton) view.findViewById(R.id.src_id_4_rbn);
        mSrcId5Rbn = (RadioButton) view.findViewById(R.id.src_id_5_rbn);
        mSrcIdRgp = (RadioGroup) view.findViewById(R.id.src_id_rgp);
        mDestLeftRbn = (RadioButton) view.findViewById(R.id.dest_left_rbn);
        mDestRightRbn = (RadioButton) view.findViewById(R.id.dest_right_rbn);
        mDestRgp = (RadioGroup) view.findViewById(R.id.dest_rgp);
        mDestId0Rbn = (RadioButton) view.findViewById(R.id.dest_id_0_rbn);
        mDestId1Rbn = (RadioButton) view.findViewById(R.id.dest_id_1_rbn);
        mDestId2Rbn = (RadioButton) view.findViewById(R.id.dest_id_2_rbn);
        mSrdId3Rbn = (RadioButton) view.findViewById(R.id.dest_id_3_rbn);
        mDestId4Rbn = (RadioButton) view.findViewById(R.id.dest_id_4_rbn);
        mDestId5Rbn = (RadioButton) view.findViewById(R.id.dest_id_5_rbn);
        mDestIdRgp = (RadioGroup) view.findViewById(R.id.dest_id_rgp);
        mChangeArmIdBtn = (Button) view.findViewById(R.id.change_arm_id_btn);
        mChangeArmIdBtn.setOnClickListener(this);
        mPositiveTurnBtn = (Button) view.findViewById(R.id.positive_turn_btn);
        mPositiveTurnBtn.setOnClickListener(this);
        mNegativeTurnBtn = (Button) view.findViewById(R.id.negative_turn_btn);
        mNegativeTurnBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.change_arm_id_btn) {

            setSrcId() ;
            setDestId();

            SendClient.getInstance(getActivity()).send(mChangeArmId, null);

        } else if (i == R.id.positive_turn_btn) {

            turnArm(false) ;

        } else if (i == R.id.negative_turn_btn) {

            turnArm(true) ;

        } else {
        }
    }

    private ChangeArmId mChangeArmId = new ChangeArmId();

    private void setSrcId() {

        int srcCheckId = mSrcRgp.getCheckedRadioButtonId() ;
        int srcCheckIndex ;
        if(srcCheckId == R.id.src_left_rbn){

            srcCheckIndex = 1 ;

        }else if(srcCheckId == R.id.src_right_rbn){

            srcCheckIndex = 2 ;

        }else {

            srcCheckIndex = 0 ;
        }
        if(srcCheckIndex == 0){

            LogHelper.i(TAG, LogHelper.__TAG__() + ", " + srcCheckIndex + "-" + 0);

//            mChangeArmId.setSrcId(1);
            mChangeArmId.setSrcId(0xFE);

        }else{

            int srcIdCheckId = mSrcIdRgp.getCheckedRadioButtonId() ;
            int srcIdCheckIndex ;
            if(srcIdCheckId == R.id.src_id_1_rbn){

                srcIdCheckIndex = 1 ;
            }else if(srcIdCheckId == R.id.src_id_2_rbn){

                srcIdCheckIndex = 2 ;

            }else if(srcIdCheckId == R.id.src_id_3_rbn){

                srcIdCheckIndex = 3 ;
            }else if(srcIdCheckId == R.id.src_id_4_rbn){

                srcIdCheckIndex = 4 ;
            }else if(srcIdCheckId == R.id.src_id_5_rbn){

                srcIdCheckIndex = 5 ;
            }else {

                srcIdCheckIndex = 0 ;
            }

            LogHelper.i(TAG, LogHelper.__TAG__() + ", " + srcCheckIndex + "-" + srcIdCheckIndex);
            mChangeArmId.setSrcId((srcCheckIndex-1) * 6 + srcIdCheckIndex + 2);
        }


    }

    private void setDestId() {

        int destCheckId = mDestRgp.getCheckedRadioButtonId() ;
        int destCheckIndex;
        if(destCheckId ==  R.id.dest_right_rbn){

            destCheckIndex = 1 ;
        }else {

            destCheckIndex = 0 ;
        }


        int destIdCheckId = mDestIdRgp.getCheckedRadioButtonId() ;
        int destIdCheckIndex;
        if(destIdCheckId == R.id.dest_id_1_rbn){

            destIdCheckIndex = 1 ;

        }else if(destIdCheckId == R.id.dest_id_2_rbn){

            destIdCheckIndex = 2 ;

        }else if(destIdCheckId == R.id.dest_id_3_rbn){

            destIdCheckIndex = 3 ;
        }else if(destIdCheckId == R.id.dest_id_4_rbn){

            destIdCheckIndex = 4 ;
        }else if(destIdCheckId == R.id.dest_id_5_rbn){

            destIdCheckIndex = 5 ;
        }else {

            destIdCheckIndex = 0 ;
        }

        LogHelper.i(TAG, LogHelper.__TAG__() + ",  " + destCheckIndex + "-" + destIdCheckIndex);

        mChangeArmId.setDestId((destCheckIndex) * 6 + destIdCheckIndex + 2);

    }


    // 控制手臂
    private ArmSendControl mArmControl = new ArmSendControl() ;
    private ArmSendControl.Joint mJoint ;

    private void turnArm(boolean isNegative){

        if(mJoint == null){

            mJoint = new ArmSendControl.Joint() ;
            mJoint.setType(Type.TO);
            mJoint.setTypeValue(10);

            mJoint.setMode(Mode.TIME);
            mJoint.setModeValue(1000);

            mArmControl.addJoint(mJoint);
        }


        int destCheckId = mDestRgp.getCheckedRadioButtonId() ;
        if(destCheckId ==  R.id.dest_right_rbn){

            mArmControl.setDirection(Direction.RIGHT);
        }else {

            mArmControl.setDirection(Direction.LEFT);
        }


        int destIdCheckId = mDestIdRgp.getCheckedRadioButtonId() ;
        int destIdCheckIndex;
        if(destIdCheckId == R.id.dest_id_1_rbn){

            destIdCheckIndex = 1 ;

        }else if(destIdCheckId == R.id.dest_id_2_rbn){

            destIdCheckIndex = 2 ;

        }else if(destIdCheckId == R.id.dest_id_3_rbn){

            destIdCheckIndex = 3 ;
        }else if(destIdCheckId == R.id.dest_id_4_rbn){

            destIdCheckIndex = 4 ;
        }else if(destIdCheckId == R.id.dest_id_5_rbn){

            destIdCheckIndex = 5 ;
        }else {

            destIdCheckIndex = 0 ;
        }

        mJoint.setId(destIdCheckIndex);
        mJoint.setNegative(isNegative);

        SendClient.getInstance(getActivity()).send(mArmControl, null);

    }

}
