package com.yongyida.robot.serial;

import com.yongyida.robot.communicate.app.utils.LogHelper;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Huangxiangxiang on 2017/8/11.
 */
public final class SerialSend{

    private static final String TAG = SerialSend.class.getSimpleName() ;

    private static final int RESULT_SEND_SUCCESS        = 0x00 ;
    private static final int RESULT_SEND_FAIL           = 0x01 ;
    private static final int RESULT_SEND_EXCEPTION      = 0x02 ;


    private static SerialSend ourInstance ;
    public static SerialSend getInstance() {

        if(ourInstance == null){
            ourInstance = new SerialSend() ;
        }
        return ourInstance;
    }


    private Serial mSerial ;

    private SerialSend(){

        mSerial = Serial.getSerial() ;
        open() ;
    }

    public void open(){

        mSerial.open() ;

    }

    public void close(){


    }

    private FileOutputStream mFileOutputStream ;

    private FileOutputStream getFileOutputStream(){

        if(mFileOutputStream == null){

            try {

                FileDescriptor sensorFd = mSerial.getSensorFd();
                mFileOutputStream = new FileOutputStream(sensorFd);

            }catch (Exception e){

                LogHelper.e(TAG, LogHelper.__TAG__() + " Exception : " + e.getMessage());
            }
        }

        return mFileOutputStream ;
    }

    public int sendData(byte[] data) {

        FileOutputStream fileOutputStream = getFileOutputStream();
        if(fileOutputStream == null){

            LogHelper.i(TAG, LogHelper.__TAG__() + " fileOutputStream is null!  " + toHexString(data));

            return RESULT_SEND_FAIL ;
        }
        try {

            LogHelper.i(TAG, LogHelper.__TAG__() + " data : " + toHexString(data));

            fileOutputStream.write(data);

        } catch (IOException e) {
            e.printStackTrace();

            LogHelper.e(TAG, LogHelper.__TAG__() + " IOException : " + e.getMessage());
            return RESULT_SEND_EXCEPTION;
        }

        return RESULT_SEND_SUCCESS ;
    }


    public static String toHexString(byte[] data){

        if(data == null){

            return null ;
        }

        return Serial.toHexString(data, data.length) ;
    }




}
