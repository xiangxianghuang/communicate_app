package com.yongyida.robot.model.y148.montrol.control.item;


/* 
                              _ooOoo_ 
                             o8888888o 
                             88" . "88 
                             (| -_- |) 
                             O\  =  /O 
                          ____/`---'\____ 
                        .'  \\|     |//  `. 
                       /  \\|||  :  |||//  \ 
                      /  _||||| -:- |||||-  \ 
                      |   | \\\  -  /// |   | 
                      | \_|  ''\---/''  |   | 
                      \  .-\__  `-`  ___/-. / 
                    ___`. .'  /--.--\  `. . __ 
                 ."" '<  `.___\_<|>_/___.'  >'"". 
                | | :  `- \`.;`\ _ /`;.`/ - ` : | | 
                \  \ `-.   \_ __\ /__ _/   .-` /  / 
           ======`-.____`-.___\_____/___.-`____.-'====== 
                              `=---=' 
           .............................................  
                    佛祖镇楼                  BUG辟易  
            佛曰:  
                    写字楼里写字间，写字间里程序员；  
                    程序人员写程序，又拿程序换酒钱。  
                    酒醒只在网上坐，酒醉还来网下眠；  
                    酒醉酒醒日复日，网上网下年复年。  
                    但愿老死电脑间，不愿鞠躬老板前；  
                    奔驰宝马贵者趣，公交自行程序员。  
                    别人笑我忒疯癫，我笑自己命太贱；  
                    不见满街漂亮妹，哪个归得程序员？ 
*/

import android.content.Context;

import com.yongyida.robot.communicate.app.common.response.SendResponse;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.FootControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.SoundLocationControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.model.agreement.Y128Send;
import com.yongyida.robot.model.agreement.Y128Steering;

/**
 * Create By HuangXiangXiang 2018/6/21
 * 串口行走
 */
public class FootSerial {

    private static final String TAG =FootSerial.class.getSimpleName() ;

    private Y128Send mSend ;

    private Y128Steering.FootMove mMove ;
    private Y128Steering.FootCorner mFootCorner ;




    private static FootSerial mInstance ;
    public static FootSerial getInstance(Context context){

        if(mInstance == null){

            mInstance = new FootSerial(context.getApplicationContext()) ;
        }

        return mInstance ;
    }

    private FootSerial(Context context){

        mSend = Y128Send.getInstance() ;

        mMove = new Y128Steering.FootMove() ;
        mMove.setSpeed(2);

        mFootCorner = new Y128Steering.FootCorner() ;
        mFootCorner.setSpeed(2);
    }



    public SendResponse onHandler(FootControl footControl, IResponseListener responseListener) {



        return null ;
    }



    /**
     *
     * 速度值
     * 1-100
     * */
    public void setSpeed(int speed){

        if(speed < 34){

            mMove.setSpeed(0);
            mFootCorner.setSpeed(0);


        }else if(speed < 67){

            mMove.setSpeed(1);
            mFootCorner.setSpeed(1);

        }else {

            mMove.setSpeed(2);
            mFootCorner.setSpeed(2);
        }
    }


    public int forward(int arg) {

        startControl(TYPE_FORWARD , arg) ;

        return 0 ;
    }

    public int back(int arg) {

        startControl(TYPE_BACK, arg) ;

        return 0 ;
    }

    public int left(int arg) {

        startControl(TYPE_LEFT , arg) ;

        return 0 ;
    }

    public int right(int arg) {

        startControl(TYPE_RIGHT , arg) ;
        return 0 ;
    }


    public int stop() {

        startControl(TYPE_STOP,0) ;

        return 0 ;
    }

    private void startControl(int type , int time){

        stopControl() ;

        mControlThread = new ControlThread(type, time) ;
        mControlThread.start();
    }

    private void stopControl(){

        if(mControlThread != null && mControlThread.isRun){

            mControlThread.stopRun();
            mControlThread = null ;
        }
    }


    private ControlThread mControlThread ;

    private static final int TYPE_FORWARD   = 0x01  ;
    private static final int TYPE_BACK      = 0x02  ;
    private static final int TYPE_LEFT      = 0x03  ;
    private static final int TYPE_RIGHT     = 0x04  ;
    private static final int TYPE_STOP      = 0x05  ;

    private static final int LEFT_RIGHT_INTERVAL_TIME = 30 ; // 左右转动的发送间隔时间

    private class ControlThread extends Thread{

        private boolean isRun ;

        private int type ;
        private int time ;

        private ControlThread(int type , int time){

            this.isRun = true ;

            this.type = type ;
            this.time = time ;

        }

        @Override
        public void run() {

            switch (type){

                case TYPE_FORWARD:

                    forward();
                    break;

                case TYPE_BACK:

                    back();
                    break;

                case TYPE_LEFT:

                    left();
                    break;

                case TYPE_RIGHT:

                    right();
                    break;
                case TYPE_STOP:

                    stopMove();
                    break;
            }


        }

        private void stopRun(){

            isRun = false ;
        }

        private void forward(){

            mMove.forward();
            sendData(mMove) ;

            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(isRun){

                stopMove() ;
            }

        }

        private void back(){

            mMove.back();
            sendData(mMove) ;

            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(isRun){

                stopMove() ;
            }

        }

        private void left(){

            mMove.left();

            sendData(mMove) ;
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(isRun){

                stopMove() ;
            }

        }

        private void right(){

            mMove.right();

            sendData(mMove) ;
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(isRun){

                stopMove() ;
            }

        }

        private void stopMove(){

            mMove.stop();
            sendData(mMove) ;
        }
    }

    private void sendData(Y128Steering.SingleChip move) {

        mSend.sendData(move.getCmd()) ;
    }






    // 转动角度
    public SendResponse onHandler(SoundLocationControl soundLocationControl, IResponseListener responseListener) {

        return null ;
    }

    public void turnLeft(int angle){

        mFootCorner.turnLeft(angle);
        sendData(mFootCorner) ;
    }

    public void turnRight(int angle){

        mFootCorner.turnRight(angle);
        sendData(mFootCorner) ;
    }


    /**
     *  声源定位 最多转动 180度
     *  @param angle 转动的角度
     * */
    public void soundLocation(int angle){

        LogHelper.i(TAG, LogHelper.__TAG__() + " " + angle );

        angle = angle % 360 ;
        if(angle < -180) {

            angle = 360 + angle ;

            mFootCorner.turnRight(angle);
            sendData(mFootCorner) ;

        }else if(angle < 0) {

            mFootCorner.turnLeft(-angle);
            sendData(mFootCorner) ;

        }else if(angle < 180){

            mFootCorner.turnRight(angle);
            sendData(mFootCorner) ;

        }else if(angle < 360){
            //
            angle =  360 - angle ;

            mFootCorner.turnLeft(angle);
            sendData(mFootCorner) ;
        }

    }


}
