package com.yongyida.robot.model.y148.montrol.control.item;


/* 
                              _ooOoo_ 
                             o8888888o 
                             88" . "88 
                             (| -_- |) 
                             O\  =  /O 
                          ____/`---'\____ 
                        .'  \\|     |//  `. 
                       /  \\|||  :  |||//  \ 
                      /  _||||| -:- |||||-  \ 
                      |   | \\\  -  /// |   | 
                      | \_|  ''\---/''  |   | 
                      \  .-\__  `-`  ___/-. / 
                    ___`. .'  /--.--\  `. . __ 
                 ."" '<  `.___\_<|>_/___.'  >'"". 
                | | :  `- \`.;`\ _ /`;.`/ - ` : | | 
                \  \ `-.   \_ __\ /__ _/   .-` /  / 
           ======`-.____`-.___\_____/___.-`____.-'====== 
                              `=---=' 
           .............................................  
                    佛祖镇楼                  BUG辟易  
            佛曰:  
                    写字楼里写字间，写字间里程序员；  
                    程序人员写程序，又拿程序换酒钱。  
                    酒醒只在网上坐，酒醉还来网下眠；  
                    酒醉酒醒日复日，网上网下年复年。  
                    但愿老死电脑间，不愿鞠躬老板前；  
                    奔驰宝马贵者趣，公交自行程序员。  
                    别人笑我忒疯癫，我笑自己命太贱；  
                    不见满街漂亮妹，哪个归得程序员？ 
*/

import android.content.Context;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.ArmControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.ArmChangeIdControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.ArmTeacherModeControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.SteeringControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.constant.Direction;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.model.agreement.Y128Steering;
import com.yongyida.robot.model.agreement.Y148Steering;
import com.yongyida.robot.usb_uart.UART;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Create By HuangXiangXiang 2018/6/19
 * 手臂
 */
public class Arm {

    private static final String TAG = Arm.class.getSimpleName() ;

    private static final int SPEED_MIN = 0 ;
    private static final int SPEED_MAX = 15 ;

    private static final int DISTANCE_MIN = 0 ;
    private static final int DISTANCE_MAX = 4096 ;


    private Y148Steering.SteerArm mSteerArm;

    private UART mUART ;

    private static Arm mInstance ;
    public static Arm getInstance(Context context){

        if(mInstance == null){

            mInstance = new Arm(context.getApplicationContext()) ;
        }

        return mInstance ;
    }

    private Arm(Context context){

        mSteerArm = new Y148Steering.SteerArm() ;
        mUART = UART.getInstance(context) ;
    }

    /**
     * 手臂（单独）控制
     * */
    public SendResponse onHandler(byte direction, byte position, SteeringControl control, IResponseListener responseListener) {

        LogHelper.i(TAG, LogHelper.__TAG__());

        return controlArm(direction, position, control);
    }


    /**
     * 手臂（组合）控制
     * */
    public SendResponse onHandler(ArmControl armControl, IResponseListener responseListener) {

        ArmControl.Action action = armControl.getAction() ;
        switch (action){

            case CUSTOM:
                // 自定义控制
                return custom(armControl) ;
            case RESET:
                // 重置
                byte direction = Y148Steering.SingleChip.getDirectionValue(armControl.getDirection()) ;
                return reset(direction) ;
        }

        return null;
    }

    /**
     * 更改手臂ID
     * */
    public SendResponse onHandler(ArmChangeIdControl armChangeIdControl, IResponseListener responseListener) {

        mSteerArm.changeId(armChangeIdControl.getSrcId(), armChangeIdControl.getDestId());
        byte[] data = mSteerArm.getCmd() ;
        mUART.writeData(data) ;

        return new SendResponse();
    }

