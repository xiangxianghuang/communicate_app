package com.yongyida.robot.model.agreement;

import android.util.SparseArray;

/**
 * Y128（YQ110）底层控制板与上位机串口通讯协议(Agreement)
 *
 *      0       Head_0              0xAA
 *      1       Head_1              0xDD
 *      2       Length              Command+Data = N+1
 *      3       Command             功能码
 *      N       Data                数据
 *      N+4     Check               校验（Length、Command、Data）
 *
 *
 *  Version 1.0.0 （2018.02.28）(LiangLiSheng & Huangxiangxiang)
 */
public class Y128Steering {

//    /**头部上下*/
//    public static final byte TYPE_HEAD_UP_DONN              = 0x01 ;
//    /**头部左右*/
//    public static final byte TYPE_HEAD_LEFT_RIGHT           = 0x02 ;
//    /**呼吸灯*/
//    public static final byte TYPE_BREATH_LED                = 0x03 ;
//    /**触感*/
//    public static final byte TYPE_TOUCH                     = 0x04 ;
//    /**行走*/
//    public static final byte TYPE_MOVE                      = 0x05 ;
//    /**电量计*/
//    public static final byte TYPE_BATTERY                   = 0x06 ;
//    /**人体感应*/
//    public static final byte TYPE_HUMAN_INDUCTION           = 0x07 ;
//    /**视觉信息*/
//    public static final byte TYPE_VISION                    = 0x08 ;


    public static final byte HEAD_0         = (byte) 0xAA;
    public static final byte HEAD_1         = (byte) 0X55;


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

//    private static byte getCheck(){
//
//        int check = 0 ;
//
//        int length = BYTE_CMD.length - 1 ;
//        for (int i =0 ; i < length ; i++){
//
//            check += (0xff&BYTE_CMD[i]) ;
//        }
//
//        check = -check ;
//        return (byte) check;
//    }

    public static byte getCheck(byte[] data){

        int check = 0 ;

        int length = data.length - 1 ;
        for (int i =0 ; i < length ; i++){

            check += (0xff&data[i]) ;
        }

//        check = -check ;
        return (byte) check;
    }

    /*****************************以下是据地的的类型***********************************************/

    public static abstract class SingleChip{

        protected byte[] content = new byte[12] ;

        public SingleChip(){

            content[0] = getFunction();
        }

        public abstract byte getFunction();  // 功能码

