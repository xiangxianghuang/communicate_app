package com.yongyida.robot.communicate.app.hardware.battery.response;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.battery.data.BatteryInfo;

/**
 * Created by HuangXiangXiang on 2018/2/24.
 */
public class BatteryResponse extends BaseResponse {

    private BatteryInfo batteryInfo;

    public BatteryInfo getBatteryInfo() {
        return batteryInfo;
    }

    public void setBatteryInfo(BatteryInfo batteryInfo) {
        this.batteryInfo = batteryInfo;
    }
}