    /**
     * 自定义手势
     * */
    private SendResponse custom(ArmControl armControl){

        switch (armControl.getDirection()){

            case LEFT:

                ArrayList<SteeringControl> controls = armControl.getArmLefts() ;
                if(controls == null){

                    return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR) ;
                }
                controlArms(Y148Steering.SteerArm.DIRECTION_LEFT, controls) ;


                break;
            case RIGHT:

                controls = armControl.getArmRights() ;
                if(controls == null){

                    return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR) ;
                }
                controlArms(Y148Steering.SteerArm.DIRECTION_RIGHT, controls) ;


                break;

            case SAME:

                controls = armControl.getArmLefts() ;
                if(controls == null){

                    return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR) ;
                }
                controlArms(Y148Steering.SteerArm.DIRECTION_SAME , controls) ;

                break;

            case BOTH:

                controls = armControl.getArmLefts() ;
                if(controls == null){

                    return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR) ;
                }
                controlArms(Y148Steering.SteerArm.DIRECTION_LEFT , controls) ;


                controls = armControl.getArmRights() ;
                if(controls == null){

                    return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR) ;
                }
                controlArms(Y148Steering.SteerArm.DIRECTION_RIGHT, controls) ;

                break;

        }

        return new SendResponse(SendResponse.RESULT_SERVER_NO_CONTROL) ;
    }

    /**
     * 全部重置
     * */
    private SendResponse reset(byte direction){

        final int size = 6 ;        // 关机数量
        Y148Steering.SteerArm.Joint[] joints = new Y148Steering.SteerArm.Joint[size] ;

        for (int i = 0 ; i < size ; i ++){

            byte id = (byte) i;
            byte negativeValue = 0;
//            byte mode = Y148Steering.SteerArm.MODE_RESET ;
//            byte type = 0 ;
//            int modeValue = 0;
//            int typeValue = 0;


            byte mode = Y148Steering.SteerArm.MODE_DISTANCE_TIME ;
            int modeValue = 2000;// speed

            byte type = Y148Steering.SteerArm.TYPE_TO ;
            int typeValue = 2048;

            int delay = 0 ;

            joints[i] = new Y148Steering.SteerArm.Joint(id, negativeValue, type, mode, typeValue,modeValue,delay) ;
        }

        mSteerArm.controlArms(direction,joints);
        mUART.writeData(mSteerArm.getCmd()) ;

        return new SendResponse()  ;
    }



    /**
     * 控制手臂多个舵机
     * */
    private boolean controlArms(byte direction, ArrayList<SteeringControl> controls){

        final int size = controls.size() ;
        Y148Steering.SteerArm.Joint[] joints = new Y148Steering.SteerArm.Joint[size] ;

        for (int i = 0 ; i < size ; i ++){

            SteeringControl control = controls.get(i) ;

            byte id = getPositionValue(control.getPosition()) ;
            byte negativeValue = getNegativeValue(control);
            byte mode ;
            byte type ;
            int modeValue ;
            int typeValue ;

            int delay = control.getDelay() ;

            SteeringControl.Mode mod = control.getMode() ;
            switch (mod){
                case STOP:

                    mode = Y148Steering.SteerArm.MODE_STOP ;
                    modeValue = 0 ;

                    type = 0 ;
                    typeValue = 0 ;

                    break;

                case RESET:

                    mode = Y148Steering.SteerArm.MODE_RESET ;
                    modeValue = 0 ;

                    type = 0 ;
                    typeValue = 0 ;

                    break;

                case LOOP:

                    negativeValue = 0 ;

                    mode = Y148Steering.SteerArm.MODE_LOOP ;
                    modeValue = getSpeedValue(control.getSpeed()) ; //速度值

                    type = 0 ;
                    typeValue = 1 ; //这个值 不能为0 否则不会转动

                    break;
                case DISTANCE_TIME:

                    negativeValue = getNegativeValue(control) ;

                    mode = Y148Steering.SteerArm.MODE_DISTANCE_TIME ;
                    modeValue = getTimeValue(control.getTime()) ; // 表示时间

                    SteeringControl.Distance distance = control.getDistance() ;
                    if(distance.getType() == SteeringControl.Distance.Type.BY){// 偏移量

                        type = Y148Steering.SteerArm.TYPE_BY ;
                        typeValue = getDistanceByValue(distance) ;

                    }else{  // 目标值

                        type = Y148Steering.SteerArm.TYPE_TO ;
                        typeValue = getDistanceToValue(distance) ;
                    }


                    break;
                case DISTANCE_SPEED:

                    negativeValue = getNegativeValue(control) ;

                    mode = Y148Steering.SteerArm.MODE_DISTANCE_SPEED ;
                    modeValue = getSpeedValue(control.getSpeed()) ;// 表示速度

                    distance = control.getDistance() ;
                    if(distance.getType() == SteeringControl.Distance.Type.BY){// 偏移量

                        type = Y148Steering.SteerArm.TYPE_BY ;
                        typeValue = getDistanceByValue(distance) ;

                    }else{  // 目标值

                        type = Y148Steering.SteerArm.TYPE_TO ;
                        typeValue = getDistanceToValue(distance) ;
                    }

                    break;

                default:
                    return false ;
            }

            joints[i] = new Y148Steering.SteerArm.Joint(id, negativeValue, type,mode,typeValue,modeValue,delay) ;
        }

        mSteerArm.controlArms(direction,joints);
        mUART.writeData(mSteerArm.getCmd()) ;

        return true ;
    }


    /**
     * 控制单个手臂舵机
     * */
    private SendResponse controlArm(byte direction, byte position, SteeringControl control){

        Y148Steering.SteerArm.Joint[] joints = new Y148Steering.SteerArm.Joint[1] ;

        byte id = position ;
        byte negativeValue ;
        byte mode ;
        byte type ;
        int modeValue ;
        int typeValue ;

        int delay = control.getDelay() ;

        SteeringControl.Mode mod = control.getMode() ;
        switch (mod){
            case STOP:

                negativeValue = 0 ;

                mode = Y148Steering.SteerArm.MODE_STOP ;
                modeValue = 0 ;

                type = 0 ;
                typeValue = 0 ;

                break;

            case RESET:

                negativeValue = 0 ;

                mode = Y148Steering.SteerArm.MODE_RESET ;
                modeValue = 0 ;

                type = 0 ;
                typeValue = 0 ;

                break;

            case LOOP:

                negativeValue = 0 ;

                mode = Y148Steering.SteerArm.MODE_LOOP ;
                modeValue = getSpeedValue(control.getSpeed()) ; //速度值

                type = 0 ;
                typeValue = 1 ; //这个值 不能为0 否则不会转动

                break;
            case DISTANCE_TIME:

                negativeValue = getNegativeValue(control) ;

                mode = Y148Steering.SteerArm.MODE_DISTANCE_TIME ;
                modeValue = getTimeValue(control.getTime()) ; // 表示时间

                SteeringControl.Distance distance = control.getDistance() ;
                if(distance.getType() == SteeringControl.Distance.Type.BY){// 偏移量

                    type = Y148Steering.SteerArm.TYPE_BY ;
                    typeValue = getDistanceByValue(distance) ;

                }else{  // 目标值

                    type = Y148Steering.SteerArm.TYPE_TO ;
                    typeValue = getDistanceToValue(distance) ;
                }


                break;
            case DISTANCE_SPEED:

                negativeValue = getNegativeValue(control) ;

                mode = Y148Steering.SteerArm.MODE_DISTANCE_SPEED ;
                modeValue = getSpeedValue(control.getSpeed()) ;// 表示速度

                distance = control.getDistance() ;
                if(distance.getType() == SteeringControl.Distance.Type.BY){// 偏移量

                    type = Y148Steering.SteerArm.TYPE_BY ;
                    typeValue = getDistanceByValue(distance) ;

                }else{  // 目标值

                    type = Y148Steering.SteerArm.TYPE_TO ;
                    typeValue = getDistanceToValue(distance) ;
                }

                break;

            default:
                return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR, "Mode 类型不支持") ;
        }

        String msg = String.format(Locale.CHINA,", id:%d, negativeValue:%d, mode:%d, type:%d, modeValue:%d, typeValue:%d, delay:%d",id, negativeValue, mode,type,modeValue,typeValue,delay) ;
        LogHelper.i(TAG ,LogHelper.__TAG__() + msg );

        joints[0] = new Y148Steering.SteerArm.Joint(id, negativeValue, type, mode, typeValue, modeValue, delay) ;

        mSteerArm.controlArms(direction,joints);
        mUART.writeData(mSteerArm.getCmd()) ;

        return new SendResponse() ;
    }


    private byte getPositionValue(SteeringControl.Position position){

        if(position != null) {

            switch (position) {

                case ARM_LEFT_0:
                case ARM_RIGHT_0:
                    return Y148Steering.SteerArm.ARM_0 ;

                case ARM_LEFT_1:
                case ARM_RIGHT_1:
                    return Y148Steering.SteerArm.ARM_1 ;

                case ARM_LEFT_2:
                case ARM_RIGHT_2:
                    return Y148Steering.SteerArm.ARM_2 ;


                case ARM_LEFT_3:
                case ARM_RIGHT_3:
                    return Y148Steering.SteerArm.ARM_3 ;


                case ARM_LEFT_4:
                case ARM_RIGHT_4:
                    return Y148Steering.SteerArm.ARM_4 ;


                case ARM_LEFT_5:
                case ARM_RIGHT_5:
                    return Y148Steering.SteerArm.ARM_5 ;
            }
        }

        return Y148Steering.SteerArm.ARM_0 ;

    }

    private byte getNegativeValue(SteeringControl control){

        return control.isNegative() ? Y148Steering.SteerArm.NEGATIVE : Y148Steering.SteerArm.POSITIVE ;
    }




    private int getDistanceToValue(SteeringControl.Distance distance){

        SteeringControl.Distance.Unit unit = distance.getUnit() ;
        int value = distance.getValue() ;

        switch (unit){

            case PERCENT:

                value = DISTANCE_MIN + (DISTANCE_MAX - DISTANCE_MIN) * value / 100 ;

                break;
            case MM:


                break;
            case CM:


                break;
            case ANGLE:

                break ;
            default:

                break;
        }



        return value ;
    }

    private int getDistanceByValue(SteeringControl.Distance distance){

        SteeringControl.Distance.Unit unit = distance.getUnit() ;
        int value = distance.getValue() ;

        switch (unit){

            case PERCENT:

                value = (DISTANCE_MAX - DISTANCE_MIN) * value / 100 ;

                break;
            case MM:


                break;
            case CM:


                break;
            case ANGLE:

                break ;
            default:

                break;
        }



        return value ;
    }









    /**
     *
     * */
    private int getSpeedValue(SteeringControl.Speed speed){

        if(speed == null){

            return (SPEED_MAX - SPEED_MIN )/2 ;
        }
        int value = speed.getValue() ;
        switch (speed.getUnit()){

            case PERCENT:

                value = SPEED_MIN + (SPEED_MAX - SPEED_MIN) * value / 100 ;

                break;

            case ACCORDING_DISTANCE:

                break;
        }

        if(value < SPEED_MIN){

            value = SPEED_MIN ;

        }else if(value > SPEED_MAX){

            value = SPEED_MAX ;

        }


        return value ;

    }

    private int getTimeValue(SteeringControl.Time time){

        if(time == null){

            return 2000;
        }

        int value = time.getValue() ;
        switch (time.getUnit()){

            case SECOND:

                value *= 1000 ;
                break;

        }

        return value ;
    }


    public SendResponse onHandler(ArmTeacherModeControl armTeacherModeControl, IResponseListener responseListener) {

        if(armTeacherModeControl.isTeacher()){

            mSteerArm.openTeacherMode();
        }else {

            mSteerArm.closeTeacherMode();
        }

        mUART.writeData(mSteerArm.getCmd()) ;
        return new SendResponse() ;
    }
}
