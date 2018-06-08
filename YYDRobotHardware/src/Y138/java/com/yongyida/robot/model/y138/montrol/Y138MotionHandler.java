package com.yongyida.robot.model.y138.montrol;

import android.content.Context;

import com.hiva.communicate.app.common.send.SendResponseListener;
import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.send.data.BaseSendControl;
import com.hiva.communicate.app.server.IResponseListener;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.motion.MotionHandler;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.ArmSendControl;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.ChangeArmId;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.FingerSendControl;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.HandAction;
import com.yongyida.robot.communicate.app.hardware.hand.response.data.HandAngle;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.MotionSendControl;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.QueryHandAngle;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.TeacherSendControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;
import com.yongyida.robot.model.agreement.Y138Steering;
import com.yongyida.robot.model.y128.motion.SlamMotion;
import com.yongyida.robot.model.y138.led.Y138LedHandler;
import com.yongyida.robot.usb_uart.UART;

/**
 * Created by HuangXiangXiang on 2018/4/20.
 */
public class Y138MotionHandler extends MotionHandler {

    private static final String TAG = Y138LedHandler.class.getSimpleName() ;

    private UART mUART ;
    private Y138Steering.SteerAction mSteerAction ;
    private Y138Steering.SteerArm mSteerArm ;
    private Y138Steering.SteerFinger mSteerFinger ;
    private Y138Steering.SteerTeacher mSteerTeacher ;
    private Y138Steering.SteerHeadLeftRight mSteerHeadLeftRight ;
    private Y138Steering.SteerHeadUpDown mSteerHeadUpDown ;

    private SlamMotion mSlamMotion ;

    public Y138MotionHandler(Context context) {
        super(context);

        mSlamMotion = SlamMotion.getInstance(context);

        mSteerAction = new Y138Steering.SteerAction() ;
        mSteerArm = new Y138Steering.SteerArm() ;
        mSteerFinger = new Y138Steering.SteerFinger() ;
        mSteerHeadLeftRight = new Y138Steering.SteerHeadLeftRight() ;
        mSteerHeadUpDown = new Y138Steering.SteerHeadUpDown() ;
        mSteerTeacher = new Y138Steering.SteerTeacher() ;

        mUART = UART.getInstance(context) ;
        mUART.openDevice(null) ;
    }

    @Override
    public BaseResponse onHandler(final MotionSend send, final IResponseListener responseListener) {

        BaseSendControl baseSendControl = send.getBaseControl() ;
        LogHelper.i(TAG , LogHelper.__TAG__() );

        if(baseSendControl instanceof MotionSendControl){

            MotionSendControl motionControl = (MotionSendControl) baseSendControl;

            MotionSendControl.Action action = motionControl.getAction() ;
            if(action != null){

                switch (action) {

                    case FOOT_FORWARD:

                        MotionSendControl.Time time = motionControl.getTime();
                        int value;
                        if (time != null) {

                            value = time.getValue();
                        } else {
                            value = 2000;
                        }

                        mSlamMotion.forward(value);

                        break;
                    case FOOT_BACK:

                        time = motionControl.getTime();
                        if (time != null) {

                            value = time.getValue();
                        } else {
                            value = 2000;
                        }

                        mSlamMotion.back(value);

                        break;
                    case FOOT_LEFT:

                        time = motionControl.getTime();
                        if (time != null) {

                            value = time.getValue();
                        } else {
                            value = 2000;
                        }

                        mSlamMotion.left(value);

                        break;
                    case FOOT_RIGHT:

                        time = motionControl.getTime();
                        if (time != null) {

                            value = time.getValue();
                        } else {
                            value = 2000;
                        }

                        mSlamMotion.right(value);


                        break;
                    case FOOT_STOP:


                        mSlamMotion.stop();


                        break;

                    case SOUND_LOCATION_LEFT:

                        MotionSendControl.Distance distance = motionControl.getDistance();

                        int d;
                        if (distance != null) {
                            d = distance.getValue();
                        } else {
                            d = 0;
                        }

                        break;

                    case SOUND_LOCATION_RIGHT:

                        distance = motionControl.getDistance();
                        if (distance != null) {
                            d = distance.getValue();
                        } else {
                            d = 0;
                        }

                        break;

                    case SOUND_LOCATION://角度  从0 - 360(从右到左)

                        distance = motionControl.getDistance();
                        if (distance != null) {
                            d = distance.getValue();
                        } else {
                            d = 90;
                        }
                        mSlamMotion.turnSoundAngle(d);
                        break;


                    case STOP:

                        mSlamMotion.stop();
                        break;



                    case HEAD_LEFT:

                        stopHeadLeftRightSharkThread() ;

                        mSteerHeadLeftRight.turnLeft(-100);
                        byte[] data = Y138Steering.getCmd(mSteerHeadLeftRight) ;
                        mUART.writeData(data) ;

                        break;

                    case HEAD_RIGHT:

                        stopHeadLeftRightSharkThread() ;

                        mSteerHeadLeftRight.turnRight(100);
                        data = Y138Steering.getCmd(mSteerHeadLeftRight) ;
                        mUART.writeData(data) ;

                        break;

                    case HEAD_LEFT_RIGHT_RESET:

                        mSteerHeadLeftRight.turnReset();
                        data = Y138Steering.getCmd(mSteerHeadLeftRight) ;
                        mUART.writeData(data) ;

                        break;
                    case HEAD_LEFT_RIGHT_SHAKE:

//                        startHeadLeftRightSharkThread() ;

                        mSteerHeadLeftRight.turnLoop();
                        data = Y138Steering.getCmd(mSteerHeadLeftRight) ;
                        mUART.writeData(data) ;



                        break;

                    case HEAD_UP:

                        stopHeadUpDownShark() ;

                        mSteerHeadUpDown.turnUp(-100);
                        data = Y138Steering.getCmd(mSteerHeadUpDown) ;
                        mUART.writeData(data) ;

                        break;

                    case HEAD_DOWN:

                        stopHeadUpDownShark();

                        mSteerHeadUpDown.turnDown(100);
                        data = Y138Steering.getCmd(mSteerHeadUpDown) ;
                        mUART.writeData(data) ;

                        break;

                    case HEAD_UP_DOWN_RESET:

                        mSteerHeadUpDown.turnReset();
                        data = Y138Steering.getCmd(mSteerHeadUpDown) ;
                        mUART.writeData(data) ;

                        break;

                    case HEAD_UP_DOWN_SHAKE:

//                        startHeadUpDownShark() ;

                        mSteerHeadUpDown.turnLoop();
                        data = Y138Steering.getCmd(mSteerHeadUpDown) ;
                        mUART.writeData(data) ;


                        break;

                }
            }

            return null ;
        }



        return null;
    }


