package com.yongyida.robot.hardware.test.item.motion.fragment;


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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.yongyida.robot.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.ArmControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.FingerControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.HandControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.constant.Direction;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.motion.adapter.NameAdapter;
import com.yongyida.robot.hardware.test.view.HorizontalListView;

/**
 * Create By HuangXiangXiang 2018/6/12
 */
public class TestHandFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    private HandControl mHandControl = new HandControl();
    /**
     * 执行
     */
    private Button mExecuteBtn;
    private HorizontalListView mActionHlv;
    private RadioGroup mDirectionRgp;
    /**
     * 是否发送
     */
    private Switch mArmSih;
    private HorizontalListView mArmActionHlv;
    /**
     * 是否发送
     */
    private Switch mFingerSih;
    private HorizontalListView mFingerActionHlv;
    private LinearLayout mCustomLlt;


    private String[] actions = {"自定义","初始化","握拳","手指轮动","握手","OK",
    "点赞","石头", "剪刀","布","迎宾","挥手", "示爱","666"} ;

    private String[] armActions = {"初始化"} ;

    private String[] fingerActions = {"初始化","握拳","点赞","OK","石头", "剪刀","布","握手", "示爱","手指轮动"} ;

    private NameAdapter mActionAdapter ;
    private NameAdapter mArmActionAdapter ;
    private NameAdapter mFingerActionAdapter ;



    @Override
    public String getName() {

        return "手臂控制";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test_hand, null);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mExecuteBtn = (Button) view.findViewById(R.id.execute_btn);
        mExecuteBtn.setOnClickListener(this);
        mActionHlv = (HorizontalListView) view.findViewById(R.id.action_hlv);
        mDirectionRgp = (RadioGroup) view.findViewById(R.id.direction_rgp);
        mArmSih = (Switch) view.findViewById(R.id.arm_sih);
        mArmActionHlv = (HorizontalListView) view.findViewById(R.id.arm_action_hlv);
        mFingerSih = (Switch) view.findViewById(R.id.finger_sih);
        mFingerActionHlv = (HorizontalListView) view.findViewById(R.id.finger_action_hlv);
        mCustomLlt = (LinearLayout) view.findViewById(R.id.custom_llt);


        mDirectionRgp.setOnCheckedChangeListener(this);

        mActionAdapter = new NameAdapter(getActivity(), actions) ;
        mActionHlv.setAdapter(mActionAdapter);
        mActionHlv.setOnItemClickListener(this);

        mArmActionAdapter = new NameAdapter(getActivity(), armActions) ;
        mArmActionHlv.setAdapter(mArmActionAdapter);
        mArmActionHlv.setOnItemClickListener(this);

        mFingerActionAdapter = new NameAdapter(getActivity(), fingerActions) ;
        mFingerActionHlv.setAdapter(mFingerActionAdapter);
        mFingerActionHlv.setOnItemClickListener(this);

        mArmSih.setOnCheckedChangeListener(this);
        mFingerSih.setOnCheckedChangeListener(this);

        mDirectionRgp.check(R.id.direction_same_rbn);
        mArmActionHlv.setSelection(0);
        mArmSih.setChecked(true);
        mFingerSih.setChecked(true);
        mArmActionHlv.setSelection(0);
        mFingerActionHlv.setSelection(0);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.execute_btn) {

            SendClient.getInstance(getActivity()).send(null,mHandControl, null);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(parent == mActionHlv){

            mHandControl.setAction(HandControl.Action.values()[position]);

            mActionAdapter.setSelectIndex(position);

            if(position == 0){

                mCustomLlt.setVisibility(View.VISIBLE);

            }else{

                mCustomLlt.setVisibility(View.GONE);
            }


        }else if(parent == mArmActionHlv){

            mHandControl.getArmControl().setAction(ArmControl.Action.values()[position+1]);

            mArmActionAdapter.setSelectIndex(position);

        } else if (parent == mFingerActionHlv) {

            mHandControl.getFingerControl().setAction(FingerControl.Action.values()[position+2]);
            mFingerActionAdapter.setSelectIndex(position);
        }


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if(group == mDirectionRgp){

            if(checkedId == R.id.direction_left_rbn){

                mHandControl.setDirection(Direction.LEFT);

            }else if(checkedId == R.id.direction_right_rbn){

                mHandControl.setDirection(Direction.RIGHT);

            }else if(checkedId == R.id.direction_same_rbn){

                mHandControl.setDirection(Direction.SAME);
            }
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(buttonView == mArmSih) {

            mHandControl.getArmControl().setControl(isChecked);

            if(isChecked){

                mArmActionHlv.setVisibility(View.VISIBLE);
            }else {

                mArmActionHlv.setVisibility(View.GONE);
            }


        }else if(buttonView == mFingerSih){

            mHandControl.getFingerControl().setControl(isChecked);

            if(isChecked){

                mFingerActionHlv.setVisibility(View.VISIBLE);
            }else {

                mFingerActionHlv.setVisibility(View.GONE);
            }

        }

    }
}
