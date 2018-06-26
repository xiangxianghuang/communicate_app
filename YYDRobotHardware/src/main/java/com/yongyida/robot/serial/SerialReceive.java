package com.yongyida.robot.serial;


import com.yongyida.robot.communicate.app.utils.LogHelper;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by HuangXiangXiang on 2018/2/24.
 * 负责接收串口数据
 */
public class SerialReceive {

    private static final String TAG = SerialReceive.class.getSimpleName() ;

    private static SerialReceive ourInstance ;
    public static SerialReceive getInstance() {

        if(ourInstance == null){
            ourInstance = new SerialReceive() ;
        }
        return ourInstance;
    }

    private Serial mSerial ;    // 串口对象
    private SerialReceive() {

        mSerial = Serial.getSerial() ;
    }


    /**是否处于监听状态*/
    public boolean isReceive() {

        return  mReceiveThread != null && mReceiveThread.isRun ;
    }


    private ReceiveThread mReceiveThread ;
    public boolean startReceive(){

        if(!isReceive()){

            FileDescriptor fileDescriptor = mSerial.getSensorFd() ;
            if (fileDescriptor == null){

                return false ;
            }

            mReceiveThread = new ReceiveThread(fileDescriptor) ;
            mReceiveThread.start();
        }

        return true ;
    }


    public boolean stopReceive(){

        if(isReceive()){

            mReceiveThread.stopReceive();
            mReceiveThread = null ;
        }

        return true;
    }




    private class ReceiveThread extends Thread{

        private FileInputStream mFileInputStream ;
        private boolean isRun = false ;

        private ReceiveThread(FileDescriptor fileDescriptor){

            mFileInputStream = new FileInputStream(fileDescriptor);
            isRun = true ;
        }

        public void stopReceive(){

            if(isRun ){

                isRun = false ;
            }

            if(mFileInputStream != null){

                try {
                    mFileInputStream.close();
                    mFileInputStream = null ;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void run() {

            while (true){

                LogHelper.i(TAG, LogHelper.__TAG__() + " start receive " ) ;
                byte[] data = new byte[100];
                int length = 0 ;
                try {
                    if(mFileInputStream != null){

                        length = mFileInputStream.read(data) ;
                    }

                    if(!isRun){
                        return;
                    }

                    if(mOnReceiveListener != null){

                        mOnReceiveListener.onReceive(data);
                    }


                } catch (IOException e) {
                    LogHelper.e(TAG , LogHelper.__TAG__() + " receive IOException " + e.getMessage() ) ;
                    e.printStackTrace();
                }

                LogHelper.i(TAG, LogHelper.__TAG__() + Serial.toHexString(data, length));

            }

        }
    }


    private OnReceiveListener mOnReceiveListener ;
    public void setOnReceiveListener(OnReceiveListener onReceiveListener){

        this.mOnReceiveListener = onReceiveListener ;
    }


    public interface OnReceiveListener{

        void onReceive(byte[] data);

    }



}
