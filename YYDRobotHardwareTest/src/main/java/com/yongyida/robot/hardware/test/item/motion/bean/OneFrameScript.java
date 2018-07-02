package com.yongyida.robot.hardware.test.item.motion.bean;


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

import com.google.gson.Gson;
import com.yongyida.robot.communicate.app.common.send.SendClient;
import com.yongyida.robot.communicate.app.hardware.motion.response.data.HandAngle;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.ArmControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.FingerControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.FootControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.HandControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.HeadControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.SteeringControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.constant.Direction;

import java.util.ArrayList;

/**
 * Create By HuangXiangXiang 2018/6/12
 * 单独一个动作
 */
public class OneFrameScript {

    public static OneFrameScript getReset(){

        OneFrameScript oneFrameScript = new OneFrameScript() ;

        oneFrameScript.footControl.setControl(false);

        return oneFrameScript ;
    }


    private int nextScriptTime = 2000 ;      // 动作耗时




    private HeadControl headControl = new HeadControl() ;
    private FootControl footControl = new FootControl() ;
    private HandControl handControl = new HandControl() ;

    public OneFrameScript(){

        //头
        headControl.setAction(HeadControl.Action.CUSTOM);

        SteeringControl headLeftRight = headControl.getHeadLeftRightControl() ;
        headLeftRight.setMode(SteeringControl.Mode.DISTANCE_TIME);
        headLeftRight.getDistance().setType(SteeringControl.Distance.Type.TO);
        headLeftRight.getDistance().setUnit(SteeringControl.Distance.Unit.PERCENT);
        headLeftRight.getDistance().setValue(50);
        headLeftRight.getTime().setUnit(SteeringControl.Time.Unit.MILLI_SECOND);
        headLeftRight.getTime().setValue(2000);

        SteeringControl headUpDown = headControl.getHeadUpDownControl() ;
        headUpDown.setMode(SteeringControl.Mode.DISTANCE_TIME);
        headUpDown.getDistance().setType(SteeringControl.Distance.Type.TO);
        headUpDown.getDistance().setUnit(SteeringControl.Distance.Unit.PERCENT);
        headUpDown.getDistance().setValue(50);

        headUpDown.getTime().setUnit(SteeringControl.Time.Unit.MILLI_SECOND);
        headUpDown.getTime().setValue(2000);

        // 脚
        SteeringControl foot = footControl.getFoot() ;
        foot.setMode(null);

        foot.setMode(SteeringControl.Mode.TIME_SPEED);
        foot.getTime().setUnit(SteeringControl.Time.Unit.MILLI_SECOND);
        foot.getTime().setValue(2000);


        // 手
        handControl.setDirection(Direction.BOTH);

        //手臂
        ArmControl armControl = handControl.getArmControl() ;
        initArms(armControl.getArmLefts()) ;
        initArms(armControl.getArmRights()) ;

        // 手指
        FingerControl fingerControl = handControl.getFingerControl() ;
        initFingers(fingerControl.getFingerLefts()) ;
        initFingers(fingerControl.getFingerRights()) ;

    }




    private void initArms(ArrayList<SteeringControl> arms){

        int size = arms.size() ;
        for (int i = 0 ; i < size ; i ++ ){

            initArm(arms.get(i)) ;
        }
    }
    private void initArm(SteeringControl arm){

        arm.setMode(SteeringControl.Mode.DISTANCE_TIME);

        arm.getDistance().setType(SteeringControl.Distance.Type.TO);
        arm.getDistance().setUnit(SteeringControl.Distance.Unit.ORIGINAL);
        arm.getDistance().setValue(2048);

        arm.getTime().setUnit(SteeringControl.Time.Unit.MILLI_SECOND);
        arm.getTime().setValue(2000);

        arm.setDelay(0);
    }


    private void initFingers(ArrayList<SteeringControl> fingers){

        int size = fingers.size() ;
        for (int i = 0 ; i < size ; i ++ ){

            initFinger(fingers.get(i)) ;
        }
    }

    private void initFinger(SteeringControl finger){

        finger.setMode(SteeringControl.Mode.DISTANCE_TIME);

        finger.getDistance().setType(SteeringControl.Distance.Type.TO);
        finger.getDistance().setUnit(SteeringControl.Distance.Unit.PERCENT);
        finger.getDistance().setValue(0);

        finger.getTime().setUnit(SteeringControl.Time.Unit.MILLI_SECOND);
        finger.getTime().setValue(2000);

        finger.setDelay(0);
    }


    public void setNextScriptTime(int nextScriptTime) {
        this.nextScriptTime = nextScriptTime;
    }

    public int getNextScriptTime() {
        return nextScriptTime;
    }

    public HeadControl getHeadControl() {
        return headControl;
    }

    public HandControl getHandControl() {
        return handControl;
    }

    public FootControl getFootControl() {
        return footControl;
    }


    /**设置*/
    public void setHandAngle(HandAngle handAngle) {

        ArmControl armControl = handControl.getArmControl() ;

        HandAngle.Angle leftArmAngle = handAngle.leftArmAngle ;
        ArrayList<SteeringControl> armLefts = armControl.getArmLefts() ;
        final int leftSize = armLefts.size() ;
        for (int i = 0 ; i < leftSize ; i ++){

            armLefts.get(i).getDistance().setValue(leftArmAngle.angles[i]);
        }

        HandAngle.Angle rightArmAngle = handAngle.rightArmAngle;
        ArrayList<SteeringControl> armRights = armControl.getArmRights() ;
        final int rightSize = armRights.size() ;
        for (int i = 0 ; i < rightSize ; i ++){

            armRights.get(i).getDistance().setValue(rightArmAngle.angles[i]);
        }

    }

    public void setRecordArmAngle(OneFrameScript recordArmAngle) {

        nextScriptTime = recordArmAngle.nextScriptTime ;

        headControl = recordArmAngle.headControl ;
        handControl = recordArmAngle.handControl ;
        footControl = recordArmAngle.footControl ;
    }

    public OneFrameScript deepClone(){

        Gson gson = new Gson() ;
        String json = gson.toJson(this) ;
        return  gson.fromJson(json,OneFrameScript.class) ;
    }



    /**
     * 执行脚本
     * */
    public void executeScript(Context context){

        if(headControl.isControl()){

            SendClient.getInstance(context).send(null, headControl, null);
        }

        if(footControl.isControl()){

            SendClient.getInstance(context).send(null, footControl, null);

        }

        if(handControl.isControl()){

            SendClient.getInstance(context).send(null, handControl, null);
        }
    }



}
