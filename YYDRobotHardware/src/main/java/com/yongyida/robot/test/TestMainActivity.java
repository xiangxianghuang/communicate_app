package com.yongyida.robot.test;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.yongyida.robot.hardware.R;

import java.util.List;

/**
 * Created by HuangXiangXiang on 2017/12/12.
 */
public class TestMainActivity extends Activity implements AdapterView.OnItemClickListener {

    private GridView mItemGvw;

    private List<ResolveInfo> mResolveInfos ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        mResolveInfos = queryResolveInfo() ;
        initView();
    }

    private void initView() {
        mItemGvw = findViewById(R.id.item_gvw);
        mItemGvw.setAdapter(mBaseAdapter);
        mItemGvw.setOnItemClickListener(this);

    }


    private List<ResolveInfo> queryResolveInfo(){

        Intent hardwareIntent = new Intent("com.yongyida.robot.HARDWARE");
        List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(hardwareIntent, 0);

        return resolveInfos ;

    }


    private BaseAdapter mBaseAdapter = new BaseAdapter(){
        @Override
        public int getCount() {

            return mResolveInfos.size();
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
                convertView = LayoutInflater.from(TestMainActivity.this).inflate(R.layout.item_text,null) ;
                textView = convertView.findViewById(R.id.text_tvw );

                convertView.setTag(textView);

            }else{

                textView = (TextView) convertView.getTag();
            }

            String label = (String) mResolveInfos.get(position).loadLabel(getPackageManager()); // 获得应用程序的Label
            textView.setText(label);

            return convertView;
        }
    } ;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        ResolveInfo reInfo = mResolveInfos.get(position) ;
        String packageName = reInfo.activityInfo.packageName; // 获得应用程序的包名
        String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name

        Intent intent = new Intent() ;
        intent.setClassName(packageName,activityName) ;
        startActivity(intent);

    }


}
