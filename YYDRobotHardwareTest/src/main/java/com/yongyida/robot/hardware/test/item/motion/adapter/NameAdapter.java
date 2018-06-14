package com.yongyida.robot.hardware.test.item.motion.adapter;


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
 * Create By HuangXiangXiang 2018/6/13
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yongyida.robot.hardware.test.R;
public class NameAdapter extends BaseAdapter {

    private int selectIndex = 0 ;

    private final LayoutInflater layoutInflater ;
    private final int left ;
    private final int top ;
    private final int right;
    private final int bottom ;
    private final String[] names ;


    public NameAdapter(Context context, String[] names){

        this.layoutInflater = LayoutInflater.from(context) ;
        this.left = context.getResources().getDimensionPixelOffset(R.dimen.buttonPaddingLeft);
        this.top = context.getResources().getDimensionPixelOffset(R.dimen.buttonPaddingTop);
        this.right= context.getResources().getDimensionPixelOffset(R.dimen.buttonPaddingRight);
        this.bottom = context.getResources().getDimensionPixelOffset(R.dimen.buttonPaddingBottom);

        this.names = names ;
    }


    public void setSelectIndex(int selectIndex) {

        this.selectIndex = selectIndex;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return names.length;
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

        TextView textView  ;
        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.item_text, null) ;
            textView = convertView.findViewById(R.id.text_tvw) ;

            textView.setPadding(left,top,right,bottom);

            convertView.setTag(textView);

        }else {

            textView = (TextView) convertView.getTag();
        }

        if(position == selectIndex){

            textView.setBackgroundResource(R.drawable.item_select_bg);

        }else {

            textView.setBackgroundResource(R.drawable.item_bg);
        }

        textView.requestLayout();
        textView.setText(names[position]);

        return convertView;
    }
}
