package com.yongyida.robot.hardware.client;

import android.content.Context;

import com.hiva.communicate.app.client.Client;
import com.hiva.communicate.app.client.Receiver;

/**
 * Created by HuangXiangXiang on 2017/12/9.
 */
public class BaseClient {

    protected HardwareClient mHardwareClient ;
    protected Receiver mReceiver ;

    public BaseClient(Context context){

        mHardwareClient = HardwareClient.getInstance(context) ;
        mReceiver = mHardwareClient.getHardwareReceiver() ;
    }



}