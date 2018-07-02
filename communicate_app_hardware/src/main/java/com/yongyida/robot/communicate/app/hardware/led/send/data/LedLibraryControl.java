package com.yongyida.robot.communicate.app.hardware.led.send.data;


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

/**
 * Create By HuangXiangXiang 2018/6/9
 * Led灯库
 *
 */
public class LedLibraryControl extends BaseLedSendControl{


    /**状态*/
    public enum State{

        POWER_ON,                           // 开机
        BASIC,                              // 基本无任务
        STAND_BY,                           // 待机
        NET_CONNECTING,                     // 网络连接中
        NET_CONNECT_SUCCESS,                // 网络连接成功
        NET_CONNECT_FAIL,                   // 网络连接失败
        POWER_OFF,                          // 关机
        BATTERY_NORMAL_LESS_20,             // 待机低于20
        BATTERY_NORMAL_LESS_10,             // 待机低于10
        BATTERY_CHARGING_EQUAL_100,         // 充电100
        BATTERY_CHARGING_MORE_20,           // 充电高于20
        BATTERY_CHARGING_LESS_20,           // 充电低于20
        VOICE_LISTENING,                    // 语音监听
        VOICE_LISTENING_TALK,               // 语音监听说话
        VOICE_TALK,                         // 语音说话
        ACTIVE_INTERACTION,                 // 主动交互
        FOOT_FORWARD,                       // 前进
        FOOT_STOP,                          // 停止
        FOOT_LEFT,                          // 左拐
        FOOT_RIGHT,                         // 右拐
        ADVERTISEMENT,                      // 广告
        VIDEO,                              // 视频通话
        MOVE_CONTROL,                       // 运动控制
        SURROUNDED,                         // 被包围
        FIRMWARE_UPDATE,                    // 固件升级
        DANCE                               // 跳舞
    }


    private State state ;

    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }
}
