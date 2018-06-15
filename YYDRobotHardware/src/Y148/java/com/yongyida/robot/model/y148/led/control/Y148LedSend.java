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

import com.hiva.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.hardware.led.response.data.LedStatus;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSend2Control;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl;
import com.yongyida.robot.model.y148.led.AndroidLed;
import com.yongyida.robot.model.y148.led.SerialLed;
import com.yongyida.robot.model.y148.led.UsbLed;

/**
 * Create By HuangXiangXiang 2018/6/14
 */
public class Y148LedSend extends BaseControlHandler<LedSendControl>{


    private final SerialLed mSerialLed ;
    private final AndroidLed mAndroidLed ;
    private final UsbLed mUsbLed ;


    private Y148LedSend(SerialLed serialLed, AndroidLed androidLed, UsbLed usbLed ){

        this.mSerialLed = serialLed ;
        this.mAndroidLed = androidLed ;
        this.mUsbLed = usbLed ;
    }


    @Override
    public void onHandler(LedSendControl ledControl, IResponseListener responseListener) {

        LedSendControl.Position position = ledControl.getPosition() ;

        switch (position){

            case EAR:

                mAndroidLed.controlLed(ledControl);
                break;

            case CHEST:

                mUsbLed.controlLed(ledControl);

                break;

            case LEFT_ARM:

                break;

            case RIGHT_ARM:
                break;
            case ARM:
                break;

        }

    }
}
