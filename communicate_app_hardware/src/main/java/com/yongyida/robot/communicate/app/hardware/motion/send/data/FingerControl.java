package com.yongyida.robot.communicate.app.hardware.motion.send.data;


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


import com.yongyida.robot.communicate.app.hardware.motion.send.data.constant.Direction;

import java.util.ArrayList;

/**
 * Create By HuangXiangXiang 2018/6/11
 * 手指控制
 */
public class FingerControl extends BaseMotionSendControl {

    /**手指运动类型*/
    public enum Action{

        CUSTOM,                 // 自定义
        STOP(0x00),             // 停止
        RESET(0x01),            // 手臂手指初始化
        MARK_FIST(0x02),        // 握拳
        GOOD(0x03),             // 点赞
        OK(0x04),               // OK
        ROCK(0x05),             // 石头
        SCISSORS(0x06),         // 剪刀
        PAPER(0x07),            // 布
        HAND_SHAKE(0x08),       // 握手
        SHOW_LOVE(0x09),        // 示爱
        FINGER_WHEEL(0x0A);     // 手指轮动

        public final int value ;
        Action(){

            this(0) ;
        }
        Action(int value){

            this.value =  value;
        }

    }

    private Direction direction = Direction.BOTH ;
    private Action action = Action.CUSTOM ;
    private ArrayList<SteeringControl> fingerLefts;
    private ArrayList<SteeringControl> fingerRights;


    public FingerControl(){

        this.fingerLefts = new ArrayList<>() ;
        SteeringControl fingerLeft0 = new SteeringControl(SteeringControl.Position.FINGER_LEFT_0) ;
        SteeringControl fingerLeft1 = new SteeringControl(SteeringControl.Position.FINGER_LEFT_1) ;
        SteeringControl fingerLeft2 = new SteeringControl(SteeringControl.Position.FINGER_LEFT_2) ;
        SteeringControl fingerLeft3 = new SteeringControl(SteeringControl.Position.FINGER_LEFT_3) ;
        SteeringControl fingerLeft4 = new SteeringControl(SteeringControl.Position.FINGER_LEFT_4) ;
        fingerLefts.add(fingerLeft0);
        fingerLefts.add(fingerLeft1);
        fingerLefts.add(fingerLeft2);
        fingerLefts.add(fingerLeft3);
        fingerLefts.add(fingerLeft4);


        this.fingerRights = new ArrayList<>() ;
        SteeringControl fingerRight0 = new SteeringControl(SteeringControl.Position.FINGER_RIGHT_0) ;
        SteeringControl fingerRight1 = new SteeringControl(SteeringControl.Position.FINGER_RIGHT_1) ;
        SteeringControl fingerRight2 = new SteeringControl(SteeringControl.Position.FINGER_RIGHT_2) ;
        SteeringControl fingerRight3 = new SteeringControl(SteeringControl.Position.FINGER_RIGHT_3) ;
        SteeringControl fingerRight4 = new SteeringControl(SteeringControl.Position.FINGER_RIGHT_4) ;
        fingerRights.add(fingerRight0);
        fingerRights.add(fingerRight1);
        fingerRights.add(fingerRight2);
        fingerRights.add(fingerRight3);
        fingerRights.add(fingerRight4);

    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {

        this.direction = direction ;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {

        this.action = action ;
    }

    public SteeringControl getFinger(SteeringControl.Position position) {

        if(fingerLefts != null){

            int size = fingerLefts.size() ;
            for (int i = 0 ; i < size ; i++){

                SteeringControl control = fingerLefts.get(i) ;
                if(position == control.getPosition()){

                    return control ;
                }
            }
        }

        if(fingerRights != null){

            int size = fingerRights.size() ;
            for (int i = 0 ; i < size ; i++){

                SteeringControl control = fingerRights.get(i) ;
                if(position == control.getPosition()){

                    return control ;
                }
            }
        }

        return null;
    }



//    private ArrayList<SteeringControl> getNeedSendControls(ArrayList<SteeringControl> steeringControls){
//
//        ArrayList<SteeringControl> controls = new ArrayList<>() ;
//
//        int size = steeringControls.size() ;
//        for (int i = 0 ; i < size ; i ++){
//
//            SteeringControl control = steeringControls.get(i) ;
//            if(control.isControl()){
//                controls.add(control) ;
//            }
//        }
//        return controls ;
//    }

}
