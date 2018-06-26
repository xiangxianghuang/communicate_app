package com.yongyida.robot.control.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.BaseSendHandlers;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.server.ServerService;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 */
public class HardWareServerService extends ServerService {

    private static final String TAG = HardWareServerService.class.getSimpleName() ;

    /**
     * 启动服务
     * */
    public static void startHardWareServerService(Context context){

        LogHelper.i(TAG, LogHelper.__TAG__()) ;

        Intent intent = new Intent(context, HardWareServerService.class) ;
        context.startService(intent) ;
    }


    private HardwareConfig mHardwareConfig ;

    @Override
    public void onCreate() {
        super.onCreate();

        LogHelper.i(TAG, LogHelper.__TAG__()) ;

        mHardwareConfig = HardwareConfig.getInstance(this) ;

        registerReceiver();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        unRegisterReceiver();
    }

    @Override
    protected void onReceiver(BaseSend send, IResponseListener responseListener) {

        LogHelper.i(TAG, LogHelper.__TAG__() + "，BaseSend : " + send);

        SendResponse sendResponse = null;

        BaseSendHandlers baseSendHandlers = mHardwareConfig.getControl(send.getClass()) ;
        if(baseSendHandlers != null){

            try{

                sendResponse = baseSendHandlers.onHandler(send ,responseListener) ;

                LogHelper.i(TAG, LogHelper.__TAG__() + "，sendResponse : " + sendResponse);
            }catch (Exception e){

                LogHelper.e(TAG, LogHelper.__TAG__() + "，Exception : " + e );
            }

        }else{

            LogHelper.e(TAG, LogHelper.__TAG__() + "，无对应的处理方式!" );
            sendResponse = new SendResponse(SendResponse.RESULT_SERVER_NO_HANDLE,null) ;
        }

        LogHelper.i(TAG, LogHelper.__TAG__() + "，sendResponse : " + sendResponse);
        if(responseListener != null && sendResponse != null){

            responseListener.onResponse(sendResponse);
        }

    }

    public static final String ACTION_MOVE = "com.yongyida.robot.MOVE" ;
    public static final String KEY_ACTION = "action" ;
    public static final String KEY_TIME = "time" ;

    private BroadcastReceiver receiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction() ;
            if(ACTION_MOVE.equals(action)){

                try{

//                    String motionAction = intent.getStringExtra(KEY_ACTION) ;
//                    int time = intent.getIntExtra(KEY_TIME, 1000) ;
//
//                    LogHelper.i(TAG, LogHelper.__TAG__() + " motionAction : " + motionAction + ", time : "+ time);
//                    MotionSendControl.Action action1 = MotionSendControl.Action.valueOf(motionAction);
//                    motionControl.setAction(action1);
//                    motionControl.getTime().setValue(time);
//
//                    onReceiver(motionControl.getSend(), null) ;

                }catch (Exception e){
                    LogHelper.e(TAG, LogHelper.__TAG__() + "Exception " + e );
                }
            }

        }
    };



//    private MotionSendControl motionControl = new MotionSendControl() ;
    private void registerReceiver(){

//        motionControl.setTime(new MotionSendControl.Time());
//
//        IntentFilter filter = new IntentFilter() ;
//        filter.addAction(ACTION_MOVE);
//
//        registerReceiver(receiver, filter) ;
    }


    private void unRegisterReceiver(){

        unregisterReceiver(receiver);
    }

}
