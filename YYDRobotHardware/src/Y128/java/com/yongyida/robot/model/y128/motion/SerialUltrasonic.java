package com.yongyida.robot.model.y128.motion;

import com.yongyida.robot.model.agreement.Y128Send;
import com.yongyida.robot.model.agreement.Y128Steering;

/**
 * Created by HuangXiangXiang on 2018/3/8.
 */
public class SerialUltrasonic {

    private Y128Steering.Ultrasonic ultrasonic ;

    public SerialUltrasonic(){

        ultrasonic = new Y128Steering.Ultrasonic() ;
    }

    public void setSendAndroidMode(int mode){

        ultrasonic.setSendAndroidMode((byte) mode);
    }

    public void setSendSlamwareMode(int mode){

        ultrasonic.setSendSlamwareMode((byte) mode);
    }


    public void sendData() {

        Y128Send.getInstance().sendData(ultrasonic) ;
    }


}
