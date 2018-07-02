package com.yongyida.robot.communicate.app.common.response;

import com.google.gson.Gson;

/**
 * Created by HuangXiangXiang on 2017/11/28.
 */
public final class SendResponse {

    private static final String TAG = SendResponse.class.getSimpleName() ;

    public static final int RESULT_SUCCESS                              = 0x00 ; // 执行成功


    public static final int RESULT_CLIENT_NO_SERVICE                    = 0x8001 ; //没有对应的服务
    public static final int RESULT_CLIENT_NONE_DATA                     = 0x8002 ; //返回数据为空

    public static final int RESULT_SERVER_NO_SEND                       = 0xF001 ; //服务端没有对应的Send
    public static final int RESULT_SERVER_NONE_CONTROL                  = 0xF002 ; //服务端没有对应的Send
    public static final int RESULT_SERVER_NO_CONTROL                    = 0xF003 ; //服务端没有对应的Send
    public static final int RESULT_SERVER_NO_HANDLE                     = 0xF004 ; //服务端没有对应的Send(control 中一部分)
    public static final int RESULT_SERVER_NO_METHOD                     = 0xF005 ; //服务端没有对应的实现(handle 中一部分)
    public static final int RESULT_SERVER_PARAMETERS_ERROR              = 0xF006 ; //参数错误
    public static final int RESULT_SERVER_OTHER_ERROR                   = 0xF007 ; //其他错误

    public static final int RESULT_SERVER_NO_SETTING_PERMISSION         = 0xF010 ; //服务端没有对应的Send
    public static final int RESULT_SERVER_TEST_MODE                     = 0xF011 ; //测试模式不允许操作
    public static final int RESULT_SERVER_EXCEPTION                     = 0xF012 ; //服务端发送异常

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

        if(className != null  && baseControl != null){

            try {
                Class clazz = Class.forName(className) ;
                return (BaseResponseControl) GSON.fromJson(baseControl.toString(), clazz);

            } catch (ClassNotFoundException e) {

                e.printStackTrace();
            }catch (Exception e){

                //当返回 为空
            }
        }

        return null;
    }
}
