package com.yongyida.robot.model.agreement;

import com.yongyida.robot.communicate.app.hardware.motion.data.ArmControl;

import java.util.ArrayList;

/**
 * Y138底层控制板与上位机串口通讯协议(Agreement)
 *
 *      0       Head_0              0xAA
 *      1       Head_1              0x55
 *      2       Length              Command+Data = N+1
 *      3       Command             功能码
 *      N       Data                数据
 *      N+4     Check               校验（Length、Command、Data）
 *
 *
 *  Version 1.0.0 （2018.04.18）(HuJun & Huangxiangxiang)
 */
public class Y138Steering {

    /**总线舵机(0x01)*/
    public static final byte TYPE_BUS                       = 0x01 ;
    /**呼吸灯控制命令(0x02)*/
    public static final byte TYPE_LED                       = 0x02 ;
    /**舵机控制命令(0x03)  (上下)*/
    public static final byte TYPE_HEAD_UP_DOWN              = 0x03 ;
    /**舵机控制命令(0x04)  (左右)*/
    public static final byte TYPE_HEAD_LEFT_RIGHT           = 0x04 ;
    /**步进电机控制命令(0x05)*/
    public static final byte TYPE_STEPPER_MOTOR             = 0x05 ;
    /**触摸控制命令(0x06)*/
    public static final byte TYPE_TOUCH                     = 0x06 ;
    /**动作调用(0x07)*/
    public static final byte TYPE_ACTION                    = 0x07 ;
    /**系统消息(0x08)*/
    public static final byte TYPE_SYSTEM                    = 0x08 ;
    /**示教模式(0x09))*/
    public static final byte TYPE_TEACHER                   = 0x09 ;



    public static final byte FUNCTION_SEND_FOOT_SPEED       = 0x60 ;        //底盘运动速度参数设置命令(0x60)
    public static final byte FUNCTION_SEND_FOOT_MOVE        = 0x61;         //底盘运动命令(0x61)
    public static final byte FUNCTION_SEND_FOOT_CORNER      = 0x62;         //底盘执行角度转向命令(0x62)
    public static final byte FUNCTION_SEND_ULTRASONIC       = 0x67;         //对超声波数据控制指令（0x67）
    public static final byte FUNCTION_SEND_READ_FOOT_STATE  = 0x68;         //对底盘驱动器状态获取控制（0x68）
    public static final byte FUNCTION_SEND_STEER            = (byte) 0xdd;  //对舵机板控制控制（0xdd）


    public static final byte FUNCTION_RECEIVE_TOUCH         = (byte) 0xdd;  //向上位机发送触摸信息（0xdd）
    public static final byte FUNCTION_RECEIVE_FAULT         = 0x20 ;        //向上位机上传故障码（0x20）
    public static final byte FUNCTION_RECEIVE_ULTRASONIC    = 0x23 ;        //超声波数据包上传（0x23）
    public static final byte FUNCTION_RECEIVE_OBD_DATA      = 0x28 ;        //OBD数据包（0x28）
    public static final byte FUNCTION_RECEIVE_SYSTEM_STATE  = 0x30 ;        //系统状态帧（0x30）



    public static final byte HEAD_0                         = (byte) 0xAA;
    public static final byte HEAD_1                         = (byte) 0X55;


    public static byte[] getCmd(SingleChip singleChip){

        return getCmd(singleChip.getContent()) ;
    }

    private static final int BASIC_LENGTH = 4;
    //
    private static byte[] getCmd(byte[] content){

        int dataLength = content.length ;
        byte[] cmd = new byte[BASIC_LENGTH + dataLength] ;

        cmd[0] = HEAD_0 ;
        cmd[1] = HEAD_1 ;
        cmd[2] = (byte) (dataLength);
        System.arraycopy(content, 0, cmd, 3, dataLength) ;
        cmd[cmd.length-1] = getCheck(cmd) ;

        return cmd ;
    }


    public static byte getCheck(byte[] data){

        byte check = 0 ;
        final int length = data.length - 1 ;
        for (int i = 2 ; i < length ; i++){

            check ^= data[i] ;
        }

        return check;
    }

    /*****************************以下是具体的的类型***********************************************/

    public static abstract class SingleChip{

        protected final byte[] content ;

        public SingleChip(){

            this(12) ;
        }
        public SingleChip(int length){

            content = new byte[length] ;

            content[0] = getFunction();
            content[1] = getType();
        }

        public abstract byte getType();     // 类型码

        public abstract byte getFunction();  // 功能码

        public byte[] getContent() {

            return content ;
        }
    }


