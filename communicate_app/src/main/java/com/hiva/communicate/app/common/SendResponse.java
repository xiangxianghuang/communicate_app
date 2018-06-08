package com.hiva.communicate.app.common;

import java.util.Locale;

/**
 * Created by HuangXiangXiang on 2017/11/28.
 * 对发送的返回的结果:
 *
 */
public class SendResponse {

    public static final int RESULT_SUCCESS                      = 0x00 ; //发送成功
    public static final int RESULT_NO_SERVICE                   = 0x01 ; //没有对应的服务
    public static final int RESULT_NO_METHOD                    = 0x02 ; //接收端不能处理
    public static final int RESULT_SEND_EXCEPTION               = 0x04 ; //发送失败
    public static final int RESULT_JSON_EXCEPTION               = 0x05 ; //机械返回数据异常
    public static final int RESULT_NO_SETTING_PERMISSION        = 0x07 ; //没有设置权限
    public static final int RESULT_NO_SEND                      = 0x08 ; //没有对应的处理集合
    public static final int RESULT_FACTORY_MODE                 = 0x06 ; //工厂模式

    private final int result ;
    private final String message ;

    public SendResponse(int result){

        this.result = result ;
        this.message = null ;
    }

    public SendResponse(int result, String message){

        this.result = result ;
        this.message = message ;
    }

    public int getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public String toString() {

        return String.format(Locale.CHINA, "result: %s, message : %s" ,result,message) ;
    }
}
