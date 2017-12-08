package com.yongyida.robot.model.y128.serial;

import android.util.Log;

import com.yongyida.robot.agreement.old.Steering;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Huangxiangxiang on 2017/8/11.
 */
public class SerialSend implements ISerialSend {

    private static final String TAG = SerialSend.class.getSimpleName() ;

    private Serial mSerial ;

    public SerialSend(Serial serial){

        mSerial = serial ;
    }

    public int sendData(Steering.SingleChip singleChip) {

        return sendData(Steering.getCmd(singleChip)) ;
    }

    private int sendData(byte[] data) {

        Log.i(TAG ,String.format("send: %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X",
            data[0],data[1],data[2],data[3],data[4],data[5],data[6],data[7],data[8],data[9]));

        FileOutputStream fileOutputStream = mSerial.getFileOutputStream();
        if(fileOutputStream == null){

            return -1 ;
        }
        try {


            fileOutputStream.write(data);

            return -2 ;

        } catch (IOException e) {
            e.printStackTrace();

            return -3;
        }
    }
}
