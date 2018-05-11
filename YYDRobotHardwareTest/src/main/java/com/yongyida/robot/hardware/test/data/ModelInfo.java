package com.yongyida.robot.hardware.test.data;

import android.os.Build;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by HuangXiangXiang on 2018/4/3.
 */
public class ModelInfo {

    private static ModelInfo mInstance ;
    public static ModelInfo getInstance(){

        if(mInstance == null){

            mInstance = new ModelInfo() ;
        }
        return mInstance ;
    }

    private final String model;      //型号
    private final String cpu;        //cpu
    private final int sdkCode;       //android版本

    private ModelInfo() {

        this.model = Build.MODEL;
        this.cpu = getCpuName();
        this.sdkCode = Build.VERSION.SDK_INT;
    }

    public String getModel() {
        return model;
    }

    public String getCpu() {
        return cpu;
    }

    public int getSdkCode() {
        return sdkCode;
    }

    public String getCpuName() {

        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);

            String text;
            while ((text = br.readLine()) != null) {

                if (text.startsWith("Hardware")) {

                    String[] texts = text.split(":");
                    if (texts.length > 1) {

                        return texts[1].trim();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getAndroidVersion(){

        String androidVersion ;

        switch (sdkCode){

            case Build.VERSION_CODES.LOLLIPOP:

                androidVersion = "5.0" ;
                break;
            case Build.VERSION_CODES.LOLLIPOP_MR1:

                androidVersion = "5.1" ;

                break;
            case Build.VERSION_CODES.M:

                androidVersion = "6.0" ;
                break;

            case Build.VERSION_CODES.N:

                androidVersion = "7.0" ;

                break;
            case Build.VERSION_CODES.N_MR1:

                androidVersion = "7.1.1" ;
                break;
            case Build.VERSION_CODES.O:

                androidVersion = "8.0" ;
                break;
            default:

                androidVersion = "V_" + sdkCode ;
                break;
        }

        return androidVersion ;
    }

    @Override
    public String toString() {

        return "机器型号 : " + model + "\ncpu类型 : " + cpu + "\nAndroid版本 : " + getAndroidVersion();
    }

}
