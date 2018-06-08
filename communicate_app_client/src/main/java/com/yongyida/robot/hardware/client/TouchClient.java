//package com.yongyida.robot.hardware.client;
//
//import android.content.Context;
//
//import com.yongyida.robot.communicate.app.hardware.touch.send.TouchSend;
//
///**
// * Created by HuangXiangXiang on 2018/2/24.
// */
//public final class TouchClient extends BaseClient<TouchSend> {
//
//    private static TouchClient mTouchClient ;
//    public static TouchClient getInstance(Context context){
//
//        if(mTouchClient == null){
//
//            mTouchClient = new TouchClient(context.getApplicationContext()) ;
//        }
//        return mTouchClient ;
//    }
//
//    private TouchClient(Context context) {
//        super(context);
//    }
//
//
//
//
//}
