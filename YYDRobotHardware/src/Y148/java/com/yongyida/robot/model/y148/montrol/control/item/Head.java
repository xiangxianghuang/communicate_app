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
import com.yongyida.robot.communicate.app.hardware.motion.send.data.HeadControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.SteeringControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.model.agreement.Y148Steering;
import com.yongyida.robot.usb_uart.UART;

/**
 * Create By HuangXiangXiang 2018/6/19
 * 头部左右运动
 */
public class Head {

    private static final int HEAD_LEFT_RIGHT_DISTANCE_MIN       = 500 ;     // 可移动的最小值
    private static final int HEAD_LEFT_RIGHT_DISTANCE_MAX       = 2500 ;    // 可移动的最大值

    private static final int HEAD_UP_DOWN_DISTANCE_MIN          = 1300 ;   // 可移动的最小值
    private static final int HEAD_UP_DOWN_DISTANCE_MAX          = 1700 ;    // 可移动的最大值


    private static final int SPEED_MIN                          = 0 ;   // 可移动的最小速度
    private static final int SPEED_MAX                          = 15 ;  // 可移动的最大速度


    private Y148Steering.SteerHead mSteerHeadLeftRight ;
    private Y148Steering.SteerHead mSteerHeadUpDown ;
    private UART mUART ;

    private static Head mInstance ;
    public static Head getInstance(Context context){

        if(mInstance == null){

            mInstance = new Head(context.getApplicationContext()) ;
        }

        return mInstance ;
    }

    private Head(Context context){

        mUART = UART.getInstance(context) ;

        mSteerHeadLeftRight = new Y148Steering.SteerHead(Y148Steering.TYPE_HEAD_LEFT_RIGHT) ;
        mSteerHeadUpDown = new Y148Steering.SteerHead(Y148Steering.TYPE_HEAD_UP_DOWN) ;
    }


    public SendResponse onHandler(HeadControl headControl, IResponseListener responseListener) {

        HeadControl.Action action = headControl.getAction() ;
        switch (action){

            case LEFT_RIGHT:

                SteeringControl control = headControl.getHeadLeftRightControl();
                return onHandler(true, control, responseListener);

            case UP_DOWN:

                control = headControl.getHeadUpDownControl();
                return onHandler(false, control, responseListener);

            case CUSTOM:

                control = headControl.getHeadLeftRightControl();
                SendResponse leftRight = onHandler(true, control, responseListener);

                control = headControl.getHeadUpDownControl();
                SendResponse upDown = onHandler(false, control, responseListener);

                if(leftRight.getResult() == SendResponse.RESULT_SUCCESS && upDown.getResult() == SendResponse.RESULT_SUCCESS){


                    return leftRight ;
                }

                if(leftRight.getResult() != SendResponse.RESULT_SUCCESS){

                    return leftRight ;
                }

                return upDown ;

        }

        return new SendResponse() ;
    }


    /**脑袋上下 或者 左右运动*/
    public SendResponse onHandler(boolean isLeftRight,SteeringControl control, IResponseListener responseListener) {

        if(control == null || !control.isControl()){

            return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR, "数据为空" ) ;
        }

        byte negativeValue = getNegativeValue(control);
        byte mode ;
        byte type ;
        int modeValue ;
        int typeValue ;

        int delay = control.getDelay() ;

