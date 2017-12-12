package com.yongyida.robot.model.y138.serial;

import com.yongyida.robot.model.y128.serial.Serial;
import com.yongyida.robot.model.y128.serial.SerialSend;
import com.yongyida.robot.model.y138.agreement.old.Steering;

/**
 * Created by HuangXiangXiang on 2017/12/7.
 */
public class VisionSerialSend extends SerialSend {

    private Steering.Vision mVision = new Steering.Vision() ;

    public VisionSerialSend(Serial serial) {
        super(serial);
    }



    public void sendStartVision(){

        mVision.setPosition(Steering.Vision.POSITION_START);
        mVision.setDistance((byte) 0);
        sendData(mVision) ;
    }


    public void sendNoneVision(){

        mVision.setPosition(Steering.Vision.POSITION_NONE);
        mVision.setDistance((byte) 0);
        sendData(mVision) ;
    }


    public void sendVision(byte position, byte distance){

        mVision.setPosition(position);
        mVision.setDistance(distance);
        sendData(mVision) ;
    }



    public void sendStopVision(){

        mVision.setPosition(Steering.Vision.POSITION_STOP);
        mVision.setDistance((byte) 0);
        sendData(mVision) ;
    }

}
