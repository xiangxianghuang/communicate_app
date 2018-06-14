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
 * Create By HuangXiangXiang 2018/6/1
 * 灯带控制效果
 */
public class LedSendControl2 extends BaseLedSendControl {

    private Direction direction = Direction.BOTH;

    private Effect effect ;

    private byte speed = 6;

    /**
     * Create By HuangXiangXiang 2018/5/31
     * 方向位置
     *
     */
    public enum Direction {

        LEFT(0x01),
        RIGHT(0x02),
        BOTH(0x03) ;

        public byte value ;
        Direction(int value){

            this.value = (byte) value;
        }

    }

    /**灯效果*/
    public enum Effect{

        CONSTANT_WHITE(0X01) ,              //
        CONSTANT_RED(0X02) ,                //
        CONSTANT_BLUE(0X03) ,               //
        CONSTANT_YELLOW(0X04) ,             //
        CONSTANT_GREEN(0X05) ,              //
        BREATH_WHITE(0X06) ,                //
        BREATH_RED(0X07) ,                  //
        BREATH_BLUE(0X08) ,                 //
        BREATH_YELLOW(0X09) ,               //
        BREATH_GREEN(0X0A) ,                //
        RACE_BLUE(0X0B) ,                   //
        RACE_GREEN(0X0C) ,                  //
        RACE_YELLOW(0X0D) ,                 //
        RACE_RED(0X0E) ,                    //
        FLICKER_BLUE(0X0F) ,                //
        FLICKER_YELLOW(0X10) ,              //
        FLICKER_GREEN(0X11) ,               //
        FLICKER_RED(0X12) ,                 //
        CIRCLE_SEVEN_COLORS(0X13) ,         //
        TURN_OFF(0X14) ;                    //


        public byte value ;
        Effect(int value){

            this.value = (byte) value;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public void setSpeed(int speed){

        if(speed < 0x01){

            this.speed = 0x01 ;

        }else if(speed > 0x13){

            this.speed = 0x13 ;
        }else {

            this.speed = (byte) speed;
        }

    }

    public byte getSpeed(){

        return speed ;
    }

}
