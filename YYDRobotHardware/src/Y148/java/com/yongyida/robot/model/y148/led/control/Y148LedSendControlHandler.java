package com.yongyida.robot.model.y148.led.control;


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
import com.yongyida.robot.communicate.app.hardware.led.control.LedSendControlHandler;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.model.agreement.Y148Steering;
import com.yongyida.robot.model.y148.led.type.AndroidLed;
import com.yongyida.robot.model.y148.led.type.SerialLed;
import com.yongyida.robot.model.y148.led.type.UsbLed;

/**
 * Create By HuangXiangXiang 2018/6/14
 */
public class Y148LedSendControlHandler extends LedSendControlHandler {


    private final SerialLed mSerialLed ;
    private final AndroidLed mAndroidLed ;
    private final UsbLed mUsbLed ;

    public Y148LedSendControlHandler(Context context) {
        super(context);

        mSerialLed = SerialLed.getInstance() ;
        mAndroidLed = AndroidLed.getInstance() ;
        mUsbLed = UsbLed.getInstance(context) ;
    }


    @Override
    public SendResponse onHandler(LedSendControl ledControl, IResponseListener responseListener) {

        LedSendControl.Position position = ledControl.getPosition() ;

        switch (position){

            case EAR:

                mAndroidLed.controlLed(ledControl);
                break;

            case CHEST:

                return mUsbLed.controlChestLed(ledControl);

            case LEFT_ARM:

                return mUsbLed.controlArmLed(Y148Steering.SingleChip.DIRECTION_LEFT, ledControl);

            case RIGHT_ARM:

                return mUsbLed.controlArmLed(Y148Steering.SingleChip.DIRECTION_RIGHT, ledControl);

            case ARM:

                return mUsbLed.controlArmLed(Y148Steering.SingleChip.DIRECTION_SAME, ledControl);

        }

        return null ;
    }
}
