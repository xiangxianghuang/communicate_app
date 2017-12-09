package com.yongyida.robot.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hiva.communicate.app.client.Receiver;
import com.hiva.communicate.app.common.IResponseListener;
import com.hiva.communicate.app.common.SendResponse;
import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.led.LedStatue;
import com.yongyida.robot.communicate.app.hardware.led.response.LedStatueResponse;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;
import com.yongyida.robot.communicate.app.hardware.led.send.LedStatueSend;
import com.yongyida.robot.communicate.app.hardware.vision.data.VisionData;
import com.yongyida.robot.hardware.R;
import com.yongyida.robot.hardware.client.HardwareClient;
import com.yongyida.robot.hardware.client.VisionClient;

/**
 * Created by HuangXiangXiang on 2017/12/6.
 */
public class TestClientActivity extends Activity {

    private static final String TAG = TestClientActivity.class.getSimpleName() ;

    private HardwareClient mHardwareClient ;
    private Receiver mHardwareReceiver ;
    private LedStatue mLedStatue ;

    private VisionClient mVisionClient ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        mHardwareClient = HardwareClient.getInstance(this) ;
        mHardwareReceiver = mHardwareClient.getHardwareReceiver() ;

        mVisionClient = new VisionClient(this) ;

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
                SendResponse sendResponse = mHardwareReceiver.send(ledSend,ledResponseListener) ;
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
                SendResponse sendResponse = mHardwareReceiver.send(ledSend,ledResponseListener) ;
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

                mVisionClient.sendVisionData(VisionData.Position.MIDDLE,120);

//                final long start = System.currentTimeMillis() ;
//
//                VisionData visionData = new VisionData() ;
//                visionData.setPosition(VisionData.Position.MIDDLE);
//                visionData.setDistance(10);
//
//                VisionDataSend versionDataSend = new VisionDataSend() ;
//                versionDataSend.setVisionData(visionData);
//
//                IResponseListener responseListener = new IResponseListener(){
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
//                SendResponse sendResponse = mHardwareReceiver.send(versionDataSend,responseListener) ;
//                LogHelper.i(TAG, LogHelper.__TAG__() + ", sendResponse : " + sendResponse);
//
//                long end1 = System.currentTimeMillis() ;
//                LogHelper.i(TAG, LogHelper.__TAG__() + ", 响应耗时2：" + (end1-start)) ;
            }
        }.start();

    }

    public void startVersion(View view) {

        new Thread(){

            @Override
            public void run() {

                mVisionClient.startVisionData();

            }
        }.start();

    }

    public void stopVersion(View view) {

        new Thread(){

            @Override
            public void run() {

                mVisionClient.stopVisionData();

            }
        }.start();

    }
}
