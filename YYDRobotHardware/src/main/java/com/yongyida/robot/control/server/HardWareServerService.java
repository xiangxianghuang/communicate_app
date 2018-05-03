package com.yongyida.robot.control.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.hiva.communicate.app.common.IResponseListener;
import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.send.BaseSend;
import com.hiva.communicate.app.server.ServerService;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.BaseHandler;
import com.yongyida.robot.communicate.app.hardware.motion.data.MotionControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;
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

        BaseResponse baseResponse ;

        BaseHandler baseHandler = mHardwareConfig.getControl(send.getClass()) ;
        if(baseHandler != null){

            baseResponse = baseHandler.onHandler(send) ;

        }else{

            LogHelper.e(TAG, LogHelper.__TAG__() + "，无对应的处理方式!" );
            baseResponse = new BaseResponse(BaseResponse.RESULT_CAN_NOT_HANDLE,null) ;
        }

        if(responseListener != null){

            responseListener.onResponse(baseResponse);
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

                    String motionAction = intent.getStringExtra(KEY_ACTION) ;
                    int time = intent.getIntExtra(KEY_TIME, 1000) ;

                    LogHelper.i(TAG, LogHelper.__TAG__() + " motionAction : " + motionAction + ", time : "+ time);
                    MotionControl.Action action1 = MotionControl.Action.valueOf(motionAction);
                    motionControl.setAction(action1);
                    motionControl.getTime().setValue(time);

                    onReceiver(motionSend, null) ;

                }catch (Exception e){
                    LogHelper.e(TAG, LogHelper.__TAG__() + "Exception " + e );
                }
            }

        }
    };



    private MotionSend motionSend =  new MotionSend() ;
    private MotionControl motionControl = new MotionControl() ;
    private void registerReceiver(){

        motionControl.setTime(new MotionControl.Time());

        motionSend.setMotionControl(motionControl) ;

        IntentFilter filter = new IntentFilter() ;
        filter.addAction(ACTION_MOVE);

        registerReceiver(receiver, filter) ;
    }


    private void unRegisterReceiver(){

        unregisterReceiver(receiver);
    }

}
