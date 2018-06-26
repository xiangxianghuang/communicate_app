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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yongyida.robot.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.FootControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.SteeringControl;
import com.yongyida.robot.hardware.test.R;

/**
 * Create By HuangXiangXiang 2018/6/12
 */
public class TestFootFragment extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    private static final String TAG = TestFootFragment.class.getSimpleName() ;

    private FootControl mFootControl = new FootControl();
    private View view;
    /**
     * 默认
     */
    private RadioButton mTypeNoneRbn;
    /**
     * 串口
     */
    private RadioButton mTypeSerialRbn;
    /**
     * 深蓝
     */
    private RadioButton mTypeSlamRbn;
    private RadioGroup mTypeRgp;
    private SeekBar mFootSpeedSbr;
    /**
     * 50
     */
    private TextView mFootSpeedTvw;
    /**
     * 前进
     */
    private Button mFootForwardBtn;
    /**
     * 后退
     */
    private Button mFootBackBtn;
    /**
     * 左转
     */
    private Button mFootLeftBtn;
    /**
     * 右转
     */
    private Button mFootRightBtn;
    /**
     * 随机
     */
    private Button mFootRandomBtn;
    /**
     * 停止
     */
    private Button mFootStopBtn;

    @Override
    public String getName() {

        return "运行行走";
    }

    private void initSteeringControl(SteeringControl control){

        control.setMode(SteeringControl.Mode.TIME_SPEED);

        control.getTime().setUnit(SteeringControl.Time.Unit.MILLI_SECOND);
        control.getSpeed().setUnit(SteeringControl.Speed.Unit.PERCENT);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        initSteeringControl(mFootControl.getFoot()) ;

        View view = inflater.inflate(R.layout.fragment_test_foot, null);


        initView(view);
        return view;
    }

    private void initView(View view) {
        mTypeNoneRbn = (RadioButton) view.findViewById(R.id.type_none_rbn);
        mTypeSerialRbn = (RadioButton) view.findViewById(R.id.type_serial_rbn);
        mTypeSlamRbn = (RadioButton) view.findViewById(R.id.type_slam_rbn);
        mTypeRgp = (RadioGroup) view.findViewById(R.id.type_rgp);
        mFootSpeedSbr = (SeekBar) view.findViewById(R.id.foot_speed_sbr);
        mFootSpeedTvw = (TextView) view.findViewById(R.id.foot_speed_tvw);
        mFootForwardBtn = (Button) view.findViewById(R.id.foot_forward_btn);
        mFootForwardBtn.setOnClickListener(this);
        mFootBackBtn = (Button) view.findViewById(R.id.foot_back_btn);
        mFootBackBtn.setOnClickListener(this);
        mFootLeftBtn = (Button) view.findViewById(R.id.foot_left_btn);
        mFootLeftBtn.setOnClickListener(this);
        mFootRightBtn = (Button) view.findViewById(R.id.foot_right_btn);
        mFootRightBtn.setOnClickListener(this);
        mFootRandomBtn = (Button) view.findViewById(R.id.foot_random_btn);
        mFootRandomBtn.setOnClickListener(this);
        mFootStopBtn = (Button) view.findViewById(R.id.foot_stop_btn);
        mFootStopBtn.setOnClickListener(this);


        mTypeRgp.setOnCheckedChangeListener(this);

        mFootSpeedSbr.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.foot_forward_btn) {

            mFootControl.setAction(FootControl.Action.FORWARD);
            mFootControl.getFoot().getTime().setValue(2000);

            SendClient.getInstance(getActivity()).send(null, mFootControl,null );

        } else if (i == R.id.foot_back_btn) {

            mFootControl.setAction(FootControl.Action.BACK);
            mFootControl.getFoot().getTime().setValue(2000);

            SendClient.getInstance(getActivity()).send(null, mFootControl,null );

        } else if (i == R.id.foot_left_btn) {

            mFootControl.setAction(FootControl.Action.LEFT);
            mFootControl.getFoot().getTime().setValue(2000);

            SendClient.getInstance(getActivity()).send(null, mFootControl,null );

        } else if (i == R.id.foot_right_btn) {

            mFootControl.setAction(FootControl.Action.RIGHT);
            mFootControl.getFoot().getTime().setValue(2000);

            SendClient.getInstance(getActivity()).send(null, mFootControl,null );

        } else if (i == R.id.foot_random_btn) {



        } else if (i == R.id.foot_stop_btn) {

            mFootControl.setAction(FootControl.Action.STOP);

            SendClient.getInstance(getActivity()).send(null, mFootControl,null );


        } else {
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if(group == mTypeRgp){

            if (checkedId == R.id.type_serial_rbn) {

                mFootControl.setType(FootControl.Type.SERIAL);

            } else if (checkedId == R.id.type_slam_rbn) {

                mFootControl.setType(FootControl.Type.SLAM);

            }else {

                mFootControl.setType(null);
            }


        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if(seekBar == mFootSpeedSbr){

            int value = progress + 1 ;
            mFootSpeedTvw.setText(String.valueOf(value));

            mFootControl.getFoot().getSpeed().setValue(value);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
