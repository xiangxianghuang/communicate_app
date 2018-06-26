package com.yongyida.robot.communicate.app.hardware.motion.response.data;


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

import com.yongyida.robot.communicate.app.common.response.BaseResponseControl;
import com.yongyida.robot.communicate.app.utils.LogHelper;

/**
 * Create By HuangXiangXiang 2018/6/22
 * 超声波信息
 */
public class Ultrasonic extends BaseResponseControl {
    private static final String TAG = Ultrasonic.class.getSimpleName() ;

    private int[] distances ;

    public int[] getDistances() {
        return distances;
    }

    public boolean setDistances(int[] distances) {

        boolean isChange = false ;

        if(this.distances == null){

            if(distances != null){

                isChange = true ;
                this.distances = distances.clone();
            }
        }else {
            if(distances == null){

                isChange = true ;
                this.distances = null;

            }else {

                if(this.distances.length != distances.length){

                    isChange = true ;
                    this.distances = distances;

                }else {

                    final int length = distances.length ;
                    for (int i = 0; i < length ; i ++){

                        if(this.distances[i] != distances[i]){

                            isChange = true ;
                            this.distances[i] = distances[i] ;
                        }
                    }
                }
            }
        }

        return isChange ;
    }
}
