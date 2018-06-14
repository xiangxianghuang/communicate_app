package com.yongyida.robot.hardware.test.item.motion.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.yongyida.robot.communicate.app.hardware.motion.send.data.FingerControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.GroupFrameControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.OneFrameScript;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.SteeringControl;
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
 * Create By HuangXiangXiang 2018/5/28
 */
public class RecordArmDialog extends Dialog implements View.OnClickListener {

    private EditText mTimeEtt;
    private EditText mDelayEtt;
    private Switch mChangedActionSth;
    private Button mCancelBtn;
    private Button mOkBtn;

    private EditText mLeftFinger0;
    private EditText mLeftFinger1;
    private EditText mLeftFinger2;
    private EditText mLeftFinger3;
    private EditText mLeftFinger4;
    private EditText mRightFinger0;
    private EditText mRightFinger1;
    private EditText mRightFinger2;
    private EditText mRightFinger3;
    private EditText mRightFinger4;

    public RecordArmDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_aciton);
        this.mTimeEtt = (EditText) findViewById(R.id.time_ett);
        this.mDelayEtt = (EditText) findViewById(R.id.delay_ett);
        this.mChangedActionSth = (Switch) findViewById(R.id.changed_action_sth);
        this.mCancelBtn = (Button) findViewById(R.id.cancel_btn);
        this.mOkBtn = (Button) findViewById(R.id.ok_btn);

        this.mLeftFinger0 = (EditText) findViewById(R.id.left_finger_0);
        this.mLeftFinger1 = (EditText) findViewById(R.id.left_finger_1);
        this.mLeftFinger2 = (EditText) findViewById(R.id.left_finger_2);
        this.mLeftFinger3 = (EditText) findViewById(R.id.left_finger_3);
        this.mLeftFinger4 = (EditText) findViewById(R.id.left_finger_4);
        this.mRightFinger0 = (EditText) findViewById(R.id.right_finger_0);
        this.mRightFinger1 = (EditText) findViewById(R.id.right_finger_1);
        this.mRightFinger2 = (EditText) findViewById(R.id.right_finger_2);
        this.mRightFinger3 = (EditText) findViewById(R.id.right_finger_3);
        this.mRightFinger4 = (EditText) findViewById(R.id.right_finger_4);

        mCancelBtn.setOnClickListener(this);
        mOkBtn.setOnClickListener(this);

        refreshUI() ;
    }


    private int getIntData(EditText editText){

        return getIntData(editText, 0) ;
    }

    private int getIntData(EditText editText ,int defaultValue){

        String valueString = editText.getText().toString();
        int value;
        try {

            value = Integer.parseInt(valueString);
        } catch (Exception e) {

            value = defaultValue;
        }

        return value ;
    }



    @Override
    public void onClick(View v) {

        if (mOkBtn == v) {

            int time = getIntData(mTimeEtt , 2000) ;
            int delay = getIntData(mDelayEtt , 0) ;

            recordArmAngle.setUsedTime(time);
            recordArmAngle.setWaitTime(delay);

            FingerControl fingerControl = recordArmAngle.getHandControl().getFingerControl() ;
            fingerControl.getFinger(SteeringControl.Position.FINGER_LEFT_0).getDistance().setValue(getIntData(mLeftFinger0));
            fingerControl.getFinger(SteeringControl.Position.FINGER_LEFT_1).getDistance().setValue(getIntData(mLeftFinger1));
            fingerControl.getFinger(SteeringControl.Position.FINGER_LEFT_2).getDistance().setValue(getIntData(mLeftFinger2));
            fingerControl.getFinger(SteeringControl.Position.FINGER_LEFT_3).getDistance().setValue(getIntData(mLeftFinger3));
            fingerControl.getFinger(SteeringControl.Position.FINGER_LEFT_4).getDistance().setValue(getIntData(mLeftFinger4));

            fingerControl.getFinger(SteeringControl.Position.FINGER_RIGHT_0).getDistance().setValue(getIntData(mRightFinger0));
            fingerControl.getFinger(SteeringControl.Position.FINGER_RIGHT_1).getDistance().setValue(getIntData(mRightFinger1));
            fingerControl.getFinger(SteeringControl.Position.FINGER_RIGHT_2).getDistance().setValue(getIntData(mRightFinger2));
            fingerControl.getFinger(SteeringControl.Position.FINGER_RIGHT_3).getDistance().setValue(getIntData(mRightFinger3));
            fingerControl.getFinger(SteeringControl.Position.FINGER_RIGHT_4).getDistance().setValue(getIntData(mRightFinger4));

            if (mOnChangedListener != null) {

                mOnChangedListener.onChanged(recordArmAngle, mChangedActionSth.isChecked());
            }
        }

        dismiss();
    }


    private OnChangedListener mOnChangedListener;
    private OneFrameScript recordArmAngle;

    public void setRecordArmAngle(OneFrameScript recordArmAngle) {

        this.recordArmAngle = recordArmAngle;
        refreshUI() ;
    }


    private void refreshUI(){

        if (mTimeEtt != null && mDelayEtt != null && recordArmAngle != null) {

            this.mChangedActionSth.setChecked(false);
            this.mTimeEtt.setText(String.valueOf(recordArmAngle.getUsedTime()));
            this.mDelayEtt.setText(String.valueOf(recordArmAngle.getWaitTime()));

            FingerControl fingerControl = recordArmAngle.getHandControl().getFingerControl() ;
            this.mLeftFinger0.setText(String.valueOf(fingerControl.getFinger(SteeringControl.Position.FINGER_LEFT_0).getDistance().getValue()));
            this.mLeftFinger1.setText(String.valueOf(fingerControl.getFinger(SteeringControl.Position.FINGER_LEFT_1).getDistance().getValue()));
            this.mLeftFinger2.setText(String.valueOf(fingerControl.getFinger(SteeringControl.Position.FINGER_LEFT_2).getDistance().getValue()));
            this.mLeftFinger3.setText(String.valueOf(fingerControl.getFinger(SteeringControl.Position.FINGER_LEFT_3).getDistance().getValue()));
            this.mLeftFinger4.setText(String.valueOf(fingerControl.getFinger(SteeringControl.Position.FINGER_LEFT_4).getDistance().getValue()));

            this.mRightFinger0.setText(String.valueOf(fingerControl.getFinger(SteeringControl.Position.FINGER_RIGHT_0).getDistance().getValue()));
            this.mRightFinger1.setText(String.valueOf(fingerControl.getFinger(SteeringControl.Position.FINGER_RIGHT_1).getDistance().getValue()));
            this.mRightFinger2.setText(String.valueOf(fingerControl.getFinger(SteeringControl.Position.FINGER_RIGHT_2).getDistance().getValue()));
            this.mRightFinger3.setText(String.valueOf(fingerControl.getFinger(SteeringControl.Position.FINGER_RIGHT_3).getDistance().getValue()));
            this.mRightFinger4.setText(String.valueOf(fingerControl.getFinger(SteeringControl.Position.FINGER_RIGHT_4).getDistance().getValue()));
        }


    }

    public interface OnChangedListener {

        void onChanged(OneFrameScript recordArmAngle, boolean isChangedAction);
    }

    public void setOnChangedListener(OnChangedListener onChangedListener) {
        this.mOnChangedListener = onChangedListener;
    }

}
