package com.hiva.communicate.app.common.response;

import com.google.gson.Gson;
import com.hiva.communicate.app.common.send.BaseSend;

/**
 * Created by HuangXiangXiang on 2017/11/28.
 */
public final class BaseResponse{

    private static final String TAG = BaseSend.class.getSimpleName() ;

    public static final int RESULT_NONE_DATA                    = -1 ;  //没有数据
    public static final int RESULT_SUCCESS                      = 0x00 ; //执行成功
    public static final int RESULT_CAN_NOT_HANDLE               = 0x01 ; //不能处理（没有对应处理方式）
    public static final int RESULT_EXCEPTION                    = 0x02 ; //处理过程中发生异常

    private final static Gson GSON = new Gson() ;

    private int result ;
    private String message ;

    private String className ;
    private Object baseControl ;    //使用String 不能解析, 使用具体的对象也不行

    public BaseResponse(){

        this.result = RESULT_SUCCESS ;
        this.message = null ;
    }

    public BaseResponse(int result){

        this.result = result ;
    }

    public  BaseResponse(int result, String message){

        this.result = result ;
        this.message = message ;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object getBaseControl() {
        return baseControl;
    }

    public void setBaseControl(Object baseControl) {
        this.baseControl = baseControl;
    }

    @Override
    public String toString() {

        return "result : " + result + ", message : " + message;
    }

    public void setBaseControl(BaseResponseControl baseControl){

        this.className = baseControl.getClass().getName() ;

        // 转换前将send 排除
        Object o =  baseControl.baseResponse ;
        baseControl.baseResponse = null ;
        this.baseControl = GSON.toJson(baseControl) ;
        baseControl.baseResponse = (BaseResponse) o;
    }

    public BaseResponseControl getBaseResponseControl() {

        try {
            Class clazz = Class.forName(className) ;
            return (BaseResponseControl) GSON.fromJson(baseControl.toString(), clazz);

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

        return null;
    }
}
