package com.yongyida.robot.hardware.test.item.hand.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.hand.bean.RecordArmAngle;



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


        this.mChangedActionSth.setChecked(false);
        this.mTimeEtt.setText(String.valueOf(recordArmAngle.getTime()));
        this.mDelayEtt.setText(String.valueOf(recordArmAngle.getDelay()));

        this.mLeftFinger0.setText(String.valueOf(recordArmAngle.getLeftFingerControl().getFingers().get(0).typeValue));
        this.mLeftFinger1.setText(String.valueOf(recordArmAngle.getLeftFingerControl().getFingers().get(1).typeValue));
        this.mLeftFinger2.setText(String.valueOf(recordArmAngle.getLeftFingerControl().getFingers().get(2).typeValue));
        this.mLeftFinger3.setText(String.valueOf(recordArmAngle.getLeftFingerControl().getFingers().get(3).typeValue));
        this.mLeftFinger4.setText(String.valueOf(recordArmAngle.getLeftFingerControl().getFingers().get(4).typeValue));

        this.mRightFinger0.setText(String.valueOf(recordArmAngle.getRightFingerControl().getFingers().get(0).typeValue));
        this.mRightFinger1.setText(String.valueOf(recordArmAngle.getRightFingerControl().getFingers().get(1).typeValue));
        this.mRightFinger2.setText(String.valueOf(recordArmAngle.getRightFingerControl().getFingers().get(2).typeValue));
        this.mRightFinger3.setText(String.valueOf(recordArmAngle.getRightFingerControl().getFingers().get(3).typeValue));
        this.mRightFinger4.setText(String.valueOf(recordArmAngle.getRightFingerControl().getFingers().get(4).typeValue));


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

            recordArmAngle.setTime(time);
            recordArmAngle.setDelay(delay);

            recordArmAngle.getLeftFingerControl().getFingers().get(0).typeValue = getIntData(mLeftFinger0) ;
            recordArmAngle.getLeftFingerControl().getFingers().get(1).typeValue = getIntData(mLeftFinger1) ;
            recordArmAngle.getLeftFingerControl().getFingers().get(2).typeValue= getIntData(mLeftFinger2) ;
            recordArmAngle.getLeftFingerControl().getFingers().get(3).typeValue = getIntData(mLeftFinger3) ;
            recordArmAngle.getLeftFingerControl().getFingers().get(4).typeValue = getIntData(mLeftFinger4) ;
            recordArmAngle.getRightFingerControl().getFingers().get(0).typeValue = getIntData(mRightFinger0) ;
            recordArmAngle.getRightFingerControl().getFingers().get(1).typeValue= getIntData(mRightFinger1) ;
            recordArmAngle.getRightFingerControl().getFingers().get(2).typeValue= getIntData(mRightFinger2) ;
            recordArmAngle.getRightFingerControl().getFingers().get(3).typeValue= getIntData(mRightFinger3) ;
            recordArmAngle.getRightFingerControl().getFingers().get(4).typeValue= getIntData(mRightFinger4) ;

            if (mOnChangedListener != null) {

                mOnChangedListener.onChanged(recordArmAngle, mChangedActionSth.isChecked());
            }
        }

        dismiss();
    }


    private OnChangedListener mOnChangedListener;
    private RecordArmAngle recordArmAngle;

    public void setRecordArmAngle(RecordArmAngle recordArmAngle) {

        this.recordArmAngle = recordArmAngle;

        if (mTimeEtt != null && mDelayEtt != null) {

            this.mChangedActionSth.setChecked(false);
            this.mTimeEtt.setText(String.valueOf(recordArmAngle.getTime()));
            this.mDelayEtt.setText(String.valueOf(recordArmAngle.getDelay()));


            this.mLeftFinger0.setText(String.valueOf(recordArmAngle.getLeftFingerControl().getFingers().get(0).typeValue));
            this.mLeftFinger1.setText(String.valueOf(recordArmAngle.getLeftFingerControl().getFingers().get(1).typeValue));
            this.mLeftFinger2.setText(String.valueOf(recordArmAngle.getLeftFingerControl().getFingers().get(2).typeValue));
            this.mLeftFinger3.setText(String.valueOf(recordArmAngle.getLeftFingerControl().getFingers().get(3).typeValue));
            this.mLeftFinger4.setText(String.valueOf(recordArmAngle.getLeftFingerControl().getFingers().get(4).typeValue));

            this.mRightFinger0.setText(String.valueOf(recordArmAngle.getRightFingerControl().getFingers().get(0).typeValue));
            this.mRightFinger1.setText(String.valueOf(recordArmAngle.getRightFingerControl().getFingers().get(1).typeValue));
            this.mRightFinger2.setText(String.valueOf(recordArmAngle.getRightFingerControl().getFingers().get(2).typeValue));
            this.mRightFinger3.setText(String.valueOf(recordArmAngle.getRightFingerControl().getFingers().get(3).typeValue));
            this.mRightFinger4.setText(String.valueOf(recordArmAngle.getRightFingerControl().getFingers().get(4).typeValue));


        }
    }


    public interface OnChangedListener {

        void onChanged(RecordArmAngle recordArmAngle, boolean isChangedAction);
    }

    public void setOnChangedListener(OnChangedListener onChangedListener) {
        this.mOnChangedListener = onChangedListener;
    }

}
