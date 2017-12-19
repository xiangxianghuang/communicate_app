package com.yongyida.robot.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hiva.communicate.app.common.Container;
import com.yongyida.robot.communicate.app.hardware.motion.data.MotionStatue;
import com.yongyida.robot.hardware.R;
import com.yongyida.robot.hardware.client.MotionClient;

import java.util.ArrayList;

/**
 * Created by HuangXiangXiang on 2017/12/19.
 */
public class TestMotionStatueActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {


    private ArrayList<MotionStatue.MotionData> mMotionDatas = new ArrayList();

    private ListView mMotionStatueLvw;
    /**
     * 添加
     */
    private Button mAddBtn;
    /**
     * 执行
     */
    private Button mExecuteBtn;


    private MotionClient mMotionClient ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMotionClient = new MotionClient(this) ;

        setContentView(R.layout.activity_test_motion_statue);
        initView();
    }

    private void initView() {
        mMotionStatueLvw = (ListView) findViewById(R.id.motion_statue_lvw);
        mAddBtn = (Button) findViewById(R.id.add_btn);
        mAddBtn.setOnClickListener(this);
        mExecuteBtn = (Button) findViewById(R.id.execute_btn);
        mExecuteBtn.setOnClickListener(this);

        mMotionStatueLvw.setAdapter(mBaseAdapter);
        mMotionStatueLvw.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.add_btn:

                showMotionStatueDialog(null) ;

                break;
            case R.id.execute_btn:

                if(mMotionDatas.isEmpty()){

                    Toast.makeText(this,"控制数据为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                MotionStatue motionStatue = new MotionStatue() ;
                motionStatue.setMotionDatas(mMotionDatas);
                mMotionClient.sendMotionStatueInMainThread(motionStatue);

                break;
        }
    }


    private BaseAdapter mBaseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {

            return mMotionDatas.size();
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

            TextView textView;
            if (convertView == null) {
                convertView = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_text, null);
                textView = convertView.findViewById(R.id.text_tvw);

                convertView.setTag(textView);

            } else {

                textView = (TextView) convertView.getTag();
            }

            MotionStatue.MotionData motionData = mMotionDatas.get(position);
            textView.setText(motionData.getPosition().name());

            return convertView;
        }
    };

    private MotionStatueDialog mMotionStatueDialog ;
    private void showMotionStatueDialog(MotionStatue.MotionData motionData){

        if(mMotionStatueDialog == null){

            mMotionStatueDialog = new MotionStatueDialog(this,motionData) ;

        }else{

            mMotionStatueDialog.setMotionData(motionData);
        }

        mMotionStatueDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        MotionStatue.MotionData motionData = mMotionDatas.get(position) ;
        showMotionStatueDialog(motionData) ;
    }


    private class MotionStatueDialog extends AlertDialog implements View.OnClickListener, AdapterView.OnItemClickListener, View.OnLongClickListener {

        private MotionStatue.MotionData originMotionData ;  //原始
        private MotionStatue.MotionData copyMotionData ; // 拷贝;

        private GridView mPositionGvw;
        private GridView mTypeGvw;
        private EditText mDistanceEtt;
        private LinearLayout mDistanceLlt;
        private EditText mTimeEtt;
        private LinearLayout mTimeLlt;
        private EditText mSpeedEtt;
        private LinearLayout mSpeedLlt;
        /**
         * 取消
         */
        private Button mCancelBtn;
        /**
         * 确定
         */
        private Button mOkBtn;


        public MotionStatueDialog(@NonNull Context context, MotionStatue.MotionData motionData) {
            super(context);
            this.originMotionData = motionData ;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.dialog_motion_statue);
            initView();

            setMotionData(originMotionData) ;
        }


        private void initView() {
            mPositionGvw = (GridView) findViewById(R.id.position_gvw);
            mPositionGvw.setVisibility(View.VISIBLE);
            mTypeGvw = (GridView) findViewById(R.id.type_gvw);
            mTypeGvw.setVisibility(View.VISIBLE);
            mDistanceEtt = (EditText) findViewById(R.id.distance_ett);
            mDistanceLlt = (LinearLayout) findViewById(R.id.distance_llt);
            mTimeEtt = (EditText) findViewById(R.id.time_ett);
            mTimeLlt = (LinearLayout) findViewById(R.id.time_llt);
            mSpeedEtt = (EditText) findViewById(R.id.speed_ett);
            mSpeedLlt = (LinearLayout) findViewById(R.id.speed_llt);
            mCancelBtn = (Button) findViewById(R.id.cancel_btn);
            mCancelBtn.setOnClickListener(this);
            mOkBtn = (Button) findViewById(R.id.ok_btn);
            mOkBtn.setOnClickListener(this);

            mPositionGvw.setAdapter(mPositionAdapter);
            mPositionGvw.setOnItemClickListener(this);
            mPositionGvw.setOnLongClickListener(this);
            mTypeGvw.setAdapter(mTypeAdapter);
            mTypeGvw.setOnItemClickListener(this);

        }

        private final Gson GSON = new Gson() ;
        public void setMotionData(MotionStatue.MotionData motionData) {

            this.originMotionData = motionData ;
            if(motionData == null){

                this.copyMotionData = new MotionStatue.MotionData() ;

            }else{

                String json = GSON.toJson(motionData) ;
                this.copyMotionData = GSON.fromJson(json, MotionStatue.MotionData.class) ;
            }

            mPositionAdapter.notifyDataSetChanged();
            mTypeAdapter.notifyDataSetChanged();

            showItem() ;

            if(copyMotionData.getDistance() == null){

                mDistanceEtt.setText("0");
            }else{
                mDistanceEtt.setText(String.valueOf(copyMotionData.getDistance().distance));
            }

            if(copyMotionData.getTime() == null){

                mTimeEtt.setText("0");
            }else{

                mTimeEtt.setText(String.valueOf(copyMotionData.getTime().time));
            }

            if(copyMotionData.getSpeed() == null){

                mSpeedEtt.setText("0");

            }else {
                mSpeedEtt.setText(String.valueOf(copyMotionData.getSpeed().speed));
            }
        }

        @Override
        public void show() {
            super.show();

            Window window = getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }

        private MotionStatue.MotionData.Position[] mPositions = MotionStatue.MotionData.Position.values();
        private BaseAdapter mPositionAdapter = new BaseAdapter() {
            @Override
            public int getCount() {

                return mPositions.length;
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

                TextView textView;
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_text, null);
                    textView = convertView.findViewById(R.id.text_tvw);

                    convertView.setTag(textView);

                } else {

                    textView = (TextView) convertView.getTag();
                }

                boolean isSelect = (copyMotionData != null && copyMotionData.getPosition() == mPositions[position]);
                textView.setBackgroundResource(isSelect ? R.drawable.item_select_bg : R.drawable.item_bg);

                textView.setText(mPositions[position].name());

                return convertView;
            }
        };

        private MotionStatue.MotionData.Type[] mTypes = MotionStatue.MotionData.Type.values();
        private BaseAdapter mTypeAdapter = new BaseAdapter() {
            @Override
            public int getCount() {

                return mTypes.length;
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

                TextView textView;
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_text, null);
                    textView = convertView.findViewById(R.id.text_tvw);

                    convertView.setTag(textView);

                } else {

                    textView = (TextView) convertView.getTag();
                }

                boolean isSelect = (copyMotionData != null && copyMotionData.getType() == mTypes[position]);
                textView.setBackgroundResource(isSelect ? R.drawable.item_select_bg : R.drawable.item_bg);

                textView.setText(mTypes[position].name());

                return convertView;
            }
        };


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                default:
                    break;
                case R.id.cancel_btn:

                    dismiss();

                    break;
                case R.id.ok_btn:

                    int size = mMotionDatas.size() ;
                    for (int i = 0; i < size ; i++){

                        MotionStatue.MotionData motionData = mMotionDatas.get(i) ;
                        if((motionData.getPosition() == copyMotionData.getPosition())){

                            if(motionData != originMotionData){

                                Toast.makeText(getContext(),"该位置已经添加动作",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }


                    switch (copyMotionData.getType()){

                        case DISTANCE_TIME:

                            MotionStatue.MotionData.Distance distance = new MotionStatue.MotionData.Distance() ;
                            String value = mDistanceEtt.getText().toString() ;

                            int data ;
                            try{

                                data = Integer.valueOf(value) ;

                            }catch (Exception e){

                                data = 0 ;
                            }
                            distance.distance = data ;
                            copyMotionData.setDistance(distance);

                            MotionStatue.MotionData.Time time = new MotionStatue.MotionData.Time() ;
                            value = mTimeEtt.getText().toString() ;

                            try{

                                data = Integer.valueOf(value) ;

                            }catch (Exception e){

                                data = 0 ;
                            }
                            time.time = data ;
                            copyMotionData.setTime(time);


                            MotionStatue.MotionData.Speed speed = null ;
                            copyMotionData.setSpeed(speed);

                            break;

                        case DISTANCE_SPEED:

                            distance = new MotionStatue.MotionData.Distance() ;
                            value = mDistanceEtt.getText().toString() ;

                            try{

                                data = Integer.valueOf(value) ;

                            }catch (Exception e){

                                data = 0 ;
                            }
                            distance.distance = data ;
                            copyMotionData.setDistance(distance);

                            time = null ;
                            copyMotionData.setTime(time);


                            speed = new MotionStatue.MotionData.Speed() ;
                            value = mSpeedEtt.getText().toString() ;

                            try{

                                data = Integer.valueOf(value) ;

                            }catch (Exception e){

                                data = 0 ;
                            }
                            speed.speed = data ;
                            copyMotionData.setSpeed(speed);

                            break;

                        case TIME_SPEED:

                            distance = null ;
                            copyMotionData.setDistance(distance);

                            time = new MotionStatue.MotionData.Time() ;
                            value = mTimeEtt.getText().toString() ;

                            try{

                                data = Integer.valueOf(value) ;

                            }catch (Exception e){

                                data = 0 ;
                            }
                            time.time = data ;
                            copyMotionData.setTime(time);


                            speed = new MotionStatue.MotionData.Speed() ;
                            value = mSpeedEtt.getText().toString() ;

                            try{

                                data = Integer.valueOf(value) ;

                            }catch (Exception e){

                                data = 0 ;
                            }
                            speed.speed = data ;
                            copyMotionData.setSpeed(speed);

                            break;
                    }



                    if(originMotionData != null){

                        mMotionDatas.remove(originMotionData);

                    }else{


                    }
                    mMotionDatas.add(copyMotionData);
                    mBaseAdapter.notifyDataSetChanged();
                    dismiss();
                    break;
            }
        }

        private void showItem(){

            switch (copyMotionData.getType()){

                case DISTANCE_TIME:
                    mDistanceLlt.setVisibility(View.VISIBLE);
                    mTimeLlt.setVisibility(View.VISIBLE);
                    mSpeedLlt.setVisibility(View.GONE);

                    break;

                case DISTANCE_SPEED:
                    mDistanceLlt.setVisibility(View.VISIBLE);
                    mTimeLlt.setVisibility(View.GONE);
                    mSpeedLlt.setVisibility(View.VISIBLE);

                    break;

                case TIME_SPEED:
                    mDistanceLlt.setVisibility(View.GONE);
                    mTimeLlt.setVisibility(View.VISIBLE);
                    mSpeedLlt.setVisibility(View.VISIBLE);

                    break;
            }
        }


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if(parent == mPositionGvw){

                MotionStatue.MotionData.Position position1 = mPositions[position] ;
                copyMotionData.setPosition(position1);
                mPositionAdapter.notifyDataSetChanged();

            }else if(parent == mTypeGvw){

                MotionStatue.MotionData.Type type = mTypes[position] ;
                copyMotionData.setType(type);
                mTypeAdapter.notifyDataSetChanged();

                showItem() ;

            }

        }

        @Override
        public boolean onLongClick(View v) {


            return false;
        }
    }

}
