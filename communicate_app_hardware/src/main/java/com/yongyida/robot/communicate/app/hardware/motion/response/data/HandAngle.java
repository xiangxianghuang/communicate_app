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

/**
 * Create By HuangXiangXiang 2018/6/12
 * 手臂角度(左臂 右臂 左指 右指)
 */
public class HandAngle extends BaseResponseControl {

    public Angle leftArmAngle = new Angle(6);
    public Angle rightArmAngle = new Angle(6);
    public Angle leftFingerAngle = new Angle(5);
    public Angle rightFingerAngle = new Angle(5) ;

    public class Angle{

        public int state ;
        public int[] angles ;

        public Angle(int length){

            angles = new int[length] ;
        }

        private boolean copyValues(int[] values1 , int[] values2){

            if(values1 != null){
                if(values2 != null){

                    int length1 = values1.length ;
                    int length2 = values2.length ;
                    if(length1 != length2){

                        values1 = values2.clone();
                        return true ;

                    }else {

                        boolean isChanged = false ;
                        for (int i = 0 ; i < length1 ; i ++ ){

                            if(values1[i] != values2[i]){

                                isChanged = true ;
                                values1[i] = values2[i] ;
                            }
                        }


                        return isChanged ;

                    }

                }else {

                    values1 = null;
                    return true ;
                }

            }else {

                if(values2 != null){

                    values1 = values2.clone();
                    return true ;

                }else {

                    return false ;
                }
            }
        }

        public boolean setValues(int state, int[] angles) {

            boolean isChanged = false ;

            if(this.state != state){

                this.state = state ;
                isChanged = true ;
            }

            if(copyValues(this.angles, angles)){

                isChanged = true ;
            }

            return isChanged ;
        }

    }




}
