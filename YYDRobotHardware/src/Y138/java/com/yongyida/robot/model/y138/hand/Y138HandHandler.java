//package com.yongyida.robot.model.y138.hand;
//
//import android.content.Context;
//
//import com.hiva.communicate.app.common.send.SendResponseListener;
//import com.hiva.communicate.app.common.response.BaseResponse;
//import com.hiva.communicate.app.common.send.data.BaseSendControl;
//import com.hiva.communicate.app.server.IResponseListener;
//import com.hiva.communicate.app.utils.LogHelper;
//import com.yongyida.robot.communicate.app.hardware.hand.HandHandler;
//import com.yongyida.robot.communicate.app.hardware.hand.response.data.HandAngle;
//import com.yongyida.robot.communicate.app.hardware.hand.send.HandSend;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.ArmSendControl;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.ChangeArmId;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.FingerSendControl;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.HandAction;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.QueryHandAngle;
//import com.yongyida.robot.communicate.app.hardware.hand.send.data.TeacherSendControl;
//import com.yongyida.robot.model.agreement.Y138Steering;
//import com.yongyida.robot.model.y138.led.Y138LedHandler;
//import com.yongyida.robot.usb_uart.UART;
//
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
///**
// * Create By HuangXiangXiang 2018/6/6
// */
//public class Y138HandHandler extends HandHandler {
//
//    private static final String TAG = Y138HandHandler.class.getSimpleName() ;
//
//    private UART mUART ;
//    private Y138Steering.SteerAction mSteerAction ;
//    private Y138Steering.SteerArm mSteerArm ;
//    private Y138Steering.SteerFinger mSteerFinger ;
//    private Y138Steering.SteerTeacher mSteerTeacher ;
//
//
//    public Y138HandHandler(Context context) {
//        super(context);
//
//        mSteerAction = new Y138Steering.SteerAction() ;
//        mSteerArm = new Y138Steering.SteerArm() ;
//        mSteerFinger = new Y138Steering.SteerFinger() ;
//        mSteerTeacher = new Y138Steering.SteerTeacher() ;
//
//        mUART = UART.getInstance(context) ;
//        mUART.openDevice(null) ;
//
//    }
//
//    @Override
//    public BaseResponse onHandler(HandSend send, final IResponseListener responseListener) {
//
//        BaseSendControl baseSendControl = send.getBaseControl() ;
//        LogHelper.i(TAG , LogHelper.__TAG__() );
//
//        if(baseSendControl instanceof HandAction){
//
//            HandAction handAction = (HandAction) baseSendControl;
//            mSteerAction.setData(handAction.getPreHandAction(), handAction.getDirection());
//            byte[] data = Y138Steering.getCmd(mSteerAction) ;
//
//            mUART.writeData(data) ;
//
//            return null ;
//        }
//
//        if(baseSendControl instanceof ChangeArmId){
//
//            ChangeArmId changeArmId = (ChangeArmId) baseSendControl;
//            mSteerArm.changeId(changeArmId.getSrcId(), changeArmId.getDestId());
//
//            byte[] data = Y138Steering.getCmd(mSteerArm) ;
//            mUART.writeData(data) ;
//
//            return null ;
//        }
//
//
//        if(baseSendControl instanceof ArmSendControl){
//
//            ArmSendControl armControl = (ArmSendControl) baseSendControl;
//
//            mSteerArm.controlArms(armControl.getDirection().value, armControl.getJoints());
//
//            byte[] data = Y138Steering.getCmd(mSteerArm) ;
//            mUART.writeData(data) ;
//
//            return null ;
//        }
//
//
//        if(baseSendControl instanceof FingerSendControl){
//
//            FingerSendControl fingerControl = (FingerSendControl) baseSendControl;
//
//            mSteerFinger.controlFingers(fingerControl.getDirection().value, fingerControl.getFingers());
//
//            byte[] data = Y138Steering.getCmd(mSteerFinger) ;
//            mUART.writeData(data) ;
//
//            return null ;
//        }
//
//
//        if(baseSendControl instanceof TeacherSendControl){
//
//            TeacherSendControl teacherControl = (TeacherSendControl) baseSendControl;
//            if(teacherControl.isTeacher()){
//
//                mSteerArm.openTeacherMode();
//
//            }else {
//
//                mSteerArm.closeTeacherMode();
//            }
//
//            byte[] data = Y138Steering.getCmd(mSteerArm) ;
//            mUART.writeData(data) ;
//
//            return null ;
//        }
//
//
//        if(baseSendControl instanceof QueryHandAngle){
//
//            QueryHandAngle queryHandAngle = (QueryHandAngle) baseSendControl;
//            if(responseListener != null){
//
//                UART.getInstance(mContext).addDataChangeListener(queryHandAngle.getTag(), new UART.DataChangeListener() {
//
//                    HandAngle handAngle = new HandAngle() ;
//
//                    @Override
//                    public void onLeftArmChanged(int[] value) {
//
//                        handAngle.leftArmAngle.angles = value ;
//                        responseListener.onResponse(handAngle.getResponse());
//                    }
//
//                    @Override
//                    public void onRightArmChanged(int[] value) {
//
//                        handAngle.rightArmAngle.angles = value ;
//                        responseListener.onResponse(handAngle.getResponse());
//                    }
//
//                    @Override
//                    public void onLeftFinger(int[] value) {
//
//                        handAngle.leftFingerAngle.angles = value ;
//                        responseListener.onResponse(handAngle.getResponse());
//                    }
//
//                    @Override
//                    public void onRightFinger(int[] value) {
//
//                        handAngle.rightFingerAngle.angles = value ;
//                        responseListener.onResponse(handAngle.getResponse());
//                    }
//                });
//
//            }else {
//
//                UART.getInstance(mContext).removeDataChangeListener(queryHandAngle.getTag());
//            }
//
//            return null ;
//        }
//
//
//        return null;
//    }
//}
