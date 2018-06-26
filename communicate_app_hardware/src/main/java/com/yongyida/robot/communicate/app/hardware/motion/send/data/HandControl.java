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


/**
 * Create By HuangXiangXiang 2018/6/11
 * 手臂手指控制
 */
public class HandControl extends BaseMotionSendControl {


    /**手指运动类型*/
    public enum Action{

        CUSTOM,           // 自定义
        RESET,            // 手臂手指初始化
        MARK_FIST,        // 握拳
        FINGER_WHEEL,     // 手指轮动
        HAND_SHAKE,       // 握手
        OK,               // OK
        GOOD,             // 点赞
        ROCK,             // 石头
        SCISSORS,         // 剪刀
        PAPER,            // 布
        SHOW_WELCOME,     // 迎宾
        SHOW_WAVE,        // 挥手
        SHOW_LOVE,        // 示爱
        SHOW_666,         // 666
        SHOW_SELF ;       // 自定义



    }

    private Direction direction  ;
    private Action action = Action.CUSTOM;

    /**手臂*/
    private ArmControl armControl = new ArmControl() ;
    /**手指*/
    private FingerControl fingerControl = new FingerControl();


    public HandControl(){

        setDirection(Direction.BOTH) ;

    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        this.armControl.setDirection(direction);
        this.fingerControl.setDirection(direction);
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public ArmControl getArmControl() {
        return armControl;
    }

    public void setArmControl(ArmControl armControl) {
        this.armControl = armControl;
    }

    public FingerControl getFingerControl() {
        return fingerControl;
    }

    public void setFingerControl(FingerControl fingerControl) {
        this.fingerControl = fingerControl;
    }


    /**
     * 在规定时间内走到指定位置
     * */
    public void startToDistanceInTimeMode(){

//        this.mode = SteeringControl.Mode.DISTANCE_TIME ;
//        this.distance.type = SteeringControl.Distance.Type.TO ;

    }

    /**
     *
     * */
    public void startToDistanceInTimeValue(int time, int distance){

//        this.time.value = time ;
//        this.distance.value = distance ;
    }
}