    private void startHeadUpDownShark(){

        if(mHeadUpDownSharkThread == null || !mHeadUpDownSharkThread.isRun){

            mHeadUpDownSharkThread = new HeadUpDownSharkThread();
            mHeadUpDownSharkThread.start();
        }
    }

    private void stopHeadUpDownShark(){

        if(mHeadUpDownSharkThread != null){

            mHeadUpDownSharkThread.stopRun();
            mHeadUpDownSharkThread = null ;
        }

    }

    private HeadUpDownSharkThread mHeadUpDownSharkThread ;
    private class HeadUpDownSharkThread extends Thread{

        private boolean isRun = true ;

        @Override
        public void run() {

            while (true){

                mSteerHeadUpDown.turnUp(-100);
                byte[] data = Y138Steering.getCmd(mSteerHeadUpDown) ;
                mUART.writeData(data) ;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!isRun){
                    return;
                }

                mSteerHeadUpDown.turnDown(100);
                data = Y138Steering.getCmd(mSteerHeadUpDown) ;
                mUART.writeData(data) ;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!isRun){
                    return;
                }
            }

        }

        private void stopRun(){

            isRun = false ;
        }


    }


    private void startHeadLeftRightSharkThread(){

        if(mHeadLeftRightSharkThread == null || !mHeadLeftRightSharkThread.isRun){

            mHeadLeftRightSharkThread = new HeadLeftRightSharkThread();
            mHeadLeftRightSharkThread.start();
        }
    }

    private void stopHeadLeftRightSharkThread(){

        if(mHeadLeftRightSharkThread != null){

            mHeadLeftRightSharkThread.stopRun();
            mHeadLeftRightSharkThread = null ;
        }

    }

    private HeadLeftRightSharkThread mHeadLeftRightSharkThread ;
    private class HeadLeftRightSharkThread extends Thread{
        private boolean isRun = true ;
        @Override
        public void run() {

            while (true){

                mSteerHeadLeftRight.turnLeft(-100);
                byte[] data = Y138Steering.getCmd(mSteerHeadLeftRight) ;
                mUART.writeData(data) ;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!isRun){
                    return;
                }


                mSteerHeadLeftRight.turnRight(100);
                data = Y138Steering.getCmd(mSteerHeadLeftRight) ;
                mUART.writeData(data) ;

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!isRun){
                    return;
                }

            }
        }


        private void stopRun(){

            isRun = false ;
        }

    }
}
