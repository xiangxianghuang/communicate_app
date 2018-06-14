//package com.yongyida.robot.communicate.app.hardware.motion.send.data;
//
///**
// * Created by HuangXiangXiang on 2017/11/30.
// * 运动控制
// */
//public class  MotionSendControl extends BaseMotionSendControl {
//
//
//    /**
//     * 动作种类
//     * */
//    public enum Action{
//
//        HEAD_LEFT,              //头部向左
//        HEAD_RIGHT,             //头部向右
//        HEAD_LEFT_RIGHT_RESET,  //头部归中
//        HEAD_LEFT_RIGHT_LOOP,   //头部左右循环
//        HEAD_LEFT_RIGHT_STOP,   //头部停止运动
//        HEAD_UP,                //头部向上
//        HEAD_DOWN,              //头部向下
//        HEAD_UP_DOWN_RESET,     //头部归中
//        HEAD_UP_DOWN_LOOP,      //头部上下循环
//        HEAD_UP_DOWN_STOP,      //头部停止运动
//        FOOT_FORWARD,           //前进
//        FOOT_BACK,              //后退
//        FOOT_LEFT,              //向左
//        FOOT_RIGHT,             //向右
//        FOOT_STOP ,             //脚步停止运动
//        SOUND_LOCATION,         //声源定位
//        STOP,                   //全部电机停止
//
//    }
//
//    /**
//     * 运行方式
//     * */
//    public enum Mode{
//
//        DISTANCE_TIME ,     // 距离 时间
//        DISTANCE_SPEED ,    // 距离 速度
//        TIME_SPEED ,        // 时间 速度
//    }
//
//    /**
//     * 运行类型
//     */
//    public enum Type{
//
//        SERIAL, //串口
//        SLAM,   //深蓝
//    }
//
//    /**
//     * 距离
//     * 单位默认 厘米
//     * */
//    public static class Distance{
//
//        public static Distance valueOf(int value) {
//
//            Distance distance = new Distance();
//            distance.setValue(value);
//
//            return distance;
//        }
//
//        /**
//         * 单位
//         * */
//        public enum Unit{
//
//            MM,             // 毫米
//            CM,             // 厘米
//            PERCENT,        // 百分比
//            ANGLE,          // 角度值
//
//
//        }
//
//
//        private int value ;
//
//        private Unit unit = Unit.CM ;
//
//        public int getValue() {
//            return value;
//        }
//
//        public void setValue(int value) {
//            this.value = value;
//        }
//
//        public Unit getUnit() {
//            return unit;
//        }
//
//        public void setUnit(Unit unit) {
//            this.unit = unit;
//        }
//    }
//    /**
//     * 时间
//     * 单位默认 毫秒
//     * */
//    public static class Time{
//
//        public static Time valueOf(int value){
//
//            Time time = new Time() ;
//            time.setValue(value);
//
//            return time ;
//        }
//
//
//
//        /**
//         * 单位
//         * */
//        public enum Unit{
//
//            MILLI_SECOND,               // 毫秒
//            SECOND,                     // 秒
//        }
//
//
//        private int value ; // 时间单位（毫秒）
//
//        private Unit unit = Unit.MILLI_SECOND ;
//
//        public int getValue() {
//            return value;
//        }
//
//        public void setValue(int value) {
//            this.value = value;
//        }
//
//        public Unit getUnit() {
//            return unit;
//        }
//
//        public void setUnit(Unit unit) {
//            this.unit = unit;
//        }
//    }
//
//    /**
//     * 速度 时间范围1-100
//     *
//     * */
//    public static class Speed{
//
//        public static Speed valueOf(int value) {
//
//            Speed speed = new Speed();
//            speed.setValue(value);
//
//            return speed;
//        }
//
//        /**
//         * 单位
//         * */
//        public enum Unit{
//
//            REAL,                   // 真实数值
//            PERCENT,                // 百分比
//            ACCORDING_DISTANCE      // 根据距离的单位，如果单位是角度，实际是每秒一度；如果单位是cm ，实际是每秒1cm
//        }
//
//
//        private int value ;
//
//        private Unit unit = Unit.PERCENT ;
//
//        public int getValue() {
//            return value;
//        }
//
//        public void setValue(int value) {
//            this.value = value;
//        }
//
//
//        public Unit getUnit() {
//            return unit;
//        }
//
//        public void setUnit(Unit unit) {
//            this.unit = unit;
//        }
//    }
//
//
//
//    private Action action ;
//    /**方式*/
//    private Mode mode ;
//    /**
//     * 运行方式
//     * */
//    private Type type ;
//    private Distance distance ;
//    private Time time ;
//    private Speed speed ;
//
//    public Action getAction() {
//        return action;
//    }
//
//    public void setAction(Action action) {
//        this.action = action;
//    }
//
//    public void setDistance(Distance distance) {
//        this.distance = distance;
//    }
//
//    public void setTime(Time time) {
//        this.time = time;
//    }
//
//    public void setSpeed(Speed speed) {
//        this.speed = speed;
//    }
//
//    public Mode getMode() {
//        return mode;
//    }
//
//    public void setMode(Mode mode) {
//        this.mode = mode;
//    }
//
//    public Type getType() {
//        return type;
//    }
//
//    public void setType(Type type) {
//        this.type = type;
//    }
//
//
//    public Distance getDistance() {
//        return distance;
//    }
//
//    public Time getTime() {
//        return time;
//    }
//
//    public Speed getSpeed() {
//        return speed;
//    }
//
//}
