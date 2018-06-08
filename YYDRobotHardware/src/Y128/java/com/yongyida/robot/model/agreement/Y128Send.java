package com.yongyida.robot.model.agreement;

import com.yongyida.robot.serial.SerialReceive;
import com.yongyida.robot.serial.SerialSend;

/**
 * Created by HuangXiangXiang on 2018/3/30.
 */
public class Y128Send {


    private static Y128Send ourInstance ;
    public static Y128Send getInstance() {

        if(ourInstance == null){
            ourInstance = new Y128Send() ;
        }
        return ourInstance;
    }

    private SerialSend serialSend ;

    private Y128Send(){

        serialSend = SerialSend.getInstance() ;

        SerialReceive.getInstance().startReceive() ;
    }

    public int sendData(Y128Steering.SingleChip singleChip) {

        return serialSend.sendData(singleChip);
    }


    public int sendData(byte[] data) {

        return serialSend.sendData(data);
    }


}
