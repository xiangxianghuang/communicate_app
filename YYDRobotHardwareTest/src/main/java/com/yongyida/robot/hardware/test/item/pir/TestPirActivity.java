package com.yongyida.robot.hardware.test.item.pir;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yongyida.robot.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.common.send.SendResponseListener;
import com.yongyida.robot.communicate.app.hardware.pir.response.data.PirValue;
import com.yongyida.robot.communicate.app.hardware.pir.send.data.QueryPirValueControl;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HuangXiangXiang on 2018/3/3.
 */
public class TestPirActivity extends TestBaseActivity {

    private static final String TAG = TestPirActivity.class.getSimpleName();

    private ListView mPirLvw;

    @Override
    protected View initContentView() {

        mInflater = LayoutInflater.from(this);

        View view = mLayoutInflater.inflate(R.layout.activity_test_pir, null);
        mPirLvw = (ListView) view.findViewById(R.id.pir_lvw);
        mPirLvw.setAdapter(mBaseAdapter);

        return view;
    }

    @Override
    protected String getTips() {
        return getString(R.string.pir_tips);
    }

    private QueryPirValueControl mQueryPirValueControl = new QueryPirValueControl();
    private SendResponseListener mSendResponseListener = new SendResponseListener<PirValue>() {

        @Override
        public void onSuccess(final PirValue pirValue) {

            if (pirValue != null) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        addPirValue(pirValue) ;
                    }
                });

            }

        }

        @Override
        public void onFail(int result, String message) {

        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SendClient.getInstance(this).send(this, mQueryPirValueControl, mSendResponseListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SendClient.getInstance(this).send(null, mQueryPirValueControl, null);
    }

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
    private class PirData {

        final String time;
        final PirValue pirValue;

        PirData(PirValue pirValue) {

            this.time = df.format(new Date());
            this.pirValue = pirValue;
        }

    }

    private final static int MAX_SIZE = 10;
    private ArrayList<PirData> mPirDatas = new ArrayList<>();

    private void addPirValue(PirValue pirValue) {

        mPirDatas.add(new PirData(pirValue));

        if (MAX_SIZE > 0) {

            if (mPirDatas.size() > MAX_SIZE) {

                mPirDatas.remove(0);
            }
        }

        mPirLvw.setSelection(mPirDatas.size() - 1);     //选择最后一个
        mBaseAdapter.notifyDataSetChanged();

    }


    private LayoutInflater mInflater;
    private BaseAdapter mBaseAdapter = new BaseAdapter() {


        @Override
        public int getCount() {
            return mPirDatas.size();
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

            ViewHolder holder;
            if (convertView == null) {

                convertView = mInflater.inflate(R.layout.item_pir_data, null);

                holder = new ViewHolder(convertView);

                convertView.setTag(holder);
            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            holder.setPirData(mPirDatas.get(position));


            return convertView;
        }


        class ViewHolder {
            View view;
            TextView mDateTvw;
            TextView mPeopleTvw;
            TextView mDistanceTvw;

            ViewHolder(View view) {
                this.view = view;
                this.mDateTvw = (TextView) view.findViewById(R.id.date_tvw);
                this.mPeopleTvw = (TextView) view.findViewById(R.id.people_tvw);
                this.mDistanceTvw = (TextView) view.findViewById(R.id.distance_tvw);
            }

            void setPirData(PirData pirData) {

                mDateTvw.setText(pirData.time);

                if (pirData.pirValue.isHasPeople()) {

                    mPeopleTvw.setTextColor(Color.GREEN);
                    mPeopleTvw.setText("监测到有人");

                } else {

                    mPeopleTvw.setTextColor(Color.RED);
                    mPeopleTvw.setText("没有监测到有人");
                }

                int distance = pirData.pirValue.getPeopleDistance();
                if (distance < 0) {

                    mDistanceTvw.setVisibility(View.INVISIBLE);

                } else {
                    mDistanceTvw.setVisibility(View.VISIBLE);
                    mDistanceTvw.setText(distance + "厘米");
                }

            }
        }

    };


}
