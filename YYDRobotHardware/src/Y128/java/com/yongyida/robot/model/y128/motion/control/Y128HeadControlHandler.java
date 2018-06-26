package com.yongyida.robot.model.y128.motion.control;

import android.content.Context;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.motion.control.HeadControlHandler;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.HeadControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.model.y128.motion.item.HeadLeftRight;
import com.yongyida.robot.model.y128.motion.item.HeadUpDown;



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
 * Create By HuangXiangXiang 2018/6/22
 * 头部运动
 */
public class Y128HeadControlHandler extends HeadControlHandler {

    private HeadLeftRight mHeadLeftRight ;
    private HeadUpDown mHeadUpDown ;


    public Y128HeadControlHandler(Context context) {
        super(context);

        mHeadLeftRight = new HeadLeftRight() ;
        mHeadUpDown = new HeadUpDown() ;
    }

    @Override
    public SendResponse onHandler(HeadControl headControl, IResponseListener responseListener) {

        HeadControl.Action action = headControl.getAction() ;
        switch (action){

            case LEFT_RIGHT:
                return mHeadLeftRight.onHandler(headControl, responseListener);

            case UP_DOWN :

                return mHeadUpDown.onHandler(headControl, responseListener);
        }

        return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR, "不支持的Action 类型" );
    }


}
