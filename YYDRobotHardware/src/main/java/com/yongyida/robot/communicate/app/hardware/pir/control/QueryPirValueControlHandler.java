package com.yongyida.robot.communicate.app.hardware.pir.control;

import android.content.Context;
import android.content.Intent;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.BaseControlHandler;
import com.yongyida.robot.communicate.app.hardware.pir.response.data.PirValue;
import com.yongyida.robot.communicate.app.hardware.pir.send.data.QueryPirValueControl;
import com.yongyida.robot.communicate.app.hardware.touch.control.QueryTouchPositionControlHandler;
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
 * Create By HuangXiangXiang 2018/7/2
 */
public abstract class QueryPirValueControlHandler extends BaseControlHandler<QueryPirValueControl> {

    private static final String TAG = QueryTouchPositionControlHandler.class.getSimpleName() ;

    public static final String ACTION_PIR_VALUE             = "com.yongyida.robot.PIR_VALUE" ;
    public static final String KEY_HAS_PEOPLE               = "hasPeople" ;
    public static final String KEY_PEOPLE_DISTANCE          = "peopleDistance" ;


    private PirValue mPirValue ;

    public QueryPirValueControlHandler(Context context) {
        super(context);

        mPirValue = new PirValue() ;

        startListenPir(mOnPirValueChangedListener) ;
    }


    protected abstract void startListenPir(OnPirValueChangedListener onTouchPositionListener);

    @Override
    public SendResponse onHandler(QueryPirValueControl queryPirValueControl, final IResponseListener responseListener) {

        String tag = queryPirValueControl.getTag() ;
        if(tag ==  null){

            return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR, "tag 不能为空" );
        }

        if (responseListener == null){

            mResponsePirValueListeners.remove(tag) ;
        }else{

            if(!mResponsePirValueListeners.containsKey(tag)){

                ResponsePirValueListener responseTouchChangedListener = new ResponsePirValueListener() {
                    @Override
                    public int responsePirValue(PirValue pirValue) {

                        return responseListener.onResponse(pirValue.getResponse());
                    }

                };
                mResponsePirValueListeners.put(tag, responseTouchChangedListener) ;
            }
        }

        return new SendResponse();
    }


    private void sendPirValue(boolean hasPeople, int peopleDistance){

        responsePirValueService(hasPeople, peopleDistance) ;

        boolean isFactory = ServerService.isFactory() ;
        LogHelper.i(TAG, LogHelper.__TAG__() + ", isFactory : " + isFactory );
        if(!ServerService.isFactory()) {

            sendTouchPositionBroadcast(hasPeople, peopleDistance); ;
        }
    }

    /**
     * 响应服务中的PIR
     * */
    private synchronized void responsePirValueService(boolean hasPeople, int peopleDistance) {

        boolean isChange = false ;
        if(mPirValue.isHasPeople() != hasPeople){

            mPirValue.setHasPeople(hasPeople);
            isChange = true ;
        }
        if(mPirValue.getPeopleDistance() != peopleDistance){

            mPirValue.setPeopleDistance(peopleDistance);
            isChange = true ;
        }

        if(!isChange){  //数据没有变化

            return;
        }

        HashSet<String> deaths = new HashSet<>() ;
        for (Map.Entry<String, ResponsePirValueListener> entry : mResponsePirValueListeners.entrySet()){

            ResponsePirValueListener responsePirValueListener = entry.getValue() ;
            int result = responsePirValueListener.responsePirValue(mPirValue) ;
            if(result != 0){    //非 0 表示已经断开

                deaths.add(entry.getKey()) ;
            }
        }

        //移除已经断开的
        for (String death : deaths) {

            mResponsePirValueListeners.remove(death) ;
        }
    }

    /**
     * 发送广播信息
     * */
    private void sendTouchPositionBroadcast(boolean hasPeople, int peopleDistance) {

        Intent intent = new Intent() ;
        intent.setAction(ACTION_PIR_VALUE) ;
        intent.putExtra(KEY_HAS_PEOPLE, hasPeople) ;
        intent.putExtra(KEY_PEOPLE_DISTANCE, peopleDistance) ;
        mContext.sendBroadcast(intent);
    }


    private final HashMap<String, ResponsePirValueListener> mResponsePirValueListeners = new HashMap<>() ;
    /**用于响应客户端Pir反馈信息*/
    public interface ResponsePirValueListener{

        /**
         * @return 0 表示反馈成功
         *          1 表示反馈失败（一般是由于通讯断开）
         * */
        int responsePirValue(PirValue pirValue) ;
    }


    /***/
    private OnPirValueChangedListener mOnPirValueChangedListener = new OnPirValueChangedListener(){

        @Override
        public void onPirValueChanged(boolean hasPeople, int peopleDistance) {

            sendPirValue(hasPeople, peopleDistance);
        }
    };
    /**
     * 触摸变化
     * */
    public interface OnPirValueChangedListener{

        void onPirValueChanged(boolean hasPeople, int peopleDistance) ;
    }

}
