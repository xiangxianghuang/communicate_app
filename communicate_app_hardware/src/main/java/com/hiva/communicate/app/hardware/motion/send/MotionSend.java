package com.hiva.communicate.app.hardware.motion.send;

import com.hiva.communicate.app.common.send.BaseSend;

/**
 * Created by HuangXiangXiang on 2017/12/4.
 * 动作
 * 1、头
 *     1.左右
 *     2.上下
 * 2 脖子
 * 3.足
 *
 */
public class MotionSend extends BaseSend {


    public static final String POSITION_HEAD_LEFT_RIGHT         = "headLeftRight" ;     //头部左右
    public static final String POSITION_HEAD_UP_DOWN            = "headUpDown" ;        //头部上下
    public static final String POSITION_NECK                    = "neck" ;              //脖子
    public static final String POSITION_FOOT                    = "foot" ;              //脚




    public static final String TYPE_DIRECT                      = "direct" ;            //头部左
    public static final String TYPE_STOP                        = "stop" ;              //头部右
    public static final String TYPE_turn_                       = "headRight" ;         //头部右
    public static final String TYPE_WAKE_UP                     = "wakeUp" ;       //


    /**位置*/
    private int position ;

    /**方式*/
    private int type ;

    /***/
    private int distance ;
    /**
     *
     * */
    private int time ;
    /**
     * 速度 时间范围1-100
     * */
    private int speed ;



}
