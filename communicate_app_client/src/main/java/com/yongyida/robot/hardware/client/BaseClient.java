package com.yongyida.robot.hardware.client;

import android.content.Context;

import com.hiva.communicate.app.client.Receiver;
import com.hiva.communicate.app.common.IResponseListener;
import com.hiva.communicate.app.common.SendResponse;
import com.hiva.communicate.app.common.send.BaseSend;

/**
 * Created by HuangXiangXiang on 2017/12/9.
 */
public class BaseClient<T extends BaseSend> {

    protected HardwareClient mHardwareClient ;
    protected Receiver mReceiver ;

    public BaseClient(Context context){

        mHardwareClient = HardwareClient.getInstance(context) ;
        mReceiver = mHardwareClient.getHardwareReceiver() ;
    }

    public SendResponse send(final T send, final IResponseListener response) {

        return mReceiver.send(send , response) ;
    }

    public void sendInMainThread(final T send, final IResponseListener response) {

        new Thread(){

            @Override
            public void run() {

                send(send , response) ;
            }
        }.start();
    }



}
