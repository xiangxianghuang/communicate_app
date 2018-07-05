package com.yongyida.robot.model.y128.led.control;

import android.content.Context;
import android.graphics.Color;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.led.control.LedSendControlHandler;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.model.agreement.Y128Send;
import com.yongyida.robot.model.agreement.Y128Steering;
import com.yongyida.robot.model.y128.led.type.Led;
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

    private Led mLed;

    public Y128LedSendControlHandler(Context context) {

        super(context);
        mLed = Led.getInstance() ;
    }

    @Override
    public SendResponse onHandler(LedSendControl ledSendControl, IResponseListener responseListener) {

        LogHelper.i(TAG, LogHelper.__TAG__() + ", ledSendControl : " + ledSendControl.toJson()) ;

        return mLed.onHandler(ledSendControl, responseListener) ;
    }

}
