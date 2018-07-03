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
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yongyida.robot.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.common.send.SendResponseListener;
import com.yongyida.robot.communicate.app.hardware.motion.response.data.Ultrasonic;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.QueryUltrasonicControl;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.data.ModelInfo;

/**
 * Create By HuangXiangXiang 2018/6/22
 * 超声波信息
 */
public class TestUltrasonicFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = TestUltrasonicFragment.class.getSimpleName() ;


    private static String [] POSITION_NAMES ;
    static{

        final String model = ModelInfo.getInstance().getModel() ;
        if(model.contains("YQ110")){

            POSITION_NAMES = new String[] {"正中","左前","左侧","左后","右后","右侧","右前"} ;

        }else{

            POSITION_NAMES = new String[] {"肚子","左前脚","左胸","左后脚","右后脚","右胸","右前腿"} ;
        }
    }




    private QueryUltrasonicControl mQueryUltrasonicControl = new QueryUltrasonicControl();
    private View view;
    /**
     * 执行
     */
    private Button mExecuteBtn;
    /**
     * 是否发送给Android
     */
    private TextView mAndroidTvw;
    /**
     * 默认
     */
    private RadioButton mAndroidDefaultRbn;
    /**
     * 发送
     */
    private RadioButton mAndroidSendRbn;
    /**
     * 不发送
     */
    private RadioButton mAndroidNoSendRbn;
    private RadioGroup mAndroidRgp;
    /**
     * 是否发送给Slam
     */
    private TextView mSlamTvw;
    /**
     * 默认
     */
    private RadioButton mSlamDefaultRbn;
    /**
     * 发送
     */
    private RadioButton mSlamSendRbn;
    /**
     * 不发送
     */
    private RadioButton mSlamNoSendRbn;
    private RadioGroup mSlamRgp;
    private GridView mDistanceGvw;

    @Override
    public String getName() {
        return "超声波";
    }

    private LayoutInflater mLayoutInflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        this.mLayoutInflater = inflater ;

        View view = inflater.inflate(R.layout.fragment_test_ultrasonic, null);

        initView(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        LogHelper.i(TAG, LogHelper.__TAG__());

        mQueryUltrasonicControl.setAndroid(QueryUltrasonicControl.Android.NO_SEND);
        mQueryUltrasonicControl.setSlam(QueryUltrasonicControl.Slam.SEND);
        SendClient.getInstance(getActivity()).send(getActivity(), mQueryUltrasonicControl,null);
    }

    private void initView(View view) {
        mExecuteBtn = (Button) view.findViewById(R.id.execute_btn);
        mExecuteBtn.setOnClickListener(this);
        mAndroidTvw = (TextView) view.findViewById(R.id.android_tvw);
        mAndroidTvw.setOnClickListener(this);
        mAndroidDefaultRbn = (RadioButton) view.findViewById(R.id.android_default_rbn);
        mAndroidSendRbn = (RadioButton) view.findViewById(R.id.android_send_rbn);
        mAndroidNoSendRbn = (RadioButton) view.findViewById(R.id.android_no_send_rbn);
        mAndroidRgp = (RadioGroup) view.findViewById(R.id.android_rgp);
        mSlamTvw = (TextView) view.findViewById(R.id.slam_tvw);
        mSlamTvw.setOnClickListener(this);
        mSlamDefaultRbn = (RadioButton) view.findViewById(R.id.slam_default_rbn);
        mSlamSendRbn = (RadioButton) view.findViewById(R.id.slam_send_rbn);
        mSlamNoSendRbn = (RadioButton) view.findViewById(R.id.slam_no_send_rbn);
        mSlamRgp = (RadioGroup) view.findViewById(R.id.slam_rgp);
        mDistanceGvw = (GridView) view.findViewById(R.id.distance_gvw);


        mUltrasonicAdapter = new UltrasonicAdapter() ;
        mDistanceGvw.setAdapter(mUltrasonicAdapter);
    }


    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            int[] distance = (int[]) msg.obj;
            mUltrasonicAdapter.setDistances(distance);
        }
    } ;


    private SendResponseListener mSendResponseListener = new SendResponseListener<Ultrasonic>(){

        @Override
        public void onSuccess(final Ultrasonic ultrasonic) {

            LogHelper.i(TAG, LogHelper.__TAG__() + ", ultrasonic : " + new Gson().toJson(ultrasonic));

            Message message = mHandler.obtainMessage() ;
            message.obj = ultrasonic.getDistances() ;
            mHandler.sendMessage(message) ;

        }

        @Override
        public void onFail(int result, String message) {

            LogHelper.e(TAG, LogHelper.__TAG__());
        }
    } ;



    @Override
    public void onClick(View v) {

        if(v == mExecuteBtn){

            int checkId = mAndroidRgp.getCheckedRadioButtonId() ;
            if(checkId == R.id.android_default_rbn){

                mQueryUltrasonicControl.setAndroid(QueryUltrasonicControl.Android.DEFAULT);

            }else if(checkId == R.id.android_send_rbn ){

                mQueryUltrasonicControl.setAndroid(QueryUltrasonicControl.Android.SEND);

            }else if(checkId == R.id.android_no_send_rbn){

                mQueryUltrasonicControl.setAndroid(QueryUltrasonicControl.Android.NO_SEND);
            }else {

                mQueryUltrasonicControl.setAndroid(null);
            }

            checkId = mSlamRgp.getCheckedRadioButtonId() ;
            if(checkId == R.id.slam_default_rbn){

                mQueryUltrasonicControl.setSlam(QueryUltrasonicControl.Slam.DEFAULT);

            }else if(checkId == R.id.slam_send_rbn ){

                mQueryUltrasonicControl.setSlam(QueryUltrasonicControl.Slam.SEND);

            }else if(checkId == R.id.slam_no_send_rbn){

                mQueryUltrasonicControl.setSlam(QueryUltrasonicControl.Slam.NO_SEND);

            }else {

                mQueryUltrasonicControl.setSlam(null);
            }


            SendClient.getInstance(getActivity()).send(getActivity(), mQueryUltrasonicControl,mSendResponseListener );

        }else if(v == mAndroidTvw){

            mAndroidRgp.clearCheck();

        }else if(v == mSlamTvw){

            mSlamRgp.clearCheck();
        }

    }

    private UltrasonicAdapter mUltrasonicAdapter ;
    private class UltrasonicAdapter extends BaseAdapter {

        private int[] distances ;
        private int[] preDistances ;

        public void setDistances(int[] distances){

            if(distances == null || distances.length != POSITION_NAMES.length){

                return;
            }

            if(this.distances == null){

                this.preDistances = distances ;
                this.distances = distances ;

            }else{

                this.preDistances = this.distances ;
                this.distances = distances ;
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return distances == null ? 0 : distances.length;
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

                convertView = mLayoutInflater.inflate(R.layout.item_text, null) ;

                textView = convertView.findViewById(R.id.text_tvw) ;
                convertView.setTag(textView);

            }else{

                textView = (TextView) convertView.getTag();
            }

            int offset = Math.abs(distances[position] - preDistances[position]) ;
            if(offset > 10){

                textView.setBackgroundColor(0x80ffff00);

            }else{

                textView.setBackgroundColor(0x8000FF00);
            }

            textView.setText(POSITION_NAMES[position] + " ：" + distances[position] + "厘米");

            return convertView;
        }
    }

}
