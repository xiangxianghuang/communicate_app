package com.yongyida.robot.model.y128.motion;

import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.model.agreement.Y128Send;
import com.yongyida.robot.model.agreement.Y128Steering;

/**
 * Created by HuangXiangXiang on 2018/3/16.
 * 声源定位
 */
public class SerialSoundLocation {

    private static final String TAG = SerialSoundLocation.class.getSimpleName() ;

    private Y128Steering.FootCorner mFootCorner = new Y128Steering.FootCorner() ;

    SerialSoundLocation(){

        setSpeed(50) ;
    }


    public void turnLeft(int angle){


        mFootCorner.turnLeft(angle);
        Y128Send.getInstance().sendData(mFootCorner) ;

    }

    public void turnRight(int angle){

        mFootCorner.turnRight(angle);
        Y128Send.getInstance().sendData(mFootCorner) ;

    }



    /**
     *
     * 速度值
     * 1-100
     * */
    public void setSpeed(int speed){

        if(speed < 34){

            mFootCorner.setSpeed(0);

        }else if(speed < 67){

            mFootCorner.setSpeed(1);
        }else {

            mFootCorner.setSpeed(2);
        }
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
            Y128Send.getInstance().sendData(mFootCorner) ;

        }else if(angle < 0) {

            mFootCorner.turnLeft(-angle);
            Y128Send.getInstance().sendData(mFootCorner) ;

        }else if(angle < 180){

            mFootCorner.turnRight(angle);
            Y128Send.getInstance().sendData(mFootCorner) ;

        }else if(angle < 360){
            //
            angle =  360 - angle ;

            mFootCorner.turnLeft(angle);
            Y128Send.getInstance().sendData(mFootCorner) ;
        }



    }



}