        public byte[] getContent() {

            return content ;
        }
    }

//    /**
//     * 头部马达
//     * 最高为1300
//     * 中间为1500
//     * 最低为1700
//     * */
//    public static class HeadUpDown extends SingleChip{
//
//        public static final byte RESET           = 0x00 ;
//        public static final byte UP              = 0x03 ;
//        public static final byte DOWN            = 0x04 ;
//
//        public byte action ;
//        public short angle  ;
//
//        /**
//         * @param angle 0 - 100度
//         *              对应的1500-1300
//         *
//         * */
//        public void setUp(int angle){
//
//            this.action = UP ;
//
//            this.angle = (short) (1500 - 2*angle);
//
//            Log.e("hxx" , "setUp angle: " + this.angle ) ;
//        }
//
//        /**
//         * @param angle 0 - 100度
//         *              对应的1500-1700
//         *
//         * */
//        public void setDown(int angle){
//
//            this.action = DOWN ;
//
//            this.angle = (short) (1500 - 2*angle);
//
//            Log.e("hxx" , "setDown angle: " + this.angle ) ;
//        }
//
//        public void setReset(){
//
//            this.action = RESET ;
//
//        }
//
//        @Override
//        public byte getFunction() {
//            return (byte) 0xDD;
//        }
//
//        @Override
//        public byte[] getContent() {
//
//            return new byte[]{action, (byte) (0xFF & (angle>>8)), (byte) (0xFF & angle), 0, 0, 0, 0 ,0, 0, 0};
//        }
//
//        @Override
//        public byte getType() {
//            return TYPE_HEAD_UP_DONN ;
//        }
//    }
//
//
//    /**左右舵机*/
//    public static class HeadLeftRight extends SingleChip{
//
//        public static final byte RESET               = 0x00 ;
//        public static final byte LEFT                = 0x01 ; //500 - 1500
//        public static final byte RIGHT               = 0x02 ; //1500 -2500
//
//        public byte action ;
//        public short angle  ;   // 500-2500
//
//
//        /**
//         * @param angle 0 - 100度
//         *
//         * */
//        public void setLeft(int angle){
//
//            this.action = LEFT ;
//
//            this.angle = (short) (1500 + 10*angle);
//
//        }
//
//        /**
//         * @param angle 0 - 100度
//         * */
//        public void setRight(int angle){
//
//            this.action = RIGHT ;
//
//            this.angle = (short) (1500 + 10*angle);
//        }
//
//        public void setReset(){
//
//            this.action = RESET ;
//            this.angle = 0 ;
//        }
//
//
//        @Override
//        public byte getFunction() {
//            return (byte) 0xDD;
//        }
//
//        @Override
//        public byte getType() {
//
//            return TYPE_HEAD_LEFT_RIGHT;
//        }
//
//        @Override
//        public byte[] getContent() {
//
//            return new byte[]{action, (byte) (0xFF & (angle>>8)), (byte) (0xFF & angle), 0, 0, 0, 0 ,0, 0, 0};
//        }
//
//    }
//
//
//    /**呼吸灯*/
//    public static class BreathLed extends SingleChip{
//
//        public static final byte POSITION_CHEST                         = 0x01 ;
//        public static final byte POSITION_RIGHT_EAR                     = 0x02 ;
//        public static final byte POSITION_LEFT_EAR                      = 0x03 ;
//        /**左臂*/
//        public static final byte POSITION_LEFT_ARM                      = 0x04 ;
//        /**右臂*/
//        public static final byte POSITION_RIGHT_ARM                     = 0x05 ;
//
//        public static final byte COLOR_RED                              = 0x01 ; //红
//        public static final byte COLOR_GREEN                            = 0x02 ; //绿
//        public static final byte COLOR_BLUE                             = 0x04 ; //蓝
//
//        public byte position ;
//        public byte speed ;//0-255(速度取值范围) 值越小速度越快
//        public byte color ;
//
//
//        @Override
//        public byte[] getContent() {
//            return new byte[]{position,speed, color, 0, 0, 0, 0 ,0, 0, 0};
//        }
//
//        @Override
//        public byte getFunction() {
//            return (byte) 0xDD;
//        }
//
//        @Override
//        public byte getType() {
//            return TYPE_BREATH_LED;
//        }
//
//        public void setColor(byte position, byte speed, byte color) {
//
//            this.position = position ;
//            this.speed = speed ;
//            this.color = color ;
//        }
//    }
//
//
//    /**触摸点*/
//    public static class Touch extends SingleChip{
//
//        public static final byte POSITION_HEAD                       = 0x00 ;   //头
//        public static final byte POSITION_BACK_HEAD                  = 0x01 ;   //后脑勺
//        public static final byte POSITION_LEFT_EAR                   = 0x02 ;   //左耳机
//        public static final byte POSITION_RIGHT_EAR                  = 0x03 ;   //右耳机
//        public static final byte POSITION_LEFT_SHOULDER              = 0x04 ;   //左肩
//        public static final byte POSITION_RIGHT_SHOULDER             = 0x08 ;   //右肩
//        public static final byte POSITION_LEFT_ARM                   = 0x10 ;   //左臂
//        public static final byte POSITION_RIGHT_ARM                  = 0x20 ;   //右臂
//
//        public byte position ;
//
//        public Touch(byte position){
//
//            this.position = position ;
//        }
//
//        @Override
//        public byte[] getContent() {
//            return new byte[]{position};
//        }
//
//        @Override
//        public byte getType() {
//            return TYPE_TOUCH;
//        }
//    }
//
//
//    /**控制行走*/
//    public static class  Move extends SingleChip{
//
//        // 后退
//        public static final byte SPEED_BACK                               = (byte) 0xFF;
//        // 停止
//        public static final byte SPEED_STOP                               = 0x00 ;
//        // 前进
//        public static final byte SPEED_FORWARD                            = 0x0F ;
//
//        public byte left ;
//        public byte right ;
//
//        @Override
//        public byte[] getContent() {
//
//            return new byte[]{left ,right};
//        }
//
//        @Override
//        public byte getType() {
//            return TYPE_MOVE;
//        }
//
//        public void setSpeed(int speed){
//
////            this.SPEED_BACK = (byte) -speed;
////            this.SPEED_FORWARD = (byte) speed;
//        }
//
//        public void forward() {
//
//            this.left = SPEED_FORWARD ;
//            this.right = SPEED_FORWARD ;
//        }
//
//        public void back() {
//
//            this.left = SPEED_BACK ;
//            this.right = SPEED_BACK ;
//        }
//
//        public void left() {
//
//            this.left = SPEED_BACK ;
//            this.right = SPEED_FORWARD ;
//        }
//
//        public void right() {
//
//            this.left = SPEED_FORWARD ;
//            this.right = SPEED_BACK ;
//        }
//
//        public void turnLeft() {
//
//            this.left = SPEED_STOP ;
//            this.right = SPEED_FORWARD ;
//        }
//
//        public void turnRight() {
//
//            this.left = SPEED_FORWARD ;
//            this.right = SPEED_STOP ;
//        }
//
//        public void backTurnLeft() {
//
//            this.left = SPEED_STOP ;
//            this.right = SPEED_BACK ;
//
//        }
//
//        public void backTurnRight() {
//
//            this.left = SPEED_BACK ;
//            this.right = SPEED_STOP ;
//        }
//
//        public void stop() {
//
//            this.left = SPEED_STOP ;
//            this.right = SPEED_STOP ;
//        }
//    }
//
//    public static class Battery extends SingleChip{
//
//        public static final byte STATUS_NO_CHARGE      = 0x00 ;
//        public static final byte STATUS_CHARGE         = 0x01 ;
//
//        public byte status ;
//        public byte battery ;
//
//        public Battery(byte status , byte battery){
//
//            this.status = status ;
//            this.battery = battery ;
//        }
//
//        @Override
//        public byte[] getContent() {
//            return new byte[]{status,battery};
//        }
//
//        @Override
//        public byte getType() {
//            return TYPE_BATTERY;
//        }
//    }
//
//    /**人体感应*/
//    public static class HumanInduction extends SingleChip {
//
//        private byte distance ; //距离单位是厘米
//
//        @Override
//        public byte[] getContent() {
//
//            return new byte[]{distance};
//        }
//
//        @Override
//        public byte getType() {
//
//            return TYPE_HUMAN_INDUCTION;
//        }
//
//    }
//
//    public static class Vision extends SingleChip{
//
//        public static final byte POSITION_NONE              = 0x00 ;//空数据
//        public static final byte POSITION_LEFT              = 0x01 ;
//        public static final byte POSITION_MIDDLE            = 0x02 ;
//        public static final byte POSITION_RIGHT             = 0x03 ;
//        public static final byte POSITION_START             = (byte) 0xF0;//起始
//        public static final byte POSITION_STOP              = (byte) 0xFF;//结束
//
//        private byte position ;
//        private byte distance ;
//
//        public void setPosition(byte position) {
//            this.position = position;
//        }
//
//        public void setDistance(byte distance) {
//            this.distance = distance;
//        }
//
//        @Override
//        public byte[] getContent() {
//            return new byte[]{position, distance};
//        }
//
//        @Override
//        public byte getType() {
//
//            return TYPE_VISION;
//        }
//    }

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


