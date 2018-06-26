package com.yongyida.robot.model.y148.montrol;

import android.content.Context;

import com.yongyida.robot.communicate.app.hardware.motion.MotionSendHandlers;
import com.yongyida.robot.model.y148.led.Y148LedSendHandlers;
import com.yongyida.robot.model.y148.montrol.control.Y148ArmControlHandler;
import com.yongyida.robot.model.y148.montrol.control.Y148ChangeArmIdControlHandler;
import com.yongyida.robot.model.y148.montrol.control.Y148FootControlHandler;
import com.yongyida.robot.model.y148.montrol.control.Y148HandControlHandler;
import com.yongyida.robot.model.y148.montrol.control.Y148SteeringControlHandler;

/**
 * Created by HuangXiangXiang on 2018/4/20.
 */
public class Y148MotionSendHandlers extends MotionSendHandlers {

    private static final String TAG = Y148LedSendHandlers.class.getSimpleName() ;

    public Y148MotionSendHandlers(Context context) {
        super(context);

        addBaseControlHandler(new Y148SteeringControlHandler(context));
        addBaseControlHandler(new Y148ArmControlHandler(context));
        addBaseControlHandler(new Y148ChangeArmIdControlHandler(context));
        addBaseControlHandler(new Y148HandControlHandler(context));
        addBaseControlHandler(new Y148FootControlHandler(context));

    }

//
//    @Override
//    public SendResponse onHandler(final MotionSend send, final IResponseListener responseListener) {
//
//        BaseSendControl baseSendControl = send.getBaseControl() ;
//        LogHelper.i(TAG , LogHelper.__TAG__() );
//
//        if(baseSendControl instanceof MotionSendControl){
//
//            MotionSendControl motionControl = (MotionSendControl) baseSendControl;
//
//            MotionSendControl.Action action = motionControl.getAction() ;
//            if(action != null){
//
//                switch (action) {
//
//                    case FOOT_FORWARD:
//
//                        MotionSendControl.Time time = motionControl.getTime();
//                        int value;
//                        if (time != null) {
//
//                            value = time.getValue();
//                        } else {
//                            value = 2000;
//                        }
//
//                        mSlamMotion.forward(value);
//
//                        break;
//                    case FOOT_BACK:
//
//                        time = motionControl.getTime();
//                        if (time != null) {
//
//                            value = time.getValue();
//                        } else {
//                            value = 2000;
//                        }
//
//                        mSlamMotion.back(value);
//
//                        break;
//                    case FOOT_LEFT:
//
//                        time = motionControl.getTime();
//                        if (time != null) {
//
//                            value = time.getValue();
//                        } else {
//                            value = 2000;
//                        }
//
//                        mSlamMotion.left(value);
//
//                        break;
//                    case FOOT_RIGHT:
//
//                        time = motionControl.getTime();
//                        if (time != null) {
//
//                            value = time.getValue();
//                        } else {
//                            value = 2000;
//                        }
//
//                        mSlamMotion.right(value);
//
//
//                        break;
//                    case FOOT_STOP:
//
//
//                        mSlamMotion.stop();
//
//
//                        break;
//
////                    case SOUND_LOCATION_LEFT:
////
////                        MotionSendControl.Distance distance = motionControl.getDistance();
////
////                        int d;
////                        if (distance != null) {
////                            d = distance.getValue();
////                        } else {
////                            d = 0;
////                        }
////
////                        break;
////
////                    case SOUND_LOCATION_RIGHT:
////
////                        distance = motionControl.getDistance();
////                        if (distance != null) {
////                            d = distance.getValue();
////                        } else {
////                            d = 0;
////                        }
////
////                        break;
//
//                    case SOUND_LOCATION://角度  从0 - 360(从右到左)
//
//                        MotionSendControl.Distance distance = motionControl.getDistance();
//                        int d;
//                        if (distance != null) {
//                            d = distance.getValue();
//                        } else {
//                            d = 90;
//                        }
//                        mSlamMotion.turnSoundAngle(d);
//                        break;
//
//
//                    case STOP:
//
//                        mSlamMotion.stop();
//                        break;
//
//
//
//                    case HEAD_LEFT:
//
//                        stopHeadLeftRightSharkThread() ;
//
//                        mSteerHeadLeftRight.turnLeft(-100);
//                        byte[] data = Y148Steering.getCmd(mSteerHeadLeftRight) ;
//                        mUART.writeData(data) ;
//
//                        break;
//
//                    case HEAD_RIGHT:
//
//                        stopHeadLeftRightSharkThread() ;
//
//                        mSteerHeadLeftRight.turnRight(100);
//                        data = Y148Steering.getCmd(mSteerHeadLeftRight) ;
//                        mUART.writeData(data) ;
//
//                        break;
//
//                    case HEAD_LEFT_RIGHT_RESET:
//
//                        mSteerHeadLeftRight.turnReset();
//                        data = Y148Steering.getCmd(mSteerHeadLeftRight) ;
//                        mUART.writeData(data) ;
//
//                        break;
//                    case HEAD_LEFT_RIGHT_LOOP:
//
////                        startHeadLeftRightSharkThread() ;
//
//                        mSteerHeadLeftRight.turnLoop();
//                        data = Y148Steering.getCmd(mSteerHeadLeftRight) ;
//                        mUART.writeData(data) ;
//
//
//
//                        break;
//
//                    case HEAD_UP:
//
//                        stopHeadUpDownShark() ;
//
//                        mSteerHeadUpDown.turnUp(-100);
//                        data = Y148Steering.getCmd(mSteerHeadUpDown) ;
//                        mUART.writeData(data) ;
//
//                        break;
//
//                    case HEAD_DOWN:
//
//                        stopHeadUpDownShark();
//
//                        mSteerHeadUpDown.turnDown(100);
//                        data = Y148Steering.getCmd(mSteerHeadUpDown) ;
//                        mUART.writeData(data) ;
//
//                        break;
//
//                    case HEAD_UP_DOWN_RESET:
//
//                        mSteerHeadUpDown.turnReset();
//                        data = Y148Steering.getCmd(mSteerHeadUpDown) ;
//                        mUART.writeData(data) ;
//
//                        break;
//
//                    case HEAD_UP_DOWN_LOOP:
//
////                        startHeadUpDownShark() ;
//
//                        mSteerHeadUpDown.turnLoop();
//                        data = Y148Steering.getCmd(mSteerHeadUpDown) ;
//                        mUART.writeData(data) ;
//
//
//                        break;
//
//                }
//            }
//
//            return null ;
//        }
//
//
//
//        return null;
//    }


//    private void startHeadUpDownShark(){
//
//        if(mHeadUpDownSharkThread == null || !mHeadUpDownSharkThread.isRun){
//
//            mHeadUpDownSharkThread = new HeadUpDownSharkThread();
//            mHeadUpDownSharkThread.start();
//        }
//    }
//
//    private void stopHeadUpDownShark(){
//
//        if(mHeadUpDownSharkThread != null){
//
//            mHeadUpDownSharkThread.stopRun();
//            mHeadUpDownSharkThread = null ;
//        }
//
//    }
//
//    private HeadUpDownSharkThread mHeadUpDownSharkThread ;
//    private class HeadUpDownSharkThread extends Thread{
//
//        private boolean isRun = true ;
//
//        @Override
//        public void run() {
//
//            while (true){
//
//                mSteerHeadUpDown.turnUp(-100);
//                byte[] data = Y148Steering.getCmd(mSteerHeadUpDown) ;
//                mUART.writeData(data) ;
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if(!isRun){
//                    return;
//                }
//
//                mSteerHeadUpDown.turnDown(100);
//                data = Y148Steering.getCmd(mSteerHeadUpDown) ;
//                mUART.writeData(data) ;
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if(!isRun){
//                    return;
//                }
//            }
//
//        }
//
//        private void stopRun(){
//
//            isRun = false ;
//        }
//
//
//    }
//
//
//    private void startHeadLeftRightSharkThread(){
//
//        if(mHeadLeftRightSharkThread == null || !mHeadLeftRightSharkThread.isRun){
//
//            mHeadLeftRightSharkThread = new HeadLeftRightSharkThread();
//            mHeadLeftRightSharkThread.start();
//        }
//    }
//
//    private void stopHeadLeftRightSharkThread(){
//
//        if(mHeadLeftRightSharkThread != null){
//
//            mHeadLeftRightSharkThread.stopRun();
//            mHeadLeftRightSharkThread = null ;
//        }
//
//    }
//
//    private HeadLeftRightSharkThread mHeadLeftRightSharkThread ;
//    private class HeadLeftRightSharkThread extends Thread{
//        private boolean isRun = true ;
//        @Override
//        public void run() {
//
//            while (true){
//
//                mSteerHeadLeftRight.turnLeft(-100);
//                byte[] data = Y148Steering.getCmd(mSteerHeadLeftRight) ;
//                mUART.writeData(data) ;
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if(!isRun){
//                    return;
//                }
//
//
//                mSteerHeadLeftRight.turnRight(100);
//                data = Y148Steering.getCmd(mSteerHeadLeftRight) ;
//                mUART.writeData(data) ;
//
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if(!isRun){
//                    return;
//                }
//
//            }
//        }
//
//
//        private void stopRun(){
//
//            isRun = false ;
//        }
//
//    }
}
