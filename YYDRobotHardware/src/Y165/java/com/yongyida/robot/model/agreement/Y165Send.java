package com.yongyida.robot.model.agreement;

import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.utils.StringUtils;
import com.yongyida.robot.serial.SerialSend;

/**
 * Created by HuangXiangXiang on 2018/3/30.
 */
public class Y165Send {

    private static final String TAG = Y165Send.class.getSimpleName() ;

    private static Y165Send ourInstance ;
    public static Y165Send getInstance() {

        if(ourInstance == null){
            ourInstance = new Y165Send() ;
        }
        return ourInstance;
    }

    private SerialSend serialSend ;

    private Y165Send(){

        serialSend = SerialSend.getInstance() ;
    }


    public int sendData(byte[] data) {

        LogHelper.i(TAG , LogHelper.__TAG__() + StringUtils.bytes2HexString(data)) ;

        return serialSend.sendData(data);
    }


}