    /**
     * 总线(0x01)
     *  手臂上面的轴
     * （待写）
     *
     * */
    public static class SteerArm extends SingleChip{



        @Override
        public byte getFunction() {

            return FUNCTION_SEND_STEER;
        }
        @Override
        public byte getType() {

            return TYPE_BUS;
        }


        /**
         * 查询状态
         * @param direction 0 表示左方  1 表示右方
         * @param id 0-5 表示从上到下的具体舵机； 0xFE 表示全部
         * */
        public void queryStatue(byte direction, byte id){

            content[2] = 0x01 ;

            content[3] = direction ;
            content[4] = id ;


        }


        /**
         * 更改ID
         * @param srcId 源ID
         * @param destId 目标ID
         * */
        public void changeId( int srcId , int destId){

            content[2] = 0x02 ;

//            content[3] = direction ;
            content[4] = (byte) srcId;
            content[5] = (byte) destId;

        }


        /**
         * 控制手臂单个舵机
         * @param direction  0 左 1 右
         * @param id 舵机Id  舵机从上到下 的舵机ID
         * @param mode  0x00 时间控制  0x01 速度控制
         * @param negative 0 正向 1 反向
         * @param type  0 偏移量 1 目标值
         * @param distance  距离值
         * @param parameter 控制参数
         * @param delay 延迟时间
         *
         * */
        public void controlSingleArm(byte direction, ArmControl.Joint joint){

            content[2] = 0x03 ;

            content[3] = direction ;

            content[4] = (byte) joint.getId();
            content[5] = (byte) ((joint.mode.value & 0x0F) | ((joint.negativeValue() & 0x01) << 4 ) | ((joint.type.value & 0x01) << 5 )) ;
            content[6] =  (byte) ((joint.typeValue >> 8) & 0xFF);
            content[7] = (byte) (joint.typeValue & 0xFF) ;
            content[8] = (byte) ((joint.modeValue >> 8) & 0xFF) ;
            content[9] = (byte) (joint.modeValue & 0xFF);
            content[10] = (byte) ((joint.delay >> 8) & 0xFF) ;
            content[11] = (byte) (joint.delay & 0xFF) ;

        }

        /**
         * 硬件复位
         *
         * */
        public void resetHardware(){

            content[2] = 0x04 ;

        }

        /**
         * 软件复位
         * */
        public void resetSoftware(){

            content[2] = 0x05 ;

        }

        /**
         * 控制手臂多个舵机
         * */
        public void controlMultipleArm(byte direction, ArrayList<ArmControl.Joint> joints){

            content[2] = 0x05 ;

            content[3] = direction ;
            int length = joints.size() ;
            content[4] = (byte) length;
            for (int i = 0 ; i < length ; i ++){

                ArmControl.Joint joint = joints.get(i) ;

                content[8*i+5] = (byte) joint.getId();
                content[8*i+6] = (byte) ((joint.mode.value & 0x0F) | ((joint.negativeValue() & 0x01) << 4 ) | ((joint.type.value & 0x01) << 5 )) ;
                content[8*i+7] =  (byte) ((joint.typeValue >> 8) & 0xFF);
                content[8*i+8] = (byte) (joint.typeValue & 0xFF) ;
                content[8*i+9] = (byte) ((joint.modeValue >> 8) & 0xFF) ;
                content[8*i+10] = (byte) (joint.modeValue & 0xFF);
                content[8*i+11] = (byte) ((joint.delay >> 8) & 0xFF) ;
                content[8*i+12] = (byte) (joint.delay & 0xFF) ;

            }



        }






    }

    /**
     * LED灯(0x02)
     * */
    public static class SteerLed extends SingleChip{

        public static final byte POSITION_LEFT_EAR                      = 0x01 ;
        public static final byte POSITION_RIGHT_EAR                     = 0x02 ;
        public static final byte POSITION_EAR                           = 0x03 ;

        public static final byte POSITION_LEFT_ARM                      = 0x04 ;
        public static final byte POSITION_RIGHT_ARM                     = 0x08 ;
        public static final byte POSITION_ARM                           = 0x0C ;

        public static final byte POSITION_LEFT_FOOT                     = 0x10 ;
        public static final byte POSITION_RIGHT_FOOT                    = 0x20 ;
        public static final byte POSITION_FOOT                          = 0x30 ;

        @Override
        public byte getFunction() {

            return FUNCTION_SEND_STEER;
        }

        @Override
        public byte getType() {

            return TYPE_LED;
        }




        /**设置位置*/
        public void setPosition(byte position){

            content[2] = position ;
        }

