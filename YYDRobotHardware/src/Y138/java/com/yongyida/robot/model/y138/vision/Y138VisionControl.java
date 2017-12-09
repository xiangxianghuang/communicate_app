package com.yongyida.robot.model.y138.vision;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.vision.VisionControl;
import com.yongyida.robot.communicate.app.hardware.vision.data.VisionControlData;
import com.yongyida.robot.communicate.app.hardware.vision.data.VisionData;
import com.yongyida.robot.communicate.app.hardware.vision.send.VisionDataSend;
import com.yongyida.robot.model.y138.agreement.old.Steering;
import com.yongyida.robot.model.y138.serial.VisionSerialSend;


/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y138VisionControl extends VisionControl {

    private static final String TAG = Y138VisionControl.class.getSimpleName() ;

    private VisionSerialSend visionSerialSend ;

    public void setVisionSerialSend(VisionSerialSend visionSerialSend) {
        this.visionSerialSend = visionSerialSend;
    }




    @Override
    public BaseResponse sendVersionData(VisionDataSend visionDataSend) {

        LogHelper.i(TAG, LogHelper.__TAG__() + ",visionDataSend : " + visionDataSend);

        VisionControlData visionControlData = visionDataSend.getVisionControlData();
        VisionData visionData = visionDataSend.getVisionData() ;

        if (visionControlData != null){

            if (visionControlData.isStart()){

                return startVisionControl() ;

            }else{

                return stopVisionControl() ;
            }

        }else if(visionData != null){

            return onVisionChanged(visionData) ;
        }

       return null;
    }


    private boolean isStartVisionControl(){

       return (mVisionThread != null && mVisionThread.isRun) ;
    }

    private BaseResponse startVisionControl() {

        if(!isStartVisionControl()){

            mVisionThread = new VisionThread() ;
            mVisionThread.start() ;
        }

        return null;
    }


    private BaseResponse onVisionChanged(VisionData visionData) {

        if(isStartVisionControl()){

            this.mVisionData = visionData ;
        }

        return null;
    }

    private BaseResponse stopVisionControl() {

        if(isStartVisionControl()){

            mVisionThread.stopRun();
            mVisionThread = null ;
        }

        return null;
    }

    private VisionData mVisionData ;
    private static final int DELAY_MILLIS = 100;
    private VisionThread mVisionThread ;
    private class VisionThread extends Thread{

        private boolean isRun = true ;

        @Override
        public void run() {

            while (isRun){

                if(mVisionData != null){

                    byte position = Steering.Vision.POSITION_LEFT ;

                    switch (mVisionData.getPosition()){

                        case LEFT:
                            position  = Steering.Vision.POSITION_LEFT;
                            break;
                        case MIDDLE:

                            position  = Steering.Vision.POSITION_MIDDLE ;
                            break;
                        case RIGHT:

                            position  = Steering.Vision.POSITION_RIGHT ;
                            break;
                    }

                    byte distance = (byte) mVisionData.getDistance() ;

                    visionSerialSend.sendVision(position,distance);

                    mVisionData = null ;

                }else{

                    visionSerialSend.sendVision();
                }

                try {
                    Thread.sleep(DELAY_MILLIS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void stopRun(){

            isRun = false ;
        }

    }




    @Override
    public boolean start() {
        return false;
    }

    @Override
    public boolean stop() {
        return false;
    }

    @Override
    public boolean isStart() {
        return false;
    }

}
