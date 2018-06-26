package com.yongyida.robot.communicate.app.common.send;


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

import com.yongyida.robot.communicate.app.client.Receiver;
import com.yongyida.robot.communicate.app.common.send.data.BaseSendControl;

/**
 * Create By HuangXiangXiang 2018/6/5
 */
public class SendClient {

    private static SendClient mSendClient ;
    public static SendClient getInstance(Context context){

        if(mSendClient == null){

            mSendClient = new SendClient(context.getApplicationContext()) ;
        }
        return mSendClient ;
    }

    private HardwareClient mHardwareClient ;
    private Receiver mReceiver ;


    private SendClient(Context context){

        mHardwareClient = HardwareClient.getInstance(context) ;
        mReceiver = mHardwareClient.getHardwareReceiver() ;
    }


    /**
     *
     * 发送控制命令（可以在主线程中使用）
     * @param context   可以为空，Android 上下文（）
     * @param control   不能为空，发送的类型
     * @param response  可为空，回调函数
     *
     *      一般只是发送一个命令,如控制运动前进, context 为空;
     *
     *
     * */
    public void send(final Context context, final BaseSendControl control, final SendResponseListener response) {

        control.setTag(context);
        BaseSend send = control.getSend();

        mReceiver.send(context, send , response) ;

    }


}
