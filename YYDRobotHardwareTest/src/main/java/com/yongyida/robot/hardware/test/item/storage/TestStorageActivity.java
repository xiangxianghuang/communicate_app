package com.yongyida.robot.hardware.test.item.storage;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Locale;

/**
 * Created by HuangXiangXiang on 2018/2/28.
 */
public class TestStorageActivity extends TestBaseActivity {

    private final static String PATH_ROM            = "/data" ;
    private final static String PATH_STORAGE        = "/storage" ;

    private String pathSdcard ;


    private TextView mRamTvw ;
    private TextView mRomTvw ;
    private TextView mSdcardTvw ;

    @Override
    protected View initContentView() {


        pathSdcard = getSdcardPath() ;

        View view = LayoutInflater.from(this).inflate(R.layout.activity_test_storage,null) ;

        mRamTvw = view.findViewById(R.id.ram_tvw) ;
        mRomTvw = view.findViewById(R.id.rom_tvw) ;
        mSdcardTvw = view.findViewById(R.id.sdcard_tvw) ;

        return view;
    }

    @Override
    protected String getTips() {
        return null;
    }


    private String getSdcardPath(){

        File file = new File("/storage") ;
        String[] paths = file.list();
        for (String path : paths){

            Log.e("hxx" , "path : " + path) ;
            if(!path.contains("emulated") && !path.contains("self")){

                return "/storage/" + path ;
            }
        }
        return  null ;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getRamInfo() ;
        getRomInfo() ;
        getSdcardInfo() ;
    }



    private void getRamInfo() {

        mRamTvw.setText(Formatter.formatFileSize(this, getAvailableRam(this)) + "/" +getTotalRam(this)) ;
    }

    // 获得可用的内存
    public static long getAvailableRam(Context mContext) {
        // 得到ActivityManager
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        // 创建ActivityManager.MemoryInfo对象

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);

        // 取得剩余的内存空间
        return mi.availMem ;
    }



    public static String getTotalRam(Context context){//GB
        String path = "/proc/meminfo";
        String firstLine = null;
        float totalRam = 0 ;
        try{
            FileReader fileReader = new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader,8192);
            firstLine = br.readLine().split("\\s+")[1];
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(firstLine != null){
            totalRam = (int)Math.ceil((new Float(Float.valueOf(firstLine) / (1024 * 1024)).doubleValue()));
        }

        return String.format(Locale.CHINA, "%.2f GB",totalRam) ;
    }

    private void getRomInfo() {

        mRomTvw.setText(getMemoryInfo(this, PATH_ROM));

    }
    private void getSdcardInfo() {

        mSdcardTvw.setText(getMemoryInfo(this, pathSdcard));
    }

    private String getMemoryInfo(Context context, String path){

        try{
            StatFs stat = new StatFs(path);
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            long totalBlocks = stat.getBlockCountLong();

            return  Formatter.formatFileSize(context, blockSize * availableBlocks) + "/" +
                    Formatter.formatFileSize(context, blockSize * totalBlocks);
        }catch (Exception e){

            return context.getString(R.string.storage_none_info);
        }

    }




}
