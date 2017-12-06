package com.hiva.communicate.app.client;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by HuangXiangXiang on 2017/11/29.
 */
public class Receivers {

    private Context context ;
    private String action ;

    Receivers(Context context, String action){

        this.context = context ;
        this.action = action ;
    }

    /**包名对应*/
    private HashMap<String, Receiver> mReceivers = new HashMap() ;

    public Receiver getReceiver(String packageName){

        Receiver receiver = mReceivers.get(packageName) ;
        if(receiver == null){

            receiver = new Receiver(context,packageName,action) ;
            mReceivers.put(packageName, receiver) ;
        }

        return receiver ;
    }



}
