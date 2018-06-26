package com.yongyida.robot.model.agreement;

import com.yongyida.robot.communicate.app.hardware.battery.BatterySendHandlers;
import com.yongyida.robot.model.y165.pir.Y165PirSendHandlers;
import com.yongyida.robot.serial.SerialReceive;

/**
 * Created by HuangXiangXiang on 2018/3/30.
 */
public class Y165Receive {

    private static final String TAG = Y165Receive.class.getSimpleName() ;

    private static Y165Receive ourInstance ;
    public static Y165Receive getInstance() {

        if(ourInstance == null){
            ourInstance = new Y165Receive() ;
        }
        return ourInstance;
    }

    private Y165Receive(){

        SerialReceive serialReceive = SerialReceive.getInstance() ;
        serialReceive.setOnReceiveListener(mOnReceiveListener);
        serialReceive.startReceive() ;
    }


    private SerialReceive.OnReceiveListener mOnReceiveListener = new SerialReceive.OnReceiveListener(){
        @Override
        public void onReceive(byte[] data) {

            receive(data) ;
        }
    };


    private void receive(byte[] data){

        if (isValid(data)){

            // 无效数据直接跳出
            return;
        }

        switch (data[2]){

            case 0x06:      //电量

                int status = data[4] ;
                int level = data[5] ;
//
//                if(mOnBatteryChangedListener != null){
//
//                    mOnBatteryChangedListener.onBatteryChanged(status, level);
//                }

                break;

            case 0x07:      //人体感应

                int distance = (((0xFF&data[4])<<8) | (0xFF&data[5])) / 2 ;

                if(mOnMonitorPersonListener != null){

                    mOnMonitorPersonListener.onMonitorPerson(distance);
                }


                break;
        }
    }





    /**
     * 是否属于有效的数据
     * */
    private boolean isValid(byte[] data){

        if(data == null){

            return true ;
        }

        final int length = data.length ;
        for (int i = 0 ; i < length ; i++ ){

            if (data[i] != 0x00){

                return false ;
            }
        }


        if((0xAA != data[0]) || (0xBB != data[1])){

            return false ;
        }


        return true ;
    }


//    /**
//     * 电池信息
//     * */
//    private BatterySendHandlers.OnBatteryChangedListener mOnBatteryChangedListener ;
//    public void setOnBatteryChangedListener(BatterySendHandlers.OnBatteryChangedListener onBatteryChangedListener){
//
//        this.mOnBatteryChangedListener = onBatteryChangedListener ;
//
//    }


    private Y165PirSendHandlers.OnMonitorPersonListener mOnMonitorPersonListener ;
    public void setOnMonitorPersonListener(Y165PirSendHandlers.OnMonitorPersonListener onMonitorPersonListener) {

        this.mOnMonitorPersonListener = onMonitorPersonListener ;
    }



}
