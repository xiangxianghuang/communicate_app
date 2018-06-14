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
import android.widget.NumberPicker;

import com.hiva.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.SoundLocationControl;
import com.yongyida.robot.hardware.test.R;

/**
 * Create By HuangXiangXiang 2018/6/13
 */
public class TestSoundLocationFragment extends BaseFragment implements View.OnClickListener {

    private SoundLocationControl mSoundLocationControl = new SoundLocationControl();
    private View view;
    /**
     * 执行
     */
    private Button mExecuteBtn;
    private NumberPicker mAngleNpr;

    @Override
    public String getName() {
        return "声源定位";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test_sound_location, null);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mExecuteBtn = (Button) view.findViewById(R.id.execute_btn);
        mExecuteBtn.setOnClickListener(this);
        mAngleNpr = (NumberPicker) view.findViewById(R.id.angle_npr);

        mAngleNpr.setMinValue(0);
        mAngleNpr.setMaxValue(359);
        mAngleNpr.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                mSoundLocationControl.setAngle(newVal);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.execute_btn) {

            SendClient.getInstance(getActivity()).send(null,mSoundLocationControl, null);

        }
    }
}
