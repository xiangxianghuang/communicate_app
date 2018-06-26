package com.yongyida.robot.movecontrol;

/**
*  深蓝底座控制行走
*  @version 0.0.5
*/
interface SlamController {

    // 向前
    int forward();

    //向后
    int back() ;

    //向左
    int left();

    //向右
    int right() ;

    //定点
    int touchPosition(int position);
    
	// 介绍完成
	int introduceEnd(int position);  
	
	//停止行走
	int stop() ;

     /** 旋转声源角度
     * @param angle
     *          (-180,0),向左转的角度
     *          (0,180),向右转的角度
     * */
    int turnSoundAngle(int angle) ;

}