    /**
     * 底盘速度参数
     * */
    public static class FootSpeed extends SingleChip{



        @Override
        public byte getFunction() {
            return FUNCTION_SEND_FOOT_SPEED;
        }

        /**
         * 协同运动使能
         * @param isCoordinationEnable
         *      true  0x01 使能
         *      false 0x00 不使能
         * */
        public void setCoordinationEnable(boolean isCoordinationEnable){

            content[1] = isCoordinationEnable ? (byte)0x01 : (byte)0x00 ;
        }

        /**
         * 协同控制
         * @param isCoordinationControl
         *      true  0x01 锁轴
         *      false 0x00 松轴
         * */
        public void setCoordinationControl(boolean isCoordinationControl){

            content[2] = isCoordinationControl ? (byte)0x01 : (byte)0x00 ;
        }

        /**
         * 设置速度
         * @param speed  速度值0-2
         * */
        public void setSpeed(byte speed){

            if(speed < 0){

                content[3] = 0 ;

            }else if(speed > 2){

                content[3] = 2 ;
            }else {

                content[3] = speed ;
            }
        }



    }

    /**
     * 底盘运动
     * */
    public static class FootMove extends SingleChip{

        public static final byte MODE_SPEED         = 0x00 ;
        public static final byte MODE_DISTANACE     = 0x01 ;
        public static final byte MODE_TIME          = 0x02 ;

