package com.hiva.communicate.app.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.hiva.communicate.app.common.Container;
import com.hiva.communicate.app.common.IResponseListener;
import com.yongyida.robot.communicate.app.ResponseListener;
import com.yongyida.robot.communicate.app.SendManager;
import com.hiva.communicate.app.common.SendResponse;
import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.send.BaseSend;
import com.hiva.communicate.app.utils.LogHelper;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 */
public abstract class ServerService extends Service {

    private static final String TAG = ServerService.class.getSimpleName() ;

    @Nullable
    @Override
    public final IBinder onBind(Intent intent) {

        LogHelper.i(TAG, LogHelper.__TAG__());
        return mSendManager;
    }

    private SendManager.Stub mSendManager = new SendManager.Stub(){

        @Override
        public String setResponseListener(String content, ResponseListener responseListener) throws RemoteException {
            LogHelper.i(TAG, LogHelper.__TAG__());

            return null;
        }

        @Override
        public String send(final String content, final ResponseListener responseListener) throws RemoteException {

            LogHelper.i(TAG, LogHelper.__TAG__());

            Container container = Container.fromJson(content) ;
            LogHelper.i(TAG, LogHelper.__TAG__() + ", container : " + container);
            String className = container.getClassName() ;
            LogHelper.i(TAG, LogHelper.__TAG__() + ", className : " + className);

            try {
                Class clazz = Class.forName(className) ;
                final BaseSend baseSend = (BaseSend) container.getData(clazz);

                new Thread(){

                    @Override
                    public void run() {

                        IResponseListener iResponseListener = null ;
                        if(responseListener != null){

                            iResponseListener = new IResponseListener(){

                                @Override
                                public void onResponse(BaseResponse response) {

                                    Container responseContainer = new Container(ServerService.this,response) ;
                                    String responseString = responseContainer.toString() ;
                                    try {
                                        responseListener.response(responseString);
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                        }
                        onReceiver(baseSend,iResponseListener) ;
                    }
                }.start();

                SendResponse sendResponse = new SendResponse(SendResponse.RESULT_SUCCESS) ;
                return new Container(ServerService.this, sendResponse).toString();

            } catch (ClassNotFoundException e) {

            }

            SendResponse sendResponse = new SendResponse(SendResponse.RESULT_NO_METHOD) ;
            return new Container(ServerService.this, sendResponse).toString();
        }
    } ;

    protected abstract void onReceiver(BaseSend send,IResponseListener responseListener);

}