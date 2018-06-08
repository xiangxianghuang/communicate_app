package com.hiva.communicate.app.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.hiva.communicate.app.ResponseListener;
import com.hiva.communicate.app.SendManager;
import com.hiva.communicate.app.common.Container;
import com.hiva.communicate.app.common.SendResponse;
import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.send.BaseSend;
import com.hiva.communicate.app.setting.send.SettingSend;
import com.hiva.communicate.app.setting.send.data.BaseSettingSendControl;
import com.hiva.communicate.app.setting.send.data.FactoryConfig;
import com.hiva.communicate.app.utils.LogHelper;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 */
public abstract class  ServerService extends Service {

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
            String packageName = container.getPackageName() ;
            LogHelper.i(TAG, LogHelper.__TAG__() + ", packageName : " + packageName);

            SendResponse sendResponse ;
            try {
                Class clazz = Class.forName(className) ;
                final BaseSend baseSend = (BaseSend) container.getData(clazz);

                if(baseSend instanceof SettingSend){

                    SettingSend settingSend = (SettingSend) baseSend;
                    sendResponse = controlFirstSend(packageName, settingSend) ;

                }else {

                    sendResponse = controlSecondSend(packageName, baseSend, responseListener) ;
                }

            } catch (ClassNotFoundException e) {

                sendResponse = new SendResponse(SendResponse.RESULT_NO_SEND) ;
            }

            // 返回发送结果
            return new Container(ServerService.this, sendResponse).toString();
        }
    } ;

    //判断是否进入工程模式
    private boolean isFactory = false ;
    /**
     * 处理第一类
     *
     * */
    private SendResponse controlFirstSend(String packageName, SettingSend settingSend){

        BaseSettingSendControl baseSendControl = settingSend.getBaseControl() ;
        if(baseSendControl instanceof FactoryConfig){

            if(isAllowSettingPackageName(packageName)){

                FactoryConfig factoryConfig = (FactoryConfig) baseSendControl;
                this.isFactory = factoryConfig.isFactory() ;

                return new SendResponse(SendResponse.RESULT_SUCCESS) ;

            }else {
                return new SendResponse(SendResponse.RESULT_NO_SETTING_PERMISSION) ;
            }

        }

        return new SendResponse(SendResponse.RESULT_NO_METHOD) ;
    }



    /**
     * 处理第二类
     * */
    private SendResponse controlSecondSend(final String packageName, final BaseSend baseSend, final ResponseListener responseListener){

        if (!isCanCommunicate(packageName)){

            return new SendResponse(SendResponse.RESULT_FACTORY_MODE) ;
        }


        // 新开一个
        new Thread(){

            @Override
            public void run() {

                IResponseListener iResponseListener = null ;
                if(responseListener != null){

                    iResponseListener = new IResponseListener(){

                        @Override
                        public void onResponse(BaseResponse response) {


                            if(isCanCommunicate(packageName)){

                                Container responseContainer = new Container(ServerService.this,response) ;
                                String responseString = responseContainer.toString() ;
                                try {
                                    responseListener.response(responseString);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                }

                try{

                    onReceiver(baseSend,iResponseListener) ;

                }catch (Exception e){

                    LogHelper.e(TAG, LogHelper.__TAG__() + " , Exception : " + e.getMessage());

                    if(iResponseListener != null){

                        BaseResponse baseResponse = new BaseResponse(BaseResponse.RESULT_EXCEPTION , e.getMessage()) ;

                        Container responseContainer = new Container(ServerService.this,baseResponse) ;
                        String responseString = responseContainer.toString() ;
                        try {
                            responseListener.response(responseString);
                        } catch (RemoteException e1) {
                            e.printStackTrace();
                            LogHelper.e(TAG, LogHelper.__TAG__() + " , Exception : " + e.getMessage());
                        }
                    }

                }
            }
        }.start();

        return new SendResponse(SendResponse.RESULT_SUCCESS) ;
    }



    /**
     * 是否是允许设置修改工厂模式的包名（可以设置一些允许的白名单）
     * 现在这个版本只允许自己
     * */
    private boolean isAllowSettingPackageName(String packageName){

        return getPackageName().equals(packageName);
    }

    /**
     * 工厂模式是否允许通讯（可以设置一些允许的白名单）
     * 现在这个版本只允许自己
     * */
    private boolean isAllowCommunicatePackageName(String packageName){

        return getPackageName().equals(packageName);
    }


    /**
     * 工厂模式只用自己能通讯
     * */
    private boolean isCanCommunicate(String packageName){

        return !isFactory || isAllowCommunicatePackageName(packageName) ;
    }


    protected abstract void onReceiver(BaseSend send,IResponseListener responseListener);


}
