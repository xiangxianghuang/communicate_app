package com.hiva.communicate.app.client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.hiva.communicate.app.ResponseListener;
import com.hiva.communicate.app.SendManager;
import com.hiva.communicate.app.common.Container;
import com.hiva.communicate.app.common.response.SendResponse;
import com.hiva.communicate.app.common.send.SendResponseListener;
import com.hiva.communicate.app.common.response.BaseResponseControl;
import com.hiva.communicate.app.common.send.BaseSend;
import com.hiva.communicate.app.common.send.data.BaseSendControl;
import com.hiva.communicate.app.utils.AppUtils;
import com.hiva.communicate.app.utils.LogHelper;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by HuangXiangXiang on 2017/11/29.
 */
public class Receiver {

    private static final String TAG = Receiver.class.getSimpleName() ;

    private Context mContext ;
    private String packageName ;
    private String action ;

    private SendManager sendManager ;

    Receiver(Context context, String packageName, String action){

        this.mContext = context ;
        this.packageName = packageName ;
        this.action = action ;
    }


    private boolean isExist(){

        HashSet<String> packageNames = AppUtils.getPackageNameByServiceAction(mContext,action) ;

        return packageNames.contains(packageName) ;
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            LogHelper.i(TAG , LogHelper.__TAG__());
            sendManager = SendManager.Stub.asInterface(service) ;


            synchronized (mBindServiceListeners){

                final int size = mBindServiceListeners.size() ;
                for (int i = 0 ; i < size ; i++){

                    mBindServiceListeners.get(i).onBindSuccess();
                }
                mBindServiceListeners.clear();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            LogHelper.e(TAG , LogHelper.__TAG__());
            sendManager = null ;
        }
    } ;

    private void bindService(){

        Intent intent = new Intent();
        intent.setPackage(packageName) ;
        intent.setAction(action) ;
        mContext.bindService(intent,connection, Context.BIND_AUTO_CREATE) ;
    }


    private final ArrayList<BindServiceListener> mBindServiceListeners = new ArrayList<>() ;
    private interface BindServiceListener{

        void onBindSuccess();

    }
    private void bindService(BindServiceListener bindServiceListener){

        mBindServiceListeners.add(bindServiceListener) ;
        bindService();
    }


    public void send(final Context context ,final BaseSend send , final SendResponseListener response) {

        // 判断通讯是否能走通
        if(sendManager == null){
            // 服务未连接
            if(isExist()){// 判断时候有该主服务

                BindServiceListener bindServiceListener = new BindServiceListener() {
                    @Override
                    public void onBindSuccess() {

                        sendToService(context, send, response);
                    }
                };
                bindService(bindServiceListener);

            }else {

                if(response != null){

                    response.onFail(SendResponse.RESULT_CLIENT_NO_SERVICE,null);
                }
            }
        }else {

            // 服务已经连接 直接发送
            sendToService(context, send, response);
        }
    }


    private void sendToService(final Context context ,final BaseSend send , final SendResponseListener response){

        ResponseListener responseListener = null  ;
        if(response != null ){

            //转换回调函数
            responseListener = new ResponseListener.Stub() {
                @Override
                public void response(String content) throws RemoteException {


                    boolean isDestroyed = isDestroyed(context) ;
                    if(isDestroyed){
                        BaseSendControl baseSendControl = send.getBaseControl() ;

                        // 如果已经销毁了发送一个销毁信息
                        LogHelper.i(TAG , LogHelper.__TAG__() + " activity 已经销毁") ;

                        send(null,baseSendControl.getSend(),null) ;

                        return ;
                    }

                    LogHelper.i(TAG , LogHelper.__TAG__() + ",response : " + response);
                    Container container = Container.fromJson(content) ;
                    LogHelper.i(TAG , LogHelper.__TAG__() + ",container : " + container);

                    SendResponse sendResponse = null ;

                    try {
                        Class clazz = Class.forName(container.getClassName()) ;
                        LogHelper.i(TAG , LogHelper.__TAG__() + ",clazz : " + clazz);

                        String data = container.getData().toString() ;
                        LogHelper.i(TAG , LogHelper.__TAG__() + ",data : " + data);

                        sendResponse = (SendResponse) container.getData(clazz);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        LogHelper.e(TAG , LogHelper.__TAG__() + ",ClassNotFoundException : " + e);
                    }catch (Exception e){

                        LogHelper.e(TAG , LogHelper.__TAG__() + ", Exception : " + e);
                    }

                    LogHelper.i(TAG , LogHelper.__TAG__() + ",sendResponse : " + sendResponse);

                    if(sendResponse == null){

                        response.onFail(SendResponse.RESULT_CLIENT_NONE_DATA, null);

                    }else {

                        int result = sendResponse.getResult() ;
                        if(result == SendResponse.RESULT_SUCCESS){

                            BaseResponseControl baseResponseControl = sendResponse.getBaseResponseControl() ;

                            response.onSuccess(baseResponseControl);

                        }else {

                            response.onFail(result, sendResponse.getMessage());
                        }
                    }
                }
            };
        }

        Container sendContainer = new Container(this.mContext, send) ;
        try {
            sendManager.send(sendContainer.toJson(), responseListener) ;
        } catch (RemoteException e) {
            e.printStackTrace();

            LogHelper.e(TAG, LogHelper.__TAG__() + "RemoteException : " + e);
        }
    }






    /**
     *
     * 判断当前是否销毁 暂时Activity可以通过判断
     *
     * */
    private boolean isDestroyed(Context context){

        if(context instanceof Activity){
            Activity activity = (Activity) context;

            boolean isDestroyed = activity.isDestroyed() ;
            boolean isFinishing = activity.isFinishing() ;

            LogHelper.i(TAG , LogHelper.__TAG__() + ",isDestroyed : " + isDestroyed + ",isFinishing : " + isFinishing);


            return isDestroyed ;
        }

        return false ;
    }



}
