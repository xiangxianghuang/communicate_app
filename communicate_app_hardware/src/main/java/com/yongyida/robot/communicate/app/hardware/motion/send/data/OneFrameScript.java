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

import com.yongyida.robot.communicate.app.hardware.motion.response.data.HandAngle;

/**
 * Create By HuangXiangXiang 2018/6/12
 * 单独一个动作
 */
public class OneFrameScript extends BaseMotionSendControl {

    private int usedTime = 2000 ;      // 动作耗时
    private int waitTime = 1000 ;      // 动作使用时间

    private SteeringControl headLeftRight = new SteeringControl(SteeringControl.Position.HEAD_LEFT_RIGHT);
    private SteeringControl headUpDown = new SteeringControl(SteeringControl.Position.HEAD_UP_DOWN);
    private HandControl handControl = new HandControl() ;
    private FootControl footControl = new FootControl() ;


    public OneFrameScript(){

        headLeftRight.startToDistanceInTimeMode();

    }


    public int getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(int usedTime) {
        this.usedTime = usedTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public SteeringControl getHeadLeftRight() {
        return headLeftRight;
    }

    public void setHeadLeftRight(SteeringControl headLeftRight) {
        this.headLeftRight = headLeftRight;
    }

    public SteeringControl getHeadUpDown() {
        return headUpDown;
    }

    public void setHeadUpDown(SteeringControl headUpDown) {
        this.headUpDown = headUpDown;
    }

    public HandControl getHandControl() {
        return handControl;
    }

    public void setHandControl(HandControl handControl) {
        this.handControl = handControl;
    }

    public FootControl getFootControl() {
        return footControl;
    }

    public void setFootControl(FootControl footControl) {
        this.footControl = footControl;
    }


    public void setHandAngle(HandAngle handAngle) {

    }


    public void setRecordArmAngle(OneFrameScript recordArmAngle) {

        waitTime = recordArmAngle.waitTime ;
        usedTime = recordArmAngle.usedTime ;
        headLeftRight = recordArmAngle.headLeftRight ;
        headUpDown = recordArmAngle.headUpDown ;
        handControl = recordArmAngle.handControl ;
        footControl = recordArmAngle.footControl ;

    }

    public OneFrameScript deepClone(){

        String json = GSON.toJson(this) ;

        return GSON.fromJson(json, OneFrameScript.class) ;
    }

}
