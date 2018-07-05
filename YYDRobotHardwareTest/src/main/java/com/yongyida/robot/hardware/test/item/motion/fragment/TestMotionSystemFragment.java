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
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yongyida.robot.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.common.send.SendResponseListener;
import com.yongyida.robot.communicate.app.hardware.motion.response.data.MotionSystem;
import com.yongyida.robot.communicate.app.hardware.motion.response.data.Ultrasonic;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.QueryMotionSystemControl;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.hardware.test.R;

import java.util.ArrayList;

/**
 * Create By HuangXiangXiang 2018/6/13
 */
public class TestMotionSystemFragment extends BaseFragment {

    private static final String TAG = TestMotionSystemFragment.class.getSimpleName() ;

    private QueryMotionSystemControl mQueryMotionSystemControl = new QueryMotionSystemControl();
    private LayoutInflater mInflater;
    private ListView mMotionSystemLvw;

    @Override
    public String getName() {

        return "马达系统信息";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        LogHelper.i(TAG, LogHelper.__TAG__());
        SendClient.getInstance(getActivity()).send(getActivity(), mQueryMotionSystemControl, mSendResponseListener );

        this.mInflater = inflater;

        View view = inflater.inflate(R.layout.fragment_test_motion_system, null);

        initView(view);
        return view;
    }

    @Override
    public void onDestroyView() {

        LogHelper.i(TAG, LogHelper.__TAG__());
        SendClient.getInstance(getActivity()).send(null, mQueryMotionSystemControl, null );

        super.onDestroyView();
    }

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            items = (ArrayList<MotionSystem.Item>) msg.obj;
            mAdapter.notifyDataSetChanged();
        }
    };

    private SendResponseListener mSendResponseListener = new SendResponseListener<MotionSystem>(){


        @Override
        public void onSuccess(MotionSystem motionSystem) {

            LogHelper.i(TAG, LogHelper.__TAG__() + ", ultrasonic : " + new Gson().toJson(motionSystem));

            Message message = mHandler.obtainMessage() ;
            message.obj = motionSystem.getItems() ;
            mHandler.sendMessage(message) ;
        }

        @Override
        public void onFail(int result, String message) {

            LogHelper.e(TAG, LogHelper.__TAG__());
        }
    } ;


    private void initView(View view) {

        mMotionSystemLvw = (ListView) view.findViewById(R.id.motion_system_lvw);
        mMotionSystemLvw.setAdapter(mAdapter);

    }

    private ArrayList<MotionSystem.Item> items ;

    private BaseAdapter mAdapter = new BaseAdapter() {

        @Override
        public int getCount() {

            return items == null ? 0 :items.size();
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

                convertView = new TextView(getActivity()) ;
            }

            textView = (TextView) convertView;

            MotionSystem.Item item = items.get(position) ;
            textView.setText(item.title + "\n" + item.info);

            return convertView;
        }

    };


}
