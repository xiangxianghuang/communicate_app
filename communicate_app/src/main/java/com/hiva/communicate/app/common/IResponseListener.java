package com.hiva.communicate.app.common;

import com.hiva.communicate.app.common.response.BaseResponse;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 */
public interface IResponseListener {

    void onResponse(BaseResponse response);
}