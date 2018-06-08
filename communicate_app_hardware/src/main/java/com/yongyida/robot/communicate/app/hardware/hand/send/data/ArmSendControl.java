package com.yongyida.robot.communicate.app.hardware.hand.send.data;

import com.yongyida.robot.communicate.app.hardware.motion.send.data.BaseMotionSendControl;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Direction;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Mode;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Type;

import java.util.ArrayList;

/**
 * Created by HuangXiangXiang on 2018/4/23.
 * 手臂关节的控制
 * 控制类型有：单独控制左手 单独控制右手  同事控制左右手
 *
 */
public class ArmSendControl extends BaseHandSendControl {

    // 方向
    private Direction direction = Direction.BOTH ;

    //手臂
    public ArrayList<Joint> joints ;

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

    /***/
    public void addJoint(Joint joint) {

        if(joints ==  null){

            this.joints = new ArrayList<>();
        }else{

            removeJoint(joint) ;
        }

        this.joints.add(joint) ;
    }


    public boolean removeJoint(Joint joint){

        return removeJoint(joint.id) ;
    }

    public boolean removeJoint(int id){

        if(joints != null){

            final int size =  joints.size() ;
            for (int i = 0 ; i < size ; i++ ){

                Joint j = joints.get(i) ;

                if(j.id == id){

                    joints.remove(i) ;
                    return true;
                }
            }
        }
        return false ;
    }


    /**手臂关节信息*/
    public static class Joint{

        // 从脑袋出开始数（0-5）
        public int id ;

        //是否是反向
        public boolean isNegative = false ;

        public Type type = Type.TO ;
        //类型的参数
        public int typeValue = 2048 ;

        public Mode mode =  Mode.TIME;
        //模式的参数
        public int modeValue = 2000;

        //延迟时间（毫秒）
        public int delay ;


        public Joint(){}

        public Joint(int id){

            setId(id);
        }

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
