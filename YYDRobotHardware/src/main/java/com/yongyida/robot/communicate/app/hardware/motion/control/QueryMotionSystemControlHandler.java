package com.yongyida.robot.communicate.app.hardware.motion.control;

import android.content.Context;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.BaseControlHandler;
import com.yongyida.robot.communicate.app.hardware.motion.response.data.MotionSystem;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.QueryMotionSystemControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.model.y20.motion.MotionStatueControl;

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
 * 系统信息变化
 */
public abstract class QueryMotionSystemControlHandler extends BaseControlHandler<QueryMotionSystemControl> {

    private static final String TAG = QueryMotionSystemControlHandler.class.getSimpleName() ;

    private MotionSystem mMotionSystem = new MotionSystem()  ;

    public QueryMotionSystemControlHandler(Context context) {

        super(context);

        setListenMotionSystem(mOnMotionSystemChangedListener) ;
    }

    protected abstract void setListenMotionSystem(OnMotionSystemChangedListener onMotionSystemChangedListener) ;

    @Override
    public SendResponse onHandler(QueryMotionSystemControl queryMotionSystemControl, final IResponseListener responseListener) {

        String tag = queryMotionSystemControl.getTag() ;
        if(tag !=  null){

            if (responseListener == null){

                mResponseMotionSystemChangedListeners.remove(tag) ;
            }else{

                if(!mResponseMotionSystemChangedListeners.containsKey(tag)){

                    ResponseMotionSystemChangedListener responseUltrasonicChangedListener = new ResponseMotionSystemChangedListener() {
                        @Override
                        public int responseMotionSystemChanged() {
                            LogHelper.i(TAG, LogHelper.__TAG__()) ;

                            return responseListener.onResponse(mMotionSystem.getResponse());
                        }
                    };
                    mResponseMotionSystemChangedListeners.put(tag, responseUltrasonicChangedListener) ;
                }
            }
        }

        return mMotionSystem.getResponse();
    }

    private void sendChanged(String title, String info){

        boolean isChange = mMotionSystem.addItem(title, info) ;
        if(isChange){

            responseChangedService() ;
        }
    }

    /**
     * 响应
     * */
    private void responseChangedService() {

        HashSet<String> deaths = new HashSet<>() ;
        for (Map.Entry<String, ResponseMotionSystemChangedListener> entry : mResponseMotionSystemChangedListeners.entrySet()){

            ResponseMotionSystemChangedListener ultrasonicChangedListener = entry.getValue() ;
            int result = ultrasonicChangedListener.responseMotionSystemChanged() ;
            if(result != 0){    //非 0 表示已经断开

                deaths.add(entry.getKey()) ;
            }
        }

        //移除已经断开的
        for (String death : deaths) {

            mResponseMotionSystemChangedListeners.remove(death) ;
        }
    }


    private final HashMap<String, ResponseMotionSystemChangedListener> mResponseMotionSystemChangedListeners = new HashMap<>() ;
    /**
     * 用于系统信息反馈信息
     * */
    public interface ResponseMotionSystemChangedListener{

        /**
         * @return 0 表示反馈成功
         *          1 表示反馈失败（一般是由于通讯断开）
         * */
        int responseMotionSystemChanged() ;
    }


    /***/
    private OnMotionSystemChangedListener mOnMotionSystemChangedListener = new OnMotionSystemChangedListener(){

        @Override
        public void onMotionSystemChanged(String title, String value) {

            sendChanged(title, value);

        }
    };

    /**
     * 系统信息变化
     * */
    public interface OnMotionSystemChangedListener{

        void onMotionSystemChanged(String title,String value )  ;
    }

}
