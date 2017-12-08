package com.yongyida.robot.communicate.app.hardware.vision.response;

import com.hiva.communicate.app.common.response.BaseResponse;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class VisionDataResponse extends BaseResponse {

    public VisionDataResponse() {

        super();
    }

    public VisionDataResponse(int result, String message) {
        super(result, message);
    }
}
