package com.yongyida.robot.communicate.app.hardware.motion.control;

import android.content.Context;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.BaseControlHandler;
import com.yongyida.robot.communicate.app.hardware.motion.response.data.HandAngle;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.QueryHandAngleControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.utils.LogHelper;

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
 * Create By HuangXiangXiang 2018/6/27
 * 查询手臂和手指角度
 */
public abstract class QueryHandAngleControlHandler extends BaseControlHandler<QueryHandAngleControl> {

    private static final String TAG = QueryHandAngleControlHandler.class.getSimpleName() ;

    private HandAngle mHandAngle = new HandAngle() ;

    public QueryHandAngleControlHandler(Context context) {
        super(context);

        setListenHandAngle(mOnHandAngleChangedListener) ;
    }

    protected abstract void setListenHandAngle(OnHandAngleChangedListener onHandAngleChangedListener) ;
    @Override
    public SendResponse onHandler(QueryHandAngleControl queryHandAngleControl,final IResponseListener responseListener) {
        String tag = queryHandAngleControl.getTag() ;
        if(tag !=  null){

            if (responseListener == null){

                mResponseChangedListeners.remove(tag) ;
            }else{

                if(!mResponseChangedListeners.containsKey(tag)){

                    ResponseChangedListener responseChangedListener = new ResponseChangedListener() {
                        @Override
                        public int responseChanged() {
                            LogHelper.i(TAG, LogHelper.__TAG__()) ;

                            return responseListener.onResponse(mHandAngle.getResponse());
                        }
                    };
                    mResponseChangedListeners.put(tag, responseChangedListener) ;
                }
            }
        }

        return mHandAngle.getResponse();
    }

    /**
     * 响应
     * */
    private synchronized void responseChangedService() {

        HashSet<String> deaths = new HashSet<>() ;
        for (Map.Entry<String, ResponseChangedListener> entry : mResponseChangedListeners.entrySet()){

            ResponseChangedListener responseHandAngleChangedListener = entry.getValue() ;
            int result = responseHandAngleChangedListener.responseChanged() ;
            if(result != 0){    //非 0 表示已经断开

                deaths.add(entry.getKey()) ;
            }
        }

        //移除已经断开的
        for (String death : deaths) {

            mResponseChangedListeners.remove(death) ;
        }
    }


    private final HashMap<String, ResponseChangedListener> mResponseChangedListeners = new HashMap<>() ;
    /**
     * 用于系统信息反馈信息
     * */
    public interface ResponseChangedListener{

        /**
         * @return 0 表示反馈成功
         *          1 表示反馈失败（一般是由于通讯断开）
         * */
        int responseChanged() ;
    }


    /***/
    private OnHandAngleChangedListener mOnHandAngleChangedListener  = new OnHandAngleChangedListener(){

        @Override
        public void onLeftArmsChanged(int state, int[] leftArms) {

            boolean isChange = mHandAngle.leftArmAngle.setValues(state, leftArms) ;
            if(isChange){
                responseChangedService() ;
            }
//            LogHelper.i(TAG, LogHelper.__TAG__() + " ,isChange : " + isChange );
        }

        @Override
        public void onRightArmsChanged(int state, int[] rightArms) {

            boolean isChange = mHandAngle.rightArmAngle.setValues(state, rightArms) ;
            if(isChange){
                responseChangedService() ;
            }
//            LogHelper.i(TAG, LogHelper.__TAG__() + " ,isChange : " + isChange );
        }

        @Override
        public void onLeftFingersChanged(int state, int[] leftFingers) {

            boolean isChange = mHandAngle.leftFingerAngle.setValues(state, leftFingers) ;
            if(isChange){
                responseChangedService() ;
            }
            LogHelper.i(TAG, LogHelper.__TAG__() + " ,isChange : " + isChange );
        }

        @Override
        public void onRightFingersChanged(int state, int[] leftRights) {

            boolean isChange = mHandAngle.rightFingerAngle.setValues(state, leftRights) ;
            if(isChange){
                responseChangedService() ;
            }
            LogHelper.i(TAG, LogHelper.__TAG__() + " ,isChange : " + isChange );
        }

    };

    /**
     * 角度值变化
     * */
    public interface OnHandAngleChangedListener{

        void onLeftArmsChanged(int state, int[] leftArms) ;

        void onRightArmsChanged(int state, int[] rightArms) ;

        void onLeftFingersChanged(int state, int[] leftFingers) ;

        void onRightFingersChanged(int state, int[] leftRights) ;
    }


}
