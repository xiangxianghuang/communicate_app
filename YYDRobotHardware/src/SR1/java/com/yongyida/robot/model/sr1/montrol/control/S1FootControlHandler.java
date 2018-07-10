package com.yongyida.robot.model.sr1.montrol.control;

import android.content.Context;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.motion.control.FootControlHandler;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.FootControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.model.sr1.montrol.control.item.FootAndroid;



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
public class S1FootControlHandler extends FootControlHandler {

    private FootAndroid mFootAndroid;

    public S1FootControlHandler(Context context) {
        super(context);

        mFootAndroid = FootAndroid.getInstance(context) ;
    }

    @Override
    public SendResponse onHandler(FootControl footControl, IResponseListener responseListener) {

        return mFootAndroid.onHandler(footControl, responseListener);

    }

}