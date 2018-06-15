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

import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSend2Control;
import com.yongyida.robot.model.agreement.Y128Send;
import com.yongyida.robot.model.agreement.Y128Steering;

/**
 * Create By HuangXiangXiang 2018/6/6
 * 串口控制LED
 */
public class SerialLed {

    private Y128Steering.SteerHorseLed mSteerHorseLed = new Y128Steering.SteerHorseLed() ;


    public void controlLed(LedSend2Control ledControl2){

        // 灯带控制

        mSteerHorseLed.setPosition(ledControl2.getDirection().value);
        mSteerHorseLed.setMode(ledControl2.getEffect().value);
        mSteerHorseLed.setSpeed(ledControl2.getSpeed());

        Y128Send.getInstance().sendData(mSteerHorseLed) ;

    }


}
