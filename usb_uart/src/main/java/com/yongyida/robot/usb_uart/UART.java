package com.yongyida.robot.usb_uart;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import com.yongyida.robot.utils.LogHelper;

import cn.wch.ch34xuartdriver.CH34xUARTDriver;

/**
 * Created by HuangXiangXiang on 2018/4/18.
 */
public class UART {

    private static final String TAG = UART.class.getSimpleName() ;

    private static final String ACTION_USB_PERMISSION = "com.yongyida.robot.USB_PERMISSION";

    private static UART mUART ;
    public static UART getInstance(Context context){

        if(mUART == null){

            mUART = new UART(context.getApplicationContext()) ;
        }

        return mUART ;
    }

    private Context mContext ;
    private UsbManager mUsbManager ;
    private CH34xUARTDriver mDriver ;
    private boolean isOpen ;

    private UART(Context context){

        mContext = context ;

        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        mDriver = new CH34xUARTDriver(mUsbManager, context,ACTION_USB_PERMISSION) ;

        boolean isSupported = mDriver.UsbFeatureSupported();
        LogHelper.i(TAG, LogHelper.__TAG__() + ", isSupported " + isSupported);
    }


    /**打开设备*/
    public boolean openDevice(OpenDeviceListener openDeviceListener) {

        mOpenDeviceListener = openDeviceListener ;
        if (isOpen){

            // 已经打开
            return true;
        }

        UsbDevice usbDevice = mDriver.EnumerateDevice() ;
        if(usbDevice == null){

            return false ;
        }


        boolean hasPermission = mUsbManager.hasPermission(usbDevice) ;
        LogHelper.i(TAG, LogHelper.__TAG__() + ", hasPermission: " + hasPermission);

        if(hasPermission){

            return openDevice() ;

        }else {

            PendingIntent pi = PendingIntent.getBroadcast(this.mContext, 0, new Intent(ACTION_USB_PERMISSION), 0);
            registerReceiver() ;
            this.mUsbManager.requestPermission(usbDevice, pi);

        }
        return false ;
    }

    /**关闭设备*/
    public void closeDevice() {

        this.mDriver.CloseDevice();
        this.isOpen = false ;

        unRegisterReceiver() ;

        stopRead() ;
    }

    public OpenDeviceListener mOpenDeviceListener ;
    public interface OpenDeviceListener{

        void onOpenDevice(boolean isOpen) ;
    }

    /**写数据*/
    public boolean writeData(byte[] data){

        LogHelper.i(TAG, LogHelper.__TAG__() + ", isOpen " + isOpen);
        LogHelper.i(TAG, LogHelper.__TAG__() + ", data " + toHexString(data,data.length));
        if(isOpen){

            int result = this.mDriver.WriteData(data, data.length) ;//小于0 表示失败
            LogHelper.i(TAG, LogHelper.__TAG__() + ", result " + result);
            if(result >= 0){

                return true ;
            }
        }else{

            openDevice(mOpenDeviceListener) ;
        }

        return false ;
    }


    private void startRead(){

        if(mReadThread == null || !mReadThread.isRun){

            mReadThread = new ReadThread() ;
            mReadThread.start();
        }
    }

    private void stopRead(){

        if(mReadThread != null && mReadThread.isRun){

            mReadThread.stopRun();
            mReadThread = null ;

        }
    }


    private ReadThread mReadThread ;
    /**读取数据*/
    private class ReadThread extends Thread{

        private boolean isRun ;

        private byte[] buffer = new byte[64];


        public ReadThread(){

            isRun = true ;
        }

        @Override
        public void run() {

            while (isRun && isOpen){

                int length = mDriver.ReadData(buffer, 64) ;
                if(length > 0){

//                    LogHelper.i(TAG, LogHelper.__TAG__() + ", length : " + length + ", data : " + toHexString(buffer, length));

                }
            }

            isRun = false ;
        }

        private void stopRun(){

            isRun = false ;
        }

    }


    private boolean isRegisterReceiver = false ;

    /***/
    private void registerReceiver(){

        if(!isRegisterReceiver){

            IntentFilter filter = new IntentFilter();
            filter.addAction(ACTION_USB_PERMISSION);

            mContext.registerReceiver(mReceiver, filter) ;

            isRegisterReceiver = true ;
        }
    }


    /***/
    private void unRegisterReceiver(){

        if(isRegisterReceiver){

            mContext.unregisterReceiver(mReceiver);

            isRegisterReceiver = false ;
        }
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            LogHelper.i(TAG, LogHelper.__TAG__() + ", action : " + action);

            if(ACTION_USB_PERMISSION.equals(action)){

                mDriver.CloseDevice();

                openDevice() ;

            }



        }
    };


    private boolean openDevice(){

        int result = mDriver.ResumeUsbList() ;// ResumeUsbList方法用于枚举CH34X设备以及打开相关设备
        LogHelper.i(TAG, LogHelper.__TAG__() + ", result " + result);
        if(result == -1){
            //打开失败

        }else if(result == 0){
            // 获取设备列表成功

            boolean isResult = mDriver.UartInit();
            if(isResult){

                isOpen = true ;

                if(mOpenDeviceListener != null){

                    mOpenDeviceListener.onOpenDevice(isOpen);
                }

                final int baudRate = 115200 ;
                final byte dataBit = 8 ;
                final byte stopBit = 1;
                final byte parity = 0;
                final byte flowControl = 0;
                isResult = mDriver.SetConfig(baudRate, dataBit, stopBit, parity, flowControl) ;
                LogHelper.i(TAG, LogHelper.__TAG__() + ", isResult " + isResult);

                startRead() ;   //开启监听

                return true ;
            }

        }else {

            // 未授权限
        }

        return false ;
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

        String format = "0x%02X" ;
        StringBuilder sb = new StringBuilder() ;
        for (int i= 0 ; i < length-1 ; i++){

            sb.append(String.format(format, data[i]) + " ") ;
        }

        sb.append(String.format(format, data[length - 1])) ;

        return sb.toString() ;
    }

}
