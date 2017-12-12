package com.yongyida.robot.model.y138.agreement.old;

import android.util.Log;

/**
 * Created by Huangxiangxiang on 2017/7/11.
 */
public class Steering {

    /**头部上下*/
    public static final byte TYPE_HEAD_UP_DONN              = 0x01 ;
    /**头部左右*/
    public static final byte TYPE_HEAD_LEFT_RIGHT           = 0x02 ;
    /**呼吸灯*/
    public static final byte TYPE_BREATH_LED                = 0x03 ;
    /**触感*/
    public static final byte TYPE_TOUCH                     = 0x04 ;
    /**行走*/
    public static final byte TYPE_MOVE                      = 0x05 ;
    /**电量计*/
    public static final byte TYPE_BATTERY                   = 0x06 ;
    /**人体感应*/
    public static final byte TYPE_HUMAN_INDUCTION           = 0x07 ;
    /**视觉信息*/
    public static final byte TYPE_VISION                    = 0x08 ;



    public static final byte HEAD_0 = (byte) 0xAA;
    public static final byte HEAD_1 = (byte) 0XBB;
    public static final byte HEAD_8 = (byte) 0x99;

    private static final byte[] BYTE_CMD = { HEAD_0,     HEAD_1,      (byte) 0x00, (byte) 0x0A, (byte) 0x00,
                                        (byte) 0x00, (byte) 0x00, (byte) 0x00, HEAD_8,      (byte) 0x00} ;


    public static byte[] getCmd(SingleChip singleChip){

        return getCmd(singleChip.getType(),singleChip.getContent()) ;
    }

    private static byte[] getCmd(byte type, byte[] content){

        //恢复默认值
        BYTE_CMD[4] = 0x00 ;
        BYTE_CMD[5] = 0x00 ;
        BYTE_CMD[6] = 0x00 ;
        BYTE_CMD[7] = 0x00 ;

        BYTE_CMD[2] = type ;
        System.arraycopy(content, 0, BYTE_CMD, 4, content.length) ;
        BYTE_CMD[9] = getCheck() ;

        return BYTE_CMD ;
    }

    private static byte getCheck(){

        int check = 0 ;

        int length = BYTE_CMD.length - 1 ;
        for (int i =0 ; i < length ; i++){

            check += (0xff&BYTE_CMD[i]) ;
        }

        check = -check ;
        return (byte) check;
    }

    /*****************************以下是据地的的类型***********************************************/

    public static abstract class SingleChip{

        public abstract byte[] getContent() ;

        public abstract byte getType() ;

    }

    /**
     * 头部马达
     * 最高为1300
     * 中间为1500
     * 最低为1700
     * */
    public static class HeadUpDown extends SingleChip{

        public static final byte RESET           = 0x00 ;
        public static final byte UP              = 0x03 ;
        public static final byte DOWN            = 0x04 ;

        public byte action ;
        public short angle  ;

        /**
         * @param angle 0 - 100度
         *              对应的1500-1300
         *
         * */
        public void setUp(int angle){

            this.action = UP ;

            this.angle = (short) (1500 - 2*angle);

            Log.e("hxx" , "setUp angle: " + this.angle ) ;
        }

        /**
         * @param angle 0 - 100度
         *              对应的1500-1700
         *
         * */
        public void setDown(int angle){

            this.action = DOWN ;

            this.angle = (short) (1500 - 2*angle);

            Log.e("hxx" , "setDown angle: " + this.angle ) ;
        }

        public void setReset(){

            this.action = RESET ;

        }

        @Override
        public byte[] getContent() {

            return new byte[]{action, (byte) (0xFF & (angle>>8)), (byte) (0xFF & angle)};
        }

        @Override
        public byte getType() {
            return TYPE_HEAD_UP_DONN ;
        }
    }


    /**左右舵机*/
    public static class HeadLeftRight extends SingleChip{

        public static final byte RESET               = 0x00 ;
        public static final byte LEFT                = 0x01 ; //500 - 1500
        public static final byte RIGHT               = 0x02 ; //1500 -2500

        public byte action ;
        public short angle  ;   // 500-2500


        /**
         * @param angle 0 - 100度
         *
         * */
        public void setLeft(int angle){

            this.action = LEFT ;

            this.angle = (short) (1500 + 10*angle);

        }

        /**
         * @param angle 0 - 100度
         * */
        public void setRight(int angle){

            this.action = RIGHT ;

            this.angle = (short) (1500 + 10*angle);
        }

        public void setReset(){

            this.action = RESET ;
            this.angle = 0 ;
        }


        @Override
        public byte[] getContent() {

            return new byte[]{action, (byte) (0xFF & (angle>>8)), (byte) (0xFF & angle)};
        }

        @Override
        public byte getType() {

            return TYPE_HEAD_LEFT_RIGHT;
        }
    }


    /**呼吸灯*/
    public static class BreathLed extends SingleChip{

        public static final byte POSITION_CHEST                         = 0x01 ;
        public static final byte POSITION_RIGHT_EAR                     = 0x02 ;
        public static final byte POSITION_LEFT_EAR                      = 0x03 ;
        /**左臂*/
        public static final byte POSITION_LEFT_ARM                      = 0x04 ;
        /**右臂*/
        public static final byte POSITION_RIGHT_ARM                     = 0x05 ;

