package com.hiva.communicate.app.common.response;

/**
 * Created by HuangXiangXiang on 2017/11/28.
 */
public class BaseResponse {

    public static final int RESULT_SUCCESS                      = 0x00 ; //执行成功
    public static final int RESULT_CAN_NOT_HANDLE               = 0x01 ; //不能处理（没有对应处理方式）
    public static final int RESULT_EXCEPTION                    = 0x02 ; //处理过程中发生异常

    private int result ;
    private String message ;

    public BaseResponse(){

        this.result = RESULT_SUCCESS ;
        this.message = null ;
    }

    public BaseResponse(int result, String message){

        this.result = result ;
        this.message = message ;
    }


    @Override
    public String toString() {

        return "result : " + result + ", message : " + message;
    }
}
