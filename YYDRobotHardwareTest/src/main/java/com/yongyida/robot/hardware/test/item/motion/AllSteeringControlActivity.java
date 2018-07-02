package com.yongyida.robot.hardware.test.item.motion;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.motion.fragment.TestAllMotionFragment;



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
 * Create By HuangXiangXiang 2018/6/29
 */
public class AllSteeringControlActivity extends Activity implements View.OnClickListener {


    public static void statrtActivity(Context context){

        Intent intent = new Intent(context, AllSteeringControlActivity.class) ;
        context.startActivity(intent);
    }


    /**
     * 全部舵机
     */
    private TextView mTitleTvw;
    /**
     * 执行
     */
    private Button mExecuteBtn;
    private FrameLayout mContentFlt;

    private TestAllMotionFragment mTestAllMotionFragment = new TestAllMotionFragment() ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_steering_control);
        initView();

        FragmentTransaction ft = getFragmentManager().beginTransaction() ;
        ft.add(R.id.content_flt, mTestAllMotionFragment) ;
        ft.commitAllowingStateLoss();
    }

    private void initView() {
        mTitleTvw = (TextView) findViewById(R.id.title_tvw);
        mExecuteBtn = (Button) findViewById(R.id.execute_btn);
        mExecuteBtn.setOnClickListener(this);
        mContentFlt = (FrameLayout) findViewById(R.id.content_flt);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.execute_btn) {

            mTestAllMotionFragment.execute();

        } else {
        }
    }
}
