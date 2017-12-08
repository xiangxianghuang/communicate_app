package com.yongyida.robot.model.y128.serial;


import android.util.Log;

import com.yongyida.robot.utils.YYDUart;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Huangxiangxiang on 2017/8/11.
 */
public class Serial implements ISerial {

    public static final String TAG = Serial.class.getSimpleName() ;

    public static final String SERIAL_PORT = "/dev/ttyS2";

    private FileDescriptor mSensorFd ;
    private FileOutputStream mFileOutputStream ;


    public FileDescriptor getSensorFd() {

        return mSensorFd;
    }

    public FileOutputStream getFileOutputStream() {

        return mFileOutputStream;
    }

    @Override
    public int open() {

        if(mSensorFd == null){

            try {

                mSensorFd = YYDUart.openUart(SERIAL_PORT, 115200, 8, 0, 1, 0);

            }catch (Exception e){
                Log.e(TAG ,"打开串口异常：" + e.getMessage()) ;
                return -1;

            }
        }

        if(mSensorFd == null){

            Log.e(TAG ,"打开串口失败")  ;
            return -2 ;
        }

        if(mFileOutputStream == null){

            try {

                mFileOutputStream = new FileOutputStream(mSensorFd);

            }catch (Exception e){


                return -3 ;
            }
        }

        return -4 ;
    }

    @Override
    public int close() {

        if(mFileOutputStream  != null){

            try {
                mFileOutputStream.close();
                mFileOutputStream = null ;
            } catch (IOException e) {
                e.printStackTrace();
                return -1 ;
            }
        }

        if(mSensorFd != null){

            try{

                YYDUart.closeUart(mSensorFd);
                mSensorFd = null ;

            }catch (Exception e){

                return -2 ;
            }
        }

        return -3 ;
    }
}
