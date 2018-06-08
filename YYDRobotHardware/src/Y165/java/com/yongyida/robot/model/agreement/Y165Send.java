package com.yongyida.robot.model.agreement;

import com.yongyida.robot.serial.SerialSend;

/**
 * Created by HuangXiangXiang on 2018/3/30.
 */
public class Y165Send {

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

    public int sendData(Y165Steering.SingleChip singleChip) {

        return serialSend.sendData(singleChip.getContent());
    }


    public int sendData(byte[] data) {

        return serialSend.sendData(data);
    }


}
