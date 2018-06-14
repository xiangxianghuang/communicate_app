package com.yongyida.robot.model.y128.motion;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.send.data.BaseSendControl;
import com.hiva.communicate.app.server.IResponseListener;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.motion.MotionHandler;
import com.yongyida.robot.communicate.app.hardware.motion.response.data.MoveFault;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.QueryMoveFaultControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.UltrasonicSendControl;
import com.yongyida.robot.model.agreement.Y128Receive;
import com.yongyida.robot.model.agreement.Y128Steering;

/**
 * Created by HuangXiangXiang on 2018/3/5.
 */
public class Y128MotionHandler extends MotionHandler {

    private static final String TAG = Y128MotionHandler.class.getSimpleName() ;


    private SerialSendHeadLeftRight mSerialSendHeadLeftRight ;
    private SerialSendHeadUpDown mSerialSendHeadUpDown ;
    private SerialSendMove mSerialSendMove ;
    private SerialSoundLocation mSerialSoundLocation ;


    private SerialUltrasonic mSerialUltrasonic ;

    private SlamMotion mSlamMotion ;


    private SparseArray<String> faults = new SparseArray<>() ;
    private MoveFault moveFault ;

    public Y128MotionHandler(Context context) {
        super(context);

        mSerialSendHeadLeftRight = new SerialSendHeadLeftRight() ;
        mSerialSendHeadUpDown = new SerialSendHeadUpDown() ;
        mSerialSendMove = new SerialSendMove() ;
        mSerialSoundLocation = new SerialSoundLocation() ;
        mSerialUltrasonic = new SerialUltrasonic() ;

        mSlamMotion = SlamMotion.getInstance(context);

        Y128Receive serialReceive = Y128Receive.getInstance() ;
        serialReceive.setOnMoveFaultListener(mOnMoveFaultListener);
        serialReceive.setOnUltrasonicChangedListener(onUltrasonicChangedListener);
        serialReceive.setOnSystemChangedListener(mOnSystemChangedListener) ;
        serialReceive.setOnOBDDataChangedListener(mOnOBDDataChangedListener) ;

        faults.put(Y128Steering.ReceiveFault.FAULT_CODE_10, "步科底盘启动不成功" );
        faults.put(Y128Steering.ReceiveFault.FAULT_CODE_20, "传感器板启动不成功或主板与传感器板连接异常" );
        faults.put(Y128Steering.ReceiveFault.FAULT_CODE_21, "传感器板启动后断开连接" );
        faults.put(Y128Steering.ReceiveFault.FAULT_CODE_22, "传感器数据帧接收出错" );
        faults.put(Y128Steering.ReceiveFault.FAULT_CODE_40, "slamware core启动不成功" );
        faults.put(Y128Steering.ReceiveFault.FAULT_CODE_41, "slamware core内部出错" );
        faults.put(Y128Steering.ReceiveFault.FAULT_CODE_50, "电量计初始化不成功" );

    }

