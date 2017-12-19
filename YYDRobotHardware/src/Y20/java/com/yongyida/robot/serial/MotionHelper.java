package com.yongyida.robot.serial;

/**
 * Created by HuangXiangXiang on 2017/12/19.
 */
public class MotionHelper extends RobotController{


    public static final String MOTOR_CALL_BACK_DATA="com.yongyida.robot.motor.callBackData";
    public static final String SERIALPORT = "/dev/ttyMT2";
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

            int result = open(SERIALPORT);
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
