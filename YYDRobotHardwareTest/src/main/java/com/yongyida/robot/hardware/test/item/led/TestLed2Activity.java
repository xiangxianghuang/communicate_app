package com.yongyida.robot.hardware.test.item.led;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;


import com.yongyida.robot.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSend2Control;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;



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
 * Create By HuangXiangXiang 2018/6/1
 */
public class TestLed2Activity extends TestBaseActivity implements SeekBar.OnSeekBarChangeListener, AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener {

    private RadioGroup mDirectionRgp;
    private GridView mEffectGvw;
    private SeekBar mSpeedSbr;
    /**
     * 13
     */
    private TextView mSpeedValueTvw;

    private String[] effectNames = {"常亮白", "常亮红", "常亮蓝", "常亮黄", "常亮绿",
                                    "呼吸白", "呼吸红", "呼吸蓝", "呼吸黄", "呼吸绿",
                                    "跑圈蓝", "跑圈绿", "跑圈黄", "跑圈红",
                                    "闪烁蓝", "闪烁黄", "闪烁绿", "闪烁红",
                                    "七彩跑圈",
                                    "关灯",
                                    } ;
    private LedSend2Control mLedControl2 = new LedSend2Control();

    @Override
    protected View initContentView() {

        View view = mLayoutInflater.inflate(R.layout.activity_test_led_2, null);

        mDirectionRgp = (RadioGroup) view.findViewById(R.id.direction_rgp);
        mSpeedSbr = (SeekBar) view.findViewById(R.id.speed_sbr);
        mSpeedValueTvw = (TextView) view.findViewById(R.id.speed_value_tvw);
        mEffectGvw = (GridView) view.findViewById(R.id.effect_gvw);

        mDirectionRgp.setOnCheckedChangeListener(this);
        mSpeedSbr.setOnSeekBarChangeListener(this);

        mEffectGvw.setAdapter(mBaseAdapter);
        mEffectGvw.setOnItemClickListener(this);
        mDirectionRgp.postInvalidate();
        return view;
    }

    @Override
    protected String getTips() {
        return null;
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        mSpeedValueTvw.setText(String.valueOf(progress+1));

        mLedControl2.setSpeed(progress+1);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private BaseAdapter mBaseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {

            return LedSend2Control.Effect.values().length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView textView ;
            if(convertView == null){

                convertView = mLayoutInflater.inflate(R.layout.item_text , null) ;
                textView = convertView.findViewById(R.id.text_tvw) ;

                convertView.setTag(textView);
            }else{

                textView = (TextView) convertView.getTag();
            }

            textView.setText(effectNames[position]);

            return convertView;
        }

    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mLedControl2.setEffect(LedSend2Control.Effect.values()[position]);

        SendClient.getInstance(this).send(null, mLedControl2, null);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if(checkedId == R.id.direction_left_rbn){

            mLedControl2.setDirection(LedSend2Control.Direction.LEFT);

        }else  if(checkedId == R.id.direction_right_rbn){

            mLedControl2.setDirection(LedSend2Control.Direction.RIGHT);

        }else  if(checkedId == R.id.direction_both_rbn){

            mLedControl2.setDirection(LedSend2Control.Direction.BOTH);
        }

    }
}
