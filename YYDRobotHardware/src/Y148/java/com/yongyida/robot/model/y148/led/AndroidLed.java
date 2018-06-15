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

import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl;
import com.yongyida.robot.dev.DevFile;

/**
 * Create By HuangXiangXiang 2018/6/6
 * 连接在Android 板子上面的呼吸灯
 *
 */
public class AndroidLed {


    private static final String HEAD_LED_ON = "112";  // 开灯
    private static final String HEAD_LED_OFF = "113";  // 关灯
    private static final String HEAD_LED_COLOR = "111";  // 颜色
    private static final String HEAD_LED_BREATH = "114";  // 呼吸


    private int bright = 100 ;

    private int red = 255 ;
    private int green = 255 ;
    private int blue = 255 ;


    /**
     * 控制耳朵呼吸灯
     */
    public void controlLed(LedSendControl ledControl) {

        LedSendControl.Effect effect = ledControl.getEffect();
        if (effect != null) {

            switch (effect) {

                case LED_ON:

                    DevFile.writeString(HEAD_LED_ON);
                    setColor(ledControl);
                    changeColor();

                    break;

                case LED_OFF:

                    DevFile.writeString(HEAD_LED_OFF);

                    break;

                case BREATH_LOW:

                    setColor(ledControl);
                    changeBreath(10);
                    break;

                case BREATH_MIDDLE:

                    setColor(ledControl);
                    changeBreath(5);
                    break;

                case BREATH_FAST:

                    setColor(ledControl);
                    changeBreath(1);
                    break;

                case BREATH:

                    int v = ledControl.getEffectValue();

                    setColor(ledControl);
                    changeBreath(v/ 1000);
                    break;
            }

        }

    }

    private void setColor(LedSendControl ledControl) {

        LedSendControl.Brightness brightness = ledControl.getBrightness();
        if (brightness != null) {
            this.bright = brightness.getValue();
        }

        LedSendControl.Color color = ledControl.getColor();
        if (color != null) {

            this.red = color.getRed();
            this.green = color.getGreen();
            this.blue = color.getBlue();
        }

    }

    private void changeColor(){

        int red = this.red * bright / 100 ;
        int green = this.green * bright / 100 ;
        int blue = this.blue * bright / 100 ;

        String color = HEAD_LED_COLOR + String.format("%03d %03d %03d", red, green, blue);
        DevFile.writeString(color);
    }

    private void changeBreath(int time){

        int red = this.red * bright / 100 ;
        int green = this.green * bright / 100 ;
        int blue = this.blue * bright / 100 ;

        String color = HEAD_LED_BREATH + String.format("%03d %03d %03d %03d",time,red,green,blue);
        DevFile.writeString(color);
    }
}