    @Override
    public BaseResponse onHandler(MotionSend send, IResponseListener responseListener) {

        BaseSendControl baseSendControl = send.getBaseControl() ;
        LogHelper.i(TAG , LogHelper.__TAG__() );

        if(baseSendControl instanceof QueryMoveFaultControl){

            return moveFault.getResponse() ;
        }

        if(baseSendControl instanceof UltrasonicSendControl){

            UltrasonicSendControl ultrasonicControl = (UltrasonicSendControl) baseSendControl;

            UltrasonicSendControl.Android android = ultrasonicControl.getAndroid() ;
            if(android != null){

                mSerialUltrasonic.setSendAndroidMode(android.ordinal());
            }

            UltrasonicSendControl.Slamware slamware = ultrasonicControl.getSlamware() ;
            if(slamware != null){

                mSerialUltrasonic.setSendSlamwareMode(slamware.ordinal());
            }

            mSerialUltrasonic.sendData();

            return null ;
        }


//        if(baseSendControl instanceof MotionSendControl){
//            MotionSendControl motionControl = (MotionSendControl) baseSendControl;
//
//            MotionSendControl.Action action = motionControl.getAction() ;
//            if(action != null){
//
//                switch (action){
//
//                    case HEAD_LEFT:
//
//                        MotionSendControl.Distance distance = motionControl.getDistance() ;
//                        int value  ;
//                        if(distance != null){
//                            value = distance.getValue() ;
//                        }else {
//
//                            value = 200 ;
//                        }
//                        mSerialSendHeadLeftRight.headLeft(value) ;
//
//                        break;
//                    case HEAD_RIGHT:
//
//                        distance = motionControl.getDistance() ;
//                        if(distance != null){
//
//                            value = distance.getValue() ;
//                        }else {
//                            value = 200 ;
//                        }
//                        mSerialSendHeadLeftRight.headRight(value) ;
//
//                        break;
//                    case HEAD_LEFT_RIGHT_RESET:
//
//                        mSerialSendHeadLeftRight.headLeftRightRest() ;
//                        break;
//
//                    case HEAD_LEFT_RIGHT_LOOP:
//
//                        mSerialSendHeadLeftRight.headLeftRightShark() ;
//                        break;
//
//                    case HEAD_LEFT_RIGHT_STOP:
//
//                        mSerialSendHeadLeftRight.headLeftRightStop() ;
//                        break;
//                    case HEAD_UP:
//
//                        MotionSendControl.Time time = motionControl.getTime() ;
//                        if(time != null){
//
//                            value = time.getValue() ;
//                        }else{
//                            value = 100 ;
//                        }
//                        mSerialSendHeadUpDown.headUp(value) ;
//
//                        break;
//                    case HEAD_DOWN:
//
//                        time = motionControl.getTime() ;
//                        if(time != null){
//
//                            value = time.getValue() ;
//                        }else{
//                            value = 100 ;
//                        }
//                        mSerialSendHeadUpDown.headDown(value) ;
//
//                        break;
//                    case HEAD_UP_DOWN_RESET:
//
//                        mSerialSendHeadUpDown.headUpDownRest() ;
//                        break;
//
//                    case HEAD_UP_DOWN_LOOP:
//
//                        mSerialSendHeadUpDown.headUpDownShark() ;
//                        break;
//
//                    case HEAD_UP_DOWN_STOP:
//
//                        mSerialSendHeadUpDown.headUpDownStop() ;
//                        break;
//                    case FOOT_FORWARD:
//
//                        time = motionControl.getTime() ;
//                        if(time != null){
//
//                            value = time.getValue() ;
//                        }else{
//                            value = 2000 ;
//                        }
//
//                        MotionSendControl.Type type = motionControl.getType();
//                        if(MotionSendControl.Type.SLAM == type) {
//                            mSlamMotion.forward(value);
//
//                        }else if(MotionSendControl.Type.SERIAL == type){
//                            MotionSendControl.Speed speed = motionControl.getSpeed() ;
//                            if(speed != null){
//
//                                mSerialSendMove.setSpeed(speed.getValue());
//                            }
//                            mSerialSendMove.forward(value) ;
//
//
//                        }else{
//                            mSlamMotion.forward(value);
//
//                            MotionSendControl.Speed speed = motionControl.getSpeed() ;
//                            if(speed != null){
//
//                                mSerialSendMove.setSpeed(speed.getValue());
//                            }
//
//                            mSerialSendMove.forward(value) ;
//                        }
//
//
//                        break;
//                    case FOOT_BACK:
//
//                        time = motionControl.getTime() ;
//                        if(time != null){
//
//                            value = time.getValue() ;
//                        }else{
//                            value = 2000 ;
//                        }
//
//                        type = motionControl.getType();
//                        if(MotionSendControl.Type.SLAM == type) {
//                            mSlamMotion.back(value);
//
//                        }else if(MotionSendControl.Type.SERIAL == type){
//                            MotionSendControl.Speed speed = motionControl.getSpeed() ;
//                            if(speed != null){
//
//                                mSerialSendMove.setSpeed(speed.getValue());
//                            }
//                            mSerialSendMove.back(value);
//
//
//                        }else{
//                            mSlamMotion.back(value);
//
//                            MotionSendControl.Speed speed = motionControl.getSpeed() ;
//                            if(speed != null){
//
//                                mSerialSendMove.setSpeed(speed.getValue());
//                            }
//                            mSerialSendMove.back(value);
//                        }
//
//                        break;
//                    case FOOT_LEFT:
//
//                        time = motionControl.getTime() ;
//                        if(time != null){
//
//                            value = time.getValue() ;
//                        }else{
//                            value = 2000 ;
//                        }
//                        type = motionControl.getType();
//                        if(MotionSendControl.Type.SLAM == type) {
//                            mSlamMotion.left(value);
//
//                        }else if(MotionSendControl.Type.SERIAL == type){
//
//                            MotionSendControl.Speed speed = motionControl.getSpeed() ;
//                            if(speed != null){
//
//                                mSerialSendMove.setSpeed(speed.getValue());
//                            }
//                            mSerialSendMove.left(value);
//
//                        }else{
//                            mSlamMotion.left(value);
//
//                            MotionSendControl.Speed speed = motionControl.getSpeed() ;
//                            if(speed != null){
//
//                                mSerialSendMove.setSpeed(speed.getValue());
//                            }
//                            mSerialSendMove.left(value);
//                        }
//
//                        break;
//                    case FOOT_RIGHT:
//
//                        time = motionControl.getTime() ;
//                        if(time != null){
//
//                            value = time.getValue() ;
//                        }else{
//                            value = 2000 ;
//                        }
//
//                        type = motionControl.getType();
//                        if(MotionSendControl.Type.SLAM == type) {
//                            mSlamMotion.right(value);
//
//                        }else if(MotionSendControl.Type.SERIAL == type){
//
//                            MotionSendControl.Speed speed = motionControl.getSpeed() ;
//                            if(speed != null){
//
//                                mSerialSendMove.setSpeed(speed.getValue());
//                            }
//                            mSerialSendMove.right(value);
//
//                        }else{
//                            mSlamMotion.right(value);
//
//                            MotionSendControl.Speed speed = motionControl.getSpeed() ;
//                            if(speed != null){
//
//                                mSerialSendMove.setSpeed(speed.getValue());
//                            }
//                            mSerialSendMove.right(value);
//                        }
//
//                        break;
//                    case FOOT_STOP:
//
//                        type = motionControl.getType();
//                        if(MotionSendControl.Type.SLAM == type) {
//                            mSlamMotion.stop();
//                        }else if(MotionSendControl.Type.SERIAL == type){
//                            mSerialSendMove.stop();
//
//                        }else{
//                            mSlamMotion.stop();
//
//                            mSerialSendMove.stop();
//                        }
//
//                        break;
//
////                    case SOUND_LOCATION_LEFT:
////
////                        distance = motionControl.getDistance() ;
////                        int d ;
////                        if(distance != null){
////                            d = distance.getValue() ;
////                        }else {
////                            d = 0 ;
////                        }
////
////                        mSerialSoundLocation.turnLeft(d);
////
////                        break;
////
////                    case SOUND_LOCATION_RIGHT:
////
////                        distance = motionControl.getDistance() ;
////                        if(distance != null){
////                            d = distance.getValue() ;
////                        }else {
////                            d = 0 ;
////                        }
////
////                        mSerialSoundLocation.turnRight(d);
////
////                        break;
//
//                    case SOUND_LOCATION://角度  从0 - 360(从右到左)
//
//                        distance = motionControl.getDistance() ;
//                        int d;
//                        if(distance != null){
//                            d = distance.getValue() ;
//                        }else {
//                            d = 90 ;
//                        }
//
//                        type = motionControl.getType();
//                        if(MotionSendControl.Type.SLAM == type) {
//                            mSlamMotion.turnSoundAngle(d) ;
//
//                        }else if(MotionSendControl.Type.SERIAL == type){
//                            mSerialSoundLocation.soundLocation(d);
//
//                        }else{
//                            mSlamMotion.turnSoundAngle(d) ;
//
//                            mSerialSoundLocation.soundLocation(d);
//                        }
//                        break;
//
//
//                    case STOP:
//
//                        type = motionControl.getType();
//                        if(MotionSendControl.Type.SLAM == type) {
//
//                            mSlamMotion.stop();
//
//                        }else if(MotionSendControl.Type.SERIAL == type){
//
//                            mSerialSendHeadLeftRight.headLeftRightStop() ;
//                            mSerialSendHeadUpDown.headUpDownStop() ;
//                            mSerialSendMove.stop();
//
//                        }else{
//                            mSlamMotion.stop();
//
//                            mSerialSendHeadLeftRight.headLeftRightStop() ;
//                            mSerialSendHeadUpDown.headUpDownStop() ;
//                            mSerialSendMove.stop();
//                        }
//
//                        break;
//
//                }
//            }
//        }

        return null;
    }


