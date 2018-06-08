package com.yongyida.robot.hardware.test.item.hand.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.hand.bean.RecordArmAngle;

import java.util.ArrayList;



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
 * Create By HuangXiangXiang 2018/5/28
 */
public class RecordAngleAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<RecordArmAngle> mRecordArmAngles;
    private int selectIndex = -1 ;



    public RecordAngleAdapter(Context context) {

        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);

    }


    public void setRecordArmAngles(ArrayList<RecordArmAngle> recordArmAngles){

        this.mRecordArmAngles = recordArmAngles;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    @Override
    public int getCount() {

        if(mRecordArmAngles == null){

            return 0 ;
        }

        return mRecordArmAngles.size();
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

        ViewHolder holder ;
        if(convertView == null){

            convertView = mLayoutInflater.inflate(R.layout.item_record_angle, null);

            holder = new ViewHolder(convertView) ;

            convertView.setTag(holder);

        }else {

            holder = (ViewHolder) convertView.getTag();
        }

        if(selectIndex == position){

            convertView.setBackgroundColor(Color.GRAY);
        }else {

            convertView.setBackgroundColor(Color.TRANSPARENT);
        }


        RecordArmAngle recordArmAngle = mRecordArmAngles.get(position) ;
        holder.setRecordArmAngle(position , recordArmAngle) ;

        return convertView;
    }

    static class ViewHolder {
        View view;
        TextView mTimeTvw;
        TextView mDelayTvw;

        ViewHolder(View view) {
            this.view = view;
            this.mTimeTvw = (TextView) view.findViewById(R.id.time_tvw);
            this.mDelayTvw = (TextView) view.findViewById(R.id.delay_tvw);
        }

        public void setRecordArmAngle(int position, RecordArmAngle recordArmAngle) {

            this.mTimeTvw.setText((position + 1) + "、 运动时间:"+ recordArmAngle.getTime() + "毫秒");
            this.mDelayTvw.setText("等待时间:" + recordArmAngle.getDelay() + "毫秒");
        }


    }
}
