package com.yongyida.robot.model.y148.montrol.control;

import android.content.Context;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.motion.control.SteeringControlHandler;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.SteeringControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.model.agreement.Y148Steering;
import com.yongyida.robot.model.y148.montrol.control.item.Arm;
import com.yongyida.robot.model.y148.montrol.control.item.Finger;
import com.yongyida.robot.model.y148.montrol.control.item.Head;



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

/**
 * Create By HuangXiangXiang 2018/6/19
 * 舵机的控制
 */
public class Y148SteeringControlHandler extends SteeringControlHandler {


    private Head mHead ;
    private Arm mArm ;
    private Finger mFinger ;



    public Y148SteeringControlHandler(Context context) {
        super(context);

        mHead = Head.getInstance(context) ;
        mArm = Arm.getInstance(context) ;
        mFinger = Finger.getInstance(context) ;

    }

    @Override
    public SendResponse onHandler(SteeringControl control, IResponseListener responseListener) {

        switch (control.getPosition()){

            case HEAD_LEFT_RIGHT:
                return mHead.onHandler(true, control, responseListener) ;

            case HEAD_UP_DOWN:
                return mHead.onHandler(false, control, responseListener) ;

            case ARM_LEFT_0:
                return mArm.onHandler(Y148Steering.SteerArm.DIRECTION_LEFT, Y148Steering.SteerArm.ARM_0,control, responseListener);
            case ARM_RIGHT_0:
                return mArm.onHandler(Y148Steering.SteerArm.DIRECTION_LEFT, Y148Steering.SteerArm.ARM_0,control, responseListener);
            case ARM_LEFT_1:
                return mArm.onHandler(Y148Steering.SteerArm.DIRECTION_LEFT, Y148Steering.SteerArm.ARM_0,control, responseListener);
            case ARM_RIGHT_1:
                return mArm.onHandler(Y148Steering.SteerArm.DIRECTION_LEFT, Y148Steering.SteerArm.ARM_0,control, responseListener);
            case ARM_LEFT_2:
                return mArm.onHandler(Y148Steering.SteerArm.DIRECTION_LEFT, Y148Steering.SteerArm.ARM_0,control, responseListener);
            case ARM_RIGHT_2:
                return mArm.onHandler(Y148Steering.SteerArm.DIRECTION_LEFT, Y148Steering.SteerArm.ARM_0,control, responseListener);
            case ARM_LEFT_3:
                return mArm.onHandler(Y148Steering.SteerArm.DIRECTION_LEFT, Y148Steering.SteerArm.ARM_0,control, responseListener);
            case ARM_RIGHT_3:
                return mArm.onHandler(Y148Steering.SteerArm.DIRECTION_LEFT, Y148Steering.SteerArm.ARM_0,control, responseListener);
            case ARM_LEFT_4:
                return mArm.onHandler(Y148Steering.SteerArm.DIRECTION_LEFT, Y148Steering.SteerArm.ARM_0,control, responseListener);
            case ARM_RIGHT_4:
                return mArm.onHandler(Y148Steering.SteerArm.DIRECTION_LEFT, Y148Steering.SteerArm.ARM_0,control, responseListener);
            case ARM_LEFT_5:
                return mArm.onHandler(Y148Steering.SteerArm.DIRECTION_LEFT, Y148Steering.SteerArm.ARM_5,control, responseListener);
            case ARM_RIGHT_5:
                return mArm.onHandler(Y148Steering.SteerArm.DIRECTION_RIGHT, Y148Steering.SteerArm.ARM_5, control, responseListener);

            case FINGER_LEFT_0:
                return mFinger.onHandler(Y148Steering.SteerArm.DIRECTION_RIGHT, Y148Steering.SteerArm.ARM_5, control, responseListener);
            case FINGER_LEFT_1:
            case FINGER_LEFT_2:
            case FINGER_LEFT_3:
            case FINGER_LEFT_4:
            case FINGER_RIGHT_0:
            case FINGER_RIGHT_1:
            case FINGER_RIGHT_2:
            case FINGER_RIGHT_3:
            case FINGER_RIGHT_4:
                return mFinger.onHandler(control, responseListener);

                default:
                    return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR, "不能处理对应的位置" ) ;
        }
    }
}