    public static final String ACTION_FAULT = "com.yongyida.robot.MOVE_FAULT" ;
    public static final String KEY_FAULT_CODE = "faultCode" ;
    public static final String KEY_FAULT_MESSAGE = "faultMessage" ;

    protected void sendFault(int faultCode, String faultMessage){

        Intent intent = new Intent(ACTION_FAULT) ;
        intent.putExtra(KEY_FAULT_CODE,faultCode ) ;
        intent.putExtra(KEY_FAULT_MESSAGE , faultMessage) ;

        mContext.sendBroadcast(intent);
    }

    public static final String ACTION_SYSTEM_CHANGED_GENERA = "com.yongyida.robot.SYSTEM_CHANGED_GENERA" ;
    public static final String KEY_GENERA_MESSAGE = "generaMessage" ;

    protected void sendSystemChanged (String message){

        Intent intent = new Intent(ACTION_SYSTEM_CHANGED_GENERA) ;
        intent.putExtra(KEY_GENERA_MESSAGE , message) ;

        mContext.sendBroadcast(intent);
    }

    public static final String ACTION_OBD_DATA_CHANGED_GENERA = "com.yongyida.robot.OBD_DATA_CHANGED_GENERA" ;

    protected void sendOBDDataChanged (String message){

        Intent intent = new Intent(ACTION_OBD_DATA_CHANGED_GENERA) ;
        intent.putExtra(KEY_GENERA_MESSAGE , message) ;

        mContext.sendBroadcast(intent);
    }


