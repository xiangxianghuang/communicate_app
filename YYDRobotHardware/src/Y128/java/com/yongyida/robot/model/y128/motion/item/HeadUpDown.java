package com.yongyida.robot.model.y128.motion.item;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.HeadControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.model.agreement.Y128Send;
import com.yongyida.robot.model.agreement.Y128Steering;

/**
 * Created by Huangxiangxiang on 2017/8/11.
 */
public class HeadUpDown {

    private static final String TAG = HeadUpDown.class.getName();

    private Y128Steering.SteerHeadUpDown mHeadUpDown = new Y128Steering.SteerHeadUpDown();

    /***/
    private int angle ;

    public int headUp(int arg) {

        LogHelper.i(TAG,LogHelper.__TAG__() + " : " + arg) ;
        headUpDownTurnBy(arg) ;

        return 0 ;
    }


    public int headDown(int arg) {

        LogHelper.i(TAG,LogHelper.__TAG__() + " : " + arg) ;
        headUpDownTurnBy(-arg) ;

        return 0;
    }


    public int headUpDownTurnTo(int arg){

        LogHelper.i(TAG,LogHelper.__TAG__() + " : " + arg) ;

        return headUpDownTurnToOneByOne(arg) ;
    }

    public int headUpDownTurnBy(int arg){

        LogHelper.i(TAG,LogHelper.__TAG__() + " : " + arg) ;

        return headUpDownTurnTo(angle + arg) ;
    }

    /**
     * 转到目标角度（以一份份的转,为了方便随时停下来）
     * */
    private int headUpDownTurnToOneByOne(int targetAngle){

        LogHelper.i(TAG,LogHelper.__TAG__() + " : " + targetAngle) ;

        if(targetAngle < -100){

            targetAngle = -100 ;

        }else if(targetAngle > 100){

            targetAngle = 100 ;
        }

        startHeadUpDown(false, targetAngle);

        return 0 ;
    }


    public int headUpDownRest() {

        LogHelper.i(TAG,LogHelper.__TAG__() ) ;

        stopHeadUpDown() ;

        mHeadUpDown.turnReset();
        this.angle = 0 ;

        return sendData(mHeadUpDown);
    }


    public int headUpDownShark() {

        LogHelper.i(TAG,LogHelper.__TAG__() ) ;

        startHeadUpDown(true, 0); ;

        return sendData(mHeadUpDown);
    }


    public int headUpDownStop() {

        LogHelper.i(TAG,LogHelper.__TAG__() ) ;

        stopHeadUpDown() ;

        return 0 ;
    }


    private HeadUpDownThread mHeadUpDownThread ;
    private void startHeadUpDown(boolean isShark ,int targetAngle ){

        LogHelper.i(TAG,LogHelper.__TAG__() + " : " + targetAngle) ;

        stopHeadUpDown() ;

        mHeadUpDownThread = new HeadUpDownThread(isShark, targetAngle) ;
        mHeadUpDownThread.start();
    }

    private void stopHeadUpDown(){

        if(mHeadUpDownThread != null){

            if(mHeadUpDownThread.isRun){

                mHeadUpDownThread.stopRun();
            }
            mHeadUpDownThread = null ;
        }

    }

    public SendResponse onHandler(HeadControl headControl, IResponseListener responseListener) {

        return null ;
    }


    private class HeadUpDownThread extends Thread{
        private int INTERVAL_TIME = 10 ;

        private boolean isRun = true ;

        private boolean isShark ;
        private int targetAngle ;

        private HeadUpDownThread(boolean isShark, int targetAngle){

            this.isShark = isShark ;
            this.targetAngle = targetAngle ;
        }

        private void stopRun(){

            LogHelper.i(TAG, LogHelper.__TAG__()) ;
            isRun = false;
        }


        @Override
        public void run() {

            if(isShark){

                headUpDownShark();

            }else{

                if(targetAngle < angle){

                    headDown(targetAngle);

                }else if(targetAngle > angle){

                    headUp(targetAngle);
                }

            }
        }


        private void headDown(int targetAngle){

            while(isRun && (angle-- > targetAngle)){

                LogHelper.w(TAG, LogHelper.__TAG__() + targetAngle  + ", isRun : " + isRun) ;

                mHeadUpDown.turnDown(angle);
                sendData(mHeadUpDown);

                try {
                    Thread.sleep(INTERVAL_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void headUp(int targetAngle) {

            while(isRun && (angle++ < targetAngle)){

                LogHelper.w(TAG, LogHelper.__TAG__() + targetAngle  + ", isRun : " + isRun) ;

                mHeadUpDown.turnUp(angle);
                sendData(mHeadUpDown);

                try {
                    Thread.sleep(INTERVAL_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        private void headUpDownShark(){

            LogHelper.i(TAG, LogHelper.__TAG__()) ;

            while(isRun){

                headUp(100) ;
                headDown(-100) ;
            }
        }

    }






    private int sendData(Y128Steering.SingleChip singleChip) {

        return Y128Send.getInstance().sendData(singleChip.getCmd()) ;
    }

}
