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

import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;

/**
 * Create By HuangXiangXiang 2018/6/20
 */
public class HeadControl extends BaseMotionSendControl{

    /**动作种类*/
    public enum Action{

        CUSTOM,                 // 自定义
        LEFT_RIGHT,             // 左右运动
        UP_DOWN,                // 上下运动
        STOP,                   // 停止运动
    }

    private Action action ;
    private SteeringControl headLeftRightControl = new SteeringControl(SteeringControl.Position.HEAD_LEFT_RIGHT) ;
    private SteeringControl headUpDownControl = new SteeringControl(SteeringControl.Position.HEAD_UP_DOWN) ;


    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public SteeringControl getHeadLeftRightControl() {
        return headLeftRightControl;
    }

    public void setHeadLeftRightControl(SteeringControl headLeftRightControl) {
        this.headLeftRightControl = headLeftRightControl;
    }

    public SteeringControl getHeadUpDownControl() {
        return headUpDownControl;
    }

    public void setHeadUpDownControl(SteeringControl headUpDownControl) {
        this.headUpDownControl = headUpDownControl;
    }
//
//    @Override
//    public String toJson() {
//
//        MotionSend copySend = this.baseSend ;
//        SteeringControl copyHeadLeftRightControl = this.headLeftRightControl ;
//        SteeringControl copyHeadUpDownControl = this.headUpDownControl ;
//
//        this.baseSend = null ;
//        switch (action){
//
//            case CUSTOM:
//
//                if(!headLeftRightControl.isControl()){
//
//                    headLeftRightControl = null ;
//                }
//
//                if(!headUpDownControl.isControl()){
//
//                    headUpDownControl = null ;
//                }
//
//                break;
//            case LEFT_RIGHT:
//                headUpDownControl = null ;
//                break;
//            case UP_DOWN:
//                headLeftRightControl = null ;
//                break;
//            case STOP:
//                headUpDownControl = null ;
//                headLeftRightControl = null ;
//                break;
//        }
//
//        String json = GSON.toJson(this) ;
//
//        this.baseSend = copySend ;
//        this.headLeftRightControl = copyHeadLeftRightControl;
//        this.headUpDownControl = copyHeadUpDownControl ;
//
//        return json ;
//    }
}
