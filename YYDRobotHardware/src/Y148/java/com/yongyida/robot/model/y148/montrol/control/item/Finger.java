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

import com.google.gson.Gson;
import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.FingerControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.SteeringControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.constant.Direction;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.model.agreement.Y148Steering;
import com.yongyida.robot.usb_uart.UART;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Create By HuangXiangXiang 2018/6/19
 */
public class Finger {

    private static final String TAG = Finger.class.getSimpleName() ;

    private static final int SPEED_MIN = 0 ;
    private static final int SPEED_MAX = 15 ;

    private static final int DISTANCE_MIN = 0 ;
    private static final int DISTANCE_MAX = 2000 ;

    private UART mSend ;

    private Y148Steering.SteerFinger mSteerFinger = new Y148Steering.SteerFinger() ;


    private static Finger mInstance ;
    public static Finger getInstance(Context context){

        if(mInstance == null){

            mInstance = new Finger(context.getApplicationContext()) ;
        }

        return mInstance ;
    }

    private Finger(Context context){

        mSend = UART.getInstance(context) ;
    }


    public SendResponse onHandler(SteeringControl control, IResponseListener responseListener) {

        return null;
    }

    /**
     * 手指控制
     * */
    public SendResponse onHandler(FingerControl fingerControl, IResponseListener responseListener) {

        FingerControl.Action action = fingerControl.getAction() ;
        if(action == null){

            return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR , "action 不能为空" ) ;
        }

