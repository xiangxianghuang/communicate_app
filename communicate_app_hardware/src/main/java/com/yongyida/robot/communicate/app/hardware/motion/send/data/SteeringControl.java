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

/**
 * Create By HuangXiangXiang 2018/6/11
 * 单个舵机 控制
 *
 *
 */
public class SteeringControl extends BaseMotionSendControl {

    /**
     * 舵机位置
     * */
    public enum Position{

        HEAD_LEFT_RIGHT,
        HEAD_UP_DOWN,
        ARM_LEFT_0,
        ARM_LEFT_1,
        ARM_LEFT_2,
        ARM_LEFT_3,
        ARM_LEFT_4,
        ARM_LEFT_5,
        ARM_RIGHT_0,
        ARM_RIGHT_1,
        ARM_RIGHT_2,
        ARM_RIGHT_3,
        ARM_RIGHT_4,
        ARM_RIGHT_5,
        FINGER_LEFT_0,
        FINGER_LEFT_1,
        FINGER_LEFT_2,
        FINGER_LEFT_3,
        FINGER_LEFT_4,
        FINGER_RIGHT_0,
        FINGER_RIGHT_1,
        FINGER_RIGHT_2,
        FINGER_RIGHT_3,
        FINGER_RIGHT_4,
        FOOT_LEFT,
        FOOT_RIGHT,

    }

    /**
     * 运行方式
     * */
    public enum Mode{

        STOP(0),                // 停止
        RESET(4),               // 归中
        LOOP(3),                // 循环
        DISTANCE_TIME(1) ,      // 距离 时间
        DISTANCE_SPEED(2) ,     // 距离 速度
        TIME_SPEED ;            // 时间 速度

        public int value ;
        Mode(){

        }
        Mode(int value){

            this.value = value ;
        }

    }

    /**
     * 距离
     * */
    public static class Distance{

        public static Distance valueOf(int value) {

            Distance distance = new Distance();
            distance.setValue(value);

            return distance;
        }

        /**
         * 单位
         * */
        public enum Unit{

            PERCENT,        // 百分比
            MM,             // 毫米
            CM,             // 厘米
            ANGLE,          // 角度值
        }

        /**运动类型*/
        public enum Type {

            BY(0),        //偏移量
            TO(1);        //目标值

            public int value ;
            Type(int value){

                this.value = value ;
            }
        }

        private Unit unit = Unit.CM ;

        private Type type ;

        private int value ;

        public Unit getUnit() {
            return unit;
        }

        public void setUnit(Unit unit) {

            this.unit = unit;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    /**
     * 时间
     * */
    public static class Time{

        public static Time valueOf(int value){

            Time time = new Time() ;
            time.setValue(value);

            return time ;
        }



        /**
         * 单位
         * */
        public enum Unit{

            MILLI_SECOND,               // 毫秒
            SECOND,                     // 秒
        }


        private Unit unit = Unit.MILLI_SECOND ;

        private int value ; // 时间单位（毫秒）

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Unit getUnit() {
            return unit;
        }

        public void setUnit(Unit unit) {
            this.unit = unit;
        }
    }

    /**
     * 速度 时间范围1-100
     *
     * */
    public static class Speed{

        public static Speed valueOf(int value) {

            Speed speed = new Speed();
            speed.setValue(value);

            return speed;
        }

        /**
         * 单位
         * */
        public enum Unit{

            ORIGINAL,               // 真实数值
            PERCENT,                // 百分比
            ACCORDING_DISTANCE      // 根据距离的单位，如果单位是角度，实际是每秒一度；如果单位是cm ，实际是每秒1cm
        }


        private int value ;

        private Unit unit = Unit.PERCENT ;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }


        public Unit getUnit() {
            return unit;
        }

        public void setUnit(Unit unit) {
            this.unit = unit;
        }
    }

    private Position position ;

    private Mode mode  ;

    private Distance distance = new Distance();

    private Time time = new Time() ;

    private Speed speed = new Speed() ;

    //是否 反向转
    private boolean isNegative = false ;


    //延迟时间（毫秒）
    private int delay ;


    public SteeringControl(Position position){

        this.position = position ;
    }


    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Distance getDistance() {
        return distance;
    }

    public Time getTime() {
        return time;
    }

    public Speed getSpeed() {
        return speed;
    }

    public boolean isNegative() {
        return isNegative;
    }

    public void setNegative(boolean negative) {
        isNegative = negative;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }


    /**
     * 在规定时间内走到指定位置
     * */
    public void startToDistanceInTimeMode(){

        this.mode = Mode.DISTANCE_TIME ;
        this.distance.type = Distance.Type.TO ;

    }

    /**
     *
     * */
    public void startToDistanceInTimeValue(int time, int distance){

        this.time.value = time ;
        this.distance.value = distance ;
    }

}
