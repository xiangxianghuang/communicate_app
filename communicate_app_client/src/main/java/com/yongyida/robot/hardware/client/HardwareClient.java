package com.yongyida.robot.hardware.client;

import android.content.Context;

import com.robot.communicate.app.client.Client;
import com.robot.communicate.app.client.Receiver;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class HardwareClient {

    private static final String HARDWARE_PACKAGE_NAME   = "com.yongyida.robot.hardware" ;
    private static final String HARDWARE_ACTION         = "com.yongyida.robot.HARDWARE" ;

    private Context mContext ;
    private Client mClient ;
    Receiver mReceiver ;

    private static HardwareClient mHardwareClient ;
    public static HardwareClient getInstance(Context context){

        if(mHardwareClient == null){

            mHardwareClient = new HardwareClient(context.getApplicationContext()) ;
        }

        return mHardwareClient ;
    }
    private HardwareClient(Context context){

        this.mContext = context ;
        this.mClient = Client.getInstance(context) ;
        getHardwareReceiver() ;
    }

    public Receiver getHardwareReceiver(){

        if(mReceiver == null){

            mReceiver = mClient.getReceiver(HARDWARE_PACKAGE_NAME,HARDWARE_ACTION) ;
        }

        return  mReceiver;
    }



}
