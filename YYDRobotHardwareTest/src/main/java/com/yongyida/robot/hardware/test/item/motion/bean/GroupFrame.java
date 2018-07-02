package com.yongyida.robot.hardware.test.item.motion.bean;


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

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Create By HuangXiangXiang 2018/6/11
 * 一组动作
 */
public class GroupFrame {

    public int id = -1;
    public String name = "新的动作组";

    private ArrayList<OneFrameScript> frameScripts = new ArrayList<>() ;


    public ArrayList<OneFrameScript> getFrameScripts() {
        return frameScripts;
    }

    public void setFrameScripts(ArrayList<OneFrameScript> frameScripts) {
        this.frameScripts = frameScripts;
    }

    public void addOneFrameScript(OneFrameScript oneFrameScript) {

        frameScripts.add(oneFrameScript) ;
    }

    public void setGroupFrame(GroupFrame groupFrame) {

//        this.id = groupFrame.id ;
        this.name = groupFrame.name ;
        this.frameScripts = groupFrame.frameScripts  ;

    }

    public GroupFrame deepClone() {

        Gson gson = new Gson() ;
        String json = gson.toJson(this) ;
        return  gson.fromJson(json,GroupFrame.class) ;
    }

    public void delete(ArrayList<OneFrameScript> selectOneFrameScripts) {

        int size = selectOneFrameScripts.size() ;
        for(int i = 0 ; i < size ; i ++ ){
            OneFrameScript oneFrameScript = selectOneFrameScripts.get(i) ;
            frameScripts.remove(oneFrameScript) ;
        }
    }

    public void copy(ArrayList<OneFrameScript> selectOneFrameScripts) {

        int size = selectOneFrameScripts.size() ;
        for(int i = 0 ; i < size ; i ++ ){
            OneFrameScript oneFrameScript = selectOneFrameScripts.get(i) ;
            frameScripts.add(oneFrameScript.deepClone()) ;
        }

    }
}
