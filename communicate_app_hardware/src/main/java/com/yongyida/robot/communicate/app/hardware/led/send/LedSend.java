package com.yongyida.robot.communicate.app.hardware.led.send;

import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.led.data.LedControl;
import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 * 修改Led灯状态
 */
public class LedSend extends BaseSend{

    private LedControl ledControl;
    private LedScene ledScene ;

    public LedScene getLedScene() {
        return ledScene;
    }

    public void setLedScene(LedScene ledScene) {
        this.ledScene = ledScene;
    }

    public LedControl getLedControl() {
        return ledControl;
    }

    public void setLedControl(LedControl ledControl) {
        this.ledControl = ledControl;
    }
}
