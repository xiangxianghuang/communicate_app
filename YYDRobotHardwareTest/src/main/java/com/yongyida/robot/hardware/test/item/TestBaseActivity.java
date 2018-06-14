package com.yongyida.robot.hardware.test.item;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.data.SettingData;

/**
 * Created by HuangXiangXiang on 2018/2/8.
 */
public abstract class TestBaseActivity extends Activity {

    public static final String TITLE = "title";
    protected LayoutInflater mLayoutInflater ;

    /**
     * 标题名称
     */
    private TextView mTitleTvw;

    private TextView mTipsTvw ;

    /**
     * 内容
     * */
    private LinearLayout mContentLlt;
    /**
     * 失败
     */
    private Button mFailBtn;
    /**
     * 成功
     */
    private Button mSuccessBtn;

    protected String mTitle ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLayoutInflater = LayoutInflater.from(this) ;

        setContentView(R.layout.activity_test_base);
        initView();

        mTitle = getIntent().getStringExtra(TITLE);
        mTitleTvw.setText(mTitle);

    }


    protected abstract View initContentView();

    protected String getTips(){

        return null ;
    }

    private void initView() {

        mTitleTvw = (TextView) findViewById(R.id.title_tvw);
        mTipsTvw = (TextView) findViewById(R.id.tips_tvw);
        mContentLlt = (LinearLayout) findViewById(R.id.content_llt);
        mFailBtn = (Button) findViewById(R.id.fail_btn);
        mFailBtn.setOnClickListener(onClickListener);
        mSuccessBtn = (Button) findViewById(R.id.success_btn);
        mSuccessBtn.setOnClickListener(onClickListener);

        String tips = getTips() ;
        if(TextUtils.isEmpty(tips)){

            mTipsTvw.setVisibility(View.GONE);
        }else{
            mTipsTvw.setVisibility(View.VISIBLE);
            mTipsTvw.setText(tips);
        }


        View contentView = initContentView();
        if (contentView != null) {

            mContentLlt.addView(contentView,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//            mContentLlt.addView(contentView);
        }

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v == mFailBtn) {

                onFail() ;

            } else if (v == mSuccessBtn) {

                onSuccess();

            } else {

            }
        }
    };


    protected void onFail(){

        SettingData.saveStatus(TestBaseActivity.this, this.getClass().getName(), SettingData.STATUS_FAIL);
        finish();
    }


    protected void onSuccess(){

        SettingData.saveStatus(TestBaseActivity.this, this.getClass().getName(), SettingData.STATUS_SUCCESS);
        finish();
    }





    protected void showResultDialog(){

        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which == DialogInterface.BUTTON_POSITIVE){

                    onSuccess() ;

                }else if(which == DialogInterface.BUTTON_NEGATIVE){

                    onFail();
                }

            }
        };

        new AlertDialog.Builder(this)
                .setTitle(mTitle)
                .setMessage(R.string.result_test)
                .setPositiveButton(R.string.success,onClickListener)
                .setNegativeButton(R.string.fail,onClickListener)
                .create()
                .show();

    }

}