        /**
         * 设置灯的速度
         *  1 - 60 速值越小 闪烁越快
         *  0x00 常灭
         *  0xFF 常亮
         * */
        public void setSpeed(byte speed){

            content[3] = speed ;
        }

        public void setColor(byte red, byte green ,byte blue){

            content[4] = red ;
            content[5] = green ;
            content[6] = blue ;
        }

    }

    /**
     * 头部运动
     * */
    public abstract static class SteerHead extends SingleChip{

        public static final byte TURN_RESET                 = 0x00; //归中
        public static final byte TURN_NEGATIVE              = 0x01; //负值（头向左、头向上）
        public static final byte TURN_POSITIVE              = 0x02; //正值（头向右、头向下）


        public static final byte CONTROL_TYPE_STOP          = 0x00; //停止
        public static final byte CONTROL_TYPE_TIME          = 0x01; //时间
        public static final byte CONTROL_TYPE_SPEED         = 0x02; //速度
        public static final byte CONTROL_TYPE_LOOP          = 0x03; //循环

        @Override
        public byte getFunction() {

            return FUNCTION_SEND_STEER;
        }

        /**
         * 归中
         * */
        public void turnReset(){

            content[2] = TURN_RESET;
            content[3] = 0x00;
            content[4] = 0x00;
        }

        /**
         * 抬头/左转
         * */
        protected void turnNegative(int angle){

//            short ang = (short) (1500 - 2*angle);

            content[2] = TURN_NEGATIVE ;
            content[3] = (byte) (0xFF & (angle>>8));
            content[4] = (byte) (0xFF & angle) ;
        }

        /**
         * 低头/右转
         * */
        protected void turnPositive(int angle){

//            short ang = (short) (1500 - 2*angle);

            content[2] = TURN_POSITIVE ;
            content[3] = (byte) (0xFF & (angle>>8));
            content[4] = (byte) (0xFF & angle) ;
        }


        /**停止运动*/
        public void setStopMode(){

            content[5] = CONTROL_TYPE_STOP;
            content[6] = 0x00 ;
            content[7] = 0x00 ;
        }

        /**
         * 设置时间模式
         *
         * */
        public void setTimeMode(int value){

            content[5] = CONTROL_TYPE_TIME;
            content[6] = (byte) (0xFF & (value>>8)) ;
            content[7] = (byte) (0xFF & value) ;
        }


        /**
         * 设置速度模式
         * @param value 表示速度数据值取值范围 0x00 - 0x0F
         * */
        public void setSpeedMode(int value){

            content[5] = CONTROL_TYPE_SPEED;
            content[6] = 0x00 ;
            content[7] = (byte) value;
        }

        /**
         * 设备循环模式
         * @param value 数据值取值范围 0x00 - 0x0F
         * */
        public void setLoopMode(int value){

            content[5] = CONTROL_TYPE_LOOP ;
            content[6] = 0x00 ;
            content[7] = (byte) value;

        }

    }

    /**
     * 头部上下运动(0x03)
     * */
    public static class SteerHeadUpDown extends SteerHead{

        @Override
        public byte getType() {

            return TYPE_HEAD_UP_DOWN;
        }

        /**
         * 抬头
         * */
        public void turnUp(int angle){

            int ang = (1500 - 2*angle);
            turnNegative(ang);
        }

        /**
         * 低头
         * */
        public void turnDown(int angle){

            short ang = (short) (1500 - 2*angle);
            turnPositive(ang);
        }


    }

    /**
     * 头部左右运动(0x04)
     * */
    public static class SteerHeadLeftRight extends SteerHead{

        @Override
        public byte getType() {

            return TYPE_HEAD_LEFT_RIGHT;
        }

        /**
         * @param angle 取值范围 -100, 100
         *
         * */
        public void turnLeft(int angle){

            int ang = 1500 + 10*angle;
            turnNegative(ang);
        }


        public void turnRight(int angle){

            int ang = 1500 + 10*angle ;
            turnPositive(ang);
        }

    }

    /**
     *  步进电机(0x05)
     *  控制手指
     *
     * */
    public static class SteerStepperMotor extends SingleChip{


        @Override
        public byte getType() {
            return TYPE_STEPPER_MOTOR;
        }

        @Override
        public byte getFunction() {
            return FUNCTION_SEND_STEER;
        }

