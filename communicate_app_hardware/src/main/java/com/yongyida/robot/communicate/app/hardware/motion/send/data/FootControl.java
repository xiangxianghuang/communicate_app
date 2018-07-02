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
 * Create By HuangXiangXiang 2018/6/11
 * 脚行走
 *
 */
public class FootControl extends BaseMotionSendControl {

    public enum Action{

        FORWARD,           //前进
        BACK,              //后退
        LEFT,              //向左
        RIGHT,             //向右
        STOP ,             //脚步停止运动
    }


    /**运行方式*/
    public enum Type{

        SERIAL, //串口
        SLAM,   //深蓝
    }

    private Action action = Action.FORWARD ;

    private Type type ;

    private SteeringControl foot = new SteeringControl(null) ;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Type getType() {
        return type;
    }

    /***/
    public void setType(Type type) {
        this.type = type;
    }

    public SteeringControl getFoot() {
        return foot;
    }


    @Override
    public String toJson() {

        MotionSend copySend = baseSend ;
        baseSend = null ;

        String json = GSON.toJson(this) ;

        baseSend = copySend;

        return json;
    }
}
