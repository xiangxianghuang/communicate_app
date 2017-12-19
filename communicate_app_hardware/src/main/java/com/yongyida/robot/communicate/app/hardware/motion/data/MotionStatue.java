package com.yongyida.robot.communicate.app.hardware.motion.data;

import java.util.ArrayList;


/**
 * Created by HuangXiangXiang on 2017/11/30.
 * 具体的动作
 */
public class MotionStatue {

    private ArrayList<MotionData> motionDatas ;

    public ArrayList<MotionData> getMotionDatas() {
        return motionDatas;
    }

    public void setMotionDatas(ArrayList<MotionData> motionDatas) {
        this.motionDatas = motionDatas;
    }

    public static class MotionData{

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

            public int distance ;


        }
        /**
         * 时间
         * */
        public static class Time{

            public int time ;



        }
        /**
         * 速度 时间范围1-100
         * */
        public static class Speed{

            public int speed ;
        }


        /**位置*/
        private Position position = Position.HEAD_LEFT_RIGHT ;
        /**方式*/
        private Type type = Type.DISTANCE_TIME;
        private Distance distance ;
        private Time time ;
        private Speed speed ;



        public void setDistanceTime(Distance distance, Time time){

            this.type = Type.DISTANCE_TIME ;
            this.distance = distance ;
            this.time = time ;
        }

        public void setDistanceSpeed(Distance distance, Speed speed){

            this.type = Type.DISTANCE_SPEED ;
            this.distance = distance ;
            this.speed = speed ;
        }

        public void setDistanceTime(Time time, Speed speed){

            this.type = Type.TIME_SPEED ;
            this.time = time ;
            this.speed = speed ;
        }

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
}
