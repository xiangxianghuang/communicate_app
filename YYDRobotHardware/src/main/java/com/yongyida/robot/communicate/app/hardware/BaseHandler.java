package com.yongyida.robot.communicate.app.hardware;

import android.content.Context;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.send.BaseSend;

/**
 * Created by HuangXiangXiang on 2017/12/4.
 */
public abstract class BaseHandler<T extends BaseSend> {

    protected Context mContext ;

    public BaseHandler(Context context){

        this.mContext = context ;
    }

    public abstract BaseResponse onHandler(T send) ;


}
