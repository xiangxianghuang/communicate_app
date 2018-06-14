package com.yongyida.robot.model.agreement;



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

    /**手臂(0x01)*/
    public static final byte TYPE_ARM                       = 0x01 ;
    /**呼吸灯控制命令(0x02)*/
    public static final byte TYPE_LED                       = 0x02 ;
    /**舵机控制命令(0x03)  (左右)*/
    public static final byte TYPE_HEAD_LEFT_RIGHT           = 0x03 ;
    /**舵机控制命令(0x04)  (上下)*/
    public static final byte TYPE_HEAD_UP_DOWN              = 0x04 ;
    /**手指(0x05)*/
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

        protected byte[] content ;
        protected byte[] fixedContent  ;     // 定长数据
        protected byte[] changeContent ;    // 变长数据


        public SingleChip(){

            fixedContent = setDataLength(12) ;
            content = fixedContent ;
        }

        protected byte[] setDataLength(int length){

            byte[] data = new byte[length] ;

            data[0] = getFunction();
            data[1] = getType();

            return data;
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

            return TYPE_ARM;
        }


        /**
         * 查询状态
         * @param direction 0 表示左方  1 表示右方
         * @param id 0-5 表示从上到下的具体舵机； 0xFE 表示全部
         * */
        public void queryStatue(byte direction, byte id){

            content = fixedContent ;

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

            content = fixedContent ;

            content[2] = 0x02 ;

//            content[3] = direction ;
            content[4] = (byte) srcId;
            content[5] = (byte) destId;

        }


//        /**
//         * 控制手臂单个舵机
//         * @param direction  0 左 1 右
//         *
//         * */
//        public void controlSingleArm(byte direction, ArmSendControl.Joint joint){
//
//            content[2] = 0x03 ;
//
//            content[3] = direction ;
//
//            content[4] = (byte) joint.getId();
//            content[5] = (byte) ((joint.mode.value & 0x0F) | ((joint.negativeValue() & 0x01) << 4 ) | ((joint.type.value & 0x01) << 5 )) ;
//            content[6] =  (byte) ((joint.typeValue >> 8) & 0xFF);
//            content[7] = (byte) (joint.typeValue & 0xFF) ;
//            content[8] = (byte) ((joint.modeValue >> 8) & 0xFF) ;
//            content[9] = (byte) (joint.modeValue & 0xFF);
//            content[10] = (byte) ((joint.delay >> 8) & 0xFF) ;
//            content[11] = (byte) (joint.delay & 0xFF) ;
//
//        }

