package com.yongyida.robot.model.y128.motion;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.yongyida.robot.movecontrol.SlamController;

/**
 * Created by HuangXiangXiang on 2018/3/5.
 * 深蓝控制运动
 */
public class SlamMotion {

    private static final String TAG = SlamMotion.class.getSimpleName();

    private Context mContext ;

    private boolean isByDistance;
    private SlamController mSlamController ;

    private ServiceConnection mMoveServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {

            mSlamController = SlamController.Stub.asInterface(service);

        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };



    public SlamMotion(Context context){

        this.mContext = context;
        bindService() ;
    }


    public void bindService(){

        Intent intent = new Intent();
        intent.setAction("com.yongyida.robot.MoveService");
        intent.setPackage("com.yongyida.robot.slamconnectcontrol");

        mContext.bindService(intent,mMoveServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unBindService(){

        mContext.unbindService(mMoveServiceConnection);
    }

    public int forward(int arg) {

        if(mSlamController != null){

            try {
                mSlamController.forward() ;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        return 0 ;
    }

    public int back(int arg) {

        if(mSlamController != null){

            try {
                mSlamController.back() ;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        return  0;
    }

    public int left(int arg) {

        if(mSlamController != null){

            try {
                mSlamController.left() ;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

       return 0 ;
    }

    public int right(int arg) {

        if(mSlamController != null){

            try {
                mSlamController.right() ;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        return 0 ;
    }

//    public int turnLeft(int arg) {
//
//        return IResult.CONTROLLER_NOT_IMPLEMENTED;
//    }
//
//    public int turnRight(int arg) {
//
//        return IResult.CONTROLLER_NOT_IMPLEMENTED;
//    }
//
//    public int backTurnLeft(int arg) {
//
//        return IResult.CONTROLLER_NOT_IMPLEMENTED;
//    }
//
//    public int backTurnRight(int arg) {
//
//        return IResult.CONTROLLER_NOT_IMPLEMENTED;
//    }

    public int point(int position) {

        if(mSlamController != null){

            try {
                mSlamController.point(position) ;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        return  0 ;
    }

    public int introduceEnd(int position) {

        if(mSlamController != null){

            try {
                mSlamController.introduceEnd(position) ;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        return 0 ;
    }

    public int turnSoundAngle(int angle) {

        if(mSlamController != null){

            try {
                mSlamController.turnSoundAngle(angle);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        return 0 ;
    }

    public int stop() {

        if(mSlamController != null){

            try {
                mSlamController.stop();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        return 0 ;
    }

}
