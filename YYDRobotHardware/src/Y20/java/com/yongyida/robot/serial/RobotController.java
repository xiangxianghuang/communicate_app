/**
 * Copyright (C) 2015 Zhensheng Yongyida Robot Co.,Ltd. All rights reserved.
 * 
 * @author: hujianfeng@yongyida.com
 * @version 0.1
 * @date 201-05-14
 * 
 */
package com.yongyida.robot.serial;

import android.util.Log;

/**
 * 机器人控制接口
 * 
 */
public class RobotController {

	private static final String TAG = RobotController.class.getSimpleName();
	
	private static final int HEAD_MOTOR_LEFTRIGHT = 0;				// 左右摇头电机
	private static final int HEAD_MOTOR_UPDOWN = 1;		    		// 上下点头电机
	private static final int HEAD_MOTOR_DIRECT_LEFT = 1;			// 左右摇头电机向左, 0:正转, 1:反转
	private static final int HEAD_MOTOR_DIRECT_RIGHT = 0;			// 左右摇头电机向右, 0:正转, 1:反转
	private static final int HEAD_MOTOR_DIRECT_UP = 1;		    	// 上下点头电机向上, 0:正转, 1:反转
	private static final int HEAD_MOTOR_DIRECT_DOWN = 0;			// 上下点头电机向下, 0:正转, 1:反转
	
	public static final int DRVTYPE_BY_TIME = 0;					// 驱动类型, 0: 按时间（单位：毫秒）
	public static final int DRVTYPE_BY_DISTANCE = 1;				// 驱动类型, 1: 按距离（单位：厘米）
	public static final int DRVTYPE_BY_LIMIT_SWITCH_POSITION = 2;	// 驱动类型, 2: 头部驱动到限位开关位置
	public static final int DRVTYPE_BY_MID_POSITION =3;     		// 驱动类型, 3: 头部驱动到中间位置
	public static final int DRVTYPE_BY_SHAKE_HEAD =4;				// 驱动类型, 4: 左右连续摇头
	private static final int MIN_SPEED = 120;	        			// 电机速度最小值
	private static final int MAX_SPEED = 1000;	        			// 电机速度最大值
	
	public static final byte READ_FALLSIGNAL_STATE = 2;				// 读防跌落信号状态
	public static final byte READ_CONTROLL_STATE = 3;				// 读控制命令完成状态
	public static final byte READ_MOTORFALL_ONOFF = 4;				// 读电机开关状态和防跌落开关状态
	public static final byte READ_VERSION = 7;		    			// 读固件版本
	public static final byte READ_HEADCONTROLL_STATE = 9;			// 读控制命令完成状态
	
	// 驱动类型
	private int mDrvType = DRVTYPE_BY_TIME;
	
	// 电机转动速度[1 ~ 100]
	private int mSpeed = 50;
	
	// 电机转动时间
	private int mTime = 1000;
	
	// 控制监听
	private ControllListener mControllListener = null;
	
	static {
		System.loadLibrary("control");
	}
	
	private static native int openSerial(String devName);
	
	private static native int closeSerial();
	
	private static native void setControllCallBacker(RobotController controll, String methodName, String methodSig);
	
	private static native int sendCommand(
			int leftDirection, int rightDirection,
			float leftSpeed, float rightSpeed,
			int argType, int argValue);
	
	private static native int sendHeadCommand(
			int motorSelect,
			int direct,
			float motorSpeed,
			int argType,
			int argValue);
			
	private static native void setUpdateStatus(boolean arg);
	
	private static native boolean getUpdateStatus();
	
	/**
	 * 设置防跌落开关开启状态
	 * @param byte 1: 开, 0: 关 
	 * @return
	 */
	private static native void setFallOnOff(byte onoff);
	
	/**
	 * 读电机开启状态和防跌落开关状态
	 * @param byte 固定值：0xff
	 * @return
	 */
	private static native void readState(byte arg);
	
