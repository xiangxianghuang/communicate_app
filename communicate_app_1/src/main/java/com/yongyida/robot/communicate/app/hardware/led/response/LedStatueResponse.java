package com.yongyida.robot.communicate.app.hardware.led.response;

import com.yongyida.robot.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.led.LedStatue;

/**
 * Created by HuangXiangXiang on 2017/12/1.
 */
public class LedStatueResponse extends BaseResponse {

    private LedStatue ledStatue ;

    public LedStatue getLedStatue() {
        return ledStatue;
    }

    public void setLedStatue(LedStatue ledStatue) {
        this.ledStatue = ledStatue;
    }

}
