package com.yongyida.robot.hardware.test.item.hand.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.hand.untils.RecordActionsHelper;



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
 * Create By HuangXiangXiang 2018/5/30
 * 读取舞曲脚本
 */
public class ReadDanceDialog extends Dialog implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener {

    private ListView mRecordLvw;
    private Button mCancelBtn;
    private Button mOkBtn;

    private RecordActionsHelper.RecordAction mNewRecordAction = new RecordActionsHelper.RecordAction();
    private RecordActionsHelper.RecordActions  mRecordActions ;

    public ReadDanceDialog(@NonNull Context context) {
        super(context);

        readRecordData() ;
    }

    private void readRecordData(){

        mRecordActions = RecordActionsHelper.getInstance(getContext()).recordActions ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_read_dance);
        this.mRecordLvw = (ListView) findViewById(R.id.record_lvw);
        this.mCancelBtn = (Button) findViewById(R.id.cancel_btn);
        this.mOkBtn = (Button) findViewById(R.id.ok_btn);

        this.mCancelBtn.setOnClickListener(this);
        this.mOkBtn.setOnClickListener(this);

        this.mRecordLvw.setVisibility(View.VISIBLE);
        this.mOkBtn.setVisibility(View.VISIBLE);

        this.mRecordLvw.setOnItemClickListener(this);
        this.mRecordLvw.setOnItemLongClickListener(this);
        this.mRecordLvw.setAdapter(mBaseAdapter);
    }

    private int selectIndex = 0 ;
    private BaseAdapter mBaseAdapter = new BaseAdapter(){

        @Override
        public int getCount() {

            return 1+ mRecordActions.recordActions.size() ;
        }

        @Override
        public Object getItem(int position) {

            RecordActionsHelper.RecordAction recordArmAngle ;

            if(position == 0){

                recordArmAngle = mNewRecordAction ;

            }else {
                recordArmAngle = mRecordActions.recordActions.get(position - 1) ;
            }

            return recordArmAngle;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView textView ;

            if(convertView == null){

                convertView = getLayoutInflater().inflate(R.layout.item_text, null) ;
                textView = convertView.findViewById(R.id.text_tvw) ;
                convertView.setTag(textView);

            }else {

                textView = (TextView) convertView.getTag();
            }

            if(position == selectIndex){

//                textView.setBackgroundResource(R.drawable.item_select_bg);
                textView.setBackgroundColor(0xFFFF4081);

            }else {
//                textView.setBackgroundResource(R.drawable.item_bg);
                textView.setBackgroundColor(Color.GRAY);
            }

            RecordActionsHelper.RecordAction recordArmAngle = (RecordActionsHelper.RecordAction) getItem(position);


            textView.setText(recordArmAngle.name + "[" + recordArmAngle.recordArmAngles.size()+ "]");

            return convertView;
        }
    } ;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        this.selectIndex = position ;
        this.mBaseAdapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View v) {

        if(v == mOkBtn){

            if(mOnSelectDataListener != null){

                RecordActionsHelper.RecordAction recordArmAngle = (RecordActionsHelper.RecordAction) mBaseAdapter.getItem(selectIndex);
                mOnSelectDataListener.onSelected(recordArmAngle.deepClone());
            }
        }

        dismiss();
    }

    private OnSelectDataListener mOnSelectDataListener ;

    public void setOnSelectDataListener(OnSelectDataListener onSelectDataListener) {
        this.mOnSelectDataListener = onSelectDataListener;
    }



    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        if(position != 0){

            DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    mRecordActions.recordActions.remove(position-1) ;
                    RecordActionsHelper.getInstance(getContext()).saveRecordActions();
                    mBaseAdapter.notifyDataSetChanged();

                }
            };
            new AlertDialog.Builder(getContext())
                    .setTitle("是否删除")
                    .setNegativeButton("取消",null)
                    .setPositiveButton("确定",mOnClickListener)
                    .create()
                    .show();
        }else{

            Toast.makeText(getContext(), "第一个未新建不能删除",Toast.LENGTH_SHORT).show();
        }



        return true;
    }

    public interface OnSelectDataListener{

        void onSelected(RecordActionsHelper.RecordAction recordAction) ;

    }

}
