package com.yongyida.robot.model.y128.motion;

import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.model.agreement.Y128Send;
import com.yongyida.robot.model.agreement.Y128Steering;

/**
 * Created by Huangxiangxiang on 2017/8/11.
 */
public class SerialSendHeadLeftRight {

    private static final String TAG = SerialSendHeadLeftRight.class.getName();

    private Y128Steering.SteerHeadLeftRight mHeadLeftRight = new Y128Steering.SteerHeadLeftRight();


    /**
     * 角度值（根据百分比）
     *  -100 - 0，   表示在左边
     *   0 - 100     表示在右边
     * */
    private int angle = 0;

    /**
     * 向左摇头
     * */
    public int headLeft(int arg) {

        LogHelper.i(TAG,LogHelper.__TAG__() + " : " + arg) ;
        headLeftRightTurnBy(- arg) ;

        return 0 ;
    }

    /**
     * 向右摇头
     * */
    public int headRight(int arg) {

        LogHelper.i(TAG,LogHelper.__TAG__() + " : " + arg) ;
        headLeftRightTurnBy(+ arg) ;

        return 0 ;
    }

    /**
     * 指定角度值
     * @param arg 取值范围为-100，100
     *
     * */
    public int headLeftRightTurnTo(int arg) {

        LogHelper.i(TAG,LogHelper.__TAG__() + " : " + arg) ;

        return headLeftRightTurnToOneByOne(arg) ;
    }

    /**
     *
     * 与当前角度值得偏移量
     * @param arg   负值表示向左方
     *               正值表示向右方
     *
     * */
    public int headLeftRightTurnBy(int arg) {

        LogHelper.i(TAG,LogHelper.__TAG__() + " : " + arg) ;

        return headLeftRightTurnTo(angle + arg) ;
    }

    /**
     *
     * 头部归中
     *
     * */
    public int headLeftRightRest() {

        LogHelper.i(TAG,LogHelper.__TAG__()) ;

        stopHeadLeftRight() ;

        angle = 0 ;

        mHeadLeftRight.turnReset();
        return sendData(mHeadLeftRight);
    }

    /**
     *
     * 头部左右摇头
     *
     * */
    public int headLeftRightShark() {

        LogHelper.i(TAG,LogHelper.__TAG__()) ;

        startHeadLeftRight(true, 0) ;

        return 0 ;
    }

    /**
     *
     * 头部停止转动
     *
     * */
    public int headLeftRightStop() {

        LogHelper.i(TAG,LogHelper.__TAG__()) ;

        stopHeadLeftRight() ;

        return 0;
    }



    /**
     * 转到目标角度（以一份份的转,为了方便随时停下来）
     * */
    private int headLeftRightTurnToOneByOne(int targetAngle){

        LogHelper.i(TAG,LogHelper.__TAG__() + " : " + targetAngle) ;

        if(targetAngle < -100){

            targetAngle = -100 ;

        }else if(targetAngle > 100){

            targetAngle = 100 ;
        }

        startHeadLeftRight(false, targetAngle) ;

        return 0 ;

    }


    private HeadLeftRightThread mHeadLeftRightThread ;
    private void startHeadLeftRight(boolean isShark, int targetAngle){

        LogHelper.i(TAG,LogHelper.__TAG__() + " : " + targetAngle) ;

        stopHeadLeftRight() ;

        mHeadLeftRightThread = new HeadLeftRightThread(isShark, targetAngle) ;
        mHeadLeftRightThread.start();
    }

    private void stopHeadLeftRight(){

        LogHelper.i(TAG,LogHelper.__TAG__()) ;

        if(mHeadLeftRightThread != null){

            if(mHeadLeftRightThread.isRun){

                mHeadLeftRightThread.stopRun();
            }

            mHeadLeftRightThread = null ;
        }
    }



    /**使用线程*/
    private class HeadLeftRightThread extends Thread{

        private int INTERVAL_TIME = 20 ;

        private boolean isRun = true ;

        private boolean isShark ;
        private int targetAngle ;

        private HeadLeftRightThread(boolean isShark, int targetAngle){

            LogHelper.i(TAG, LogHelper.__TAG__()) ;

            this.isShark = isShark ;
            this.targetAngle = targetAngle ;
        }

        private void stopRun(){

            LogHelper.i(TAG, LogHelper.__TAG__()) ;
            isRun = false;
        }


        @Override
        public void run() {

            LogHelper.i(TAG, LogHelper.__TAG__()) ;

            if(isShark){

                headLeftRightShark() ;

            }else {

                if(targetAngle < angle){

                    headLeft(targetAngle) ;

                }else if(targetAngle > angle){

                    headRight(targetAngle);
                }
            }
        }


        private void headLeft(int targetAngle) {

            LogHelper.i(TAG, LogHelper.__TAG__()) ;

             while(isRun && (angle-- > targetAngle)){

                LogHelper.w(TAG, LogHelper.__TAG__() + targetAngle  + ", isRun : " + isRun) ;

                mHeadLeftRight.turnLeft(angle);
                sendData(mHeadLeftRight);

                try {
                    Thread.sleep(INTERVAL_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void headRight(int targetAngle){

            LogHelper.i(TAG, LogHelper.__TAG__()) ;

            while(isRun && (angle++ < targetAngle)){

                LogHelper.w(TAG, LogHelper.__TAG__() + targetAngle  + ", isRun : " + isRun) ;

                mHeadLeftRight.turnRight(angle);
                sendData(mHeadLeftRight);

                try {
                    Thread.sleep(INTERVAL_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        /***/
        private void headLeftRightShark(){

            LogHelper.i(TAG, LogHelper.__TAG__()) ;

            while(isRun){

                headLeft(-100) ;
                headRight(100) ;
            }
        }

    }

    private int sendData(Y128Steering.SingleChip singleChip) {

       return Y128Send.getInstance().sendData(singleChip) ;
    }

}
