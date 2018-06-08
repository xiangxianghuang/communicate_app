package com.yongyida.robot.communicate.app.hardware.hand.send.data.constant;


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
 * Create By HuangXiangXiang 2018/6/
 * 预设动作（手指和手臂共同作用）
 */
public enum PreHandAction {

    GESTURE_STOP(0x00),             // 停止
    GESTURE_INIT(0x01),             // 手臂手指初始化
    GESTURE_MARK_FIST(0x02),        // 握拳
    GESTURE_FINGER_WHEEL(0x03),     // 手指轮动
    GESTURE_HAND_SHAKE(0x04),       // 握手
    GESTURE_OK(0x05),               // OK
    GESTURE_GOOD(0x06),             // 点赞
    GESTURE_ROCK(0x07),             // 石头
    GESTURE_SCISSORS(0x08),         // 剪刀
    GESTURE_PAPER(0x09),            // 布
    GESTURE_SHOW_WELCOME(0x0A),     // 迎宾
    GESTURE_SHOW_WAVE(0x0B),        // 挥手
    GESTURE_SHOW_LOVE(0x0C),        // 示爱
    GESTURE_SHOW_666(0x0D),         // 666
    GESTURE_SHOW_SELF(0x0E) ;       // 自定义


    public byte value ;
    PreHandAction(int value){

        this.value = (byte) value;
    }
}
