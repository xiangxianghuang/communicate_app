package com.yongyida.robot.communicate.app.hardware.led.send;

import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 * 修改Led灯状态
 */
public class LedSend extends BaseSend{

    private LedScene ledScene ;

    public LedScene getLedScene() {
        return ledScene;
    }

    public void setLedScene(LedScene ledScene) {
        this.ledScene = ledScene;
    }
}
