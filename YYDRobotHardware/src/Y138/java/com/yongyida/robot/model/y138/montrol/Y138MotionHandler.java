package com.yongyida.robot.model.y138.montrol;

import android.content.Context;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.motion.MotionHandler;
import com.yongyida.robot.communicate.app.hardware.motion.data.ArmControl;
import com.yongyida.robot.communicate.app.hardware.motion.data.ChangeArmId;
import com.yongyida.robot.communicate.app.hardware.motion.data.HandAction;
import com.yongyida.robot.communicate.app.hardware.motion.data.MotionControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;
import com.yongyida.robot.model.agreement.Y138Steering;
import com.yongyida.robot.model.y128.motion.SlamMotion;
import com.yongyida.robot.usb_uart.UART;
import com.yongyida.robot.utils.YYDUart;

/**
 * Created by HuangXiangXiang on 2018/4/20.
 */
public class Y138MotionHandler extends MotionHandler {

    private UART mUART ;
    private Y138Steering.SteerAction mSteerAction ;

    private Y138Steering.SteerArm mSteerArm ;

    private SlamMotion mSlamMotion ;


    public Y138MotionHandler(Context context) {
        super(context);

        mSlamMotion = new SlamMotion(context);

        mSteerAction = new Y138Steering.SteerAction() ;
        mSteerArm = new Y138Steering.SteerArm() ;

        mUART = UART.getInstance(context) ;
        mUART.openDevice(null) ;
    }

    @Override
    public BaseResponse onHandler(MotionSend send) {

        HandAction handAction = send.getHandAction() ;
        if(handAction != null){

            mSteerAction.setData(handAction.getType(), handAction.getValue());
            byte[] data = Y138Steering.getCmd(mSteerAction) ;

            mUART.writeData(data) ;

            return null ;
        }

        ChangeArmId changeArmId = send.getChangeArmId() ;
        if(changeArmId != null){

            mSteerArm.changeId(changeArmId.getSrcId(), changeArmId.getDestId());

            byte[] data = Y138Steering.getCmd(mSteerArm) ;
            mUART.writeData(data) ;

            return null ;
        }

        ArmControl armControl = send.getArmControl() ;
        if(armControl != null){

//            mSteerArm.controlMultipleArm(armControl.getDirection().value, armControl.getJoints());
            mSteerArm.controlSingleArm(armControl.getDirection().value, armControl.getJoints().get(0));

            byte[] data = Y138Steering.getCmd(mSteerArm) ;
            mUART.writeData(data) ;

            return  null ;
        }


        MotionControl motionControl = send.getMotionControl() ;
        if(motionControl != null){

            MotionControl.Action action = motionControl.getAction() ;
            if(action != null){

                switch (action) {

                    case FOOT_FORWARD:

                        MotionControl.Time time = motionControl.getTime();
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

                        MotionControl.Distance distance = motionControl.getDistance();
                        ;
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
                    }
                }
                return null;
            }

            return null;
    }
}
