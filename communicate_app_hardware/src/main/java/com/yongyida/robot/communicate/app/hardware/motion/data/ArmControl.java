package com.yongyida.robot.communicate.app.hardware.motion.data;

import java.util.ArrayList;

/**
 * Created by HuangXiangXiang on 2018/4/23.
 * 手臂关节的控制
 */
public class ArmControl {


    /**
     * 方向
     * 左右
     * */
    public enum Direction{

        LEFT((byte)0),
        RIGHT((byte)1);

        public byte value ;
        Direction(byte value){

            this.value = value ;
        }

    }


    //
    public enum Type{

        BY((byte)0),    //偏移量
        TO((byte)1);     //目标值

        public byte value ;
        Type(byte value){

            this.value = value ;
        }
    }

    public enum Mode{

        TIME((byte)0),
        SPEED((byte)1);

        public byte value ;
        Mode(byte value){

            this.value = value ;
        }
    }

    // 方向
    private Direction direction ;

    //手臂
    private ArrayList<Joint> joints ;


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public ArrayList<Joint> getJoints() {
        return joints;
    }

    public void setJoints(ArrayList<Joint> joints) {
        this.joints = joints;
    }

    public void setJoint(Joint joint) {

        if(joints ==  null){

            this.joints = new ArrayList<>();
        }

        this.joints.add(joint) ;
    }




    /**手臂关节信息*/
    public static class Joint{

        // 从脑袋出开始数（0-5）
        public int id ;

        //是否是反向
        public boolean isNegative = false ;

        public Type type ;
        //类型的参数
        public int typeValue ;

        public Mode mode ;
        //模式的参数
        public int modeValue ;

        //延迟时间（毫秒）
        public int delay ;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isNegative() {
            return isNegative;
        }

        public byte negativeValue() {
            return (byte) (isNegative ? 1 : 0);
        }

        public void setNegative(boolean negative) {
            isNegative = negative;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Mode getMode() {
            return mode;
        }

        public void setMode(Mode mode) {
            this.mode = mode;
        }

        public int getDelay() {
            return delay;
        }

        public void setDelay(int delay) {
            this.delay = delay;
        }

        public int getTypeValue() {
            return typeValue;
        }

        public void setTypeValue(int typeValue) {
            this.typeValue = typeValue;
        }

        public int getModeValue() {
            return modeValue;
        }

        public void setModeValue(int modeValue) {
            this.modeValue = modeValue;
        }
    }



}
