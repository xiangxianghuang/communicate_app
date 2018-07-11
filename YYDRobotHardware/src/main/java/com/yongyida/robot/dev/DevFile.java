package com.yongyida.robot.dev;


/* 
                              _ooOoo_ 
                             o8888888o 
                             88" . "88 
                             (| -_- |) 
                             O\  =  /O 
                          ____/`---'\____ 
                        .'  \\|     |//  `. 
                       /  \\|||  :  |||//  \ 
                      /  _||||| -:- |||||-  \ 
                      |   | \\\  -  /// |   | 
                      | \_|  ''\---/''  |   | 
                      \  .-\__  `-`  ___/-. / 
                    ___`. .'  /--.--\  `. . __ 
                 ."" '<  `.___\_<|>_/___.'  >'"". 
                | | :  `- \`.;`\ _ /`;.`/ - ` : | | 
                \  \ `-.   \_ __\ /__ _/   .-` /  / 
           ======`-.____`-.___\_____/___.-`____.-'====== 
                              `=---=' 
           .............................................  
                    佛祖镇楼                  BUG辟易  
            佛曰:  
                    写字楼里写字间，写字间里程序员；  
                    程序人员写程序，又拿程序换酒钱。  
                    酒醒只在网上坐，酒醉还来网下眠；  
                    酒醉酒醒日复日，网上网下年复年。  
                    但愿老死电脑间，不愿鞠躬老板前；  
                    奔驰宝马贵者趣，公交自行程序员。  
                    别人笑我忒疯癫，我笑自己命太贱；  
                    不见满街漂亮妹，哪个归得程序员？ 
*/

import com.yongyida.robot.utils.LogHelper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Create By HuangXiangXiang 2018/6/6
 *
 * 操作文件Android /dev目录下的文件
 */
public class DevFile {

    private static final String TAG = DevFile.class.getSimpleName() ;

    public static final String PATH_FILE = "/dev/aw9523b" ;


    /**
     * 写入文件
     * */
    public static boolean writeString(String string){

        LogHelper.i(TAG, LogHelper.__TAG__() + " , string : " + string);

        return writeString(PATH_FILE, string) ;
    }


    /**
     * 写入文件
     * */
    public static boolean writeString(String path, String string){

        LogHelper.i(TAG, LogHelper.__TAG__() + " , path : " + path + " , string : " + string);
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(string);
            writer.flush();
            writer.close();

            return true ;
        } catch (IOException e) {

            e.printStackTrace();
            LogHelper.e(TAG, LogHelper.__TAG__() + " , e : " + e.getMessage() );
        }

        return false ;
    }

}
