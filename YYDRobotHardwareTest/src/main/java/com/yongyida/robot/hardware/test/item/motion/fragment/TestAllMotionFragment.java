package com.yongyida.robot.hardware.test.item.motion.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.yongyida.robot.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.SteeringControl;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.motion.adapter.NameAdapter;
import com.yongyida.robot.hardware.test.view.HorizontalListView;



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
 * Create By HuangXiangXiang 2018/6/12
 * 全部舵机控制
 */
public class TestAllMotionFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private SteeringControl mSteeringControl = new SteeringControl(null);
    private View view;
    /**
     * 执行
     */
    private Button mExecuteBtn;
    private HorizontalListView mPositionHlv;
    private HorizontalListView mModeHlv;
    private HorizontalListView mDistanceUnitHlv;
    private HorizontalListView mDistanceTypeHlv;
    private EditText mDistanceValueEtt;
    private LinearLayout mDistanceLlt;
    private HorizontalListView mTimeUnitHlv;
    private EditText mTimeValueEtt;
    private HorizontalListView mSpeedUnitHlv;
    private EditText mSpeedValueEtt;
    /**
     * 是否反向转动
     */
    private Switch mIsNegativeSth;
    /**
     * 0
     */
    private EditText mDelayEtt;


    private final String[] positions = {"头左右", "头上下", "左臂0", "左臂1", "左臂2", "左臂3", "左臂4", "左臂5",
            "右臂0", "右臂1", "右臂2", "右臂3", "右臂4", "右臂5","左指0", "左指1", "左指2", "左指3", "左指4",
            "右指0", "右指1", "右指2", "右指3", "右指4", "左腿", "右腿",
    } ;

    private final String[] modes = {"停止", "归中", "循环", "距离 时间", "距离 速度", "时间 速度"} ;

    private final String[] distanceUnits = {"真实值","百分比", "毫米", "厘米", "角度值"} ;
    private final String[] distanceTypes = {"偏移量", "目标值"} ;

    private final String[] timeUnits = {"毫秒", "秒"} ;

    private final String[] speedUnits = {"真实值", "百分比", "根据距离的单位"} ;

    @Override
    public String getName() {

        return "全部舵机控制";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test_all_motion, null);

        initView(view);
        return view;
    }

    private void initView(View view) {

        mExecuteBtn = (Button) view.findViewById(R.id.execute_btn);
        mExecuteBtn.setOnClickListener(this);
        mPositionHlv = (HorizontalListView) view.findViewById(R.id.position_hlv);
        mModeHlv = (HorizontalListView) view.findViewById(R.id.mode_hlv);
        mDistanceUnitHlv = (HorizontalListView) view.findViewById(R.id.distance_unit_hlv);
        mDistanceTypeHlv = (HorizontalListView) view.findViewById(R.id.distance_type_hlv);
        mDistanceValueEtt = (EditText) view.findViewById(R.id.distance_value_ett);
        mDistanceLlt = (LinearLayout) view.findViewById(R.id.distance_llt);
        mTimeUnitHlv = (HorizontalListView) view.findViewById(R.id.time_unit_hlv);
        mTimeValueEtt = (EditText) view.findViewById(R.id.time_value_ett);
        mSpeedUnitHlv = (HorizontalListView) view.findViewById(R.id.speed_unit_hlv);
        mSpeedValueEtt = (EditText) view.findViewById(R.id.speed_value_ett);
        mIsNegativeSth = (Switch) view.findViewById(R.id.is_negative_sth);
        mDelayEtt = (EditText) view.findViewById(R.id.delay_ett);


        positionAdapter = new NameAdapter(getActivity(),positions) ;
        mPositionHlv.setAdapter(positionAdapter);
        mPositionHlv.setOnItemClickListener(this);

        modeAdapter = new NameAdapter(getActivity(),modes) ;
        mModeHlv.setAdapter(modeAdapter);
        mModeHlv.setOnItemClickListener(this);

        distanceUnitAdapter = new NameAdapter(getActivity(),distanceUnits) ;
        mDistanceUnitHlv.setAdapter(distanceUnitAdapter);
        mDistanceUnitHlv.setOnItemClickListener(this);

        distanceTypeAdapter = new NameAdapter(getActivity(),distanceTypes) ;
        mDistanceTypeHlv.setAdapter(distanceTypeAdapter);
        mDistanceTypeHlv.setOnItemClickListener(this);


        timeUnitAdapter = new NameAdapter(getActivity(),timeUnits) ;
        mTimeUnitHlv.setAdapter(timeUnitAdapter);
        mTimeUnitHlv.setOnItemClickListener(this);

        speedUnitAdapter = new NameAdapter(getActivity(),speedUnits) ;
        mSpeedUnitHlv.setAdapter(speedUnitAdapter);
        mSpeedUnitHlv.setOnItemClickListener(this);

    }

    private NameAdapter positionAdapter ;
    private NameAdapter modeAdapter ;
    private NameAdapter distanceUnitAdapter ;
    private NameAdapter distanceTypeAdapter ;
    private NameAdapter timeUnitAdapter ;
    private NameAdapter speedUnitAdapter ;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(parent == mPositionHlv){

            positionAdapter.setSelectIndex(position);
            mSteeringControl.setPosition(SteeringControl.Position.values()[position]);

        }else if(parent == mModeHlv){

            modeAdapter.setSelectIndex(position);
            mSteeringControl.setMode(SteeringControl.Mode.values()[position]);

        }else if(parent == mDistanceUnitHlv){

            distanceUnitAdapter.setSelectIndex(position);
            mSteeringControl.getDistance().setUnit(SteeringControl.Distance.Unit.values()[position]);

        }else if(parent == mDistanceTypeHlv){

            distanceTypeAdapter.setSelectIndex(position);
            mSteeringControl.getDistance().setType(SteeringControl.Distance.Type.values()[position]);


        }else if(parent == mTimeUnitHlv){

            timeUnitAdapter.setSelectIndex(position);
            mSteeringControl.getTime().setUnit(SteeringControl.Time.Unit.values()[position]);

        }else if(parent == mSpeedUnitHlv){

            speedUnitAdapter.setSelectIndex(position);
            mSteeringControl.getSpeed().setUnit(SteeringControl.Speed.Unit.values()[position]);
        }

    }


    ;


    @Override
    public void onClick(View v) {

        if(v == mExecuteBtn){

            int distanceValue = getIntValue(mDistanceValueEtt) ;
            int timeValue = getIntValue(mTimeValueEtt) ;
            int speedValue = getIntValue(mSpeedValueEtt) ;
            int delayValue = getIntValue(mDelayEtt) ;

            mSteeringControl.getDistance().setValue(distanceValue);
            mSteeringControl.getTime().setValue(timeValue);
            mSteeringControl.getSpeed().setValue(speedValue);

            mSteeringControl.setNegative(mIsNegativeSth.isChecked());
            mSteeringControl.setDelay(delayValue);

            SendClient.getInstance(getActivity()).send(null,mSteeringControl,null );
        }
    }

    public int getIntValue(EditText editText){

        int value ;

        String string = editText.getText().toString() ;
        try {

            value = Integer.parseInt(string) ;
        }catch (Exception e){

            value = 0 ;
            editText.setText(String.valueOf(0));
        }

        return value ;
    }

}