	/**
	 * open, 打开串口
	 * 
	 * @param String devName
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int open(String devName) {
		Log.d(TAG, "open(), devName: " + devName);
		
		int ret = openSerial(devName);
		if (ret == 0) {
			setControllCallBacker(this, "onCallBacker", "(I[B)V");
		}
		return ret;
	}

	/**
	 * close, 关闭串口
	 * 
	 * @param void
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int close() {
		Log.d(TAG, "close()");
		
		return closeSerial();
	}
	
	/**
	 * JNI串口读回调
	 * 
	 * @param int cmdType　命令类型
	 *        byte[] data　命令数据
	 * 
	 * @return void
	 * 
	 */
	public void onCallBacker(int cmdType, byte[] data) {
		if (mControllListener != null) {
			mControllListener.onCallBack(cmdType, data);
		}
	}
	
	/**
	 * 返回驱动类型
	 * @param
	 * @return int
	 *
	 */
	public int getDrvType() {
		return mDrvType;
	}
	
	/**
	 * 设置驱动类型
	 * @param drvType
	 * 
	 */
	public void setDrvType(int drvType) {
		mDrvType = drvType;
		Log.d(TAG, "setDrvType(), DrvType: " + mDrvType);
	}
	
	/**
	 * 返回最小速度
	 * @param
	 * @return int
	 *
	 */
	public static int getMinSpeed() {
		return 0;
	}
	
	/**
	 * 返回最大速度
	 * @param
	 * @return int
	 *
	 */
	public static int getMaxSpeed() {
		return 100;
	}
	
	/**
	 * 返回速度
	 * @param
	 * @return int
	 *
	 */
	public int getSpeed() {
		return mSpeed;
	}
	
	/**
	 * 设置速度[1 ~ 100]
	 * @param speed
	 * 
	 */
	public void setSpeed(int speed) {
		if (speed <= 0) {
			speed = getMinSpeed();
		}
		else if ( speed >= 100) {
			speed = getMaxSpeed();
		}
		mSpeed = MIN_SPEED + (int)((MAX_SPEED - MIN_SPEED) / 100.0 * speed);
		Log.d(TAG, "setSpeed(), Speed ratio: " + speed + "%, value: " + mSpeed);
	}

	public int getSpeedValue(int speed) {
		if (speed < 0) {
			speed = 0;
		}
		else if ( speed > 100) {
			speed = 100;
		}

		return MIN_SPEED + (int)((MAX_SPEED - MIN_SPEED) / 100.0 * speed);
	}
	
	/**
	 * 设置控制监听接口
	 * @param ControllListener
	 * 
	 */
	public void setControllListener(ControllListener listener) {
		mControllListener = listener;
	}
	
