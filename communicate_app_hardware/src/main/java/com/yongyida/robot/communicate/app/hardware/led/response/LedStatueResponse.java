package com.yongyida.robot.communicate.app.hardware.led.response;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.led.LedStatue;

/**
 * Created by HuangXiangXiang on 2017/12/1.
 * 响应获取LED当前状态
 */
public class LedStatueResponse extends BaseResponse {

    private LedStatue ledStatue ;

    public LedStatueResponse(){
        super();
    }

    public LedStatueResponse(int result, String message) {
        super(result, message);
    }

    public LedStatue getLedStatue() {
        return ledStatue;
    }

    public void setLedStatue(LedStatue ledStatue) {
        this.ledStatue = ledStatue;
    }

}
