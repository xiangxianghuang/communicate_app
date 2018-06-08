package com.yongyida.robot.communicate.app.hardware.hand.send.data;

import com.yongyida.robot.communicate.app.hardware.motion.send.data.BaseMotionSendControl;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Direction;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Mode;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Type;

import java.util.ArrayList;

/**
 * Created by HuangXiangXiang on 2018/4/23.
 * 手指的控制
 *
 */
public class FingerSendControl extends BaseHandSendControl {

    // 方向
    private Direction direction = Direction.BOTH ;

    //手臂
    public ArrayList<Finger> fingers ;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void addFinger(Finger finger) {

        if(fingers ==  null){

            this.fingers = new ArrayList<>();
        }else{

            removeFinger(finger) ;
        }

        this.fingers.add(finger) ;
    }

    public boolean removeFinger(Finger finger){

        return removeFinger(finger.id) ;
    }

    public boolean removeFinger(int id){

        if(fingers != null){

            final int size =  fingers.size() ;
            for (int i = 0 ; i < size ; i++ ){

                Finger finger = fingers.get(i) ;

                if(finger.id == id){

                    fingers.remove(i) ;
                    return true;
                }
            }
        }

        return false ;
    }

    public ArrayList<Finger> getFingers() {
        return fingers;
    }

    public void setFingers(ArrayList<Finger> fingers) {
        this.fingers = fingers;
    }

    public static class Finger{

        // 从大拇指到小拇指的Id
        public int id ;
        //是否打开 true:打开  false:关闭
        public boolean isOpen ;

        public Type type = Type.TO;

        public Mode mode = Mode.SPEED;

        public int typeValue = 0;

        public int modeValue = 10 ;

        //延迟时间（毫秒）
        public int delay ;

        public Finger() {
        }

        public Finger(int id) {

            setId(id);
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean open) {
            isOpen = open;
        }

        public byte openValue(){

            return (byte) (isOpen ? 0 : 1);
        }

        public Mode getMode() {
            return mode;
        }

        public void setMode(Mode mode) {
            this.mode = mode;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public int getModeValue() {
            return modeValue;
        }

        public void setModeValue(int modeValue) {
            this.modeValue = modeValue;
        }

        public int getTypeValue() {
            return typeValue;
        }

        public void setTypeValue(int typeValue) {
            this.typeValue = typeValue;
        }

        public int getDelay() {
            return delay;
        }

        public void setDelay(int delay) {
            this.delay = delay;
        }
    }


}
