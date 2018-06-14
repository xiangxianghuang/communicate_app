package com.hiva.communicate.app.client;

import android.content.Context;

import com.hiva.communicate.app.common.send.SendResponseListener;
import com.hiva.communicate.app.common.SendResponse;
import com.hiva.communicate.app.common.send.BaseSend;

import java.util.HashMap;

/**
 * Created by HuangXiangXiang on 2017/11/28.
 * 1、实现 1 对 1的发送
 */
public class Client {

    private Context mContext ;

    /**
     * action 对应
     * */
    private HashMap<String, Receivers> mReceivers = new HashMap() ;


    private static Client mClient ;
    public static Client getInstance(Context context){

        if(mClient == null){

            mClient = new Client(context.getApplicationContext()) ;
        }

        return mClient ;
    }
    private Client(Context context){

        this.mContext = context ;
    }

    public Receiver getReceiver(String packageName,String action){

        Receivers receivers = mReceivers.get(action) ;
        if(receivers == null){

            receivers = new Receivers(mContext, action) ;

            mReceivers.put(action, receivers) ;
        }

        return receivers.getReceiver(packageName) ;
    }

//    public SendResponse send(Receiver receiver, BaseSend baseSend, SendResponseListener response){
//
//        return receiver.send(baseSend,response) ;
//    }
//
//    public SendResponse send(String packageName,String action, BaseSend baseSend, SendResponseListener response){
//
//        Receiver receiver = getReceiver(packageName, action) ;
//
//        return receiver.send(baseSend,response) ;
//    }



}
