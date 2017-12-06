package com.hiva.communicate.app.hardware.vision.response;

import com.hiva.communicate.app.common.response.BaseResponse;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class VersionDataResponse extends BaseResponse {

    public VersionDataResponse() {

        super();
    }

    public VersionDataResponse(int result, String message) {
        super(result, message);
    }
}