    private OnMoveFaultListener mOnMoveFaultListener = new OnMoveFaultListener() {
        @Override
        public void onMoveFault(int faultCode) {

            String message = faults.get(faultCode) ;
            if(message == null){

                message = "未知错误代码 :" + faultCode ;
            }

            moveFault = new MoveFault() ;
            moveFault.setCode(faultCode);
            moveFault.setMessage(message);

            sendFault(faultCode,message);
        }
    };

    /**
     * 错误
     * */
    public interface OnMoveFaultListener{

        void onMoveFault(int faultCode) ;
    }

    public static final String ACTION_ULTRASONIC_CHANGED = "com.yongyida.robot.ULTRASONIC_CHANGED" ;
    public static final String KEY_DISTANCES = "distances" ;
    private void sendUltrasonic(Y128Steering.ReceiveUltrasonic receiveUltrasonic){

        Intent intent = new Intent(ACTION_ULTRASONIC_CHANGED) ;
        intent.putExtra(KEY_DISTANCES,receiveUltrasonic.getDistances() ) ;

        mContext.sendBroadcast(intent);

    }


    private OnUltrasonicChangedListener onUltrasonicChangedListener = new OnUltrasonicChangedListener() {
        @Override
        public void onUltrasonicChanged(Y128Steering.ReceiveUltrasonic receiveUltrasonic) {

            sendUltrasonic(receiveUltrasonic) ;
        }


    };
    public interface OnUltrasonicChangedListener{

        void onUltrasonicChanged(Y128Steering.ReceiveUltrasonic receiveUltrasonic) ;
    }


    private OnSystemChangedListener mOnSystemChangedListener = new OnSystemChangedListener() {
        @Override
        public void onSystemChanged(Y128Steering.ReceiveSystemState receiveSystemState) {

            String message = receiveSystemState.toString() ;
            sendSystemChanged(message) ;
        }
    };
    public interface OnSystemChangedListener{

        void onSystemChanged(Y128Steering.ReceiveSystemState receiveSystemState);

    }

    private OnOBDDataChangedListener mOnOBDDataChangedListener = new OnOBDDataChangedListener() {
        @Override
        public void onSystemChanged(Y128Steering.OBDData obdData) {

            sendOBDDataChanged(obdData.toString()) ;
        }

    };
    public interface OnOBDDataChangedListener{

        void onSystemChanged(Y128Steering.OBDData obdData);
    }

}
