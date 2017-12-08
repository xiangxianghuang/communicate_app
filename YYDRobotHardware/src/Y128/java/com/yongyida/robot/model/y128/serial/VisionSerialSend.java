package com.yongyida.robot.model.y128.serial;

import com.yongyida.robot.communicate.app.hardware.vision.VisionData;
import com.yongyida.robot.agreement.old.Steering;

/**
 * Created by HuangXiangXiang on 2017/12/7.
 */
public class VisionSerialSend extends SerialSend {

    private Steering.Vision mVision = new Steering.Vision() ;

    public VisionSerialSend(Serial serial) {
        super(serial);
    }

    public void sendVision(VisionData versionData){

        switch (versionData.getPosition()){

            case LEFT:
                mVision.setPosition(Steering.Vision.POSITION_LEFT);
                break;
            case MIDDLE:
                mVision.setPosition(Steering.Vision.POSITION_MIDDLE);
                break;
            case RIGHT:
                mVision.setPosition(Steering.Vision.POSITION_RIGHT);
                break;
        }

        mVision.setDistance((byte) versionData.getDistance());

        sendData(mVision) ;

    }

}
