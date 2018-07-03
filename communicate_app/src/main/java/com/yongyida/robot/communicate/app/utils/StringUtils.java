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




    /**
     * 将byte数组转变成String字符串
     * */
    public static String bytes2HexString(byte[] bytes){

        if(bytes == null){

            return null ;
        }
        return bytes2HexString(bytes, bytes.length) ;
    }


    /**
     * 将byte数组转变成String字符串，选择字符长度
     * */
    public static String bytes2HexString(byte[] bytes , int length){

        if(bytes == null){

            return null ;
        }

        int len = bytes.length ;
        if(len == 0 || length <= 0){

            return "" ;
        }

        if(length > len){

            length = len ;
        }

        String format = "%02X" ;
        StringBuilder sb = new StringBuilder() ;
        for (int i= 0 ; i < length-1 ; i++){

            sb.append(String.format(format, bytes[i]) + " ") ;
        }

        sb.append(String.format(format, bytes[length - 1])) ;

        return sb.toString() ;
    }

}
