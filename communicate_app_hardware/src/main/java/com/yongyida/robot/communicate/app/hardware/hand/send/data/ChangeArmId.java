package com.yongyida.robot.communicate.app.hardware.hand.send.data;

import com.yongyida.robot.communicate.app.hardware.motion.send.data.BaseMotionSendControl;

/**
 * Created by HuangXiangXiang on 2018/4/23.
 */
public class ChangeArmId extends BaseHandSendControl {

    //原始的Id 刚刚上电时候是 01
    private  int srcId = 0x01 ;
    //左侧序号是从2-7 右侧序号是从8-13
    private int destId ;


    public int getSrcId() {
        return srcId;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }

    public int getDestId() {
        return destId;
    }

    public void setDestId(int destId) {
        this.destId = destId;
    }
}
