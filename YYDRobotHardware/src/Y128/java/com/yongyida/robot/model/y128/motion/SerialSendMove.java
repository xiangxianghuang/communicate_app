package com.yongyida.robot.model.y128.motion;

import com.yongyida.robot.model.agreement.Y128Send;
import com.yongyida.robot.model.agreement.Y128Steering;

/**
 * Created by Huangxiangxiang on 2017/8/11.
 */
public class SerialSendMove{


    private Y128Steering.FootMove mMove ;

    public SerialSendMove() {

        mMove = new Y128Steering.FootMove() ;
        mMove.setSpeed(2);
    }

    /**
     *
     * 速度值
     * 1-100
     * */
    public void setSpeed(int speed){

        if(speed < 34){

            mMove.setSpeed(0);

        }else if(speed < 67){

            mMove.setSpeed(1);
        }else {

            mMove.setSpeed(2);
        }
    }


    public int forward(int arg) {

        startControl(TYPE_FORWARD , arg) ;

        return 0 ;
    }

    public int back(int arg) {

        startControl(TYPE_BACK, arg) ;

        return 0 ;
    }

    public int left(int arg) {

        startControl(TYPE_LEFT , arg) ;

        return 0 ;
    }

    public int right(int arg) {

        startControl(TYPE_RIGHT , arg) ;
        return 0 ;
    }


    public int stop() {

        startControl(TYPE_STOP,0) ;

        return 0 ;
    }

    private void startControl(int type , int time){

        stopControl() ;

        mControlThread = new ControlThread(type, time) ;
        mControlThread.start();
    }

    private void stopControl(){

        if(mControlThread != null && mControlThread.isRun){

            mControlThread.stopRun();
            mControlThread = null ;
        }
    }


    private ControlThread mControlThread ;

    private static final int TYPE_FORWARD   = 0x01  ;
    private static final int TYPE_BACK      = 0x02  ;
    private static final int TYPE_LEFT      = 0x03  ;
    private static final int TYPE_RIGHT     = 0x04  ;
    private static final int TYPE_STOP      = 0x05  ;

    private static final int LEFT_RIGHT_INTERVAL_TIME = 30 ; // 左右转动的发送间隔时间

    private class ControlThread extends Thread{

        private boolean isRun ;

        private int type ;
        private int time ;

        private ControlThread(int type , int time){

            this.isRun = true ;

            this.type = type ;
            this.time = time ;

        }

        @Override
        public void run() {

            switch (type){

                case TYPE_FORWARD:

                    forward();
                    break;

                case TYPE_BACK:

                    back();
                    break;

                case TYPE_LEFT:

                    left();
                    break;

                case TYPE_RIGHT:

                    right();
                    break;
                case TYPE_STOP:

                    stopMove();
                    break;
            }


        }

        private void stopRun(){

            isRun = false ;
        }

        private void forward(){

            mMove.forward();
            sendData(mMove) ;

            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(isRun){

                stopMove() ;
            }

        }

        private void back(){

            mMove.back();
            sendData(mMove) ;

            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(isRun){

                stopMove() ;
            }

        }

        private void left(){

            mMove.left();

            sendData(mMove) ;
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(isRun){

                stopMove() ;
            }

        }

        private void right(){

            mMove.right();

            sendData(mMove) ;
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(isRun){

                stopMove() ;
            }

        }

        private void stopMove(){

            mMove.stop();
            sendData(mMove) ;
        }
    }

    private void sendData(Y128Steering.SingleChip move) {

        Y128Send.getInstance().sendData(move) ;
    }


}
