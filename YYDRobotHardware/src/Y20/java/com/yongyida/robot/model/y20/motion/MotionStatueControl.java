package com.yongyida.robot.model.y20.motion;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.motion.data.MotionControl;
import com.yongyida.robot.serial.MotionHelper;

import java.util.ArrayList;

/**
 * Created by HuangXiangXiang on 2017/12/19.
 */
public class MotionStatueControl {

    private MotionHelper motionHelper ;

    public MotionStatueControl(String serialPort) {

        motionHelper = new MotionHelper(serialPort);
    }

    protected BaseResponse onControl(ArrayList<MotionControl> motionControls) {

//        motionHelper.open();
//
//        MotionControl leftFoot = null ;
//        MotionControl rightFoot = null ;
//
//        int size = motionControls.size() ;
//        for (int i = 0 ; i < size ; i++){
//
//            MotionControl motionData = motionControls.get(i) ;
//            switch (motionData.getPosition()){
//
//                case HEAD_LEFT_RIGHT:
//                    onHead(motionData, true) ;
//                    break;
//                case HEAD_UP_DOWN:
//                    onHead(motionData, false) ;
//                    break;
////                case NECK:
////                    break;
////                case BODY:
////                    break;
//                case LEFT_FOOT:
//
//                    leftFoot = motionData ;
//                    break;
//                case RIGHT_FOOT:
//                    rightFoot = motionData ;
//                    break;
//            }
//        }
//
//        onFootControl(leftFoot,rightFoot) ;

        return null;
    }

    private void onHead(MotionControl motionData, boolean isLeftRight){

        boolean isForward = true ;// 是否是正转

        int motorSelect = isLeftRight ? 0 : 1 ;
        int direct;
        int motorSpeed;
        int argType;
        int argValue;

        switch (motionData.getType()){

//            case TIME_SPEED:
//
//                argType = MotionHelper.DRVTYPE_BY_TIME;
//                argValue = motionData.getTime().getValue() ;
//
//                MotionControl.Speed headSpeed = motionData.getSpeed() ;
//                if(headSpeed == null){
//
//                    motorSpeed = 0 ;
//
//                }else if( headSpeed.getValue() < 0){
//
//                    isForward = !isForward ;
//                    motorSpeed = -headSpeed.getValue() ;
//
//                }else {
//
//                    motorSpeed = headSpeed.getValue() ;
//                }
//
//                direct = isLeftRight ? (isForward ? 0 : 1) :(isForward ? 1 : 0) ;
//                motionHelper.doHeadCommand(motorSelect, direct,motionHelper.getSpeedValue(motorSpeed),argType,argValue) ;
//                break;
//
//            case DISTANCE_SPEED:
//
//                argType = MotionHelper.DRVTYPE_BY_DISTANCE;
//                argValue = motionData.getDistance().getValue();
//                if(argValue < 0 ){
//
//                    argValue = -argValue ;
//                    isForward = !isForward ;
//                }
//
//                headSpeed = motionData.getSpeed() ;
//                if(headSpeed == null){
//
//                    motorSpeed = 0 ;
//
//                }else if( headSpeed.getValue() < 0){
//
//                    isForward = !isForward ;
//                    motorSpeed = - headSpeed.getValue() ;
//
//                }else {
//
//                    motorSpeed = headSpeed.getValue() ;
//                }
//
//                direct = isLeftRight ? (isForward ? 0 : 1) :(isForward ? 1 : 0) ;
//                motionHelper.doHeadCommand(motorSelect, direct,motionHelper.getSpeedValue(motorSpeed),argType,argValue) ;
//                break;
        }
    }


