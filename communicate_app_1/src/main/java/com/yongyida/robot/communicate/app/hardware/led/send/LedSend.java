package com.yongyida.robot.communicate.app.hardware.led.send;

import com.yongyida.robot.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.led.LedStatue;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 * 修改Led灯状态
 */
public class LedSend extends BaseSend{

    private LedStatue ledStatue ;

    public LedStatue getLedStatue() {
        return ledStatue;
    }

    public void setLedStatue(LedStatue ledStatue) {
        this.ledStatue = ledStatue;
    }
}
