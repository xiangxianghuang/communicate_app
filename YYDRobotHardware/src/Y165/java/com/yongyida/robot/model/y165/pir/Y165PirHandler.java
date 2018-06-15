package com.yongyida.robot.model.y165.pir;

import android.content.Context;

import com.hiva.communicate.app.common.response.SendResponse;
import com.hiva.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.hardware.pir.PirHandler;
import com.yongyida.robot.communicate.app.hardware.pir.send.PirSend;
import com.yongyida.robot.model.agreement.Y165Receive;

/**
 * Created by HuangXiangXiang on 2018/3/30.
 */
public class Y165PirHandler extends PirHandler {

    public Y165PirHandler(Context context) {
        super(context);

        Y165Receive receive = Y165Receive.getInstance() ;
        receive.setOnMonitorPersonListener(mOnMonitorPersonListener);
    }

    @Override
    public SendResponse onHandler(PirSend send, IResponseListener responseListener) {
        return null;
    }

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
