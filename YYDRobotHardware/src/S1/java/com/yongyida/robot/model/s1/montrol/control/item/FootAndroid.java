package com.yongyida.robot.model.s1.montrol.control.item;


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
import com.yongyida.robot.communicate.app.hardware.motion.send.data.SteeringControl;
import com.yongyida.robot.communicate.app.server.IResponseListener;
import com.yongyida.robot.dev.DevFile;

/**
 * Create By HuangXiangXiang 2018/6/30
 */
public class FootAndroid {

    private static final String PATH_FILE   = "/dev/yydmotor" ;         // s1 控制足部运动

    private static final String LEFT        = "110" ;                   // s1 控制足部运动
    private static final String RIGHT       = "111" ;                   // s1 控制足部运动
    private static final String STOP        = "12" ;                    // s1 控制足部运动


    private static FootAndroid mInstance ;
    public static FootAndroid getInstance(Context context){

        if(mInstance == null){

            mInstance = new FootAndroid(context.getApplicationContext()) ;
        }

        return mInstance ;
    }

    private FootAndroid(Context context){

    }

    public SendResponse onHandler(FootControl footControl, IResponseListener responseListener) {

        FootControl.Action action = footControl.getAction() ;
        if(action != null){

            switch (action){

                case LEFT:
                case RIGHT:

                    startMove(action, getTimeValue(footControl.getFoot().getTime())) ;
                    break;

                case STOP:

                    stopMove();
                    break;
            }

        }
        return null;
    }


    private void startMove(FootControl.Action action, int time){

        if(mMoveThread != null){

            mMoveThread.stopRun();
            mMoveThread = null ;
        }

        mMoveThread = new MoveThread(action, time) ;
        mMoveThread.start();
    }


    private void stopMove(){

        if(mMoveThread != null){

            mMoveThread.stopRun();
            mMoveThread = null ;
        }

        FootAndroid.this.stop();

    }

    private MoveThread mMoveThread ;

    private class MoveThread extends Thread{

        private boolean isRun = true ;
        private FootControl.Action action ;
        private int time ;


        MoveThread(FootControl.Action action, int time){

            this.action = action ;
            this.time = time ;
        }

        @Override
        public void run() {

            switch (action){

                case LEFT:

                    FootAndroid.this.left();
                    try {
                        sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(isRun){

                        isRun = false ;
                        FootAndroid.this.stop();
                    }

                    break;

                case RIGHT:

                    FootAndroid.this.right();
                    try {
                        sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(isRun){
                        isRun = false ;

                        FootAndroid.this.stop();
                    }

                    break;


            }

        }



        public void stopRun(){

            if(isRun){

                isRun = false ;
            }

        }
    }

    private int getTimeValue(SteeringControl.Time time){

        if(time == null){

            return 2000 ;
        }

        int value = time.getValue() ;
        SteeringControl.Time.Unit unit = time.getUnit() ;
        if(unit != null){

            switch (unit){

                case SECOND:
                    value *= 1000 ;
                    break;

            }
        }

        return value ;
    }

    private void left(){

        DevFile.writeString(PATH_FILE, LEFT) ;
    }

    private void right(){

        DevFile.writeString(PATH_FILE, RIGHT) ;
    }

    private void stop(){

        DevFile.writeString(PATH_FILE, STOP) ;

    }

}
