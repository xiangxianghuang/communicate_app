package com.yongyida.robot.communicate.app.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.robot.communicate.app.common.Container;
import com.yongyida.robot.communicate.app.ResponseListener;
import com.yongyida.robot.communicate.app.SendManager;
import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.setting.send.SettingSend;
import com.yongyida.robot.communicate.app.setting.send.data.BaseSettingSendControl;
import com.yongyida.robot.communicate.app.setting.send.data.FactoryConfig;
import com.yongyida.robot.communicate.app.utils.LogHelper;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 */
public abstract class  ServerService extends Service {

    private static final String TAG = ServerService.class.getSimpleName();

    @Nullable
    @Override
    public final IBinder onBind(Intent intent) {

        LogHelper.i(TAG, LogHelper.__TAG__());
        return mSendManager;
    }

    private SendManager.Stub mSendManager = new SendManager.Stub() {

        @Override
        public void send(final String content, final ResponseListener responseListener) throws RemoteException {

            LogHelper.i(TAG, LogHelper.__TAG__());

            Container container = Container.fromJson(content);
            LogHelper.i(TAG, LogHelper.__TAG__() + ", container : " + container);
            String className = container.getClassName();
            LogHelper.i(TAG, LogHelper.__TAG__() + ", className : " + className);
            final String packageName = container.getPackageName();
            LogHelper.i(TAG, LogHelper.__TAG__() + ", packageName : " + packageName);

            SendResponse sendResponse;

            try {
                Class clazz = Class.forName(className);
                final BaseSend baseSend = (BaseSend) container.getData(clazz);

                if (baseSend instanceof SettingSend) {

                    SettingSend settingSend = (SettingSend) baseSend;
                    sendResponse = controlSettingSend(packageName, settingSend);

                } else {

                    if (!isCanCommunicate(packageName)) {

                        sendResponse = new SendResponse(SendResponse.RESULT_SERVER_TEST_MODE);

                    } else {

                        new Thread() {

                            @Override
                            public void run() {

                                controlOtherSend(packageName, baseSend, responseListener);
                            }
                        }.start();

                        return;
                    }
                }

            } catch (ClassNotFoundException e) {

                sendResponse = new SendResponse(SendResponse.RESULT_SERVER_NO_SEND);
            }

            LogHelper.i(TAG, LogHelper.__TAG__() + " sendResponse : " + sendResponse);
            if (responseListener != null) {

                Container sendContainer = new Container(ServerService.this, sendResponse);
                responseListener.response(sendContainer.toJson());
            }

        }
    };

    //判断是否进入工程模式
    private static boolean isFactory = false;

    public static boolean isFactory(){

        return isFactory ;
    }

    /**
     * 处理设置一些信息类
     */
    private SendResponse controlSettingSend(String packageName, SettingSend settingSend) {

        BaseSettingSendControl baseSendControl = settingSend.getBaseControl();
        if (baseSendControl instanceof FactoryConfig) {

            if (isAllowSettingPackageName(packageName)) {

                FactoryConfig factoryConfig = (FactoryConfig) baseSendControl;
                this.isFactory = factoryConfig.isFactory();

                return new SendResponse(SendResponse.RESULT_SUCCESS);

            } else {
                return new SendResponse(SendResponse.RESULT_SERVER_NO_SETTING_PERMISSION);
            }

        }

        return new SendResponse(SendResponse.RESULT_SERVER_NO_CONTROL);
    }


    /**
     * 由于下面操作时间不确定，已经会造成一些异常错误
     * 为了不造成主线程阻塞，和崩溃 新建一个线程 不会对主线程造成影响
     */
    private void controlOtherSend(final String packageName, final BaseSend baseSend, final ResponseListener responseListener) {

        IResponseListener iResponseListener = null;
        if (responseListener != null) {

            final IBinder iBinder = responseListener.asBinder();
            iResponseListener = new IResponseListener() {

                @Override
                public int onResponse(SendResponse response) {

                    if (isCanCommunicate(packageName)) {

                        Container responseContainer = new Container(ServerService.this, response);
                        String responseString = responseContainer.toString();

                        if (!iBinder.isBinderAlive()) {

                            return 1;

                        } else {

                            try {
                                responseListener.response(responseString);

                                return 0;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return -1;
                }
            };
        }

        try {

            onReceiver(baseSend, iResponseListener);

        } catch (Exception e) {

            LogHelper.e(TAG, LogHelper.__TAG__() + " , Exception : " + e.getMessage());

            if (iResponseListener != null) {

                SendResponse sendResponse = new SendResponse(SendResponse.RESULT_SERVER_EXCEPTION, e.getMessage());

                Container responseContainer = new Container(ServerService.this, sendResponse);
                String responseString = responseContainer.toString();
                try {
                    responseListener.response(responseString);
                } catch (RemoteException e1) {
                    e.printStackTrace();
                    LogHelper.e(TAG, LogHelper.__TAG__() + " , Exception : " + e.getMessage());
                }
            }
        }
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
