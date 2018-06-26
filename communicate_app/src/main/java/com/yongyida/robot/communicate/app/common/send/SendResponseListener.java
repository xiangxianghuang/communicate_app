package com.yongyida.robot.communicate.app.common.send;


import com.yongyida.robot.communicate.app.common.response.BaseResponseControl;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 * 发送返回响应
 */
public interface SendResponseListener<T extends BaseResponseControl> {

    void onSuccess(T t);

    void onFail(int result, String message);
}