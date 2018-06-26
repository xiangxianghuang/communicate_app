package com.robot.communicate.app.common;

import android.content.Context;

import com.google.gson.Gson;

/**
 * Created by Huangxiangxiang on 2017/6/26.
 * 传输数据的容器，
 * 将对象转换成JSON字符串使用
 */
public class Container {

    private  static final String TAG = Container.class.getSimpleName() ;
    private final static Gson GSON = new Gson() ;

    // 数据来源
    private String packageName ;

    private String className ;
    private Object data ;

    public static Container fromJson(String json){

        return GSON.fromJson(json, Container.class) ;
    }

    public Container(Context context, Object object){

        this.packageName = (context == null) ? null : context.getPackageName() ;

        this.className = object.getClass().getName() ;
//        this.data = object ;
        this.data = toJson(object) ;
    }

    public String getClassName() {

        return className;
    }

    public String getPackageName() {
        return packageName;
    }

    public Object getData() {
        return data;
    }

    public <T>T getData(Class<T> classOfT){

        return fromJson(data.toString(), classOfT) ;
    }

    @Override
    public String toString() {

        return toJson() ;
    }

    public String toJson() {

        return GSON.toJson(this);
    }




    public static String toJson(Object object) {

        return GSON.toJson(object);
    }

    public static <T>T fromJson(String json ,Class<T> classOfT){

        return GSON.fromJson(json, classOfT) ;
    }


}
