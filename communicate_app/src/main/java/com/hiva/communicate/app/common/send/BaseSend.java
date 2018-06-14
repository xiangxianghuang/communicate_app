package com.hiva.communicate.app.common.send;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.hiva.communicate.app.common.send.data.BaseSendControl;

/**
 * Created by HuangXiangXiang on 2017/11/27.
 * 所有发送的基类 一般用作区分种类
 */
public class BaseSend <T extends BaseSendControl> {

    private static final String TAG = BaseSend.class.getSimpleName() ;

    private final static Gson GSON = new Gson() ;

    private String className ;
    private Object baseControl ;    //使用String 不能解析, 使用具体的对象也不行

    public void setBaseControl(BaseSendControl baseControl){

        this.className = baseControl.getClass().getName() ;

        // 转换前将send 排除
//        Object o = baseControl.baseSend ;
//        baseControl.baseSend = null ;
//
//        this.baseControl = GSON.toJson(baseControl) ;
//        baseControl.baseSend = (BaseSend) o;

        this.baseControl = baseControl.toJson() ;
    }

    public T getBaseControl() {

        try {
            Class clazz = Class.forName(className) ;
            return (T) GSON.fromJson(baseControl.toString(), clazz);

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

        return null;
    }





}
