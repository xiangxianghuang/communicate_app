package com.yongyida.robot.hardware.test.item.motion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yongyida.robot.communicate.app.hardware.motion.response.data.HandAngle;
import com.yongyida.robot.hardware.test.R;



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
public class AngleAdapter extends BaseAdapter {

    private Context context ;
    private LayoutInflater inflater ;

    private HandAngle handAngle;


    public AngleAdapter(Context context){

        this.context = context ;
        this.inflater = LayoutInflater.from(context) ;
    }

    public void setHandAngle(HandAngle handAngle){

        this.handAngle = handAngle ;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        if(handAngle == null){

            return 0 ;
        }

        return handAngle.leftArmAngle.angles.length +
                handAngle.rightArmAngle.angles.length +
                handAngle.leftFingerAngle.angles.length +
                handAngle.rightFingerAngle.angles.length;
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
//        if(convertView == null){      // 使用缓存 会出现数据丢失

            convertView = inflater.inflate(R.layout.item_angle, null) ;
            textView = convertView.findViewById(R.id.angle_tvw) ;

            convertView.setTag(textView);

//        }else {
//
//            textView = (TextView) convertView.getTag();
//        }

        int index = position/2 ;
        if(position % 2 == 0){
            //左

            if(index < handAngle.leftArmAngle.angles.length){
                // 臂
                int angle = handAngle.leftArmAngle.angles[index] ;
                textView.setText("左臂"+index+"：" + angle);

            }else {
                // 指

                int left = index - handAngle.leftArmAngle.angles.length ;
                int angle = handAngle.leftFingerAngle.angles[left] ;
                textView.setText("左指"+left+"：" + angle);
            }

        }else {
            // 右

            if(index < handAngle.rightArmAngle.angles.length){
                // 臂
                int angle = 0 ;
                angle = handAngle.rightArmAngle.angles[index] ;
                textView.setText("右臂"+index+"：" + angle);

            }else {
                // 指

                int left = index - handAngle.rightArmAngle.angles.length ;
                int angle = handAngle.rightFingerAngle.angles[left] ;
                textView.setText("右指"+left+"：" + angle);
            }
        }

        return convertView;
    }
}
