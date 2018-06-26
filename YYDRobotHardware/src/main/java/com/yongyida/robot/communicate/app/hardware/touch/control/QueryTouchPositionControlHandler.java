package com.yongyida.robot.communicate.app.hardware.touch.control;

import android.content.Context;
import android.content.Intent;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.BaseControlHandler;
import com.yongyida.robot.communicate.app.hardware.touch.response.data.TouchPosition;
import com.yongyida.robot.communicate.app.hardware.touch.send.data.QueryTouchPositionControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.server.ServerService;
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
 * Create By HuangXiangXiang 2018/6/21
 * 触摸点变化
 */
public abstract class QueryTouchPositionControlHandler extends BaseControlHandler<QueryTouchPositionControl> {

    private static final String TAG = QueryTouchPositionControlHandler.class.getSimpleName() ;

    private static final String ACTION_TOUCH    = "com.yongyida.robot.TOUCH" ;
    private static final String KEY_TOUCH_POINT = "touch_point" ;


    private TouchPosition mTouchPosition ;

    public QueryTouchPositionControlHandler(Context context) {

        super(context);

        mTouchPosition = new TouchPosition() ;

        startListenTouch(mOnTouchPositionListener) ;
    }


    protected abstract void startListenTouch(OnTouchPositionListener onTouchPositionListener);


    @Override
    public SendResponse onHandler(QueryTouchPositionControl queryTouchPositionControl,final IResponseListener responseListener) {

        String tag = queryTouchPositionControl.getTag() ;
        if(tag ==  null){

            return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR, "tag 不能为空" );
        }

        if (responseListener == null){

            mResponseTouchChangedListeners.remove(tag) ;
        }else{

            if(!mResponseTouchChangedListeners.containsKey(tag)){

                ResponseTouchChangedListener responseTouchChangedListener = new ResponseTouchChangedListener() {
                    @Override
                    public int responseTouchChanged(TouchPosition.Position position) {

                        mTouchPosition.setPosition(position);

                        return responseListener.onResponse(mTouchPosition.getResponse());
                    }

                };
                mResponseTouchChangedListeners.put(tag, responseTouchChangedListener) ;
            }
        }

        mTouchPosition.setPosition(null);
        return mTouchPosition.getResponse();
    }


    private void sendTouchPosition(TouchPosition.Position position){

        responseTouchPositionService(position) ;

        boolean isFactory = ServerService.isFactory() ;
        LogHelper.i(TAG, LogHelper.__TAG__() + ", isFactory : " + isFactory );
        if(!ServerService.isFactory()) {

            sendTouchPositionBroadcast(position) ;
        }
    }


    /**
     * 响应服务中的触摸
     * */
    private void responseTouchPositionService(TouchPosition.Position position) {

        HashSet<String> deaths = new HashSet<>() ;
        for (Map.Entry<String, ResponseTouchChangedListener> entry : mResponseTouchChangedListeners.entrySet()){

            ResponseTouchChangedListener batteryChangeListener = entry.getValue() ;
            int result = batteryChangeListener.responseTouchChanged(position) ;
            if(result != 0){    //非 0 表示已经断开

                deaths.add(entry.getKey()) ;
            }
        }

        //移除已经断开的
        for (String death : deaths) {

            mResponseTouchChangedListeners.remove(death) ;
        }
    }

    /**
     * 发送广播信息
     * */
    private void sendTouchPositionBroadcast(TouchPosition.Position position) {

        Intent intent = new Intent() ;
        intent.setAction(ACTION_TOUCH) ;
        intent.putExtra(KEY_TOUCH_POINT, position.name()) ;
        mContext.sendBroadcast(intent);
    }


    private final HashMap<String, ResponseTouchChangedListener> mResponseTouchChangedListeners = new HashMap<>() ;
    /**用于响应触摸反馈信息*/
    public interface ResponseTouchChangedListener{

        /**
         * @return 0 表示反馈成功
         *          1 表示反馈失败（一般是由于通讯断开）
         * */
        int responseTouchChanged(TouchPosition.Position point) ;
    }



    /***/
    private OnTouchPositionListener mOnTouchPositionListener = new OnTouchPositionListener(){

        @Override
        public void onTouchPosition(TouchPosition.Position point) {

            sendTouchPosition(point) ;
        }
    };
    /**
     * 触摸变化
     * */
    public interface OnTouchPositionListener{

        void onTouchPosition(TouchPosition.Position point) ;
    }


}
