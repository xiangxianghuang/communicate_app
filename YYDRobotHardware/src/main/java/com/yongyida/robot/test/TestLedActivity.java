package com.yongyida.robot.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;
import com.yongyida.robot.hardware.R;
import com.yongyida.robot.hardware.client.LedClient;

/**
 * Created by HuangXiangXiang on 2017/12/12.
 */
public class TestLedActivity extends Activity implements AdapterView.OnItemClickListener {


    private LedClient mLedClient ;

    private GridView mEffectGvw;
    private LedScene[] mLedScenes = LedScene.values() ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_led);
        initView();

        mLedClient = new LedClient(this) ;
    }

    private void initView() {

        mEffectGvw = findViewById(R.id.effect_gvw);
        mEffectGvw.setAdapter(mBaseAdapter);
        mEffectGvw.setOnItemClickListener(this);
    }


    private BaseAdapter mBaseAdapter = new BaseAdapter(){
        @Override
        public int getCount() {

            return mLedScenes.length;
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
                convertView = LayoutInflater.from(TestLedActivity.this).inflate(R.layout.item_text,null) ;
                textView = convertView.findViewById(R.id.text_tvw );

                convertView.setTag(textView);

            }else{

                textView = (TextView) convertView.getTag();
            }
            textView.setText(mLedScenes[position].name());

            return convertView;
        }
    } ;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        LedScene ledScene = mLedScenes[position];
        mLedClient.sendLightEffectInMainThread(ledScene);

    }
}
