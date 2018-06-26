package com.yongyida.robot.model.y128.led.control;

import android.content.Context;
import android.graphics.Color;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.led.control.LedSendControlHandler;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.model.agreement.Y128Send;
import com.yongyida.robot.model.agreement.Y128Steering;
import com.yongyida.robot.utils.LogHelper;

import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl.Effect;
import static com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl.Position;



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
 * Create By HuangXiangXiang 2018/6/21
 */
public class Y128LedSendControlHandler extends LedSendControlHandler {

    private static final String TAG = Y128LedSendControlHandler.class.getSimpleName() ;

    private Y128Steering.SteerLed breathLed = new Y128Steering.SteerLed() ;
    private Y128Steering.SteerLed2 breathLed2 = new Y128Steering.SteerLed2() ;
    private Y128Send mSend ;

    public Y128LedSendControlHandler(Context context) {

        super(context);

        mSend = Y128Send.getInstance() ;
    }

    @Override
    public SendResponse onHandler(LedSendControl ledSendControl, IResponseListener responseListener) {

        LogHelper.i(TAG, LogHelper.__TAG__() + ", ledSendControl : " + ledSendControl.toJson()) ;

        Effect e = ledSendControl.getEffect() ;
        if(e == null){

            return null ;
        }

        Position p = ledSendControl.getPosition() ;
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

                breathLed.setSpeed((byte) 60);
                mSend.sendData(breathLed.getCmd()) ;
                break;

            case BREATH_MIDDLE:

                breathLed.setSpeed((byte) 30);
                mSend.sendData(breathLed.getCmd()) ;
                break;

            case BREATH_FAST:

                breathLed.setSpeed((byte) 1);
                mSend.sendData(breathLed.getCmd()) ;
                break;
        }

        return null;
    }


}
