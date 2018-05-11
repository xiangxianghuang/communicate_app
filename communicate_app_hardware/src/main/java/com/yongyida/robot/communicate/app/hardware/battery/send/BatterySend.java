package com.yongyida.robot.communicate.app.hardware.battery.send;

import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.battery.data.QueryBattery;

/**
 * Created by HuangXiangXiang on 2018/2/24.
 */
public class BatterySend extends BaseSend {

    private QueryBattery queryBattery ;

    public QueryBattery getQueryBattery() {
        return queryBattery;
    }

    public void setQueryBattery(QueryBattery queryBattery) {
        this.queryBattery = queryBattery;
    }
}
