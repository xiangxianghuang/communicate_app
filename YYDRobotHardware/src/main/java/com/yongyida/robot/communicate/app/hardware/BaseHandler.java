package com.yongyida.robot.communicate.app.hardware;

import android.content.Context;

import com.hiva.communicate.app.common.response.SendResponse;
import com.hiva.communicate.app.common.send.BaseSend;
import com.hiva.communicate.app.server.IResponseListener;
import com.hiva.communicate.app.utils.LogHelper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by HuangXiangXiang on 2017/12/4.
 */
public abstract class BaseHandler<T extends BaseSend> {

    private final static String TAG = BaseHandler.class.getSimpleName() ;

    protected Context mContext ;

    private Class<T> tClass ;

    public BaseHandler(Context context){

        this.mContext = context ;
    }

    public abstract SendResponse onHandler(T send, IResponseListener responseListener) ;


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
