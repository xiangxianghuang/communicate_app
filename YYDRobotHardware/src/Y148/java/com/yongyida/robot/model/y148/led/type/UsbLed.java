package com.yongyida.robot.model.y148.led.type;


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
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.model.agreement.Y148Steering;
import com.yongyida.robot.usb_uart.UART;

import static com.yongyida.robot.model.agreement.Y148Steering.SingleChip.DIRECTION_RIGHT;

/**
 * Create By HuangXiangXiang 2018/6/6
 * Y148 连接在USB串口的灯[胸、手臂]
 */
public class UsbLed {

    private static final String TAG = UsbLed.class.getSimpleName() ;

    private UART mUART ;
    private Y148Steering.SteerLed mChestLed = new Y148Steering.SteerLed() ;     // 胸前灯
    private Y148Steering.SteerFinger mFingerLed = new Y148Steering.SteerFinger() ;    // 手臂灯

    private static UsbLed mInstance ;
    public static UsbLed getInstance(Context context){

        if(mInstance == null){

            mInstance = new UsbLed(context) ;
        }
        return mInstance ;
    }

    private UsbLed(Context context){

        mUART = UART.getInstance(context) ;

        mChestLed.setPosition(Y148Steering.SteerLed.POSITION_CHEST);
    }


    /**LED 参数*/
    private static class LedParam{


        private static final byte MODE_OFF                               = 0x00 ; // 常灭
        private static final byte MODE_ON                                = 0x01 ; // 常亮
        private static final byte MODE_BREATH                            = 0x02 ; // 呼吸
//        private static final byte MODE_HORSE                             = 0x03 ; // 跑马

        private static final short PARAM_BREATH_LOW                     = 16000 ;   // 呼吸慢(16秒一个轮回)
        private static final short PARAM_BREATH_MIDDLE                  = 2500 ;    // 呼吸中(2.5秒一个轮回)
        private static final short PARAM_BREATH_FAST                    = 500 ;     // 呼吸快(0.5秒一个轮回)

        private byte mode ;     // 模式
        private short param ;
        private byte red ;
        private byte green ;
        private byte blue ;

        private final boolean isArm ;

        LedParam(boolean isArm, byte mode){

            this.isArm = isArm ;
            this.mode = mode ;
        }

        LedParam(boolean isArm, short param){

            this.isArm = isArm ;
            this.mode = MODE_BREATH ;
            this.param = param ;
        }


        private static LedParam parseLedSendControl(boolean isArm, LedSendControl ledSendControl){

            LedParam ledParam ;

            LedSendControl.Effect effect = ledSendControl.getEffect();
            if(effect == null ){

                return null ;
            }

            switch (effect) {

                case LED_OFF:

                    ledParam = new LedParam(isArm, MODE_OFF) ;
                    return ledParam ;

                case LED_ON:
                case NORMAL:

                    ledParam = new LedParam(isArm, MODE_ON) ;
                    break;

                case BREATH_LOW:

                    ledParam = new LedParam(isArm, PARAM_BREATH_LOW) ;
                    break;

                case BREATH_MIDDLE:

                    ledParam = new LedParam(isArm, PARAM_BREATH_MIDDLE) ;
                    break;

                case BREATH_FAST:

                    ledParam = new LedParam(isArm, PARAM_BREATH_FAST) ;
                    break;

                case BREATH:

                    short param = (short) ledSendControl.getEffectValue();
                    ledParam = new LedParam(isArm, param) ;
                    break;

                default:

                    return null ;
            }

            ledParam.setColor(ledSendControl);

            return ledParam ;
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

                if(isArm){

                    this.red = (byte) (red * b / 200);
                    this.green = (byte) (green * b / 200);
                    this.blue = (byte) (blue * b / 200);

                }else {

                    this.red = (byte) (red * b / 100);
                    this.green = (byte) (green * b / 100);
                    this.blue = (byte) (blue * b / 100);
                }


                LogHelper.i(TAG,LogHelper.__TAG__() + ", red : " + this.red  + ", green : " + this.green  + ", blue : " + this.blue);

            }
        }

    }



    /**控制胸口灯*/
    public SendResponse controlChestLed(LedSendControl ledControl) {

        LedParam ledParam = LedParam.parseLedSendControl(false, ledControl) ;
        if(ledParam == null){

            return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR, "参数错误" ) ;
        }

        mChestLed.setLedParam(ledParam.mode, ledParam.param, ledParam.red, ledParam.green, ledParam.blue);
        mUART.writeData(mChestLed.getCmd()) ;

        return new SendResponse() ;
    }


    /**
     * 控制手臂灯
     * 由于LED挂在手指的上面，所以走手指上的控制
     * */
    public SendResponse controlArmLed(byte direction , LedSendControl ledControl) {

        LedParam ledParam = LedParam.parseLedSendControl(true, ledControl) ;
        if(ledParam == null){

            return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR, "参数错误" ) ;
        }

        mFingerLed.controlLed(direction,ledParam.mode, ledParam.param, ledParam.red, ledParam.green, ledParam.blue);
        mUART.writeData(mFingerLed.getCmd()) ;

        return new SendResponse() ;
    }

}
