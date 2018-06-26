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

    private static final int HEAD_LEFT_RIGHT_DISTANCE_MIN       = 500 ;   // 可移动的最小值
    private static final int HEAD_LEFT_RIGHT_DISTANCE_MAX       = 1700 ; // 可移动的最大值

    private static final int HEAD_UP_DOWN_DISTANCE_MIN          = 1300 ;   // 可移动的最小值
    private static final int HEAD_UP_DOWN_DISTANCE_MAX          = 1700 ; // 可移动的最大值


    private static final int SPEED_MIN          = 0 ;   // 可移动的最小速度
    private static final int SPEED_MAX          = 100 ; // 可移动的最大速度


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

                SteeringControl control = headControl.getHeadLeftRightControl();;
                if(control == null){

                    return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR) ;
                }

                return onHandler(true, control, responseListener);

            case UP_DOWN:

                control = headControl.getHeadUpDownControl() ;
                if(control == null){

                    return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR) ;
                }

                return onHandler(false, control, responseListener);

            case CUSTOM:

                break;


        }



        return new SendResponse() ;
    }


    /**脑袋上下 或者 左右运动*/
    public SendResponse onHandler(boolean isLeftRight,SteeringControl control, IResponseListener responseListener) {

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

            case LOOP :

                mode = Y148Steering.SingleChip.MODE_LOOP ;
                modeValue = 0 ;

                type = 0 ;
                typeValue = 0 ;

                break;


            case DISTANCE_TIME :    // 距离时间

                mode = Y148Steering.SingleChip.MODE_DISTANCE_TIME ;
                modeValue = 0 ;

                SteeringControl.Distance distance = control.getDistance() ;
                type = getDistanceType(distance) ;
                if(isLeftRight){

                    typeValue = getLeftRightDistanceValue(distance) ;
                }else {

                    typeValue = getUpDownDistanceValue(distance) ;
                }

                break;

            case DISTANCE_SPEED :

                mode = Y148Steering.SingleChip.MODE_DISTANCE_SPEED ;
                modeValue = 0 ;

                distance = control.getDistance() ;
                type = getDistanceType(distance) ;
                if(isLeftRight){

                    typeValue = getLeftRightDistanceValue(distance) ;
                }else {

                    typeValue = getUpDownDistanceValue(distance) ;
                }

                break;

            default:
                return new SendResponse(SendResponse.RESULT_SERVER_NO_METHOD) ;
        }

        mSteerHeadLeftRight.controlHead(negativeValue,type, mode, typeValue, modeValue,delay);
        mUART.writeData(mSteerHeadLeftRight.getCmd()) ;

        return new SendResponse();
    }


    private byte getNegativeValue(SteeringControl control){

        return control.isNegative() ? Y148Steering.SteerArm.NEGATIVE : Y148Steering.SteerArm.POSITIVE ;
    }

    private byte getDistanceType(SteeringControl.Distance distance){

        if(distance.getType() == SteeringControl.Distance.Type.BY){

            return Y148Steering.SteerArm.TYPE_BY ;
        }

        return Y148Steering.SteerArm.TYPE_TO ;
    }

    private int getLeftRightDistanceValue(SteeringControl.Distance distance){

        SteeringControl.Distance.Unit unit = distance.getUnit() ;
        int value = distance.getValue() ;

        switch (unit){

            case PERCENT:

                value = HEAD_LEFT_RIGHT_DISTANCE_MIN + (HEAD_LEFT_RIGHT_DISTANCE_MAX - HEAD_LEFT_RIGHT_DISTANCE_MIN) * value / 100 ;

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


    private int getUpDownDistanceValue(SteeringControl.Distance distance){

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


    /**
     *
     * */
    private int getSpeedValue(SteeringControl.Speed speed){

        if(speed == null){

            return 2;
        }
        int value = speed.getValue() ;
        switch (speed.getUnit()){

            case PERCENT:

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
