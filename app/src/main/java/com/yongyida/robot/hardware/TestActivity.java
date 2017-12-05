package com.yongyida.robot.hardware;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yongyida.robot.communicate.app.client.Client;
import com.yongyida.robot.communicate.app.client.Receiver;
import com.yongyida.robot.communicate.app.common.IResponseListener;
import com.yongyida.robot.communicate.app.common.SendResponse;
import com.yongyida.robot.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.led.LedStatue;
import com.yongyida.robot.communicate.app.hardware.led.response.LedStatueResponse;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;
import com.yongyida.robot.communicate.app.hardware.led.send.LedStatueSend;
import com.yongyida.robot.communicate.app.hardware.vision.VersionData;
import com.yongyida.robot.communicate.app.hardware.vision.send.VersionDataSend;
import com.yongyida.robot.communicate.app.utils.LogHelper;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 */
public class TestActivity extends Activity {

    private static final String TAG = TestActivity.class.getSimpleName() ;

    private Client mClient ;
    private Receiver mReceiver ;
    private LedStatue mLedStatue ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        mClient = Client.getInstance(this) ;

        String packageName = "com.yongyida.robot.hardware" ;
        String action = "com.yongyida.robot.HARDWARE" ;
        mReceiver = mClient.getReceiver(packageName,action) ;

//        Button button = new Button(this) ;
//        setContentView(button);
//        button.setText("发送");
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                sendData() ;
//            }
//        });

    }


//    private void sendData(){
//
//        new Thread(){
//            @Override
//            public void run() {
//
//                final long start = System.currentTimeMillis() ;
//
//                LedSend ledSend = new LedSend() ;
//                IResponseListener ledResponseListener = new IResponseListener(){
//
//                    @Override
//                    public void onResponse(BaseResponse response) {
//
//                        LogHelper.i(TAG, LogHelper.__TAG__() + ", response : " + response);
//
//                        long end2 = System.currentTimeMillis() ;
//                        LogHelper.i(TAG, LogHelper.__TAG__() + ", 响应耗时1：" + (end2-start)) ;
//                    }
//                };
//                SendResponse sendResponse = mReceiver.send(ledSend,ledResponseListener) ;
//                LogHelper.i(TAG, LogHelper.__TAG__() + ", sendResponse : " + sendResponse);
//
//                long end1 = System.currentTimeMillis() ;
//                LogHelper.i(TAG, LogHelper.__TAG__() + ", 响应耗时2：" + (end1-start)) ;
//            }
//        }.start();
//
//    }

    public void getLed(View view) {

        if(mLedStatue != null){

            return;
        }

        new Thread(){
            @Override
            public void run() {

                final long start = System.currentTimeMillis() ;

                LedStatueSend ledSend = new LedStatueSend() ;
                IResponseListener ledResponseListener = new IResponseListener(){

                    @Override
                    public void onResponse(BaseResponse response) {

                        LedStatueResponse ledStatueResponse = (LedStatueResponse) response;
                        mLedStatue = ledStatueResponse.getLedStatue() ;

                        LogHelper.i(TAG, LogHelper.__TAG__() + ", response : " + response);

                        long end2 = System.currentTimeMillis() ;
                        LogHelper.i(TAG, LogHelper.__TAG__() + ", 响应耗时1：" + (end2-start)) ;
                    }
                };
                SendResponse sendResponse = mReceiver.send(ledSend,ledResponseListener) ;
                LogHelper.i(TAG, LogHelper.__TAG__() + ", sendResponse : " + sendResponse);

                long end1 = System.currentTimeMillis() ;
                LogHelper.i(TAG, LogHelper.__TAG__() + ", 响应耗时2：" + (end1-start)) ;
            }
        }.start();

    }

    boolean isFlag = false ;
    int cold ;
    int warm ;
    public void updateLed(View view) {


        if(mLedStatue == null){

            return;
        }

        if(isFlag){
            isFlag = false;
            mLedStatue.setCold(++cold);

        }else{
            isFlag = true ;
            mLedStatue.setWarm(++warm);
        }


        new Thread(){
            @Override
            public void run() {

                final long start = System.currentTimeMillis() ;

                LedSend ledSend = new LedSend() ;
                ledSend.setLedStatue(mLedStatue);

                IResponseListener ledResponseListener = new IResponseListener(){

                    @Override
                    public void onResponse(BaseResponse response) {

                        LogHelper.i(TAG, LogHelper.__TAG__() + ", response : " + response);

                        long end2 = System.currentTimeMillis() ;
                        LogHelper.i(TAG, LogHelper.__TAG__() + ", 响应耗时1：" + (end2-start)) ;
                    }
                };
                SendResponse sendResponse = mReceiver.send(ledSend,ledResponseListener) ;
                LogHelper.i(TAG, LogHelper.__TAG__() + ", sendResponse : " + sendResponse);

                long end1 = System.currentTimeMillis() ;
                LogHelper.i(TAG, LogHelper.__TAG__() + ", 响应耗时2：" + (end1-start)) ;
            }
        }.start();


    }

    public void sendVersionData(View view) {

        new Thread(){
            @Override
            public void run() {

                final long start = System.currentTimeMillis() ;

                VersionData versionData = new VersionData() ;
                versionData.setPosition(VersionData.Position.MIDDLE);
                versionData.setDistance(10);

                VersionDataSend versionDataSend = new VersionDataSend() ;
                versionDataSend.setVersionData(versionData);

                IResponseListener responseListener = new IResponseListener(){

                    @Override
                    public void onResponse(BaseResponse response) {

                        LogHelper.i(TAG, LogHelper.__TAG__() + ", response : " + response);

                        long end2 = System.currentTimeMillis() ;
                        LogHelper.i(TAG, LogHelper.__TAG__() + ", 响应耗时1：" + (end2-start)) ;
                    }
                };
                SendResponse sendResponse = mReceiver.send(versionDataSend,responseListener) ;
                LogHelper.i(TAG, LogHelper.__TAG__() + ", sendResponse : " + sendResponse);

                long end1 = System.currentTimeMillis() ;
                LogHelper.i(TAG, LogHelper.__TAG__() + ", 响应耗时2：" + (end1-start)) ;
            }
        }.start();

    }
}