    private void onFootControl(MotionControl leftFoot, MotionControl rightFoot){

        boolean isLefForward = true ;// 是否是正转
        boolean isRightForward = true ;// 是否是正转

        int leftDirection ;
        int rightDirection ;
        int leftSpeed ;
        int rightSpeed ;
        int argType ;
        int argValue ;


//        if(leftFoot != null){
//            if(rightFoot != null){
//                // 左右都有值
//                if(leftFoot.getType() == rightFoot.getType() ){  //左右模式相同
//
//                    switch (leftFoot.getType()){
//
//                        case TIME_SPEED:
//
//                            argType = MotionHelper.DRVTYPE_BY_TIME;
//                            argValue = leftFoot.getTime().getValue();
//
//                            MotionControl.Speed lSpeed = leftFoot.getSpeed() ;
//                            if(lSpeed == null){
//
//                                leftSpeed = 0 ;
//                            }else if( lSpeed.getValue() < 0){
//
//                                isLefForward = !isLefForward ;
//                                leftSpeed = - lSpeed.getValue() ;
//
//                            }else {
//
//                                leftSpeed = lSpeed.getValue() ;
//                            }
//
//                            MotionControl.Speed rSpeed = rightFoot.getSpeed() ;
//                            if(rSpeed == null){
//
//                                rightSpeed = 0 ;
//                            }else if( rSpeed.getValue() < 0){
//
//                                isRightForward = !isRightForward ;
//                                rightSpeed = - rSpeed.getValue() ;
//
//                            }else {
//
//                                rightSpeed = rSpeed.getValue() ;
//                            }
//
//                            leftDirection = isLefForward? 0 : 1 ;
//                            rightDirection = isRightForward? 0 : 1 ;
//                            motionHelper.doCommand(leftDirection,rightDirection ,motionHelper.getSpeedValue(leftSpeed) ,motionHelper.getSpeedValue(rightSpeed),argType,argValue) ;
//
//                            break;
//
//                        case DISTANCE_SPEED:
//
//                            argType = MotionHelper.DRVTYPE_BY_DISTANCE;
//                            argValue = leftFoot.getDistance().getValue();
//                            if(argValue < 0){
//
//                                isLefForward = !isLefForward ;
//                                isRightForward = !isRightForward ;
//
//                                argValue = -argValue ;
//                            }
//
//                            lSpeed = leftFoot.getSpeed() ;
//                            if(lSpeed == null){
//
//                                leftSpeed = 0 ;
//                            }else if( lSpeed.getValue() < 0){
//
//                                isLefForward = !isLefForward ;
//                                leftSpeed = - lSpeed.getValue() ;
//
//                            }else {
//
//                                leftSpeed = lSpeed.getValue() ;
//                            }
//
//                            rSpeed = leftFoot.getSpeed() ;
//                            if(rSpeed == null){
//
//                                rightSpeed = 0 ;
//                            }else if( rSpeed.getValue() < 0){
//
//                                isRightForward = !isRightForward ;
//                                rightSpeed = - rSpeed.getValue() ;
//
//                            }else {
//
//                                rightSpeed = rSpeed.getValue() ;
//                            }
//
//                            leftDirection = isLefForward? 0 : 1 ;
//                            rightDirection = isRightForward? 0 : 1 ;
//                            motionHelper.doCommand(leftDirection,rightDirection ,motionHelper.getSpeedValue(leftSpeed) ,motionHelper.getSpeedValue(rightSpeed),argType,argValue) ;
//
//                            break;
//                    }
//
//                }
//
//
//            }else{
//
//                switch (leftFoot.getType()){
//
//                    case TIME_SPEED:
//
//                        argType = MotionHelper.DRVTYPE_BY_TIME;
//                        argValue = leftFoot.getTime().getValue();
//
//                        MotionControl.Speed lSpeed = leftFoot.getSpeed() ;
//                        if(lSpeed == null){
//
//                            leftSpeed = 0 ;
//                        }else if( lSpeed.getValue() < 0){
//
//                            isLefForward = !isLefForward ;
//                            leftSpeed = - lSpeed.getValue() ;
//
//                        }else {
//
//                            leftSpeed = lSpeed.getValue() ;
//                        }
//
//                        rightDirection = 0 ;
//                        rightSpeed = 0 ;
//
//                        leftDirection = isLefForward? 0 : 1 ;
//                        motionHelper.doCommand(leftDirection,rightDirection ,motionHelper.getSpeedValue(leftSpeed) ,motionHelper.getSpeedValue(rightSpeed),argType,argValue) ;
//
//                        break;
//
//                    case DISTANCE_SPEED:
//
//                        argType = MotionHelper.DRVTYPE_BY_DISTANCE;
//                        argValue = leftFoot.getDistance().getValue();
//                        if(argValue < 0){
//
//                            isLefForward = !isLefForward ;
//                            argValue = -argValue ;
//                        }
//
//                        lSpeed = leftFoot.getSpeed() ;
//                        if(lSpeed == null){
//
//                            leftSpeed = 0 ;
//                        }else if( lSpeed.getValue() < 0){
//
//                            isLefForward = !isLefForward ;
//                            leftSpeed = - lSpeed.getValue() ;
//
//                        }else {
//
//                            leftSpeed = lSpeed.getValue() ;
//                        }
//
//                        rightDirection = 0 ;
//                        rightSpeed = 0 ;
//
//                        leftDirection = isLefForward? 0 : 1 ;
//                        motionHelper.doCommand(leftDirection,rightDirection ,motionHelper.getSpeedValue(leftSpeed) ,motionHelper.getSpeedValue(rightSpeed),argType,argValue) ;
//
//                        break;
//                }
//
//            }
//
//        }else{
//
//            if(rightFoot != null){
//
//
//                switch (rightFoot.getType()){
//
//                    case TIME_SPEED:
//
//                        argType = MotionHelper.DRVTYPE_BY_TIME;
//                        argValue = rightFoot.getTime().getValue();
//
//                        MotionControl.Speed rSpeed = rightFoot.getSpeed() ;
//                        if(rSpeed == null){
//
//                            rightSpeed = 0 ;
//                        }else if( rSpeed.getValue() < 0){
//
//                            isRightForward = !isRightForward ;
//                            rightSpeed = - rSpeed.getValue() ;
//
//                        }else {
//
//                            rightSpeed = rSpeed.getValue() ;
//                        }
//
//
//                        leftDirection = 0 ;
//                        leftSpeed = 0 ;
//
//                        rightDirection = isRightForward ? 0 : 1 ;
//                        motionHelper.doCommand(leftDirection,rightDirection ,motionHelper.getSpeedValue(leftSpeed) ,motionHelper.getSpeedValue(rightSpeed),argType,argValue) ;
//
//                        break;
//
//                    case DISTANCE_SPEED:
//
//                        argType = MotionHelper.DRVTYPE_BY_DISTANCE;
//                        argValue = rightFoot.getDistance().getValue();
//                        if(argValue < 0){
//
//                            isRightForward = !isRightForward ;
//                            argValue = -argValue ;
//                        }
//
//                        rSpeed = rightFoot.getSpeed() ;
//                        if(rSpeed == null){
//
//                            rightSpeed = 0 ;
//                        }else if( rSpeed.getValue() < 0){
//
//                            isRightForward = !isRightForward ;
//                            rightSpeed = - rSpeed.getValue() ;
//
//                        }else {
//
//                            rightSpeed = rSpeed.getValue() ;
//                        }
//
//                        leftDirection = 0 ;
//                        leftSpeed = 0 ;
//
//                        rightDirection = isRightForward ? 0 : 1 ;
//                        motionHelper.doCommand(leftDirection,rightDirection ,motionHelper.getSpeedValue(leftSpeed) ,motionHelper.getSpeedValue(rightSpeed),argType,argValue) ;
//
//                        break;
//                }
//
//
//            }else{
//                // 左右都无值（啥也不做）
//            }
//
//        }

    }



}
