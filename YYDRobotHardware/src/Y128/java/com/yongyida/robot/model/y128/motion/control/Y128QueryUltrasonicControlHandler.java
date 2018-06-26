package com.yongyida.robot.model.y128.motion.control;


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
import com.yongyida.robot.communicate.app.hardware.motion.control.QueryUltrasonicControlHandler;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.QueryUltrasonicControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.model.agreement.Y128Receive;
import com.yongyida.robot.model.agreement.Y128Send;
import com.yongyida.robot.model.agreement.Y128Steering;

/**
 * Create By HuangXiangXiang 2018/6/22
 */
public class Y128QueryUltrasonicControlHandler extends QueryUltrasonicControlHandler {

    private Y128Send mSend = Y128Send.getInstance();
    private Y128Steering.Ultrasonic mUltrasonic = new Y128Steering.Ultrasonic() ;

    public Y128QueryUltrasonicControlHandler(Context context) {
        super(context);
    }

    @Override
    protected void setListenUltrasonic(OnUltrasonicChangedListener onUltrasonicChangedListener) {

        Y128Receive.getInstance().setOnUltrasonicChangedListener(onUltrasonicChangedListener);
    }

    @Override
    public SendResponse onHandler(QueryUltrasonicControl queryUltrasonicControl, IResponseListener responseListener) {

        QueryUltrasonicControl.Android android = queryUltrasonicControl.getAndroid() ;
        if(android != null){
            switch (android){

                case SEND:
                    mUltrasonic.setSendAndroidMode(Y128Steering.Ultrasonic.MODE_ANDROID_SEND);
                    break;

                case NO_SEND:
                    mUltrasonic.setSendAndroidMode(Y128Steering.Ultrasonic.MODE_ANDROID_NO_SEND);
                    break;

                case DEFAULT:
                    mUltrasonic.setSendAndroidMode(Y128Steering.Ultrasonic.MODE_ANDROID_DEFAULT);
                    break;
            }
        }

        QueryUltrasonicControl.Slam slam = queryUltrasonicControl.getSlam() ;
        if(slam != null){
            switch (slam){

                case SEND:
                    mUltrasonic.setSendAndroidMode(Y128Steering.Ultrasonic.MODE_SLAM_SEND);
                    break;

                case NO_SEND:
                    mUltrasonic.setSendAndroidMode(Y128Steering.Ultrasonic.MODE_SLAM_NO_SEND);
                    break;

                case DEFAULT:
                    mUltrasonic.setSendAndroidMode(Y128Steering.Ultrasonic.MODE_SLAM_DEFAULT);
                    break;
            }
        }

        mSend.sendData(mUltrasonic.getCmd()) ;

        return super.onHandler(queryUltrasonicControl, responseListener);
    }
}
