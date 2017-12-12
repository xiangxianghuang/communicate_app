package com.yongyida.robot.model.y138.vision;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.vision.VisionControl;
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
    public BaseResponse onControl(VisionDataSend visionDataSend) {

        LogHelper.i(TAG, LogHelper.__TAG__() + ",visionDataSend : " + visionDataSend);

        VisionData visionData = visionDataSend.getVisionData() ;

        if(visionData != null){

            return onVisionChanged(visionData) ;
        }

       return null;
    }

    private BaseResponse onVisionChanged(VisionData visionData) {

        byte position = Steering.Vision.POSITION_NONE ;
        switch (visionData.getPosition()){

            case NONE:
                position  = Steering.Vision.POSITION_NONE ;
                break;
            case LEFT:
                position  = Steering.Vision.POSITION_LEFT;
                break;
            case MIDDLE:
                position  = Steering.Vision.POSITION_MIDDLE ;
                break;
            case RIGHT:
                position  = Steering.Vision.POSITION_RIGHT ;
                break;
            case START:
                position  = Steering.Vision.POSITION_START ;
                break;
            case STOP:
                position  = Steering.Vision.POSITION_STOP ;
                break;
        }
        byte distance = (byte) visionData.getDistance() ;
        visionSerialSend.sendVision(position,distance);

        return null;
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
