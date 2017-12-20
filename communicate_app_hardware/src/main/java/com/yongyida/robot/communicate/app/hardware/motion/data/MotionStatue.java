package com.yongyida.robot.communicate.app.hardware.motion.data;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 * 具体的动作可以同时发送多个
 */
public class MotionStatue {

    /**
     * 位置
     * */
    public enum Position{

        HEAD_LEFT_RIGHT ,           //头部左右
        HEAD_UP_DOWN ,              //头部上下
        NECK ,                      //脖子
        BODY,                       //身体
        LEFT_FOOT ,                 //左脚脚
        RIGHT_FOOT ,                //右脚脚

    }
    /**
     * 方式
     * */
    public enum Type{

        DISTANCE_TIME ,     // 距离 时间
        DISTANCE_SPEED ,    // 距离 速度
        TIME_SPEED ,        // 时间 速度
    }
    /**
     * 距离
     * */
    public static class Distance{

        private int value ;

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

        private int value ;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
    /**
     * 速度 时间范围1-100
     * */
    public static class Speed{

        private int value ;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }


    /**位置*/
    private Position position = Position.HEAD_LEFT_RIGHT ;
    /**方式*/
    private Type type = Type.DISTANCE_TIME;
    private Distance distance ;
    private Time time ;
    private Speed speed ;


    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public Type getType() {
        return type;
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

}
