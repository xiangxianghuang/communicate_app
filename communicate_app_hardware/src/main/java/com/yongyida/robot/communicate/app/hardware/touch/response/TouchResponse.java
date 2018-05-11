package com.yongyida.robot.communicate.app.hardware.touch.response;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.touch.data.TouchInfo;

/**
 * Created by HuangXiangXiang on 2018/3/1.
 */
public class TouchResponse extends BaseResponse {

    private TouchInfo mTouchPositions ;    // 触摸点 的信息

    public TouchInfo getTouchPositions() {
        return mTouchPositions;
    }

    public void setTouchPositions(TouchInfo touchPositions) {
        this.mTouchPositions = touchPositions;
    }
}
