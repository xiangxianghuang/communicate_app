package com.yongyida.robot.hardware.test.item.motion.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yongyida.robot.communicate.app.hardware.motion.response.data.MotionSystem;
import com.yongyida.robot.communicate.app.hardware.motion.response.data.MotionSystemHistory;
import com.yongyida.robot.hardware.test.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



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
 * Create By HuangXiangXiang 2018/7/10
 */
public class HistoriesDialog extends Dialog {

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");

    private ListView mHistoriesLvw;
    private ArrayList<MotionSystemHistory.History> mHistories ;

    public HistoriesDialog(@NonNull Context context,ArrayList<MotionSystemHistory.History> histories) {
        super(context);
        this.mHistories = histories ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_histories);
        this.mHistoriesLvw = (ListView) findViewById(R.id.histories_lvw);
        this.mHistoriesLvw.setAdapter(mAdapter);

    }


    private BaseAdapter mAdapter = new BaseAdapter() {

        @Override
        public int getCount() {

            return mHistories.size();
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

                convertView = new TextView(getContext()) ;
            }

            textView = (TextView) convertView;

            MotionSystemHistory.History history = mHistories.get(position) ;

            textView.setText(Html.fromHtml(df.format(new Date(history.time))+ "<br>" + history.value));

            return convertView;
        }

    };

}