        /**
         * 手指控制
         *
         * @param direction 方向  3[4-5]  0 左手  1右手
         * @param position 位置   3[0-3]  0-4 大拇指到小指
         * @param open 手势 4[4] 0 摊开 1 紧握
         * @param type 控制模式 4[5] 0 偏移量  1 目标值
         * @param mode 控制类型 4[0-3] 0 停止 1 时间模式 2 速度模式 3 循环
         * @param distance 5-6 距离
         * @param parameter 7-8 控制参数
         *
         * */
        public void controlFinger(byte direction, byte position,
                  byte open, byte type, byte mode, short distance, short parameter){

            content[2] = 0x00 ;

            content[3] = (byte) (((direction & 0x0F) << 4) | (position & 0x0F));
            content[4] = (byte) (((type & 0x01) << 5) | ((open & 0x01) << 4 )| (mode & 0x0F)) ;
            content[5] = (byte) ((distance >> 8) & 0xFF);
            content[6] = (byte) (distance & 0xFF) ;
            content[7] = (byte) ((parameter >> 8) & 0xFF) ;
            content[8] = (byte) (parameter & 0xFF) ;

        }


        /**
         * 手掌控制(属于手指的组合)
         * */
        public void controlPalm(){

            content[2] = 0x01 ;
        }





    }


    /**
     *  指定动作调用(0x07)
     * */
    public static class SteerAction extends SingleChip{

        public final static byte RESET_ARM           = 0x01 ;    // 手臂初始
        public final static byte RESET_PALM          = 0x02 ;    // 手掌初始
        public final static byte FINGER_WHEEL        = 0x03 ;    // 手指轮动
        public final static byte GESTURE_HAND_SHAKE  = 0x04 ;    // 握手
        public final static byte GESTURE_OK          = 0x05 ;    // OK
        public final static byte GESTURE_GOOD        = 0x06 ;    // 点赞
        public final static byte GESTURE_DANCE       = 0x07 ;    // 跳舞
        public final static byte GESTURE_ROCK        = 0x08 ;    // 石头
        public final static byte GESTURE_SCISSORS    = 0x09 ;    // 剪刀
        public final static byte GESTURE_PAPER       = 0x0A ;    // 布

        public final static byte DIRECTION_LEFT      = 0x00 ;   // 左
        public final static byte DIRECTION_RIGHT     = 0x01 ;   // 右
        public final static byte DIRECTION_ALL       = 0x02 ;   // 全部

        @Override
        public byte getType() {
            return TYPE_ACTION;
        }

        @Override
        public byte getFunction() {
            return FUNCTION_SEND_STEER;
        }


        public void setData(int type , int value){

            content[2] = (byte) type;
            content[3] = (byte) value;
        }

        /**
         * 恢复至初始状态
         * @param position  0x01 表示手臂 0x02 表示手掌
         * @param direction 0x00 左侧 0x01 右侧 0x02 两侧
         *
         * */
        public void reset(byte position, byte direction){

            content[2] = position ;
            content[3] = direction ;
        }

        /**
         * 手指轮动
         * @param times 次数 取值范围 0x00-0xff
         * */
        public void fingerWheel(byte times){

            content[2] = FINGER_WHEEL ;
            content[3] = times;
        }


        /**
         * 设置手势
         * @param gesture
         * @see #GESTURE_HAND_SHAKE  握手(0x04)
         * @see #GESTURE_OK  OK(0x05)
         * @see #GESTURE_GOOD  点赞(0x06)
         * @see #GESTURE_DANCE  跳舞(0x07)
         * @see #GESTURE_ROCK  石头(0x08)
         * @see #GESTURE_SCISSORS  剪刀(0x09)
         * @see #GESTURE_PAPER  布(0x0A)
         *
         * */
        public void setGesture(byte gesture){

            content[2] = gesture ;
            content[3] = 0x00;
        }


    }

    /**
     *  示教模式(0x09)
     *  (待写)
     * */
    public static class SteerTeacher extends SingleChip{
        @Override
        public byte getType() {
            return TYPE_TEACHER;
        }

        @Override
        public byte getFunction() {
            return 0;
        }
    }



    // 以下为单片机端发送到Android信息
    public static abstract class Receive extends SingleChip{

        public abstract void setData(byte[] data);
    }

    /**
     * 触摸信息（0x06）
     * （待写）
     * */
    public static class ReceiveTouch extends Receive{

        @Override
        public byte getType() {
            return TYPE_TOUCH;
        }

        @Override
        public byte getFunction() {
            return FUNCTION_RECEIVE_TOUCH;
        }


        @Override
        public void setData(byte[] data) {

        }
    }

    /**
     * 系统信息(0x08)
     * （待写）
     * */
    public static class ReceiveSystem extends Receive{

        @Override
        public byte getType() {
            return TYPE_SYSTEM;
        }

        @Override
        public byte getFunction() {
            return 0;
        }

        @Override
        public void setData(byte[] data) {

        }
    }


}
