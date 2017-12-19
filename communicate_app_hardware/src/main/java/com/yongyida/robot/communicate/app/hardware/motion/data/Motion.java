package com.yongyida.robot.communicate.app.hardware.motion.data;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 * 动作
 */
public class Motion {

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
