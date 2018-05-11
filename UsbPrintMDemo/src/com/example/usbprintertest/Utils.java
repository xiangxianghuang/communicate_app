package com.example.usbprintertest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Environment;


public class Utils {

	/**
	 * 获取当前系统的语言环境
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean isZh(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		if (language.endsWith("zh"))
			return true;
		else
			return false;
	}

	/**
	 * 获取Assets子文件夹下的文件数据流数组InputStream[]
	 * 
	 * @param context
	 * @return InputStream[]
	 */
	@SuppressWarnings("unused")
	private static InputStream[] getAssetsImgaes(String imgPath, Context context) {
		String[] list = null;
		InputStream[] arryStream = null;
		try {
			list = context.getResources().getAssets().list(imgPath);
			arryStream = new InputStream[3];
			for (int i = 0; i < list.length; i++) {
				InputStream is = context.getResources().getAssets()
						.open(imgPath + File.separator + list[i]);
				arryStream[i] = is;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arryStream;
	}

	/*
	 * 未转换为十六进制字节的字符串
	 * 
	 * @param paramString
	 * 
	 * @return byte[]
	 */
	public static byte[] hexStr2Bytesnoenter(String paramString) {
		String[] paramStr = paramString.split(" ");
		byte[] arrayOfByte = new byte[paramStr.length];

		for (int j = 0; j < paramStr.length; j++) {
			arrayOfByte[j] = Integer.decode("0x" + paramStr[j]).byteValue();
		}
		return arrayOfByte;
	}

	/**
	 * 统计指定字符串中某个符号出现的次数
	 * 
	 * @param str
	 * @return int
	 */
	public static int Count(String strData, String str) {
		int iBmpNum = 0;
		for (int i = 0; i < strData.length(); i++) {
			String getS = strData.substring(i, i + 1);
			if (getS.equals(str)) {
				iBmpNum++;
			}
		}
		//System.out.println(str + "出现了:" + iBmpNum + "次");
		return iBmpNum;
	}
	
	/**
	 * 字符串转换为16进制
	 * 
	 * @param strPart
	 * @return
	 */
	@SuppressLint({ "UseValueOf", "DefaultLocale" }) 
	public static String stringTo16Hex(String strPart) {
		if (strPart == "")
			return "";
		try {
			byte[] b = strPart.getBytes("gbk"); // 数组指定编码格式，解决中英文乱码
			String str = "";
			for (int i = 0; i < b.length; i++) {
				Integer I = new Integer(b[i]);
				@SuppressWarnings("static-access")
				String strTmp = I.toHexString(b[i]);
				if (strTmp.length() > 2)
					strTmp = strTmp.substring(strTmp.length() - 2) + " ";
				else
					strTmp = strTmp.substring(0, strTmp.length()) + " ";
				str = str + strTmp;
			}
			return str.toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * @Title:intToHexString
	 * @Description:10进制数字转成16进制
	 * @param a 转化数据
	 * @param len 占用字节数
	 * @return String
	 */
	public static String intToHexString(int a, int len) {
		len <<= 1;
		String hexString = Integer.toHexString(a);
		int b = len - hexString.length();
		if (b > 0) {
			for (int i = 0; i < b; i++) {
				hexString = "0" + hexString;
			}
		}
		return hexString;
	}
	/**
	 * 通过选择文件获取路径
	 * @param context
	 * @param uri
	 * @return String
	 */
	public static String getPath(Context context, Uri uri) {
		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] projection = { "_data" };
			Cursor cursor = null;
			try {
				cursor = context.getContentResolver().query(uri, projection,
						null, null, null);
				int column_index = cursor.getColumnIndexOrThrow("_data");
				if (cursor.moveToFirst()) {
					return cursor.getString(column_index);
				}
			} catch (Exception e) {
				// Eat it
			}
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}
		return null;
	}
	
	// ------------------20161216 Add-----------------------
	/**
	 * 获取SD卡路径
	 * @return String
	 */
	private static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator;
	}

	/**
	 * BitmapOption 位图选项
	 * @param inSampleSize
	 * @return
	 */
	private static Options getBitmapOption(int inSampleSize) {
		System.gc();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inSampleSize = inSampleSize;
		options.inPreferredConfig = Config.ARGB_4444; // T4 二维码图片效果最佳
		return options;
	}

	/**
	 * 获取Bitmap数据
	 * 
	 * @param imgPath
	 * @return
	 */
	public static Bitmap getBitmapData(String imgPath) {
		Bitmap bm = BitmapFactory.decodeFile(imgPath, getBitmapOption(1)); // 将图片的长和宽缩小味原来的1/2
		return bm;
	}
	
	/**
	 * 获取SDCard图片路径,指定已知的路径
	 * @param fileName
	 * @return
	 */
	public static String getBitmapPath(String fileName) {
		String imgPath = getSDCardPath() + "DCIM" + File.separator + "BMP"
				+ File.separator + fileName;
		return imgPath;
	}
	
	/** 
	 * SharedPreferences存储数据方式工具类 
	 * @author zuolongsnail 
	 */
	public final static String SETTING = "masung";  
	// 移除数据
	public static void removeValue(Context context,String key) {  
    	Editor sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit(); 
    	sp.clear();  
    	sp.commit(); 
    }
	
    public static void putValue(Context context,String key, int value) {  
         Editor sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();  
         sp.putInt(key, value);  
         sp.commit();  
    }  
    public static void putValue(Context context,String key, boolean value) {  
         Editor sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();  
         sp.putBoolean(key, value);  
         sp.commit();  
    }  
    public static void putValue(Context context,String key, String value) {  
         Editor sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();  
         sp.putString(key, value);  
         sp.commit();  
    }  
    public static int getValue(Context context,String key, int defValue) {  
        SharedPreferences sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);  
        int value = sp.getInt(key, defValue);  
        return value;  
    }  
    public static boolean getValue(Context context,String key, boolean defValue) {  
        SharedPreferences sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);  
        boolean value = sp.getBoolean(key, defValue);  
        return value;  
    }  
    public static String getValue(Context context,String key, String defValue) {  
        SharedPreferences sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);  
        String value = sp.getString(key, defValue);  
        return value;  
    }  
}
