package com.hiva.communicate.app.common.response;

import com.google.gson.Gson;
import com.hiva.communicate.app.common.send.BaseSend;

/**
 * Created by HuangXiangXiang on 2017/11/28.
 */
public final class SendResponse {

    private static final String TAG = BaseSend.class.getSimpleName() ;

    public static final int RESULT_SUCCESS                          = 0x00 ; // 执行成功

//    public static final int RESULT_NO_SERVICE                       = -1 ;   // 没有找到服务
//    public static final int RESULT_NONE_DATA                        = -1 ;   // 没有数据
//    public static final int RESULT_CAN_NOT_HANDLE                   = 0x01 ; // 不能处理（没有对应处理方式）
//    public static final int RESULT_EXCEPTION                        = 0x02 ; // 处理过程中发生异常
//    public static final int RESULT_DATA_EROOR                       = 0x03 ; // 数据格式输入有误
//    public static final int RESULT_MEthod_Override                  = 0x03 ; // 方法没有实现
//    public static final int RESULT_PARAMETER_UNREALIZED             = 0x03 ; // 输出参数没有实现
//
//    public static final int RESULT_SUCCESS                      = 0x00 ; //发送成功
//    public static final int RESULT_NO_METHOD                    = 0x02 ; //接收端不能处理
//    public static final int RESULT_SEND_EXCEPTION               = 0x04 ; //发送失败
//    public static final int RESULT_JSON_EXCEPTION               = 0x05 ; //机械返回数据异常
//    public static final int RESULT_NO_SETTING_PERMISSION        = 0x07 ; //没有设置权限
//    public static final int RESULT_NO_SEND                      = 0x08 ; //没有对应的处理集合
//    public static final int RESULT_FACTORY_MODE                 = 0x06 ; //工厂模式


    public static final int RESULT_CLIENT_NO_SERVICE                    = 0x8001 ; //没有对应的服务
    public static final int RESULT_CLIENT_NONE_DATA                     = 0x8002 ; //返回数据为空

    public static final int RESULT_SERVER_NO_SEND                       = 0xF001 ; //服务端没有对应的Send
    public static final int RESULT_SERVER_NO_CONTROL                    = 0xF002 ; //服务端没有对应的Send
    public static final int RESULT_SERVER_NO_HANDLE                     = 0xF003 ; //服务端没有对应的Send

    public static final int RESULT_SERVER_NO_SETTING_PERMISSION         = 0xF010 ; //服务端没有对应的Send
    public static final int RESULT_SERVER_TEST_MODE                     = 0xF011 ; //测试模式不允许操作
    public static final int RESULT_SERVER_EXCEPTION                     = 0xF012 ; //测试模式不允许操作

    private final static Gson GSON = new Gson() ;

    private int result ;
    private String message ;

    private String className ;
    private Object baseControl ;    //使用String 不能解析, 使用具体的对象也不行


    public SendResponse(){

        this.result = RESULT_SUCCESS ;
    }

    public SendResponse(int result){

        this.result = result ;
    }

    public SendResponse(int result, String message){

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
        Object o =  baseControl.sendResponse;
        baseControl.sendResponse = null ;
        this.baseControl = GSON.toJson(baseControl) ;
        baseControl.sendResponse = (SendResponse) o;
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
