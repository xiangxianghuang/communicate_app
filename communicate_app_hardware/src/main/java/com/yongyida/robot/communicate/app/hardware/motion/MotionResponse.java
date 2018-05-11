package com.yongyida.robot.communicate.app.hardware.motion;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.motion.data.MoveFault;

/**
 * Created by HuangXiangXiang on 2018/3/8.
 */
public class MotionResponse extends BaseResponse {

    private MoveFault moveFault ;

    public MoveFault getMoveFault() {
        return moveFault;
    }

    public void setMoveFault(MoveFault moveFault) {
        this.moveFault = moveFault;
    }
}
