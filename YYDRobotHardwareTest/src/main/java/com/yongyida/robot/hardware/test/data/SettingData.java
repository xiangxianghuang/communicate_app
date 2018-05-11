package com.yongyida.robot.hardware.test.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.hiva.communicate.app.utils.LogHelper;

/**
 * Created by HuangXiangXiang on 2018/2/7.
 */
public class SettingData {

    private static final String TAG = SettingData.class.getSimpleName() ;

    private static final String SETTING = "Setting" ;
    private static final String IS_OPEN = "isOpen" ;


    public static final int STATUS_INIT        = -1; //初始化状态
    public static final int STATUS_SUCCESS     = 0 ; //成功状态
    public static final int STATUS_FAIL        = 1 ; //失败状态


    public static boolean isOpen(Context context){

        SharedPreferences sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE) ;
        return sp.getBoolean(IS_OPEN,false) ;
    }


    public static void saveIsOpen(Context context, boolean isOpen){

        SharedPreferences sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE) ;
        SharedPreferences.Editor editor = sp.edit() ;
        editor.putBoolean(IS_OPEN, isOpen) ;
        editor.apply();
    }

    /**
     * 获取测试状态
     * */
    public static int getStatus(Context context, String name){

        SharedPreferences sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE) ;
        return sp.getInt(name,STATUS_INIT) ;
    }

    /**
     * 保存测试状态
     * */
    public static void saveStatus(Context context, String name ,int status){

        LogHelper.i(TAG, LogHelper.__TAG__() + ",context : " + context + ", name : " + name + ", status : ");

        SharedPreferences sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE) ;
        SharedPreferences.Editor editor = sp.edit() ;
        editor.putInt(name, status) ;
        editor.apply();
    }



}
