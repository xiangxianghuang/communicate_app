package com.yongyida.robot.hardware.client;


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

import com.hiva.communicate.app.client.Receiver;
import com.hiva.communicate.app.common.IResponseListener;
import com.hiva.communicate.app.common.SendResponse;
import com.hiva.communicate.app.common.send.BaseControl;
import com.hiva.communicate.app.common.send.BaseSend;

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


    public SendResponse send(final BaseControl control, final IResponseListener response) {

        BaseSend send = control.getSend();

        return mReceiver.send(send , response) ;
    }


    public void sendInMainThread(final BaseControl control, final IResponseListener response) {

        new Thread(){

            @Override
            public void run() {

                send(control , response) ;
            }
        }.start();
    }

}
