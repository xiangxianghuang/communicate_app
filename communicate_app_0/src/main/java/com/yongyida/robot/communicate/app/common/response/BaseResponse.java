package com.yongyida.robot.communicate.app.common.response;

/**
 * Created by HuangXiangXiang on 2017/11/28.
 */
public abstract class BaseResponse {

    public static final int RESULT_SUCCESS                      = 0x00 ; //执行成功
    public static final int RESULT_CAN_NOT_HANDLE               = 0x01 ; //不能处理（没有对应处理方式）

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


}
