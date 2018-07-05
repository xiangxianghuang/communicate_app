package com.yongyida.robot.model.y128.led.type;


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

import android.graphics.Color;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.model.agreement.Y128Send;
import com.yongyida.robot.model.agreement.Y128Steering;
import com.yongyida.robot.utils.LogHelper;

/**
 * Create By HuangXiangXiang 2018/7/4
 */
public class Led {

    private static final String TAG = Led.class.getSimpleName() ;

    private static final byte BREATH_SPEED_MIN          = 60 ;
    private static final byte BREATH_SPEED_MAX          = 1 ;

    public static final byte BREATH_SPEED_LOW          = 60 ;
    public static final byte BREATH_SPEED_MIDDLE       = 30 ;
    public static final byte BREATH_SPEED_FAST         = 1 ;

    private Y128Steering.SteerLed breathLed = new Y128Steering.SteerLed() ;
    private Y128Steering.SteerLed2 breathLed2 = new Y128Steering.SteerLed2() ;
    private Y128Send mSend ;

    private static Led mLed ;
    public static Led getInstance(){

        if(mLed == null){

            mLed = new Led() ;
        }
        return mLed ;
    }


    private Led() {

        mSend = Y128Send.getInstance() ;
    }

    public SendResponse onHandler(LedSendControl ledSendControl, IResponseListener responseListener) {

        LogHelper.i(TAG, LogHelper.__TAG__() + ", ledSendControl : " + ledSendControl.toJson()) ;

        LedSendControl.Effect e = ledSendControl.getEffect() ;
        if(e == null){

            return null ;
        }

        LedSendControl.Position p = ledSendControl.getPosition() ;
        switch (p){

            case LEFT_EAR :

                breathLed.setPosition(Y128Steering.SteerLed.POSITION_LEFT_EAR);
                breathLed2.setPosition(Y128Steering.SteerLed.POSITION_LEFT_EAR);
                break;

            case RIGHT_EAR :

                breathLed.setPosition(Y128Steering.SteerLed.POSITION_RIGHT_EAR);
                breathLed2.setPosition(Y128Steering.SteerLed.POSITION_RIGHT_EAR);
                break;

            case CHEST :

                breathLed.setPosition(Y128Steering.SteerLed.POSITION_CHEST);
                breathLed2.setPosition(Y128Steering.SteerLed.POSITION_CHEST);
                break;

        }

        LedSendControl.Color c = ledSendControl.getColor() ;
        if(c != null){

            int value = (0xFF<< 24 )|c.getColor() ;
            switch (value){

                case Color.RED:

                    breathLed.setColor(Y128Steering.SteerLed.COLOR_RED);
                    breathLed2.setColor(Y128Steering.SteerLed.COLOR_RED);
                    break;

                case Color.GREEN:

                    breathLed.setColor(Y128Steering.SteerLed.COLOR_GREEN);
                    breathLed2.setColor(Y128Steering.SteerLed.COLOR_GREEN);
                    break;

                case Color.BLUE:

                    breathLed.setColor(Y128Steering.SteerLed.COLOR_BLUE);
                    breathLed2.setColor(Y128Steering.SteerLed.COLOR_BLUE);
                    break;
            }
        }

        switch (e){

            case NORMAL:
            case LED_ON:

                breathLed2.setOn(true);
                mSend.sendData(breathLed2.getCmd()) ;
                break;

            case LED_OFF:

                breathLed2.setOn(false);
                mSend.sendData(breathLed2.getCmd()) ;
                break ;

            case BREATH_LOW:

                breathLed.setSpeed(BREATH_SPEED_LOW);
                mSend.sendData(breathLed.getCmd()) ;
                break;

            case BREATH_MIDDLE:

                breathLed.setSpeed(BREATH_SPEED_MIDDLE);
                mSend.sendData(breathLed.getCmd()) ;
                break;

            case BREATH_FAST:

                breathLed.setSpeed(BREATH_SPEED_FAST);
                mSend.sendData(breathLed.getCmd()) ;
                break;
        }

        return null;
    }



    public void setChestOn(int color){

        breathLed.setPosition(Y128Steering.SteerLed.POSITION_CHEST);
        breathLed2.setOn(true);
        switch (color){

            case Color.RED:

                breathLed2.setColor(Y128Steering.SteerLed.COLOR_RED);
                break;

            case Color.GREEN:

                breathLed2.setColor(Y128Steering.SteerLed.COLOR_GREEN);
                break;

            case Color.BLUE:

                breathLed2.setColor(Y128Steering.SteerLed.COLOR_BLUE);
                break;
        }

        mSend.sendData(breathLed2.getCmd()) ;

    }

    public void setChestBreath(byte speed , int color){

        breathLed.setPosition(Y128Steering.SteerLed.POSITION_CHEST);
        breathLed.setSpeed(speed);
        switch (color){

            case Color.RED:

                breathLed.setColor(Y128Steering.SteerLed.COLOR_RED);
                break;

            case Color.GREEN:

                breathLed.setColor(Y128Steering.SteerLed.COLOR_GREEN);
                break;

            case Color.BLUE:

                breathLed.setColor(Y128Steering.SteerLed.COLOR_BLUE);
                break;
        }

        mSend.sendData(breathLed.getCmd()) ;
    }






}