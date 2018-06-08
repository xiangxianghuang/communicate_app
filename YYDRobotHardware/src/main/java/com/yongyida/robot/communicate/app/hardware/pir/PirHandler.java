package com.yongyida.robot.communicate.app.hardware.pir;

import android.content.Context;
import android.content.Intent;

import com.yongyida.robot.communicate.app.hardware.BaseHandler;
import com.yongyida.robot.communicate.app.hardware.pir.send.PirSend;

/**
 */
public abstract class PirHandler extends BaseHandler<PirSend> {

    public PirHandler(Context context) {
        super(context);
    }


    public static final String ACTION_MONITOR_PERSON = "com.yongyida.robot.MONITOR_PERSON" ;
    public static final String KEY_DISTANCE      = "distance" ;


    protected void monitorPerson(){

        Intent intent = new Intent(ACTION_MONITOR_PERSON) ;
        mContext.sendBroadcast(intent);
    }


    protected void monitorPerson(int distance){

        Intent intent = new Intent(ACTION_MONITOR_PERSON);
        intent.putExtra(KEY_DISTANCE, distance);
        mContext.sendBroadcast(intent);
    }

}
