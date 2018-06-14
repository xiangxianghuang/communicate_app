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
import android.widget.SeekBar;
import android.widget.TextView;

import com.hiva.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.SteeringControl;
import com.yongyida.robot.hardware.test.R;

/**
 * Create By HuangXiangXiang 2018/6/12
 */
public class TestHeadFragment extends BaseFragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private SteeringControl mHeadLeftRight = new SteeringControl(SteeringControl.Position.HEAD_LEFT_RIGHT);
    private SteeringControl mHeadUpDown = new SteeringControl(SteeringControl.Position.HEAD_UP_DOWN);
    private View view;
    private SeekBar mHeadLeftRightSpeedSbr;
    /**
     * 50
     */
    private TextView mHeadLeftRightSpeedTvw;
    /**
     * 右到头
     */
    private Button mHeadRightEndBtn;
    /**
     * 右一点
     */
    private Button mHeadRightOneBtn;
    /**
     * 归中
     */
    private Button mHeadLeftRightResetBtn;
    /**
     * 左一点
     */
    private Button mHeadLeftOneBtn;
    /**
     * 左到头
     */
    private Button mHeadLeftEndBtn;
    /**
     * 循环
     */
    private Button mHeadLeftRightLoopBtn;
    /**
     * 停止
     */
    private Button mHeadLeftRightStopBtn;
    private SeekBar mHeadUpDownSpeedSbr;
    /**
     * 50
     */
    private TextView mHeadUpDownSpeedTvw;
    /**
     * 上到头
     */
    private Button mHeadUpEndBtn;
    /**
     * 上一点
     */
    private Button mHeadUpOneBtn;
    /**
     * 归中
     */
    private Button mHeadUpDownResetBtn;
    /**
     * 下一点
     */
    private Button mHeadDownOneBtn;
    /**
     * 下到头
     */
    private Button mHeadDownEndBtn;
    /**
     * 循环
     */
    private Button mHeadUpDownLoopBtn;
    /**
     * 停止
     */
    private Button mHeadUpDownStopBtn;

    @Override
    public String getName() {
        return "头部控制运动";
    }


    private void initSteeringControl(SteeringControl control){

        control.getSpeed().setUnit(SteeringControl.Speed.Unit.PERCENT);
        control.getDistance().setUnit(SteeringControl.Distance.Unit.PERCENT);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        initSteeringControl(mHeadLeftRight) ;
        initSteeringControl(mHeadUpDown);

        View view = inflater.inflate(R.layout.fragment_test_head, null);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mHeadLeftRightSpeedSbr = (SeekBar) view.findViewById(R.id.head_left_right_speed_sbr);
        mHeadLeftRightSpeedTvw = (TextView) view.findViewById(R.id.head_left_right_speed_tvw);
        mHeadRightEndBtn = (Button) view.findViewById(R.id.head_right_end_btn);
        mHeadRightEndBtn.setOnClickListener(this);
        mHeadRightOneBtn = (Button) view.findViewById(R.id.head_right_one_btn);
        mHeadRightOneBtn.setOnClickListener(this);
        mHeadLeftRightResetBtn = (Button) view.findViewById(R.id.head_left_right_reset_btn);
        mHeadLeftRightResetBtn.setOnClickListener(this);
        mHeadLeftOneBtn = (Button) view.findViewById(R.id.head_left_one_btn);
        mHeadLeftOneBtn.setOnClickListener(this);
        mHeadLeftEndBtn = (Button) view.findViewById(R.id.head_left_end_btn);
        mHeadLeftEndBtn.setOnClickListener(this);
        mHeadLeftRightLoopBtn = (Button) view.findViewById(R.id.head_left_right_loop_btn);
        mHeadLeftRightLoopBtn.setOnClickListener(this);
        mHeadLeftRightStopBtn = (Button) view.findViewById(R.id.head_left_right_stop_btn);
        mHeadLeftRightStopBtn.setOnClickListener(this);
        mHeadUpDownSpeedSbr = (SeekBar) view.findViewById(R.id.head_up_down_speed_sbr);
        mHeadUpDownSpeedTvw = (TextView) view.findViewById(R.id.head_up_down_speed_tvw);
        mHeadUpEndBtn = (Button) view.findViewById(R.id.head_up_end_btn);
        mHeadUpEndBtn.setOnClickListener(this);
        mHeadUpOneBtn = (Button) view.findViewById(R.id.head_up_one_btn);
        mHeadUpOneBtn.setOnClickListener(this);
        mHeadUpDownResetBtn = (Button) view.findViewById(R.id.head_up_down_reset_btn);
        mHeadUpDownResetBtn.setOnClickListener(this);
        mHeadDownOneBtn = (Button) view.findViewById(R.id.head_down_one_btn);
        mHeadDownOneBtn.setOnClickListener(this);
        mHeadDownEndBtn = (Button) view.findViewById(R.id.head_down_end_btn);
        mHeadDownEndBtn.setOnClickListener(this);
        mHeadUpDownLoopBtn = (Button) view.findViewById(R.id.head_up_down_loop_btn);
        mHeadUpDownLoopBtn.setOnClickListener(this);
        mHeadUpDownStopBtn = (Button) view.findViewById(R.id.head_up_down_stop_btn);
        mHeadUpDownStopBtn.setOnClickListener(this);


        mHeadLeftRightSpeedSbr.setOnSeekBarChangeListener(this);
        mHeadUpDownSpeedSbr.setOnSeekBarChangeListener(this);

        mHeadLeftRightSpeedSbr.setProgress(50);
        mHeadUpDownSpeedSbr.setProgress(50);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.head_right_end_btn) {

            mHeadLeftRight.setMode(SteeringControl.Mode.DISTANCE_SPEED);
            mHeadLeftRight.getDistance().setType(SteeringControl.Distance.Type.TO);
            mHeadLeftRight.getDistance().setValue(100);

            SendClient.getInstance(getActivity()).send(null, mHeadLeftRight, null );

        } else if (i == R.id.head_right_one_btn) {

            mHeadLeftRight.setMode(SteeringControl.Mode.DISTANCE_SPEED);
            mHeadLeftRight.getDistance().setType(SteeringControl.Distance.Type.BY);
            mHeadLeftRight.getDistance().setValue(10);

            SendClient.getInstance(getActivity()).send(null, mHeadLeftRight, null );


        } else if (i == R.id.head_left_right_reset_btn) {

            mHeadLeftRight.setMode(SteeringControl.Mode.RESET);

            SendClient.getInstance(getActivity()).send(null, mHeadLeftRight, null );

        } else if (i == R.id.head_left_one_btn) {

            mHeadLeftRight.setMode(SteeringControl.Mode.DISTANCE_SPEED);
            mHeadLeftRight.getDistance().setType(SteeringControl.Distance.Type.BY);
            mHeadLeftRight.getDistance().setValue(-10);

            SendClient.getInstance(getActivity()).send(null, mHeadLeftRight, null );

        } else if (i == R.id.head_left_end_btn) {

            mHeadLeftRight.setMode(SteeringControl.Mode.DISTANCE_SPEED);
            mHeadLeftRight.getDistance().setType(SteeringControl.Distance.Type.TO);
            mHeadLeftRight.getDistance().setValue(-100);

            SendClient.getInstance(getActivity()).send(null, mHeadLeftRight, null );

        } else if (i == R.id.head_left_right_loop_btn) {


            mHeadLeftRight.setMode(SteeringControl.Mode.LOOP);

            SendClient.getInstance(getActivity()).send(null, mHeadLeftRight, null );


        } else if (i == R.id.head_left_right_stop_btn) {

            mHeadLeftRight.setMode(SteeringControl.Mode.STOP);

            SendClient.getInstance(getActivity()).send(null, mHeadLeftRight, null );

        } else if (i == R.id.head_up_end_btn) {

            mHeadUpDown.setMode(SteeringControl.Mode.DISTANCE_SPEED);
            mHeadUpDown.getDistance().setType(SteeringControl.Distance.Type.TO);
            mHeadUpDown.getDistance().setValue(-100);

            SendClient.getInstance(getActivity()).send(null, mHeadUpDown, null );

        } else if (i == R.id.head_up_one_btn) {

            mHeadUpDown.setMode(SteeringControl.Mode.DISTANCE_SPEED);
            mHeadUpDown.getDistance().setType(SteeringControl.Distance.Type.BY);
            mHeadUpDown.getDistance().setValue(-10);

            SendClient.getInstance(getActivity()).send(null, mHeadUpDown, null );

        } else if (i == R.id.head_up_down_reset_btn) {

            mHeadUpDown.setMode(SteeringControl.Mode.RESET);

            SendClient.getInstance(getActivity()).send(null, mHeadUpDown, null );

        } else if (i == R.id.head_down_one_btn) {

            mHeadUpDown.setMode(SteeringControl.Mode.DISTANCE_SPEED);
            mHeadUpDown.getDistance().setType(SteeringControl.Distance.Type.BY);
            mHeadUpDown.getDistance().setValue(10);

            SendClient.getInstance(getActivity()).send(null, mHeadUpDown, null );


        } else if (i == R.id.head_down_end_btn) {

            mHeadUpDown.setMode(SteeringControl.Mode.DISTANCE_SPEED);
            mHeadUpDown.getDistance().setType(SteeringControl.Distance.Type.TO);
            mHeadUpDown.getDistance().setValue(100);

            SendClient.getInstance(getActivity()).send(null, mHeadUpDown, null );

        } else if (i == R.id.head_up_down_loop_btn) {

            mHeadUpDown.setMode(SteeringControl.Mode.LOOP);

            SendClient.getInstance(getActivity()).send(null, mHeadUpDown, null );


        } else if (i == R.id.head_up_down_stop_btn) {

            mHeadUpDown.setMode(SteeringControl.Mode.STOP);

            SendClient.getInstance(getActivity()).send(null, mHeadUpDown, null );

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if(seekBar == mHeadLeftRightSpeedSbr){

            int value = progress + 1 ;

            mHeadLeftRightSpeedTvw.setText(String.valueOf(value));
            mHeadLeftRight.getSpeed().setValue(value);

        }else if(seekBar == mHeadUpDownSpeedSbr){

            int value = progress + 1 ;

            mHeadUpDownSpeedTvw.setText(String.valueOf(value));
            mHeadUpDown.getSpeed().setValue(value);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
