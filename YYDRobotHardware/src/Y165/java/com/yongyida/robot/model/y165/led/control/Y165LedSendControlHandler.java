package com.yongyida.robot.model.y165.led.control;

import android.content.Context;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.led.control.LedSendControlHandler;
import com.yongyida.robot.communicate.app.hardware.led.send.data.LedSendControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.model.agreement.Y128Send;
import com.yongyida.robot.model.agreement.Y165Send;
import com.yongyida.robot.model.agreement.Y165Steering;



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
 * Create By HuangXiangXiang 2018/7/2
 */
public class Y165LedSendControlHandler extends LedSendControlHandler {

    private Y165Send mSend ;
    private Y165Steering.EyeLed mEyeLed ;

    public Y165LedSendControlHandler(Context context) {
        super(context);

        mSend = Y165Send.getInstance() ;
        mEyeLed = new Y165Steering.EyeLed() ;
    }

    @Override
    public SendResponse onHandler(LedSendControl ledSendControl, IResponseListener responseListener) {

        LedSendControl.Position position = ledSendControl.getPosition() ;
        if(!LedSendControl.Position.EYE.equals(position)){

            return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR ,"位置不支持" ) ;
        }

        LedSendControl.Effect effect = ledSendControl.getEffect() ;
        if(effect == null){

            return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR ,"效果不能为空") ;
        }

        switch (effect){

            case LED_ON:

                mEyeLed.setOnOff(true );
                mSend.sendData(mEyeLed.getCmd()) ;

                return new SendResponse();
            case LED_OFF:

                mEyeLed.setOnOff(false);
                mSend.sendData(mEyeLed.getCmd()) ;

                return new SendResponse();

            default:
                return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR ,"效果不支持" ) ;

        }

    }
}
