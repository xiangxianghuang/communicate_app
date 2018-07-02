package com.yongyida.robot.usb_uart;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.usb.IUsbManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;

import com.yongyida.robot.utils.LogHelper;

import java.util.ArrayList;
import java.util.HashMap;

import cn.wch.ch34xuartdriver.CH34xUARTDriver;

/**
 * Created by HuangXiangXiang on 2018/4/18.
 * USB 转串口
 */
public class UART {

    private static final String TAG = UART.class.getSimpleName() ;

    private static final String ACTION_USB_PERMISSION = "com.yongyida.robot.USB_PERMISSION";

    private static UART mInstance ;
    public static UART getInstance(Context context){

        if(mInstance == null){

            mInstance = new UART(context.getApplicationContext()) ;
        }

        return mInstance ;
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

        openDevice(null) ;
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

            registerReceiver() ;
            if(!requestUsbPermissionWithDialog(mContext,usbDevice)){

                requestUsbPermission(mContext, usbDevice) ;
            }

        }
        return false ;
    }

    private void requestUsbPermission(Context context,UsbDevice usbDevice){

        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
        this.mUsbManager.requestPermission(usbDevice, pi);
    }

    /**
     * 请求打开USB权限,如果使用和系统相同签名可以不用弹出确认对话框
     * 并且需要 android.permission.MANAGE_USB 权限
     * */
    private boolean requestUsbPermissionWithDialog(Context context,UsbDevice usbDevice){

        LogHelper.i(TAG , LogHelper.__TAG__()) ;

        final PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo aInfo = pm.getApplicationInfo(context.getPackageName(),0);

            IBinder b = ServiceManager.getService(Context.USB_SERVICE);
            IUsbManager service = IUsbManager.Stub.asInterface(b);
            service.grantDevicePermission(usbDevice, aInfo.uid);    // uid 用于对某个ID复制权限

            Intent intent = new Intent(ACTION_USB_PERMISSION);
            context.sendBroadcast(intent);

            return  true ;

        }catch (Exception e){

            e.printStackTrace();
            LogHelper.e(TAG , LogHelper.__TAG__() + " Exception : " + e);
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
            try {
                Thread.sleep(10);   // 防止数据发送过快造成异常
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

        private byte[] buffer = new byte[256];


        public ReadThread(){

            isRun = true ;
        }

        @Override
        public void run() {

            LogHelper.i(TAG, LogHelper.__TAG__()) ;

            while (isRun && isOpen){

                int length = mDriver.ReadData(buffer, buffer.length) ;
                if(length > 0){

                    LogHelper.i(TAG, LogHelper.__TAG__() + ", length : " + length + ", data : " + toHexString(buffer, length));
                    parseData(buffer, length) ;

                }
            }

            isRun = false ;
        }

        private void stopRun(){

            isRun = false ;
        }

    }


    private static final byte CB_ID_VERSION             = 0x00 ;
    private static final byte CB_ID_PING                = 0x01 ;
    private static final byte CB_DEV_STATE              = 0x02 ;
    private static final byte CB_ID_SENSOR              = 0x03 ;
    private static final byte CB_DEV_LEFT_ARM_ANGLE     = 0x04 ;
    private static final byte CB_DEV_RIGHT_ARM_ANGLE    = 0x05 ;
    private static final byte CB_DEV_HEAD_ANGLE         = 0x06 ;
    private static final byte CB_DEV_LEFT_PALM_ANGLE    = 0x07 ;
    private static final byte CB_DEV_RIGHT_PALM_ANGLE   = 0x08 ;


    private int leftArm0 ;
    private int leftArm1 ;
    private int leftArm2 ;
    private int leftArm3 ;
    private int leftArm4 ;
    private int leftArm5 ;

    private int rightArm0 ;
    private int rightArm1 ;
    private int rightArm2 ;
    private int rightArm3 ;
    private int rightArm4 ;
    private int rightArm5 ;


    private int leftPalm0 ;
    private int leftPalm1 ;
    private int leftPalm2 ;
    private int leftPalm3 ;
    private int leftPalm4 ;

    private int rightPalm0 ;
    private int rightPalm1 ;
    private int rightPalm2 ;
    private int rightPalm3 ;
    private int rightPalm4 ;


    public int[] leftArms = new int[6];
    public int[] rightArms = new int[6];
    public int[] leftFingers = new int[5];
    public int[] rightFingers = new int[5];

    public static byte getCheck(byte[] data , int length){

        byte check = 0 ;
        length = length  - 1 ;
        for (int i = 2 ; i < length ; i++){

            check ^= data[i] ;
        }

        return check;
    }

    float headTotal = 0  ;
    float headError = 0  ;

    float checkTotal = 0  ;
    float checkError = 0  ;

    private void parseData(byte[] data, int length){

        headTotal++ ;
        if((data[0] != (byte)0xAA) || data[1] != (byte)0x55){

            headError++ ;
            LogHelper.w(TAG, LogHelper.__TAG__() + "数据头错误"+(headError/headTotal)+", length : " + length + ", data : " + toHexString(data, length));
            return;
        }
        checkTotal++ ;
        if(getCheck(data, length) != data[length-1] ){
            checkError++ ;
            LogHelper.w(TAG, LogHelper.__TAG__() + "校验异常比率 : "+(checkError/checkTotal)+", length : " + length + ", data : " + toHexString(data, length));
            return;
        }

        switch (data[4]){

            case CB_ID_VERSION:

                break;
            case CB_ID_PING:

                break;
            case CB_DEV_STATE:

                break;
            case CB_ID_SENSOR:

                byte state = data[5] ;  // 00 表示 舵机板正常  ; 01 表示 右手 ; 02 表示 左手
                LogHelper.i(TAG, LogHelper.__TAG__() + ", length : " + length + ", data : " + toHexString(data, length));

                break;
            case CB_DEV_LEFT_ARM_ANGLE:

                state = data[5] ;// 左手运行状态 00 表示正常； 01表示未知错误（一般舵机未知错误）；02 无响应
                int leftArm0 = byte2Int(data[6] , data[7]) ;
                int leftArm1 = byte2Int(data[8] , data[9]) ;
                int leftArm2 = byte2Int(data[10] , data[11]);
                int leftArm3 = byte2Int(data[12] , data[13]) ;
                int leftArm4 = byte2Int(data[14] , data[15]);
                int leftArm5 = byte2Int(data[16] , data[17]);

                if(this.leftArm0 != leftArm0 || this.leftArm1 != leftArm1 || this.leftArm2 != leftArm2 ||
                        this.leftArm3 != leftArm3 || this.leftArm4 != leftArm4 || this.leftArm5 != leftArm5 ){

                    leftArms[0] = this.leftArm0 = leftArm0 ;
                    leftArms[1] = this.leftArm1 = leftArm1 ;
                    leftArms[2] = this.leftArm2 = leftArm2 ;
                    leftArms[3] = this.leftArm3 = leftArm3 ;
                    leftArms[4] = this.leftArm4 = leftArm4 ;
                    leftArms[5] = this.leftArm5 = leftArm5 ;

//                    LogHelper.i(TAG, LogHelper.__TAG__() + "左臂运行状态：" + state +
//                            ", leftArm0 : " + leftArm0 + ", leftArm1 : " + leftArm1 +
//                            ", leftArm2 : " + leftArm2 + ", leftArm3 : " + leftArm3 +
//                            ", leftArm4 : " + leftArm4 + ", leftArm5 : " + leftArm5  );

                    if(mHandAngleChangedListener != null){

                        mHandAngleChangedListener.onLeftArmsChanged(state, leftArms);
                    }
                }

                break;
            case CB_DEV_RIGHT_ARM_ANGLE:

                state = data[5] ;// 右手运行状态 00 表示正常； 01表示未知错误（一般舵机未知错误）；02 无响应
                int rightArm0 = byte2Int(data[6] , data[7]) ;
                int rightArm1 = byte2Int(data[8] , data[9]) ;
                int rightArm2 = byte2Int(data[10] , data[11]);
                int rightArm3 = byte2Int(data[12] , data[13]) ;
                int rightArm4 = byte2Int(data[14] , data[15]);
                int rightArm5 = byte2Int(data[16] , data[17]);

                if(this.rightArm0 != rightArm0 || this.rightArm1 != rightArm1 || this.rightArm2 != rightArm2 ||
                        this.rightArm3 != rightArm3 || this.rightArm4 != rightArm4 || this.rightArm5 != rightArm5 ){

                    rightArms[0] = this.rightArm0 = rightArm0 ;
                    rightArms[1] = this.rightArm1 = rightArm1 ;
                    rightArms[2] = this.rightArm2 = rightArm2 ;
                    rightArms[3] = this.rightArm3 = rightArm3 ;
                    rightArms[4] = this.rightArm4 = rightArm4 ;
                    rightArms[5] = this.rightArm5 = rightArm5 ;


//                    LogHelper.i(TAG, LogHelper.__TAG__() + "右臂运行状态：" + state +
//                            ", rightArm0 : " + rightArm0 + ", rightArm1 : " + rightArm1 +
//                            ", rightArm2 : " + rightArm2 + ", rightArm3 : " + rightArm3 +
//                            ", rightArm4 : " + rightArm4 + ", rightArm5 : " + rightArm5  );

                    if(mHandAngleChangedListener != null){

                        mHandAngleChangedListener.onRightArmsChanged(state, rightArms);
                    }
                }


                break;
            case CB_DEV_HEAD_ANGLE: // 头部值（先左右，后前后）

                state = data[5] ;
                int headLeftRight = byte2Int(data[6], data[7]) ;
                int headUpDown = byte2Int(data[8], data[9]) ;

                LogHelper.i(TAG, LogHelper.__TAG__() + "头部运行状态：" + state +
                        ", headLeftRight : " + headLeftRight + ", headUpDown : " + headUpDown );

                break;
            case CB_DEV_LEFT_PALM_ANGLE:

                state = data[5] ;// 左手指 运行状态 00 表示正常； 01表示未知错误（一般舵机未知错误）；02 无响应
                int leftPalm0 = byte2Int(data[6] , data[7]) ;
                int leftPalm1 = byte2Int(data[8] , data[9]) ;
                int leftPalm2 = byte2Int(data[10] , data[11]);
                int leftPalm3 = byte2Int(data[12] , data[13]) ;
                int leftPalm4 = byte2Int(data[14] , data[15]);


                if(this.leftPalm0 != leftPalm0 || this.leftPalm1 != leftPalm1 || this.leftPalm2 != leftPalm2 ||
                        this.leftPalm3 != leftPalm3 || this.leftPalm4 != leftPalm4 ){

                    leftFingers[0] = this.leftPalm0 = leftPalm0 ;
                    leftFingers[1] = this.leftPalm1 = leftPalm1 ;
                    leftFingers[2] = this.leftPalm2 = leftPalm2 ;
                    leftFingers[3] = this.leftPalm3 = leftPalm3 ;
                    leftFingers[4] = this.leftPalm4 = leftPalm4 ;


                    LogHelper.i(TAG, LogHelper.__TAG__() + "左指运行状态：" + state +
                            ", leftPalm0 : " + leftPalm0 + ", leftPalm1 : " + leftPalm1 +
                            ", leftPalm2 : " + leftPalm2 + ", leftPalm3 : " + leftPalm3 +
                            ", leftPalm4 : " + leftPalm4 );

                    if(mHandAngleChangedListener != null){

                        mHandAngleChangedListener.onLeftFingersChanged(state, leftFingers);
                    }
                }



                break ;

            case CB_DEV_RIGHT_PALM_ANGLE:

                state = data[5] ;// 右手指 运行状态 00 表示正常； 01表示未知错误（一般舵机未知错误）；02 无响应
                int rightPalm0 = byte2Int(data[6] , data[7]) ;
                int rightPalm1 = byte2Int(data[8] , data[9]) ;
                int rightPalm2 = byte2Int(data[10] , data[11]);
                int rightPalm3 = byte2Int(data[12] , data[13]) ;
                int rightPalm4 = byte2Int(data[14] , data[15]);


                if(this.rightPalm0 != rightPalm0 || this.rightPalm1 != rightPalm1 || this.rightPalm2 != rightPalm2 ||
                        this.rightPalm3 != rightPalm3 || this.rightPalm4 != rightPalm4 ){

                    rightFingers[0] = this.rightPalm0 = rightPalm0 ;
                    rightFingers[1] = this.rightPalm1 = rightPalm1 ;
                    rightFingers[2] = this.rightPalm2 = rightPalm2 ;
                    rightFingers[3] = this.rightPalm3 = rightPalm3 ;
                    rightFingers[4] = this.rightPalm4 = rightPalm4 ;

                    LogHelper.i(TAG, LogHelper.__TAG__() + "右指运行状态：" + state +
                            ", rightPalm0 : " + rightPalm0 + ", rightPalm1 : " + rightPalm1 +
                            ", rightPalm2 : " + rightPalm2 + ", rightPalm3 : " + rightPalm3 +
                            ", rightPalm4 : " + rightPalm4 );

                    if(mHandAngleChangedListener != null){

                        mHandAngleChangedListener.onRightFingersChanged(state, rightFingers);
                    }
                }

                break;

            default:
                break;

        }


    }


    private int byte2Int(byte b1 , byte b2){

        return  ((b1 & 0xFF) << 8) | (b2 & 0xFF ) ;
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

        String format = "%02X" ;
        StringBuilder sb = new StringBuilder() ;
        for (int i= 0 ; i < length-1 ; i++){

            sb.append(String.format(format, data[i]) + " ") ;
        }

        sb.append(String.format(format, data[length - 1])) ;

        return sb.toString() ;
    }



    private HandAngleChangedListener mHandAngleChangedListener ;
    public void setHandAngleChangedListener(HandAngleChangedListener handAngleChangedListener){

        this.mHandAngleChangedListener = handAngleChangedListener ;
    }
    public interface HandAngleChangedListener{

        void onLeftArmsChanged(int state, int[] leftArms) ;

        void onRightArmsChanged(int state, int[] rightArms) ;

        void onLeftFingersChanged(int state, int[] leftFingers) ;

        void onRightFingersChanged(int state, int[] leftRights) ;
    }

}