        public static final byte TYPE_FORWARD       = 0x00 ;
        public static final byte TYPE_BACK          = 0x01 ;
        public static final byte TYPE_LEFT          = 0x02 ;
        public static final byte TYPE_RIGHT         = 0x03 ;
        public static final byte TYPE_STOP          = 0x04 ;



        @Override
        public byte getFunction() {
            return FUNCTION_SEND_FOOT_MOVE;
        }

        /**
         * 设置控制模式
         * */
        public void setMode(byte mode){

            content[1] = mode ;
        }

        public void setSpeed(int speed){

            content[2] = (byte) (speed >> 8);
            content[3] = (byte) speed;
        }

        public void setType(byte type){

            content[4] = type ;
        }

        public void forward(){

            setType(TYPE_FORWARD) ;
        }

        public void back(){

            setType(TYPE_BACK) ;
        }
        public void left(){

            setType(TYPE_LEFT) ;
        }
        public void right(){

            setType(TYPE_RIGHT) ;
        }
        public void stop(){

            setType(TYPE_STOP) ;
        }

    }

    /**
     * 转动角度
     * */
    public static class FootCorner extends SingleChip{


        public static final byte TURN_LEFT      = 0x00;
        public static final byte TURN_RIGHT     = 0x01;


        @Override
        public byte getFunction() {
            return FUNCTION_SEND_FOOT_CORNER;
        }

        public void turnLeft(int angle){

            content[1] = TURN_LEFT ;
            content[2] = (byte) (0xFF & (angle>>8));
            content[3] = (byte) (0xFF & angle) ;
        }


        public void turnRight(int angle){

            content[1] = TURN_RIGHT ;
            content[2] = (byte) (0xFF & (angle>>8));
            content[3] = (byte) (0xFF & angle) ;
        }


        public void setSpeed(int speed){

            content[4] = (byte) (speed >> 8);
            content[5] = (byte) speed;
        }


    }

    /**
     * 超声波信息
     * */
    public static class Ultrasonic extends SingleChip{

        public static final byte MODE_NO_SEND_ANDROID       = 0x00 ;
        public static final byte MODE_START_SEND_ANDROID    = 0x01 ;
        public static final byte MODE_STOP_SEND_ANDROID     = 0x02 ;

        public static final byte MODE_SEND_SLAMWARE         = 0x00 ;
        public static final byte MODE_START_SEND_SLAMWARE    = 0x01 ;
        public static final byte MODE_STOP_SEND_SLAMWARE     = 0x02 ;


        @Override
        public byte getFunction() {
            return FUNCTION_SEND_ULTRASONIC;
        }


        public void setSendAndroidMode(byte mode){

            content[1] = mode ;
        }


        public void setSendSlamwareMode(byte mode){

            content[2] = mode ;
        }
    }


    /**
     * 读取底盘状态信息
     * */
    public static class ReadFootState extends SingleChip{


        @Override
        public byte getFunction() {
            return FUNCTION_SEND_READ_FOOT_STATE;
        }

        public void setReadFootState(boolean isReadFootState){

            content[1] = (byte) (isReadFootState ? 0x01 : 0x00);
        }


    }


    public static class SteerHeadUpDown extends SingleChip{

        public static final byte TURN_RESET      = 0x00;
        public static final byte TURN_UP         = 0x01;
        public static final byte TURN_DOWN       = 0x02;