        public static final byte COLOR_RED                              = 0x01 ; //红
        public static final byte COLOR_GREEN                            = 0x02 ; //绿
        public static final byte COLOR_BLUE                             = 0x04 ; //蓝

        public byte position ;
        public byte speed ;//0-255(速度取值范围) 值越小速度越快
        public byte color ;


        @Override
        public byte[] getContent() {
            return new byte[]{position,speed, color};
        }

        @Override
        public byte getType() {
            return TYPE_BREATH_LED;
        }

        public void setColor(byte position, byte speed, byte color) {

            this.position = position ;
            this.speed = speed ;
            this.color = color ;
        }
    }


    /**触摸点*/
    public static class Touch extends SingleChip{

        public static final byte POSITION_HEAD                       = 0x00 ;   //头
        public static final byte POSITION_BACK_HEAD                  = 0x01 ;   //后脑勺
        public static final byte POSITION_LEFT_EAR                   = 0x02 ;   //左耳机
        public static final byte POSITION_RIGHT_EAR                  = 0x03 ;   //右耳机
        public static final byte POSITION_LEFT_SHOULDER              = 0x04 ;   //左肩
        public static final byte POSITION_RIGHT_SHOULDER             = 0x08 ;   //右肩
        public static final byte POSITION_LEFT_ARM                   = 0x10 ;   //左臂
        public static final byte POSITION_RIGHT_ARM                  = 0x20 ;   //右臂

        public byte position ;

        public Touch(byte position){

            this.position = position ;
        }

        @Override
        public byte[] getContent() {
            return new byte[]{position};
        }

        @Override
        public byte getType() {
            return TYPE_TOUCH;
        }
    }


    /**控制行走*/
    public static class  Move extends SingleChip{

        // 后退
        public static final byte SPEED_BACK                               = (byte) 0xFF;
        // 停止
        public static final byte SPEED_STOP                               = 0x00 ;
        // 前进
        public static final byte SPEED_FORWARD                            = 0x0F ;

        public byte left ;
        public byte right ;

        @Override
        public byte[] getContent() {

            return new byte[]{left ,right};
        }

        @Override
        public byte getType() {
            return TYPE_MOVE;
        }

        public void setSpeed(int speed){

//            this.SPEED_BACK = (byte) -speed;
//            this.SPEED_FORWARD = (byte) speed;
        }

        public void forward() {

            this.left = SPEED_FORWARD ;
            this.right = SPEED_FORWARD ;
        }

        public void back() {
            
            this.left = SPEED_BACK ;
            this.right = SPEED_BACK ;
        }

        public void left() {
            
            this.left = SPEED_BACK ;
            this.right = SPEED_FORWARD ;
        }

        public void right() {

            this.left = SPEED_FORWARD ;
            this.right = SPEED_BACK ;
        }

        public void turnLeft() {

            this.left = SPEED_STOP ;
            this.right = SPEED_FORWARD ;
        }

        public void turnRight() {

            this.left = SPEED_FORWARD ;
            this.right = SPEED_STOP ;
        }

        public void backTurnLeft() {

            this.left = SPEED_STOP ;
            this.right = SPEED_BACK ;

        }

        public void backTurnRight() {

            this.left = SPEED_BACK ;
            this.right = SPEED_STOP ;
        }

        public void stop() {

            this.left = SPEED_STOP ;
            this.right = SPEED_STOP ;
        }
    }

    public static class Battery extends SingleChip{

        public static final byte STATUS_NO_CHARGE      = 0x00 ;
        public static final byte STATUS_CHARGE         = 0x01 ;


        public byte status ;
        public byte battery ;


        public Battery(byte status , byte battery){

            this.status = status ;
            this.battery = battery ;
        }


        @Override
        public byte[] getContent() {
            return new byte[]{status,battery};
        }

        @Override
        public byte getType() {
            return TYPE_BATTERY;
        }
    }

    /**人体感应*/
    public static class HumanInduction extends SingleChip {

        private byte distance ; //距离单位是厘米

        @Override
        public byte[] getContent() {

            return new byte[]{distance};
        }

        @Override
        public byte getType() {

            return TYPE_HUMAN_INDUCTION;
        }

    }

    public static class Vision extends SingleChip{

        public static final byte POSITION_NONE              = 0x00 ;//空数据
        public static final byte POSITION_LEFT              = 0x01 ;
        public static final byte POSITION_MIDDLE            = 0x02 ;
        public static final byte POSITION_RIGHT             = 0x03 ;
        public static final byte POSITION_START             = (byte) 0xF0;//起始
        public static final byte POSITION_STOP              = (byte) 0xFF;//结束

        private byte position ;
        private byte distance ;

        public void setPosition(byte position) {
            this.position = position;
        }

        public void setDistance(byte distance) {
            this.distance = distance;
        }

        @Override
        public byte[] getContent() {
            return new byte[]{position, distance};
        }

        @Override
        public byte getType() {

            return TYPE_VISION;
        }
    }

}
