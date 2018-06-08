package com.yongyida.robot.communicate.app.hardware.motion.response.data;

import com.hiva.communicate.app.common.response.BaseResponseControl;

/**
 * Created by HuangXiangXiang on 2018/3/8.
 *
 * 运动错误信息
 */
public class MoveFault extends BaseResponseControl {

    private int code ;
    private String message ;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
