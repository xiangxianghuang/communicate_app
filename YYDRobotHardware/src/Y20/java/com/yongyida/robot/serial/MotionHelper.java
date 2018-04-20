package com.yongyida.robot.serial;

/**
 * Created by HuangXiangXiang on 2017/12/19.
 */
public class MotionHelper extends RobotController{


    public static final String MOTOR_CALL_BACK_DATA="com.yongyida.robot.motor.callBackData";
    public static final String SERIAL_PORT_8163     = "/dev/ttyMT2";        // Y20 8163
    public static final String SERIAL_PORT_8735     = "/dev/ttyMT1";        // Y50 8735

    private String serialPort ;
    public MotionHelper(String serialPort){

        this.serialPort = serialPort ;
    }


    private ControllListener mControllListener = new ControllListener(){
        public void onCallBack(int cmdType, byte[] data) {
//            mIntent=new Intent(MOTOR_CALL_BACK_DATA);
//            mIntent.putExtra("cmdType",cmdType);
//            mIntent.putExtra("data",data);
//            sendBroadcast(mIntent);
        }
    };


    private boolean isOpen = false ;

    public void open(){

        if(!isOpen){

            int result = open(serialPort);
            if(result == 0){

                isOpen = true ;
                setControllListener(mControllListener);
            }
        }
    }

    public int close(){

        if(isOpen){

            setControllListener(null);
            int result = super.close() ;
            if(result == 0){

                isOpen = false ;
            }
        }

        return 0 ;
    }
}
