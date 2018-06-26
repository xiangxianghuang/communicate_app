package com.yongyida.robot.model.y165.pir;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.pir.PirSendHandlers;
import com.yongyida.robot.model.agreement.Y165Receive;

/**
 * Created by HuangXiangXiang on 2018/3/30.
 */
public class Y165PirSendHandlers extends PirSendHandlers {

    public Y165PirSendHandlers(Context context) {
        super(context);

        Y165Receive receive = Y165Receive.getInstance() ;
        receive.setOnMonitorPersonListener(mOnMonitorPersonListener);
    }

//    @Override
//    public SendResponse onHandler(PirSend send, IResponseListener responseListener) {
//        return null;
//    }

    private OnMonitorPersonListener mOnMonitorPersonListener = new OnMonitorPersonListener(){

        @Override
        public void onMonitorPerson(int distance) {

            monitorPerson(distance);

        }
    };

    /**
     * 电池变化
     * */
    public interface OnMonitorPersonListener{

        void onMonitorPerson(int distance) ;
    }


}