	/**
	 * doCommand, 执行命令
	 * 
	 * @param int leftDirection, 左电机转动方向, 0:正转, -1: 反转
	 *        int rightDirection, 右电机转动方向, 0:正转, -1: 反转
	 *        float leftSpeed, 左电机转动速率
	 *		  float rightSpeed, 右电机转动速率
	 *        int argType, 参数类型, 0: 参数表示时间（单位：毫秒）, 1: 参数表示距离（单位：厘米）
     *        int argValue, 参数值, 表示转动的距离或时间
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int doCommand(
			int leftDirection,
			int rightDirection,
			float leftSpeed,
			float rightSpeed,
			int argType,
			int argValue) {
		Log.d(TAG, "doCommand()");
		return sendCommand(leftDirection,
				rightDirection,
				leftSpeed,
				rightSpeed,
				argType,
				argValue);
	}
	
	/**
	 * forward, 前进
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int forward(int arg) {
		Log.d(TAG, "forward()");
		return sendCommand(0,  // 左电机正转
				0,             // 右电机正转
				mSpeed,        // 左电机转动速率
				mSpeed,        // 右电机转动速率
				mDrvType,      // 参数类型
				arg);          // 参数值
	}

	/**
	 * back, 后退
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int back(int arg) {
		Log.d(TAG, "back()");
		return sendCommand(-1, // 左电机反转
				-1,            // 右电机反转
				mSpeed,        // 左电机转动速率
				mSpeed,        // 右电机转动速率
				mDrvType,      // 参数类型
				arg);          // 参数值
	}

	/**
	 * left, 左转
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int left(int arg) {
		Log.d(TAG, "left()");
		return sendCommand(-1,  // 左电机正转
				0,             // 右电机正转
				mSpeed,        // 左电机转动速率
				mSpeed,        // 右电机转动速率
				mDrvType,      // 参数类型
				arg);          // 参数值
	}

	/**
	 * right, 右转
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int right(int arg) {
		Log.d(TAG, "right()");
		return sendCommand(0,  // 左电机正转
				-1,             // 右电机正转
				mSpeed,        // 左电机转动速率
				mSpeed,             // 右电机转动速率
			    mDrvType,      // 参数类型
				arg);          // 参数值
	}
		/**
	 * left, 向左转
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int turnLeft(int arg) {
		Log.d(TAG, "turnLeft()");
		return sendCommand(0,       // 左电机正转
				0,                  // 右电机正转
				0,             // 右电机转动速率
				mSpeed,        		    // 左电机转动速率0
				mDrvType,           // 参数类型
				arg);               // 参数值
	}
	
	/**
	 * right, 向右转
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int turnRight(int arg) {
		Log.d(TAG, "turnRight()");
		return sendCommand(0,      // 左电机正转
				0,                 // 右电机正转
				mSpeed,                 // 右电机转动速率0
			    0,            // 左电机转动速率
			    mDrvType,          // 参数类型
				arg);              // 参数值
	}

	/**
	 * 向左后方转
	 * */
	public int backTurnLeft(int arg){
		Log.d(TAG, "backTurnLeft()");
		return sendCommand(-1,      // 左电机反转
				-1,                 // 右电机反转
				mSpeed,            	// 左电机转动速率
				0,             		// 右电机转动速率0
				mDrvType,          // 参数类型
				arg);              // 参数值

	}

	/**
	 * 向右后方转
	 * */
	public int backTurnRight(int arg){
		Log.d(TAG, "backTurnRight()");
		return sendCommand(-1,      // 左电机正转
				-1,                 // 右电机正转
				0,            		// 左电机转动速率0
				mSpeed,             // 右电机转动速率0
				mDrvType,          // 参数类型
				arg);              // 参数值
	}



	/**
	 * stop, 停止
	 * 
	 * @param void
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int stop() {
		Log.d(TAG, "stop()");
		return sendCommand(0,  // 左电机正转
				0,             // 右电机正转
				0,             // 左电机转动速率0
				0,             // 右电机转动速率0
				mDrvType,      // 参数类型
			    0xFFFF);       // 参数值
	}
	
	/**
	 * 设置防跌落开关开启状态
	 * @param boolean true: 开, false: 关 
	 * @return
	 */
	public void setFallOn(boolean on) {
		Log.d(TAG, "setFallOn(), on: " + on);
		
		setFallOnOff((byte)(on ? 1 : 0));
	}
	
	/**
	 * 读电机开启状态和防跌落开关状态, 状态会通过回调返回。
	 * 
	 * @param
	 * @return
	 */
	public void readState(int arg) {
		Log.d(TAG, "readState()");
		
		readState((byte)arg);
	}
	
	
	
	/**
	 * doHeadCommand, 执行头部命令
	 * 
	 * @param int motorSelect, 电机选择, 0为左右摇头电机, 1为上下点头电机
	 *        int direct, 电机转动方向, 0:正转, 1:反转
	 *        float motorSpeed, 电机转动速率
	 *        int argType, 参数类型, 0: 参数表示时间（单位：毫秒）, 1: 参数表示距离（单位：厘米）
     *        int argValue, 参数值, 表示转动的距离或时间
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int doHeadCommand(
			int motorSelect,
			int direct,
			float motorSpeed,
			int argType,
			int argValue) {
		Log.d(TAG, "doHeadCommand()");
		return sendHeadCommand(motorSelect,
				direct,
				motorSpeed,
				argType,
				argValue);
	}

	/**
	 * headLeft, 头部左转
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int headLeft(int arg) {
		Log.d(TAG, "headLeft()");
		return doHeadCommand(
				HEAD_MOTOR_LEFTRIGHT,
				HEAD_MOTOR_DIRECT_LEFT,
				mSpeed,
				mDrvType,
				arg);
	}
	
	
	/**
	 * headRight, 头部右转
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int headRight(int arg) {
		Log.d(TAG, "headRight()");
		return doHeadCommand(
				HEAD_MOTOR_LEFTRIGHT,
				HEAD_MOTOR_DIRECT_RIGHT,
				mSpeed,
				mDrvType,
				arg);
	}
	
	/**
	* headLeftEnd, 头部左转到限位开关位置
	*
	*/
	public int headLeftEnd(){
		Log.d(TAG, "headLeftEnd()");
		return doHeadCommand(
				HEAD_MOTOR_LEFTRIGHT,
				HEAD_MOTOR_DIRECT_LEFT,
				mSpeed,
				DRVTYPE_BY_LIMIT_SWITCH_POSITION,
				mTime);
	}
	
