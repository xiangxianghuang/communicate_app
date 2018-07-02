package com.yongyida.robot.hardware.test.item.motion.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.yongyida.robot.communicate.app.hardware.motion.send.data.ArmControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.FingerControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.FootControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.HandControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.HeadControl;
import com.yongyida.robot.hardware.test.item.motion.bean.OneFrameScript;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.SteeringControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.constant.Direction;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.motion.adapter.EditAngleAdapter;
import com.yongyida.robot.hardware.test.item.motion.adapter.NameAdapter;
import com.yongyida.robot.hardware.test.view.HorizontalListView;

import java.util.ArrayList;



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
 * Create By HuangXiangXiang 2018/6/28
 */
public class EditOneFrameScriptDialog extends Dialog implements CompoundButton.OnCheckedChangeListener, TextWatcher, AdapterView.OnItemClickListener, SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    public EditOneFrameScriptDialog(@NonNull Context context) {
        super(context);
    }

    private CheckBox mHeadLeftRightCbx;
    private SeekBar mHeadLeftRightSbr;
    private TextView mHeadLeftRightValueTvw;
    private EditText mHeadLeftRightUsedTimeEtt;
    private CheckBox mHeadUpDownCbx;
    private SeekBar mHeadUpDownSbr;
    private TextView mHeadUpDownValueTvw;
    private EditText mHeadUpDownUsedTimeEtt;
    private CheckBox mFootCbx;
    private RadioGroup mFootRgp ;
    private RadioButton mFootForwardRbn;
    private RadioButton mFootBackRbn;
    private RadioButton mFootLeftRbn;
    private RadioButton mFootRightRbn;
    private RadioButton mFootStopRbn;
    private EditText mFootUsedTimeEtt;
    private CheckBox mHandCbx;
    private LinearLayout mHandLlt;
    private RadioButton mDirectionLeftRbn;
    private RadioButton mDirectionRightRbn;
    private RadioButton mDirectionSameRbn;
    private RadioButton mDirectionBothRbn;
    private RadioGroup mDirectionRgp;
    private HorizontalListView mHandActionHlv;
    private Switch mArmSih;
    private HorizontalListView mArmActionHlv;
    private ListView mArmLvw;
    private Switch mFingerSih;
    private HorizontalListView mFingerActionHlv;
    private ListView mFingerLvw;
    private LinearLayout mCustomLlt;
    private EditText mNextScriptTimeEtt;
    private Button mCancelBtn;
    private Button mExecuteBtn;
    private Button mOkBtn;

    private HandControl.Action[] mHandActions = HandControl.Action.values() ;
    private ArmControl.Action[] mArmActions = ArmControl.Action.values() ;
    private FingerControl.Action[] mFingerActions = FingerControl.Action.values() ;


    private String[] mHandActionNames = {"自定义","初始化","握拳","手指轮动","握手","OK","点赞","石头", "剪刀","布","迎宾","挥手", "示爱","666"} ;
    private String[] mArmActionNames = {"自定义","初始化"} ;
    private String[] mFingerActionNames = {"自定义","停止","初始化","握拳","点赞","OK","石头", "剪刀","布","握手", "示爱","手指轮动"} ;

    private NameAdapter mHandActionAdapter ;
    private NameAdapter mArmActionAdapter ;
    private NameAdapter mFingerActionAdapter ;


    private EditAngleAdapter mArmEditAngleAdapter ;
    private EditAngleAdapter mFingerEditAngleAdapter ;

    private OneFrameScript mOneFrameScript ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_edit_one_frame_script);

        this.mHeadLeftRightCbx = (CheckBox) findViewById(R.id.head_left_right_cbx);
        this.mHeadLeftRightSbr = (SeekBar) findViewById(R.id.head_left_right_sbr);
        this.mHeadLeftRightValueTvw = (TextView) findViewById(R.id.head_left_right_value_tvw);
        this.mHeadLeftRightUsedTimeEtt = (EditText) findViewById(R.id.head_left_right_used_time_ett);
        this.mHeadUpDownCbx = (CheckBox) findViewById(R.id.head_up_down_cbx);
        this.mHeadUpDownSbr = (SeekBar) findViewById(R.id.head_up_down_sbr);
        this.mHeadUpDownValueTvw = (TextView) findViewById(R.id.head_up_down_value_tvw);
        this.mHeadUpDownUsedTimeEtt = (EditText) findViewById(R.id.head_up_down_used_time_ett);
        this.mFootCbx = (CheckBox) findViewById(R.id.foot_cbx);
        this.mFootRgp = (RadioGroup) findViewById(R.id.foot_rgp);
        this.mFootForwardRbn = (RadioButton) findViewById(R.id.foot_forward_rbn);
        this.mFootBackRbn = (RadioButton) findViewById(R.id.foot_back_rbn);
        this.mFootLeftRbn = (RadioButton) findViewById(R.id.foot_left_rbn);
        this.mFootRightRbn = (RadioButton) findViewById(R.id.foot_right_rbn);
        this.mFootStopRbn = (RadioButton) findViewById(R.id.foot_stop_rbn);
        this.mFootUsedTimeEtt = (EditText) findViewById(R.id.foot_used_time_ett);
        this.mHandCbx = (CheckBox) findViewById(R.id.hand_cbx);
        this.mHandLlt = (LinearLayout) findViewById(R.id.hand_llt);
        this.mDirectionLeftRbn = (RadioButton) findViewById(R.id.direction_left_rbn);
        this.mDirectionRightRbn = (RadioButton) findViewById(R.id.direction_right_rbn);
        this.mDirectionSameRbn = (RadioButton) findViewById(R.id.direction_same_rbn);
        this.mDirectionBothRbn = (RadioButton) findViewById(R.id.direction_both_rbn);
        this.mDirectionRgp = (RadioGroup) findViewById(R.id.direction_rgp);
        this.mHandActionHlv = (HorizontalListView) findViewById(R.id.hand_action_hlv);
        this.mArmSih = (Switch) findViewById(R.id.arm_sih);
        this.mArmActionHlv = (HorizontalListView) findViewById(R.id.arm_action_hlv);
        this.mArmLvw = (ListView) findViewById(R.id.arm_lvw);
        this.mFingerSih = (Switch) findViewById(R.id.finger_sih);
        this.mFingerActionHlv = (HorizontalListView) findViewById(R.id.finger_action_hlv);
        this.mFingerLvw = (ListView) findViewById(R.id.finger_lvw);
        this.mCustomLlt = (LinearLayout) findViewById(R.id.custom_llt);
        this.mNextScriptTimeEtt = (EditText) findViewById(R.id.next_script_time_ett);
        this.mCancelBtn = (Button) findViewById(R.id.cancel_btn);
        this.mExecuteBtn = (Button) findViewById(R.id.execute_btn);
        this.mOkBtn = (Button) findViewById(R.id.ok_btn);

        this.mHeadLeftRightCbx.setOnCheckedChangeListener(this);
        this.mHeadUpDownCbx.setOnCheckedChangeListener(this);
        this.mFootCbx.setOnCheckedChangeListener(this);
        this.mHandCbx.setOnCheckedChangeListener(this);
        this.mArmSih.setOnCheckedChangeListener(this);
        this.mFingerSih.setOnCheckedChangeListener(this);

        this.mHeadLeftRightSbr.setOnSeekBarChangeListener(this);
        this.mHeadUpDownSbr.setOnSeekBarChangeListener(this);

        this.mFootRgp.setOnCheckedChangeListener(this);
        this.mDirectionRgp.setOnCheckedChangeListener(this);

        this.mHandActionAdapter = new NameAdapter(getContext(),mHandActionNames) ;
        this.mHandActionHlv.setAdapter(mHandActionAdapter);
        this.mArmActionAdapter = new NameAdapter(getContext(), mArmActionNames) ;
        this.mArmActionHlv.setAdapter(mArmActionAdapter);
        this.mFingerActionAdapter = new NameAdapter(getContext(), mFingerActionNames) ;
        this.mFingerActionHlv.setAdapter(mFingerActionAdapter);

        this.mHandActionHlv.setOnItemClickListener(this);
        this.mArmActionHlv.setOnItemClickListener(this);
        this.mFingerActionHlv.setOnItemClickListener(this);

        this.mArmEditAngleAdapter = new EditAngleAdapter(getContext()) ;
        this.mArmLvw.setAdapter(mArmEditAngleAdapter);

        this.mFingerEditAngleAdapter = new EditAngleAdapter(getContext()) ;
        this.mFingerLvw.setAdapter(mFingerEditAngleAdapter);

        this.mHeadLeftRightUsedTimeEtt.addTextChangedListener(this);
        this.mHeadUpDownUsedTimeEtt.addTextChangedListener(this);
        this.mFootUsedTimeEtt.addTextChangedListener(this);
        this.mNextScriptTimeEtt.addTextChangedListener(this);

        this.mCancelBtn.setOnClickListener(this);
        this.mExecuteBtn.setOnClickListener(this);
        this.mOkBtn.setOnClickListener(this);


        refreshUI() ;
    }

    public void setOneFrameScript(OneFrameScript oneFrameScript) {
        this.mOneFrameScript = oneFrameScript;

        refreshUI() ;
    }

    /**刷新*/
    private void refreshUI(){

        if(mOneFrameScript != null && mHeadLeftRightCbx != null){

            HeadControl headControl = mOneFrameScript.getHeadControl() ;

            // 头部左右
            SteeringControl headLeftRight = headControl.getHeadLeftRightControl() ;
            mHeadLeftRightCbx.setChecked(headLeftRight.isControl());
            SteeringControl.Distance distance = headLeftRight.getDistance() ;
            mHeadLeftRightSbr.setProgress(distance.getValue());
            SteeringControl.Time time = headLeftRight.getTime() ;
            mHeadLeftRightUsedTimeEtt.setText(String.valueOf(time.getValue()));

            // 头部上下
            SteeringControl headUpDown = headControl.getHeadUpDownControl() ;
            mHeadUpDownCbx.setChecked(headUpDown.isControl());
            distance = headUpDown.getDistance() ;
            mHeadUpDownSbr.setProgress(distance.getValue());
            time = headUpDown.getTime() ;
            mHeadUpDownUsedTimeEtt.setText(String.valueOf(time.getValue()));

            // 足
            FootControl footControl = mOneFrameScript.getFootControl() ;
            mFootCbx.setChecked(footControl.isControl());
            SteeringControl foot = footControl.getFoot() ;
            FootControl.Action action = footControl.getAction() ;
            switch (action){
                case FORWARD:
                    mFootRgp.check(R.id.foot_forward_rbn);
                    break;
                case BACK:
                    mFootRgp.check(R.id.foot_back_rbn);
                    break;
                case LEFT:
                    mFootRgp.check(R.id.foot_left_rbn);
                    break;
                case RIGHT:
                    mFootRgp.check(R.id.foot_right_rbn);
                    break;
                case STOP:
                    mFootRgp.check(R.id.foot_stop_rbn);
                    break;
            }
            time = foot.getTime() ;
            mFootUsedTimeEtt.setText(String.valueOf(time.getValue()));


            // 手
            HandControl handControl = mOneFrameScript.getHandControl() ;
            mHandCbx.setChecked(handControl.isControl());
            Direction direction = handControl.getDirection() ;
            switch (direction){
                case LEFT:
                    mDirectionRgp.check(R.id.direction_left_rbn);
                    break;
                case RIGHT:
                    mDirectionRgp.check(R.id.direction_right_rbn);
                    break;
                case SAME:
                    mDirectionRgp.check(R.id.direction_same_rbn);
                    break;
                case BOTH:
                    mDirectionRgp.check(R.id.direction_both_rbn);
                    break;
            }
            mArmEditAngleAdapter.setDirection(direction);
            mFingerEditAngleAdapter.setDirection(direction);


            HandControl.Action handAction = handControl.getAction() ;
            int index = 0 ;
            final int length = mHandActions.length ;
            for (int i = 0 ; i < length ; i ++){

                if(mHandActions[i] == handAction){

                    index = i ;
                    break;
                }
            }
            mHandActionAdapter.setSelectIndex(index);

            // 手臂
            ArmControl armControl = handControl.getArmControl() ;
            mArmSih.setChecked(armControl.isControl());

            mArmEditAngleAdapter.setSteeringControls(armControl.getArmLefts(),armControl.getArmRights());

            // 手指
            FingerControl fingerControl = handControl.getFingerControl() ;
            mFingerSih.setChecked(fingerControl.isControl());

            mFingerEditAngleAdapter.setSteeringControls(fingerControl.getFingerLefts(),fingerControl.getFingerRights());

            mNextScriptTimeEtt.setText(String.valueOf(mOneFrameScript.getNextScriptTime()));
        }
    }

    @Override
    public void show() {

        super.show();

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(parent == mHandActionHlv){

            if(position == 0){

                mCustomLlt.setVisibility(View.VISIBLE);

            }else {

                mCustomLlt.setVisibility(View.GONE);
            }


            mHandActionAdapter.setSelectIndex(position);
            mOneFrameScript.getHandControl().setAction(mHandActions[position]);

        }else if(parent == mArmActionHlv){

            if(position == 0){

                mArmLvw.setVisibility(View.VISIBLE);

            }else {

                mArmLvw.setVisibility(View.GONE);
            }

            mArmActionAdapter.setSelectIndex(position);
            mOneFrameScript.getHandControl().getArmControl().setAction(mArmActions[position]);

        }else if(parent == mFingerActionHlv){

            if(position == 0){

                mFingerLvw.setVisibility(View.VISIBLE);

            }else {

                mFingerLvw.setVisibility(View.GONE);
            }

            mFingerActionAdapter.setSelectIndex(position);
            mOneFrameScript.getHandControl().getFingerControl().setAction(mFingerActions[position]);
        }

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(buttonView == mHeadLeftRightCbx){

            mHeadLeftRightSbr.setEnabled(isChecked);
            mHeadLeftRightUsedTimeEtt.setEnabled(isChecked);

            mOneFrameScript.getHeadControl().getHeadLeftRightControl().setControl(isChecked);

        }else if(buttonView == mHeadUpDownCbx){

            mHeadUpDownSbr.setEnabled(isChecked);
            mHeadUpDownUsedTimeEtt.setEnabled(isChecked);

            mOneFrameScript.getHeadControl().getHeadUpDownControl().setControl(isChecked);

        }else if(buttonView == mFootCbx){

            mFootForwardRbn.setEnabled(isChecked);
            mFootBackRbn.setEnabled(isChecked);
            mFootLeftRbn.setEnabled(isChecked);
            mFootRightRbn.setEnabled(isChecked);
            mFootStopRbn.setEnabled(isChecked);

            mFootUsedTimeEtt.setEnabled(isChecked);

            mOneFrameScript.getFootControl().setControl(isChecked);


        }else if(buttonView == mHandCbx){

            if(isChecked){

                mHandLlt.setVisibility(View.VISIBLE);

            }else {

                mHandLlt.setVisibility(View.GONE);
            }

            mOneFrameScript.getHandControl().setControl(isChecked);

        }else if(buttonView == mArmSih){

            if(isChecked){

                this.mArmActionHlv.setVisibility(View.VISIBLE);
                this.mArmLvw.setVisibility(View.VISIBLE);

            }else {

                this.mArmActionHlv.setVisibility(View.GONE);
                this.mArmLvw.setVisibility(View.GONE);

            }

            mOneFrameScript.getHandControl().getArmControl().setControl(isChecked);

        }else if(buttonView == mFingerSih){


            if(isChecked){

                this.mFingerActionHlv.setVisibility(View.VISIBLE);
                this.mFingerLvw.setVisibility(View.VISIBLE);

            }else {

                this.mFingerActionHlv.setVisibility(View.GONE);
                this.mFingerLvw.setVisibility(View.GONE);

            }

            mOneFrameScript.getHandControl().getFingerControl().setControl(isChecked);

        }

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if(mHeadLeftRightSbr == seekBar){

            mHeadLeftRightValueTvw.setText(progress + "%");

            mOneFrameScript.getHeadControl().getHeadLeftRightControl().getDistance().setValue(progress);

        }else if(mHeadUpDownSbr == seekBar) {

            mHeadUpDownValueTvw.setText(progress + "%");

            mOneFrameScript.getHeadControl().getHeadUpDownControl().getDistance().setValue(progress);
        }


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        int value;
        try {

            value = Integer.parseInt(s.toString()) ;
        }catch (Exception e){

            value = 0 ;
        }

        if(s == mHeadUpDownUsedTimeEtt.getText()){

            mOneFrameScript.getHeadControl().getHeadUpDownControl().getTime().setValue(value);


        }else if(s == mHeadLeftRightUsedTimeEtt.getText()){

            mOneFrameScript.getHeadControl().getHeadLeftRightControl().getTime().setValue(value);

        }else if(s == mFootUsedTimeEtt.getText()){

            mOneFrameScript.getFootControl().getFoot().getTime().setValue(value);


        }else if(s == mNextScriptTimeEtt.getText()){

            mOneFrameScript.setNextScriptTime(value);

        }

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if(group == mFootRgp){

            if (checkedId == R.id.foot_forward_rbn) {

                mOneFrameScript.getFootControl().setAction(FootControl.Action.FORWARD);

            }else if(checkedId == R.id.foot_back_rbn){

                mOneFrameScript.getFootControl().setAction(FootControl.Action.BACK);
            }else if(checkedId == R.id.foot_left_rbn){

                mOneFrameScript.getFootControl().setAction(FootControl.Action.LEFT);
            }else if(checkedId == R.id.foot_right_rbn){

                mOneFrameScript.getFootControl().setAction(FootControl.Action.RIGHT);
            }else if(checkedId == R.id.foot_stop_rbn){

                mOneFrameScript.getFootControl().setAction(FootControl.Action.STOP);
            }

        }else if(group == mDirectionRgp){

            Direction direction ;
            if (checkedId == R.id.direction_left_rbn) {

                direction = Direction.LEFT ;
            }else if(checkedId == R.id.direction_right_rbn){

                direction = Direction.RIGHT ;
            }else if(checkedId == R.id.direction_same_rbn){

                direction = Direction.SAME ;
            }else if(checkedId == R.id.direction_both_rbn){

                direction = Direction.BOTH ;
            }else{

                direction = null ;
            }

            mOneFrameScript.getHandControl().setDirection(direction);

            mArmEditAngleAdapter.setDirection(direction);
            mFingerEditAngleAdapter.setDirection(direction);
        }


    }


    @Override
    public void onClick(View v) {

        if(v == mCancelBtn) {

            dismiss();

        }else if(v == mExecuteBtn){

            mOneFrameScript.executeScript(getContext());

        }else if(v == mOkBtn){


            if(mOnDataChangedListener != null){

                mOnDataChangedListener.onDataChanged(mOneFrameScript);
            }

            dismiss();
        }
    }

    private OnDataChangedListener mOnDataChangedListener ;
    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener){

        this.mOnDataChangedListener = onDataChangedListener ;
    }
    public interface OnDataChangedListener{

        void onDataChanged(OneFrameScript oneFrameScript);

    }



}
