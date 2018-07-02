package com.yongyida.robot.hardware.test.item.motion.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.yongyida.robot.communicate.app.hardware.motion.send.data.SteeringControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.constant.Direction;
import com.yongyida.robot.hardware.test.R;

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
 * Create By HuangXiangXiang 2018/6/28
 */
public class EditAngleAdapter extends BaseAdapter {

    private final LayoutInflater mLayoutInflater;


    private Direction mDirection  ;
    private final ArrayList<SteeringControl> mSteeringControls = new ArrayList<SteeringControl>() ;
    private ArrayList<SteeringControl> mLeftSteerings ;
    private ArrayList<SteeringControl> mRightSteerings;


    public EditAngleAdapter(Context context) {

        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void setSteeringControls(ArrayList<SteeringControl> leftSteerings, ArrayList<SteeringControl> rightSteerings){

        this.mLeftSteerings = leftSteerings ;
        this.mRightSteerings = rightSteerings ;

        refreshData() ;
    }


    public void setDirection(Direction direction) {

        this.mDirection = direction;
        refreshData() ;
    }

    private void refreshData(){

        mSteeringControls.clear();
        if(mDirection != null && mLeftSteerings != null && mRightSteerings != null){

            switch (mDirection){

                case LEFT:
                case SAME:

                    mSteeringControls.addAll(mLeftSteerings) ;
                    break;

                case RIGHT:
                    mSteeringControls.addAll(mRightSteerings) ;
                    break;


                case BOTH:

                    mSteeringControls.addAll(mLeftSteerings) ;
                    mSteeringControls.addAll(mRightSteerings) ;

                    break;
            }

            notifyDataSetChanged();
        }

    }







    @Override
    public int getCount() {

        if (mSteeringControls == null) {

            return 0;

        } else {

            return mSteeringControls.size();
        }
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
        if (convertView == null) {

            convertView = mLayoutInflater.inflate(R.layout.item_edit_angle, null);
            holder = new ViewHolder(convertView) ;
            convertView.setTag(holder);

        }else {

            holder = (ViewHolder) convertView.getTag();
        }

        SteeringControl steeringControl = mSteeringControls.get(position) ;
        holder.setSteeringControl(steeringControl) ;

        return convertView;
    }

    static class ViewHolder {

        private SteeringControl mSteeringControl ;

        private TextView mPositionTvw;
        private EditText mAngleValueEtt;
        private EditText mDelayTimeEtt;
        private EditText mUsedTimeEtt;

        ViewHolder(View view) {
            this.mPositionTvw = (TextView) view.findViewById(R.id.position_tvw);
            this.mAngleValueEtt = (EditText) view.findViewById(R.id.angle_value_ett);
            this.mDelayTimeEtt = (EditText) view.findViewById(R.id.delay_time_ett);
            this.mUsedTimeEtt = (EditText) view.findViewById(R.id.used_time_ett);

            this.mAngleValueEtt.addTextChangedListener(mTextWatcher);
            this.mDelayTimeEtt.addTextChangedListener(mTextWatcher);
            this.mUsedTimeEtt.addTextChangedListener(mTextWatcher);
        }

        void setSteeringControl(SteeringControl steeringControl) {

            this.mSteeringControl = steeringControl ;

            this.mPositionTvw.setText(steeringControl.getPosition().name());
            this.mAngleValueEtt.setText(String.valueOf(steeringControl.getDistance().getValue()));
            this.mDelayTimeEtt.setText(String.valueOf(steeringControl.getDelay()));
            this.mUsedTimeEtt.setText(String.valueOf(steeringControl.getTime().getValue()));

        }

        private TextWatcher mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                int value;
                try {

                    value = Integer.parseInt(s.toString()) ;
                }catch (Exception e){

                    value = 0 ;
                }

                if (s == mAngleValueEtt.getText()){

                    mSteeringControl.getDistance().setValue(value);

                }else if(s == mDelayTimeEtt.getText()){

                    mSteeringControl.setDelay(value);

                }else if(s == mUsedTimeEtt.getText()){

                    mSteeringControl.getTime().setValue(value);
                }

            }
        } ;
    }
}
