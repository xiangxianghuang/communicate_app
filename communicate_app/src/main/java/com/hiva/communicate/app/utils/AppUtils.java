package com.hiva.communicate.app.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */

public class AppUtils {

    public static final String TAG = AppUtils.class.getSimpleName() ;

    /**
     *
     * 通过Action 获取,service package
     * */
    public static HashSet<String> getPackageNameByServiceAction(Context context, String action){

        HashSet<String> packageNames = new HashSet<>() ;

        PackageManager pm = context.getPackageManager();
        Intent robotService = new Intent(action) ;
        List<ResolveInfo> ris =  pm.queryIntentServices(robotService, 0) ;
        final int size = ris.size() ;
        for (int i = 0 ; i < size ; i++){

            ResolveInfo ri = ris.get(i) ;
            packageNames.add(ri.serviceInfo.packageName) ;

            LogHelper.i(TAG, action + " -> " + ri.serviceInfo.packageName) ;
        }
        return packageNames ;
    }




//    /**
//     *
//     * 通过Action 获取,service package
//     * */
//    public static HashMap<String,Receiver> getOutputClientByServiceAction(Context context , String action){
//
//        HashMap<String,Receiver> outputClients = new HashMap<>() ;
//
//        PackageManager pm = context.getPackageManager();
//        Intent robotService = new Intent(action) ;
//        List<ResolveInfo> ris =  pm.queryIntentServices(robotService, 0) ;
//        final int size = ris.size() ;
//        for (int i = 0 ; i < size ; i++){
//
////            ResolveInfo ri = ris.get(i) ;
////            packageNames.add(ri.serviceInfo.packageName) ;
//
//
//            String packageName = ris.get(i).serviceInfo.packageName ;
//            Receiver receiver = new Receiver(context, packageName, action) ;
//            outputClients.put(packageName, receiver) ;
//
//        }
//        return outputClients ;
//    }


}