        @Override
        public byte getFunction() {

            content[1] = 0x01 ;

            return FUNCTION_SEND_STEER;
        }

        public void turnReset(){

            content[2] = TURN_RESET;
        }

        public void turnUp(int angle){

            short ang = (short) (1500 - 2*angle);

            content[2] = TURN_UP ;
            content[3] = (byte) (0xFF & (ang>>8));
            content[4] = (byte) (0xFF & ang) ;
        }

        public void turnDown(int angle){

            short ang = (short) (1500 - 2*angle);

            content[2] = TURN_DOWN ;
            content[3] = (byte) (0xFF & (ang>>8));
            content[4] = (byte) (0xFF & ang) ;
        }
    }


    public static class SteerHeadLeftRight extends SingleChip{

        public static final byte TURN_RESET         = 0x00;
        public static final byte TURN_LEFT          = 0x01;
        public static final byte TURN_RIGHT         = 0x02;

        @Override
        public byte getFunction() {

            content[1] = 0x02 ;
            return FUNCTION_SEND_STEER;
        }


        public void turnReset(){

            content[2] = TURN_RESET;
        }

        public void turnLeft(int angle){

            short ang = (short) (1500 + 7*angle);

            content[2] = TURN_LEFT ;
            content[3] = (byte) (0xFF & (ang>>8));
            content[4] = (byte) (0xFF & ang) ;
        }


        public void turnRight(int angle){


            short ang = (short) (1500 + 7*angle);

            content[2] = TURN_RIGHT ;
            content[3] = (byte) (0xFF & (ang>>8));
            content[4] = (byte) (0xFF & ang) ;
        }

    }

    /**
     * LED灯
     * */
    public static class SteerLed extends SingleChip{

        public static final byte POSITION_CHEST                         = 0x01 ;
        public static final byte POSITION_RIGHT_EAR                     = 0x02 ;
        public static final byte POSITION_LEFT_EAR                      = 0x03 ;
//        /**左臂*/
//        public static final byte POSITION_LEFT_ARM                      = 0x04 ;
//        /**右臂*/
//        public static final byte POSITION_RIGHT_ARM                     = 0x05 ;
//
        public static final byte COLOR_RED                              = 0x01 ; //红
        public static final byte COLOR_GREEN                            = 0x02 ; //绿
        public static final byte COLOR_BLUE                             = 0x04 ; //蓝


        @Override
        public byte getFunction() {

            content[1] = 0x03 ;

            return FUNCTION_SEND_STEER;
        }

        public void setPosition(byte position){

            content[2] = position ;
        }

        public void setSpeed(byte speed){

            content[3] = speed ;
        }

        public void setColor(byte color){

            content[4] = color ;
        }

    }


        public static class SteerLed2 extends SingleChip{

            @Override
            public byte getFunction() {

                content[1] = 0x04 ;

                return FUNCTION_SEND_STEER;
            }

            public void setPosition(byte position){

                content[2] = position ;
            }

            public void setOn(boolean isOn){

                content[3] = (byte) (isOn ? 0x01 : 0x00);
            }

            public void setColor(byte color){

                content[4] = color ;
            }


    }



    // 以下为单片机端发送到Android信息

    public static abstract class Receive extends SingleChip{

        public abstract void setData(byte[] data);
    }


    /**
     * 触摸信息
     * */
    public static class ReceiveTouch extends Receive{

        @Override
        public byte getFunction() {
            return FUNCTION_RECEIVE_TOUCH;
        }


        @Override
        public void setData(byte[] data) {

        }
    }

    /**
     * 故障信息
     * */
    public static class ReceiveFault extends Receive{

        public static final int FAULT_CODE_10 = 0x10 ;   //0x10 步科底盘启动不成功
        public static final int FAULT_CODE_20 = 0x20 ;   //0x20 传感器板启动不成功或主板与传感器板连接异常
        public static final int FAULT_CODE_21 = 0x21 ;   //0x21 传感器板启动后断开连接
        public static final int FAULT_CODE_22 = 0x22 ;   //0x22 传感器数据帧接收出错
        public static final int FAULT_CODE_40 = 0x40 ;   //0x40  slamware core启动不成功
        public static final int FAULT_CODE_41 = 0x41 ;   //0x41  slamware core内部出错
        public static final int FAULT_CODE_50 = 0x50 ;   //0x50 电量计初始化不成功


