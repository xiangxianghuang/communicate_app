package com.yongyida.robot.model.y148.led;


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

import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl;
import com.yongyida.robot.model.agreement.Y138Steering;
import com.yongyida.robot.usb_uart.UART;

/**
 * Create By HuangXiangXiang 2018/6/6
 */
public class UsbLed {

    private UART mUART ;
    private Y138Steering.SteerLed mSteerLed = new Y138Steering.SteerLed() ;

    UsbLed(Context context){

        mUART = UART.getInstance(context) ;
    }


    public void controlLed(LedSendControl ledControl) {

        LedSendControl.Effect effect = ledControl.getEffect();
        if (effect != null) {

            switch (effect) {

                case LED_ON:

                    setColor(ledControl);

                    mSteerLed.setLedOnMode();
                    byte[] data = Y138Steering.getCmd(mSteerLed);
                    mUART.writeData(data);
                    break;

                case LED_OFF:

                    mSteerLed.setLedOffMode();
                    data = Y138Steering.getCmd(mSteerLed);
                    mUART.writeData(data);
                    break;

                case BREATH_LOW:

                    setColor(ledControl);

                    mSteerLed.setLedBreathMode(16000);
                    data = Y138Steering.getCmd(mSteerLed);
                    mUART.writeData(data);
                    break;

                case BREATH_MIDDLE:

                    setColor(ledControl);

                    mSteerLed.setLedBreathMode(2500);
                    data = Y138Steering.getCmd(mSteerLed);
                    mUART.writeData(data);
                    break;

                case BREATH_FAST:

                    setColor(ledControl);

                    mSteerLed.setLedBreathMode(500);
                    data = Y138Steering.getCmd(mSteerLed);
                    mUART.writeData(data);
                    break;

                case BREATH:

                    setColor(ledControl);

                    int v = ledControl.getEffectValue();
                    mSteerLed.setLedBreathMode(v);
                    data = Y138Steering.getCmd(mSteerLed);
                    mUART.writeData(data);

                    break;
            }

        }
    }


    private void setColor (LedSendControl ledControl){

        int b;
        LedSendControl.Brightness brightness = ledControl.getBrightness();
        if (brightness != null) {
            b = brightness.getValue();
        } else {

            b = 100;
        }

        LedSendControl.Color color = ledControl.getColor();
        if (color != null) {

            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();

            red = red * b / 100;
            green = green * b / 100;
            blue = blue * b / 100;

            mSteerLed.setColor((byte) red, (byte) green, (byte) blue);
        }
    }


}
