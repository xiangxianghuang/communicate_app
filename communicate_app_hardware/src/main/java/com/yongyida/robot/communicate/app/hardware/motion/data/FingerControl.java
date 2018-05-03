package com.yongyida.robot.communicate.app.hardware.motion.data;

/**
 * Created by HuangXiangXiang on 2018/4/23.
 * 手指的控制
 *
 */
public class FingerControl {


    /**
     * 方向
     * 左右
     * */
    public enum Direction{

        LEFT,
        RIGHT,
        BOTH
    }


    //
    public enum Type{

        TO,
        AT

    }

    public enum Mode{

        STOP,
        TIME,
        SPEED,
        LOOP

    }




    public static class Finger{

        // 从大拇指到小拇指的Id
        private int id ;
        //是否打开 true:打开  false:关闭
        private boolean isOpen ;

        private int mode ;

        private int type;

        private int destance ;

        private int parameter ;
        //延迟时间（毫秒）
        private int delay ;




    }


}
