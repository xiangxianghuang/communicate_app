package com.yongyida.robot.model.agreement;

import com.yongyida.robot.communicate.app.hardware.battery.control.QueryBatteryControlHandler;
import com.yongyida.robot.communicate.app.hardware.battery.response.data.BatteryInfo;
import com.yongyida.robot.communicate.app.hardware.motion.control.QueryMotionSystemControlHandler;
import com.yongyida.robot.communicate.app.hardware.motion.control.QueryUltrasonicControlHandler;
import com.yongyida.robot.communicate.app.hardware.motion.response.data.MotionSystemHistory;
import com.yongyida.robot.communicate.app.hardware.touch.TouchSendHandlers;
import com.yongyida.robot.communicate.app.hardware.touch.control.QueryTouchPositionControlHandler;
import com.yongyida.robot.communicate.app.hardware.touch.response.data.TouchPosition;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.utils.StringUtils;
import com.yongyida.robot.serial.SerialReceive;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
        public void onReceive(byte[] data, int length) {

            receive(data,length) ;
        }
    };


    private void receive(byte[] data, int length){

        LogHelper.i(TAG, LogHelper.__TAG__() + StringUtils.bytes2HexString(data,length));
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

                receiveUltrasonic(data,length) ;
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

        if(onTouchPositionListener != null) {

            TouchPosition.Position point;
            // 前脑
            if (equal(data[4], 0x01)) {
                point = TouchPosition.Position.FORE_HEAD;
            }
            // 后脑勺
            else if (equal(data[4], 0x02)) {
                point = TouchPosition.Position.BACK_HEAD;
            }
            // 右手臂
            else if (equal(data[4], 0x04)) {

                point = TouchPosition.Position.RIGHT_ARM;
            }
            // 右肩
            else if (equal(data[4], 0x08)) {

                point = TouchPosition.Position.RIGHT_SHOULDER;
            }
            // 左肩
            else if (equal(data[4], 0x10)) {

                point = TouchPosition.Position.LEFT_SHOULDER;
            }
            // 左手臂
            else if (equal(data[4], 0x20)) {

                point = TouchPosition.Position.LEFT_ARM;
            } else {

                point = null;
            }

            if (point != null) {

                onTouchPositionListener.onTouchPosition(point);
            }
        }
    }

    private boolean equal(int i1 , int i2){

        return  i2 == (i1 & i2) ;
    }

    /**故障信息*/
    private void receiveFault(byte[] data){

        String title = "运动故障信息" ;
        String value = Y128Steering.ReceiveFault.getFaultMessages(data[4]) ;
        boolean isAdd = addHistory(title, value);

        if(isAdd && mOnMotionSystemChangedListener != null){

            mOnMotionSystemChangedListener.onMotionSystemChanged( title, value);
        }
    }

    /**超声波信息*/
    private void receiveUltrasonic(byte[] data, int length){

        LogHelper.i(TAG, LogHelper.__TAG__() + " " + StringUtils.bytes2HexString(data,length));

        if(mOnUltrasonicChangedListener != null){

            mReceiveUltrasonic.setData(data);
            mOnUltrasonicChangedListener.onUltrasonicChanged(mReceiveUltrasonic.getDistances());
        }

    }

    private void receiverOBDData(byte []data){

        mOBDData.setData(data);

        LogHelper.i(TAG, LogHelper.__TAG__() + " " + mOBDData .toString());

        if(mOnBatteryChangeListener != null){

            mBatteryInfo.setLevel(mOBDData.getLevel());
            mOnBatteryChangeListener.onBatteryChange(mBatteryInfo);
        }

        String title = "OBD信息" ;
        String value = mOBDData.toString() ;
        boolean isAdd = addHistory(title, value);

        if(isAdd && mOnMotionSystemChangedListener != null){

            mOnMotionSystemChangedListener.onMotionSystemChanged( title, value);
        }
    }

    /**
     * 系统信息
     * 其中包括电池信息
     *
     * */
    private void receiveSystemState(byte[] data){

        mReceiveSystemState.setData(data);

        LogHelper.i(TAG, LogHelper.__TAG__() + " " + mReceiveSystemState .toString());

        if(mOnBatteryChangeListener != null){

            mBatteryInfo.setCharging(mReceiveSystemState.isCharging());
            mBatteryInfo.setState(mReceiveSystemState.getChargeState());

            mOnBatteryChangeListener.onBatteryChange(mBatteryInfo);
        }



        String title = "系统信息" ;
        String value = mReceiveSystemState.toString() ;
        boolean isAdd = addHistory(title, value);

        if(isAdd && mOnMotionSystemChangedListener != null){

            mOnMotionSystemChangedListener.onMotionSystemChanged( title, value);
        }

    }

    private BatteryInfo mBatteryInfo = new BatteryInfo() ;
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
    private QueryBatteryControlHandler.OnBatteryChangeListener mOnBatteryChangeListener ;
    public void setOnBatteryChangeListener(QueryBatteryControlHandler.OnBatteryChangeListener onBatteryChangeListener) {

        this.mOnBatteryChangeListener = onBatteryChangeListener;
    }

    /**
     * 触摸信息
     * */
    private QueryTouchPositionControlHandler.OnTouchPositionListener onTouchPositionListener ;
    public void setOnTouchPositionListener(QueryTouchPositionControlHandler.OnTouchPositionListener onTouchPositionListener) {
        this.onTouchPositionListener = onTouchPositionListener;
    }

    /**
     * 超声波信息
     * */
    private QueryUltrasonicControlHandler.OnUltrasonicChangedListener mOnUltrasonicChangedListener ;
    public void setOnUltrasonicChangedListener(QueryUltrasonicControlHandler.OnUltrasonicChangedListener onUltrasonicChangedListener) {
        this.mOnUltrasonicChangedListener = onUltrasonicChangedListener;
    }

    /**
     * 系统信息
     * */
    private QueryMotionSystemControlHandler.OnMotionSystemChangedListener mOnMotionSystemChangedListener ;
    public void setOnMotionSystemChangedListener(QueryMotionSystemControlHandler.OnMotionSystemChangedListener onMotionSystemChangedListener) {
        this.mOnMotionSystemChangedListener = onMotionSystemChangedListener;
    }


    private static final int MAX_SIZE = 100 ;   //历史记录最大条数
    private final HashMap<String, ArrayList<MotionSystemHistory.History>> historyMap  = new HashMap<>() ;

    /**
     * 增加历史记录
     * */
    private boolean addHistory(String title, String value){

        if(value != null){

            ArrayList<MotionSystemHistory.History> histories = historyMap.get(title) ;
            if(histories == null || histories.isEmpty()){

                histories = new ArrayList<>() ;
                MotionSystemHistory.History history = new MotionSystemHistory.History(value) ;
                histories.add(history) ;

                historyMap.put(title, histories) ;

                return true ;

            }else{

                MotionSystemHistory.History last = histories.get(histories.size()-1) ;
                if(!value.equals(last.value)){

                    MotionSystemHistory.History history = new MotionSystemHistory.History(value) ;
                    histories.add(history) ;

                    if(histories.size() > MAX_SIZE){

                        histories.remove(0) ;
                    }

                    return true ;
                }
            }
        }

        return false ;
    }

    public ArrayList<MotionSystemHistory.History> getHistories(String title){

        return historyMap.get(title) ;
    }



}
