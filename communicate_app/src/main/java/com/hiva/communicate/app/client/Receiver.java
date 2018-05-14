package com.hiva.communicate.app.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;

import com.hiva.communicate.app.ResponseListener;
import com.hiva.communicate.app.SendManager;
import com.hiva.communicate.app.common.Container;
import com.hiva.communicate.app.common.IResponseListener;
import com.hiva.communicate.app.common.SendResponse;
import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.send.BaseSend;
import com.hiva.communicate.app.utils.AppUtils;
import com.hiva.communicate.app.utils.LogHelper;

import java.util.HashSet;
import java.util.concurrent.CountDownLatch;

/**
 * Created by HuangXiangXiang on 2017/11/29.
 */
public class Receiver {

    private static final String TAG = Receiver.class.getSimpleName() ;

    private Context context ;
    private String packageName ;
    private String action ;

    private SendManager sendManager ;

    private CountDownLatch countDownLatch ;

    Receiver(Context context, String packageName, String action){

        this.context = context ;
        this.packageName = packageName ;
        this.action = action ;
    }


    private boolean isExist(){

        HashSet<String> packageNames = AppUtils.getPackageNameByServiceAction(context,action) ;

        return packageNames.contains(packageName) ;
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            LogHelper.e(TAG , LogHelper.__TAG__());
            sendManager = SendManager.Stub.asInterface(service) ;

            LogHelper.e(TAG , LogHelper.__TAG__());
            if(countDownLatch != null){

                LogHelper.e(TAG , LogHelper.__TAG__());
                countDownLatch.countDown();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    } ;

    private void bindService(){

        Intent intent = new Intent();
        intent.setPackage(packageName) ;
        intent.setAction(action) ;
        context.bindService(intent,connection, Context.BIND_AUTO_CREATE) ;
    }

    /**
     *
     * */
    public SendManager getSendManager(){

        LogHelper.e(TAG , LogHelper.__TAG__());
        if(sendManager == null || !sendManager.asBinder().isBinderAlive()){ // 新增判断绑定是否还活着
            LogHelper.e(TAG , LogHelper.__TAG__());
            if(isExist()){

                //必须不能再主线程
                if(isMainThread()){

                    throw new RuntimeException("不能在主线程执行该方法") ;
                }

                LogHelper.e(TAG , LogHelper.__TAG__());
                bindService();

                LogHelper.e(TAG , LogHelper.__TAG__());
                countDownLatch = new CountDownLatch(1) ;
                try {
                    LogHelper.e(TAG , LogHelper.__TAG__());
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LogHelper.e(TAG , LogHelper.__TAG__());

            }
        }
        LogHelper.e(TAG , LogHelper.__TAG__());
        return sendManager ;
    }


    public static boolean isMainThread() {

        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public SendResponse send(BaseSend send , final IResponseListener response) {

        ResponseListener responseListener = null;
        if(response != null){

            responseListener = new ResponseListener.Stub() {
                @Override
                public void response(String content) throws RemoteException {

                    LogHelper.i(TAG , LogHelper.__TAG__() + ",response : " + response);
                    Container container = Container.fromJson(content) ;
                    LogHelper.i(TAG , LogHelper.__TAG__() + ",container : " + container);

                    BaseResponse baseResponse = null ;

                    try {
                        Class clazz = Class.forName(container.getClassName()) ;
                        LogHelper.i(TAG , LogHelper.__TAG__() + ",clazz : " + clazz);

                        baseResponse = (BaseResponse) container.getData(clazz);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        LogHelper.i(TAG , LogHelper.__TAG__() + ",ClassNotFoundException : " + e);
                    }

                    LogHelper.i(TAG , LogHelper.__TAG__() + ",baseResponse : " + baseResponse);
                    response.onResponse(baseResponse);

                }
            };
        }

        SendManager sendManager = getSendManager() ;
        if(sendManager == null){

            return new SendResponse(SendResponse.RESULT_NO_SERVICE) ;
        }

        Container sendContainer = new Container(context, send) ;

        String sendResponseString ;
        try {

            sendResponseString = sendManager.send(sendContainer.toString(), responseListener) ;

        } catch (Exception e) {

            return new SendResponse(SendResponse.RESULT_SEND_EXCEPTION,e.getMessage()) ;
        }

        try{
            Container sendResponseContainer = Container.fromJson(sendResponseString) ;
            return sendResponseContainer.getData(SendResponse.class) ;

        }catch (Exception e){

            return new SendResponse(SendResponse.RESULT_JSON_EXCEPTION,e.getMessage()) ;
        }

    }


}
