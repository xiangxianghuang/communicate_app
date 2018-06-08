package com.yongyida.robot.model.agreement;

import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.battery.BatteryHandler;
import com.yongyida.robot.communicate.app.hardware.touch.TouchHandler;
import com.yongyida.robot.communicate.app.hardware.touch.response.data.TouchInfo;
import com.yongyida.robot.model.y128.motion.Y128MotionHandler;
import com.yongyida.robot.serial.SerialReceive;

/**
 * Created by HuangXiangXiang on 2018/3/30.
 */
public class Y128Receive {

    private static final String TAG = Y128Receive.class.getSimpleName() ;

    private static Y128Receive ourInstance ;
    public static Y128Receive getInstance() {

        if(ourInstance == null){
            ourInstance = new Y128Receive() ;
        }
        return ourInstance;
    }

    private Y128Receive(){

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

        switch (data[3]){

            case Y128Steering.FUNCTION_RECEIVE_TOUCH:

                receiveTouch(data) ;
                break;

            case Y128Steering.FUNCTION_RECEIVE_FAULT:

                receiveFault(data) ;
                break;

            case Y128Steering.FUNCTION_RECEIVE_ULTRASONIC:

                receiveUltrasonic(data) ;
                break;
            case Y128Steering.FUNCTION_RECEIVE_OBD_DATA:

                receiverOBDData(data);
                break;

            case Y128Steering.FUNCTION_RECEIVE_SYSTEM_STATE:

                receiveSystemState(data) ;
                break;
        }
    }

    /**触摸信息*/
    private void receiveTouch(byte[] data){

        LogHelper.i(TAG, LogHelper.__TAG__() + String.format("position :  %X" , data[4]));

        if(mOnTouchListener != null) {

            TouchInfo.Point point;
            // 前脑
            if (0x01 == (data[4] & 0x01)) {
                point = TouchInfo.Point.FORE_HEAD;
            }
            // 后脑勺
            else if (0x02 == (data[4] & 0x02)) {
                point = TouchInfo.Point.BACK_HEAD;
            }
            // 右手臂
            else if (0x04 == (data[4] & 0x04)) {

                point = TouchInfo.Point.RIGHT_ARM;
            }
            // 右肩
            else if (0x08 == (data[4] & 0x08)) {

                point = TouchInfo.Point.RIGHT_SHOULDER;
            }
            // 左肩
            else if (0x10 == (data[4] & 0x10)) {

                point = TouchInfo.Point.LEFT_SHOULDER;
            }
            // 左手臂
            else if (0x20 == (data[4] & 0x20)) {

                point = TouchInfo.Point.LEFT_ARM;
            } else {

                point = null;
            }

            if (point != null) {

                mOnTouchListener.onTouchListener(point);
            }
        }
    }

    /**故障信息*/
    private void receiveFault(byte[] data){

        if(this.mOnMoveFaultListener != null){

            int faultCode = data[4] ;
            this.mOnMoveFaultListener.onMoveFault(faultCode);
        }

    }

    /**超声波信息*/
    private void receiveUltrasonic(byte[] data){

        if(mOnUltrasonicChangedListener != null){

            mReceiveUltrasonic.setData(data);

            mOnUltrasonicChangedListener.onUltrasonicChanged(mReceiveUltrasonic);
        }

    }

    private void receiverOBDData(byte []data){

        mOBDData.setData(data);

        LogHelper.i(TAG, LogHelper.__TAG__() + " " + mOBDData .toString());

        if(mOnBatteryChangedListener != null){

            level = mOBDData.getLevel();

            mOnBatteryChangedListener.onBatteryChanged(state, level);
        }

        if(mOnOBDDataChangedListener != null){

            mOnOBDDataChangedListener.onSystemChanged(mOBDData);
        }
    }

    /**
     * 系统信息
     *
     *
     * */
    private void receiveSystemState(byte[] data){

        mReceiveSystemState.setData(data);

        LogHelper.i(TAG, LogHelper.__TAG__() + " " + mReceiveSystemState .toString());

        if(mOnBatteryChangedListener != null){

            state = mReceiveSystemState.getChargeState();

            mOnBatteryChangedListener.onBatteryChanged(state, level);
        }

        if(mOnSystemChangedListener != null){

            mOnSystemChangedListener.onSystemChanged(mReceiveSystemState);
        }


    }

    private int state ;
    private int level ;
    private Y128Steering.ReceiveTouch mReceiveTouch = new Y128Steering.ReceiveTouch();
    private Y128Steering.ReceiveFault mReceiveFault = new Y128Steering.ReceiveFault() ;
    private Y128Steering.ReceiveUltrasonic mReceiveUltrasonic = new Y128Steering.ReceiveUltrasonic() ;
    private Y128Steering.OBDData mOBDData = new Y128Steering.OBDData() ;
    private Y128Steering.ReceiveSystemState mReceiveSystemState = new Y128Steering.ReceiveSystemState();



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


        if(Y128Steering.HEAD_0 != data[0] || Y128Steering.HEAD_1 != data[1]){

            return false ;
        }


        return true ;
    }


    /**
     * 电池信息
     * */
    private BatteryHandler.OnBatteryChangedListener mOnBatteryChangedListener ;
    public void setOnBatteryChangedListener(BatteryHandler.OnBatteryChangedListener onBatteryChangedListener){

        this.mOnBatteryChangedListener = onBatteryChangedListener ;

    }


    /**
     * 触摸信息
     * */
    private TouchHandler.OnTouchListener mOnTouchListener ;
    public void setOnTouchListener(TouchHandler.OnTouchListener onTouchListener) {
        this.mOnTouchListener = onTouchListener;
    }


    private Y128MotionHandler.OnMoveFaultListener mOnMoveFaultListener ;
    public void setOnMoveFaultListener(Y128MotionHandler.OnMoveFaultListener onMoveFaultListener) {
        this.mOnMoveFaultListener = onMoveFaultListener;
    }


    private Y128MotionHandler.OnUltrasonicChangedListener mOnUltrasonicChangedListener ;
    public void setOnUltrasonicChangedListener(Y128MotionHandler.OnUltrasonicChangedListener onUltrasonicChangedListener) {
        this.mOnUltrasonicChangedListener = onUltrasonicChangedListener;
    }

    private Y128MotionHandler.OnSystemChangedListener mOnSystemChangedListener ;
    public void setOnSystemChangedListener(Y128MotionHandler.OnSystemChangedListener onSystemChangedListener) {
        this.mOnSystemChangedListener = onSystemChangedListener;
    }


    private Y128MotionHandler.OnOBDDataChangedListener mOnOBDDataChangedListener ;
    public void setOnOBDDataChangedListener(Y128MotionHandler.OnOBDDataChangedListener onOBDDataChangedListener) {
        this.mOnOBDDataChangedListener = onOBDDataChangedListener;
    }


}
