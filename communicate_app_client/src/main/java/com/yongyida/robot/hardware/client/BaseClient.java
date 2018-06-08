//package com.yongyida.robot.hardware.client;
//
//import android.content.Context;
//
//import com.hiva.communicate.app.client.Receiver;
//import com.hiva.communicate.app.common.IResponseListener;
//import com.hiva.communicate.app.common.SendResponse;
//import com.hiva.communicate.app.common.send.BaseControl;
//import com.hiva.communicate.app.common.send.BaseSend;
//
///**
// * Created by HuangXiangXiang on 2017/12/9.
// */
//public class BaseClient<T extends BaseSend> {
//
//
//    private static BaseClient mBaseClient ;
//    public static BaseClient getInstance(Context context){
//
//        if(mBaseClient == null){
//
//            mBaseClient = new BaseClient(context.getApplicationContext()) ;
//        }
//        return mBaseClient ;
//    }
//
//
//    protected HardwareClient mHardwareClient ;
//    protected Receiver mReceiver ;
//
//
//    protected BaseClient(Context context){
//
//        mHardwareClient = HardwareClient.getInstance(context) ;
//        mReceiver = mHardwareClient.getHardwareReceiver() ;
//    }
//
//    public SendResponse send(final T send, final IResponseListener response) {
//
//        return mReceiver.send(send , response) ;
//    }
//
//    public void sendInMainThread(final T send, final IResponseListener response) {
//
//        new Thread(){
//
//            @Override
//            public void run() {
//
//                send(send , response) ;
//            }
//        }.start();
//    }
//
//
//
//    public SendResponse send(final BaseControl control, final IResponseListener response) {
//
//        BaseSend send = control.getSend();
//
//        return mReceiver.send(send , response) ;
//
//
//    }
//
//    public void sendInMainThread(final BaseControl control, final IResponseListener response) {
//
//        new Thread(){
//
//            @Override
//            public void run() {
//
//                send(control , response) ;
//            }
//        }.start();
//    }
//
//}
