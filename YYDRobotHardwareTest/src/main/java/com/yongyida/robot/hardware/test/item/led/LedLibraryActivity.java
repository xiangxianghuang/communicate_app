package com.yongyida.robot.hardware.test.item.led;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.yongyida.robot.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedLibraryControl;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.data.ModelInfo;
import com.yongyida.robot.hardware.test.item.motion.adapter.NameAdapter;

import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedLibraryControl.*;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedLibraryControl.State.BATTERY_CHARGING_EQUAL_100;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedLibraryControl.State.BATTERY_CHARGING_LESS_100;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedLibraryControl.State.BATTERY_NORMAL_5_TO_10;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedLibraryControl.State.BATTERY_NORMAL_LESS_5;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedLibraryControl.State.STAND_BY;



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
 * Create By HuangXiangXiang 2018/6/26
 */
public class LedLibraryActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {


    private GridView mLedLibraryGvw;


    private static State[] mStates ;
    private static String[] mStateNames ;
    static{

        String model = ModelInfo.getInstance().getModel() ;
        if(model.contains("YQ110")){

            mStates = new State[]{BATTERY_NORMAL_LESS_5, BATTERY_NORMAL_5_TO_10,
                    BATTERY_CHARGING_LESS_100, BATTERY_CHARGING_EQUAL_100, STAND_BY
            };
            mStateNames = new String[]{"低于5%电量","5-10%电量","充电未满","充电已满","待机"} ;


        }else{

            mStates = State.values() ;

            final int length = mStates.length;
            mStateNames = new String[length];
            for (int i = 0; i < length; i++) {

                mStateNames[i] = mStates[i].toString();
            }
        }


    }



    private NameAdapter mNameAdapter;
    /**
     * 返回
     */
    private Button mBackBtn;


    public static void startActivity(Context context) {

        Intent intent = new Intent(context, LedLibraryActivity.class);
        context.startActivity(intent);
    }


    private LedLibraryControl mLedLibraryControl = new LedLibraryControl();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led_library);
        initView();

    }

//    public void back(View view){
//
//        finish();
//    }

    private void initView() {
        mLedLibraryGvw = (GridView) findViewById(R.id.led_library_gvw);
        mLedLibraryGvw.setOnItemClickListener(this);

        mNameAdapter = new NameAdapter(this, mStateNames);
        mNameAdapter.setSelectIndex(-1);
        mLedLibraryGvw.setAdapter(mNameAdapter);
        mBackBtn = (Button) findViewById(R.id.back_btn);
        mBackBtn.setOnClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mNameAdapter.setSelectIndex(position);

        mLedLibraryControl.setState(mStates[position]);
        SendClient.getInstance(this).send(null, mLedLibraryControl, null);
    }

    @Override
    public void onClick(View v) {

        if(v == mBackBtn){

            finish();
        }

    }
}