//        /**
//         * 控制手臂多个舵机
//         * */
//        public void controlArms(byte direction, ArrayList<ArmSendControl.Joint> joints){
//
//            final int size = joints.size() ;
//            changeContent = setDataLength(size*8 + 5) ;
//            content = changeContent ;
//
//            content[2] = 0x03 ;
//            content[3] = direction ;
//            content[4] = (byte) size;
//
//            for (int i = 0 ; i < size ; i ++){
//
//                ArmSendControl.Joint joint = joints.get(i) ;
//
//                content[8*i+5] = (byte) joint.getId();
//                content[8*i+6] = (byte) ((joint.mode.value & 0x0F) | ((joint.negativeValue() & 0x01) << 4 ) | ((joint.type.value & 0x01) << 5 )) ;
//                content[8*i+7] = (byte) (joint.typeValue & 0xFF) ;
//                content[8*i+8] =  (byte) ((joint.typeValue >> 8) & 0xFF);
//                content[8*i+9] = (byte) (joint.modeValue & 0xFF);
//                content[8*i+10] = (byte) ((joint.modeValue >> 8) & 0xFF) ;
//                content[8*i+11] = (byte) (joint.delay & 0xFF) ;
//                content[8*i+12] = (byte) ((joint.delay >> 8) & 0xFF) ;
//
//            }
//
//        }

        /**
         * 硬件复位
         *
         * */
        public void resetHardware(){

            content = fixedContent ;

            content[2] = 0x04 ;

        }

        /**
         * 软件复位
         * */
        public void resetSoftware(){

            content = fixedContent ;

            content[2] = 0x05 ;

        }


        /**
         * 开启示教模式
         * */
        public void openTeacherMode(){

            content = fixedContent ;

            content[2] = 6 ;
            content[3] = 1 ;
        }

        /**
         * 关闭示教模式
         * */
        public void closeTeacherMode(){

            content = fixedContent ;

            content[2] = 6 ;
            content[3] = 0 ;
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


        public static final byte MODE_OFF                               = 0x00 ; // 常灭
        public static final byte MODE_ON                                = 0x01 ; // 常亮
        public static final byte MODE_BREATH                            = 0x02 ; // 呼吸
        public static final byte MODE_HORSE                             = 0x03 ; // 跑马


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
         * */
        public void setLedOffMode(){

            setMode(MODE_OFF, 0 );
        }
        /**
         * 设置灯的速度
         * */
        public void setLedOnMode(){

            setMode(MODE_ON, 0 );
        }

        /**
         * 设置灯的速度
         * */
        public void setLedBreathMode(int parameter){

            setMode(MODE_BREATH, parameter );
        }


        /**
         * 跑马灯效果
         * */
        public void setLedHorseMode(int parameter){

            setMode(MODE_HORSE, parameter );
        }

        public void setMode(byte mode, int parameter){

            content[3] = mode ;

            content[4] = (byte) (parameter & 0xFF);
            content[5] = (byte) ((parameter >> 8) & 0xFF) ;
        }


        public void setColor(byte red, byte green, byte blue){

            content[6] = red ;
            content[7] = green ;
            content[8] = blue ;
        }



    }

    /**
     * 头部运动
     * */
    public abstract static class SteerHead extends SingleChip{

        public static final byte TURN_RESET                 = 0x00; //归中
        public static final byte TURN_NEGATIVE              = 0x01; //负值（头向左、头向上）
        public static final byte TURN_POSITIVE              = 0x02; //正值（头向右、头向下）


        public static final byte TYPE_BY                    = 0x00; // 偏移量
        public static final byte TYPE_TO                    = 0x01; // 目标值


        public static final byte MODE_STOP                  = 0x00; //停止
        public static final byte MODE_TIME                  = 0x01; //时间
        public static final byte MODE_SPEED                 = 0x02; //速度
        public static final byte MODE_LOOP                  = 0x03; //循环
        public static final byte MODE_RESET                 = 0x04; //重置

        @Override
        public byte getFunction() {

            return FUNCTION_SEND_STEER;
        }

        /**
         * 归中
         * */
        public void turnReset(){

            controlHead((byte)0,TYPE_TO, MODE_RESET,0, 10,0) ;
        }


        public void turnLoop(){

            controlHead((byte)0,TYPE_TO,MODE_LOOP, 100, 10,0) ;
        }

        /**
         * 抬头/左转
         * */
        protected void turnNegative(int angle){

            controlHead((byte)1,TYPE_TO, MODE_SPEED,angle, 10,0) ;
        }

        /**
         * 低头/右转
         * */
        protected void turnPositive(int angle){

            controlHead((byte)0,TYPE_TO, MODE_SPEED,angle, 10,0) ;
        }


        /**
         *
         * */
        public void controlHead(byte isNegative, byte type,byte mode,
                                int typeValue, int modeValue, int delay){

            content[2] = (byte) ((mode & 0x0F) | ((isNegative & 0x01) << 4 ) | ((type & 0x01) << 5 )) ;
            content[3] = (byte) (typeValue & 0xFF) ;
            content[4] = (byte) ((typeValue >> 8) & 0xFF);
            content[5] = (byte) (modeValue & 0xFF);
            content[6] = (byte) ((modeValue >> 8) & 0xFF) ;
            content[7] = (byte) (delay & 0xFF) ;
            content[8] = (byte) ((delay >> 8) & 0xFF) ;

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

            int ang = (1500 + 2*angle);
            turnNegative(ang);
        }

        /**
         * 低头
         * */
        public void turnDown(int angle){

            short ang = (short) (1500 + 2*angle);
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
     * */
    public static class SteerFinger extends SingleChip{


        @Override
        public byte getType() {
            return TYPE_STEPPER_MOTOR;
        }

        @Override
        public byte getFunction() {
            return FUNCTION_SEND_STEER;
        }

//        /**
//         * 手指控制
//         *
//         * @param direction 方向  3[4-5]  0 左手  1右手
//         *
//         * */
//        public void controlFingers(byte direction, ArrayList<FingerSendControl.Finger> fingers){
//
//            final int size = fingers.size() ;
//            changeContent = setDataLength(size*8 + 5) ;
//            content = changeContent ;
//
//            content[2] = 0x00 ;
//            content[3] = direction ;
//            content[4] = (byte) size;
//
//            for (int i = 0 ; i < size ; i++){
//
//                FingerSendControl.Finger finger = fingers.get(i) ;
//
//                content[8*i+5] = (byte) finger.id ;
//                content[8*i+6] = (byte) ((finger.mode.value & 0x0F) | ((finger.openValue() & 0x01) << 4 ) | ((finger.type.value & 0x01) << 5 )) ;
//                content[8*i+7] = (byte) (finger.typeValue & 0xFF) ;
//                content[8*i+8] =  (byte) ((finger.typeValue >> 8) & 0xFF);
//                content[8*i+9] = (byte) (finger.modeValue & 0xFF);
//                content[8*i+10] = (byte) ((finger.modeValue >> 8) & 0xFF) ;
//                content[8*i+11] = (byte) (finger.delay & 0xFF) ;
//                content[8*i+12] = (byte) ((finger.delay >> 8) & 0xFF) ;
//            }
//        }
//
//
//        /**
//         * 手掌控制(属于手指的组合)
//         * */
//        public void controlPalm(){
//
//            content[2] = 0x01 ;
//        }
//
//
//
//
//
    }


    /**
     *  指定动作调用(0x07)
     * */
    public static class SteerAction extends SingleChip{

        @Override
        public byte getType() {
            return TYPE_ACTION;
        }

        @Override
        public byte getFunction() {
            return FUNCTION_SEND_STEER;
        }


//        public void setData(PreHandAction preHandAction, Direction direction ){
//
//            content[2] = preHandAction.value;
//            content[3] = direction.value;
//        }

    }

    /**
     *  示教模式(0x09)
     * */
    public static class SteerTeacher extends SingleChip{
        @Override
        public byte getType() {
            return TYPE_TEACHER;
        }

        @Override
        public byte getFunction() {
            return FUNCTION_SEND_STEER;
        }


        /**
         * 开启示教模式
         * */
        public void openTeacherMode(){

            content[2] = 1 ;
        }

        /**
         * 关闭示教模式
         * */
        public void closeTeacherMode(){

            content[2] = 0 ;
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
