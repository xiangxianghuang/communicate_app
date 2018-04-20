package com.yongyida.robot.control.model;

import android.os.Build;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by HuangXiangXiang on 2017/12/20.
 *
 *
 *
 */
public class ModelInfo {

    private static ModelInfo mInstance ;
    public static ModelInfo getInstance(){

        if(mInstance == null){

            mInstance = new ModelInfo() ;
        }
        return mInstance ;
    }

    private final String model ;      //型号
    private final String cpu ;        //cpu
    private final int sdkCode ;       //android版本

    private ModelInfo(){

        this.model = Build.MODEL ;
        this.cpu =getCpuName() ;
        this.sdkCode = Build.VERSION.SDK_INT ;
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

    private String getCpuName(){

        try{
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);

            String text;
            while((text = br.readLine())!= null){

                if(text.startsWith("Hardware")){

                    String [] texts = text.split(":") ;
                    if(texts.length > 1){

                        return texts[1].trim() ;
                    }
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {

        return "model : " + model + ", cpu : " + cpu + ", sdkCode : " + sdkCode ;
    }
}
