package com.yongyida.robot.model.agreement;

import com.yongyida.robot.communicate.app.hardware.battery.control.QueryBatteryControlHandler;
import com.yongyida.robot.communicate.app.hardware.battery.response.data.BatteryInfo;
import com.yongyida.robot.communicate.app.hardware.pir.control.QueryPirValueControlHandler;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.utils.StringUtils;
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
        public void onReceive(byte[] data, int length) {

            receive(data, length) ;
        }
    };

    private BatteryInfo mBatteryInfo = new BatteryInfo() ;
    private static final int STATE_CHARGING = 0x00 ;        // 充电返回状态码

    private void receive(byte[] data, int length){

        LogHelper.i(TAG, LogHelper.__TAG__() + ""+StringUtils.bytes2HexString(data, length));
        if (isValid(data)){

            // 无效数据直接跳出
            return;
        }

        switch (data[2]){

            case 0x06:      //电量

                mBatteryInfo.setCharging(data[4] == STATE_CHARGING);
                mBatteryInfo.setLevel(data[5]);
                mBatteryInfo.setState(data[4]);

                if(mOnBatteryChangeListener != null){

                    mOnBatteryChangeListener.onBatteryChange(mBatteryInfo);
                }

                break;

            case 0x07:      //人体感应

                if(mOnPirValueChangedListener != null){

                    int distance = (((0xFF&data[4])<<8) | (0xFF&data[5])) / 2 ;
                    mOnPirValueChangedListener.onPirValueChanged(true, distance);
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



    /**电池信息*/
    private QueryBatteryControlHandler.OnBatteryChangeListener mOnBatteryChangeListener ;
    public void setOnBatteryChangeListener(QueryBatteryControlHandler.OnBatteryChangeListener onBatteryChangeListener) {
        this.mOnBatteryChangeListener = onBatteryChangeListener;
    }


    /**Pir 信息*/
    private QueryPirValueControlHandler.OnPirValueChangedListener mOnPirValueChangedListener;
    public void setOnPirValueChangedListener(QueryPirValueControlHandler.OnPirValueChangedListener onPirValueChangedListener) {
        this.mOnPirValueChangedListener = onPirValueChangedListener;
    }
}
