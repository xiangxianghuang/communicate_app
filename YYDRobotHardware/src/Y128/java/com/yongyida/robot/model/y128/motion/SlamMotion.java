package com.yongyida.robot.model.y128.motion;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.movecontrol.SlamController;

/**
 * Created by HuangXiangXiang on 2018/3/5.
 * 深蓝控制运动
 */
public class SlamMotion {

    private static final String TAG = SlamMotion.class.getSimpleName();


    private static SlamMotion mSlamMotion ;
    public static SlamMotion getInstance(Context context){

        if(mSlamMotion == null){

            mSlamMotion = new SlamMotion(context.getApplicationContext()) ;
        }

        return mSlamMotion ;
    }


    private Context mContext ;

    private SlamController mSlamController ;

    private SlamMotion(Context context){

        this.mContext = context;
        bindSystemService() ;
    }

    private ServiceConnection mMoveServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {

            LogHelper.i(TAG, LogHelper.__TAG__());

            mSlamController = SlamController.Stub.asInterface(service);

            if(mBindSystemServiceListener != null){

                try {
                    mBindSystemServiceListener.onBindSuccess();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            LogHelper.i(TAG, LogHelper.__TAG__());
            mSlamController = null ;
        }
    };


    private void bindSystemService(BindSystemServiceListener bindSystemServiceListener){

        LogHelper.i(TAG, LogHelper.__TAG__());
        this.mBindSystemServiceListener = bindSystemServiceListener ;

        if(mSlamController == null){

            bindSystemService() ;

        }else {

            try {
                this.mBindSystemServiceListener.onBindSuccess();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }

    public void bindSystemService(){

        Intent intent = new Intent();
        intent.setAction("com.yongyida.robot.MoveService");
        intent.setPackage("com.yongyida.robot.slamconnectcontrol");

        mContext.bindService(intent,mMoveServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unBindService(){

        mContext.unbindService(mMoveServiceConnection);
    }

    private BindSystemServiceListener mBindSystemServiceListener ;
    private interface BindSystemServiceListener{

        void onBindSuccess() throws RemoteException;
    }

    public int forward(int arg) {

        LogHelper.i(TAG, LogHelper.__TAG__());

        BindSystemServiceListener bindSystemServiceListener = new BindSystemServiceListener(){
            @Override
            public void onBindSuccess() throws RemoteException {

                LogHelper.i(TAG, LogHelper.__TAG__());
                mSlamController.forward() ;

                mBindSystemServiceListener = null ;
            }
        } ;
        bindSystemService(bindSystemServiceListener);

        return 0 ;
    }

    public int back(int arg) {

        LogHelper.i(TAG, LogHelper.__TAG__());

        BindSystemServiceListener bindSystemServiceListener = new BindSystemServiceListener(){
            @Override
            public void onBindSuccess() throws RemoteException {

                LogHelper.i(TAG, LogHelper.__TAG__());
                mSlamController.back() ;

                mBindSystemServiceListener = null ;
            }
        } ;
        bindSystemService(bindSystemServiceListener);

        return 0 ;
    }

    public int left(int arg) {

        LogHelper.i(TAG, LogHelper.__TAG__());

        BindSystemServiceListener bindSystemServiceListener = new BindSystemServiceListener(){
            @Override
            public void onBindSuccess() throws RemoteException {

                LogHelper.i(TAG, LogHelper.__TAG__());
                mSlamController.left() ;

                mBindSystemServiceListener = null ;
            }
        } ;
        bindSystemService(bindSystemServiceListener);

       return 0 ;
    }

    public int right(int arg) {

        LogHelper.i(TAG, LogHelper.__TAG__());

        BindSystemServiceListener bindSystemServiceListener = new BindSystemServiceListener(){
            @Override
            public void onBindSuccess() throws RemoteException {

                LogHelper.i(TAG, LogHelper.__TAG__());
                mSlamController.right() ;

                mBindSystemServiceListener = null ;
            }
        } ;
        bindSystemService(bindSystemServiceListener);

        return 0 ;
    }


//    @Deprecated
//    public int point(final int position) {
//
//        LogHelper.i(TAG, LogHelper.__TAG__());
//
//        BindSystemServiceListener bindSystemServiceListener = new BindSystemServiceListener(){
//            @Override
//            public void onBindSuccess() throws RemoteException {
//
//                LogHelper.i(TAG, LogHelper.__TAG__());
//                mSlamController.point(position) ;
//
//
//            }
//        } ;
//        bindSystemService(bindSystemServiceListener);
//
//        return  0 ;
//    }


    public int turnSoundAngle(final int angle) {

        LogHelper.i(TAG, LogHelper.__TAG__());

        BindSystemServiceListener bindSystemServiceListener = new BindSystemServiceListener(){
            @Override
            public void onBindSuccess() throws RemoteException {

                LogHelper.i(TAG, LogHelper.__TAG__() + ", angle : " + angle);

                mSlamController.turnSoundAngle(angle) ;

                mBindSystemServiceListener = null ;
            }
        } ;
        bindSystemService(bindSystemServiceListener);

        return 0 ;
    }

    public int stop() {

        LogHelper.i(TAG, LogHelper.__TAG__());

        BindSystemServiceListener bindSystemServiceListener = new BindSystemServiceListener(){
            @Override
            public void onBindSuccess() throws RemoteException {

                LogHelper.i(TAG, LogHelper.__TAG__() );
                mSlamController.stop() ;

                mBindSystemServiceListener = null ;
            }
        } ;
        bindSystemService(bindSystemServiceListener);

        return 0 ;
    }

}
