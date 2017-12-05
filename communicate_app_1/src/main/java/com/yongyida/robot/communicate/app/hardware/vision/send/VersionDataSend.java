package com.yongyida.robot.communicate.app.hardware.vision.send;

import com.yongyida.robot.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.vision.VersionData;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class VersionDataSend extends BaseSend {

    private VersionData versionData ;

    public VersionData getVersionData() {
        return versionData;
    }

    public void setVersionData(VersionData versionData) {
        this.versionData = versionData;
    }
}
