package com.yongyida.robot.communicate.app.utils;

/**
 * Created by HuangXiangXiang on 2017/12/4.
 */
public class StringUtils {


     /**
     * 比较是否相等
     * ""和null 不相等
     * */
    public static boolean isEquals(String str1, String str2){

        if(str1 == null){

            return str2 == null ;
        }

        return str1.equals(str2) ;
    }

    /**
     * 比较是否相等
     * ""和null 相等
     * */
    public static boolean isEqualsIgnoreNull(String str1, String str2){

        str1 = (str1 == null) ? "" : str1 ;
        str2 = (str2 == null) ? "" : str2 ;

        return str1.equals(str2) ;
    }


}
