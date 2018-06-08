package com.yongyida.robot.serial;



import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.utils.YYDUart;

import java.io.FileDescriptor;
import java.util.HashMap;

/**
 * Created by Huangxiangxiang on 2017/8/11.
 * 串口信息
 */
public final class Serial {

    public static final String TAG = Serial.class.getSimpleName() ;

    public static final String SERIAL_PORT = "/dev/ttyS2";  // 串口名称
//    public static final String SERIAL_PORT = "/dev/ttyS4";  // 串口名称


    public static final int RESULT_SUCCESS                      = 0x00000000;   //操作成功
    public static final int RESULT_OPEN_SERIAL_EXCEPTION        = 0x00010001;   //打开串口异常
    public static final int RESULT_OPEN_SERIAL_FAIL             = 0x00010002;   //打开串口失败
    public static final int RESULT_CLOSE_SERIAL_EXCEPTION       = 0x00020001;   //打开串口异常

    private static final HashMap<String, Serial> mSerials = new HashMap<>() ;
    public static Serial getSerial(){

        return getSerial(SERIAL_PORT) ;
    }
    public static Serial getSerial(String name){

        Serial serial = mSerials.get(name) ;
        if(serial == null){

            serial = new Serial(name) ;
            mSerials.put(name, serial) ;
        }

        return serial ;
    }


    private String name ;   //串口名称
    private FileDescriptor mSensorFd ;

    private Serial(String name) {

        this.name = name ;
        open() ;
    }

    public FileDescriptor getSensorFd() {

        return mSensorFd;
    }

    /**
     * 打开串口
     * */
    public int open() {

        if(mSensorFd == null){

            try {
                mSensorFd = YYDUart.openUart(name, 115200, 8, 0, 1, 0);

            }catch (Exception e){

                LogHelper.e(TAG ,"打开串口异常：" + e.getMessage()) ;
                return RESULT_OPEN_SERIAL_EXCEPTION;
            }
        }

        if(mSensorFd == null){

            LogHelper.e(TAG ,"打开串口失败")  ;
            return RESULT_OPEN_SERIAL_FAIL ;
        }

        return RESULT_SUCCESS ;
    }

    /**
     * 关闭串口
     * */
    public int close() {

        if(mSensorFd != null){

            try{

                YYDUart.closeUart(mSensorFd);
                mSensorFd = null ;

            }catch (Exception e){

                LogHelper.e(TAG ,"关闭串口异常：" + e.getMessage()) ;
                return RESULT_CLOSE_SERIAL_EXCEPTION ;
            }
        }

        return RESULT_SUCCESS ;
    }



    /**
     * 将byte数字转变成String格式
     * */
    public static String toHexString(byte[] data , int length){

        if(data == null){

            return null ;
        }

        int len = data.length ;
        if(len == 0 || length <= 0){

            return "" ;
        }

        if(length > len){

            length = len ;
        }

        String format = "%02X" ;
        StringBuilder sb = new StringBuilder() ;
        for (int i= 0 ; i < length-1 ; i++){

            sb.append(String.format(format, data[i]) + " ") ;
        }

        sb.append(String.format(format, data[length - 1])) ;

        return sb.toString() ;
    }
}