        @Override
        public byte getFunction() {
            return FUNCTION_RECEIVE_FAULT;
        }

        @Override
        public void setData(byte[] data) {

        }
    }


    /**
     * 超声波信息
     * */
    public static class ReceiveUltrasonic extends Receive{

        private int[] distances ;

        public int[] getDistances() {
            return distances;
        }

        @Override
        public byte getFunction() {
            return FUNCTION_RECEIVE_ULTRASONIC;
        }

        @Override
        public void setData(byte[] data) {

            int length = data[4] & 0xFF ;
            if(distances == null || distances.length != length){

                distances = new int[length] ;
            }
            for (int i = 0 ; i < length ; i ++ ){

                distances[i] = data[5+i] & 0xFF ;
            }

        }



    }

    /**
     *  OBD数据包
     */

    public static class OBDData extends Receive{

        public static SparseArray<String> driverStatusInfos = new SparseArray<>() ;
        static {

            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_0,"驱动器正常（机器人正常情况）");
            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_1,"驱动器内部错误");
            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_2,"编码器ABN信号错误");
            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_3,"编码器UVW信号错误");
            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_4,"编码器计数错误");
            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_5,"驱动器温度过高");
            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_6,"驱动器总线电压过高");
            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_7,"驱动器总线电压过低");
            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_8,"驱动器输出短路");
            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_9,"驱动器制动电阻异常");
            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_10,"实际跟踪误差超过允许范围");
            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_11,"逻辑电压过低18");
            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_12,"I2*T故障");
            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_13,"输出脉冲频率过高");
            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_14,"保留备用");
            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_15,"寻找电机错误");
            driverStatusInfos.put(Y128Steering.ReceiveSystemState.DRIVER_STATUS_16,"EEPROM内部错误");
        }


        private short voltage ;     //电压
        private byte level ;        //电量

        private short driver1Status ;
        private short driver2Status ;

        @Override
        public void setData(byte[] data){

            this.voltage = (short) (data[4] << 8 | data[5]);
            this.level = data[6] ;
            this.driver1Status = (short) (data[8] << 8 | data[9]);
            this.driver2Status = (short) (data[10] << 8 | data[11]);

        }


        public short getVoltage() {
            return voltage;
        }

        public byte getLevel() {
            return level;
        }

        public short getDriver1Status() {
            return driver1Status;
        }

        public short getDriver2Status() {
            return driver2Status;
        }

        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder() ;

            sb.append("电压值 : " + voltage/100F + "\n") ;
            sb.append("电量值 : " + level + "%\n") ;

            String driverStatusInfo = driverStatusInfos.get(driver1Status) ;
            if(driverStatusInfo == null){

                driverStatusInfo = "未知码 " + driver1Status ;
            }
            sb.append("底盘1状态 : " + driverStatusInfo + "\n") ;

            driverStatusInfo = driverStatusInfos.get(driver2Status) ;
            if(driverStatusInfo == null){

                driverStatusInfo = "未知码 " + driver2Status ;
            }
            sb.append("底盘2状态 : " + driverStatusInfo ) ;

            return sb.toString();
        }

        @Override
        public byte getFunction() {

            return FUNCTION_RECEIVE_OBD_DATA;
        }
    }




    /**
     * 系统信息
     * */
    public static class ReceiveSystemState extends Receive{

        public static final byte CHARGE_FREE = 0x00 ;           //正常状态
        public static final byte CHARGE_IS_CHARGING = 0x01 ;    //正在连接底座充电
        public static final byte CHARGE_WIRE_CHARGING = 0x02 ;  //正在连接线缆充电
        public static final byte CHARGE_ATTACHING = 0x03 ;      //正在和充电座对接
        public static final byte CHARGE_DETACHING = 0x04 ;      //正在脱离充电座
        public static final byte CHARGE_FULL = 0x05 ;           //充满，但仍然连接在充电座上

        public static final byte KINCO_START_FAIL = 0x00 ;      //kinco底盘启动不成功
        public static final byte KINCO_IS_WORKING = 0x01 ;      //kinco底盘正常工作

        public static final short DRIVER_STATUS_0 = 0x0000;     //驱动器正常（机器人正常情况）
        public static final short DRIVER_STATUS_1 = 0x0100;     //驱动器内部错误
        public static final short DRIVER_STATUS_2 = 0x0200;     //编码器ABN信号错误
        public static final short DRIVER_STATUS_3 = 0x0400;     //编码器UVW信号错误
        public static final short DRIVER_STATUS_4 = 0x0800;     //编码器计数错误
        public static final short DRIVER_STATUS_5 = 0x1000;     //驱动器温度过高
        public static final short DRIVER_STATUS_6 = 0x2000;     //驱动器总线电压过高
        public static final short DRIVER_STATUS_7 = 0x4000;     //驱动器总线电压过低
        public static final short DRIVER_STATUS_8 = (short) 0x8000;//驱动器输出短路
        public static final short DRIVER_STATUS_9 = 0x0001;     //驱动器制动电阻异常
        public static final short DRIVER_STATUS_10 = 0x0002;    //实际跟踪误差超过允许范围
        public static final short DRIVER_STATUS_11 = 0x0004;    //逻辑电压过低18
        public static final short DRIVER_STATUS_12 = 0x0008;    //I2*T故障
        public static final short DRIVER_STATUS_13 = 0x0010;    //输出脉冲频率过高
        public static final short DRIVER_STATUS_14 = 0x0020;    //保留备用
        public static final short DRIVER_STATUS_15 = 0x0040;    //寻找电机错误
        public static final short DRIVER_STATUS_16 = 0x0080;    //EEPROM内部错误

        public static final short SENSORBOARD_START_FAIL = 0x00;    //传感器板启动连接失败
        public static final short SENSORBOARD_IS_WORKING = 0x01;    //传感器板正常工作
        public static final short SENSORBOARD_DISCONNECT = 0x02;    //传感器板启动后又断开连接
        public static final short SENSORBOARD_RECV_ERR = 0x03;      //传感器接收帧出错


        public static final short SLAMWARECORE_START_FAIL = 0x00;    //slamware core 启动不成功
        public static final short SLAMWARECORE_IS_WORKING = 0x01;    //slamware core 正常工作
        public static final short SLAMWARECORE_ERROR = 0x02;        //slamware core 内部出错
        public static final short SLAMWARECORE_DISCONNECT = 0x03;    //slamware core 断开连接
        public static final short SLAMWARECORE_START_OK = 0x04;      //slamware core 启动成功
        public static final short SLAMWARECORE_FIREWARE_UPDATE = 0x05; //slamware core固件升级


        @Override
        public byte getFunction() {

            return FUNCTION_RECEIVE_SYSTEM_STATE;
        }


        public static SparseArray<String> batteryStateInfos = new SparseArray<>() ;
        static {

            batteryStateInfos.put(CHARGE_FREE, "正常状态" );
            batteryStateInfos.put(CHARGE_IS_CHARGING, "正在连接底座充电" );
            batteryStateInfos.put(CHARGE_WIRE_CHARGING, "正在连接线缆充电" );
            batteryStateInfos.put(CHARGE_ATTACHING, "正在和充电座对接" );
            batteryStateInfos.put(CHARGE_DETACHING, "正在脱离充电座" );
            batteryStateInfos.put(CHARGE_FULL, "充满，但仍然连接在充电座上" );

        }
        public static SparseArray<String> kincoStateInfos = new SparseArray<>() ;
        static {

            kincoStateInfos.put(KINCO_START_FAIL,"kinco底盘启动不成功");
            kincoStateInfos.put(KINCO_IS_WORKING,"kinco底盘正常工作");
        }

        public static SparseArray<String> sensorboardStatusInfos = new SparseArray<>() ;
        static {

            sensorboardStatusInfos.put(SENSORBOARD_START_FAIL,"传感器板启动连接失败");
            sensorboardStatusInfos.put(SENSORBOARD_IS_WORKING,"传感器板正常工作");
            sensorboardStatusInfos.put(SENSORBOARD_DISCONNECT,"传感器板启动后又断开连接");
            sensorboardStatusInfos.put(SENSORBOARD_RECV_ERR,"传感器接收帧出错");
        }
        public static SparseArray<String> slamwareStatusInfos = new SparseArray<>() ;
        static {
            slamwareStatusInfos.put(SLAMWARECORE_START_FAIL,"slamware core 启动不成功");
            slamwareStatusInfos.put(SLAMWARECORE_IS_WORKING,"slamware core 正常工作");
            slamwareStatusInfos.put(SLAMWARECORE_ERROR,"slamware core 内部出错");
            slamwareStatusInfos.put(SLAMWARECORE_DISCONNECT,"slamware core 断开连接");
            slamwareStatusInfos.put(SLAMWARECORE_START_OK,"slamware core 启动成功");
            slamwareStatusInfos.put(SLAMWARECORE_FIREWARE_UPDATE,"slamware core固件升级");

        }


        private byte chargeState ;          //充电状态
        private byte kincoState ;           //Kinco底盘状态
        private byte sensorboardStatus ;    //传感器板状态
        private byte slamwareStatus ;       //Slamware core运行状态
        private byte ioStatus ;             //IO传感器状态
        private byte sysStatus ;            //系统状态
        private byte hardVer ;              //硬件版本
        private byte softVer ;              //软件版本

        @Override
        public void setData(byte[] data){

            this.chargeState = data[4] ;
            this.kincoState = data[5] ;
            this.sensorboardStatus = data[6] ;
            this.slamwareStatus = data[7] ;
            this.ioStatus = data[8] ;
            this.sysStatus = data[9] ;
            this.hardVer = data[13] ;
            this.softVer = data[14] ;

        }

        public byte getChargeState() {
            return chargeState;
        }

        public byte getKincoState() {
            return kincoState;
        }

        public byte getSensorboardStatus() {
            return sensorboardStatus;
        }

        public byte getSlamwareStatus() {
            return slamwareStatus;
        }

        public byte getIoStatus() {
            return ioStatus;
        }

        public byte getSysStatus() {
            return sysStatus;
        }

        public byte getHardVer() {
            return hardVer;
        }

        public byte getSoftVer() {
            return softVer;
        }

        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder() ;

            String batteryStateInfo = batteryStateInfos.get(chargeState) ;
            if(batteryStateInfo == null){

                batteryStateInfo = "未知码 " + chargeState ;
            }
            sb.append("充电状态 : " + batteryStateInfo + "\n") ;

            String kincoStateInfo = kincoStateInfos.get(kincoState) ;
            if(kincoStateInfo == null){

                kincoStateInfo = "未知码 " + kincoState ;
            }
            sb.append("Kinco底盘状态 : " + kincoStateInfo + "\n") ;


            sb.append("IO传感器状态 : " + ioStatus + "\n") ;

            String sensorboardStatusInfo = sensorboardStatusInfos.get(sensorboardStatus) ;
            if(sensorboardStatusInfo == null){

                sensorboardStatusInfo = "未知码 " + sensorboardStatus ;
            }
            sb.append("传感器板状态 : " + sensorboardStatusInfo + "\n") ;


            String slamwareStatusInfo = slamwareStatusInfos.get(slamwareStatus) ;
            if(slamwareStatusInfo == null){

                slamwareStatusInfo = "未知码 " + sensorboardStatus ;
            }
            sb.append("Slamware 运行状态 : " + slamwareStatusInfo + "\n") ;

            boolean isUseUltrasonic = ( (sysStatus & 0x01) == 0x01 ) ;
            sb.append("系统状态 : " + (isUseUltrasonic ? "不使用超声波数据" : "使用超声波数据") + "\n" ) ;

            sb.append("硬件版本 : V" + ((hardVer&0xf0)>> 4) + "." + (hardVer&0x0f ) + "\n") ;

            sb.append("软件版本 : V" + ((softVer&0xf0)>> 4) + "." + (softVer&0x0f )  + "\n") ;

            return sb.toString();

        }


    }



}
