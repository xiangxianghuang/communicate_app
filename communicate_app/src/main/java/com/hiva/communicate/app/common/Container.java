package com.hiva.communicate.app.common;

import android.content.Context;

import com.google.gson.Gson;

/**
 * Created by Huangxiangxiang on 2017/6/26.
 * 传输数据的容器，
 * 将对象转换成JSON字符串使用
 */
public class Container {

    private  static final String TAG = Container.class.getSimpleName() ;

    // 数据来源
    private String packageName ;

    private String className ;
    private Object data ;

    private boolean isNeedJudge = false ;

    private String confirmClassName ;

    public static Container fromJson(String json){

        return new Gson().fromJson(json, Container.class) ;
    }


    public Container(Context context, Object object){

        this.packageName = (context == null) ? null : context.getPackageName() ;

        this.className = object.getClass().getName() ;
        this.data = object ;
    }

    public String getClassName() {

        return className;
    }

    public String getPackageName() {
        return packageName;
    }

    public boolean isNeedJudge() {
        return isNeedJudge;
    }

    /**
     * 是否需要预判
     * */
    public void setNeedJudge(boolean needJudge) {
        isNeedJudge = needJudge;
    }

    public Object getData() {
        return data;
    }

    public void setConfirmClassName(String confirmClassName){

        this.confirmClassName = confirmClassName ;
    }

    public String getConfirmClassName() {
        return confirmClassName;
    }

    public <T>T getData(Class<T> classOfT){

        return fromJson(data.toString(), classOfT) ;
    }

    @Override
    public String toString() {

        return toJson() ;
    }

    public String toJson() {

        return new Gson().toJson(this);
    }

    private static String toJson(Object object) {

        return new Gson().toJson(object);
    }

    private static <T>T fromJson(String json ,Class<T> classOfT){

        return new Gson().fromJson(json, classOfT) ;
    }


}
