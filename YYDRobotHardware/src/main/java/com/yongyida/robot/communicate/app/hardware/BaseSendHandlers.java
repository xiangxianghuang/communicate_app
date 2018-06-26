package com.yongyida.robot.communicate.app.hardware;

import android.content.Context;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.common.send.data.BaseSendControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.utils.LogHelper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by HuangXiangXiang on 2017/12/4.
 */
public abstract class BaseSendHandlers<T extends BaseSend> {

    private final static String TAG = BaseSendHandlers.class.getSimpleName() ;

    protected Context mContext ;

    private Class<T> tClass ;

    private final HashMap<Class<? extends BaseSendControl>, BaseControlHandler> controlHandlerMap = new HashMap<>() ;

    public BaseSendHandlers(Context context){

        this.mContext = context ;
    }

    public final SendResponse onHandler(T send, IResponseListener responseListener) {

        BaseSendControl baseSendControl = send.getBaseControl() ;
        if(baseSendControl == null)
            return new SendResponse(SendResponse.RESULT_SERVER_NONE_CONTROL) ;  // 数据为空

        Class key = baseSendControl.getClass() ;
        BaseControlHandler baseControlHandler = controlHandlerMap.get(key);
        if(baseControlHandler == null){

            return new SendResponse(SendResponse.RESULT_SERVER_NO_CONTROL) ;
        }

        return baseControlHandler.onHandler(baseSendControl, responseListener);

    }

    /**新增*/
    public void addBaseControlHandler(BaseControlHandler baseControlHandler){

        controlHandlerMap.put(baseControlHandler.getBaseSendControlClass(), baseControlHandler) ;
    }

    /**移除*/
    public void removeBaseControlHandler(Class<? extends BaseSendControl> clazz){

        controlHandlerMap.remove(clazz) ;
    }

    public Class<T> getSendClass(){

        if(tClass == null){

            Type genType = getClass().getSuperclass().getGenericSuperclass();
            LogHelper.i(TAG, LogHelper.__TAG__() + ", genType : " + genType );

            Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
            LogHelper.i(TAG, LogHelper.__TAG__() + ", params : " + params );

            tClass = (Class<T>) params[0];
            LogHelper.i(TAG, LogHelper.__TAG__() + ", tClass : " + tClass );

        }
        return tClass ;
    }

}
