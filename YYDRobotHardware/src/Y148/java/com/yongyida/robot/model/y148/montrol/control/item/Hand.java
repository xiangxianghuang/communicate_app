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
import com.yongyida.robot.communicate.app.hardware.motion.send.data.FingerControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.HandControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.constant.Direction;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.model.agreement.Y148Steering;
import com.yongyida.robot.usb_uart.UART;

/**
 * Create By HuangXiangXiang 2018/6/20
 * 手
 */
public class Hand {

    private Y148Steering.SteerAction mSteerAction ;

    private UART mUART ;

    private Arm mArm ;
    private Finger mFinger ;


    private static Hand mInstance ;
    public static Hand getInstance(Context context){

        if(mInstance == null){

            mInstance = new Hand(context.getApplicationContext()) ;
        }

        return mInstance ;
    }

    private Hand(Context context){

        mSteerAction = new Y148Steering.SteerAction() ;
        mUART = UART.getInstance(context) ;

        mArm = Arm.getInstance(context) ;
        mFinger = Finger.getInstance(context) ;

    }

    public SendResponse onHandler(HandControl handControl, IResponseListener responseListener) {

        HandControl.Action action = handControl.getAction() ;
        if(action == null){

            return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR , "Action 不能为空") ;
        }

        switch (action) {

            case CUSTOM:
                return custom(handControl, responseListener);
            case RESET:
                return preHandGuest(Y148Steering.SteerAction.HAND_RESET, getDirectionValue(handControl.getDirection()));
            case MARK_FIST:
                return preHandGuest(Y148Steering.SteerAction.HAND_MARK_FIST, getDirectionValue(handControl.getDirection()));
            case FINGER_WHEEL:
                return preHandGuest(Y148Steering.SteerAction.HAND_FINGER_WHEEL, getDirectionValue(handControl.getDirection()));
            case HAND_SHAKE:
                return preHandGuest(Y148Steering.SteerAction.HAND_HAND_SHAKE, getDirectionValue(handControl.getDirection()));
            case OK:
                return preHandGuest(Y148Steering.SteerAction.HAND_OK, getDirectionValue(handControl.getDirection()));
            case GOOD:
                return preHandGuest(Y148Steering.SteerAction.HAND_GOOD, getDirectionValue(handControl.getDirection()));
            case ROCK:
                return preHandGuest(Y148Steering.SteerAction.HAND_ROCK, getDirectionValue(handControl.getDirection()));
            case SCISSORS:
                return preHandGuest(Y148Steering.SteerAction.HAND_SCISSORS, getDirectionValue(handControl.getDirection()));
            case PAPER:
                return preHandGuest(Y148Steering.SteerAction.HAND_PAPER, getDirectionValue(handControl.getDirection()));
            case SHOW_WELCOME:
                return preHandGuest(Y148Steering.SteerAction.HAND_SHOW_WELCOME, getDirectionValue(handControl.getDirection()));
            case SHOW_WAVE:
                return preHandGuest(Y148Steering.SteerAction.HAND_SHOW_WAVE, getDirectionValue(handControl.getDirection()));
            case SHOW_LOVE:
                return preHandGuest(Y148Steering.SteerAction.HAND_SHOW_LOVE, getDirectionValue(handControl.getDirection()));
            case SHOW_666:
                return preHandGuest(Y148Steering.SteerAction.HAND_SHOW_666, getDirectionValue(handControl.getDirection()));
            case SHOW_SELF:
                return preHandGuest(Y148Steering.SteerAction.HAND_SHOW_SELF, getDirectionValue(handControl.getDirection()));
            default:
                return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR, "Action 参数正确");
        }
    }


    private SendResponse custom(HandControl handControl, IResponseListener responseListener){

        SendResponse armSendResponse = null ;
        SendResponse fingerSendResponse = null ;

        ArmControl armControl = handControl.getArmControl() ;
        if(armControl != null && armControl.isControl()){

            armSendResponse = mArm.onHandler(armControl, responseListener);
        }

        FingerControl fingerControl = handControl.getFingerControl() ;
        if(fingerControl != null && fingerControl.isControl()){

            fingerSendResponse = mFinger.onHandler(fingerControl, responseListener);
        }

        return null ;
    }


    private SendResponse preHandGuest(byte guest, byte direction){

        mSteerAction.setData(guest,direction);
        mUART.writeData(mSteerAction.getCmd()) ;

        return new SendResponse() ;
    }


    private byte getDirectionValue(Direction direction) {

        if (direction != null) {

            switch (direction) {
                case LEFT:

                    return Y148Steering.SingleChip.DIRECTION_LEFT;
                case RIGHT:

                    return Y148Steering.SingleChip.DIRECTION_RIGHT;
                case SAME:
                case BOTH:

                    return Y148Steering.SingleChip.DIRECTION_SAME;
            }
        }
        return Y148Steering.SingleChip.DIRECTION_LEFT;
    }

}
