package com.yongyida.robot.model.y20.motion;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.motion.data.MotionStatue;
import com.yongyida.robot.serial.MotionHelper;

import java.util.ArrayList;

/**
 * Created by HuangXiangXiang on 2017/12/19.
 */
public class MotionStatueControl {

    private MotionHelper motionHelper = new MotionHelper() ;

    protected BaseResponse onControl(MotionStatue motionStatue) {

        motionHelper.open();

        ArrayList<MotionStatue.MotionData> motionDatas = motionStatue.getMotionDatas() ;

        MotionStatue.MotionData leftFoot = null ;
        MotionStatue.MotionData rightFoot = null ;

        int size = motionDatas.size() ;
        for (int i = 0 ; i < size ; i++){

            MotionStatue.MotionData motionData = motionDatas.get(i) ;
            if(motionData.getPosition() == MotionStatue.MotionData.Position.LEFT_FOOT){

                leftFoot = motionData ;

            }else if(motionData.getPosition() == MotionStatue.MotionData.Position.RIGHT_FOOT){

                rightFoot = motionData ;
            }
        }

        if(leftFoot != null){

            if(rightFoot != null){
                // 左右都有值
                if(leftFoot.getType() == rightFoot.getType() ){  //左右模式相同

                    switch (leftFoot.getType()){

                        case TIME_SPEED:

                            int leftDirection ;
                            int rightDirection ;
                            int leftSpeed ;
                            int rightSpeed ;
                            int argType = MotionHelper.DRVTYPE_BY_TIME;
                            int argValue = leftFoot.getTime().time;

                            MotionStatue.MotionData.Speed lSpeed = leftFoot.getSpeed() ;
                            if(lSpeed == null){

                                leftDirection = 0 ;
                                leftSpeed = 0 ;
                            }else if( lSpeed.speed < 0){

                                leftDirection = -1 ;
                                leftSpeed = - lSpeed.speed ;

                            }else {

                                leftDirection = 0 ;
                                leftSpeed = lSpeed.speed ;
                            }

                            MotionStatue.MotionData.Speed rSpeed = rightFoot.getSpeed() ;
                            if(rSpeed == null){

                                rightDirection = 0 ;
                                rightSpeed = 0 ;
                            }else if( rSpeed.speed < 0){

                                rightDirection = -1 ;
                                rightSpeed = - rSpeed.speed ;

                            }else {

                                rightDirection = 0 ;
                                rightSpeed = rSpeed.speed ;
                            }

                            motionHelper.doCommand(leftDirection,rightDirection ,motionHelper.getSpeedValue(leftSpeed) ,motionHelper.getSpeedValue(rightSpeed),argType,argValue) ;

                            break;

                        case DISTANCE_SPEED:

                            argType = MotionHelper.DRVTYPE_BY_DISTANCE;
                            argValue = leftFoot.getDistance().distance;

                            lSpeed = leftFoot.getSpeed() ;
                            if(lSpeed == null){

                                leftDirection = 0 ;
                                leftSpeed = 0 ;
                            }else if( lSpeed.speed < 0){

                                leftDirection = -1 ;
                                leftSpeed = - lSpeed.speed ;

                            }else {

                                leftDirection = 0 ;
                                leftSpeed = lSpeed.speed ;
                            }

                            rSpeed = leftFoot.getSpeed() ;
                            if(rSpeed == null){

                                rightDirection = 0 ;
                                rightSpeed = 0 ;
                            }else if( rSpeed.speed < 0){

                                rightDirection = -1 ;
                                rightSpeed = - rSpeed.speed ;

                            }else {

                                rightDirection = 0 ;
                                rightSpeed = rSpeed.speed ;
                            }

                            motionHelper.doCommand(leftDirection,rightDirection ,motionHelper.getSpeedValue(leftSpeed) ,motionHelper.getSpeedValue(rightSpeed),argType,argValue) ;

                            break;

                    }


                }


            }else{

                switch (leftFoot.getType()){

                    case TIME_SPEED:

                        int leftDirection ;
                        int rightDirection ;
                        int leftSpeed ;
                        int rightSpeed ;
                        int argType = MotionHelper.DRVTYPE_BY_TIME;
                        int argValue = leftFoot.getTime().time;

                        MotionStatue.MotionData.Speed lSpeed = leftFoot.getSpeed() ;
                        if(lSpeed == null){

                            leftDirection = 0 ;
                            leftSpeed = 0 ;
                        }else if( lSpeed.speed < 0){

                            leftDirection = -1 ;
                            leftSpeed = - lSpeed.speed ;

                        }else {

                            leftDirection = 0 ;
                            leftSpeed = lSpeed.speed ;
                        }


                        rightDirection = 0 ;
                        rightSpeed = 0 ;


                        motionHelper.doCommand(leftDirection,rightDirection ,motionHelper.getSpeedValue(leftSpeed) ,motionHelper.getSpeedValue(rightSpeed),argType,argValue) ;

                        break;

                    case DISTANCE_SPEED:

                        argType = MotionHelper.DRVTYPE_BY_DISTANCE;
                        argValue = leftFoot.getDistance().distance;

                        lSpeed = leftFoot.getSpeed() ;
                        if(lSpeed == null){

                            leftDirection = 0 ;
                            leftSpeed = 0 ;
                        }else if( lSpeed.speed < 0){

                            leftDirection = -1 ;
                            leftSpeed = - lSpeed.speed ;

                        }else {

                            leftDirection = 0 ;
                            leftSpeed = lSpeed.speed ;
                        }

                        rightDirection = 0 ;
                        rightSpeed = 0 ;

                        motionHelper.doCommand(leftDirection,rightDirection ,motionHelper.getSpeedValue(leftSpeed) ,motionHelper.getSpeedValue(rightSpeed),argType,argValue) ;

                        break;
                }

            }

        }else{

            if(rightFoot != null){


                switch (leftFoot.getType()){

                    case TIME_SPEED:

                        int leftDirection ;
                        int rightDirection ;
                        int leftSpeed ;
                        int rightSpeed ;
                        int argType = MotionHelper.DRVTYPE_BY_TIME;
                        int argValue = leftFoot.getTime().time;

                        leftDirection = 0 ;
                        leftSpeed = 0 ;

                        MotionStatue.MotionData.Speed rSpeed = rightFoot.getSpeed() ;
                        if(rSpeed == null){

                            rightDirection = 0 ;
                            rightSpeed = 0 ;
                        }else if( rSpeed.speed < 0){

                            rightDirection = -1 ;
                            rightSpeed = - rSpeed.speed ;

                        }else {

                            rightDirection = 0 ;
                            rightSpeed = rSpeed.speed ;
                        }

                        motionHelper.doCommand(leftDirection,rightDirection ,motionHelper.getSpeedValue(leftSpeed) ,motionHelper.getSpeedValue(rightSpeed),argType,argValue) ;

                        break;

                    case DISTANCE_SPEED:

                        argType = MotionHelper.DRVTYPE_BY_DISTANCE;
                        argValue = leftFoot.getDistance().distance;

                        leftDirection = 0 ;
                        leftSpeed = 0 ;

                        rSpeed = leftFoot.getSpeed() ;
                        if(rSpeed == null){

                            rightDirection = 0 ;
                            rightSpeed = 0 ;
                        }else if( rSpeed.speed < 0){

                            rightDirection = -1 ;
                            rightSpeed = - rSpeed.speed ;

                        }else {

                            rightDirection = 0 ;
                            rightSpeed = rSpeed.speed ;
                        }

                        motionHelper.doCommand(leftDirection,rightDirection ,motionHelper.getSpeedValue(leftSpeed) ,motionHelper.getSpeedValue(rightSpeed),argType,argValue) ;

                        break;

                }


            }else{
                // 左右都无值（啥也不做）
            }

        }








        return null;
    }
}
