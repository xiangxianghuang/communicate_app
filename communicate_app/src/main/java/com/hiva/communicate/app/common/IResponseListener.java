package com.hiva.communicate.app.common;

import android.support.annotation.NonNull;

import com.hiva.communicate.app.common.response.BaseResponse;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 */
public interface IResponseListener {

    void onResponse(@NonNull BaseResponse response);
}