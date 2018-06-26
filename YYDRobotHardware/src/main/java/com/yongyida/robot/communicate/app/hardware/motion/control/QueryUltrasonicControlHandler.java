package com.yongyida.robot.communicate.app.hardware.motion.control;

import android.content.Context;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.BaseControlHandler;
import com.yongyida.robot.communicate.app.hardware.motion.response.data.Ultrasonic;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.QueryUltrasonicControl;
import com.yongyida.robot.communicate.app.hardware.touch.control.QueryTouchPositionControlHandler;
import com.yongyida.robot.communicate.app.hardware.touch.response.data.TouchPosition;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.server.ServerService;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.model.agreement.Y128Steering;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;



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
 * 查询超声波信息
 *
 */
public abstract class QueryUltrasonicControlHandler extends BaseControlHandler<QueryUltrasonicControl> {

    private static final String TAG = QueryUltrasonicControlHandler.class.getSimpleName() ;

    protected final Ultrasonic mUltrasonic = new Ultrasonic() ;

    public QueryUltrasonicControlHandler(Context context) {
        super(context);

        setListenUltrasonic(mOnUltrasonicChangedListener) ;
    }

    protected abstract void setListenUltrasonic(OnUltrasonicChangedListener onUltrasonicChangedListener) ;


    @Override
    public SendResponse onHandler(QueryUltrasonicControl queryUltrasonicControl, final IResponseListener responseListener) {

        String tag = queryUltrasonicControl.getTag() ;
        if(tag ==  null){

            return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR, "tag 不能为空" );
        }

        if (responseListener == null){

            mResponseUltrasonicChangedListeners.remove(tag) ;
        }else{

            if(!mResponseUltrasonicChangedListeners.containsKey(tag)){

                ResponseUltrasonicChangedListener responseUltrasonicChangedListener = new ResponseUltrasonicChangedListener() {
                    @Override
                    public int responseUltrasonicChanged() {

                        LogHelper.i(TAG, LogHelper.__TAG__()) ;

                        return responseListener.onResponse(mUltrasonic.getResponse());
                    }
                };

                mResponseUltrasonicChangedListeners.put(tag, responseUltrasonicChangedListener) ;
            }
        }

        return mUltrasonic.getResponse();
    }


    private void sendUltrasonicChanged(int[] distance){

        boolean isChange = mUltrasonic.setDistances(distance) ;
        if(isChange){

            responseUltrasonicChangedService() ;
        }
    }

    /**
     * 响应
     * */
    private void responseUltrasonicChangedService() {

        HashSet<String> deaths = new HashSet<>() ;
        for (Map.Entry<String, ResponseUltrasonicChangedListener> entry : mResponseUltrasonicChangedListeners.entrySet()){

            ResponseUltrasonicChangedListener ultrasonicChangedListener = entry.getValue() ;
            int result = ultrasonicChangedListener.responseUltrasonicChanged() ;
            if(result != 0){    //非 0 表示已经断开

                deaths.add(entry.getKey()) ;
            }
        }

        //移除已经断开的
        for (String death : deaths) {

            mResponseUltrasonicChangedListeners.remove(death) ;
        }
    }


    private final HashMap<String, ResponseUltrasonicChangedListener> mResponseUltrasonicChangedListeners = new HashMap<>() ;
    /**
     * 用于响应超声波变化反馈信息
     * */
    public interface ResponseUltrasonicChangedListener{

        /**
         * @return 0 表示反馈成功
         *          1 表示反馈失败（一般是由于通讯断开）
         * */
        int responseUltrasonicChanged() ;
    }


    /***/
    private OnUltrasonicChangedListener mOnUltrasonicChangedListener = new OnUltrasonicChangedListener(){

        @Override
        public void onUltrasonicChanged(int[] distance) {

            sendUltrasonicChanged(distance) ;
        }
    };

    /**
     * 超声波变化
     * */
    public interface OnUltrasonicChangedListener{

        void onUltrasonicChanged(int[] distance)  ;
    }



}
