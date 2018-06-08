package com.yongyida.robot.hardware.test.item.hand;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yongyida.robot.communicate.app.hardware.hand.send.data.HandAction;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Direction;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.PreHandAction;
import com.hiva.communicate.app.common.send.SendClient;
import com.yongyida.robot.hardware.test.R;


/**
 * Created by HuangXiangXiang on 2018/4/20.
 * 手臂 运动
 */
public class TestActionFragment extends Fragment implements AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener {

    private RadioGroup mDirectionRgp;
    private GridView mPreActionGvw;

    private String[] preActionNames = {"停止","手初始化","握拳","手指轮动",
            "握手", "OK","点赞","石头",
            "剪刀","布","迎宾","挥手",
            "示爱","666","自定义"} ;


    private LayoutInflater mLayoutInflater ;

    private HandAction mHandAction = new HandAction() ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mLayoutInflater = inflater ;

        View view = inflater.inflate(R.layout.fragment_test_action, null);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mDirectionRgp = (RadioGroup) view.findViewById(R.id.direction_rgp);
        mDirectionRgp.setOnCheckedChangeListener(this);


        mPreActionGvw = (GridView) view.findViewById(R.id.pre_action_gvw);
        mPreActionGvw.setAdapter(mBaseAdapter);
        mPreActionGvw.setOnItemClickListener(this);

    }


    private BaseAdapter mBaseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {

            return PreHandAction.values().length;
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

                convertView = mLayoutInflater.inflate(R.layout.item_text , null) ;
                textView = convertView.findViewById(R.id.text_tvw) ;

                convertView.setTag(textView);
            }else{

                textView = (TextView) convertView.getTag();
            }

            textView.setText(preActionNames[position]);

            return convertView;
        }

    };

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if(checkedId == R.id.direction_left_rbn){

            mHandAction.setDirection(Direction.LEFT);

        }else  if(checkedId == R.id.direction_right_rbn){

            mHandAction.setDirection(Direction.RIGHT);

        }else  if(checkedId == R.id.direction_both_rbn){

            mHandAction.setDirection(Direction.BOTH);
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mHandAction.setPreHandAction(PreHandAction.values()[position]);
        SendClient.getInstance(getActivity()).send(mHandAction, null);
    }


}
