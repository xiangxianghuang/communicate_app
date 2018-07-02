package com.yongyida.robot.model.y148.montrol.control;

import android.content.Context;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.common.send.data.BaseSendControl;
import com.yongyida.robot.communicate.app.hardware.motion.control.HandControlHandler;
import com.yongyida.robot.communicate.app.hardware.motion.control.QueryHandAngleControlHandler;
import com.yongyida.robot.communicate.app.hardware.motion.response.data.HandAngle;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.usb_uart.UART;



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
 * Create By HuangXiangXiang 2018/6/27
 * 查询手臂角度
 *
 */
public class Y148QueryHandAngleControlHandler extends QueryHandAngleControlHandler {

    private static final String TAG = Y148QueryHandAngleControlHandler.class.getSimpleName() ;

    private OnHandAngleChangedListener mOnHandAngleChangedListener ;

    public Y148QueryHandAngleControlHandler(Context context) {

        super(context);
    }

    @Override
    protected void setListenHandAngle(OnHandAngleChangedListener onHandAngleChangedListener) {

        this.mOnHandAngleChangedListener = onHandAngleChangedListener ;



        final UART.HandAngleChangedListener mHandAngleChangedListener = new UART.HandAngleChangedListener(){

            @Override
            public void onLeftArmsChanged(int state, int[] leftArms) {

//                LogHelper.i(TAG, LogHelper.__TAG__());

                if(mOnHandAngleChangedListener != null ){

                    mOnHandAngleChangedListener.onLeftArmsChanged(state, leftArms);
                }
            }

            @Override
            public void onRightArmsChanged(int state, int[] rightArms) {

//                LogHelper.i(TAG, LogHelper.__TAG__());

                if(mOnHandAngleChangedListener != null ){

                    mOnHandAngleChangedListener.onRightArmsChanged(state, rightArms);
                }

            }

            @Override
            public void onLeftFingersChanged(int state, int[] leftFingers) {

                LogHelper.i(TAG, LogHelper.__TAG__());

                if(mOnHandAngleChangedListener != null ){

                    mOnHandAngleChangedListener.onLeftFingersChanged(state, leftFingers);
                }
            }

            @Override
            public void onRightFingersChanged(int state, int[] leftRights) {

                LogHelper.i(TAG, LogHelper.__TAG__());

                if(mOnHandAngleChangedListener != null ){

                    mOnHandAngleChangedListener.onRightFingersChanged(state, leftRights);
                }
            }
        };
        LogHelper.i(TAG, LogHelper.__TAG__() + ", mHandAngleChangedListener : " + mHandAngleChangedListener) ;
        UART.getInstance(mContext).setHandAngleChangedListener(mHandAngleChangedListener) ;
    }


//    private final UART.HandAngleChangedListener mHandAngleChangedListener = new UART.HandAngleChangedListener(){
//
//        @Override
//        public void onLeftArmsChanged(int state, int[] leftArms) {
//
//            LogHelper.i(TAG, LogHelper.__TAG__());
//
//            if(mOnHandAngleChangedListener != null ){
//
//                mOnHandAngleChangedListener.onLeftArmsChanged(state, leftArms);
//            }
//        }
//
//        @Override
//        public void onRightArmsChanged(int state, int[] rightArms) {
//
//            LogHelper.i(TAG, LogHelper.__TAG__());
//
//            if(mOnHandAngleChangedListener != null ){
//
//                mOnHandAngleChangedListener.onRightArmsChanged(state, rightArms);
//            }
//
//        }
//
//        @Override
//        public void onLeftFingersChanged(int state, int[] leftFingers) {
//
//            LogHelper.i(TAG, LogHelper.__TAG__());
//
//            if(mOnHandAngleChangedListener != null ){
//
//                mOnHandAngleChangedListener.onLeftFingersChanged(state, leftFingers);
//            }
//        }
//
//        @Override
//        public void onRightFingersChanged(int state, int[] leftRights) {
//
//            LogHelper.i(TAG, LogHelper.__TAG__());
//
//            if(mOnHandAngleChangedListener != null ){
//
//                mOnHandAngleChangedListener.onRightFingersChanged(state, leftRights);
//            }
//        }
//    };

}