        switch (action){

            case CUSTOM:
                return custom(fingerControl);
            case STOP:
                return preFinger(fingerControl.getDirection(), Y148Steering.SteerFinger.FINGER_STOP) ;
            case RESET:
                return preFinger(fingerControl.getDirection(), Y148Steering.SteerFinger.FINGER_RESET) ;
            case MARK_FIST:
                return preFinger(fingerControl.getDirection(), Y148Steering.SteerFinger.FINGER_MARK_FIST) ;
            case GOOD:
                return preFinger(fingerControl.getDirection(), Y148Steering.SteerFinger.FINGER_GOOD) ;
            case OK:
                return preFinger(fingerControl.getDirection(), Y148Steering.SteerFinger.FINGER_OK) ;
            case ROCK:
                return preFinger(fingerControl.getDirection(), Y148Steering.SteerFinger.FINGER_ROCK) ;
            case SCISSORS:
                return preFinger(fingerControl.getDirection(), Y148Steering.SteerFinger.FINGER_SCISSORS) ;
            case PAPER:
                return preFinger(fingerControl.getDirection(), Y148Steering.SteerFinger.FINGER_PAPER) ;
            case HAND_SHAKE:
                return preFinger(fingerControl.getDirection(), Y148Steering.SteerFinger.FINGER_HAND_SHAKE) ;
            case SHOW_LOVE:
                return preFinger(fingerControl.getDirection(), Y148Steering.SteerFinger.FINGER_SHOW_LOVE) ;
            case FINGER_WHEEL:
                return preFinger(fingerControl.getDirection(), Y148Steering.SteerFinger.FINGER_FINGER_WHEEL) ;
            default:
                return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR , "此action 不能处理空" ) ;
        }
    }

    /**
     * 自定义控制
     * */
    private SendResponse custom(FingerControl fingerControl) {

        switch (fingerControl.getDirection()){

            case LEFT:

                ArrayList<SteeringControl> controls = fingerControl.getFingerLefts() ;
                if(controls == null){

                    return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR) ;
                }
                controlFingers(Y148Steering.SteerArm.DIRECTION_LEFT, controls) ;


                break;
            case RIGHT:

                controls = fingerControl.getFingerRights() ;
                if(controls == null){

                    return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR) ;
                }
                controlFingers(Y148Steering.SteerArm.DIRECTION_RIGHT, controls) ;


                break;

            case SAME:

                controls = fingerControl.getFingerLefts() ;
                if(controls == null){

                    return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR) ;
                }
                controlFingers(Y148Steering.SteerArm.DIRECTION_SAME , controls) ;

                break;

            case BOTH:

                controls = fingerControl.getFingerLefts() ;
                if(controls == null){

                    return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR) ;
                }

                controlFingers(Y148Steering.SteerArm.DIRECTION_LEFT , controls) ;

                controls = fingerControl.getFingerRights() ;
                if(controls == null){

                    return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR) ;
                }
                controlFingers(Y148Steering.SteerArm.DIRECTION_RIGHT, controls) ;

                break;

        }

        return new SendResponse(SendResponse.RESULT_SERVER_NO_CONTROL) ;

    }



    /**
     * 定义的动作（具体动作类型存在单片机里面）
     * */
    private SendResponse preFinger(Direction direction, byte preFinger) {

        mSteerFinger.controlPreFinger(Y148Steering.SingleChip.getDirectionValue(direction), preFinger);
        mSend.writeData(mSteerFinger.getCmd()) ;

        return new SendResponse() ;
    }


    public SendResponse onHandler(byte direction, byte position, SteeringControl control, IResponseListener responseListener) {


        return controlFinger(direction, position,control);
    }


    private boolean controlFingers(byte direction, ArrayList<SteeringControl> controls) {

        final int size = controls.size() ;
        Y148Steering.SteerArm.Joint[] joints = new Y148Steering.SteerArm.Joint[size] ;

        Gson gson = new Gson() ;
        for (int i = 0 ; i < size ; i ++){

            SteeringControl control = controls.get(i) ;

            LogHelper.i(TAG, "control : " + gson.toJson(control));

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

            String msg = String.format(Locale.CHINA,", id:%d, negativeValue:%d, mode:%d, type:%d, modeValue:%d, typeValue:%d, delay:%d",id, negativeValue, mode,type,modeValue,typeValue,delay) ;
            LogHelper.i(TAG ,LogHelper.__TAG__() + msg );
            joints[i] = new Y148Steering.SteerArm.Joint(id, negativeValue, type,mode,typeValue,modeValue,delay) ;
        }

        mSteerFinger.controlFingers(direction,joints);
        mSend.writeData(mSteerFinger.getCmd()) ;

        return true ;
    }

    private SendResponse controlFinger(byte direction, byte position, SteeringControl control) {

        Y148Steering.SingleChip.Joint[] joints = new Y148Steering.SingleChip.Joint[1] ;

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

        mSteerFinger.controlFingers(direction,joints);
        mSend.writeData(mSteerFinger.getCmd()) ;

        return new SendResponse() ;
    }


    private byte getNegativeValue(SteeringControl control){

        return control.isNegative() ? Y148Steering.SteerArm.NEGATIVE : Y148Steering.SteerArm.POSITIVE ;
    }


    private byte getPositionValue(SteeringControl.Position position){

        if(position != null) {

            switch (position) {

                case FINGER_LEFT_0:
                case FINGER_RIGHT_0:
                    return Y148Steering.SteerArm.FINGER_0 ;

                case FINGER_LEFT_1:
                case FINGER_RIGHT_1:
                    return Y148Steering.SteerArm.FINGER_1 ;

                case FINGER_LEFT_2:
                case FINGER_RIGHT_2:
                    return Y148Steering.SteerArm.FINGER_2 ;

                case FINGER_LEFT_3:
                case FINGER_RIGHT_3:
                    return Y148Steering.SteerArm.FINGER_3 ;

                case FINGER_LEFT_4:
                case FINGER_RIGHT_4:
                    return Y148Steering.SteerArm.FINGER_4 ;

            }
        }

        return Y148Steering.SteerArm.ARM_0 ;

    }


    private int getDistanceToValue(SteeringControl.Distance distance){

        SteeringControl.Distance.Unit unit = distance.getUnit() ;
        int value = distance.getValue() ;

        switch (unit){

            case PERCENT:

                value = DISTANCE_MIN + (DISTANCE_MAX - DISTANCE_MIN) * (value) / 100 ;

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


}
