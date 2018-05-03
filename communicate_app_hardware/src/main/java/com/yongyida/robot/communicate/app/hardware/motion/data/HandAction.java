package com.yongyida.robot.communicate.app.hardware.motion.data;

/**
 * Created by HuangXiangXiang on 2018/4/20.
 * 手势的动作
 *
 */
public class HandAction {

    private int type ;
    private int value ;

    public int getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public enum Position{

        RESET_ARM(0x01),
        RESET_PALM(0x02);

        private int data;
        Position(int data){

            this.data = data;
        }

    }

    public enum Direction{

        LEFT(0x00) ,
        RIGHT(0x01) ,
        ALL(0x02) ;

        private int data ;

        Direction(int data){

            this.data = data ;
        }

    }

    public enum Gesture{

        GESTURE_HAND_SHAKE(0x04),
        GESTURE_OK(0x05),
        GESTURE_GOOD(0x06),
        GESTURE_DANCE(0x07),
        GESTURE_ROCK(0x08),
        GESTURE_SCISSORS(0x09),
        GESTURE_PAPER(0x0A);

        private int data;
        Gesture(int data){

            this.data = data;
        }

    }


    public void reset(Position position, Direction direction){

        this.type = position.data ;
        this.value = direction.data ;
    }

    /**
     *  手指轮动
     * */
    public void fingerWheel(byte times){

        this.type = 0x03 ;
        this.value = times ;
    }


    /**
     * 设置手势
     * */
    public void setGesture(Gesture gesture){

        this.type = gesture.data ;
        this.value = 0x00 ;
    }


}