        switch (control.getMode()){

            case STOP :

                mode = Y148Steering.SingleChip.MODE_STOP ;
                modeValue = 0 ;

                type = 0 ;
                typeValue = 0 ;

                break;

            case RESET :

                mode = Y148Steering.SingleChip.MODE_RESET ;
                modeValue = 0 ;

                type = 0 ;
                typeValue = 0 ;

                break;

            case LOOP :     // 循环

                mode = Y148Steering.SingleChip.MODE_LOOP ;
                modeValue = getSpeedValue(control.getSpeed()) ;// 速度值

                type = 0 ;
                typeValue = 1 ; //这个值 不能为0 否则不会转动

                break;

            case DISTANCE_TIME :    // 距离 时间

                mode = Y148Steering.SingleChip.MODE_DISTANCE_TIME ;
                modeValue = getTimeValue(control.getTime()) ;     // 表示时间

                SteeringControl.Distance distance = control.getDistance() ;
                if(distance.getType() == SteeringControl.Distance.Type.BY){// 偏移量

                    type = Y148Steering.SteerArm.TYPE_BY ;
                    if(isLeftRight){

                        typeValue = getLeftRightDistanceByValue(distance) ;
                    }else {

                        typeValue = getUpDownDistanceByValue(distance) ;
                    }

                }else{  // 目标值

                    type = Y148Steering.SteerArm.TYPE_TO ;

                    if(isLeftRight){

                        typeValue = getLeftRightDistanceToValue(distance) ;
                    }else {

                        typeValue = getUpDownDistanceToValue(distance) ;
                    }
                }

                break;

            case DISTANCE_SPEED :

                mode = Y148Steering.SingleChip.MODE_DISTANCE_SPEED ;
                modeValue = getSpeedValue(control.getSpeed()) ;     // 表示速度

                distance = control.getDistance() ;
                if(distance.getType() == SteeringControl.Distance.Type.BY){// 偏移量

                    type = Y148Steering.SteerArm.TYPE_BY ;
                    if(isLeftRight){

                        typeValue = getLeftRightDistanceByValue(distance) ;
                    }else {

                        typeValue = getUpDownDistanceByValue(distance) ;
                    }

                }else{  // 目标值

                    type = Y148Steering.SteerArm.TYPE_TO ;

                    if(isLeftRight){

                        typeValue = getLeftRightDistanceToValue(distance) ;
                    }else {

                        typeValue = getUpDownDistanceToValue(distance) ;
                    }

                }

                break;

            default:
                return new SendResponse(SendResponse.RESULT_SERVER_NO_METHOD) ;
        }

        if(isLeftRight){

            mSteerHeadLeftRight.controlHead(negativeValue,type, mode, typeValue, modeValue,delay);
            mUART.writeData(mSteerHeadLeftRight.getCmd()) ;

        }else {

            mSteerHeadUpDown.controlHead(negativeValue,type, mode, typeValue, modeValue,delay);
            mUART.writeData(mSteerHeadUpDown.getCmd()) ;
        }

        return new SendResponse();
    }


    private byte getNegativeValue(SteeringControl control){

        return control.isNegative() ? Y148Steering.SteerArm.NEGATIVE : Y148Steering.SteerArm.POSITIVE ;
    }


    private int getLeftRightDistanceToValue(SteeringControl.Distance distance){

        SteeringControl.Distance.Unit unit = distance.getUnit() ;
        int value = distance.getValue() ;

        switch (unit){

            case PERCENT:

                value = HEAD_LEFT_RIGHT_DISTANCE_MIN + (HEAD_LEFT_RIGHT_DISTANCE_MAX - HEAD_LEFT_RIGHT_DISTANCE_MIN) * value  / 100 ;

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

    private int getLeftRightDistanceByValue(SteeringControl.Distance distance){

        SteeringControl.Distance.Unit unit = distance.getUnit() ;
        int value = distance.getValue() ;

        switch (unit){

            case PERCENT:

                value = (HEAD_LEFT_RIGHT_DISTANCE_MAX - HEAD_LEFT_RIGHT_DISTANCE_MIN) * value / 100 ;

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

    private int getUpDownDistanceToValue(SteeringControl.Distance distance){

        SteeringControl.Distance.Unit unit = distance.getUnit() ;
        int value = distance.getValue() ;

        switch (unit){

            case PERCENT:
                value = HEAD_UP_DOWN_DISTANCE_MIN + (HEAD_UP_DOWN_DISTANCE_MAX - HEAD_UP_DOWN_DISTANCE_MIN) * value / 100 ;

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

    private int getUpDownDistanceByValue(SteeringControl.Distance distance){

        SteeringControl.Distance.Unit unit = distance.getUnit() ;
        int value = distance.getValue() ;

        switch (unit){

            case PERCENT:

                value = (HEAD_UP_DOWN_DISTANCE_MAX - HEAD_UP_DOWN_DISTANCE_MIN) * value / 100 ;
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
     * 转换成对应真实的速度值
     * */
    private int getSpeedValue(SteeringControl.Speed speed){

        if(speed == null){

            return (SPEED_MIN + SPEED_MAX) / 2 ;
        }
        int value = speed.getValue() ;
        switch (speed.getUnit()){

            case ORIGINAL:

                break;

            case PERCENT:

                value = SPEED_MIN + (SPEED_MAX - SPEED_MIN ) * value / 100 ;
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
