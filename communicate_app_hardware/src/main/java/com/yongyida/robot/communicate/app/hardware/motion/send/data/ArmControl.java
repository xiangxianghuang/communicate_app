package com.yongyida.robot.communicate.app.hardware.motion.send.data;


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

import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;
import com.yongyida.robot.communicate.app.hardware.motion.send.data.constant.Direction;

import java.util.ArrayList;

/**
 * Create By HuangXiangXiang 2018/6/11
 * 手臂控制
 */
public class ArmControl extends BaseMotionSendControl {

    /**动作种类*/
    public enum Action{

        CUSTOM,                 // 自定义
        RESET;                  // 恢复初始化

        public final byte value ;
        Action(){

            this(0) ;
        }
        Action(int value){

            this.value = (byte) value;
        }
    }

    private Direction direction = Direction.BOTH ;
    private Action action = Action.CUSTOM ;
    private ArrayList<SteeringControl> armLefts ;
    private ArrayList<SteeringControl> armRights ;

    public ArmControl(){

        armLefts = new ArrayList<>() ;

        SteeringControl armLeft0 = new SteeringControl(SteeringControl.Position.ARM_LEFT_0) ;
        SteeringControl armLeft1 = new SteeringControl(SteeringControl.Position.ARM_LEFT_1) ;
        SteeringControl armLeft2 = new SteeringControl(SteeringControl.Position.ARM_LEFT_2) ;
        SteeringControl armLeft3 = new SteeringControl(SteeringControl.Position.ARM_LEFT_3) ;
        SteeringControl armLeft4 = new SteeringControl(SteeringControl.Position.ARM_LEFT_4) ;
        SteeringControl armLeft5 = new SteeringControl(SteeringControl.Position.ARM_LEFT_5) ;
        armLefts.add(armLeft0);
        armLefts.add(armLeft1);
        armLefts.add(armLeft2);
        armLefts.add(armLeft3);
        armLefts.add(armLeft4);
        armLefts.add(armLeft5);


        armRights = new ArrayList<>() ;
        SteeringControl armRight0 = new SteeringControl(SteeringControl.Position.ARM_RIGHT_0) ;
        SteeringControl armRight1 = new SteeringControl(SteeringControl.Position.ARM_RIGHT_1) ;
        SteeringControl armRight2 = new SteeringControl(SteeringControl.Position.ARM_RIGHT_2) ;
        SteeringControl armRight3 = new SteeringControl(SteeringControl.Position.ARM_RIGHT_3) ;
        SteeringControl armRight4 = new SteeringControl(SteeringControl.Position.ARM_RIGHT_4) ;
        SteeringControl armRight5 = new SteeringControl(SteeringControl.Position.ARM_RIGHT_5) ;
        armRights.add(armRight0);
        armRights.add(armRight1);
        armRights.add(armRight2);
        armRights.add(armRight3);
        armRights.add(armRight4);
        armRights.add(armRight5);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {

        this.direction = direction ;

    }


    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {

        this.action = action;
    }


    public ArrayList<SteeringControl> getArmLefts() {
        return armLefts;
    }

    public ArrayList<SteeringControl> getArmRights() {
        return armRights;
    }

    public SteeringControl getArm(SteeringControl.Position position) {

        if(armLefts != null){

            int size = armLefts.size() ;
            for (int i = 0 ; i < size ; i++){

                SteeringControl control = armLefts.get(i) ;
                if(position == control.getPosition()){

                    return control ;
                }
            }
        }

        if(armRights != null){

            int size = armRights.size() ;
            for (int i = 0 ; i < size ; i++){

                SteeringControl control = armRights.get(i) ;
                if(position == control.getPosition()){

                    return control ;
                }
            }
        }

        return null;
    }



    @Override
    public String toJson() {

        MotionSend copySend =  baseSend ;
        ArrayList<SteeringControl> copyArmLefts = this.armLefts ;
        ArrayList<SteeringControl> copyArmRights = this.armRights ;

        if(action == Action.CUSTOM){

            switch (direction){

                case SAME:

                    this.armLefts = (ArrayList<SteeringControl>) getNeedSendControls(this.armLefts);
                    this.armRights = null ;
                    break;

                case LEFT:

                    this.armLefts = (ArrayList<SteeringControl>) getNeedSendControls(this.armLefts);
                    this.armRights = null ;
                    break;

                case RIGHT:

                    this.armLefts = null ;
                    this.armRights = (ArrayList<SteeringControl>) getNeedSendControls(this.armRights);
                    break;

                case BOTH:

                    this.armLefts = (ArrayList<SteeringControl>) getNeedSendControls(this.armLefts);
                    this.armRights = (ArrayList<SteeringControl>) getNeedSendControls(this.armRights);

                    break;
            }

        }else {

            this.armLefts = null ;
            this.armRights = null ;
        }

        String json = GSON.toJson(this );

        baseSend = copySend;
        this.armLefts  = copyArmLefts ;
        this.armRights = copyArmRights ;


        return json;
    }


//    private ArrayList<SteeringControl> getNeedSendControls(ArrayList<SteeringControl> steeringControls){
//
//        ArrayList<SteeringControl> controls = new ArrayList<>() ;
//
//        int size = steeringControls.size() ;
//        for (int i = 0 ; i < size ; i ++){
//
//            SteeringControl control = steeringControls.get(i) ;
//            if(control.isControl()){
//                controls.add(control) ;
//            }
//        }
//        return controls ;
//    }


}
