//package com.yongyida.robot.hardware.test.item.hand.bean;
//
//
///*
//                              _ooOoo_
//                             o8888888o
//                             88" . "88
//                             (| -_- |)
//                             O\  =  /O
//                          ____/`---'\____
//                        .'  \\|     |//  `.
//                       /  \\|||  :  |||//  \
//                      /  _||||| -:- |||||-  \
//                      |   | \\\  -  /// |   |
//                      | \_|  ''\---/''  |   |
//                      \  .-\__  `-`  ___/-. /
//                    ___`. .'  /--.--\  `. . __
//                 ."" '<  `.___\_<|>_/___.'  >'"".
//                | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//                \  \ `-.   \_ __\ /__ _/   .-` /  /
//           ======`-.____`-.___\_____/___.-`____.-'======
//                              `=---='
//           .............................................
//                    佛祖镇楼                  BUG辟易
//            佛曰:
//                    写字楼里写字间，写字间里程序员；
//                    程序人员写程序，又拿程序换酒钱。
//                    酒醒只在网上坐，酒醉还来网下眠；
//                    酒醉酒醒日复日，网上网下年复年。
//                    但愿老死电脑间，不愿鞠躬老板前；
//                    奔驰宝马贵者趣，公交自行程序员。
//                    别人笑我忒疯癫，我笑自己命太贱；
//                    不见满街漂亮妹，哪个归得程序员？
//*/
//
//import com.google.gson.Gson;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.ArmSendControl;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.FingerSendControl;
//import com.yongyida.robot.communicate.app.hardware.hand.response.data.HandAngle;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Direction;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Mode;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.constant.Type;
//
//import java.util.ArrayList;
//
///**
// * Create By HuangXiangXiang 2018/5/28
// */
//public class RecordArmAngle {
//
//    private ArmSendControl leftArmControl ;
//    private ArmSendControl rightArmControl ;
//
//    private FingerSendControl leftFingerControl ;
//    private FingerSendControl rightFingerControl ;
//
//    private int time = 2000;   // 该动作耗时
//    private int delay = 1000; //延迟
//
//    public RecordArmAngle(){
//
//        leftArmControl = new ArmSendControl() ;
//        leftArmControl.setDirection(Direction.LEFT);
//        ArrayList<ArmSendControl.Joint> leftJoints = new ArrayList<>() ;
//        for (int i = 0 ; i < 6 ; i++){
//
//            ArmSendControl.Joint joint = new ArmSendControl.Joint(i);
//            leftJoints.add(joint);
//            initJoint(joint) ;
//        }
//        leftArmControl.setJoints(leftJoints);
//
//        rightArmControl = new ArmSendControl() ;
//        rightArmControl.setDirection(Direction.RIGHT);
//        ArrayList<ArmSendControl.Joint> rightJoints = new ArrayList<>() ;
//        for (int i = 0 ; i < 6 ; i++){
//
//            ArmSendControl.Joint joint = new ArmSendControl.Joint(i);
//            rightJoints.add(joint);
//            initJoint(joint) ;
//        }
//        rightArmControl.setJoints(rightJoints);
//
//
//
//        leftFingerControl = new FingerSendControl() ;
//        leftFingerControl.setDirection(Direction.LEFT);
//        ArrayList<FingerSendControl.Finger> leftFingers = new ArrayList<>() ;
//        for(int i = 0 ; i < 5 ; i++){
//
//            FingerSendControl.Finger finger = new FingerSendControl.Finger(i) ;
//            leftFingers.add(finger) ;
//            initFinger(finger) ;
//        }
//        leftFingerControl.setFingers(leftFingers);
//
//
//        rightFingerControl = new FingerSendControl() ;
//        rightFingerControl.setDirection(Direction.RIGHT);
//        ArrayList<FingerSendControl.Finger> rightFingers = new ArrayList<>() ;
//        for(int i = 0 ; i < 5 ; i++){
//
//            FingerSendControl.Finger finger = new FingerSendControl.Finger(i) ;
//            rightFingers.add(finger) ;
//            initFinger(finger) ;
//        }
//        rightFingerControl.setFingers(rightFingers);
//
//    }
//
//
//    private void initJoint(ArmSendControl.Joint joint){
//
//        joint.setMode(Mode.TIME);
//        joint.setModeValue(2000);
//        joint.setType(Type.TO);
//        joint.setTypeValue(2048);
//    }
//
//    /**
//     * 初始化手指
//     * */
//    private void initFinger(FingerSendControl.Finger finger){
//
//        finger.setMode(Mode.TIME);
//        finger.setModeValue(2000);
//        finger.setType(Type.TO);
//        finger.setTypeValue(0);
//    }
//
//
//    public ArmSendControl getLeftArmControl() {
//        return leftArmControl;
//    }
//
//    public void setLeftArmControl(ArmSendControl leftArmControl) {
//        this.leftArmControl = leftArmControl;
//    }
//
//    public ArmSendControl getRightArmControl() {
//        return rightArmControl;
//    }
//
//    public void setRightArmControl(ArmSendControl rightArmControl) {
//        this.rightArmControl = rightArmControl;
//    }
//
//    public FingerSendControl getLeftFingerControl() {
//        return leftFingerControl;
//    }
//
//    public void setLeftFingerControl(FingerSendControl leftFingerControl) {
//        this.leftFingerControl = leftFingerControl;
//    }
//
//    public FingerSendControl getRightFingerControl() {
//        return rightFingerControl;
//    }
//
//    public void setRightFingerControl(FingerSendControl rightFingerControl) {
//        this.rightFingerControl = rightFingerControl;
//    }
//
//    public int getTime() {
//        return time;
//    }
//
//    public void setTime(int time) {
//        this.time = time;
//
//        int size = leftArmControl.joints.size() ;
//        for (int i = 0 ; i < size ; i++){
//
//            setJointTime(leftArmControl.joints.get(i), time) ;
//        }
//
//        size = rightArmControl.joints.size() ;
//        for (int i = 0 ; i < size ; i++){
//
//            setJointTime(rightArmControl.joints.get(i), time) ;
//        }
//
//        size = leftFingerControl.fingers.size() ;
//        for (int i = 0 ; i < size ; i++){
//
//            setFingerTime(leftFingerControl.fingers.get(i), time) ;
//        }
//
//        size = rightFingerControl.fingers.size() ;
//        for (int i = 0 ; i < size ; i++){
//
//            setFingerTime(rightFingerControl.fingers.get(i), time) ;
//        }
//
//    }
//
//    private void setJointTime(ArmSendControl.Joint joint, int time){
//
//        joint.setModeValue(time);
//    }
//
//
//
//    private void setFingerTime(FingerSendControl.Finger finger, int time){
//
//        finger.setModeValue(time);
//    }
//
//    public int getDelay() {
//        return delay;
//    }
//
//    public void setDelay(int delay) {
//        this.delay = delay;
//    }
//
//
//    public void setHandAngle(HandAngle handAngle){
//
//        int size = leftArmControl.joints.size() ;
//        for (int i = 0 ; i < size ; i++){
//
//            leftArmControl.joints.get(i).setTypeValue(handAngle.leftArmAngle.angles[i]);
//        }
//
//        size = rightArmControl.joints.size() ;
//        for (int i = 0 ; i < size ; i++){
//
//            rightArmControl.joints.get(i).setTypeValue(handAngle.rightArmAngle.angles[i]);
//        }
//    }
//
//
//    public void setRecordArmAngle(RecordArmAngle recordArmAngle) {
//
//        leftArmControl = recordArmAngle.leftArmControl ;
//        rightArmControl = recordArmAngle.rightArmControl ;
//        leftFingerControl = recordArmAngle.leftFingerControl ;
//        rightFingerControl = recordArmAngle.rightFingerControl ;
//        time = recordArmAngle.time ;
//        delay = recordArmAngle.delay ;
//    }
//
//    private static Gson GSON = new Gson() ;
//    public RecordArmAngle deepClone() {
//
//        String json = GSON.toJson(this) ;
//        return GSON.fromJson(json, RecordArmAngle.class) ;
//    }
//}
