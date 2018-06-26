package com.yongyida.robot.model.y148.montrol.control.item;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.FootControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.SoundLocationControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.SteeringControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.utils.AppUtils;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.movecontrol.SlamController;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by HuangXiangXiang on 2018/3/5.
 * 深蓝控制运动 运动控制不支持调节速度
 * 因此支持时间和距离
 */
public class FootSlam {

    private static final String TAG = FootSlam.class.getSimpleName();

    private static final String PACKAGE_NAME    = "com.yongyida.robot.slamconnectcontrol" ;
    private static final String ACTION          = "com.yongyida.robot.MoveService" ;

    private static FootSlam mInstance ;
    public static FootSlam getInstance(Context context){

        if(mInstance == null){

            mInstance = new FootSlam(context.getApplicationContext()) ;
        }
        return mInstance ;
    }

    private Context mContext ;

    private SlamController mSlamController ;

    private FootSlam(Context context){

        this.mContext = context;
        bindSystemService() ;
    }

    /**
     * 是否存在包名与action
     * */
    public boolean isExist(){

        HashSet<String> packageNames = AppUtils.getPackageNameByServiceAction(mContext,ACTION) ;

        return packageNames.contains(PACKAGE_NAME) ;
    }


    private ServiceConnection mMoveServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {

            LogHelper.i(TAG, LogHelper.__TAG__());

            mSlamController = SlamController.Stub.asInterface(service);

            synchronized (mBindServiceListeners){

                final int size = mBindServiceListeners.size() ;
                for (int i = 0 ; i < size ; i++){

                    mBindServiceListeners.get(i).onBindSuccess();
                }
                mBindServiceListeners.clear();
            }

        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            LogHelper.i(TAG, LogHelper.__TAG__());
            mSlamController = null ;
        }
    };


    /**
     * 检查深蓝服务
     * @return 0 表示 已经成功连接
     *          1 表示 系统中找不到深蓝服务
     *          2 表示 没有绑定服务
     * */
    private int checkSlamService(){

        if(mSlamController != null){

            LogHelper.i(TAG, LogHelper.__TAG__() + " 已经成功连接");

            return 0 ;
        }

        if(!isExist()){

            LogHelper.e(TAG, LogHelper.__TAG__() + ", 系统中找不到深蓝服务");
            return 1 ;
        }

        return 2 ;
    }


    /**
     * */
    private void  bindSlamService(BindServiceListener bindServiceListener){
        LogHelper.i(TAG , LogHelper.__TAG__());

        mBindServiceListeners.add(bindServiceListener) ;
        bindSystemService() ;
    }

    public void bindSystemService(){

        Intent intent = new Intent();
        intent.setPackage(PACKAGE_NAME);
        intent.setAction(ACTION);

        mContext.bindService(intent,mMoveServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unBindService(){

        mContext.unbindService(mMoveServiceConnection);
    }

    private final ArrayList<BindServiceListener> mBindServiceListeners = new ArrayList<>() ;
    private interface BindServiceListener{

        void onBindSuccess();
    }

    /**
     * 足下运动
     * */
    public SendResponse onHandler(final FootControl footControl, final IResponseListener responseListener) {

        LogHelper.i(TAG, LogHelper.__TAG__());

        int result = checkSlamService() ;
        if(result == 0 ){

            LogHelper.i(TAG, LogHelper.__TAG__() + " 已经成功连接");

            return controlFoot(footControl, responseListener) ;
        }

        if(result == 1){

            LogHelper.e(TAG, LogHelper.__TAG__() + ", 系统中找不到深蓝服务");
            return new SendResponse(SendResponse.RESULT_SERVER_OTHER_ERROR , "系统中找不到深蓝服务") ;
        }

        BindServiceListener bindServiceListener = new BindServiceListener() {
            @Override
            public void onBindSuccess() {

                SendResponse sendResponse = controlFoot(footControl, responseListener) ;

                if(responseListener != null){

                    responseListener.onResponse(sendResponse) ;
                }
            }
        };
        bindSlamService(bindServiceListener) ;

        LogHelper.i(TAG, LogHelper.__TAG__() + ", 正在绑定深蓝服务");
        return new SendResponse(SendResponse.RESULT_SERVER_OTHER_ERROR , "正在绑定深蓝服务") ;
    }


    /**
     * 运动控制
     * */
    private SendResponse controlFoot(FootControl footControl, IResponseListener responseListener) {

        SteeringControl control = footControl.getFoot() ;
        if(control == null){

            return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR , "foot 数据为空") ;
        }

        FootControl.Action action = footControl.getAction() ;
        switch (action){

            case FORWARD:
                forward(2000) ;
               break;
            case BACK:
                back(2000); ;
                break;
            case LEFT:
                left(2000); ;
                break;
            case RIGHT:
                right(2000); ;
                break;
            case STOP:
                stop();
                break;

            default:
                return new SendResponse(SendResponse.RESULT_SERVER_PARAMETERS_ERROR , "foot action 不能处理") ;
        }
        return new SendResponse() ;
    }



    private void forward(int arg) {

        LogHelper.i(TAG, LogHelper.__TAG__());

        try {
            mSlamController.forward() ;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void back(int arg) {

        LogHelper.i(TAG, LogHelper.__TAG__());
        try {
            mSlamController.back() ;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void left(int arg) {

        LogHelper.i(TAG, LogHelper.__TAG__());
        try {
            mSlamController.left() ;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void right(int arg) {

        LogHelper.i(TAG, LogHelper.__TAG__());
        try {
            mSlamController.right() ;
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }



    public void turnSoundAngle(final int angle) {

        LogHelper.i(TAG, LogHelper.__TAG__() + ", angle : " + angle);

        try {
            mSlamController.turnSoundAngle(angle) ;
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void stop() {

        LogHelper.i(TAG, LogHelper.__TAG__());
        try {
            mSlamController.stop() ;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 巡航某条线路
     * */
    public void navigateLine(String position) {

        LogHelper.i(TAG, LogHelper.__TAG__());

    }

    /**
     * 导航某个点
     * */
    public void navigatePoint(String position) {

        LogHelper.i(TAG, LogHelper.__TAG__());



    }


    /**声源定位*/
    public SendResponse onHandler(final SoundLocationControl soundLocationControl, final IResponseListener responseListener) {

        LogHelper.i(TAG, LogHelper.__TAG__());

        int result = checkSlamService() ;
        if(result == 0 ){

            LogHelper.i(TAG, LogHelper.__TAG__() + " 已经成功连接");

            return soundLocationControl(soundLocationControl, responseListener) ;
        }

        if(result == 1){

            LogHelper.e(TAG, LogHelper.__TAG__() + ", 系统中找不到深蓝服务");
            return new SendResponse(SendResponse.RESULT_SERVER_OTHER_ERROR , "系统中找不到深蓝服务") ;
        }

        BindServiceListener bindServiceListener = new BindServiceListener() {
            @Override
            public void onBindSuccess() {

                SendResponse sendResponse = soundLocationControl(soundLocationControl, responseListener) ;

                if(responseListener != null){

                    responseListener.onResponse(sendResponse) ;
                }
            }
        };
        bindSlamService(bindServiceListener) ;

        LogHelper.i(TAG, LogHelper.__TAG__() + ", 正在绑定深蓝服务");
        return new SendResponse(SendResponse.RESULT_SERVER_OTHER_ERROR , "正在绑定深蓝服务") ;

    }



    private SendResponse soundLocationControl(final SoundLocationControl soundLocationControl, final IResponseListener responseListener){

        int angle = soundLocationControl.getAngle() ;
        try {
            mSlamController.turnSoundAngle(angle) ;
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return new SendResponse();
    }
}
