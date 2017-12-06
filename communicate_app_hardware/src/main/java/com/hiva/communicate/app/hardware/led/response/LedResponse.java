package com.hiva.communicate.app.hardware.led.response;

import com.hiva.communicate.app.common.response.BaseResponse;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 *
 */
public class LedResponse extends BaseResponse {

    public LedResponse() {
        super();
    }

    public LedResponse(int result, String message) {
        super(result, message);
    }
}