	/**
	* headLeftEnd, 头部右转到限位开关位置
	*
	*/
	public int headRightEnd(){
		Log.d(TAG, "headRightEnd()");
		return doHeadCommand(
				HEAD_MOTOR_LEFTRIGHT,
				HEAD_MOTOR_DIRECT_RIGHT,
				mSpeed,
				DRVTYPE_BY_LIMIT_SWITCH_POSITION,
				mTime);
	}
	
	/**
	* headLeftTurnMid, 头部从左边驱动到中间位置
	*
	*/
	public int headLeftTurnMid(){
		Log.d(TAG, "headLeftTurnMid()");
		return doHeadCommand(
				HEAD_MOTOR_LEFTRIGHT,
				HEAD_MOTOR_DIRECT_RIGHT,
				mSpeed,
				DRVTYPE_BY_MID_POSITION,
				mTime);
	}
	
	/**
	* headRightTurnMid, 头部从右边驱动到中间位置
	*
	*/
	public int headRightTurnMid(){
		Log.d(TAG, "headRightTurnMid()");
		return doHeadCommand(
				HEAD_MOTOR_LEFTRIGHT,
				HEAD_MOTOR_DIRECT_LEFT,
				mSpeed,
				DRVTYPE_BY_MID_POSITION,
				mTime);
	}
	
	/**
	* headShake, 头部从左边开始做摇头动作
	*
	*/
	public int headShake(){
		Log.d(TAG, "headShake()");
		return doHeadCommand(
				HEAD_MOTOR_LEFTRIGHT,
				HEAD_MOTOR_DIRECT_LEFT,
				mSpeed,
				DRVTYPE_BY_SHAKE_HEAD,
				mTime);
	}
	
	
	/**
	 * headUp, 头部向上
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int headUp(int arg) {
		Log.d(TAG, "headUp()");
		return doHeadCommand(
				HEAD_MOTOR_UPDOWN,
				HEAD_MOTOR_DIRECT_UP,
				mSpeed,
				mDrvType,
				arg);
	}

	/**
	 * headDown, 头部向下
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int headDown(int arg) {
		Log.d(TAG, "headDown()");
		return doHeadCommand(
				HEAD_MOTOR_UPDOWN,
				HEAD_MOTOR_DIRECT_DOWN,
				mSpeed,
				mDrvType,
				arg);
	}
	
	/**
	 * headStop, 头部停止
	 * 
	 * @param void
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int headStop() {
		Log.d(TAG, "headStop()");
		return doHeadCommand(
				HEAD_MOTOR_LEFTRIGHT,
				HEAD_MOTOR_DIRECT_LEFT,
				0,
				mDrvType,
				0xFFFF);
	}
	
	/**
	 * SetUpdateStatus, 设置升级状态
	 * 
	 * @param boolean arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public void SetUpdateStatus(boolean arg) {
		Log.d(TAG, "SetUpdateStatus()");
		
		setUpdateStatus(arg);
	}
	
	/**
	 * GetUpdateStatus, 返回升级状态
	 * 
	 * @param 无
	 * 
	 * @return boolean, true: 升级中, falase: 不在升级中
	 * 
	 */
	public boolean GetUpdateStatus() {
		Log.d(TAG, "GetUpdateStatus()");
		
		return getUpdateStatus();
	}
}
